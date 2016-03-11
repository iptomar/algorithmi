/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.flowchart.dialogs;

import i18n.Fi18N;
import core.data.exception.FlowchartException;
import flowchart.utils.Theme;
import i18n.EditorI18N;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author zulu
 */
public class Fdialog {

    
    public static void compileException(FlowchartException ex) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(ex.getHtmlMessage()), BorderLayout.CENTER);
        //icon of exception
        ImageIcon icon = Fi18N.loadIcon(Fi18N.get("DIALOG.exception.icon"), 96);
        panel.add(new JLabel(icon), BorderLayout.WEST);
        JOptionPane.showMessageDialog(null,
                panel,
                Fi18N.get("DIALOG.exception.title"),
                JOptionPane.PLAIN_MESSAGE
        );
    }
    
  
    public static void compileException(Exception ex) {
        compileException(new FlowchartException(ex));

    }

    public static void showKeyMessage(String key, String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(Theme.toHtml(message)), BorderLayout.CENTER);
        //icon of information
        ImageIcon icon = Fi18N.loadIcon(Fi18N.get(key + ".icon"), 96);
        panel.add(new JLabel(icon), BorderLayout.WEST);
        JOptionPane.showMessageDialog(null,
                panel,
                Fi18N.get(key + ".title"),
                JOptionPane.PLAIN_MESSAGE
        );

    }

    public static void showKeyMessage(String key) {
        Fdialog.showKeyMessage(key, Fi18N.get(key + ".message"));
    }

    public static void showKeyMessage(String key, String[] args) {
        String message = Fi18N.get(key + ".message");
        message = Theme.replaceParameters(message, args);
        Fdialog.showKeyMessage(key, message);
    }
    
    public static void showEditorKeyMessage(String key, String[] args) {
        String message = EditorI18N.get(key + ".message");
        message = Theme.replaceParameters(message, args);
        Fdialog.showKeyMessage(key, message);
    }
    
    
    public static void showMessage(String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(Theme.toHtml(message)), BorderLayout.CENTER);
        //icon of information
        ImageIcon icon = Fi18N.loadIcon(Fi18N.get("DIALOG.flowchartMenu.icon"), 96);
        panel.add(new JLabel(icon), BorderLayout.WEST);
        JOptionPane.showMessageDialog(null,
                panel,
                Fi18N.get("FLOWCHART.application.title"),
                JOptionPane.PLAIN_MESSAGE
        );

    }
  
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510191708L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////


}
