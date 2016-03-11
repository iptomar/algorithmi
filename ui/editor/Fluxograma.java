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
package ui.editor;

import i18n.EditorI18N;
import ui.dialogs.FMessages;
import ui.editor.run.RunProgram;
import ui.utils.ButtonsFluxTab;
import flowchart.utils.FileUtils;
import core.data.exception.FlowchartException;
import ui.flowchart.FeditorScrool;
import ui.flowchart.dialogs.FlowChartProperties;
import ui.flowchart.dialogs.CodeText;
import i18n.Fi18N;
import flowchart.algorithm.FunctionGraph;
import flowchart.algorithm.GlobalMemoryGraph;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.Program;
import flowchart.function.Function;
import flowchart.function.MenuFunction;
import flowchart.shape.Fshape;
import flowchart.utils.FluxImage;
import flowchart.utils.UserName;
import flowchart.utils.image.ImageUtils;
import java.awt.event.KeyEvent;
import ui.FProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import ui.FLog;
import ui.flowchart.dialogs.Fdialog;
import ui.flowchart.dialogs.NewProgram;

/**
 *
 * @author
 */
public class Fluxograma extends javax.swing.JFrame {

    boolean showAboutBox = false;
    Program myProgram;
    private UserName user;
//    ListProgramsPopupMenu popupListPrograms = new ListProgramsPopupMenu();

    /**
     * Creates new form Fluxograma
     */
    public Fluxograma() {

        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        I18N();
        initMyComponents();
        if (showAboutBox) {
            showAbout();
        }
    }

    public void initMyComponents() {

        updateUser();
        try {
            File file = new File(FProperties.get(FProperties.keyLastProgramOpened));
            if (file.exists()) {
                myProgram = Program.loadProgram(file.getAbsolutePath());
                createDisplaytoProgram();
                FMessages.status(lbblStatus, FMessages.INFO, "PROGRAM.newProgram.opened", myProgram.getFileName());
            }
        } catch (Exception e) {
//            newProgram();
            showAbout();
        }
        updateFileList("");
    }

    public void updateUser() {
//        lblUserAvatar.setIcon(FProperties.getUserAvatar());
//        lblUserAvatar.setText(FProperties.getCrypt(FProperties.keyUserName));
//        lblUserAvatar.setHorizontalTextPosition(JLabel.CENTER);
//        lblUserAvatar.setVerticalTextPosition(JLabel.BOTTOM);
    }

