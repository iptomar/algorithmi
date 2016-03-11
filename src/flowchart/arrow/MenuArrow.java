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

import ui.flowchart.dialogs.ShapeMenuDialog;
import i18n.Fi18N;
import i18n.FkeyWord;
import flowchart.decide.Do_While;
import flowchart.define.Define;
import flowchart.execute.Execute;
import flowchart.read.Read;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import flowchart.decide.forNext.For_Next;
import flowchart.decide.IfElse.IfThenElse;
import flowchart.decide.While_Do;
import flowchart.jump.Jump;
import flowchart.returnFunc.Return;
import flowchart.utils.Theme;
import flowchart.write.Write;
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
public class MenuArrow extends ShapeMenuDialog implements MenuPattern {

    Arrow arrow;

    /**
     * Creates new form TerminatorMenu
     *
     */
    public MenuArrow() {
        super("MENU.arrow.title");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Fi18N.loadDialogTitle(this, "MENU.arrow.title", "");
        initComponents();

        I18N();
        this.setAlwaysOnTop(false);
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
        btExecute.addKeyListener(escapeListener);
        btWrite.addKeyListener(escapeListener);
        btRead.addKeyListener(escapeListener);
        btIfElse.addKeyListener(escapeListener);
        btWhile.addKeyListener(escapeListener);
        btFor.addKeyListener(escapeListener);
        btDoWhile.addKeyListener(escapeListener);
        btCancel.addKeyListener(escapeListener);

    }

    public void I18N() {
        int size = 32;
        Fi18N.loadButton(btDefine, "DEFINE.instruction", size);
        Fi18N.loadButton(btExecute, "EXECUTE.instruction", size);
        Fi18N.loadButton(btWrite, "WRITE.instruction", size);
        Fi18N.loadButton(btRead, "READ.instruction", size);
        
        Fi18N.loadButton(btIfElse, "IF.instruction", size);
        Fi18N.loadButton(btWhile, "WHILE.instruction", size);
        Fi18N.loadButton(btDoWhile, "DO.instruction", size);
        
      
        Fi18N.loadButton(btReturn, "RETURN.instruction", size);
        
        Fi18N.loadButton(btFor, "FOR.instruction", size);
        Fi18N.loadButton(btJump, "JUMP.instruction", size);

        Fi18N.loadButton(btCancel, "BUTTON.cancel", size);
    }

