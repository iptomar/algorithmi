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
package flowchart.execute;

import core.Memory;
import core.data.Fsymbol;
import core.data.Ftext;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import core.parser.Mark;
import core.parser.tokenizer.BreakMarks;
import ui.FProperties;
import ui.flowchart.dialogs.Fdialog;
import ui.flowchart.dialogs.ShapeMenuDialog;
import i18n.Fi18N;
import i18n.FkeyWord;
import flowchart.help.Help;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import flowchart.utils.Theme;
import ui.flowchart.expression.Identation;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author ZULU
 */
public class MenuExecute extends ShapeMenuDialog implements MenuPattern {

    String oldText;
    Execute execute;
    Fsymbol myvar;
    Memory memory;

    /**
     * Creates new form TerminatorMenu
     */
    public MenuExecute() {
        super("EXECUTE.instruction.title");
//        setUndecorated(true);
        initComponents();
        I18N();

        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //------------------------------------------------------------
        //Add key Listener to Text Fields
        txtExpressionShape.addKeyListener(getKeyListener(btOk, KeyEvent.VK_ENTER));
        txtExpressionShape.addKeyListener(getKeyListener(btCancel, KeyEvent.VK_ESCAPE));
        txtComments.addKeyListener(getKeyListener(btCancel, KeyEvent.VK_ESCAPE));
        //------------------------------------------------------------
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    }

    public void I18N() {
        txtExpressionShape.setKeyword(Execute.KEYWORD);
        Fi18N.loadTabTile(tabMain, "EXECUTE.tab.calcutate", 0);
        //Fi18N.loadTabTile(tabMain, "EXECUTE.tab.execute", 1);
        Fi18N.loadTabTile(tabMain, "MENU.comments", 1);
        Fi18N.loadTabTile(tabMain, "MENU.help", 2);

        Fi18N.loadButton(btOk, "BUTTON.accept", 24);
        Fi18N.loadButton(btCancel, "BUTTON.cancel", 24);
        Fi18N.loadButton(btHelp, "BUTTON.help", 16);

        Fi18N.loadMenuShapeHelp(lblHelp, "EXECUTE.instruction");

        lblHelp.setBackground(FProperties.executeColor);
        lblOPEqual.setText(FkeyWord.OPERATOR_SET);

        pnVariable.setBorder(BorderFactory.createTitledBorder(Fi18N.get("EXECUTE.name")));
        pnExpression.setBorder(BorderFactory.createTitledBorder(Fi18N.get("EXECUTE.value")));

        //lblName.setText(Fi18N.get("EXECUTE.name"));
        cbVariable.setToolTipText(Theme.toHtml(Fi18N.get("EXECUTE.name.help")));

        //lblValue.setText(Fi18N.get("EXECUTE.value"));
        txtExpression.setToolTipText(Theme.toHtml(Fi18N.get("EXECUTE.value.help")));

//        lblComments.setText(Fi18N.get("EXECUTE.comments"));
        txtComments.setToolTipText(Theme.toHtml(Fi18N.get("EXECUTE.comments.help")));
    }

    public void updateGUI() {
        String exp = Identation.ident(txtExpression.getText(), memory, execute.algorithm.myProgram);
        if (myvar == null) {
            txtExpressionShape.setInstruction(exp);
            return;
        } else if (myvar != null && (myvar instanceof Farray || myvar instanceof Ftext)) {
            txtIndexes.setVisible(true);
        } else {
            txtIndexes.setVisible(false);
        }
        StringBuilder txt = new StringBuilder(myvar.getName());
        if (myvar instanceof Farray) {
            txt.append(Identation.ident(txtIndexes.getText(), memory, execute.algorithm.myProgram));
        }
        if (myvar instanceof Ftext) {
            txt.append(Identation.ident(txtIndexes.getText(), memory, execute.algorithm.myProgram));
        }
        txt.append(" " + FkeyWord.OPERATOR_SET + " " + exp);
        txtExpressionShape.setInstruction(txt.toString());
    }

