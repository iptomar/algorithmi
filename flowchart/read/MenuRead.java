/*
 * Copyright (c) 2015 Instituto Politecnico de Tomar. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2015   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package flowchart.read;

import core.Memory;
import core.data.Fsymbol;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import core.parser.Mark;
import ui.FProperties;
import ui.flowchart.dialogs.Fdialog;
import ui.flowchart.dialogs.ShapeMenuDialog;
import flowchart.execute.Execute;
import flowchart.help.Help;
import flowchart.shape.Fshape;
import flowchart.shape.MenuPattern;
import flowchart.utils.Theme;
import i18n.Fi18N;
import i18n.FkeyWord;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author ZULU
 */
public class MenuRead extends ShapeMenuDialog implements MenuPattern {

    String oldText;
    Read read;
    Fsymbol myvar;
    Memory memory;

    /**
     * Creates new form TerminatorMenu
     */
    public MenuRead() {
        super("READ.instruction.title");
//        setUndecorated(true);
        initComponents();
        //Add key Listener to Text Fields
        txtExpressionShape.addKeyListener(getKeyListener(btOk, KeyEvent.VK_ENTER));
        txtExpressionShape.addKeyListener(getKeyListener(btCancel, KeyEvent.VK_ESCAPE));
        txtComments.addKeyListener(getKeyListener(btCancel, KeyEvent.VK_ESCAPE));

        I18N();
    }

    public void I18N() {

        txtExpressionShape.setKeyword(Read.KEYWORD);
        Fi18N.loadTabTile(tabMain, "READ.tab.read", 0);
        Fi18N.loadTabTile(tabMain, "MENU.comments", 1);
        Fi18N.loadTabTile(tabMain, "MENU.help", 2);

        Fi18N.loadTabTile(tabRead, "READ.tab.memory", 0);
        Fi18N.loadTabTile(tabRead, "READ.tab.define", 1);

        Fi18N.loadButton(btOk, "BUTTON.accept", 24);
        Fi18N.loadButton(btCancel, "BUTTON.cancel", 24);
        Fi18N.loadButton(btHelp, "BUTTON.help", 16);
        Fi18N.loadMenuShapeHelp(lblHelp, "READ.instruction");

        cbDataType.setToolTipText(Theme.toHtml(Fi18N.get("DEFINE.type.help")));
        cbDataType.setModel(new DefaultComboBoxModel(FkeyWord.getDataTypes()));

        Fi18N.loadLabel(lblNewName, "READ.newName");
        Fi18N.loadLabel(lblnewType, "READ.newType");

        lblName.setText(Fi18N.get("DEFINE.name.title"));
        txtName.setToolTipText(Fi18N.loadTooltip("DEFINE.name.help"));

        lblHelp.setBackground(FProperties.readColor);

        lblName.setText(Fi18N.get("READ.name"));
        cbVariable.setToolTipText(Theme.toHtml(Fi18N.get("READ.name.help")));

//        lblComments.setText(Fi18N.get("READ.comments"));
        txtComments.setToolTipText(Theme.toHtml(Fi18N.get("READ.comments.help")));

    }

    private void updateGUI() {
        StringBuilder txt = new StringBuilder();
        if (tabRead.getSelectedIndex() == 0) {
            //nothing is defined in memory
            if (cbVariable.getSelectedIndex() < 0) {
                tabRead.setSelectedIndex(1);
                return;
            }
            //select one variable
            txt.append(cbVariable.getSelectedItem().toString());
            if (myvar instanceof Farray) {
                txt.append(txtIndexes.getText().trim());
            }
        } else {
            txt.append(cbDataType.getSelectedItem().toString()
                    + " " + txtName.getText());
        }
        txtExpressionShape.setInstruction(txt.toString());
    }

