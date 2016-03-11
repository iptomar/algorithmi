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
public class DisplayProgramInfo extends javax.swing.JDialog {

    private static DisplayProgramInfo instance = new DisplayProgramInfo();
    public static boolean isCanceled;
    public static String fileName;
    public static String problemDescription;

    /**
     * Creates new form TerminatorMenu
     */
    public DisplayProgramInfo() {
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
                    setVisible(false);
                }
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    btOkActionPerformed(null);
                    evt.consume();
                }
            }
        };
        this.addKeyListener(menuKeyListener);
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

  
    public void I18N() {
        setIconImage(EditorI18N.loadIcon("APPLICATION.icon", 24).getImage());
        Fi18N.loadButton(btOk, "BUTTON.accept", 24);
        pnFileName.setBorder(BorderFactory.createTitledBorder(EditorI18N.get("NEW_PROGRAM.folder.message")));
        pnProgram.setBorder(BorderFactory.createTitledBorder(EditorI18N.get("NEW_PROGRAM.program.message")));

    }

    public static void show(String msg) {
        UserName user = FProperties.getUser();
        instance.txtProblemDescription.setFont(Fshape.myFONT);

        instance.lblUserAvatar.setIcon(user.getAvatar());
        instance.isCanceled = true;

        instance.txtProblemDescription.setText("");
        instance.txtPath.setText(msg);
        String name = FileUtils.getFileWithoutExtension(msg);

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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblUserAvatar = new javax.swing.JLabel();
        pnFileName = new javax.swing.JPanel();
        txtPath = new javax.swing.JLabel();
        pnProgram = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtProblemDescription = new javax.swing.JTextArea();

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

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel2");

        jLabel4.setText("jLabel2");

        jLabel5.setText("jLabel5");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel5)
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(28, 28, 28))
        );

        jPanel6.add(jPanel2);

        jPanel1.setLayout(new java.awt.BorderLayout());

        lblUserAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblUserAvatar, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel1);

        pnFileName.setBorder(javax.swing.BorderFactory.createTitledBorder("FileName"));

        txtPath.setText("jLabel2");

        javax.swing.GroupLayout pnFileNameLayout = new javax.swing.GroupLayout(pnFileName);
        pnFileName.setLayout(pnFileNameLayout);
        pnFileNameLayout.setHorizontalGroup(
            pnFileNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
        pnFileNameLayout.setVerticalGroup(
            pnFileNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPath, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pnProgram.setBorder(javax.swing.BorderFactory.createTitledBorder("Problem"));
        pnProgram.setLayout(new java.awt.BorderLayout());

        txtProblemDescription.setColumns(20);
        txtProblemDescription.setLineWrap(true);
        txtProblemDescription.setRows(5);
        txtProblemDescription.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtProblemDescription);

        pnProgram.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnProgram, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnProgram, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(41, 41, 41))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
        isCanceled = false;
        fileName = txtPath.getText().trim();
        problemDescription = txtProblemDescription.getText();
        setVisible(false);
    }//GEN-LAST:event_btOkActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblUserAvatar;
    private javax.swing.JPanel pnFileName;
    private javax.swing.JPanel pnProgram;
    private javax.swing.JLabel txtPath;
    private javax.swing.JTextArea txtProblemDescription;
    // End of variables declaration//GEN-END:variables

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