    public final void I18N() {

        try {

            setIconImage(EditorI18N.loadIcon("APPLICATION.icon", 24).getImage());

            EditorI18N.loadResource(mnFile, "MENU.file");

            EditorI18N.loadResource(mnNewFlux, btNewFlux, "FILE.new");

            EditorI18N.loadResource(mnOpenFile, btOpenFile, "FILE.open");

            EditorI18N.loadResource(mnSaveAs, btSaveFileAs, "FILE.save.as");
            EditorI18N.loadResource(mnExitApplication, "FILE.exit");
            EditorI18N.loadResource(mnSaveFile, "FILE.save");

            EditorI18N.loadResource(mnAbout, "FILE.about");

            EditorI18N.loadResource(mnView, "MENU.view");
            EditorI18N.loadResource(mnCopyToClipboard, "VIEW.copyClipboard");
            EditorI18N.loadResource(mnSave_Image, "VIEW.saveImage");

            EditorI18N.loadResource(mnZoomOut, btZoomOut, "VIEW.zoom_out");
            EditorI18N.loadResource(mnZoomIn, btZoomIn, "VIEW.zoom_in");

            EditorI18N.loadResource(mnProgram, "MENU.program");
            EditorI18N.loadResource(mnCode, btCode, "PROGRAM.code");
            EditorI18N.loadResource(mnRun, btRunFlux, "PROGRAM.run");

            EditorI18N.loadResource(mnNewFunction, btAddFunction, "PROGRAM.newFunction");

            EditorI18N.loadResource(mnOpenFunction, btOpenFunction, "FILE.open.function");

            EditorI18N.loadResource(mnGlobalMemory, btGlobalMemory, "PROGRAM.globalMemory");

            setTitle(Fi18N.get("FLOWCHART.application.title"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro na leitura da internacionalização");
        }
    }

    public void showAbout() {
        ImageIcon icon = EditorI18N.loadIcon("PROGRAM.icon", 256);
        ImageIcon tabIcon = EditorI18N.loadIcon("PROGRAM.icon", 32);

        final JLabel lb = new JLabel(EditorI18N.get("PROGRAM.about.info"), SwingConstants.CENTER);
        lb.setIcon(icon);
        tbProgram.insertTab(EditorI18N.get("PROGRAM.about"), tabIcon, lb, "", 0);
        tbProgram.setTabComponentAt(0, new ButtonsFluxTab(tbProgram, null));
        tbProgram.setSelectedIndex(0);
        //automatic close of showAboutBox
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    tbProgram.remove(lb);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Fluxograma.class.getName()).log(Level.SEVERE, null, ex);
                }
//                if (tbProgram.getTabCount() == 0) {
//                    btNewFluxActionPerformed(null);
//                }
            }
        }).start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        lbblStatus = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstProgramFiles = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtPath = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        btNewFlux = new javax.swing.JButton();
        btOpenFile = new javax.swing.JButton();
        btSaveFileAs = new javax.swing.JButton();
        pnProgramInfo = new javax.swing.JPanel();
        lblUserAvatar = new javax.swing.JLabel();
        lblUserFullName = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btGlobalMemory = new javax.swing.JButton();
        btAddFunction = new javax.swing.JButton();
        btOpenFunction = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btCode = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btRunFlux = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btZoomOut = new javax.swing.JButton();
        btZoomIn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        tbProgram = new javax.swing.JTabbedPane();
        mainMenu = new javax.swing.JMenuBar();
        mnFile = new javax.swing.JMenu();
        mnNewFlux = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnOpenFile = new javax.swing.JMenuItem();
        mnOpenFunction = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnSaveFile = new javax.swing.JMenuItem();
        mnSaveAs = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnAbout = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        mnExitApplication = new javax.swing.JMenuItem();
        mnView = new javax.swing.JMenu();
        mnZoomOut = new javax.swing.JMenuItem();
        mnZoomIn = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        mnProperties = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        mnCopyToClipboard = new javax.swing.JMenuItem();
        mnSave_Image = new javax.swing.JMenuItem();
        mnProgram = new javax.swing.JMenu();
        mnCompile = new javax.swing.JMenuItem();
        mnRun = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mnNewFunction = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mnGlobalMemory = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mnCode = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lblUser.setText("user");
        lblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUserMouseClicked(evt);
            }
        });

        lbblStatus.setText("Flowchart alpha version 0.0");
        lbblStatus.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(lbblStatus)
            .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        lstProgramFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstProgramFilesMouseClicked(evt);
            }
        });
        lstProgramFiles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lstProgramFilesKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lstProgramFilesKeyTyped(evt);
            }
        });
        lstProgramFiles.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstProgramFilesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstProgramFiles);

        txtPath.setEditable(false);
        txtPath.setBackground(new java.awt.Color(204, 204, 204));
        txtPath.setColumns(20);
        txtPath.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtPath.setForeground(new java.awt.Color(0, 0, 0));
        txtPath.setLineWrap(true);
        txtPath.setRows(5);
        jScrollPane3.setViewportView(txtPath);

        jPanel4.setLayout(new java.awt.GridLayout(1, 3, 5, 0));

        btNewFlux.setText("new");
        btNewFlux.setFocusable(false);
        btNewFlux.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btNewFlux.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btNewFlux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNewFluxActionPerformed(evt);
            }
        });
        jPanel4.add(btNewFlux);

        btOpenFile.setText("open");
        btOpenFile.setFocusable(false);
        btOpenFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btOpenFile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOpenFileActionPerformed(evt);
            }
        });
        jPanel4.add(btOpenFile);

        btSaveFileAs.setText("save As");
        btSaveFileAs.setFocusable(false);
        btSaveFileAs.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btSaveFileAs.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btSaveFileAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveFileAsActionPerformed(evt);
            }
        });
        jPanel4.add(btSaveFileAs);

        pnProgramInfo.setBorder(javax.swing.BorderFactory.createTitledBorder("UserName"));

        lblUserAvatar.setBackground(new java.awt.Color(204, 255, 255));
        lblUserAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserAvatar.setText("user");
        lblUserAvatar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblUserAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUserAvatarMouseClicked(evt);
            }
        });

        lblUserFullName.setText("jButton1");

        javax.swing.GroupLayout pnProgramInfoLayout = new javax.swing.GroupLayout(pnProgramInfo);
        pnProgramInfo.setLayout(pnProgramInfoLayout);
        pnProgramInfoLayout.setHorizontalGroup(
            pnProgramInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUserAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblUserFullName, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
        );
        pnProgramInfoLayout.setVerticalGroup(
            pnProgramInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProgramInfoLayout.createSequentialGroup()
                .addComponent(lblUserAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(lblUserFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addComponent(pnProgramInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(pnProgramInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        btGlobalMemory.setText("AddMem");
        btGlobalMemory.setFocusable(false);
        btGlobalMemory.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btGlobalMemory.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btGlobalMemory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGlobalMemoryActionPerformed(evt);
            }
        });
        jToolBar1.add(btGlobalMemory);

        btAddFunction.setText("AddFunc");
        btAddFunction.setFocusable(false);
        btAddFunction.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btAddFunction.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btAddFunction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddFunctionActionPerformed(evt);
            }
        });
        jToolBar1.add(btAddFunction);

        btOpenFunction.setText("openF");
        btOpenFunction.setFocusable(false);
        btOpenFunction.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btOpenFunction.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btOpenFunction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOpenFunctionActionPerformed(evt);
            }
        });
        jToolBar1.add(btOpenFunction);

        jLabel3.setText("            ");
        jToolBar1.add(jLabel3);

        btCode.setText("code");
        btCode.setFocusable(false);
        btCode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btCode.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCodeActionPerformed(evt);
            }
        });
        jToolBar1.add(btCode);

        jLabel2.setText("                     ");
        jToolBar1.add(jLabel2);

        btRunFlux.setText("Run");
        btRunFlux.setFocusable(false);
        btRunFlux.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btRunFlux.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btRunFlux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRunFluxActionPerformed(evt);
            }
        });
        jToolBar1.add(btRunFlux);

        jLabel1.setText("            ");
        jToolBar1.add(jLabel1);

        btZoomOut.setText("Z-");
        btZoomOut.setFocusable(false);
        btZoomOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btZoomOut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btZoomOutActionPerformed(evt);
            }
        });
        jToolBar1.add(btZoomOut);

        btZoomIn.setText("Z+");
        btZoomIn.setFocusable(false);
        btZoomIn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btZoomIn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btZoomInActionPerformed(evt);
            }
        });
        jToolBar1.add(btZoomIn);

        jLabel4.setText("        ");
        jToolBar1.add(jLabel4);

        jPanel5.add(jToolBar1, java.awt.BorderLayout.NORTH);
        jPanel5.add(tbProgram, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel5);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        mnFile.setText("Ficheiro");

        mnNewFlux.setText("newProgram");
        mnNewFlux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnNewFluxActionPerformed(evt);
            }
        });
        mnFile.add(mnNewFlux);
        mnFile.add(jSeparator2);

        mnOpenFile.setText("openProgram");
        mnOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnOpenFileActionPerformed(evt);
            }
        });
        mnFile.add(mnOpenFile);

        mnOpenFunction.setText("openFunction");
        mnOpenFunction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnOpenFunctionActionPerformed(evt);
            }
        });
        mnFile.add(mnOpenFunction);
        mnFile.add(jSeparator1);

        mnSaveFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        mnSaveFile.setText("saveProgram");
        mnSaveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSaveFileActionPerformed(evt);
            }
        });
        mnFile.add(mnSaveFile);

        mnSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        mnSaveAs.setText("save Program As");
        mnSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSaveAsActionPerformed(evt);
            }
        });
        mnFile.add(mnSaveAs);
        mnFile.add(jSeparator3);

        mnAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        mnAbout.setText("obout");
        mnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAboutActionPerformed(evt);
            }
        });
        mnFile.add(mnAbout);
        mnFile.add(jSeparator8);

        mnExitApplication.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        mnExitApplication.setText("Save code");
        mnExitApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnExitApplicationActionPerformed(evt);
            }
        });
        mnFile.add(mnExitApplication);

        mainMenu.add(mnFile);

        mnView.setText("Visualizar");

        mnZoomOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_MINUS, java.awt.event.InputEvent.CTRL_MASK));
        mnZoomOut.setText("zoom out");
        mnZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnZoomOutActionPerformed(evt);
            }
        });
        mnView.add(mnZoomOut);

        mnZoomIn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PLUS, java.awt.event.InputEvent.CTRL_MASK));
        mnZoomIn.setText("Zoom in");
        mnZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnZoomInActionPerformed(evt);
            }
        });
        mnView.add(mnZoomIn);
        mnView.add(jSeparator7);

        mnProperties.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        mnProperties.setText("Propiedades");
        mnProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPropertiesActionPerformed(evt);
            }
        });
        mnView.add(mnProperties);
        mnView.add(jSeparator9);

        mnCopyToClipboard.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mnCopyToClipboard.setText("copy clipboard");
        mnCopyToClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCopyToClipboardActionPerformed(evt);
            }
        });
        mnView.add(mnCopyToClipboard);

        mnSave_Image.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        mnSave_Image.setText("save Image");
        mnSave_Image.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSave_ImageActionPerformed(evt);
            }
        });
        mnView.add(mnSave_Image);

        mainMenu.add(mnView);

        mnProgram.setText("Programa");

        mnCompile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        mnCompile.setText("compilar");
        mnCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCompileActionPerformed(evt);
            }
        });
        mnProgram.add(mnCompile);

        mnRun.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        mnRun.setText("Correr");
        mnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnRunActionPerformed(evt);
            }
        });
        mnProgram.add(mnRun);
        mnProgram.add(jSeparator5);

        mnNewFunction.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        mnNewFunction.setText("new Function");
        mnProgram.add(mnNewFunction);
        mnProgram.add(jSeparator4);

        mnGlobalMemory.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        mnGlobalMemory.setText("GlobalMemory");
        mnGlobalMemory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGlobalMemoryActionPerformed(evt);
            }
        });
        mnProgram.add(mnGlobalMemory);
        mnProgram.add(jSeparator6);

        mnCode.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        mnCode.setText("portugol");
        mnCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCodeActionPerformed(evt);
            }
        });
        mnProgram.add(mnCode);

        mainMenu.add(mnProgram);

        setJMenuBar(mainMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnNewFluxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnNewFluxActionPerformed
        myProgram.tryToSave();
        newProgram(true);
    }//GEN-LAST:event_mnNewFluxActionPerformed

    private void newProgram(boolean askForName) {
        FProperties.setUser(user);
        boolean firstTime = myProgram == null;
        //currentPath
        String currentPath = FileUtils.PROGRAMS_PATH;
        if (!firstTime) {//update path to the same of myProgram            
            currentPath = myProgram.getFileName();
        } else {
            currentPath = currentPath + Program.defaultFileName;
        }

        if (askForName) {
            NewProgram.show(currentPath);
        } else {
            NewProgram.fileName = currentPath;
        }
        if (!NewProgram.isCanceled) {
            //create program
            myProgram = new Program();
            //create file in the current path
            myProgram.setFileName(NewProgram.fileName);
            myProgram.txtProblem = NewProgram.problemDescription;
            myProgram.setMain(new AlgorithmGraph(new JPanel(), myProgram));
//            createMainEditor(myProgram.getMain());
            createDisplayToProgram(myProgram);
            FMessages.status(lbblStatus, FMessages.INFO, "PROGRAM.newProgram.created", myProgram.getFileName());
            if (!firstTime) {//save the program if is not the first time
                mnSaveFileActionPerformed(null);
            }
        }
        myProgram.tryToSave();
        updateFileList(myProgram.fileName);
        lstProgramFiles.setSelectedValue(FileUtils.getFileFromPath(myProgram.getFileName()), true);

    }

    private FeditorScrool getCurrentFlux() {
        int index = tbProgram.getSelectedIndex();
        if (tbProgram.getComponentAt(index) instanceof FeditorScrool) {
            return ((FeditorScrool) tbProgram.getComponentAt(index));
        }
        return null;

    }
    private void btNewFluxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNewFluxActionPerformed
        mnNewFluxActionPerformed(evt);

    }//GEN-LAST:event_btNewFluxActionPerformed

    private void btZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btZoomOutActionPerformed
        myProgram.tryToSave();
        myProgram.zoomOut();
        FMessages.status(lbblStatus, FMessages.OK, "Zoom " + myProgram.getZoom());

    }//GEN-LAST:event_btZoomOutActionPerformed

    private void btZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btZoomInActionPerformed
        myProgram.tryToSave();
        myProgram.zoomIn();
        FMessages.status(lbblStatus, FMessages.OK, "Zoom " + myProgram.getZoom());
    }//GEN-LAST:event_btZoomInActionPerformed

    private void mnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnZoomOutActionPerformed
        btZoomOutActionPerformed(evt);
    }//GEN-LAST:event_mnZoomOutActionPerformed

    private void mnZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnZoomInActionPerformed
        btZoomInActionPerformed(evt);
    }//GEN-LAST:event_mnZoomInActionPerformed


    private void btRunFluxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRunFluxActionPerformed
        myProgram.tryToSave();
        List<Fshape> errors = myProgram.compile();
        if (errors.isEmpty()) {
            FMessages.status(lbblStatus, FMessages.INFO, "PROGRAM.newProgram.run", myProgram.getFileName());
            Program toRun = myProgram.getClone();
            new RunProgram(toRun);
        }
    }//GEN-LAST:event_btRunFluxActionPerformed

    private void mnCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCodeActionPerformed
        btCodeActionPerformed(evt);
    }//GEN-LAST:event_mnCodeActionPerformed

    private void mnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnRunActionPerformed
        btRunFluxActionPerformed(evt);
    }//GEN-LAST:event_mnRunActionPerformed

    private void mnSaveFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSaveFileActionPerformed
        if (myProgram == null) {
            FMessages.status(lbblStatus, FMessages.WARNING, "PROGRAM.run.noProgram");
            return;
        }
        try {
            myProgram.save();
            lbblStatus.setText(EditorI18N.get("FILES.prog.success.message") + ":" + myProgram.getFileName());
            updateGUI();
            FMessages.status(lbblStatus, FMessages.INFO, "PROGRAM.newProgram.saved", myProgram.getFileName());
        } catch (Exception ex) {
            myProgram.fileName = FileUtils.correctFileName(myProgram.fileName);
            FMessages.status(lbblStatus, FMessages.WARNING, "PROGRAM.newProgram.saved", myProgram.getFileName());
        }

    }//GEN-LAST:event_mnSaveFileActionPerformed

    private void createDisplaytoProgram() {
        createMainEditor(myProgram.getMain());
        if (myProgram.getGlobalMem() != null) {
            createMemoryEditor(myProgram.getGlobalMem());
        }
        for (FunctionGraph func : myProgram.getFunctions()) {
            createFunctionEditor(func);
        }
        tbProgram.setSelectedIndex(0);
        FMessages.status(lbblStatus, FMessages.INFO, "PROGRAM.newProgram.created", myProgram.getFileName());
    }

    private void mnOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnOpenFileActionPerformed
        myProgram.tryToSave();
        createDisplayToProgram(FileUtils.chooseProgramFromFile(myProgram.getFileName()));
    }//GEN-LAST:event_mnOpenFileActionPerformed

    public void createDisplayToProgram(Program newProg) {
        if (newProg != null) {
            myProgram.tryToSave();
            myProgram = newProg;
            myProgram.updateProgramOfAlgorithms();
            createDisplaytoProgram();
            FMessages.status(lbblStatus, FMessages.INFO, "PROGRAM.newProgram.opened", myProgram.getFileName());
            showProgramInfo();
        }
    }

    public void updateFileList(String path) {
        File[] progs;
        if (!path.isEmpty()) {
            txtPath.setText(FileUtils.getPath(path));
            progs = FileUtils.getProgramsInPath(path);
//            txtPath.setText(FileUtils.getPath(myProgram.getFileName()));
//            progs = FileUtils.getProgramsInPath(myProgram.getFileName());

        } else {
            txtPath.setText(System.getProperty("user.dir"));
            progs = FileUtils.getProgramsInPath(System.getProperty("user.dir"));
        }
        DefaultListModel model = new DefaultListModel();
        for (File file : progs) {
            model.addElement(file.getName());
        }
        lstProgramFiles.setModel(model);
//
//        if (myProgram != null) {
//            lstProgramFiles.setSelectedValue(FileUtils.getFileFromPath(myProgram.getFileName()), true);
//        }

    }

    private void btOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOpenFileActionPerformed
        mnOpenFileActionPerformed(evt);
    }//GEN-LAST:event_btOpenFileActionPerformed

    private void btSaveFileAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveFileAsActionPerformed
        mnSaveAsActionPerformed(evt);
    }//GEN-LAST:event_btSaveFileAsActionPerformed

    private void btAddFunctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddFunctionActionPerformed
        myProgram.tryToSave();
        //-------------------------------------------------------------------------NEW FUNC
        FunctionGraph fg = new FunctionGraph(new JPanel(), myProgram);
        Function newFunc = (Function)fg.getBegin();
        //newFunc.setLocation(btAddFunction.getLocation());
        //menu to create function
        MenuFunction m = new MenuFunction();
        m.showDialog(newFunc, 0, 0);
        //all is ok
        if (!m.isIsCanceled()) {
//            fg.updateDefinition(newFunc);
            createFunctionEditor(fg);
            myProgram.tryToSave();
            FMessages.status(lbblStatus, FMessages.INFO, "PROGRAM.function.created.OK", fg.getFunctionName());
        } else {
            myProgram.remove(fg);
        }

    }//GEN-LAST:event_btAddFunctionActionPerformed


    private void mnSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSaveAsActionPerformed
        try {
            FileUtils.saveProgramAs(myProgram);

            FMessages.status(lbblStatus, FMessages.INFO, "FILES.prog.success.message", myProgram.getFileName());
            updateGUI();
            updateFileList(myProgram.getFileName());
            lstProgramFiles.setSelectedValue(FileUtils.getFileFromPath(myProgram.getFileName()), true);
        } catch (Exception ex) {
            FMessages.flowchartException(new FlowchartException(ex));
        }
    }//GEN-LAST:event_mnSaveAsActionPerformed

    private void mnGlobalMemoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGlobalMemoryActionPerformed
        if (myProgram == null) {
            FMessages.status(lbblStatus, FMessages.WARNING, "PROGRAM.run.noProgram");
            return;
        }
        if (myProgram.getGlobalMem() != null) {
            tbProgram.setSelectedIndex(0);
            FMessages.status(lbblStatus, FMessages.WARNING, "PROGRAM.memory.load.duplicated");
        } else {
            GlobalMemoryGraph mem = new GlobalMemoryGraph(new JPanel(), myProgram);
            myProgram.setGlobalMemory(mem);
            createMemoryEditor(mem);
            FMessages.status(lbblStatus, FMessages.INFO, "PROGRAM.memory.created.OK");
        }
    }//GEN-LAST:event_mnGlobalMemoryActionPerformed

    private void createMemoryEditor(GlobalMemoryGraph mem) {
        //create elements of the tab
        FeditorScrool memory = new FeditorScrool(mem);

        String title = EditorI18N.get("APPLICATION.tabedPane.memory.title");
        String toolTip = EditorI18N.get("APPLICATION.tabedPane.memory.help");
        ImageIcon icon = EditorI18N.loadIcon("APPLICATION.tabedPane.memory.icon", 16);
        //insert tab
        tbProgram.insertTab(title, icon, memory, toolTip, 0);
        //insert close button
        tbProgram.setTabComponentAt(0, new ButtonsFluxTab(tbProgram, myProgram));
        //select tab
        tbProgram.setSelectedIndex(0);
        updateGUI();
    }

    private void createFunctionEditor(FunctionGraph func) {
        //create elements of the tab
        FeditorScrool function = new FeditorScrool(func);
        Function defFunc = (Function) func.getBegin();
        String title = defFunc.getFunctionName();
        String toolTip = defFunc.comments;
        ImageIcon icon = EditorI18N.loadIcon("APPLICATION.tabedPane.main.icon", 16);
        int numTabs = tbProgram.getTabCount();
        //insert tab
        tbProgram.insertTab(title, icon, function, toolTip, numTabs);
        //insert close button
        tbProgram.setTabComponentAt(numTabs, new ButtonsFluxTab(tbProgram, myProgram));
        //select tab
        tbProgram.setSelectedIndex(numTabs);
    }

    private void createMainEditor(AlgorithmGraph alg) {
        //clear all
        tbProgram.removeAll();
        //create elements of the tab
        FeditorScrool mainProg = new FeditorScrool(alg);
        String title = EditorI18N.get("APPLICATION.tabedPane.main.title");
        String toolTip = EditorI18N.get("APPLICATION.tabedPane.main.help");
        ImageIcon icon = EditorI18N.loadIcon("APPLICATION.tabedPane.main.icon", 16);
        tbProgram.insertTab(title, icon, mainProg, toolTip, 0);
        tbProgram.setSelectedComponent(mainProg);
        updateGUI();
    }

    private void btGlobalMemoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGlobalMemoryActionPerformed
        myProgram.tryToSave();
        mnGlobalMemoryActionPerformed(null);
    }//GEN-LAST:event_btGlobalMemoryActionPerformed

    private void mnOpenFunctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnOpenFunctionActionPerformed
//        //load function
//        AlgorithmGraph func = FileUtils.LoadFluxogram(myProgram);
//        //function valid
//        if (myProgram.isNewNameValid(func.getFunctionName())) {
//            if (func instanceof FunctionGraph) {
//                createFunctionEditor((FunctionGraph) func);
//            } else {
//                createMemoryEditor((GlobalMemoryGraph) func);
//            }
//        } else {
//            FMessages.dialogParam(FMessages.ERROR, "APPLICATION.fileLoadedError", func.getFunctionName());
//        }

        btOpenFunctionActionPerformed(evt);
    }//GEN-LAST:event_mnOpenFunctionActionPerformed

    private void mnExitApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnExitApplicationActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnExitApplicationActionPerformed

    private void btCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCodeActionPerformed
        myProgram.tryToSave();
        CodeText.show(myProgram);
    }//GEN-LAST:event_btCodeActionPerformed

    private void btOpenFunctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOpenFunctionActionPerformed
        try {
            //load algorithm from file
            AlgorithmGraph algorithm = FileUtils.LoadFluxogram(myProgram);
            //validate name in the program
//            myProgram.validateNewName(algorithm.getFunctionName());
//            algorithm.myProgram = myProgram;
            //add new function
            if (algorithm instanceof FunctionGraph) {
                createFunctionEditor((FunctionGraph) algorithm);
//                myProgram.addFunction((FunctionGraph) algorithm);
                FMessages.status(lbblStatus, FMessages.INFO, "PROGRAM.memory.load.OK", algorithm.getFunctionName());
            } else { //add new memory
                createMemoryEditor((GlobalMemoryGraph) algorithm);
//                myProgram.setGlobalMem((GlobalMemoryGraph) algorithm);
                FMessages.status(lbblStatus, FMessages.INFO, "PROGRAM.function.load.OK", algorithm.getFunctionName());
            }

        } catch (FlowchartException ex) {
            FMessages.flowchartException(ex);
        }
    }//GEN-LAST:event_btOpenFunctionActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //save current program to load in the next execution
        try {
            myProgram.tryToSave();
            FProperties.set(FProperties.keyLastProgramOpened, myProgram.getFileName());
            FLog.printLn(":::::::::::::: END ::::::::::: " + myProgram.getFileName());
            FProperties.save();
            FLog.close();
        } catch (Exception e) {
        }