    public void setShape(Fshape shape) {

        memory = shape.algorithm.getMemory(shape.parent);

        txtExpressionShape.updateMenu(memory, shape.algorithm.getMyProgram());
        txtExpression.updateMenu(memory, shape.algorithm.getMyProgram());
        txtExpression.paintBackground();
        txtIndexes.updateMenu(memory, shape.algorithm.getMyProgram());
        txtIndexes.paintBackground();

        execute = (Execute) shape;
        if (execute.var != null) {
            myvar = memory.getByName(execute.var.getName());
        }

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel();

        model.addElement(""); // null element to call procedures
        for (Fsymbol var : memory.getMem()) {
            model.addElement(var.getName());
        }
        cbVariable.setModel(model);
        if (myvar == null) {
            //select the first one
            cbVariable.setSelectedIndex(0);
            spTxtIndex.setVisible(false);
        } else {
            cbVariable.setSelectedItem(myvar.getName());
            if (myvar instanceof Farray) {
                spTxtIndex.setVisible(true);
                //definition of indexes
                if (execute.var instanceof Farray) {
                    txtIndexes.setText(((Farray) execute.var).getIndexesDefinition());
                } else {
                    //default definiton of indexes    
                    txtIndexes.setText(getDefaultIndexes((Farray) myvar));
                }
            } else if (myvar instanceof Ftext) {
                spTxtIndex.setVisible(true);
                //definition of indexes
                if (execute.var instanceof Ftext && ((Ftext) execute.var).isIndexed()) {
                    txtIndexes.setText(
                            Mark.SQUARE_OPEN
                            + ((Ftext) execute.var).indexExpression.toString()
                            + Mark.SQUARE_CLOSE
                    );
                } else {
                    //default definiton of indexes    
                    txtIndexes.setText("");
                }
            } else {
                spTxtIndex.setVisible(false);
            }
        }
        tabMain.setSelectedIndex(0); // vars
    }

