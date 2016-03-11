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
package ui.editor.run;

import core.data.complexData.Farray;
import i18n.EditorI18N;
import ui.dialogs.FMessages;
import ui.utils.TextAreaRun;
import core.data.exception.FlowchartException;
import ui.flowchart.dialogs.Fdialog;
import flowchart.algorithm.Program;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.arrow.Arrow;
import flowchart.shape.Fshape;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import ui.FLog;

/**
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class RunProgram extends javax.swing.JFrame implements Runnable {

    public static int SIZE_OF_MEMORY_DISPLAY = 250;
    public static int SIZE_OF_CONSOLE_DISPLAY = 300;
    Program original;
    GraphExecutor sourceFlux; // source of the algorithms

//    Console console; // console to display input and output
//    Memory mem; // memory     
    MemoryDisplay displayMemory;  // display of memory
//    Fshape nodeInExecution;  // intruction in execution

//    JScrollPane scrool; // scrool to fluxogram
    Thread autorun;
    TextAreaRun txtInstructions;

    /**
     * Creates new form RunAlgorithm
     */
    public RunProgram(Program program) {
        initComponents();
        this.original = program.getClone();
        I18N();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        initMyComponents();
        pack();
        setVisible(true);
        revalidate();
        repaint();
        sppMemory.setDividerLocation(0.20);
        sppFluxogram.setDividerLocation(0.75);
        btStart_debugActionPerformed(null);

    }

    private void initMyComponents() {
        //clone of the original program
        sourceFlux = new GraphExecutor(original);
//        mem = new Memory();

        spMemory.setAlignmentX(LEFT_ALIGNMENT);
        pnMemory.removeAll();
        pnMemory.setLayout(new BoxLayout(pnMemory, BoxLayout.Y_AXIS));

        displayMemory = new MemoryDisplay(sourceFlux);
        pnMemory.add(displayMemory);
        //create graphics with first graph
//        nodeInExecution = initRunningProgram();

        JTabbedPane tp = new JTabbedPane();
        sppFluxogram.setTopComponent(sourceFlux.view);
        sppFluxogram.setBottomComponent(tp);

        tp.addTab(EditorI18N.get("RUN.console"), new JScrollPane(sourceFlux.getConsole()));

        txtInstructions = new TextAreaRun();
        tp.addTab(EditorI18N.get("RUN.intructions"), txtInstructions);

        revalidate();

    }

    public void I18N() {
        EditorI18N.loadResource(btRun_debug, "RUN.debug", 48);
        EditorI18N.loadResource(btRun_step, "RUN.step", 48);
        EditorI18N.loadResource(btRun_abort, "RUN.abort", 48);
        EditorI18N.loadResource(btStart_debug, "RUN.start", 48);
        EditorI18N.loadResource(btClose, "FILE.exit", 48);

        spVelocity.setToolTipText(EditorI18N.get("RUN.executionVelocity"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btClose = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btStart_debug = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btRun_step = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        spVelocity = new javax.swing.JSpinner();
        btRun_debug = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btRun_abort = new javax.swing.JButton();
        sppMemory = new javax.swing.JSplitPane();
        spMemory = new javax.swing.JScrollPane();
        pnMemory = new javax.swing.JPanel();
        sppFluxogram = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btClose.setText("close");
        btClose.setFocusable(false);
        btClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btClose.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCloseActionPerformed(evt);
            }
        });
        jToolBar1.add(btClose);

        jLabel4.setText("                                                ");
        jToolBar1.add(jLabel4);

        btStart_debug.setText("start");
        btStart_debug.setFocusable(false);
        btStart_debug.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btStart_debug.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btStart_debug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStart_debugActionPerformed(evt);
            }
        });
        jToolBar1.add(btStart_debug);

        jLabel7.setText("      ");
        jToolBar1.add(jLabel7);

        btRun_step.setText("step");
        btRun_step.setFocusable(false);
        btRun_step.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btRun_step.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btRun_step.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRun_stepActionPerformed(evt);
            }
        });
        jToolBar1.add(btRun_step);

        jLabel1.setText("                               ");
        jToolBar1.add(jLabel1);

        spVelocity.setModel(new javax.swing.SpinnerNumberModel(5, 1, 10, 1));
        spVelocity.setMaximumSize(new java.awt.Dimension(80, 70));
        spVelocity.setMinimumSize(new java.awt.Dimension(60, 30));
        spVelocity.setPreferredSize(new java.awt.Dimension(60, 30));
        jToolBar1.add(spVelocity);

        btRun_debug.setText("run");
        btRun_debug.setFocusable(false);
        btRun_debug.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btRun_debug.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btRun_debug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRun_debugActionPerformed(evt);
            }
        });
        jToolBar1.add(btRun_debug);

        jLabel8.setText("                     ");
        jToolBar1.add(jLabel8);

        btRun_abort.setText("stop");
        btRun_abort.setFocusable(false);
        btRun_abort.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btRun_abort.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btRun_abort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRun_abortActionPerformed(evt);
            }
        });
        jToolBar1.add(btRun_abort);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        sppMemory.setDividerLocation(100);
        sppMemory.setDividerSize(5);
        sppMemory.setMinimumSize(new java.awt.Dimension(350, 102));

        pnMemory.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pnMemory.setLayout(new javax.swing.BoxLayout(pnMemory, javax.swing.BoxLayout.Y_AXIS));
        spMemory.setViewportView(pnMemory);

        sppMemory.setLeftComponent(spMemory);

        sppFluxogram.setDividerLocation(500);
        sppFluxogram.setDividerSize(5);
        sppFluxogram.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppFluxogram.setMinimumSize(new java.awt.Dimension(10, 27));
        sppFluxogram.setPreferredSize(new java.awt.Dimension(10, 27));
        sppMemory.setRightComponent(sppFluxogram);

        getContentPane().add(sppMemory, java.awt.BorderLayout.CENTER);

        lblStatus.setText("jLabel1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStatus)
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btRun_stepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRun_stepActionPerformed
        //stop thread
        if (autorun != null) {
            try {
                autorun.interrupt();
            } catch (Exception e) {
            }
            autorun = null;
        }

        runStep();
    }//GEN-LAST:event_btRun_stepActionPerformed

    private void updateGUI() {
        if (sourceFlux.getNodeExecuted() != null) {
            displayMemory.updateMemory();
          //  FMessages.status(lblStatus, FMessages.OK, sourceFlux.getNodeExecuted().getExecutionResult());
        }
        Fshape executed = sourceFlux.getNodeExecuted();
        if (!(executed instanceof Arrow)) {
            txtInstructions.append(executed);
        }
    }

    public void runStep() {

        try {
            //sourceFlux.log();
            sourceFlux.executeNext();
            //ignore arrows
            if (getSleep() == 0 && sourceFlux.getNodeInExecution() instanceof Arrow) {
                runStep();
                return;
            }
            updateGUI();
        } catch (FlowchartException ex) {
            String msg = EditorI18N.get("RUN.message.programException")
                    + "\n"
                    + ex.getTxtMessage()
                    + "\n" + EditorI18N.get("RUN.message.programAborted");
            sourceFlux.getConsole().write("\n" + msg);
            Fdialog.compileException(ex);
            FLog.runError(msg);
            btRun_abortActionPerformed(null);
        }
    }

    private void btRun_debugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRun_debugActionPerformed
        if (autorun != null) {
            lblStatus.setText(EditorI18N.get("RUN.message.programInExecution"));
            return;
        }
        if (sourceFlux.getNodeInExecution() == null) {
            btStart_debugActionPerformed(null);
        }
        autorun = new Thread(this);
        autorun.start();

    }//GEN-LAST:event_btRun_debugActionPerformed

    private void btRun_abortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRun_abortActionPerformed
        try {
            autorun.interrupt();
        } catch (Exception e) {
        }
        autorun = null;
        sourceFlux.stopExecution();
        lblStatus.setText(EditorI18N.get("RUN.message.programAborted"));
    }//GEN-LAST:event_btRun_abortActionPerformed

    private void btStart_debugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStart_debugActionPerformed
        sourceFlux.restart();
        txtInstructions.reset();
        updateGUI();
        FMessages.status(lblStatus, FMessages.OK, "RUN.message.programStarted", original.getFileName());
    }//GEN-LAST:event_btStart_debugActionPerformed

    private void btCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCloseActionPerformed
        btRun_abortActionPerformed(evt);
        dispose();
    }//GEN-LAST:event_btCloseActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        autorun = null;
    }//GEN-LAST:event_formWindowClosed

    public void run() {
        while (autorun != null && sourceFlux.getNodeInExecution() != null) {
            try {
                runStep();
                autorun.sleep(getSleep());
            } catch (Exception e) {
                //Flowchart exception - ABORT
                if (e instanceof FlowchartException) {
                    btRun_abortActionPerformed(null);
                    autorun = null;
                }
                //GUI exception - CONTINUE
            }
        }
        autorun = null;
    }

    public int getSleep() {
        return (10 - (Integer) spVelocity.getValue()) * 10;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btClose;
    private javax.swing.JButton btRun_abort;
    private javax.swing.JButton btRun_debug;
    private javax.swing.JButton btRun_step;
    private javax.swing.JButton btStart_debug;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel pnMemory;
    private javax.swing.JScrollPane spMemory;
    private javax.swing.JSpinner spVelocity;
    private javax.swing.JSplitPane sppFluxogram;
    private javax.swing.JSplitPane sppMemory;
    // End of variables declaration//GEN-END:variables

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