//        btSaveFileActionPerformed(null);
//        String file = System.getProperty("user.dir") + File.separator + "bin"
//                + File.separator
//                + user.getName() + "." + FileUtils.FILTER_PROG_EXT;
//        try {
//            FileUtils.saveProgram(myProgram, file);
//        } catch (IOException ex) {
//        }
    }//GEN-LAST:event_formWindowClosing

    private void mnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAboutActionPerformed
        showAbout();
    }//GEN-LAST:event_mnAboutActionPerformed

    private void mnPropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPropertiesActionPerformed
        myProgram.tryToSave();
        FlowChartProperties props = new FlowChartProperties(this.myProgram);
        props.setVisible(true);
        if (!props.isCanceled) {
            setUser(FProperties.getUser());
            //newProgram(true);
        }
    }//GEN-LAST:event_mnPropertiesActionPerformed

    private void mnCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCompileActionPerformed
        List<Fshape> errors = myProgram.compile();
        if (errors.isEmpty()) {
            FMessages.status(lbblStatus, FMessages.OK, EditorI18N.get("MESSAGE.compile.OK"));
        } else {
            FMessages.status(lbblStatus, FMessages.ERROR, EditorI18N.get("MESSAGE.compile.ERROR"));
        }
    }//GEN-LAST:event_mnCompileActionPerformed

    private void mnCopyToClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCopyToClipboardActionPerformed
        FluxImage.copyToClipBoard(myProgram);
    }//GEN-LAST:event_mnCopyToClipboardActionPerformed

    private void mnSave_ImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSave_ImageActionPerformed
        FluxImage.saveTofile(myProgram);
    }//GEN-LAST:event_mnSave_ImageActionPerformed

    private void lstProgramFilesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstProgramFilesValueChanged
        if (lstProgramFiles.getSelectedIndex() >= 0) {
            if (myProgram != null) { // save if not null ( deleted file )
                myProgram.tryToSave();
//                displayFile(FileUtils.getPath(myProgram.getFileName()) + lstProgramFiles.getSelectedValue().toString());
            }
            displayFile(txtPath.getText() + lstProgramFiles.getSelectedValue().toString());

        }
    }//GEN-LAST:event_lstProgramFilesValueChanged

    private void displayFile(String fileName) {
        try {
            myProgram = Program.loadProgram(fileName);
            createDisplayToProgram(myProgram);
        } catch (Exception ex) {
            FLog.printLn("lstProgramFilesValueChanged " + fileName + " " + ex.getMessage());
        }
    }

    private void askToDeleteFile() {
        String msg = EditorI18N.get("POPUP.listFiles.delete.message").replace("%1", FileUtils.getFileFromPath(myProgram.getFileName()));
        int resp = FMessages.dialogYesNo("warning", msg);
        if (resp == 0) {
            File file = new File(myProgram.getFileName());
            try {
                file.delete();
                //  Files.deleteIfExists(file.toPath());
            } catch (Exception ex) {
//                Fdialog.compileException(ex);
                Fdialog.showEditorKeyMessage("POPUP.listFiles.deleteError", new String[]{myProgram.getFileName()});
                return;
            }
            if (lstProgramFiles.getModel().getSize() > 1) {
                String fileName = myProgram.getFileName();
                myProgram = null; // delete file
                int index = lstProgramFiles.getSelectedIndex() ; // selected index
                updateFileList(fileName);
                lstProgramFiles.setSelectedIndex(index >= 0 ? index : 0);
            } else {
                newProgram(true);
            }
            return;
        }
    }

    private void lstProgramFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstProgramFilesMouseClicked

