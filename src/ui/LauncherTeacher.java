/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import core.data.exception.FlowchartException;
import flowchart.utils.UserName;
import flowchart.utils.image.ImageUtils;
import i18n.Fi18N;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.ListModel;
import ui.dialogs.problems.ProblemEditor;
import ui.flowchart.dialogs.Fdialog;
import ui.utils.Crypt;

/**
 *
 * @author zulu
 */
public class LauncherTeacher extends javax.swing.JFrame {

    ProblemEditor problem = new ProblemEditor();
    UserName user;

    /**
     * Creates new form Launcher
     */
    public LauncherTeacher() {
        initComponents();        
        I18N();
        addNewUser();
        loadUsers();
        setLocationRelativeTo(null);
    }

    private void I18N() {
        setTitle(Fi18N.get("FLOWCHART.application.title"));
        lblFlowcharCopyright.setText(Fi18N.get("FLOWCHART.application.copyright"));

        Fi18N.loadButton(btOk, "LAUNCHER.okUser", 64);
        Fi18N.loadTabTile(tabMain, "LAUNCHER.selectUser", 0, 32);
        Fi18N.loadTabTile(tabMain, "LAUNCHER.newUser", 1, 32);
        Fi18N.loadTabTile(tabMain, "LAUNCHER.about", 2, 32);

        pnUsers.setBorder(BorderFactory.createTitledBorder(Fi18N.get("LAUNCHER.users.list")));
        pnInformation.setBorder(BorderFactory.createTitledBorder(Fi18N.get("LAUNCHER.users.info")));

        Fi18N.loadLabel(lblUserName, "LAUNCHER.name");
        Fi18N.loadLabel(lblUserFullName, "LAUNCHER.fullName");
        Fi18N.loadLabel(lblUserNumber, "LAUNCHER.number");

        Fi18N.loadLabel(lblUserNameNew, "LAUNCHER.name");
        Fi18N.loadLabel(lblUserFullNameNew, "LAUNCHER.fullName");
        Fi18N.loadLabel(lblUserNumberNew, "LAUNCHER.number");

        pnInformationNewUser.setBorder(BorderFactory.createTitledBorder(Fi18N.get("LAUNCHER.users.info")));
        txtUserNameNew.setText(Fi18N.get("LAUNCHER.newName"));
        txtUserFullNameNew.setText(Fi18N.get("LAUNCHER.newFullName"));
        txtUserNumberNew.setText(Fi18N.get("LAUNCHER.newNumber"));

    }

    public void setNewUser() {
        tabMain.getTabComponentAt(0).setEnabled(false);
        tabMain.getTabComponentAt(2).setEnabled(false);
        tabMain.setSelectedIndex(1);
        user = new UserName();
        txtUserNameNew.setText(user.getName());
        txtUserFullNameNew.setText(user.getFullName());
        txtUserNumberNew.setText(user.getCode());
        lblAvatar.setIcon(user.getAvatar());
        txtPassword1.setText(user.getPassword());
        txtPassword2.setText(user.getPassword());
        btOk.setIcon(user.getAvatar());
        txtUserNameNewCaretUpdate(null);
    }

