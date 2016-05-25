/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.editor.left;

import flowchart.algorithm.Program;
import flowchart.algorithm.run.AutoRunProgram;
import i18n.EditorI18N;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author zulu
 */
public class ProblemDisplay extends javax.swing.JPanel {

    public Program myProgram;

    /**
     * Creates new form ProblemEvaluate
     */
    public ProblemDisplay() {
        initComponents();
        I18N();
    }

    public final void I18N() {
        try {
            EditorI18N.loadResource(btVerify, "PROBLEM.evaluate", 32);
            EditorI18N.loadResource(btSubmit, "PROBLEM.submit", 32);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "PROBLEMA Erro na leitura da internacionalização");
        }
    }

    public void setProgram(Program prog) {
        this.myProgram = prog;
        lblUser.setIcon(myProgram.myProblem.user.getAvatar(64));
        lblUser.setToolTipText(myProgram.myProblem.user.getFullName());
        ((TitledBorder) pnUser.getBorder()).setTitle(myProgram.myProblem.user.getName());
        displayProblem();
    }

    private void displayProblem() {
        txtProblem.setText("");
        txtResult.setText("");
        if (!myProgram.myProblem.isEmpty()) {
            addTitle(txtProblem, myProgram.myProblem.title, 30);
            addImage(txtProblem, myProgram.myProblem.getImage());
            addTitle(txtProblem, "", 30);
            addDescription(txtProblem, myProgram.myProblem.description, 14);
            for(int i=0; i < myProgram.myProblem.input.size();i++){
                addIOtittle(txtProblem, EditorI18N.get("PROBLEM.input") + " " + (i+1), 20);
                addIOdata(txtProblem, myProgram.myProblem.input.get(i), 14);
                addIOtittle(txtProblem, EditorI18N.get("PROBLEM.output") + " " + (i+1), 20);
                addIOdata(txtProblem, myProgram.myProblem.output.get(i), 14);
            }
        }
        txtProblem.setCaretPosition(0);
    }

    public static void addTitle(final JTextPane txt, final String msg, int fontSize) {
        try {
            StyleContext sc = new StyleContext();
            Style style = sc.getStyle(StyleContext.DEFAULT_STYLE);

            Document doc = txt.getDocument();
            StyleConstants.setForeground(style, Color.DARK_GRAY);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
            StyleConstants.setFontSize(style, fontSize);
            StyleConstants.setSpaceAbove(style, 4);
            StyleConstants.setSpaceBelow(style, 4);
            doc.insertString(doc.getLength(), msg + "\n", style);
        } catch (BadLocationException ex) {
        }
    }

    public static void addDescription(final JTextPane txt, final String msg, int fontSize) {
        try {
            StyleContext sc = new StyleContext();
            Style style = sc.getStyle(StyleContext.DEFAULT_STYLE);
            Document doc = txt.getDocument();
            StyleConstants.setForeground(style, Color.BLACK);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
            StyleConstants.setFontSize(style, fontSize);
            StyleConstants.setSpaceAbove(style, 4);
            StyleConstants.setSpaceBelow(style, 4);
            doc.insertString(doc.getLength(), msg + " \n", style);
        } catch (BadLocationException ex) {
        }
    }
    
    public static void addIOtittle(final JTextPane txt, final String msg, int fontSize) {
        try {
            StyleContext sc = new StyleContext();
            Style style = sc.getStyle(StyleContext.DEFAULT_STYLE);
            Document doc = txt.getDocument();
            StyleConstants.setForeground(style, Color.DARK_GRAY);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
            StyleConstants.setFontSize(style, fontSize);
            StyleConstants.setSpaceAbove(style, 4);
            StyleConstants.setSpaceBelow(style, 4);
            doc.insertString(doc.getLength(), msg + " \n", style);
        } catch (BadLocationException ex) {
        }
    }
    public static void addIOdata(final JTextPane txt, final String msg, int fontSize) {
        try {
            StyleContext sc = new StyleContext();
            Style style = sc.getStyle(StyleContext.DEFAULT_STYLE);
            Document doc = txt.getDocument();
            StyleConstants.setForeground(style, Color.BLACK);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
            StyleConstants.setFontSize(style, fontSize);
            StyleConstants.setSpaceAbove(style, 4);
            StyleConstants.setSpaceBelow(style, 4);
            StyleConstants.setFontFamily(style, "Courier New");
            doc.insertString(doc.getLength(), msg + " \n", style);
        } catch (BadLocationException ex) {
        }
    }
    public static void addTestResult(final JTextPane txt,int fontSize,int example, final String msg, int result) {
        try {
            String input = EditorI18N.get("PROBLEM.input");
            StyleContext sc = new StyleContext();
            Style style = sc.getStyle(StyleContext.DEFAULT_STYLE);
            Document doc = txt.getDocument();
            StyleConstants.setForeground(style, Color.BLACK);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
            StyleConstants.setFontSize(style, fontSize);
            StyleConstants.setSpaceAbove(style, 4);
            StyleConstants.setSpaceBelow(style, 4);
            StyleConstants.setFontFamily(style, "Courier New");
            StyleConstants.setForeground(style, Color.DARK_GRAY);
            doc.insertString(doc.getLength(), input +" "+example + ": ", style);
            StyleConstants.setForeground(style, Color.BLUE);
            doc.insertString(doc.getLength(), String.format("%3d ", result), style);
            StyleConstants.setForeground(style, Color.BLACK);
            doc.insertString(doc.getLength(), msg + " \n", style);
            
        } catch (BadLocationException ex) {
        }
    }
    

    public static void addImage(final JTextPane txt, final ImageIcon img) {
        try {
            StyledDocument doc = txt.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);
            StyleContext sc = new StyleContext();
            Style style = sc.getStyle(StyleContext.DEFAULT_STYLE);
            StyleConstants.setIcon(style, img);
            doc.insertString(doc.getLength(), " ", style);
        } catch (BadLocationException ex) {
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

        pnHeader = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btVerify = new javax.swing.JButton();
        btSubmit = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtResult = new javax.swing.JTextPane();
        pnUser = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtProblem = new javax.swing.JTextPane();

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        btVerify.setText("Verify");
        btVerify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVerifyActionPerformed(evt);
            }
        });
        jPanel1.add(btVerify);

        btSubmit.setText("submit");
        jPanel1.add(btSubmit);

        jPanel3.add(jPanel1, java.awt.BorderLayout.NORTH);

        jScrollPane2.setViewportView(txtResult);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pnUser.setBorder(javax.swing.BorderFactory.createTitledBorder("userinfo"));
        pnUser.setLayout(new java.awt.BorderLayout());
        pnUser.add(lblUser, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout pnHeaderLayout = new javax.swing.GroupLayout(pnHeader);
        pnHeader.setLayout(pnHeaderLayout);
        pnHeaderLayout.setHorizontalGroup(
            pnHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHeaderLayout.createSequentialGroup()
                .addComponent(pnUser, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
        );
        pnHeaderLayout.setVerticalGroup(
            pnHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnHeaderLayout.createSequentialGroup()
                .addComponent(pnUser, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        txtProblem.setEditable(false);
        jScrollPane1.setViewportView(txtProblem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btVerifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVerifyActionPerformed
        txtResult.setText("");
        
        AutoRunProgram exe = new AutoRunProgram(myProgram);
        ArrayList<String> result = exe.testProgram(myProgram);
        for (int i = 0; i < result.size(); i++) {
            String student = result.get(i);
            String teacher = myProgram.myProblem.output.get(i);
            if( student.equals(teacher)){
                addTestResult(txtResult, 14, i+1, "OK", 100);
            }else{
                addTestResult(txtResult, 14, i+1, "ERROR", 0);
            }
            
        }
        
        
    }//GEN-LAST:event_btVerifyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btSubmit;
    private javax.swing.JButton btVerify;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel pnHeader;
    private javax.swing.JPanel pnUser;
    private javax.swing.JTextPane txtProblem;
    private javax.swing.JTextPane txtResult;
    // End of variables declaration//GEN-END:variables

}
