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

import flowchart.utils.FileUtils;
import i18n.Fi18N;
import flowchart.algorithm.Program;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.text.BadLocationException;
import javax.swing.text.rtf.RTFEditorKit;
import ui.flowchart.expression.TextExpression;

/**
 * Singleton class to display Exction messages
 *
 * @author ZULU
 */
public class CodeText extends javax.swing.JFrame {

    private static CodeText instance = new CodeText();
    private static Program myProg;

    /**
     * Creates new form TerminatorMenu
     */
    public CodeText() {
        super(Fi18N.get("DIALOG.code.title"));
        this.setResizable(true);
        initComponents();
        I18N();
        //------------------------------------------------------------
        //---  Set <ENTER> and <ESC> actions to controls       -------
        //------------------------------------------------------------
        KeyListener menuKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {  // handler
                if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    btOkActionPerformed(null);
                }
            }
        };
        this.addKeyListener(menuKeyListener);
        btOk.addKeyListener(menuKeyListener);
        btCopy.addKeyListener(menuKeyListener);
        btSave.addKeyListener(menuKeyListener);
        txtCode.addKeyListener(menuKeyListener);
        cbLanguage.addKeyListener(menuKeyListener);
    }

    public void I18N() {
        setIconImage(Fi18N.loadKeyIcon("DIALOG.code.icon", 24).getImage());
        Fi18N.loadButton(btOk, "BUTTON.accept", 24);
        Fi18N.loadButton(btSave, "DIALOG.code.save", 24);
        Fi18N.loadButton(btCopy, "DIALOG.code.clipboard", 24);
        ComboBoxModel<String> model = new DefaultComboBoxModel<>(
                new String[]{
                    Fi18N.get("TRANSLATOR.naturalLanguage.title"),
                    Fi18N.get("TRANSLATOR.tokens.title"),
                    Fi18N.get("TRANSLATOR.langC.title"),
                    Fi18N.get("TRANSLATOR.java.title"),
                    Fi18N.get("TRANSLATOR.python.title")
                }
        );
        cbLanguage.setModel(model);

    }

    public static void show(Program myProgram) {
        myProg = myProgram;
        instance.txtCode.updateMenu(null, myProg);
        instance.setLocationRelativeTo(null);
        instance.txtCode.setText(myProgram.getPseudoCode());
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

        jToggleButton1 = new javax.swing.JToggleButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btOk = new javax.swing.JButton();
        btSave = new javax.swing.JButton();
        cbLanguage = new javax.swing.JComboBox();
        btCopy = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCode = new ui.flowchart.expression.TextExpression();

        jToggleButton1.setText("jToggleButton1");

        jButton3.setText("jButton3");

        jLabel3.setText("jLabel3");

        jLabel5.setText("jLabel5");

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        btOk.setText("ok");
        btOk.setFocusable(false);
        btOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOkActionPerformed(evt);
            }
        });

        btSave.setText("Save");
        btSave.setFocusable(false);
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });

        cbLanguage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Portugol", "Tokens", "C", "Java", "C#", "Javascript", " " }));
        cbLanguage.setLightWeightPopupEnabled(false);
        cbLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLanguageActionPerformed(evt);
            }
        });

        btCopy.setText("jButton1");
        btCopy.setFocusable(false);
        btCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCopyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btSave, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btOk, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btOk, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btSave)
                .addComponent(cbLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btCopy))
        );

        txtCode.setEditable(false);
        txtCode.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(txtCode);

        jScrollPane2.setViewportView(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
        instance.setVisible(false);
    }//GEN-LAST:event_btOkActionPerformed

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        FileUtils.saveCode(txtCode.getText(), cbLanguage.getSelectedItem().toString(), FileUtils.getPath(myProg.getFileName()));
    }//GEN-LAST:event_btSaveActionPerformed

    private void cbLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLanguageActionPerformed
        switch (cbLanguage.getSelectedIndex()) {
            case 0:
                txtCode.setText(myProg.getPseudoCode());
                break;
            case 1:
                txtCode.setText(myProg.getTokens());
                break;
            case 3:
                txtCode.setText(myProg.getHigLevelLang("JAVA"));
                break;
            case 4:
                txtCode.setText(myProg.getHigLevelLang("PYTHON"));
                break;
        }
        txtCode.setCaretPosition(0);
        instance.revalidate();
        instance.repaint();
    }//GEN-LAST:event_cbLanguageActionPerformed

    private void btCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCopyActionPerformed
//        try {
//            //        StringSelection stringSelection = new StringSelection(txtCode.getText());
////        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
////        clpbrd.setContents(stringSelection, null);
//
//            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
//            RTFEditorKit rtfek = new RTFEditorKit();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            rtfek.write(baos, txtCode.getStyledDocument(), 0, txtCode.getStyledDocument().getLength());
//            baos.flush();
//            DataHandler dh = new DataHandler(baos.toByteArray(), rtfek.getContentType());
//            clpbrd.setContents(dh, null);
//        } catch (Exception ex) {
//            Logger.getLogger(CodeText.class.getName()).log(Level.SEVERE, null, ex);
//        }
        copyTextToClipboard(txtCode.getText());
    }//GEN-LAST:event_btCopyActionPerformed

    public static void copyTextToClipboard(String txt) {
        try {
            TextExpression formatedText = new TextExpression();
            formatedText.setFont(new java.awt.Font("Courier New", 0, 10)); // NOI18N
            formatedText.setText(txt);
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            RTFEditorKit rtfek = new RTFEditorKit();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            rtfek.write(baos, formatedText.getStyledDocument(), 0, formatedText.getStyledDocument().getLength());
            baos.flush();
            DataHandler dh = new DataHandler(baos.toByteArray(), rtfek.getContentType());
            clpbrd.setContents(dh, null);
        } catch (Exception ex) {

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCopy;
    private javax.swing.JButton btOk;
    private javax.swing.JButton btSave;
    private javax.swing.JComboBox cbLanguage;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private ui.flowchart.expression.TextExpression txtCode;
    // End of variables declaration//GEN-END:variables

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
