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
package ui.flowchart;

import i18n.Fi18N;
import flowchart.shape.Fshape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Created on 20/set/2015, 10:52:40
 *
 * @author zulu
 */
public class PopupMenuDelete extends JPopupMenu {

    Fshape instruction;

    public PopupMenuDelete(final Fshape instruction) {
        this.instruction = instruction;
        JMenuItem item = new JMenuItem();
        Fi18N.loadMenuItem(item, "BUTTON.delete", 24);        
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instruction.algorithm.removePattern(instruction);
            }
        });
        add(item);

    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509201052L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
