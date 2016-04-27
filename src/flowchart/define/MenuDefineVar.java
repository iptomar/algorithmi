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
package flowchart.define;

import core.Memory;
import core.data.FabstractNumber;
import core.data.Fsymbol;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import core.parser.Mark;
import ui.FProperties;
import ui.flowchart.dialogs.Fdialog;
import ui.flowchart.dialogs.ShapeMenuDialog;
import i18n.Fi18N;
import i18n.FkeyWord;
import flowchart.help.Help;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import flowchart.utils.Theme;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author ZULU
 */
public class MenuDefineVar extends ShapeMenuDialog implements MenuPattern {

    Define define;
    Memory memory;
//    public static int DEFINEDVARS = 1;
//    private static String defaultVarName = FkeyWord.get("TYPE.defaultVarName");

    String oldText = "";

    /**
     * Creates new form TerminatorMenu
     */
    public MenuDefineVar() {
//        super((JFrame) null,Fi18N.get("DEFINE.instruction.title"),true );
        super("DEFINE.instruction.title");

//        setUndecorated(true);
        initComponents();
//        tbPValueArray.removeTabAt(1); // remove array
        I18N();
        //Add key Listener to Text Fields
        txtExpressionShape.addKeyListener(getKeyListener(btOk, KeyEvent.VK_ENTER));
        txtExpressionShape.addKeyListener(getKeyListener(btCancel, KeyEvent.VK_ESCAPE));
        txtComments.addKeyListener(getKeyListener(btCancel, KeyEvent.VK_ESCAPE));
        //------------------------------------------------------------
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    }

    public void I18N() {

        txtExpressionShape.setKeyword(Define.KEYWORD);

        Fi18N.loadButton(btOk, "BUTTON.accept", 24);
        Fi18N.loadButton(btCancel, "BUTTON.cancel", 24);
        Fi18N.loadButton(btHelp, "BUTTON.help", 16);
        Fi18N.loadMenuShapeHelp(lblHelp, "DEFINE.instruction");
        lblHelp.setBackground(FProperties.defineColor);

        lblType.setText(Fi18N.get("DEFINE.type.title"));
        cbDataType.setToolTipText(Theme.toHtml(Fi18N.get("DEFINE.type.help")));
        cbDataType.setModel(new DefaultComboBoxModel(FkeyWord.getDataTypes()));
        cbDataType.setBackground(FProperties.getColor(FProperties.keySintaxBackground));

        lblName.setText(Fi18N.get("DEFINE.name.title"));
        txtName.setToolTipText(Fi18N.loadTooltip("DEFINE.name.help"));

//        lblComments.setText(Fi18N.get("DEFINE.comments.title"));
        txtComments.setToolTipText(Fi18N.loadTooltip("DEFINE.comments.help"));

        //--------------------------- VALORES ------------------------------------------------        
//        lblExpression.setText(Fi18N.get("DEFINE.expression.title"));
        txtExpression.setToolTipText(Theme.toHtml(Fi18N.get("DEFINE.expression.help")));
        txtExpression.paintBackground();
        //--------------------------- ARRAYS ------------------------------------------------
        cbArray.setText(Fi18N.get("DEFINE.array.title"));
        cbArray.setToolTipText(Theme.toHtml(Fi18N.get("DEFINE.array.help")));

        lblColumns.setText(Fi18N.get("DEFINE.columns.title"));
        spColumns.setToolTipText(Fi18N.get("DEFINE.columns.help"));

        lblLines.setText(Fi18N.get("DEFINE.lines.title"));
        spLines.setToolTipText(Fi18N.get("DEFINE.lines.help"));

        txtExpression.setToolTipText(Fi18N.get("DEFINE.definition.help"));

        txtExpression.paintBackground();
        txtDefinition.paintBackground();

        Fi18N.loadTabTile(tabMain, "DEFINE.tab.value", 0);
        Fi18N.loadTabTile(tabMain, "DEFINE.tab.array", 1);
        Fi18N.loadTabTile(tabMain, "MENU.comments", 2);
        Fi18N.loadTabTile(tabMain, "MENU.help", 3);

    }

    private void updateGUI() {
        String myDefinition = "";
        if (cbArray.isSelected()) {
            myDefinition = cbDataType.getSelectedItem().toString()
                    + " " + txtName.getText().trim()
                    + txtDefinition.getText().trim();

        } else {
            myDefinition = cbDataType.getSelectedItem().toString()
                    + " " + txtName.getText().trim()
                    + " " + FkeyWord.OPERATOR_SET
                    + " " + txtExpression.getText().trim();
        }
        myDefinition = myDefinition.replace("\n", " ");
        txtExpressionShape.setText(Define.KEYWORD + " " + myDefinition.trim());
    }

