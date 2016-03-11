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
package ui.flowchart.dialogs;

import flowchart.shape.Fshape;
import i18n.Fi18N;
import i18n.EditorI18N;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import flowchart.utils.FileUtils;
import flowchart.utils.UserName;
import javax.swing.BorderFactory;
import ui.FProperties;

/**
 * Singleton class to display Exction messages
 *
 * @author ZULU
 */
public class NewProgram extends javax.swing.JDialog {

    private static NewProgram instance = new NewProgram();
    public static boolean isCanceled;
    public static String fileName;
    public static String problemDescription;

    /**
     * Creates new form TerminatorMenu
     */
    public NewProgram() {
        super((JFrame) null, EditorI18N.get("NEW_PROGRAM.title.title"), true);
        initComponents();
        I18N();
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
        this.addKeyListener(menuKeyListener);
        txtPath.addKeyListener(menuKeyListener);
        txtFileName.addKeyListener(menuKeyListener);
    }

    public void updatePath() {
        txtPath.setText(FileUtils.updateFileName(txtPath.getText(), txtFileName.getText()));
    }

    private static String getNewFileName(String fileName) {
        int index = fileName.length();
        while (index > 0 && Character.isDigit(fileName.charAt(index - 1))) {
            index--;
        }
        String name = fileName.substring(0, index);
        String number = fileName.substring(index);
        int num = 0;
        try {
            num = Integer.parseInt(number);
        } catch (Exception e) {
        }
        return name + String.format("%02d", num + 1);

    }

    public void updateFilename() {
        txtFileName.setText(FileUtils.getFileWithoutExtension(txtPath.getText()));
    }

    public void I18N() {
        setIconImage(EditorI18N.loadIcon("APPLICATION.icon", 24).getImage());
        Fi18N.loadButton(btOk, "BUTTON.accept", 24);
        Fi18N.loadButton(btCancel, "BUTTON.cancel", 24);
        EditorI18N.loadResource(btSelectPath, "NEW_PROGRAM.folder", 32);
        pnFileName.setBorder(BorderFactory.createTitledBorder(EditorI18N.get("NEW_PROGRAM.folder.message")));
        //pnProgram.setBorder(BorderFactory.createTitledBorder(EditorI18N.get("NEW_PROGRAM.program.message")));
//        EditorI18N.loadLabel(lblTitle, "NEW_PROGRAM.title", 64);
//        lblTitle.setText("");

    }

    public static void show(String msg) {
        UserName user = FProperties.getUser();
        instance.txtFileName.setFont(Fshape.myFONT);
       // instance.txtProblemDescription.setFont(Fshape.myFONT);

        instance.lblUserAvatar.setIcon(user.getAvatar());
        instance.lblUserName.setText(user.getFullName());
        instance.isCanceled = true;

        //instance.txtProblemDescription.setText("");
        instance.txtPath.setText(msg);
        String name = FileUtils.getFileWithoutExtension(msg);
        instance.txtFileName.setText(getNewFileName(name));
        instance.txtFileName.requestFocus();

        instance.setLocationRelativeTo(null);
        instance.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btOk = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblUserAvatar = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        pnFileName = new javax.swing.JPanel();
        btSelectPath = new javax.swing.JButton();
        txtPath = new javax.swing.JLabel();
        txtFileName = new javax.swing.JTextField();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

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

        jPanel1.setLayout(new java.awt.BorderLayout());

        lblUserAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUserAvatar, java.awt.BorderLayout.CENTER);

        lblUserName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserName.setText("user");
        jPanel1.add(lblUserName, java.awt.BorderLayout.PAGE_END);

        jPanel6.add(jPanel1);

        pnFileName.setBorder(javax.swing.BorderFactory.createTitledBorder("FileName"));

        btSelectPath.setText("...");
        btSelectPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSelectPathActionPerformed(evt);
            }
        });

        txtPath.setText("jLabel2");

        txtFileName.setText("jTextField2");
        txtFileName.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFileNameCaretUpdate(evt);
            }
        });
        txtFileName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFileNameKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pnFileNameLayout = new javax.swing.GroupLayout(pnFileName);
        pnFileName.setLayout(pnFileNameLayout);
        pnFileNameLayout.setHorizontalGroup(
            pnFileNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnFileNameLayout.createSequentialGroup()
                .addComponent(btSelectPath, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnFileNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFileName, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)))
        );
        pnFileNameLayout.setVerticalGroup(
            pnFileNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnFileNameLayout.createSequentialGroup()
                .addComponent(txtPath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtFileName, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(btSelectPath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
        isCanceled = false;
        fileName = txtPath.getText().trim();
//        problemDescription = txtProblemDescription.getText();
        setVisible(false);
    }//GEN-LAST:event_btOkActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        isCanceled = true;
        setVisible(false);
    }//GEN-LAST:event_btCancelActionPerformed

    private void txtFileNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFileNameKeyTyped

    }//GEN-LAST:event_txtFileNameKeyTyped

    private void txtFileNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFileNameCaretUpdate
        updatePath();
    }//GEN-LAST:event_txtFileNameCaretUpdate

    private void btSelectPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSelectPathActionPerformed
        JFileChooser fileFluxogram = new JFileChooser(txtPath.getText());
        int returnValue = fileFluxogram.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            txtPath.setText(fileFluxogram.getSelectedFile().getAbsolutePath());
            updateFilename();
        }
    }//GEN-LAST:event_btSelectPathActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btOk;
    private javax.swing.JButton btSelectPath;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblUserAvatar;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JPanel pnFileName;
    private javax.swing.JTextField txtFileName;
    private javax.swing.JLabel txtPath;
    // End of variables declaration//GEN-END:variables

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
