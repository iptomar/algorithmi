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
package flowchart.shape;

import i18n.Fi18N;
import i18n.FkeyWord;
import java.awt.Font;
import javax.swing.JFrame;

/**
 * Created on 20/out/2015, 7:32:43
 *
 * @author zulu - computer
 */
public class MenuDialog extends javax.swing.JDialog {

    public MenuDialog(String key) {
        super((JFrame) null, Fi18N.get(key), true);
        setIconImage(Fi18N.loadKeyIcon("DIALOG.flowchartMenu.icon", 24).getImage()); 
    }
    public MenuDialog() {
        super((JFrame) null,null, true);
        setIconImage(Fi18N.loadKeyIcon("DIALOG.flowchartMenu.icon", 24).getImage()); 
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510200732L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