    public void loadUsers() {
        File dir = new File(System.getProperty("user.dir") + File.separator + "bin");
        dir.mkdirs();
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".properties");
            }
        });

        DefaultListModel model = new DefaultListModel();
        for (File file : files) {
            UserName user = FProperties.loadUserName(file.getAbsolutePath());
            if (user != null) {
                model.addElement(user);
            }
        }
        lstUsers.setModel(model);
        if (model.isEmpty()) {
            setNewUser();
        } else {
            lstUsers.setSelectedIndex(0);
        }
    }

    public boolean isUsernameDuplicated(String userName) {
        ListModel model = lstUsers.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            if (userName.equalsIgnoreCase(model.getElementAt(i).toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        btOk = new javax.swing.JButton();
        tabMain = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        pnInformation = new javax.swing.JPanel();
        lblUserName = new javax.swing.JLabel();
        lblUserFullName = new javax.swing.JLabel();
        lblUserNumber = new javax.swing.JLabel();
        txtUserNumber = new javax.swing.JTextField();
        txtUserFullName = new javax.swing.JTextField();
        txtUserName = new javax.swing.JTextField();
        pnUsers = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstUsers = new javax.swing.JList();
        lblUserNumber1 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        pnInformationNewUser = new javax.swing.JPanel();
        lblUserNameNew = new javax.swing.JLabel();
        lblUserFullNameNew = new javax.swing.JLabel();
        lblUserNumberNew = new javax.swing.JLabel();
        txtUserNumberNew = new javax.swing.JTextField();
        txtUserFullNameNew = new javax.swing.JTextField();
        txtUserNameNew = new javax.swing.JTextField();
        pnNewUserAvatar = new javax.swing.JPanel();
        lblAvatar = new javax.swing.JLabel();
        txtPassword1 = new javax.swing.JPasswordField();
        lblUserPassword1 = new javax.swing.JLabel();
        lblUserPassword2 = new javax.swing.JLabel();
        txtPassword2 = new javax.swing.JPasswordField();
        pnabout = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblFlowcharCopyright = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btOk.setText("ok");
        btOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOkActionPerformed(evt);
            }
        });

        pnInformation.setBorder(javax.swing.BorderFactory.createTitledBorder("Information"));

        lblUserName.setText("name");

        lblUserFullName.setText("name");

        lblUserNumber.setText("Number");

        txtUserNumber.setEditable(false);
        txtUserNumber.setText("jTextField2");

        txtUserFullName.setEditable(false);
        txtUserFullName.setText("jTextField1");

        txtUserName.setEditable(false);
        txtUserName.setText("jTextField1");
        txtUserName.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtUserNameCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout pnInformationLayout = new javax.swing.GroupLayout(pnInformation);
        pnInformation.setLayout(pnInformationLayout);
        pnInformationLayout.setHorizontalGroup(
            pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnInformationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnInformationLayout.createSequentialGroup()
                        .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnInformationLayout.createSequentialGroup()
                        .addComponent(lblUserFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnInformationLayout.createSequentialGroup()
                        .addComponent(lblUserNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnInformationLayout.setVerticalGroup(
            pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnInformationLayout.createSequentialGroup()
                .addGroup(pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserFullName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserNumber)
                    .addComponent(txtUserNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnUsers.setBorder(javax.swing.BorderFactory.createTitledBorder("users"));
        pnUsers.setLayout(new java.awt.BorderLayout());

        lstUsers.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstUsers.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstUsersValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstUsers);

        pnUsers.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        lblUserNumber1.setText("password");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserNumber1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnUsers, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserNumber1)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        tabMain.addTab("tab1", jPanel1);

        pnInformationNewUser.setBorder(javax.swing.BorderFactory.createTitledBorder("Information"));

        lblUserNameNew.setText("name");

        lblUserFullNameNew.setText("name");

        lblUserNumberNew.setText("Number");

        txtUserNumberNew.setText("jTextField2");

        txtUserFullNameNew.setText("jTextField1");

        txtUserNameNew.setText("jTextField1");
        txtUserNameNew.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtUserNameNewCaretUpdate(evt);
            }
        });

        pnNewUserAvatar.setBorder(javax.swing.BorderFactory.createTitledBorder("Foto"));
        pnNewUserAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnNewUserAvatarMouseClicked(evt);
            }
        });
        pnNewUserAvatar.setLayout(new java.awt.BorderLayout());

        lblAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnNewUserAvatar.add(lblAvatar, java.awt.BorderLayout.CENTER);

        lblUserPassword1.setText("New Password");

        lblUserPassword2.setText("Repeat Password");

        javax.swing.GroupLayout pnInformationNewUserLayout = new javax.swing.GroupLayout(pnInformationNewUser);
        pnInformationNewUser.setLayout(pnInformationNewUserLayout);
        pnInformationNewUserLayout.setHorizontalGroup(
            pnInformationNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnInformationNewUserLayout.createSequentialGroup()
                .addGroup(pnInformationNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnInformationNewUserLayout.createSequentialGroup()
                        .addGroup(pnInformationNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnInformationNewUserLayout.createSequentialGroup()
                                .addComponent(lblUserFullNameNew, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUserNameNew, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnInformationNewUserLayout.createSequentialGroup()
                                .addComponent(lblUserNumberNew, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUserNumberNew, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 140, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnInformationNewUserLayout.createSequentialGroup()
                        .addGroup(pnInformationNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUserPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUserPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(pnInformationNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword2)
                            .addComponent(txtPassword1)))
                    .addGroup(pnInformationNewUserLayout.createSequentialGroup()
                        .addComponent(lblUserNameNew, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserFullNameNew)))
                .addGap(18, 18, 18)
                .addComponent(pnNewUserAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnInformationNewUserLayout.setVerticalGroup(
            pnInformationNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnInformationNewUserLayout.createSequentialGroup()
                .addGroup(pnInformationNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnNewUserAvatar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnInformationNewUserLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnInformationNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUserFullNameNew)
                            .addComponent(txtUserNameNew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnInformationNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUserNameNew)
                            .addComponent(txtUserFullNameNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnInformationNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUserNumberNew)
                            .addComponent(txtUserNumberNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnInformationNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUserPassword1)
                            .addComponent(txtPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnInformationNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUserPassword2)
                            .addComponent(txtPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(76, 76, 76))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnInformationNewUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnInformationNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, 235, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabMain.addTab("tab2", jPanel2);

        pnabout.setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/images/flowchart.png"))); // NOI18N
        jLabel1.setText("Algorithmi 2015");
        pnabout.add(jLabel1, java.awt.BorderLayout.CENTER);

        tabMain.addTab("tab3", pnabout);

        lblFlowcharCopyright.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btOk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tabMain, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblFlowcharCopyright, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabMain, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btOk, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFlowcharCopyright)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
        if (tabMain.getSelectedIndex() == 1) {
            if (isUsernameDuplicated(txtUserNameNew.getText())) {
                Fdialog.compileException(new FlowchartException(
                        "LAUNCHER.newUser.duplicatedName", txtUserNameNew.getText()));
                return;
            }
            if (Crypt.isEqual(txtPassword1.getPassword(), txtPassword2.getPassword())) {
                user.setAvatar((ImageIcon) lblAvatar.getIcon());
                user.setFullName(txtUserFullNameNew.getText());
                user.setName(txtUserNameNew.getText());
                user.setCode(txtUserNumberNew.getText());
                user.setPassword(txtPassword1.getPassword());
                FProperties.setUser(user);
                FProperties.save();
            } else {
                Fdialog.showMessage(Fi18N.get("PROPERTIES.passwordNotMatch"));
                return;
            }
        }//new User
        else {
            if (user.getName().equalsIgnoreCase(Fi18N.get("LAUNCHER.newName"))) {
                setNewUser();
                return;
            }
            if (!Crypt.isEqual(user.getPassword().toCharArray(), txtPassword.getPassword())) {
                Fdialog.showMessage(Fi18N.get("PROPERTIES.passwordNotMatch"));
                return;
            }
        }
        problem.setUser(user);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                problem.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_btOkActionPerformed

    private void lstUsersValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstUsersValueChanged
        if (lstUsers.getSelectedIndex() >= 0) {
            user = (UserName) lstUsers.getSelectedValue();
            FProperties.loadCrypt(user.getName());
            txtUserName.setText(user.getName());
            txtUserFullName.setText(user.getFullName());
            txtUserNumber.setText(user.getCode());
            btOk.setIcon(user.getAvatar());
        } else {
            txtUserName.setText("");
            txtUserFullName.setText("");
            txtUserNumber.setText("");
            txtPassword.setText("");
        }
    }//GEN-LAST:event_lstUsersValueChanged

    private void txtUserNameNewCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtUserNameNewCaretUpdate
        btOk.setText(txtUserNameNew.getText().trim());
    }//GEN-LAST:event_txtUserNameNewCaretUpdate

    private void txtUserNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtUserNameCaretUpdate
        btOk.setText(txtUserName.getText().trim());
    }//GEN-LAST:event_txtUserNameCaretUpdate

    private void pnNewUserAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnNewUserAvatarMouseClicked
        ImageIcon img = ImageUtils.selectImageFromFile();
        if (img != null) {
            user.setAvatar(img);
            lblAvatar.setIcon(user.getAvatar());
            btOk.setIcon(user.getAvatar());
        }

    }//GEN-LAST:event_pnNewUserAvatarMouseClicked

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
            java.util.logging.Logger.getLogger(LauncherTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LauncherTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LauncherTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LauncherTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LauncherTeacher().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JLabel lblFlowcharCopyright;
    private javax.swing.JLabel lblUserFullName;
    private javax.swing.JLabel lblUserFullNameNew;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserNameNew;
    private javax.swing.JLabel lblUserNumber;
    private javax.swing.JLabel lblUserNumber1;
    private javax.swing.JLabel lblUserNumberNew;
    private javax.swing.JLabel lblUserPassword1;
    private javax.swing.JLabel lblUserPassword2;
    private javax.swing.JList lstUsers;
    private javax.swing.JPanel pnInformation;
    private javax.swing.JPanel pnInformationNewUser;
    private javax.swing.JPanel pnNewUserAvatar;
    private javax.swing.JPanel pnUsers;
    private javax.swing.JPanel pnabout;
    private javax.swing.JTabbedPane tabMain;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPassword1;
    private javax.swing.JPasswordField txtPassword2;
    private javax.swing.JTextField txtUserFullName;
    private javax.swing.JTextField txtUserFullNameNew;
    private javax.swing.JTextField txtUserName;
    private javax.swing.JTextField txtUserNameNew;
    private javax.swing.JTextField txtUserNumber;
    private javax.swing.JTextField txtUserNumberNew;
    // End of variables declaration//GEN-END:variables

    public void addNewUser() {
        user = new UserName();
        txtUserNameNew.setText(user.getName());
        txtUserFullNameNew.setText(user.getFullName());
        txtUserNumberNew.setText(user.getCode());
        txtPassword1.setText("");
        txtPassword2.setText("");
        lblAvatar.setIcon(user.getAvatar());
    }

}