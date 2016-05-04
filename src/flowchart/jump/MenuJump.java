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
package flowchart.jump;

import core.data.exception.FlowchartException;
import ui.FProperties;
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

/**
 *
 * @author ZULU
 */
public class MenuJump extends ShapeMenuDialog implements MenuPattern {

    Jump jump;

    /**
     * Creates new form TerminatorMenu
     */
    public MenuJump() {
        super("JUMP.menu.title");
//        setUndecorated(true);
        initComponents();
        I18N();
        //------------------------------------------------------------
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
                }
            }
        };
        this.addKeyListener(menuKeyListener);
//        txtComments.addKeyListener(menuKeyListener);

    }

    public void I18N() {
        Fi18N.loadButton(btOk, "MENU.OK", 24);
        Fi18N.loadButton(btCancel, "MENU.cancel", 24);
        Fi18N.loadButton(btHelp, "MENU.help", 16);
        //---------------- T A B S  -------------------
        Fi18N.loadTabTile(tabMain, "JUMP.jump", 0);
        Fi18N.loadTabTile(tabMain, "MENU.comments", 1);
        Fi18N.loadTabTile(tabMain, "MENU.help", 2);

        Fi18N.loadMenuShapeHelp(lblHelp, "JUMP.instruction");
        lblHelp.setBackground(FProperties.jumpColor);

        rbBreak.setText(FkeyWord.get("KEYWORD.break"));
        rbBreak.setToolTipText(Fi18N.get("JUMP.break.help"));

        rbContinue.setText(FkeyWord.get("KEYWORD.continue"));
        rbContinue.setToolTipText(Fi18N.get("JUMP.continue.help"));

        txtComments.setToolTipText(Theme.toHtml(Fi18N.get("JUMP.comments.help")));

    }

    public void showDialog(Fshape shape, int x, int y) {
        updateFont();
        setLocationRelativeTo(null);

        jump = (Jump) shape;
        Fshape cicle = jump.getCicle(shape);
        if (cicle == null) {
            FlowchartException ex = new FlowchartException("JUMP.exception.noCicle");
            ex.show(shape.getInstruction());
            return;
        }
        if (jump.getInstruction().equalsIgnoreCase(Jump.breakTxt)) {
            rbBreak.setSelected(true);
        } else {
            rbContinue.setSelected(true);
        }
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        tabMain = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        rbBreak = new javax.swing.JRadioButton();
        rbContinue = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComments = new javax.swing.JTextArea();
        lblHelp = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btOk = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        btHelp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        buttonGroup1.add(rbBreak);
        rbBreak.setSelected(true);
        rbBreak.setText("jRadioButton1");
        rbBreak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbBreakActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbContinue);
        rbContinue.setText("jRadioButton2");
        rbContinue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbContinueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbBreak)
                .addGap(106, 106, 106)
                .addComponent(rbContinue)
                .addContainerGap(199, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbContinue)
                    .addComponent(rbBreak))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        tabMain.addTab("tab1", jPanel1);

        txtComments.setBackground(new java.awt.Color(204, 204, 204));
        txtComments.setColumns(20);
        txtComments.setRows(2);
        jScrollPane1.setViewportView(txtComments);

        tabMain.addTab("tab4", jScrollPane1);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tabMain)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabMain, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbBreakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbBreakActionPerformed
        btOkActionPerformed(evt);
    }//GEN-LAST:event_rbBreakActionPerformed

    private void rbContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbContinueActionPerformed
        btOkActionPerformed(evt);
    }//GEN-LAST:event_rbContinueActionPerformed

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
        if (rbBreak.isSelected()) {
            jump.buildInstruction(rbBreak.getText(), txtComments.getText());
        } else {
            jump.buildInstruction(rbContinue.getText(), txtComments.getText());
        }
        jump.setSelected(false);
        setVisible(false);
    }//GEN-LAST:event_btOkActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        jump.setSelected(false);
        setVisible(false);
    }//GEN-LAST:event_btCancelActionPerformed

    private void btHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHelpActionPerformed
        Help.show("FOR.intruction.www");
    }//GEN-LAST:event_btHelpActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btHelp;
    private javax.swing.JButton btOk;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHelp;
    private javax.swing.JRadioButton rbBreak;
    private javax.swing.JRadioButton rbContinue;
    private javax.swing.JTabbedPane tabMain;
    private javax.swing.JTextArea txtComments;
    // End of variables declaration//GEN-END:variables

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