    public void setShape(Fshape shape) {
        read = (Read) shape;
        memory = shape.algorithm.getMemory(shape.parent);

        txtExpressionShape.updateMenu(memory, shape.algorithm.getMyProgram());
        txtIndexes.updateMenu(memory, shape.algorithm.getMyProgram());

        if (memory.getMem().isEmpty()) {
            tabRead.getTabComponentAt(0).setEnabled(false);
            tabRead.setSelectedIndex(1);
        } else {
            tabRead.getTabComponentAt(0).setEnabled(true);
            tabRead.setSelectedIndex(1);
        }
        myvar = read.var;
        //-----------------------------------------
        txtComments.setText(read.getComments());
        //--------------------------------------------------------------- VARS IN MEMORY
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel();
        for (Fsymbol var : memory.getMem()) {
            model.addElement(var.getName());
        }
        cbVariable.setModel(model);
        pnIndex.setVisible(false);
        tabMain.setSelectedIndex(0);
        //---------------------------------------------------------------
        if (myvar != null && memory.isDefined(myvar.getName())) { // variable defined
            cbVariable.setSelectedItem(myvar.getName());
            if (myvar instanceof Farray) {
                pnIndex.setVisible(true);
                txtIndexes.setInstruction(((Farray) read.var).getIndexesDefinition());
            }
            txtName.setText(memory.getnextDefaultVarName());
            tabRead.setSelectedIndex(0);
        } else if (myvar != null) { //
            cbDataType.setSelectedItem(myvar.getTypeName());
            txtName.setText(myvar.getName());
            tabRead.setSelectedIndex(1);
        } else if (memory.isEmpty()) {
            txtName.setText(memory.getnextDefaultVarName());
            tabRead.setSelectedIndex(1); // define            
        } else {
            tabRead.setSelectedIndex(0); // read
        }
        updateGUI();
    }

