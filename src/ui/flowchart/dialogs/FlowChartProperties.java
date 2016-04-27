/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.flowchart.dialogs;

import ui.FProperties;
import i18n.Fi18N;
import flowchart.algorithm.Program;
import flowchart.shape.MenuDialog;
import flowchart.utils.UserName;
import flowchart.utils.image.ImageUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import ui.dialogs.FMessages;
import ui.utils.Crypt;

/**
 *
 * @author zulu
 */
public class FlowChartProperties extends MenuDialog {

    Font myFont;
    UserName user;
    public boolean isCanceled = true;

    //  Program myProgram;
    /**
     * Creates new form FColors
     */
    public FlowChartProperties() {
        this.user = user;
        setTitle(Fi18N.get("PROPERTIES.menu.title"));
        initComponents();
        setLocationRelativeTo(null);
        I18N();
        loadProperties();
    }

    public void I18N() {
        myFont = FProperties.getFont();
        tpProperties.setTitleAt(0, Fi18N.get("PROPERTIES.tab.user"));
        tpProperties.setTitleAt(1, Fi18N.get("PROPERTIES.tab.shapes"));
        tpProperties.setTitleAt(2, Fi18N.get("PROPERTIES.tab.show"));
        tpProperties.setTitleAt(3, Fi18N.get("PROPERTIES.tab.font"));
        tpProperties.setTitleAt(4, Fi18N.get("PROPERTIES.tab.language"));

        cbItalic.setText(Fi18N.get("PROPERTIES.tab.font.italic"));
        cbBold.setText(Fi18N.get("PROPERTIES.tab.font.bold"));
        lblSize.setText(Fi18N.get("PROPERTIES.tab.font.size"));

        //pnShapes.setBorder(javax.swing.BorderFactory.createTitledBorder(Fi18N.get("PROPERTIES.shapes.title")));
        Fi18N.loadButton(btOk, "BUTTON.accept", 24);
        Fi18N.loadButton(btCancel, "BUTTON.cancel", 24);

        Fi18N.loadLabel(colorTerminator, "PROPERTIES.terminator", 24);
        Fi18N.loadLabel(colorWrite, "PROPERTIES.write", 24);
        Fi18N.loadLabel(colorRead, "PROPERTIES.read", 24);

        Fi18N.loadLabel(colorExecute, "PROPERTIES.execute", 24);
        Fi18N.loadLabel(colorDefine, "PROPERTIES.define", 24);

        Fi18N.loadLabel(colorReturn, "PROPERTIES.return", 24);
        Fi18N.loadLabel(colorJump, "PROPERTIES.jump", 24);

        Fi18N.loadLabel(colorDecision, "PROPERTIES.decision", 24);
        Fi18N.loadLabel(colorConnector, "PROPERTIES.connector", 24);
        Fi18N.loadLabel(colorIteration, "PROPERTIES.iteration", 24);
        //---------------------------------------------------------------------- VIEW
        Fi18N.loadLabel(colorArrow, "PROPERTIES.arrow", 24);
        Fi18N.loadLabel(colorSelected, "PROPERTIES.selected", 24);
        Fi18N.loadLabel(colorError, "PROPERTIES.error", 24);
        Fi18N.loadLabel(colorRun, "PROPERTIES.run", 24);

        lblTransp.setText(Fi18N.get("PROPERTIES.shape.transparence"));
        lblTransp2.setText(Fi18N.get("PROPERTIES.shape.transparence"));

        lblShapeBorder.setText(Fi18N.get("PROPERTIES.shapeBorder.title"));
        sldBorderSize.setToolTipText(Fi18N.get("PROPERTIES.shapeBorder.help"));
        sldBorderSize.setValue(FProperties.BORDER_SIZE);

        lblArrowLenght.setText(Fi18N.get("PROPERTIES.arrowLenght.title"));
        sldArrowLenght.setToolTipText(Fi18N.get("PROPERTIES.arrowLenght.help"));
        sldArrowLenght.setValue((int) (FProperties.ARROW_LENGHT_RATIO * 10));

        lblSpaceBetween.setText(Fi18N.get("PROPERTIES.space.title"));
        lblSpaceBetween.setToolTipText(Fi18N.get("PROPERTIES.space.help"));
        sldSpace.setValue((int) (FProperties.SPACE_BETWEEN_LEVELS));

        //load Fonts 
        GraphicsEnvironment e = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        DefaultListModel fontNameListModel = new DefaultListModel();
        for (String name : e.getAvailableFontFamilyNames()) {
            fontNameListModel.addElement(name);
        }
        lstFontName.setModel(fontNameListModel);
        lblDemo.setText(Fi18N.get("FLOWCHART.application.title"));

        Fi18N.loadLabel(txtColorBackground, "PROPERTIES.background");
        txtColorBackground.setBackground(FProperties.getColor(null));
        Fi18N.loadLabel(txtColorFunction, "PROPERTIES.function");
        Fi18N.loadLabel(txtColorInteger, "PROPERTIES.int");
        Fi18N.loadLabel(txtColorKeyword, "PROPERTIES.keyword");
        Fi18N.loadLabel(txtColorLogic, "PROPERTIES.logic");

        Fi18N.loadLabel(txtColorNormal, "PROPERTIES.normal");

        Fi18N.loadLabel(txtColorOperator, "PROPERTIES.operator");

        Fi18N.loadLabel(txtColorReal, "PROPERTIES.real");

        txtColorBackground.setBackground(FProperties.getColor("keySintaxBackground"));
        loadSintaxHighlight(txtColorBackground, "PROPERTIES.background", FProperties.keySintaxBackground);
        loadSintaxHighlight(txtColorFunction, "PROPERTIES.function", FProperties.keySintaxFunction);
        loadSintaxHighlight(txtColorInteger, "PROPERTIES.int", FProperties.keySintaxInteger);
        loadSintaxHighlight(txtColorKeyword, "PROPERTIES.keyword", FProperties.keySintaxKeyword);
        loadSintaxHighlight(txtColorLogic, "PROPERTIES.logic", FProperties.keySintaxLogic);
        loadSintaxHighlight(txtColorNormal, "PROPERTIES.normal", FProperties.keySintaxNormal);
        loadSintaxHighlight(txtColorOperator, "PROPERTIES.operator", FProperties.keySintaxOperator);
        loadSintaxHighlight(txtColorReal, "PROPERTIES.real", FProperties.keySintaxReal);
        loadSintaxHighlight(txtColorString, "PROPERTIES.string", FProperties.keySintaxString);

        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: TAB USERNAME
        UserName user = FProperties.getUser();
        pnUserAvatar.setBorder(BorderFactory.createTitledBorder(user.getName()));

        Fi18N.loadLabel(lblUserFullName, "PROPERTIES.userFullName");
        txtUserFullName.setText(user.getFullName());

        Fi18N.loadLabel(lblUserNumber, "PROPERTIES.userNumber");
        txtUserNumber.setText(user.getCode());
        Fi18N.loadLabel(lblUserPassword1, "PROPERTIES.userNewPassword1");
        txtPassword1.setText(user.getPassword());
        Fi18N.loadLabel(lblUserPassword2, "PROPERTIES.userNewPassword2");
        txtPassword2.setText(user.getPassword());

        lblAvatar.setIcon(user.getAvatar());
        Fi18N.loadButton(btDelete, "LAUNCHER.deleteUser", 24);

        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: TAB language
        lstLang.setBorder(javax.swing.BorderFactory.createTitledBorder(Fi18N.get("PROPERTIES.selectLang.title")));
        lstLang.setToolTipText(Fi18N.get("PROPERTIES.selectLang.help"));

    }

