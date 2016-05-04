/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogs;

import i18n.EditorI18N;
import core.data.exception.FlowchartException;
import i18n.Fi18N;
import flowchart.utils.Theme;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author zulu
 */
public class FMessages {

    public static final String ERROR = "error";
    public static final String WARNING = "warning";
    public static final String INFO = "info";
    public static final String OK = "ok";

    /**
     * presents a message in the status label
     *
     * @param lbblStatus label to show status
     * @param type type of message ERROR WARNING INFO OK
     * @param messageKey key to message or message
     * @param params parameters to replace %1 %2 %...
     */
    public static void status(JLabel lbblStatus, String type, String messageKey, String... params) {
        int size = 24;
        String message = EditorI18N.get(messageKey);
        if (message == null) {
            message = messageKey;
        }
        lbblStatus.setText(Theme.messageHtml(message, params));
        type = type.toLowerCase().trim();
        switch (type) {
            case ERROR:
                lbblStatus.setIcon(EditorI18N.loadIcon("APPLICATION.dialog.error.icon", size));
                break;
            case WARNING:
                lbblStatus.setIcon(EditorI18N.loadIcon("APPLICATION.dialog.warning.icon", size));
                break;
            case INFO:
                lbblStatus.setIcon(EditorI18N.loadIcon("APPLICATION.dialog.info.icon", size));
                break;
            case OK:
                lbblStatus.setIcon(EditorI18N.loadIcon("APPLICATION.dialog.ok.icon", size));
                break;
        }
    }

    //---------------------------------------------------------------------------   
    //--------------------- MESSAGE --------------------------------------------- 
    //---------------------------------------------------------------------------
    public static void dialog(final String type, final String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FMessages().showMessage(type, message);
            }
        });
    }

    public static void dialogParam(String type, String key, String... msg) {
        String message = EditorI18N.get(key);
        dialog(type, Theme.messageHtml(message, msg));
    }

    //---------------------------------------------------------------------------   
    //--------------------- MESSAGE --------------------------------------------- 
    //---------------------------------------------------------------------------
    public static void flowchartException(FlowchartException ex) {
        JOptionPane.showMessageDialog(null,
                getPanel(ex.getHtmlMessage()),
                EditorI18N.get("APPLICATION.dialog.error.title"),
                JOptionPane.OK_OPTION,
                EditorI18N.loadIcon("APPLICATION.dialog.error.icon", 64));
    }

    //---------------------------------------------------------------------------   
    //--------------------- MESSAGE --------------------------------------------- 
    //---------------------------------------------------------------------------

    public static void flowchartException(Exception ex) {
        JOptionPane.showMessageDialog(null,
                getPanel(ex.getMessage()),
                EditorI18N.get("APPLICATION.dialog.error.title"),
                JOptionPane.OK_OPTION,
                EditorI18N.loadIcon("APPLICATION.dialog.error.icon", 64));
    }

    public static String inputDialog(String key, String originalTxt) {
        return (String) JOptionPane.showInputDialog(
                null,
                EditorI18N.get(key + ".title"),
                Fi18N.get("FLOWCHART.application.title"),
                JOptionPane.PLAIN_MESSAGE,
                EditorI18N.loadIcon(key + ".icon", 64),
                null,
                originalTxt);
    }

    private void showMessage(String key, String info) {
        JOptionPane.showMessageDialog(null,
                getPanel(Theme.toHtml(info)),
                EditorI18N.get("APPLICATION.dialog." + key + ".title"),
                JOptionPane.OK_OPTION,
                EditorI18N.loadIcon("APPLICATION.dialog." + key + ".icon", 64));
    }

    //---------------------------------------------------------------------------   
    //--------------------- YES NO --------------------------------------------- 
    //---------------------------------------------------------------------------    
    public static int dialogYesNo(String key, String info) {
        return dialogYesNo(
                EditorI18N.get("APPLICATION.dialog." + key + ".title"),
                info,
                EditorI18N.loadIcon("APPLICATION.dialog." + key + ".icon", 64));
    }

    public static int dialogYesNo(String title, String info, ImageIcon icon) {
        Object[] options = {
            Fi18N.get("BUTTON.accept"),
            Fi18N.get("BUTTON.cancel")};

        return JOptionPane.showOptionDialog(null,
                getPanel(Theme.toHtml(info)),
                        title,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        icon,
                        options,
                        options[1]
                );
    }

    //---------------------------------------------------------------------------   
    //--------------------- YES NO CANCEL--------------------------------------------- 
    //---------------------------------------------------------------------------    
    public static int dialogYesNoCancel(String type, String message) {
        Object[] options = {Fi18N.get("BUTTON.accept"),
            Fi18N.get("BUTTON.reject"),
            Fi18N.get("BUTTON.cancel")};
        return JOptionPane.showOptionDialog(null,
                getPanel(message),
                EditorI18N.get("APPLICATION.dialog." + type + ".title"),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                EditorI18N.loadIcon("APPLICATION.dialog." + type + ".icon", 64),
                options,
                options[1]
        );
    }

    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    private static JPanel getPanel(String info) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(info);
//        ImageIcon image = EditorI18N.loadIcon("APPLICATION.dialog.icon", 96);
//        label.setIcon(image);
        panel.add(label);

        return panel;
    }
    //--------------------------------------------------------------------------- NEW PROGRAM
    //---------------------------------------------------------------------------

   

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