    public void showDialog(Fshape shape, int x, int y) {
        updateFont();
        setLocationRelativeTo(null);
        oldText = shape.getInstruction();
        setShape(shape);
        //-----------------------------------------
        //:::::::::::::::::::::::::::::::::::::::::
        //::::::::   Show Dialog  :::::::::::::::::
        setLocationRelativeTo(null);
        updateFont();        
        txtExpressionShape.requestFocus();
        tabMain.setSelectedIndex(1);
        this.setVisible(true);
        //::::::::::::::::::::::::::::::::::::::::
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tabMain = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        tabRead = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        cbVariable = new javax.swing.JComboBox();
        pnIndex = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtIndexes = new ui.flowchart.expression.TextExpression();
        jPanel4 = new javax.swing.JPanel();
        cbDataType = new javax.swing.JComboBox();
        txtName = new javax.swing.JTextField();
        lblnewType = new javax.swing.JLabel();
        lblNewName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComments = new javax.swing.JTextArea();
        lblHelp = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btOk = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        btHelp = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtExpressionShape = new ui.flowchart.expression.TextExpression();

        jToggleButton1.setText("jToggleButton1");

        jButton3.setText("jButton3");

        jLabel3.setText("jLabel3");

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFocusable(false);
        setResizable(false);

        jPanel2.setFocusable(false);

        tabMain.setFocusable(false);

        tabRead.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        tabRead.setFocusable(false);
        tabRead.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabReadStateChanged(evt);
            }
        });

        lblName.setText("Nome");
        lblName.setFocusable(false);

        cbVariable.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        cbVariable.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbVariableActionPerformed(evt);
            }
        });

        pnIndex.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "Index"));
        pnIndex.setLayout(new java.awt.BorderLayout());

        txtIndexes.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtIndexesCaretUpdate(evt);
            }
        });
        txtIndexes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIndexesKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(txtIndexes);

        pnIndex.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(38, Short.MAX_VALUE))
                    .addComponent(pnIndex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        tabRead.addTab("tab1", jPanel3);

        cbDataType.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        cbDataType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbDataType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDataTypeActionPerformed(evt);
            }
        });

        txtName.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        txtName.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNameCaretUpdate(evt);
            }
        });

        lblnewType.setText("Tipo");

        lblNewName.setText("Nome");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblNewName, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblnewType, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbDataType, 0, 456, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbDataType, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblnewType, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNewName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        tabRead.addTab("tab2", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabRead)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabRead, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        tabMain.addTab("tab1", jPanel1);

        txtComments.setBackground(new java.awt.Color(204, 204, 204));
        txtComments.setColumns(20);
        txtComments.setRows(2);
        jScrollPane1.setViewportView(txtComments);

        tabMain.addTab("tab2", jScrollPane1);

        lblHelp.setText("help");
        lblHelp.setFocusable(false);
        lblHelp.setOpaque(true);
        tabMain.addTab("tab3", lblHelp);

        jPanel6.setFocusable(false);
        jPanel6.setLayout(new java.awt.GridLayout(1, 3, 10, 10));

        btOk.setText("ok");
        btOk.setFocusable(false);
        btOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOkActionPerformed(evt);
            }
        });
        jPanel6.add(btOk);

        btCancel.setText("Cancel");
        btCancel.setFocusable(false);
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });
        jPanel6.add(btCancel);

        btHelp.setText("help");
        btHelp.setBorder(null);
        btHelp.setBorderPainted(false);
        btHelp.setContentAreaFilled(false);
        btHelp.setFocusable(false);
        btHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHelpActionPerformed(evt);
            }
        });
        jPanel6.add(btHelp);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(tabMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtExpressionShape.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtExpressionShape.setOpaque(true);
        jScrollPane4.setViewportView(txtExpressionShape);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbVariableActionPerformed

        if (myvar != null) {
            myvar = memory.getByName(cbVariable.getSelectedItem().toString());
            cbVariable.setToolTipText("(" + myvar.getInstruction() + ") " + myvar.getComments());
            if (myvar instanceof Farray) {//-----------------------------ARRAY
                // make a clone of the variable              
                // indexes of the variable is strored in the array expression
                //of the indexes
                myvar = (Fsymbol) myvar.clone();
                //--------------------------------------------------------------
                Farray array = (Farray) myvar;
                // put the indexes [ ] in the text field of update tooltip
                StringBuilder txt = new StringBuilder();
                int numIndexes = array.getNumberOfIndexes();
                for (int i = 0; i < numIndexes; i++) {
                    txt.append(Mark.SQUARE_OPEN + " 0 " + Mark.SQUARE_CLOSE);
                }
                txtIndexes.setText(txt.toString());

                txtIndexes.setToolTipText(array.getInstruction());
                pnIndex.setVisible(true);

            } else {
                pnIndex.setVisible(false);
            }
        } else { //-------------------------------------------------------------SINGLE VAR
            cbVariable.setToolTipText(null);
            pnIndex.setVisible(false);
        }
        updateGUI();
    }//GEN-LAST:event_cbVariableActionPerformed

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed

        try {
            if (myvar instanceof Farray) {
                //check definition
                myvar = Execute.buildArray(cbVariable.getSelectedItem().toString()
                        + txtIndexes.getText(), memory, read.algorithm.getMyProgram());
            }
            read.buildInstruction(txtExpressionShape.getInstruction(), txtComments.getText());
            read.parseShape();
            read.setSelected(false);
            setVisible(false);
        } catch (FlowchartException e) {
            Fdialog.compileException(e);
            read.var = null;
        }

    }//GEN-LAST:event_btOkActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        read.setSelected(false);
        read.setInstruction(oldText);
        setVisible(false);
    }//GEN-LAST:event_btCancelActionPerformed

    private void btHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHelpActionPerformed
        Help.show("READ.instruction.www");
    }//GEN-LAST:event_btHelpActionPerformed

    private void txtIndexesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIndexesKeyTyped
        updateGUI();
    }//GEN-LAST:event_txtIndexesKeyTyped

    private void cbDataTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDataTypeActionPerformed
        updateGUI();
    }//GEN-LAST:event_cbDataTypeActionPerformed

    private void txtNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNameCaretUpdate
        updateGUI();
    }//GEN-LAST:event_txtNameCaretUpdate

    private void txtIndexesCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtIndexesCaretUpdate
        updateGUI();
    }//GEN-LAST:event_txtIndexesCaretUpdate

    private void tabReadStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabReadStateChanged
        updateGUI();
    }//GEN-LAST:event_tabReadStateChanged

    public void parseVar() throws FlowchartException {
        myvar = memory.getByName(cbVariable.getSelectedItem().toString());
        if (myvar instanceof Farray) {//-----------------------------ARRAY
            // make a clone of the variable              
            // indexes of the variable is strored in the array expression
            //of the indexes
            myvar = (Fsymbol) myvar.clone();
            //--------------------------------------------------------------
            Farray array = (Farray) myvar;
            array.setTextIndexes(txtIndexes.getText(), memory, read.algorithm.getMyProgram());
            myvar = array;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btHelp;
    private javax.swing.JButton btOk;
    private javax.swing.JComboBox cbDataType;
    private javax.swing.JComboBox cbVariable;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lblHelp;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNewName;
    private javax.swing.JLabel lblnewType;
    private javax.swing.JPanel pnIndex;
    private javax.swing.JTabbedPane tabMain;
    private javax.swing.JTabbedPane tabRead;
    private javax.swing.JTextArea txtComments;
    private ui.flowchart.expression.TextExpression txtExpressionShape;
    private ui.flowchart.expression.TextExpression txtIndexes;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