    @Override
    public void showDialog(Fshape shape, int x, int y) {

        oldText = shape.getInstruction();
  setShape(shape);
        //update indexed var
        //cbVariableActionPerformed(null);
        //-----------------------------------------
        txtExpression.setText(execute.getExpressionToCalculate());
        txtComments.setText(execute.getComments());
        //-----------------------------------------
        //:::::::::::::::::::::::::::::::::::::::::
        updateFont();
        setLocationRelativeTo(null);
        txtExpressionShape.requestFocus();
        tabMain.setSelectedIndex(1);
        this.setVisible(true);

        //:::::::::::::::::::::::::::::::::::::::::
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
        pnMain = new javax.swing.JPanel();
        tabMain = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        lblOPEqual = new javax.swing.JLabel();
        pnExpression = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtExpression = new ui.flowchart.expression.TextExpression();
        pnVariable = new javax.swing.JPanel();
        cbVariable = new javax.swing.JComboBox();
        spTxtIndex = new javax.swing.JScrollPane();
        txtIndexes = new ui.flowchart.expression.TextExpression();
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
        setResizable(false);

        lblOPEqual.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        lblOPEqual.setText("=");

        pnExpression.setBorder(javax.swing.BorderFactory.createTitledBorder("Expression"));
        pnExpression.setLayout(new java.awt.GridLayout(1, 0));

        txtExpression.setOpaque(true);
        txtExpression.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtExpressionCaretUpdate(evt);
            }
        });
        jScrollPane5.setViewportView(txtExpression);

        pnExpression.add(jScrollPane5);

        pnVariable.setBorder(javax.swing.BorderFactory.createTitledBorder("Variable"));

        cbVariable.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        cbVariable.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbVariableActionPerformed(evt);
            }
        });

        txtIndexes.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtIndexesCaretUpdate(evt);
            }
        });
        spTxtIndex.setViewportView(txtIndexes);

        javax.swing.GroupLayout pnVariableLayout = new javax.swing.GroupLayout(pnVariable);
        pnVariable.setLayout(pnVariableLayout);
        pnVariableLayout.setHorizontalGroup(
            pnVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbVariable, 0, 208, Short.MAX_VALUE)
            .addComponent(spTxtIndex)
        );
        pnVariableLayout.setVerticalGroup(
            pnVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnVariableLayout.createSequentialGroup()
                .addComponent(cbVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spTxtIndex, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOPEqual, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnExpression, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnVariable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnExpression, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblOPEqual)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabMain.addTab("Calcular", jPanel1);

        txtComments.setBackground(new java.awt.Color(204, 204, 204));
        txtComments.setColumns(20);
        txtComments.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        txtComments.setRows(2);
        jScrollPane1.setViewportView(txtComments);

        tabMain.addTab("Comentarios", jScrollPane1);

        lblHelp.setBackground(new java.awt.Color(204, 255, 204));
        lblHelp.setText("help");
        lblHelp.setOpaque(true);
        tabMain.addTab("tab4", lblHelp);

        jPanel6.setLayout(new java.awt.GridLayout(1, 3, 10, 10));

        btOk.setText("ok");
        btOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOkActionPerformed(evt);
            }
        });
        jPanel6.add(btOk);

        btCancel.setText("Cancel");
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
        btHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHelpActionPerformed(evt);
            }
        });
        jPanel6.add(btHelp);

        javax.swing.GroupLayout pnMainLayout = new javax.swing.GroupLayout(pnMain);
        pnMain.setLayout(pnMainLayout);
        pnMainLayout.setHorizontalGroup(
            pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabMain)
                    .addGroup(pnMainLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnMainLayout.setVerticalGroup(
            pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabMain, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
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
                    .addComponent(pnMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbVariableActionPerformed
        myvar = memory.getByName(cbVariable.getSelectedItem().toString());
        if (myvar != null) {
            cbVariable.setToolTipText("(" + myvar.getInstruction() + ") " + myvar.getComments());
            if (myvar instanceof Farray) {//-----------------------------ARRAY
                // make a clone of the variable              
                // indexes of the variable is strored in the array expression
                //of the indexes
                myvar = (Fsymbol) myvar.clone();
                //--------------------------------------------------------------
                Farray array = (Farray) myvar;
                spTxtIndex.setVisible(true);
                txtIndexes.setText(getDefaultIndexes(array));
                txtIndexes.setToolTipText(array.getInstruction());
            } else if (myvar instanceof Ftext) {//-----------------------------ARRAY
                // make a clone of the variable              
                // indexes of the variable is strored in the array expression
                //of the indexes
                myvar = (Fsymbol) myvar.clone();
                //--------------------------------------------------------------
                Ftext txt = (Ftext) myvar;
                spTxtIndex.setVisible(true);
                if (txt.isIndexed()) {
                    txtIndexes.setText(txt.indexExpression.toString());
                } else {
                    txtIndexes.setText("");
                }
                txtIndexes.setToolTipText(txt.getInstruction());
            } else {
                spTxtIndex.setVisible(false);
            }
        } else { //-------------------------------------------------------------SINGLE VAR
            cbVariable.setToolTipText(null);
            txtIndexes.setVisible(false);
        }
        updateGUI();
    }//GEN-LAST:event_cbVariableActionPerformed

    public String getDefaultIndexes(Farray array) {
        int numIndexes = array.getNumberOfIndexes();
        // put the indexes [ ] in the text field of update tooltip
        StringBuilder txt = new StringBuilder();
        for (int i = 0; i < numIndexes; i++) {
            txt.append(Mark.SQUARE_OPEN + " 0 " + Mark.SQUARE_CLOSE);
        }
        return txt.toString();
    }


    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed

        try {
            //check parentesis
            BreakMarks.checkParentesis(txtExpression.getText());
            BreakMarks.checkParentesis(txtIndexes.getText());

            execute.buildInstruction(txtExpressionShape.getInstruction(), txtComments.getText());

            execute.parseShape();
            execute.setSelected(false);
            setVisible(false);
        } catch (FlowchartException e) {
            //e.setInstruction(txtExpression.getText());
            Fdialog.compileException(e);
            execute.expressionToCalculate = null;
        }
    }//GEN-LAST:event_btOkActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        execute.setInstruction(oldText);
        execute.setSelected(false);
        setVisible(false);
    }//GEN-LAST:event_btCancelActionPerformed

    private void btHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHelpActionPerformed
        Help.show("EXECUTE.intruction.www");
    }//GEN-LAST:event_btHelpActionPerformed

    private void txtExpressionCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtExpressionCaretUpdate
        updateGUI();
    }//GEN-LAST:event_txtExpressionCaretUpdate

    private void txtIndexesCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtIndexesCaretUpdate
        updateGUI();
    }//GEN-LAST:event_txtIndexesCaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btHelp;
    private javax.swing.JButton btOk;
    private javax.swing.JComboBox cbVariable;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lblHelp;
    private javax.swing.JLabel lblOPEqual;
    private javax.swing.JPanel pnExpression;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnVariable;
    private javax.swing.JScrollPane spTxtIndex;
    private javax.swing.JTabbedPane tabMain;
    private javax.swing.JTextArea txtComments;
    private ui.flowchart.expression.TextExpression txtExpression;
    private ui.flowchart.expression.TextExpression txtExpressionShape;
    private ui.flowchart.expression.TextExpression txtIndexes;
    // End of variables declaration//GEN-END:variables

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