//        if (!lstProgramFiles.isSelectionEmpty() // and list selection is not empty
//                && lstProgramFiles.locationToIndex(evt.getPoint()) // and clicked point is
//                == lstProgramFiles.getSelectedIndex()) {       //   inside selected item bounds
//
//            if (SwingUtilities.isRightMouseButton(evt)) { //right
//                askToDeleteFile();
//            }//right
//            else {
//                displayFile(FileUtils.getPath(myProgram.getFileName()) + lstProgramFiles.getSelectedValue().toString());
//            }
//
//        }
    }//GEN-LAST:event_lstProgramFilesMouseClicked

    private void lstProgramFilesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstProgramFilesKeyTyped

    }//GEN-LAST:event_lstProgramFilesKeyTyped

    private void lstProgramFilesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstProgramFilesKeyPressed
        if (lstProgramFiles.isSelectionEmpty()) {
            return;
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            displayFile(FileUtils.getPath(myProgram.getFileName())
                    + lstProgramFiles.getSelectedValue().toString());
        }
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            askToDeleteFile();
        }
        System.out.println("KEY " + evt.getKeyCode());
    }//GEN-LAST:event_lstProgramFilesKeyPressed

    private void lblUserAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserAvatarMouseClicked
        myProgram.tryToSave();
        FlowChartProperties props = new FlowChartProperties(this.myProgram);
        props.setVisible(true);
        if (!props.isCanceled) {
            setUser(FProperties.getUser());
            newProgram(true);
        }
    }//GEN-LAST:event_lblUserAvatarMouseClicked

    private void lblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserMouseClicked
        mnPropertiesActionPerformed(null);
    }//GEN-LAST:event_lblUserMouseClicked

    public void updateGUI() {
        setTitle(EditorI18N.get("APPLICATION.title")
                + " [" + myProgram.getFileName() + "]");

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fluxograma.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fluxograma.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fluxograma.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fluxograma.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fluxograma().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddFunction;
    private javax.swing.JButton btCode;
    private javax.swing.JButton btGlobalMemory;
    private javax.swing.JButton btNewFlux;
    private javax.swing.JButton btOpenFile;
    private javax.swing.JButton btOpenFunction;
    private javax.swing.JButton btRunFlux;
    private javax.swing.JButton btSaveFileAs;
    private javax.swing.JButton btZoomIn;
    private javax.swing.JButton btZoomOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbblStatus;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserAvatar;
    private javax.swing.JButton lblUserFullName;
    private javax.swing.JList lstProgramFiles;
    private javax.swing.JMenuBar mainMenu;
    private javax.swing.JMenuItem mnAbout;
    private javax.swing.JMenuItem mnCode;
    private javax.swing.JMenuItem mnCompile;
    private javax.swing.JMenuItem mnCopyToClipboard;
    private javax.swing.JMenuItem mnExitApplication;
    private javax.swing.JMenu mnFile;
    private javax.swing.JMenuItem mnGlobalMemory;
    private javax.swing.JMenuItem mnNewFlux;
    private javax.swing.JMenuItem mnNewFunction;
    private javax.swing.JMenuItem mnOpenFile;
    private javax.swing.JMenuItem mnOpenFunction;
    private javax.swing.JMenu mnProgram;
    private javax.swing.JMenuItem mnProperties;
    private javax.swing.JMenuItem mnRun;
    private javax.swing.JMenuItem mnSaveAs;
    private javax.swing.JMenuItem mnSaveFile;
    private javax.swing.JMenuItem mnSave_Image;
    private javax.swing.JMenu mnView;
    private javax.swing.JMenuItem mnZoomIn;
    private javax.swing.JMenuItem mnZoomOut;
    private javax.swing.JPanel pnProgramInfo;
    private javax.swing.JTabbedPane tbProgram;
    private javax.swing.JTextArea txtPath;
    // End of variables declaration//GEN-END:variables
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @return the user
     */
    public UserName getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserName user) {
        try {
            this.user = user;
            lblUser.setText(user.getName() + " [" + user.getCode() + "]" + user.getFullName());
            lblUser.setIcon(ImageUtils.resize(user.getAvatar(), 24, 24));
            File file = new File(FProperties.get(FProperties.keyLastProgramOpened));
            if (file.exists()) {
                myProgram = Program.loadProgram(file.getAbsolutePath());
            } else {
                newProgram(false);
            }
            createDisplaytoProgram();
            FMessages.status(lbblStatus, FMessages.INFO, "PROGRAM.newProgram.opened", myProgram.getFileName());
        } catch (Exception ex) {
            newProgram(false);
        }
        showProgramInfo();
        updateFileList(myProgram.fileName);
        lstProgramFiles.setSelectedValue(FileUtils.getFileFromPath(myProgram.getFileName()), true);
        FLog.printLn("User :" + user.getCode() + " - " + user.getName() + " - " + user.getFullName());
    }

    public void showProgramInfo() {
        if (myProgram == null) {
            return;
        }
        UserName user1 = UserName.createUser(myProgram.digitalSignature);
        pnProgramInfo.setBorder(BorderFactory.createTitledBorder(
                user1.getFullName()));
        lblUserAvatar.setIcon(user1.getAvatar());
        lblUserAvatar.setText("");
        lblUserFullName.setText(FileUtils.getFileFromPath(myProgram.getFileName()));
        lblUserAvatar.setToolTipText("[" + user1.getCode() + "] " + user1.getFullName());

    }
}