    private void setShape() {
        if (define.getVarSymbol() == null) {  // not defined 
            cbArray.setSelected(false);
            txtName.setText(memory.getnextDefaultVarName());
            txtExpression.setText(Fsymbol.defaultValue(cbDataType.getSelectedItem().toString()));
            txtComments.setText("");
            tabMain.getTabComponentAt(0).setEnabled(true);
            tabMain.getTabComponentAt(1).setEnabled(false);
            tabMain.setSelectedIndex(0); // define var 
        } else if (define.getVarSymbol() != null && define.getVarSymbol() instanceof Farray) { // Array type
            cbArray.setSelected(true);
            cbDataType.setSelectedItem(define.getVarSymbol().getTypeName());
            txtName.setText(define.getVarSymbol().getName());
            txtComments.setText(define.getVarSymbol().getComments());
            Farray array = (Farray) define.getVarSymbol();
            List<Expression> indexes = array.getIndexes();
            //update spinners
            if (indexes.get(0).isConstant()) {
                int index = ((FabstractNumber) indexes.get(0).getReturnType()).getIntValue();
                spColumns.setValue(index);
                spLines.setValue(1);
            }
            //update spinners
            if (indexes.size() > 1 && indexes.get(1).isConstant()) {
                int index = ((FabstractNumber) indexes.get(1).getReturnType()).getIntValue();
                spLines.setValue(index);
            }
            //update definition
            txtDefinition.setText(array.getIndexesDefinition());
            tabMain.getTabComponentAt(1).setEnabled(true);
            tabMain.getTabComponentAt(0).setEnabled(false);
            tabMain.setSelectedIndex(1); // define array 
        } else {
            cbArray.setSelected(false);
            cbDataType.setSelectedItem(define.getVarSymbol().getTypeName());
            txtName.setText(define.getVarSymbol().getName());
            txtExpression.setText(define.getExpression());
            txtComments.setText(define.getVarSymbol().getComments());
            tabMain.getTabComponentAt(0).setEnabled(true);
            tabMain.getTabComponentAt(1).setEnabled(false);
            tabMain.setSelectedIndex(0); // define var   
        }

        updateGUI();

    }

