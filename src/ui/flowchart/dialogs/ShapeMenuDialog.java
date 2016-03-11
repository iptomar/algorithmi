//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
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
package ui.flowchart.dialogs;

import i18n.Fi18N;
import flowchart.shape.Fshape;
import flowchart.shape.MenuPattern;
import ui.FProperties;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.text.JTextComponent;

/**
 * Created on 13/nov/2015, 17:05:30
 *
 * @author zulu - computer
 */
public abstract class ShapeMenuDialog extends javax.swing.JDialog implements MenuPattern {

    public ShapeMenuDialog(String key) {
        super((JFrame) null, Fi18N.get(key), true);
//        setUndecorated(true);
        //getRootPane().setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
//        getRootPane().setBorder(BorderFactory.createSoftBevelBorder(10));
        this.setIconImage(Fi18N.loadKeyIconImage("FLOWCHART.application.icon", 24));
        setResizable(false);

    }

    public static int getFontSize() {
        return Math.min(Math.max(12, Fshape.myFONT.getSize()), 25);
    }

    public void updateFont() {
        String fontName = Fshape.myFONT.getFontName();
        int fontStype = Fshape.myFONT.getStyle();
        int fontSize = getFontSize();
        Font f = new Font(fontName, fontStype, fontSize);
        updateFont(getContentPane(), f);
    }

    public void updateFont(Component c, Font f) {
        if (!(c instanceof JLabel) && !(c instanceof JButton)) {
            c.setFont(f);
        }
        if (c instanceof JTextComponent) {
            clearError((JTextComponent) c);
        }

        // FLog.printLn("UPDATE FONT " + c.getClass().getSimpleName());
        if (c instanceof JMenu) {
            JMenu mn = (JMenu) c;
            //  FLog.printLn("UPDATE JMENU FONT " + c.getClass().getSimpleName());
            for (Component c1 : mn.getMenuComponents()) {
                updateFont(c1, f);
            }
        } else if (c instanceof Container) {
            // FLog.printLn("UPDATE CONTAINER FONT " + c.getClass().getSimpleName());
            for (Component c1 : ((Container) c).getComponents()) {
                updateFont(c1, f);
            }
        }

    }

    public void setError(JTextComponent txt) {
        txt.setBackground(FProperties.errorColor);
    }

    public void clearError(JTextComponent txt) {
        txt.setBackground(FProperties.getColor(FProperties.keySintaxBackground));
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * gets the 
     *
     * @param bt Jbutton called btCancel
     * @return keyListener to perform btCancelActionPerformed
     */
    public KeyListener getKeyListener(final JButton bt, final int keyCode) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {  // handler
                if (evt.getKeyCode() == keyCode) {
                    for (ActionListener a : bt.getActionListeners()) {
                        a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {
                            //Nothing need go here, the actionPerformed method (with the
                            //above arguments) will trigger the respective listener
                        });
                        evt.consume();
                    }
                }
            }
        };
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201511131705L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
