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
package flowchart.function;

import core.Memory;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import flowchart.algorithm.FunctionGraph;
import ui.flowchart.dialogs.Fdialog;
import i18n.Fi18N;
import ui.FProperties;
import ui.flowchart.dialogs.ShapeMenuDialog;
import i18n.FkeyWord;
import flowchart.help.Help;
import flowchart.shape.Fshape;
import flowchart.shape.MenuPattern;
import flowchart.utils.Theme;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

/**
 *
 * @author ZULU
 */
public class MenuFunction extends ShapeMenuDialog implements MenuPattern {

    String oldText;
    public Function oldFunction;
    public Function function;
    Memory memory;

    private boolean isCanceled = true; //to verify if the user uses the cancel button

    /**
     * Creates new form TerminatorMenu
     */
    public MenuFunction() {
        super("FUNCTION.instruction.title");
//        setUndecorated(true);
        initComponents();
        I18N();
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //------------------------------------------------------------
        //---  Set <ENTER> and <ESC> actions to controls       -------
        //------------------------------------------------------------
        KeyListener menuKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {  // handler
                if (evt.getKeyCode() == evt.VK_ESCAPE) {
                    btCancelActionPerformed(null);
                }
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    btOkActionPerformed(null);
                    evt.consume();
                }
            }
        };
        //txtComments.addKeyListener(menuKeyListener);
        txtExpressionShape.addKeyListener(menuKeyListener);
        txtName.addKeyListener(menuKeyListener);
    }

    public void I18N() {
        txtExpressionShape.setKeyword(Function.KEYWORD_FUNCTION);
        Fi18N.loadButton(btOk, "MENU.OK", 24);
        Fi18N.loadButton(btCancel, "MENU.cancel", 24);
        Fi18N.loadButton(btHelp, "MENU.help", 16);
        //---------------- T A B S  -------------------
        Fi18N.loadTabTile(tabMain, "FUNCTION.tabFuncName", 0);
        Fi18N.loadTabTile(tabMain, "MENU.comments", 1);
        Fi18N.loadTabTile(tabMain, "MENU.help", 2);
        Fi18N.loadButton(btOk, "BUTTON.accept", 24);
        Fi18N.loadButton(btCancel, "BUTTON.cancel", 24);
        Fi18N.loadMenuShapeHelp(lblHelp, "FUNCTION.instruction");
        Fi18N.loadButton(btHelp, "BUTTON.help", 16);
        lblHelp.setBackground(FProperties.terminatorColor);
        //------------------------------------------ DEFINICAO --------------------------
        Fi18N.loadLabel(lblReturnFunc, "FUNCTION.return");
        Fi18N.loadLabel(lblName, "FUNCTION.nameFunc");
        //combo box return type
        cbReturnType.setToolTipText(Theme.toHtml(Fi18N.get("FUNCTION.return.help")));
        loadDataTypes(cbReturnType, true);
        //------------------------------------------ PARAMETROS --------------------------
        Fi18N.loadButton(btAddParameter, "FUNCTION.buttonAdd", 16);
        Fi18N.loadButton(btRemoveParameter, "FUNCTION.buttonRemove", 16);

        //combo box type parameter
        loadDataTypes(cbTypeParam, false);
        cbTypeParam.setToolTipText(Theme.toHtml(Fi18N.get("FUNCTION.typeParameter.help")));

    }

    private void loadDataTypes(JComboBox cb, boolean _void) {
        //--------------------------------------------------------------------- 
        //combo box return type
        //---------------------------------------------------------------------
        String[] types = FkeyWord.getDataTypes();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        if (_void) {
            model.addElement(core.data.Fvoid.TYPE_VOID_NAME);
        }
        for (String type : types) {
            model.addElement(type);
        }
        cb.setModel(model);
        //---------------------------------------------------------------------
        //--------------------------------------------------------------------- 
    }

    public boolean validateName(String txt) {
        try {
            Fsymbol.validadeName(txt);
            function.algorithm.myProgram.validateNewName(txt);
        } catch (Exception e) {
            FlowchartException def = new FlowchartException("FUNCTION.invalidName", txt);
            Fdialog.compileException(def);
            return false;
        }
        return true;
    }

    public void loadParameters() {
        DefaultListModel model = new DefaultListModel();
        for (FunctionParameter p : function.getParameters()) {
            model.addElement(p);
        }
        lstParams.setModel(model);
        cbReturnType.setSelectedItem(function.getReturnSymbol().getTypeName());
        txtName.setText(function.getFunctionName());
        txtComments.setText(function.getComments());
        txtExpressionShape.setInstruction(function.getTextualInstruction());
        cbTypeParam.setSelectedIndex(-1);
        txtParameterName.setText("");
    }

    public void updateGUI() {
        txtExpressionShape.setInstruction(function.getTextualInstruction());
    }

    public void showDialog(Fshape shape, int x, int y) {
         oldFunction = (Function)shape; 
         //create clone of old function
        function = new Function(shape.algorithm);
        try {
            function.buildInstruction(shape.getInstruction(), shape.comments);
        } catch (FlowchartException ex) {
          //nothing
        }
        
       
        //---------------------------------------------------------------------- function not defined
        if (function.getFunctionName().isEmpty() || function.getReturnSymbol() == null) {
            String fname = function.algorithm.myProgram.getDefaultNewName();
            setTitle(Fi18N.get("FUNCTION.menu.title") + " " + fname);
            validateName(fname);
            function.setFunctionName(fname);
            function.setParameters(new ArrayList<FunctionParameter>());
            tabMain.setSelectedIndex(0); // define function
        } else {
            tabMain.setSelectedIndex(1); // comments
        }
        loadParameters();
        //-----------------------------------------
        //:::::::::::::::::::::::::::::::::::::::::
        //::::::::   Show Dialog  :::::::::::::::::
        setLocationRelativeTo(null);
        updateFont();
        updateGUI();
        txtExpressionShape.requestFocus();
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

        jPanel4 = new javax.swing.JPanel();
        tabMain = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        cbReturnType = new javax.swing.JComboBox();
        lblReturnFunc = new javax.swing.JLabel();
        pnParametrs = new javax.swing.JPanel();
        cbTypeParam = new javax.swing.JComboBox();
        txtParameterName = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstParams = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        btAddParameter = new javax.swing.JButton();
        btRemoveParameter = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComments = new javax.swing.JTextArea();
        lblHelp = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btOk = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        btHelp = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtExpressionShape = new ui.flowchart.expression.TextExpression();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        lblName.setText("name");

        txtName.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        txtName.setText("txtname");
        txtName.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNameCaretUpdate(evt);
            }
        });
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameFocusLost(evt);
            }
        });
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNameKeyTyped(evt);
            }
        });

        cbReturnType.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        cbReturnType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbReturnType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbReturnTypeActionPerformed(evt);
            }
        });

        lblReturnFunc.setText("return");

        pnParametrs.setBorder(javax.swing.BorderFactory.createTitledBorder("Parameters"));

        cbTypeParam.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        cbTypeParam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtParameterName.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        txtParameterName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtParameterNameFocusLost(evt);
            }
        });

        lstParams.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        lstParams.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstParams.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstParamsValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(lstParams);

        jPanel2.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        btAddParameter.setText("jButton1");
        btAddParameter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddParameterActionPerformed(evt);
            }
        });
        jPanel2.add(btAddParameter);

        btRemoveParameter.setText("jButton1");
        btRemoveParameter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveParameterActionPerformed(evt);
            }
        });
        jPanel2.add(btRemoveParameter);

        javax.swing.GroupLayout pnParametrsLayout = new javax.swing.GroupLayout(pnParametrs);
        pnParametrs.setLayout(pnParametrsLayout);
        pnParametrsLayout.setHorizontalGroup(
            pnParametrsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnParametrsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnParametrsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbTypeParam, 0, 237, Short.MAX_VALUE)
                    .addComponent(txtParameterName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        pnParametrsLayout.setVerticalGroup(
            pnParametrsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnParametrsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbTypeParam, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtParameterName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 45, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnParametrsLayout.createSequentialGroup()
                .addGroup(pnParametrsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnParametrs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(lblReturnFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(cbReturnType, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbReturnType, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReturnFunc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnParametrs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabMain.addTab("tab1", jPanel1);

        txtComments.setBackground(new java.awt.Color(204, 204, 204));
        txtComments.setColumns(20);
        txtComments.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        txtComments.setRows(2);
        jScrollPane1.setViewportView(txtComments);

        tabMain.addTab("tab3", jScrollPane1);

        lblHelp.setText("help");
        lblHelp.setOpaque(true);
        tabMain.addTab("tab3", lblHelp);

        jPanel8.setLayout(new java.awt.GridLayout(1, 3, 10, 10));

        btOk.setText("ok");
        btOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOkActionPerformed(evt);
            }
        });
        jPanel8.add(btOk);

        btCancel.setText("Cancel");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });
        jPanel8.add(btCancel);

        btHelp.setText("help");
        btHelp.setBorder(null);
        btHelp.setBorderPainted(false);
        btHelp.setContentAreaFilled(false);
        btHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHelpActionPerformed(evt);
            }
        });
        jPanel8.add(btHelp);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabMain)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(tabMain, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txtExpressionShape.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtExpressionShape.setOpaque(true);
        jScrollPane3.setViewportView(txtExpressionShape);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbReturnTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbReturnTypeActionPerformed
        try {
            function.setReturnSymbol(Fsymbol.createSymbolByType(cbReturnType.getSelectedItem().toString()));
            updateGUI();
        } catch (FlowchartException ex) {
            Logger.getLogger(MenuFunction.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_cbReturnTypeActionPerformed

    private void txtParameterNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtParameterNameFocusLost
        if (!txtParameterName.getText().isEmpty()) {
            if (validateName(txtParameterName.getText())) {
                txtParameterName.setBackground(Color.WHITE);
            } else {
                txtParameterName.setBackground(Color.ORANGE);
                this.requestFocus();
            }
        }


    }//GEN-LAST:event_txtParameterNameFocusLost

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        isCanceled = true;
    }//GEN-LAST:event_formWindowDeactivated

    private void btAddParameterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddParameterActionPerformed
        try {
            //-------------------------------------------------------------------------------------------build define instructions
            if (validadeParameterName(txtParameterName.getText())) {
                Fsymbol paramVar = Fsymbol.create(cbTypeParam.getSelectedItem().toString(), txtParameterName.getText());
                FunctionParameter param = new FunctionParameter(paramVar, false, function.algorithm);
                function.parameters.add(param);
                txtParameterName.setBackground(Color.WHITE);
                updateGUI();
            } else {
                txtParameterName.setBackground(Color.ORANGE);
                txtParameterName.requestFocus();
            }

            //------------------------------------------------------------------------------------------------------ build function parameter
        } catch (FlowchartException ex) {
            Logger.getLogger(MenuFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btAddParameterActionPerformed


    private void btRemoveParameterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveParameterActionPerformed
        if (lstParams.getSelectedIndex() >= 0) {
            function.parameters.remove(lstParams.getSelectedIndex());
            updateGUI();
        }
    }//GEN-LAST:event_btRemoveParameterActionPerformed

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
        try {
            Function f = new Function(function.algorithm);
            f.buildInstruction(txtExpressionShape.getInstruction(), txtComments.getText());
            oldFunction.updateDefinition(f);
            oldFunction.setSelected(false);
            isCanceled = false;
//            function.algorithm.setName(function.getFunctionName()); //update name of the algorithm
//            ((FunctionGraph)function.algorithm).updateDefinition(function);
            setVisible(false);
        } catch (FlowchartException e) {
            Fdialog.compileException(e);
        }
    }//GEN-LAST:event_btOkActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
//        try {
//            function.buildInstruction(oldText, oldFunction.getComments());
//        } catch (FlowchartException ex) {
//        }
        oldFunction.setSelected(false);
        isCanceled = true;
        setVisible(false);

    }//GEN-LAST:event_btCancelActionPerformed

    private void btHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHelpActionPerformed
        Help.show("FUNCTION.instruction.www");
    }//GEN-LAST:event_btHelpActionPerformed

    private void lstParamsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstParamsValueChanged
        if (lstParams.getSelectedIndex() >= 0) {
            String sel = lstParams.getSelectedValue().toString();
            cbTypeParam.setSelectedItem(sel.substring(0, sel.indexOf(" ")));
            txtParameterName.setText(sel.substring(sel.indexOf(" ")).trim());
        } else {
            cbTypeParam.setSelectedIndex(-1);
            txtParameterName.setText("");
        }
    }//GEN-LAST:event_lstParamsValueChanged

    private void txtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusLost
        //name is changed
        if (!oldFunction.getFunctionName().equalsIgnoreCase(txtName.getText())) {
            //name is valid
            if (validateName(txtName.getText())) {
                function.setFunctionName(txtName.getText());
            } else {
                txtName.requestFocus();
            }
        }
        updateGUI();
    }//GEN-LAST:event_txtNameFocusLost

    private void txtNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNameCaretUpdate
        function.setFunctionName(txtName.getText().trim());
        updateGUI();
    }//GEN-LAST:event_txtNameCaretUpdate

    private void txtNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyTyped

    }//GEN-LAST:event_txtNameKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddParameter;
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btHelp;
    private javax.swing.JButton btOk;
    private javax.swing.JButton btRemoveParameter;
    private javax.swing.JComboBox cbReturnType;
    private javax.swing.JComboBox cbTypeParam;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblHelp;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblReturnFunc;
    private javax.swing.JList lstParams;
    private javax.swing.JPanel pnParametrs;
    private javax.swing.JTabbedPane tabMain;
    private javax.swing.JTextArea txtComments;
    private ui.flowchart.expression.TextExpression txtExpressionShape;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtParameterName;
    // End of variables declaration//GEN-END:variables
    /**
     * @return the isCanceled
     */
    public boolean isIsCanceled() {
        return isCanceled;
    }

    public boolean validadeParameterName(String name) {
        if (!validateName(name)) {
            return false;
        }
        for (FunctionParameter fp : function.parameters) {
            if (name.equalsIgnoreCase(fp.varSymbol.getName())) {
                FlowchartException def = new FlowchartException("FUNCTION.invalidNameDuplicateParameter", name);
                Fdialog.compileException(def);
                return false;
            }
        }
        return true;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