    @Override
    public void showDialog(Fshape shape, int x, int y) {
        oldText = shape.getInstruction();
        getContentPane().setBackground(shape.getBackground());
        //----------------------------------------------------
        //set memory to expression menu
        memory = shape.algorithm.getMemory(shape.parent);
        txtExpression.updateMenu(memory, shape.algorithm.getMyProgram());
        txtDefinition.updateMenu(memory, shape.algorithm.getMyProgram());
        txtExpressionShape.updateMenu(memory, shape.algorithm.getMyProgram());
        //----------------------------------------------------

        define = (Define) shape;
        setShape();

        //-----------------------------------------
        //:::::::::::::::::::::::::::::::::::::::::
        //::::::::   Show Dialog  :::::::::::::::::
        setLocationRelativeTo(null);
        updateFont();
        txtExpressionShape.requestFocus();
        tabMain.setSelectedIndex(2);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btOk = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        btHelp = new javax.swing.JButton();
        tabMain = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtExpression = new ui.flowchart.expression.TextExpression();
        pnArray = new javax.swing.JPanel();
        spColumns = new javax.swing.JSpinner();
        lblLines = new javax.swing.JLabel();
        lblColumns = new javax.swing.JLabel();
        spLines = new javax.swing.JSpinner();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtDefinition = new ui.flowchart.expression.TextExpression();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComments = new javax.swing.JTextArea();
        lblHelp = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblType = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        cbDataType = new javax.swing.JComboBox();
        txtName = new javax.swing.JTextField();
        cbArray = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtExpressionShape = new ui.flowchart.expression.TextExpression();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel3.setLayout(new java.awt.GridLayout(1, 3, 10, 10));

        btOk.setText("ok");
        btOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOkActionPerformed(evt);
            }
        });
        jPanel3.add(btOk);

        btCancel.setText("Cancel");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });
        jPanel3.add(btCancel);

        btHelp.setText("help");
        btHelp.setBorder(null);
        btHelp.setBorderPainted(false);
        btHelp.setContentAreaFilled(false);
        btHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHelpActionPerformed(evt);
            }
        });
        jPanel3.add(btHelp);

        txtExpression.setOpaque(true);
        txtExpression.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtExpressionCaretUpdate(evt);
            }
        });
        jScrollPane4.setViewportView(txtExpression);

        tabMain.addTab("tab4", jScrollPane4);

        spColumns.setModel(new javax.swing.SpinnerNumberModel(2, 2, 20, 1));
        spColumns.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spColumnsStateChanged(evt);
            }
        });

        lblLines.setText("linhas");

        lblColumns.setText("colunas");

        spLines.setModel(new javax.swing.SpinnerNumberModel(1, 1, 20, 1));
        spLines.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spLinesStateChanged(evt);
            }
        });

        txtDefinition.setOpaque(true);
        txtDefinition.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtDefinitionCaretUpdate(evt);
            }
        });
        jScrollPane6.setViewportView(txtDefinition);

        javax.swing.GroupLayout pnArrayLayout = new javax.swing.GroupLayout(pnArray);
        pnArray.setLayout(pnArrayLayout);
        pnArrayLayout.setHorizontalGroup(
            pnArrayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnArrayLayout.createSequentialGroup()
                .addComponent(lblColumns, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spColumns, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(lblLines, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spLines, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 317, Short.MAX_VALUE))
            .addComponent(jScrollPane6)
        );
        pnArrayLayout.setVerticalGroup(
            pnArrayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnArrayLayout.createSequentialGroup()
                .addGroup(pnArrayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnArrayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblColumns)
                        .addComponent(lblLines)
                        .addComponent(spLines, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spColumns))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
        );

        tabMain.addTab("Array", pnArray);

        txtComments.setBackground(new java.awt.Color(204, 204, 204));
        txtComments.setColumns(20);
        txtComments.setRows(2);
        jScrollPane1.setViewportView(txtComments);

        tabMain.addTab("tab4", jScrollPane1);

        lblHelp.setBackground(new java.awt.Color(204, 204, 204));
        lblHelp.setText("help");
        lblHelp.setOpaque(true);
        tabMain.addTab("tab3", lblHelp);

        lblType.setText("Tipo");

        lblName.setText("Nome");

        cbDataType.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        cbDataType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbDataType.setSelectedIndex(-1);
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

        cbArray.setText("jCheckBox1");
        cbArray.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbArrayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblType, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbDataType, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbArray)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(cbDataType, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cbArray))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addComponent(lblType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tabMain, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabMain, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txtExpressionShape.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtExpressionShape.setOpaque(true);
        jScrollPane3.setViewportView(txtExpressionShape);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus

    }//GEN-LAST:event_formWindowLostFocus

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
        String myDefinition = txtExpressionShape.getInstruction();
        try {//            
            define.buildInstruction(myDefinition, txtComments.getText());
            define.setSelected(false);
            setVisible(false);
        } catch (FlowchartException e) {
            //e.setInstruction(myDefinition);
            Fdialog.compileException(e);
            txtExpressionShape.setInstruction(myDefinition);
            txtExpressionShape.requestFocus();
        }

    }//GEN-LAST:event_btOkActionPerformed


    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed

        try {
            define.buildInstruction(oldText, txtComments.getText());
        } catch (FlowchartException ex) {
        }
        define.setSelected(false);
        setVisible(false);
    }//GEN-LAST:event_btCancelActionPerformed

    private void cbDataTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDataTypeActionPerformed
        txtExpression.setText(Fsymbol.defaultValue(cbDataType.getSelectedItem().toString()));
        if (cbArray.isSelected()) {
            spColumnsStateChanged(null);
            spColumns.requestFocus();
        } else {
            txtExpression.requestFocus();
            txtExpression.setSelectionStart(0);
            txtExpression.setSelectionEnd(txtExpression.getText().length());
        }
        updateGUI();

    }//GEN-LAST:event_cbDataTypeActionPerformed

    private void cbArrayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbArrayActionPerformed
        if (!cbArray.isSelected()) {
            tabMain.getTabComponentAt(1).setEnabled(false);
            tabMain.getTabComponentAt(0).setEnabled(true);
            txtExpression.requestFocus();
            txtExpression.setSelectionEnd(txtExpression.getText().length());
            tabMain.setSelectedIndex(0);

        } else {
            tabMain.getTabComponentAt(1).setEnabled(true);
            tabMain.getTabComponentAt(0).setEnabled(false);

            spColumnsStateChanged(null);
            spColumns.requestFocus();
            tabMain.setSelectedIndex(1);
        }
        updateGUI();

    }//GEN-LAST:event_cbArrayActionPerformed

    private void spColumnsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spColumnsStateChanged
        String def = "" + Mark.SQUARE_OPEN + (int) spColumns.getValue() + Mark.SQUARE_CLOSE;
        if ((int) spLines.getValue() > 1) {
            def += "" + Mark.SQUARE_OPEN + (int) spLines.getValue() + Mark.SQUARE_CLOSE;
        }
        txtDefinition.setText(def);
        updateGUI();
    }//GEN-LAST:event_spColumnsStateChanged

    private void spLinesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spLinesStateChanged
        spColumnsStateChanged(evt);
    }//GEN-LAST:event_spLinesStateChanged

    private void btHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHelpActionPerformed
        Help.show("DEFINE.instruction.www");
    }//GEN-LAST:event_btHelpActionPerformed

    private void txtExpressionCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtExpressionCaretUpdate
        updateGUI();
    }//GEN-LAST:event_txtExpressionCaretUpdate

    private void txtNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNameCaretUpdate
        updateGUI();
    }//GEN-LAST:event_txtNameCaretUpdate

    private void txtDefinitionCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtDefinitionCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDefinitionCaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btHelp;
    private javax.swing.JButton btOk;
    private javax.swing.JCheckBox cbArray;
    private javax.swing.JComboBox cbDataType;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblColumns;
    private javax.swing.JLabel lblHelp;
    private javax.swing.JLabel lblLines;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblType;
    private javax.swing.JPanel pnArray;
    private javax.swing.JSpinner spColumns;
    private javax.swing.JSpinner spLines;
    private javax.swing.JTabbedPane tabMain;
    private javax.swing.JTextArea txtComments;
    private ui.flowchart.expression.TextExpression txtDefinition;
    private ui.flowchart.expression.TextExpression txtExpression;
    private ui.flowchart.expression.TextExpression txtExpressionShape;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
