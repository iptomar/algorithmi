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
package flowchart.arrow;

import i18n.Fi18N;
import flowchart.define.Define;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class MenuArrowDefine extends javax.swing.JDialog implements MenuPattern {

    Arrow arrow;

    /**
     * Creates new form TerminatorMenu
     *
     */
    public MenuArrowDefine() {
        super((JFrame) null, Fi18N.get("MENU.arrow.title"), true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Fi18N.loadDialogTitle(this, "MENU.arrow.title", "");
        ((JPanel) getContentPane()).setBorder(BorderFactory.createLineBorder(Color.GREEN));
//        setUndecorated(true);
        initComponents();        
        I18N();
        //exit with escape char  
        addKeyListener(new KeyAdapter() {
        });

        KeyListener escapeListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {  // handler
                if (ke.getKeyCode() == ke.VK_ESCAPE) {
                    btCancelActionPerformed(null);
                }
            }
        };
        this.addKeyListener(escapeListener);

        btDefine.addKeyListener(escapeListener);
        btCancel.addKeyListener(escapeListener);

    }

    public void I18N() {
        int size = 32;
        Fi18N.loadButton(btDefine, "DEFINE.instruction", size);
        Fi18N.loadButton(btCancel, "BUTTON.cancel", size);
    }

    @Override
    public void showDialog(Fshape shape, int x, int y) {
        setLocationRelativeTo(shape.algorithm.graph);
        arrow = (Arrow) shape;
        arrow.setSelected(true);
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

        jToggleButton1 = new javax.swing.JToggleButton();
        jButton3 = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        btDefine = new javax.swing.JButton();

        jToggleButton1.setText("jToggleButton1");

        jButton3.setText("jButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(5, 5, 5, 5));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        btCancel.setText("Cancel");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        btDefine.setText("Definir");
        btDefine.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btDefine.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btDefine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDefineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btDefine, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btDefine, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btDefineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDefineActionPerformed
        Define define = new Define(arrow.algorithm);
        arrow.algorithm.addSimpleShape(arrow, define);
        arrow.setSelected(true);
        setVisible(false);
        define.editMenu(this.getX(), this.getY());
        if (!define.isOk()) {
            arrow.algorithm.removePattern(define);
        }
    }//GEN-LAST:event_btDefineActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        arrow.setSelected(false);
        setVisible(false);
    }//GEN-LAST:event_btCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btDefine;
    private javax.swing.JButton jButton3;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