    @Override
    public void showDialog(Fshape shape, int x, int y) {
        setLocationRelativeTo(null);
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

        btCancel = new javax.swing.JButton();
        pnSimple = new javax.swing.JPanel();
        btDefine = new javax.swing.JButton();
        btRead = new javax.swing.JButton();
        btExecute = new javax.swing.JButton();
        btWrite = new javax.swing.JButton();
        pnComplex = new javax.swing.JPanel();
        btIfElse = new javax.swing.JButton();
        btWhile = new javax.swing.JButton();
        btDoWhile = new javax.swing.JButton();
        btFor = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btReturn = new javax.swing.JButton();
        btJump = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(5, 5, 5, 5));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });

        btCancel.setText("Cancel");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        pnSimple.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnSimple.setLayout(new java.awt.GridLayout(4, 0, 0, 2));

        btDefine.setText("Definir");
        btDefine.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btDefine.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btDefine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDefineActionPerformed(evt);
            }
        });
        pnSimple.add(btDefine);

        btRead.setText("Read");
        btRead.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btRead.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReadActionPerformed(evt);
            }
        });
        pnSimple.add(btRead);

        btExecute.setText("calculate");
        btExecute.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btExecute.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExecuteActionPerformed(evt);
            }
        });
        pnSimple.add(btExecute);

        btWrite.setText("Write");
        btWrite.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btWrite.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btWrite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btWriteActionPerformed(evt);
            }
        });
        pnSimple.add(btWrite);

        pnComplex.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnComplex.setEnabled(false);
        pnComplex.setLayout(new java.awt.GridLayout(4, 0, 0, 2));

        btIfElse.setText("se");
        btIfElse.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btIfElse.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btIfElse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIfElseActionPerformed(evt);
            }
        });
        pnComplex.add(btIfElse);

        btWhile.setText("while");
        btWhile.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btWhile.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btWhile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btWhileActionPerformed(evt);
            }
        });
        pnComplex.add(btWhile);

        btDoWhile.setText("Do while");
        btDoWhile.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btDoWhile.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btDoWhile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDoWhileActionPerformed(evt);
            }
        });
        pnComplex.add(btDoWhile);

        btFor.setText("For");
        btFor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btFor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btFor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btForActionPerformed(evt);
            }
        });
        pnComplex.add(btFor);

        jPanel1.setEnabled(false);
        jPanel1.setLayout(new java.awt.GridLayout(4, 0, 0, 2));

        btReturn.setText("Return");
        btReturn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btReturn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReturnActionPerformed(evt);
            }
        });
        jPanel1.add(btReturn);

        btJump.setText("break/continue");
        btJump.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btJump.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btJump.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btJumpActionPerformed(evt);
            }
        });
        jPanel1.add(btJump);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnSimple, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnComplex, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnSimple, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                    .addComponent(pnComplex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExecuteActionPerformed
        Execute exe = new Execute(arrow.algorithm);
        arrow.algorithm.addSimpleShape(arrow, exe);
        setVisible(false);
        exe.editMenu(this.getX(), this.getY());
        if (!exe.isOk()) {
            arrow.algorithm.removePattern(exe);
        }
    }//GEN-LAST:event_btExecuteActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        arrow.setSelected(false);
        setVisible(false);
    }//GEN-LAST:event_btCancelActionPerformed

    private void btWriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btWriteActionPerformed
        Write shape = new Write(arrow.algorithm);
        arrow.algorithm.addSimpleShape(arrow, shape);
        setVisible(false);
        shape.editMenu(this.getX(), this.getY());
        if (!shape.isOk()) {
            arrow.algorithm.removePattern(shape);
        }
    }//GEN-LAST:event_btWriteActionPerformed

    private void btReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReadActionPerformed
        Read shape = new Read(arrow.algorithm);
        arrow.algorithm.addSimpleShape(arrow, shape);
        setVisible(false);
        shape.editMenu(this.getX(), this.getY());
        if (!shape.isOk()) {
            arrow.algorithm.removePattern(shape);
        }
    }//GEN-LAST:event_btReadActionPerformed

    private void btIfElseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIfElseActionPerformed
        IfThenElse shape = new IfThenElse(arrow.algorithm);
        arrow.algorithm.addShapeIfElse(arrow, shape);
        setVisible(false);
        shape.editMenu(this.getX(), this.getY());
        if (!shape.isOk()) {
            arrow.algorithm.removePattern(shape);
        }
    }//GEN-LAST:event_btIfElseActionPerformed

    private void btWhileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btWhileActionPerformed
        While_Do shape = new While_Do(arrow.algorithm);
        arrow.algorithm.addShapeWhileDo(arrow, shape);
        setVisible(false);
        shape.editMenu(this.getX(), this.getY());
        if (!shape.isOk()) {
            arrow.algorithm.removePattern(shape);
        }
    }//GEN-LAST:event_btWhileActionPerformed

    private void btForActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btForActionPerformed
        For_Next shape = new For_Next(arrow.algorithm);
        arrow.algorithm.addShapeWhileDo(arrow, shape);
        setVisible(false);
        shape.editMenu(this.getX(), this.getY());
        if (!shape.isOk()) {
            arrow.algorithm.removePattern(shape);
        }
    }//GEN-LAST:event_btForActionPerformed

    private void btDoWhileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDoWhileActionPerformed
        Fshape shape = new Do_While(arrow.algorithm);
        arrow.algorithm.addShapeDoWhile(arrow, shape);
        setVisible(false);
        shape.editMenu(this.getX(), this.getY());
        if (!shape.isOk()) {
            arrow.algorithm.removePattern(shape);
        }
    }//GEN-LAST:event_btDoWhileActionPerformed

    private void btReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReturnActionPerformed
        Fshape shape = new Return(arrow.algorithm);
        setVisible(false);
        arrow.setSelected(true);
        arrow.algorithm.addSimpleShape(arrow, shape);
        shape.editMenu(this.getX(), this.getY());
        if (!shape.isOk()) {
            arrow.algorithm.removePattern(shape);
        }
    }//GEN-LAST:event_btReturnActionPerformed

    private void btJumpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btJumpActionPerformed
        Fshape shape = new Jump(arrow.algorithm);
        arrow.algorithm.addSimpleShape(arrow, shape);
        setVisible(false);
        shape.editMenu(this.getX(), this.getY());
        if (!shape.isOk()) {
            arrow.algorithm.removePattern(shape);
        }
    }//GEN-LAST:event_btJumpActionPerformed

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
        setVisible(false);
        System.out.println("Lost Focus");
    }//GEN-LAST:event_formFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btDefine;
    private javax.swing.JButton btDoWhile;
    private javax.swing.JButton btExecute;
    private javax.swing.JButton btFor;
    private javax.swing.JButton btIfElse;
    private javax.swing.JButton btJump;
    private javax.swing.JButton btRead;
    private javax.swing.JButton btReturn;
    private javax.swing.JButton btWhile;
    private javax.swing.JButton btWrite;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnComplex;
    private javax.swing.JPanel pnSimple;
    // End of variables declaration//GEN-END:variables

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
