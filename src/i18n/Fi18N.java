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
package i18n;

import ui.FLog;
import flowchart.utils.TextUtils;
import flowchart.utils.Theme;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * Created on 20/set/2015, 11:54:06
 *
 * @author zulu - computer
 */
public class Fi18N {

    public static String IMG_LOCATION = "/ui/images/";
    private static String LANG_FILE = "i18n.flowchart";
    static ResourceBundle messages = null;
    static String language = "pt";
    static String country = "PT";

    static {
        init();
    }

    public static void init() {
        if (messages == null) {
            messages = ResourceBundle.getBundle(LANG_FILE, new Locale(language, country));
        }
    }

    public static void load(String lang, String land) {
        //do not reload properties
        if (!country.equals(land) || !language.equals(lang)) {
            language = lang;
            country = land;
            messages = ResourceBundle.getBundle(LANG_FILE, new Locale(language, country));
            //Load Language
            FkeyWord.load(lang, land);
        }
    }

    public static final String get(String key) {
        try {
            return messages.getString(key.trim()).trim();
        } catch (Exception e) {
            FLog.runError(key + " [public static final String get(String key)] ");
        }
        return null;
    }

    public static final String silentGet(String key) {
        try {
            return messages.getString(key.trim()).trim();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * -Configure Dialogs
     *
     * @param dialog dialog
     * @param key key of title
     * @param title text to append in title
     */
    public static void loadDialogTitle(Dialog dialog, String key, String title) {
        try {
            dialog.setIconImage(loadIcon(get("DIALOG.title.icon"), 16).getImage());
            dialog.setTitle(get(key) + " \t " + title);
        } catch (Exception e) {
            FLog.runError(" public static void loadDialogTitle(Dialog dialog, String key, String title) ");
        }
    }

    /**
     * Load icon text and tooltip to the button
     *
     * @param bt button
     * @param key key
     * @param size size of the icon
     */
    public static void loadButton(JButton bt, String key, int size) {
        //--------------------- TITLE ---------------------------------
        try {
            bt.setText(get(key + ".title"));
        } catch (Exception e) {
            FLog.runError(" public static void loadButton(JButton bt, String key, int size)");
        }
        //-------------------TOOLTIP --------------------------------------
        try {
            bt.setToolTipText(Theme.toHtml(get(key + ".help")));
        } catch (Exception e) {
            bt.setToolTipText(null);
        }
        //-------------------ICON --------------------------------------
        try {
            bt.setIcon(loadIcon(get(key + ".icon"), size));
        } catch (Exception e) {
            bt.setIcon(null);
        }
    }

    /**
     * Load icon text and tooltip to the button
     *
     * @param bt button
     * @param key key
     * @param size size of the icon
     */
    public static void loadTabTile(final JTabbedPane tab, String key, final int index) {
        loadTabTile(tab, key, index, 16);
    }

    /**
     * Load icon text and tooltip to the button
     *
     * @param bt button
     * @param key key
     * @param size size of the icon
     */
    public static void loadTabTile(final JTabbedPane tab, String key, final int index, int size) {
        //--------------------- TITLE ---------------------------------
        JLabel lbl = new JLabel();
        lbl.setIconTextGap(5);
        lbl.setHorizontalTextPosition(SwingConstants.RIGHT);
        try {
            lbl.setText(get(key + ".title"));
        } catch (Exception e) {
            lbl.setText(get(key));
        }
        //-------------------TOOLTIP --------------------------------------
        try {
            lbl.setToolTipText(Theme.toHtml(get(key + ".help")));
        } catch (Exception e) {
        }
        //-------------------ICON --------------------------------------
        try {
            lbl.setIcon(loadIcon(get(key + ".icon"), size));
        } catch (Exception e) {
        }

        lbl.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (tab.getTabComponentAt(index).isEnabled()) {
                    tab.setSelectedIndex(index);
                }
            }
        });
        tab.setTabComponentAt(index, lbl);
    }

    /**
     * Load icon text and tooltip to the button
     *
     * @param label button
     * @param key key
     * @param size size of the icon
     */
    public static void loadLabel(JLabel label, String key, int size) {
        //--------------------- TITLE ---------------------------------
        try {
            label.setText(get(key + ".title"));
        } catch (Exception e) {
            FLog.runError("public static void loadLabel(JLabel label, String key, int size)" + key);
        }
        //-------------------TOOLTIP --------------------------------------
        try {
            label.setToolTipText(Theme.toHtml(get(key + ".help")));
        } catch (Exception e) {
            label.setToolTipText(null);
        }
        //-------------------ICON --------------------------------------
        try {
            label.setIcon(loadIcon(get(key + ".icon"), size));
        } catch (Exception e) {
            label.setIcon(null);
        }
    }

    /**
     * Load icon text and tooltip to the button
     *
     * @param menuItem button
     * @param key key
     * @param size size of the icon
     */
    public static void loadMenuItem(JMenuItem menuItem, String key, int size) {
        //--------------------- TITLE ---------------------------------
        try {
            menuItem.setText(get(key + ".title"));
        } catch (Exception e) {
            FLog.runError("public static void loadMenuItem(JMenuItem menuItem, String key, int size)" + key);
        }
        //-------------------TOOLTIP --------------------------------------
        try {
            menuItem.setToolTipText(Theme.toHtml(get(key + ".help")));
        } catch (Exception e) {
            menuItem.setToolTipText(null);
        }
        //-------------------ICON --------------------------------------
        try {
            menuItem.setIcon(loadIcon(get(key + ".icon"), size));
        } catch (Exception e) {
            menuItem.setIcon(null);
        }
    }

    /**
     * Load icon text and tooltip to the button
     *
     * @param lb Label
     * @param key key
     * @param size size of the icon
     */
    public static void loadLabel(JLabel lb, String key) {
        //--------------------- TITLE ---------------------------------
        try {
            lb.setText(get(key + ".title"));
        } catch (Exception e) {
            lb.setText(get(key));
        }
        //-------------------TOOLTIP --------------------------------------
        try {
            lb.setToolTipText(Theme.toHtml(get(key + ".help")));
            return;
        } catch (Exception e) {
        }
    }

    /**
     * Load icon text and tooltip to the button
     *
     * @param lb Label
     * @param key key
     * @param size size of the icon
     */
    public static void loadButton(AbstractButton comp, String key) {
        //--------------------- TITLE ---------------------------------
        try {
            comp.setText(get(key + ".title"));
        } catch (Exception e) {
            comp.setText(get(key));
        }
        //-------------------TOOLTIP --------------------------------------
        try {
            comp.setToolTipText(Theme.toHtml(get(key + ".help")));
        } catch (Exception e) {
        }
        //-------------------ICON --------------------------------------
        try {
            comp.setIcon(loadIcon(get(key + ".icon"), 16));
        } catch (Exception e) {
        }
    }

    public static String loadTooltip(String key) {
        //-------------------TOOLTIP --------------------------------------
        try {
            return Theme.toHtml(get(key));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * load a icon and a text for a label
     *
     * @param label
     * @param key
     */
    public static void loadMenuShapeHelp(JLabel label, String key) {

        try {
            label.setText(Theme.toHtml(get(key + ".help")));
            label.setIcon(loadIcon(get(key + ".icon"), 48));
        } catch (Exception e) {
            FLog.runError("public static void loadMenuShapeHelp(JLabel label, String key)" + key);
        }
    }

    /**
     * Load a icon int he path /flowchart/GUI/images/
     *
     * @param icon name of the file
     * @param height height of the icon [ 0 ] = not scalling
     * @return icon with size
     */
    public static ImageIcon loadIcon(String icon, int height) {
        try {
            ImageIcon iconImage = new ImageIcon(Fi18N.class.getResource(IMG_LOCATION + icon));
            Image newimg = null;
            if (height > 0) {
                //calcular width
                double ratio = (double) iconImage.getIconWidth() / (double) iconImage.getIconHeight();
                int width = (int) (ratio * height);
                //resize icon
                newimg = iconImage.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            } else {
                newimg = iconImage.getImage();
            }
            iconImage.setImage(newimg);
            return iconImage;
        } catch (Exception e) {
            FLog.printLn("Fi18n loadIcon " + icon + " height " + height + " ERROR " + IMG_LOCATION + icon);
            return null;
        }

    }

    /**
     * Load a icon int he path /flowchart/GUI/images/
     *
     * @param key key of icon
     * @param height height of the icon
     * @return icon with size
     */
    public static ImageIcon loadKeyIcon(String key, int height) {
        return loadIcon(get(key), height);
    }

    /**
     * Load a icon int he path /flowchart/GUI/images/
     *
     * @param key key of icon
     * @param height height of the icon
     * @return icon with size
     */
    public static Image loadKeyIconImage(String key, int height) {
        try {
            return loadIcon(get(key), height).getImage();
        } catch (Exception e) {
            return null;
        }

    }

    //----------------------------------------------------------------------------------
    public static void printLog() {
        FLog.printSeparator();
        FLog.printLn(" load " + messages.getBaseBundleName());
        FLog.printSeparator();
        StringBuilder txt = new StringBuilder(System.lineSeparator());
        // get the keys
        Enumeration<String> enumeration = messages.getKeys();
        // print all the keys
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            txt.append(TextUtils.setSize(key, 50, "_"));
            txt.append(" ");
            txt.append(messages.getString(key));
            txt.append(System.lineSeparator());
        }
        FLog.printLn(txt.toString());
        FLog.printSeparator();
    }
    //----------------------------------------------------------------------------------

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509201154L;

    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
    
}
