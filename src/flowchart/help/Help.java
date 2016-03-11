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
package flowchart.help;

import ui.FLog;
import ui.FProperties;
import i18n.Fi18N;
import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author ZULU
 */
public class Help extends javax.swing.JDialog {

    public static String HTML_PATH = "/flowchart/help/";

    private static Help singleton = new Help();
    private static ArrayList<String> pages = new ArrayList<>();
    private static int PAGENUMBER = -1;

    /**
     * Creates new form TerminatorMenu
     */
    public Help() {
        super((JFrame) null, Fi18N.get("BUTTON.help"), true);
        setIconImage(Fi18N.loadKeyIcon("BUTTON.help.icon", 16).getImage());
//        setUndecorated(true);
        initComponents();
        I18N();
        //------------------------------------------------------------
        //---  Set <ENTER> and <ESC> actions to controls       -------
        //------------------------------------------------------------
        KeyListener menuKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {  // handler
                if (evt.getKeyCode() == evt.VK_ESCAPE || evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    setVisible(false);
                }
            }
        };
        this.addKeyListener(menuKeyListener);
        txtHelp.addKeyListener(menuKeyListener);
        txtHelp.setContentType("text/html");//set content as html
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: listen hiperlinks
        txtHelp.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent hle) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                    String myLink = hle.getURL().toString();
                    int startLink = myLink.indexOf(HTML_PATH);
                    myLink = myLink.substring(startLink, myLink.length());
                    displayPage(myLink);
                }
            }
        });
    }

    public void I18N() {
        Fi18N.loadLabel(lblStatus, "FLOWCHART.application", 16);
        Fi18N.loadButton(btHome, "HELP.home");
        Fi18N.loadButton(btPrevious, "HELP.previous");
        Fi18N.loadButton(btNext, "HELP.next");
    }

    public static void displayURLhelp(String link) {
        PAGENUMBER = pages.size() - 1;
        displayPage(link);
        singleton.setLocationRelativeTo(null);
        singleton.setVisible(true);
    }

    public static void displayPage(String link) {
        if (PAGENUMBER > 0 && PAGENUMBER < pages.size() - 1) {
            pages = new ArrayList<String>(pages.subList(0, PAGENUMBER));
        }
        pages.add(link);
        PAGENUMBER = pages.size() - 1;

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (String page : pages) {
            model.addElement(page);
        }
        singleton.cbAdress.setModel(model);
        singleton.cbAdress.setSelectedIndex(PAGENUMBER);
        displayLinkInJar(PAGENUMBER);
    }

    public static void displayLinkInJar(int index) {
        if (index < 0 || index >= pages.size()) {
            return;
        }
        //Log.printLn("LINK " + link);
        try {
            URL url = Help.class.getResource(pages.get(index));
            singleton.txtHelp.setPage(url);
            singleton.cbAdress.setSelectedIndex(index);

        } catch (IOException ex) {
            FLog.printLn("displayLinkInJar INDEX " + pages.get(index) + " \t " + ex.getMessage());
            singleton.txtHelp.setText(ex.getMessage());
        }

    }

    public static void show(String key) {
        displayURLhelp(HTML_PATH
                + FProperties.getLanguageSufix() + "/"
                + Fi18N.get(key));
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
        jPanel2 = new javax.swing.JPanel();
        btPrevious = new javax.swing.JButton();
        btNext = new javax.swing.JButton();
        btHome = new javax.swing.JButton();
        cbAdress = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtHelp = new javax.swing.JEditorPane();
        lblStatus = new javax.swing.JLabel();

        jToggleButton1.setText("jToggleButton1");

        jButton3.setText("jButton3");

        jLabel3.setText("jLabel3");

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFocusable(false);
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridLayout());

        btPrevious.setText("jButton1");
        btPrevious.setFocusable(false);
        btPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPreviousActionPerformed(evt);
            }
        });
        jPanel2.add(btPrevious);

        btNext.setText("jButton2");
        btNext.setFocusable(false);
        btNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNextActionPerformed(evt);
            }
        });
        jPanel2.add(btNext);

        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);

        btHome.setText("jButton4");
        btHome.setFocusable(false);
        btHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHomeActionPerformed(evt);
            }
        });
        jPanel1.add(btHome, java.awt.BorderLayout.EAST);

        cbAdress.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbAdress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAdressActionPerformed(evt);
            }
        });
        jPanel1.add(cbAdress, java.awt.BorderLayout.CENTER);

        jScrollPane2.setFocusable(false);

        txtHelp.setEditable(false);
        txtHelp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHelpFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(txtHelp);

        lblStatus.setForeground(new java.awt.Color(153, 153, 153));
        lblStatus.setText("jLabel1");
        lblStatus.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblStatus))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus

    }//GEN-LAST:event_formWindowLostFocus

    private void txtHelpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHelpFocusLost
        //setVisible(false);
    }//GEN-LAST:event_txtHelpFocusLost

    private void btPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPreviousActionPerformed
        PAGENUMBER = PAGENUMBER > 0 ? PAGENUMBER - 1 : PAGENUMBER;
        displayLinkInJar(PAGENUMBER);
    }//GEN-LAST:event_btPreviousActionPerformed

    private void btNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNextActionPerformed
        PAGENUMBER = PAGENUMBER < pages.size() - 1 ? PAGENUMBER + 1 : PAGENUMBER;
        displayLinkInJar(PAGENUMBER);
    }//GEN-LAST:event_btNextActionPerformed

    private void cbAdressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAdressActionPerformed
        PAGENUMBER = cbAdress.getSelectedIndex();
        displayLinkInJar(PAGENUMBER);
    }//GEN-LAST:event_cbAdressActionPerformed

    private void btHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHomeActionPerformed
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(Fi18N.get("HELP.home.www")));
            } catch (Exception ex) {
                 FLog.runError(" btHomeActionPerformed( " +ex.getMessage());
            }
        }
    }//GEN-LAST:event_btHomeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btHome;
    private javax.swing.JButton btNext;
    private javax.swing.JButton btPrevious;
    private javax.swing.JComboBox cbAdress;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JEditorPane txtHelp;
    // End of variables declaration//GEN-END:variables

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
