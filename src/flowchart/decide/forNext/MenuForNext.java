/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowchart.decide.forNext;

import core.Memory;
import core.data.FabstractNumber;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import ui.FProperties;
import ui.flowchart.dialogs.Fdialog;
import ui.flowchart.dialogs.ShapeMenuDialog;
import i18n.Fi18N;
import i18n.FkeyWord;
import flowchart.help.Help;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import flowchart.utils.Theme;
import ui.flowchart.expression.Identation;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author ZULU
 */
public class MenuForNext extends ShapeMenuDialog implements MenuPattern {

    For_Next for_next;
    String oldText;
    Memory memory;
    Fsymbol var;
    Expression from, to, step;

    /**
     * Creates new form TerminatorMenu
     */
    public MenuForNext() {
        super("FOR.instruction.title");
//        setUndecorated(true);
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
                    evt.consume();
                }
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    btOkActionPerformed(null);
                    evt.consume();
                }
            }
        };
        //------------------------------------------------------------
        //txtComments.addKeyListener(menuKeyListener);
        txtExpressionShape.addKeyListener(menuKeyListener);
        txtFrom.addKeyListener(menuKeyListener);
        txtStep.addKeyListener(menuKeyListener);
        txtTo.addKeyListener(menuKeyListener);
        txtNewName.addKeyListener(menuKeyListener);

    }

    public void I18N() {
        txtExpressionShape.setKeyword(For_Next.KEYWORD);
        Fi18N.loadButton(btOk, "MENU.OK", 24);
        Fi18N.loadButton(btCancel, "MENU.cancel", 24);
        Fi18N.loadButton(btHelp, "MENU.help", 16);
        //---------------- T A B S  -------------------
        Fi18N.loadTabTile(tabMain, "FOR.tabVariable", 0);
        Fi18N.loadTabTile(tabMain, "MENU.comments", 1);
        Fi18N.loadTabTile(tabMain, "MENU.help", 2);

        Fi18N.loadTabTile(tpVariable, "FOR.tab.memory", 0);
        Fi18N.loadTabTile(tpVariable, "FOR.tab.iterator", 1);

        Fi18N.loadMenuShapeHelp(lblHelp, "FOR.instruction");
        lblHelp.setBackground(FProperties.iterationColor);

        Fi18N.loadLabel(lblVarName, "FOR.memVariable");
        cbVariable.setToolTipText(Theme.toHtml(Fi18N.get("FOR.tabVariable.help")));

        Fi18N.loadLabel(lblNewType, "FOR.varIterator.type");
        cbNewType.setToolTipText(Theme.toHtml(Fi18N.get("FOR.varIterator.type.help")));

        Fi18N.loadLabel(lblNewName, "FOR.varIterator.name");
        txtNewName.setToolTipText(Theme.toHtml(Fi18N.get("FOR.varIterator.name.help")));

        lblStart.setText(Fi18N.get("FOR.start.title"));
        txtFrom.setToolTipText(Fi18N.get("FOR.start.help"));

        lblStop.setText(Fi18N.get("FOR.stop.title"));
        txtTo.setToolTipText(Fi18N.get("FOR.stop.help"));

        lblStep.setText(Fi18N.get("FOR.step.title"));
        txtStep.setToolTipText(Fi18N.get("FOR.step.help"));

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel();
        model.addElement(FkeyWord.get("TYPE.integer"));
        model.addElement(FkeyWord.get("TYPE.real"));
        cbNewType.setModel(model);

        txtFrom.paintBackground();
        txtTo.paintBackground();
        txtStep.paintBackground();
    }

    public void updateGUI() {
        try {
            StringBuilder txt = new StringBuilder();
            if (tpVariable.getSelectedIndex() == 0 && cbVariable.getSelectedIndex() >= 0) {
                //------------------------------------------------------------------  memory variable
                txt.append(cbVariable.getSelectedItem().toString());
            } else {
                //------------------------------------------------------------------  new variable
                txt.append(cbNewType.getSelectedItem().toString() + " " + txtNewName.getText().trim());
            }
            txt.append(" " + For_Next._FROM + " ");
            txt.append(Identation.ident(txtFrom.getText(), memory, for_next.algorithm.myProgram));
            txt.append(" " + For_Next._TO + " ");
            txt.append(Identation.ident(txtTo.getText(), memory, for_next.algorithm.myProgram));
            txt.append(" " + For_Next._STEP + " ");
            txt.append(Identation.ident(txtStep.getText(), memory, for_next.algorithm.myProgram));
            txtExpressionShape.setInstruction(txt.toString());
        } catch (Exception e) {
        }
    }

    @Override
    public void showDialog(Fshape shape, int x, int y) {

        this.for_next = (For_Next) shape;

        oldText = shape.getInstruction();
        memory = shape.algorithm.getMemory(shape.parent);

        txtExpressionShape.updateMenu(memory, shape.algorithm.getMyProgram());
        txtFrom.updateMenu(memory, shape.algorithm.getMyProgram());
        txtTo.updateMenu(memory, shape.algorithm.getMyProgram());
        txtStep.updateMenu(memory, shape.algorithm.getMyProgram());
        //----------------------------------------------- COMBO BOX NUMERIC VARIABLE
//        memory = shape.algorithm.getMemory(shape.parent);
        int countNumericVariables = 0;
        //-----------------------------------------
        //update model 
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel();
        for (Fsymbol var : memory.getMem()) {
            if (var instanceof FabstractNumber) {
                countNumericVariables++;
                model.addElement(var.getName());
            }
        }
        cbVariable.setModel(model);
        //-------------------------------------------------------- HIDE VARIABLE
        if (countNumericVariables == 0) {
            tpVariable.getTabComponentAt(0).setEnabled(false);
            tpVariable.setSelectedIndex(1);
        } else {
            tpVariable.getTabComponentAt(0).setEnabled(true);
            tpVariable.setSelectedIndex(0);
        }
        setShape();
        //-----------------------------------------
        //:::::::::::::::::::::::::::::::::::::::::
        //::::::::   Show Dialog  :::::::::::::::::
        setLocationRelativeTo(null);
        updateGUI();
        updateFont();
        txtExpressionShape.requestFocus();
        this.setVisible(true);
        //:::::::::::::::::::::::::::::::::::::::::
    }

    private void setShape() {
        txtNewName.setForeground(Color.BLACK);
        txtFrom.setForeground(Color.BLACK);
        txtStep.setForeground(Color.BLACK);
        txtTo.setForeground(Color.BLACK);

//        memory = for_next.algorithm.getMemory(for_next.parent);
        // update the information with shape
        if (for_next.var != null) {
            cbVariable.setSelectedItem(for_next.getName());
            txtFrom.setText(for_next.start.getIdented());
            txtTo.setText(for_next.stop.getIdented());
            txtStep.setText(for_next.step.getIdented());
            //update variable
            Fsymbol varMem = memory.getByName(for_next.var.getName());
            if (varMem != null) {
                tpVariable.setSelectedIndex(0);
                cbVariable.setSelectedItem(varMem.getName());
            } else {
                tpVariable.setSelectedIndex(1);
                cbNewType.setSelectedItem(for_next.var.getTypeName());
                txtNewName.setText(for_next.var.getName());
            }
            //update comments
            txtComments.setText(for_next.getComments());
            tabMain.setSelectedIndex(1); // coments
        } //ser default values
        else {
            tabMain.setSelectedIndex(0); // variable
            //default iterators
            cbNewType.setSelectedIndex(0);
            txtNewName.setText(Fi18N.get("FOR.iterator.defaultName") + getNewIteratorNumber());
            txtFrom.setText("0");
            txtTo.setText("5");
            txtStep.setText("1");
            txtComments.setText("");

        }
//        txtComments.setText(this.for_next.getComments());

    }

    /**
     * updates the static iterator number to the last definide in memory
     */
    private int getNewIteratorNumber() {
        String it = Fi18N.get("FOR.iterator.defaultName");
        int iteraNumber = 0;
        Fshape previous = for_next.parent;
        while (previous != null) {
            if (previous instanceof For_Next) {
                For_Next fornext = (For_Next) previous;
                if (fornext.var.getName().startsWith(it)) {
                    try {
                        int number = Integer.parseInt(fornext.var.getName().substring(it.length()));
                        if (number > iteraNumber) {
                            iteraNumber = number;
                        }
                    } catch (Exception e) {
                    }

                }
            }
            previous = previous.parent;
        }
        return iteraNumber + 1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        tabMain = new javax.swing.JTabbedPane();
        pnVariable = new javax.swing.JPanel();
        tpVariable = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        cbVariable = new javax.swing.JComboBox();
        lblVarName = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtNewName = new javax.swing.JTextField();
        lblNewName = new javax.swing.JLabel();
        lblNewType = new javax.swing.JLabel();
        cbNewType = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        lblStart = new javax.swing.JLabel();
        lblStop = new javax.swing.JLabel();
        lblStep = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtFrom = new ui.flowchart.expression.TextExpression();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtTo = new ui.flowchart.expression.TextExpression();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtStep = new ui.flowchart.expression.TextExpression();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComments = new javax.swing.JTextArea();
        lblHelp = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btOk = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        btHelp = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtExpressionShape = new ui.flowchart.expression.TextExpression();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        pnVariable.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tpVariable.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        tpVariable.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tpVariableStateChanged(evt);
            }
        });

        cbVariable.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        cbVariable.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbVariableActionPerformed(evt);
            }
        });

        lblVarName.setText("Var");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblVarName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cbVariable, 0, 199, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblVarName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tpVariable.addTab("tab1", jPanel1);

        txtNewName.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        txtNewName.setText("jTextField2");
        txtNewName.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNewNameCaretUpdate(evt);
            }
        });

        lblNewName.setText("name");

        lblNewType.setText("Var");

        cbNewType.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        cbNewType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbNewType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNewTypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lblNewName, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNewName))
            .addComponent(cbNewType, 0, 199, Short.MAX_VALUE)
            .addComponent(lblNewType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lblNewType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbNewType, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNewName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNewName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tpVariable.addTab("tab2", jPanel4);

        lblStart.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblStart.setText("start");

        lblStop.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblStop.setText("stop");

        lblStep.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblStep.setText("step");

        txtFrom.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFromCaretUpdate(evt);
            }
        });
        jScrollPane2.setViewportView(txtFrom);

        txtTo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtToCaretUpdate(evt);
            }
        });
        jScrollPane3.setViewportView(txtTo);

        txtStep.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtStepCaretUpdate(evt);
            }
        });
        jScrollPane5.setViewportView(txtStep);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblStop, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblStep, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblStart, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane5)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStop, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblStep, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout pnVariableLayout = new javax.swing.GroupLayout(pnVariable);
        pnVariable.setLayout(pnVariableLayout);
        pnVariableLayout.setHorizontalGroup(
            pnVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnVariableLayout.createSequentialGroup()
                .addComponent(tpVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnVariableLayout.setVerticalGroup(
            pnVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnVariableLayout.createSequentialGroup()
                .addComponent(tpVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabMain.addTab("tab1", pnVariable);

        txtComments.setBackground(new java.awt.Color(204, 204, 204));
        txtComments.setColumns(20);
        txtComments.setRows(2);
        jScrollPane1.setViewportView(txtComments);

        tabMain.addTab("tab3", jScrollPane1);

        lblHelp.setText("help");
        lblHelp.setOpaque(true);
        tabMain.addTab("tab4", lblHelp);

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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabMain)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(tabMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtExpressionShape.setOpaque(true);
        jScrollPane4.setViewportView(txtExpressionShape);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbVariableActionPerformed
        updateGUI();
    }//GEN-LAST:event_cbVariableActionPerformed

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
//        txtNewName.setForeground(Color.BLACK);
//        txtFrom.setForeground(Color.BLACK);
//        txtStep.setForeground(Color.BLACK);
//        txtTo.setForeground(Color.BLACK);
//        tabMain.setSelectedIndex(0);
//        String txtFor = txtExpressionShape.getInstruction();
//        String _from = txtFor.substring(txtFor.indexOf(For_Next._FROM) + For_Next._FROM.length(), txtFor.indexOf(For_Next._TO));
//        String _to = txtFor.substring(txtFor.indexOf(For_Next._TO) + For_Next._TO.length(), txtFor.indexOf(For_Next._STEP));
//        String _step = txtFor.substring(txtFor.indexOf(For_Next._STEP) + For_Next._STEP.length());
//        txtFrom.setText(_from);
//        txtTo.setText(_to);
//        txtStep.setText(_step);

        try {
//
//            //------------------------------------------------------------------  memory variable
//            if (tpVariable.getSelectedIndex() == 0 && cbVariable.getSelectedIndex() >= 0) {
//                var = memory.getByName(cbVariable.getSelectedItem().toString());
//            } else {
//                //---------------------------------------------------------------new variable
//                //::::::::::::::::::::::::: VARIABLE DEFINED EXCEPTION :::::::::
//                Fsymbol memVar = memory.getByName(txtNewName.getText().trim());
//                if (memVar != null) {
//                    FlowchartException fe = new FlowchartException("FOR.exception.variableDefined", memVar.getName());
//                    Fdialog.compileException(fe);
//                    txtNewName.requestFocus();
//                    txtNewName.setForeground(Color.RED);
//                    tpVariable.setSelectedIndex(1);
//                    return;
//                }
//                //----------------- try to create iterator
//                try {
//                    var = Fsymbol.create(cbNewType.getSelectedItem().toString(), txtNewName.getText().trim());
//                } catch (FlowchartException e) {
//                    Fdialog.compileException(e);
//                    tpVariable.setSelectedIndex(1);
//                    txtNewName.requestFocus();
//                    txtNewName.setForeground(Color.RED);
//                    return;
//                }
//            }
//            //------------------------------------------------------------------ from
//            try {
//                from = new Expression(txtFrom.getText(), memory, for_next.algorithm.getMyProgram());
//                var.setValue(from.getReturnType());
//            } catch (FlowchartException e) {
//                Fdialog.compileException(e);
//                txtFrom.requestFocus();
//                txtFrom.setForeground(Color.RED);
//                return;
//            }
//            //------------------------------------------------------------------ to
//            try {
//                to = new Expression(txtTo.getText(), memory, for_next.algorithm.getMyProgram());
//                var.setValue(to.getReturnType());
//            } catch (FlowchartException e) {
//                Fdialog.compileException(e);
//                txtTo.requestFocus();
//                txtTo.setForeground(Color.RED);
//                return;
//            }
//            //------------------------------------------------------------------ to
//            try {
//                step = new Expression(txtStep.getText(), memory, for_next.algorithm.getMyProgram());
//                var.setValue(step.getReturnType());
//            } catch (FlowchartException e) {
//                Fdialog.compileException(e);
//                txtStep.requestFocus();
//                txtStep.setForeground(Color.RED);
//                return;
//            }
            //------------------------------------------------------------------ all OK
            //for_next.buildInstruction(var, from, to, step, txtComments.getText());            
            for_next.buildInstruction(txtExpressionShape.getText().trim() , txtComments.getText());
            for_next.setSelected(false);
            setVisible(false);
        } catch (FlowchartException e) {
            Fdialog.compileException(e);
        }

    }//GEN-LAST:event_btOkActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        for_next.setInstruction(oldText);
        for_next.setSelected(false);
        setVisible(false);
    }//GEN-LAST:event_btCancelActionPerformed

    private void btHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHelpActionPerformed
        Help.show("FOR.intruction.www");
    }//GEN-LAST:event_btHelpActionPerformed

    private void cbNewTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNewTypeActionPerformed
        updateGUI();
    }//GEN-LAST:event_cbNewTypeActionPerformed

    private void txtNewNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNewNameCaretUpdate
        updateGUI();
    }//GEN-LAST:event_txtNewNameCaretUpdate

    private void tpVariableStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tpVariableStateChanged
        updateGUI();
    }//GEN-LAST:event_tpVariableStateChanged

    private void txtFromCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFromCaretUpdate
        updateGUI();
    }//GEN-LAST:event_txtFromCaretUpdate

    private void txtToCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtToCaretUpdate
        updateGUI();
    }//GEN-LAST:event_txtToCaretUpdate

    private void txtStepCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtStepCaretUpdate
        updateGUI();
    }//GEN-LAST:event_txtStepCaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btHelp;
    private javax.swing.JButton btOk;
    private javax.swing.JComboBox cbNewType;
    private javax.swing.JComboBox cbVariable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblHelp;
    private javax.swing.JLabel lblNewName;
    private javax.swing.JLabel lblNewType;
    private javax.swing.JLabel lblStart;
    private javax.swing.JLabel lblStep;
    private javax.swing.JLabel lblStop;
    private javax.swing.JLabel lblVarName;
    private javax.swing.JPanel pnVariable;
    private javax.swing.JTabbedPane tabMain;
    private javax.swing.JTabbedPane tpVariable;
    private javax.swing.JTextArea txtComments;
    private ui.flowchart.expression.TextExpression txtExpressionShape;
    private ui.flowchart.expression.TextExpression txtFrom;
    private javax.swing.JTextField txtNewName;
    private ui.flowchart.expression.TextExpression txtStep;
    private ui.flowchart.expression.TextExpression txtTo;
    // End of variables declaration//GEN-END:variables

}
