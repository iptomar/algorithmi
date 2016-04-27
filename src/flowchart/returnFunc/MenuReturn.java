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
package flowchart.returnFunc;

import core.Memory;
import core.data.Fsymbol;
import core.data.Fvoid;
import core.data.exception.FlowchartException;
import ui.FProperties;
import ui.flowchart.dialogs.Fdialog;
import ui.flowchart.dialogs.ShapeMenuDialog;
import i18n.Fi18N;
import i18n.FkeyWord;
import flowchart.function.Function;
import flowchart.help.Help;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import flowchart.utils.Theme;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author ZULU
 */
public class MenuReturn extends ShapeMenuDialog implements MenuPattern {

    Return returnFunc;
    Fsymbol returnType;
    String oldText;
    Memory memory;

    /**
     * Creates new form TerminatorMenu
     */
    public MenuReturn() {
        super("RETURN.instruction.title");
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
    }

    public void I18N() {
        txtExpressionShape.setKeyword(Return.KEYWORD);
        Fi18N.loadButton(btOk, "MENU.OK", 24);
        Fi18N.loadButton(btCancel, "MENU.cancel", 24);
        Fi18N.loadButton(btHelp, "MENU.help", 16);
        //---------------- T A B S  -------------------
        Fi18N.loadTabTile(tabMain, "MENU.comments", 0);
        Fi18N.loadTabTile(tabMain, "MENU.help", 1);

        Fi18N.loadMenuShapeHelp(lblHelp, "RETURN.instruction");
        lblHelp.setBackground(FProperties.terminatorColor);
        txtComments.setToolTipText(Theme.toHtml(Fi18N.get("RETURN.comments.help")));
    }

    public void showDialog(Fshape shape, int x, int y) {

        //----------------------------------------------------
        //set memory to expression menu
        memory = shape.algorithm.getMemory(shape.parent);
        txtExpressionShape.updateMenu(memory, shape.algorithm.getMyProgram());
        initShape(shape);
        //-----------------------------------------
        //:::::::::::::::::::::::::::::::::::::::::
        //::::::::   Show Dialog  :::::::::::::::::
        setLocationRelativeTo(null);
        updateFont();
        txtExpressionShape.requestFocus();
        this.setVisible(true);        
        //:::::::::::::::::::::::::::::::::::::::::
    }

    private void initShape(Fshape shape) {
        btOk.requestFocus();
        returnFunc = (Return) shape;
        Fshape rets = returnFunc.getFunction(returnFunc);
        if (rets == null) {
            txtExpressionShape.setEnabled(false);
            txtExpressionShape.setInstruction("");
            return;
        }
        Function myFunc = (Function) rets;
        returnType = myFunc.getReturnSymbol();
        if (returnType instanceof Fvoid) {
            txtExpressionShape.setEnabled(false);
            txtExpressionShape.setInstruction("");
        } else {
            txtExpressionShape.setEnabled(true);
            if (returnFunc.returnExpression != null) {
                txtExpressionShape.setInstruction(returnFunc.returnExpression.getIdented());
            } else {
                txtExpressionShape.setInstruction(myFunc.getReturnSymbol().getDefaultValue());
            }
            txtExpressionShape.requestFocus();
        }       
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
        jPanel1 = new javax.swing.JPanel();
        tabMain = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComments = new javax.swing.JTextArea();
        lblHelp = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
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

        txtComments.setBackground(new java.awt.Color(204, 204, 204));
        txtComments.setColumns(20);
        txtComments.setRows(2);
        jScrollPane1.setViewportView(txtComments);

        tabMain.addTab("tab2", jScrollPane1);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabMain, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(tabMain, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
        try {
            returnFunc.buildInstruction(txtExpressionShape.getInstruction(), txtComments.getText());
            returnFunc.parseShape();
            returnFunc.setSelected(false);
            setVisible(false);
        } catch (FlowchartException e) {
            Fdialog.compileException(e);
           // e.setInstruction(txtExpressionShape.getText());            
            returnFunc.returnExpression = null;
        }

    }//GEN-LAST:event_btOkActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        try {
            returnFunc.setInstruction(oldText);
            returnFunc.setSelected(false);
        } catch (Exception e) {
        }
        setVisible(false);
    }//GEN-LAST:event_btCancelActionPerformed

    private void btHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHelpActionPerformed
        Help.show("RETURN.instruction.www");
    }//GEN-LAST:event_btHelpActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btHelp;
    private javax.swing.JButton btOk;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lblHelp;
    private javax.swing.JTabbedPane tabMain;
    private javax.swing.JTextArea txtComments;
    private ui.flowchart.expression.TextExpression txtExpressionShape;
    // End of variables declaration//GEN-END:variables

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