    public void loadSintaxHighlight(JLabel sintax, String guiKey, String propKey) {
        Fi18N.loadLabel(sintax, guiKey, 16);
        sintax.setFont(myFont);
        sintax.setForeground(FProperties.getColor(propKey));
        sintax.setBackground(txtColorBackground.getBackground());
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
        lblDemo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btOk = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        tpProperties = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        lblUserFullName = new javax.swing.JLabel();
        txtUserNumber = new javax.swing.JTextField();
        lblUserNumber = new javax.swing.JLabel();
        lblUserPassword1 = new javax.swing.JLabel();
        txtPassword1 = new javax.swing.JPasswordField();
        lblUserPassword2 = new javax.swing.JLabel();
        txtPassword2 = new javax.swing.JPasswordField();
        txtUserFullName = new javax.swing.JTextField();
        pnUserAvatar = new javax.swing.JPanel();
        lblAvatar = new javax.swing.JLabel();
        btDelete = new javax.swing.JButton();
        pnShapes = new javax.swing.JPanel();
        transShapes = new javax.swing.JSlider();
        lblTransp = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        colorTerminator = new javax.swing.JLabel();
        colorDefine = new javax.swing.JLabel();
        colorWrite = new javax.swing.JLabel();
        colorRead = new javax.swing.JLabel();
        colorExecute = new javax.swing.JLabel();
        colorIteration = new javax.swing.JLabel();
        colorConnector = new javax.swing.JLabel();
        colorDecision = new javax.swing.JLabel();
        colorJump = new javax.swing.JLabel();
        colorReturn = new javax.swing.JLabel();
        pnShow = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        colorArrow = new javax.swing.JLabel();
        colorSelected = new javax.swing.JLabel();
        colorRun = new javax.swing.JLabel();
        colorError = new javax.swing.JLabel();
        lblTransp2 = new javax.swing.JLabel();
        transpView = new javax.swing.JSlider();
        jPanel4 = new javax.swing.JPanel();
        lblShapeBorder = new javax.swing.JLabel();
        sldBorderSize = new javax.swing.JSlider();
        lblBorderSizeValue = new javax.swing.JLabel();
        lblArrowLenght = new javax.swing.JLabel();
        sldArrowLenght = new javax.swing.JSlider();
        lblArrowLenghtValue = new javax.swing.JLabel();
        lblSpaceBetween = new javax.swing.JLabel();
        sldSpace = new javax.swing.JSlider();
        lblSpaceValue = new javax.swing.JLabel();
        pnFont = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstFontName = new javax.swing.JList();
        jPanel8 = new javax.swing.JPanel();
        txtColorBackground = new javax.swing.JLabel();
        txtColorNormal = new javax.swing.JLabel();
        txtColorKeyword = new javax.swing.JLabel();
        txtColorString = new javax.swing.JLabel();
        txtColorReal = new javax.swing.JLabel();
        txtColorInteger = new javax.swing.JLabel();
        txtColorLogic = new javax.swing.JLabel();
        txtColorOperator = new javax.swing.JLabel();
        txtColorFunction = new javax.swing.JLabel();
        txtColorComments = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        cbBold = new javax.swing.JCheckBox();
        cbItalic = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        lblSize = new javax.swing.JLabel();
        spSize = new javax.swing.JSpinner();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstLang = new javax.swing.JList();
        Lnguagem = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtKeyWords = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtLanguage = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtEditor = new javax.swing.JTextArea();

        jLabel1.setText("jLabel1");

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel6.setLayout(new java.awt.BorderLayout());

        lblDemo.setText("Inicio \"ola mundo\" fim");
        jPanel6.add(lblDemo, java.awt.BorderLayout.CENTER);

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setLayout(new java.awt.GridLayout(1, 2, 10, 10));

        btOk.setText("ok");
        btOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOkActionPerformed(evt);
            }
        });
        jPanel3.add(btOk);

        btCancel.setText("Cancel");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });
        jPanel3.add(btCancel);

        lblUserFullName.setText("name");

        txtUserNumber.setText("jTextField2");

        lblUserNumber.setText("Number");

        lblUserPassword1.setText("New Password");

        lblUserPassword2.setText("Repeat Password");

        txtUserFullName.setText("jTextField1");

        pnUserAvatar.setBorder(javax.swing.BorderFactory.createTitledBorder("username"));
        pnUserAvatar.setLayout(new java.awt.BorderLayout());

        lblAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAvatarMouseClicked(evt);
            }
        });
        pnUserAvatar.add(lblAvatar, java.awt.BorderLayout.CENTER);

        btDelete.setText("Delete");
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(pnUserAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUserNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUserNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblUserFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 99, Short.MAX_VALUE))
                            .addComponent(txtUserFullName)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUserPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUserPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword1)
                            .addComponent(txtPassword2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblUserNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUserFullName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnUserAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserPassword1)
                    .addComponent(txtPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserPassword2)
                    .addComponent(txtPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btDelete)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        tpProperties.addTab("UserName", jPanel10);

        pnShapes.setBackground(new java.awt.Color(204, 204, 204));
        pnShapes.setFocusable(false);
        pnShapes.setOpaque(false);

        transShapes.setMaximum(255);
        transShapes.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                transShapesStateChanged(evt);
            }
        });

        lblTransp.setText("transp");

        jPanel1.setLayout(new java.awt.GridLayout(5, 2, 5, 5));

        colorTerminator.setText("Terminator");
        colorTerminator.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorTerminator.setFocusable(false);
        colorTerminator.setOpaque(true);
        colorTerminator.setRequestFocusEnabled(false);
        colorTerminator.setVerifyInputWhenFocusTarget(false);
        colorTerminator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });
        jPanel1.add(colorTerminator);

        colorDefine.setText("Define");
        colorDefine.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorDefine.setOpaque(true);
        colorDefine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });
        jPanel1.add(colorDefine);

        colorWrite.setText("Write");
        colorWrite.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorWrite.setOpaque(true);
        colorWrite.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });
        jPanel1.add(colorWrite);

        colorRead.setText("read");
        colorRead.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorRead.setOpaque(true);
        colorRead.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });
        jPanel1.add(colorRead);

        colorExecute.setText("Execute");
        colorExecute.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorExecute.setOpaque(true);
        colorExecute.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });
        jPanel1.add(colorExecute);

        colorIteration.setText("for");
        colorIteration.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorIteration.setOpaque(true);
        colorIteration.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });
        jPanel1.add(colorIteration);

        colorConnector.setText("Connector");
        colorConnector.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorConnector.setOpaque(true);
        colorConnector.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });
        jPanel1.add(colorConnector);

        colorDecision.setText("Decision");
        colorDecision.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorDecision.setOpaque(true);
        colorDecision.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });
        jPanel1.add(colorDecision);

        colorJump.setText("Jump");
        colorJump.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorJump.setOpaque(true);
        colorJump.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });
        jPanel1.add(colorJump);

        colorReturn.setText("Return");
        colorReturn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorReturn.setOpaque(true);
        colorReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });
        jPanel1.add(colorReturn);

        javax.swing.GroupLayout pnShapesLayout = new javax.swing.GroupLayout(pnShapes);
        pnShapes.setLayout(pnShapesLayout);
        pnShapesLayout.setHorizontalGroup(
            pnShapesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnShapesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnShapesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnShapesLayout.createSequentialGroup()
                        .addComponent(lblTransp, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transShapes, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 32, Short.MAX_VALUE))
        );
        pnShapesLayout.setVerticalGroup(
            pnShapesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnShapesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnShapesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(transShapes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTransp))
                .addContainerGap())
        );

        tpProperties.addTab("Formas", pnShapes);

        pnShow.setBackground(new java.awt.Color(204, 204, 204));
        pnShow.setFocusable(false);
        pnShow.setOpaque(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        colorArrow.setText("Arrow");
        colorArrow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorArrow.setFocusable(false);
        colorArrow.setOpaque(true);
        colorArrow.setRequestFocusEnabled(false);
        colorArrow.setVerifyInputWhenFocusTarget(false);
        colorArrow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });

        colorSelected.setText("Selected");
        colorSelected.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorSelected.setFocusable(false);
        colorSelected.setOpaque(true);
        colorSelected.setRequestFocusEnabled(false);
        colorSelected.setVerifyInputWhenFocusTarget(false);
        colorSelected.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });

        colorRun.setText("run");
        colorRun.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorRun.setFocusable(false);
        colorRun.setOpaque(true);
        colorRun.setRequestFocusEnabled(false);
        colorRun.setVerifyInputWhenFocusTarget(false);
        colorRun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });

        colorError.setText("error");
        colorError.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorError.setFocusable(false);
        colorError.setOpaque(true);
        colorError.setRequestFocusEnabled(false);
        colorError.setVerifyInputWhenFocusTarget(false);
        colorError.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseColorEvent(evt);
            }
        });

        lblTransp2.setText("transp");

        transpView.setMaximum(255);
        transpView.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                transpViewStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTransp2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transpView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(colorArrow, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(colorError, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(colorSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(colorRun, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorArrow, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorError, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorRun, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(transpView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTransp2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblShapeBorder.setText("shapeBorde");

        sldBorderSize.setMaximum(20);
        sldBorderSize.setValue(5);
        sldBorderSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldBorderSizeStateChanged(evt);
            }
        });

        lblBorderSizeValue.setText("jLabel1");

        lblArrowLenght.setText("ArrowLenght");

        sldArrowLenght.setMaximum(50);
        sldArrowLenght.setMinimum(5);
        sldArrowLenght.setValue(15);
        sldArrowLenght.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldArrowLenghtStateChanged(evt);
            }
        });

        lblArrowLenghtValue.setText("jLabel1");

        lblSpaceBetween.setText("Space");

        sldSpace.setMaximum(50);
        sldSpace.setValue(15);
        sldSpace.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldSpaceStateChanged(evt);
            }
        });

        lblSpaceValue.setText("jLabel1");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblShapeBorder, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblArrowLenght, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSpaceBetween, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sldArrowLenght, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(sldBorderSize, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(sldSpace, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSpaceValue)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblBorderSizeValue, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                        .addComponent(lblArrowLenghtValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBorderSizeValue)
                    .addComponent(sldBorderSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShapeBorder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblArrowLenghtValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblArrowLenght, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sldArrowLenght, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSpaceValue)
                    .addComponent(sldSpace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSpaceBetween))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnShowLayout = new javax.swing.GroupLayout(pnShow);
        pnShow.setLayout(pnShowLayout);
        pnShowLayout.setHorizontalGroup(
            pnShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnShowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnShowLayout.setVerticalGroup(
            pnShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnShowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tpProperties.addTab("Formas", pnShow);

        pnFont.setLayout(new java.awt.BorderLayout());

        lstFontName.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstFontName.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstFontNameValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstFontName);

        pnFont.add(jScrollPane1, java.awt.BorderLayout.NORTH);

        jPanel8.setLayout(new java.awt.GridLayout(2, 5, 5, 5));

        txtColorBackground.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtColorBackground.setText("background");
        txtColorBackground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtColorBackground.setOpaque(true);
        txtColorBackground.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtColorBackgroundMouseClicked(evt);
            }
        });
        jPanel8.add(txtColorBackground);

        txtColorNormal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtColorNormal.setText("Normal");
        txtColorNormal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtColorNormal.setOpaque(true);
        txtColorNormal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtColorMouseClicked(evt);
            }
        });
        jPanel8.add(txtColorNormal);

        txtColorKeyword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtColorKeyword.setText("Keyword");
        txtColorKeyword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtColorKeyword.setOpaque(true);
        txtColorKeyword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtColorMouseClicked(evt);
            }
        });
        jPanel8.add(txtColorKeyword);

        txtColorString.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtColorString.setText("String");
        txtColorString.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtColorString.setOpaque(true);
        txtColorString.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtColorMouseClicked(evt);
            }
        });
        jPanel8.add(txtColorString);

        txtColorReal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtColorReal.setText("Real");
        txtColorReal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtColorReal.setOpaque(true);
        txtColorReal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtColorMouseClicked(evt);
            }
        });
        jPanel8.add(txtColorReal);

        txtColorInteger.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtColorInteger.setText("Integer");
        txtColorInteger.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtColorInteger.setOpaque(true);
        txtColorInteger.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtColorMouseClicked(evt);
            }
        });
        jPanel8.add(txtColorInteger);

        txtColorLogic.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtColorLogic.setText("Logic");
        txtColorLogic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtColorLogic.setOpaque(true);
        txtColorLogic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtColorMouseClicked(evt);
            }
        });
        jPanel8.add(txtColorLogic);

        txtColorOperator.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtColorOperator.setText("Operator");
        txtColorOperator.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtColorOperator.setOpaque(true);
        txtColorOperator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtColorMouseClicked(evt);
            }
        });
        jPanel8.add(txtColorOperator);

        txtColorFunction.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtColorFunction.setText("Function");
        txtColorFunction.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtColorFunction.setOpaque(true);
        txtColorFunction.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtColorMouseClicked(evt);
            }
        });
        jPanel8.add(txtColorFunction);

        txtColorComments.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtColorComments.setText("Comments");
        txtColorComments.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtColorComments.setOpaque(true);
        txtColorComments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtColorMouseClicked(evt);
            }
        });
        jPanel8.add(txtColorComments);

        pnFont.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel9.setLayout(new java.awt.GridLayout(1, 3, 5, 10));

        cbBold.setText("Bold");
        cbBold.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbBoldItemStateChanged(evt);
            }
        });
        jPanel9.add(cbBold);

        cbItalic.setText("italic");
        cbItalic.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbItalicItemStateChanged(evt);
            }
        });
        jPanel9.add(cbItalic);

        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        lblSize.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSize.setText("size");
        jPanel5.add(lblSize);

        spSize.setModel(new javax.swing.SpinnerNumberModel(14, 5, null, 1));
        spSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spSizeStateChanged(evt);
            }
        });
        jPanel5.add(spSize);

        jPanel9.add(jPanel5);

        pnFont.add(jPanel9, java.awt.BorderLayout.SOUTH);

        tpProperties.addTab("Fonte", pnFont);

        lstLang.setBorder(javax.swing.BorderFactory.createTitledBorder("Lingua"));
        lstLang.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "pt_PT", "en_US" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstLang.setSelectedIndex(0);
        jScrollPane2.setViewportView(lstLang);

        txtKeyWords.setEditable(false);
        txtKeyWords.setColumns(20);
        txtKeyWords.setRows(5);
        jScrollPane3.setViewportView(txtKeyWords);

        Lnguagem.addTab("Palavras ", jScrollPane3);

        txtLanguage.setEditable(false);
        txtLanguage.setColumns(20);
        txtLanguage.setRows(5);
        jScrollPane4.setViewportView(txtLanguage);

        Lnguagem.addTab("Linguagem", jScrollPane4);

        txtEditor.setEditable(false);
        txtEditor.setColumns(20);
        txtEditor.setRows(5);
        jScrollPane5.setViewportView(txtEditor);

        Lnguagem.addTab("Editor", jScrollPane5);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Lnguagem, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Lnguagem)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)))
        );

        tpProperties.addTab("linguagem", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpProperties, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(tpProperties, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed

        FProperties.setLanguageSufix(lstLang.getSelectedValue().toString());
        FProperties.setColor(FProperties.terminatorColorkey, colorTerminator.getBackground());
        FProperties.setColor(FProperties.writeColorkey, colorWrite.getBackground());
        FProperties.setColor(FProperties.readColorkey, colorRead.getBackground());
        FProperties.setColor(FProperties.executeColorkey, colorExecute.getBackground());
        FProperties.setColor(FProperties.defineColorkey, colorDefine.getBackground());
        FProperties.setColor(FProperties.jumpColorkey, colorJump.getBackground());
        FProperties.setColor(FProperties.returnColorkey, colorReturn.getBackground());
        FProperties.setColor(FProperties.decisionColorkey, colorDecision.getBackground());
        FProperties.setColor(FProperties.connectorColorkey, colorConnector.getBackground());
        FProperties.setColor(FProperties.iterationColorkey, colorIteration.getBackground());

        FProperties.setColor(FProperties.selectedColorKey, colorSelected.getBackground());
        FProperties.setColor(FProperties.runColorKey, colorRun.getBackground());
        FProperties.setColor(FProperties.errorColorKey, colorError.getBackground());
        FProperties.setColor(FProperties.arrowColorkey, colorArrow.getBackground());
        //-------------------------------------------------------------------------------SYNTAX HIGHLIGHT
        FProperties.setColor(FProperties.keySintaxBackground, txtColorBackground.getBackground());
        FProperties.setColor(FProperties.keySintaxFunction, txtColorFunction.getForeground());
        FProperties.setColor(FProperties.keySintaxInteger, txtColorInteger.getForeground());
        FProperties.setColor(FProperties.keySintaxKeyword, txtColorKeyword.getForeground());
        FProperties.setColor(FProperties.keySintaxLogic, txtColorLogic.getForeground());
        FProperties.setColor(FProperties.keySintaxNormal, txtColorNormal.getForeground());
        FProperties.setColor(FProperties.keySintaxOperator, txtColorOperator.getForeground());
        FProperties.setColor(FProperties.keySintaxReal, txtColorReal.getForeground());
        FProperties.setColor(FProperties.keySintaxString, txtColorString.getForeground());
        FProperties.setColor(FProperties.keySintaxComments, txtColorComments.getForeground());

        FProperties.setMinBorder(sldBorderSize.getValue());
        FProperties.setArrowLength(sldArrowLenght.getValue() / 10.0);
        FProperties.setSPACE_BETWEEN_LEVELS(sldSpace.getValue());
        FProperties.setFont(getMyFont());
        UserName user = FProperties.getUser();
        if (!Crypt.isEqual(txtPassword1.getPassword(), txtPassword2.getPassword())) {
            Fdialog.showMessage(Fi18N.get("PROPERTIES.passwordNotMatch"));
        } else {
            user.setPassword(txtPassword1.getPassword());
        }
        user.setFullName(txtUserFullName.getText());
        user.setCode(txtUserNumber.getText());
        user.setAvatar((ImageIcon) lblAvatar.getIcon());
        FProperties.setUser(user);
        FProperties.save();
        FProperties.updateSystemProperties();
        isCanceled = false;
        setVisible(false);
    }//GEN-LAST:event_btOkActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        FProperties.reLoad(); // load old values
        isCanceled = true;
        setVisible(false);
    }//GEN-LAST:event_btCancelActionPerformed

    private void lstFontNameValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstFontNameValueChanged
        setSintaxHihlightFont(getMyFont());
    }//GEN-LAST:event_lstFontNameValueChanged

    private void cbItalicItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbItalicItemStateChanged
        setSintaxHihlightFont(getMyFont());
    }//GEN-LAST:event_cbItalicItemStateChanged

    private void cbBoldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbBoldItemStateChanged
        setSintaxHihlightFont(getMyFont());
    }//GEN-LAST:event_cbBoldItemStateChanged

    private void transShapesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_transShapesStateChanged
        int alpha = transShapes.getValue();
        updateTransparency(colorTerminator, alpha);
        updateTransparency(colorWrite, alpha);
        updateTransparency(colorRead, alpha);
        updateTransparency(colorExecute, alpha);
        updateTransparency(colorDefine, alpha);
        updateTransparency(colorJump, alpha);
        updateTransparency(colorReturn, alpha);
        updateTransparency(colorIteration, alpha);
        updateTransparency(colorConnector, alpha);
        updateTransparency(colorDecision, alpha);
        repaint();
    }//GEN-LAST:event_transShapesStateChanged

    private void chooseColorEvent(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseColorEvent
        JLabel label = (JLabel) evt.getSource();
        Color newColor = JColorChooser.showDialog(this, label.getText(), label.getBackground());
        if (newColor != null) {
            label.setBackground(newColor);
        }
        repaint();
    }//GEN-LAST:event_chooseColorEvent

    private void transpViewStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_transpViewStateChanged
        int alpha = transpView.getValue();
        updateTransparency(colorArrow, alpha);
        updateTransparency(colorRun, alpha);
        updateTransparency(colorError, alpha);
        updateTransparency(colorSelected, alpha);
        repaint();
    }//GEN-LAST:event_transpViewStateChanged

    private void sldArrowLenghtStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldArrowLenghtStateChanged
        lblArrowLenghtValue.setText(sldArrowLenght.getValue() + "");
    }//GEN-LAST:event_sldArrowLenghtStateChanged

    private void sldBorderSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldBorderSizeStateChanged
        lblBorderSizeValue.setText(sldBorderSize.getValue() + "");
    }//GEN-LAST:event_sldBorderSizeStateChanged

    private void sldSpaceStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldSpaceStateChanged
        lblSpaceValue.setText(sldSpace.getValue() + "");
    }//GEN-LAST:event_sldSpaceStateChanged

    private void txtColorBackgroundMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtColorBackgroundMouseClicked
        JLabel label = (JLabel) evt.getSource();
        Color newColor = JColorChooser.showDialog(this, label.getText(), label.getBackground());
        if (newColor != null) {
            label.setBackground(newColor);
            txtColorBackground.setForeground(txtColorNormal.getForeground());
            txtColorBackground.setBackground(newColor);
            txtColorNormal.setBackground(newColor);
            txtColorFunction.setBackground(newColor);
            txtColorInteger.setBackground(newColor);
            txtColorKeyword.setBackground(newColor);
            txtColorLogic.setBackground(newColor);
            txtColorOperator.setBackground(newColor);
            txtColorReal.setBackground(newColor);
            txtColorString.setBackground(newColor);
        }
    }//GEN-LAST:event_txtColorBackgroundMouseClicked

    public void setSintaxHihlightFont(Font f) {
        txtColorBackground.setForeground(txtColorNormal.getForeground());
        txtColorBackground.setFont(f);
        txtColorNormal.setFont(f);
        txtColorFunction.setFont(f);
        txtColorKeyword.setFont(f);
        txtColorInteger.setFont(f);
        txtColorLogic.setFont(f);
        txtColorOperator.setFont(f);
        txtColorReal.setFont(f);
        txtColorString.setFont(f);
    }


    private void txtColorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtColorMouseClicked
        JLabel label = (JLabel) evt.getSource();
        Color newColor = JColorChooser.showDialog(this, label.getText(), label.getBackground());
        if (newColor != null) {
            label.setForeground(newColor);
        }
    }//GEN-LAST:event_txtColorMouseClicked

    private void spSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spSizeStateChanged
        setSintaxHihlightFont(getMyFont());
    }//GEN-LAST:event_spSizeStateChanged

    private void lblAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAvatarMouseClicked
        ImageIcon img = ImageUtils.selectImageFromFile();
        if (img != null) {
            user.setAvatar(img);
            lblAvatar.setIcon(user.getAvatar());
        }
    }//GEN-LAST:event_lblAvatarMouseClicked

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        int resp = FMessages.dialogYesNo(
                Fi18N.get("PROPERTIES.deleteUser.title"),
                Fi18N.get("PROPERTIES.deleteUser.help"),
                (ImageIcon) lblAvatar.getIcon());
        if (resp == 0) {
            FProperties.delete();
            System.exit(0);
        }
    }//GEN-LAST:event_btDeleteActionPerformed

    private void updateTransparency(JLabel label, int alpha) {
        Color oldColor = label.getBackground();
        label.setBackground(new Color(
                oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue(),
                alpha
        ));
        label.repaint();

    }

    private void setMyFont(Font f) {
        lstFontName.setSelectedValue(f.getFamily(), true);
        cbBold.setSelected(f.isBold());
        cbItalic.setSelected(f.isItalic());
        spSize.setValue(f.getSize());
    }

    private Font getMyFont() {
        return new Font(
                lstFontName.getSelectedValue().toString(),
                (cbBold.isSelected() ? Font.BOLD : 0)
                + (cbItalic.isSelected() ? Font.ITALIC : 0),
                (Integer) spSize.getValue());

    }

    private void loadProperties() {
        lstLang.setSelectedValue(FProperties.getLanguageSufix(), true);
        colorTerminator.setBackground(FProperties.terminatorColor);
        colorWrite.setBackground(FProperties.writeColor);
        colorRead.setBackground(FProperties.readColor);
        colorDefine.setBackground(FProperties.defineColor);
        colorExecute.setBackground(FProperties.executeColor);
        colorJump.setBackground(FProperties.jumpColor);
        colorReturn.setBackground(FProperties.returnColor);
        colorIteration.setBackground(FProperties.iterationColor);
        colorConnector.setBackground(FProperties.connectorColor);
        colorDecision.setBackground(FProperties.decisionColor);

        colorArrow.setBackground(FProperties.arrowColor);
        colorSelected.setBackground(FProperties.selectedColor);
        colorRun.setBackground(FProperties.runColor);
        colorError.setBackground(FProperties.errorColor);

        setMyFont(FProperties.font);
        transShapes.setValue(FProperties.terminatorColor.getAlpha());
        user = FProperties.getUser();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Lnguagem;
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btOk;
    private javax.swing.JCheckBox cbBold;
    private javax.swing.JCheckBox cbItalic;
    private javax.swing.JLabel colorArrow;
    private javax.swing.JLabel colorConnector;
    private javax.swing.JLabel colorDecision;
    private javax.swing.JLabel colorDefine;
    private javax.swing.JLabel colorError;
    private javax.swing.JLabel colorExecute;
    private javax.swing.JLabel colorIteration;
    private javax.swing.JLabel colorJump;
    private javax.swing.JLabel colorRead;
    private javax.swing.JLabel colorReturn;
    private javax.swing.JLabel colorRun;
    private javax.swing.JLabel colorSelected;
    private javax.swing.JLabel colorTerminator;
    private javax.swing.JLabel colorWrite;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblArrowLenght;
    private javax.swing.JLabel lblArrowLenghtValue;
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JLabel lblBorderSizeValue;
    private javax.swing.JLabel lblDemo;
    private javax.swing.JLabel lblShapeBorder;
    private javax.swing.JLabel lblSize;
    private javax.swing.JLabel lblSpaceBetween;
    private javax.swing.JLabel lblSpaceValue;
    private javax.swing.JLabel lblTransp;
    private javax.swing.JLabel lblTransp2;
    private javax.swing.JLabel lblUserFullName;
    private javax.swing.JLabel lblUserNumber;
    private javax.swing.JLabel lblUserPassword1;
    private javax.swing.JLabel lblUserPassword2;
    private javax.swing.JList lstFontName;
    private javax.swing.JList lstLang;
    private javax.swing.JPanel pnFont;
    private javax.swing.JPanel pnShapes;
    private javax.swing.JPanel pnShow;
    private javax.swing.JPanel pnUserAvatar;
    private javax.swing.JSlider sldArrowLenght;
    private javax.swing.JSlider sldBorderSize;
    private javax.swing.JSlider sldSpace;
    private javax.swing.JSpinner spSize;
    private javax.swing.JTabbedPane tpProperties;
    private javax.swing.JSlider transShapes;
    private javax.swing.JSlider transpView;
    private javax.swing.JLabel txtColorBackground;
    private javax.swing.JLabel txtColorComments;
    private javax.swing.JLabel txtColorFunction;
    private javax.swing.JLabel txtColorInteger;
    private javax.swing.JLabel txtColorKeyword;
    private javax.swing.JLabel txtColorLogic;
    private javax.swing.JLabel txtColorNormal;
    private javax.swing.JLabel txtColorOperator;
    private javax.swing.JLabel txtColorReal;
    private javax.swing.JLabel txtColorString;
    private javax.swing.JTextArea txtEditor;
    private javax.swing.JTextArea txtKeyWords;
    private javax.swing.JTextArea txtLanguage;
    private javax.swing.JPasswordField txtPassword1;
    private javax.swing.JPasswordField txtPassword2;
    private javax.swing.JTextField txtUserFullName;
    private javax.swing.JTextField txtUserNumber;
    // End of variables declaration//GEN-END:variables
}
