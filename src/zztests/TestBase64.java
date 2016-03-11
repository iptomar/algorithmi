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
package zztests;

import flowchart.utils.image.ImageUtils;
import i18n.Fi18N;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Created on 9/dez/2015, 11:06:49
 *
 * @author zulu - computer
 */
public class TestBase64 {

    public static void main(String[] args) {
        String filename = "D:\\Cloud\\Dropbox\\IPT2016\\code\\Flowchart\\Flowchart_091\\src\\ui\\images\\flowchart.png";
        JLabel l = new JLabel();
        l.setIcon(ImageUtils.loadPathIcon(filename, 96,96));
        
        ImageUtils.saveImage(l.getIcon(), "c:/_test/icon.png");
        
        ImageUtils.saveBase64((ImageIcon)l.getIcon(), "c:/_test/icon.b64g");
        filename = "question.png";
        JLabel l2 = new JLabel();
        l2.setIcon(ImageUtils.loadBase64("c:/_test/icon.b64g"));
        JFrame frame = new JFrame("test Base64");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2));
        frame.add(l);
        frame.add(l2);
        
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201512091106L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
