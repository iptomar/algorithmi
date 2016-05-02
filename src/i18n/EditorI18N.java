///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2015                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****     This software was build with the purpose of investigate and    ****/
///****     learning.                                                      ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package i18n;

import ui.FLog;
import flowchart.utils.TextUtils;
import flowchart.utils.Theme;
import java.awt.Image;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/**
 *
 * @author SZULU
 */
public class EditorI18N {

    private static String MESSAGE_FILE = "i18n.Editor";
    static ResourceBundle resourcesI18n;
    static String language = "pt";
    static String country = "PT";

    static {
        resourcesI18n = ResourceBundle.getBundle(MESSAGE_FILE, new Locale(language, country));
    }

    public static void load(String lang, String land) {
        //do not reload properties
        if (!country.equals(land) || !language.equals(lang)) {
            language = lang;
            country = land;
            resourcesI18n = ResourceBundle.getBundle(MESSAGE_FILE, new Locale(language, country));
        }
    }

    public static final String get(String key) {
        try {
            //Log.printLn(EditorI18N.class.getName() + "  get " + key);
            return resourcesI18n.getString(key.trim()).trim();
        } catch (Exception e) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            FLog.printLn(EditorI18N.class.getName() + "  get Key[" + key + "] Error " + e.getMessage());
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            return null;
        }

    }

    public static final String get(String key, String[] params) {
        // FLog.printLn(EditorI18N.class.getName() + "  get " + key + " params " + Arrays.toString(params));
        return Theme.htmlParameters(key, resourcesI18n.getString(key).trim(), params);
    }

    public static final String get(String key, String p1) {
        return get(key, new String[]{p1});
    }

    public static final String get(String key, String p1, String p2) {
        return get(key, new String[]{p1, p2});
    }

    public static final String get(String key, String p1, String p2, String p3) {
        return get(key, new String[]{p1, p2, p3});
    }

    public static void loadIcon(AbstractButton bt, String icon, int size) {
        ImageIcon imgIc = new ImageIcon(EditorI18N.class.getResource(Fi18N.IMG_LOCATION + icon));
        Image newimg = imgIc.getImage().getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
        bt.setIcon(new ImageIcon(newimg));
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
        //Log.printLn(EditorI18N.class.getName() + " LoadIcon " + icon + " size " + size);
    }

    public static ImageIcon loadIcon(String key, int size) {
        String icon = EditorI18N.get(key);
        ImageIcon iconImage = new ImageIcon(EditorI18N.class.getResource(Fi18N.IMG_LOCATION + icon));
        //calcular width
        double ratio = (double) iconImage.getIconWidth() / (double) iconImage.getIconHeight();
        int width = (int) (ratio * size);
        //resize icon
        Image newimg = iconImage.getImage().getScaledInstance(width, size, java.awt.Image.SCALE_SMOOTH);
        iconImage.setImage(newimg);

        // FLog.printLn("LoadIcon  Key " + key + " size " + size);
        return iconImage;
    }

    public static void loadResource(AbstractButton item, String key) {
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
        // FLog.printLn(EditorI18N.class.getName() + " loadResource " + key);
        key = key.trim();
        try {
            item.setText(get(key).trim());
        } catch (Exception ex) {
            item.setText(get(key + ".title").trim());
        }
        try {
            item.setToolTipText(resourcesI18n.getString(key + ".help").trim());
        } catch (Exception ex) {
        }
        try {
            loadIcon(item, resourcesI18n.getString(key + ".icon"), 16);
        } catch (Exception ex) {
        }
    }

    public static void loadLabel(JLabel item, String key, int size) {
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
        // FLog.printLn(EditorI18N.class.getName() + " loadResource " + key);
        key = key.trim();
        try {
            item.setText(get(key).trim());
        } catch (Exception ex) {
            item.setText(get(key + ".title").trim());
        }
        try {
            item.setToolTipText(resourcesI18n.getString(key + ".help").trim());
        } catch (Exception ex) {
        }
        try {
            item.setIcon(loadIcon(key + ".icon", size));
        } catch (Exception ex) {
        }
    }

    public static void loadLabel(JLabel item, String key) {
        loadLabel(item, key, 16);
    }

    public static void loadResource(AbstractButton menu, AbstractButton button, String key) {
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
        //Log.printLn(EditorI18N.class.getName() + " loadResource " + key);
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
        key = key.trim();
        try {
            menu.setText(get(key));
        } catch (Exception ex) {
        }
        try {
            menu.setToolTipText(get(key + ".help"));
        } catch (Exception ex) {
        }
        try {
            loadIcon(menu, get(key + ".icon"), 16);
        } catch (Exception ex) {
        }
        try {
            button.setToolTipText(get(key + ".help"));
        } catch (Exception ex) {
        }
        try {
            loadIcon(button, get(key + ".icon"), 32);
            button.setText("");
        } catch (Exception ex) {
        }

    }

    public static void loadResource(AbstractButton button, String key, int size) {
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
        // FLog.printLn(EditorI18N.class.getName() + " loadResource " + key);
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG

        key = key.trim();
        try {
            button.setToolTipText(get(key + ".help"));
        } catch (Exception ex) {
        }
        try {
            loadIcon(button, get(key + ".icon"), size);
            button.setText("");
        } catch (Exception ex) {
        }
    }

    //----------------------------------------------------------------------------------
    public static void printLog() {
        FLog.printSeparator();
        FLog.printLn(" load " + MESSAGE_FILE + " " + resourcesI18n.getBaseBundleName());
        FLog.printSeparator();
        StringBuilder txt = new StringBuilder(System.lineSeparator());
        // get the keys
        Enumeration<String> enumeration = resourcesI18n.getKeys();
        // print all the keys
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            txt.append(TextUtils.setSize(key, 50, "_"));
            txt.append(" ");
            txt.append(resourcesI18n.getString(key));
            txt.append(System.lineSeparator());
        }
        FLog.printLn(txt.toString());
        FLog.printSeparator();
    }
    
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static void loadTab(JTabbedPane item, String key,int index,  int size) {
        key = key.trim();
        try {
            item.setTitleAt(index,get(key).trim());
        } catch (Exception ex) {
            item.setTitleAt(index,get(key + ".title").trim());
        }
        try {
            item.setToolTipTextAt(index,resourcesI18n.getString(key + ".help").trim());
        } catch (Exception ex) {
        }
        try {
            item.setIconAt(index,loadIcon(key + ".icon", size));
        } catch (Exception ex) {
        }
    }

    public static void loadTab(JTabbedPane tab,int index, String key) {
        loadTab(tab, key, index, 32);
    }
    
    //----------------------------------------------------------------------------------
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
