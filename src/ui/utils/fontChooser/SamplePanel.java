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

package ui.utils.fontChooser;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
 
import javax.swing.JPanel;
 
public class SamplePanel extends JPanel {
 
    private static final long serialVersionUID = 7405261908065757006L;
 
    private Font font;
 
    private String sampleString;
 
    public SamplePanel(String sampleString) {
        super();
        this.sampleString = sampleString;
    }
 
    public void setSampleFont(Font font) {
        this.font = font;
        repaint();
    }
 
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
 
        if (font == null) {
            return;
        }
 
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout layout = new TextLayout(sampleString, font, frc);
        Rectangle2D bounds = layout.getBounds();
 
        int width = (int) Math.round(bounds.getWidth());
        int height = (int) Math.round(bounds.getHeight());
        int x = (getWidth() - width) / 2;
        int y = height + (getHeight() - height) / 2;
 
        layout.draw(g2d, (float) x, (float) y);
    }
 
}