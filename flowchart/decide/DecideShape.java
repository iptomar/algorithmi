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
package flowchart.decide;

import ui.FProperties;
import i18n.Fi18N;
import flowchart.shape.BorderFlowChart;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Polygon;

/**
 *
 * @author ZULU
 */
public class DecideShape extends BorderFlowChart {

    static String VALUE_TRUE = Fi18N.get("IF.then");
    static String VALUE_FALSE = Fi18N.get("IF.else");

    /**
     * Creates a line border with the specified color, thickness, and corner
     * shape.
     *
     * @param color the color of the border
     */
    public DecideShape(Color color) {
        super(color);
    }

    /**
     * Paints the border for the specified component with the specified position
     * and size.
     *
     * @param c the component for which this border is being painted
     * @param g the paint graphics
     * @param x the x position of the painted border
     * @param y the y position of the painted border
     * @param width the width of the painted border
     * @param height the height of the painted border
     */
    public void paint(Graphics g, int x, int y, int width, int height, Color color) {        
        width--;
        height--;
        g.setColor(color);

        Polygon p = new Polygon();
        int midx = (x + width) / 2;
        int midy = (y + height) / 2;

        p.addPoint(midx, y);
        p.addPoint(x + width - 1, midy);
        p.addPoint(midx, y + height - 1);
        p.addPoint(x, midy);
        g.fillPolygon(p);
        g.setColor(FProperties.lineColor);
        g.drawPolygon(p);
    }

    /**
     * Reinitialize the insets parameter with this Border's current Insets.
     *
     * @param c the component for which this border insets value applies
     * @param insets the object to be reinitialized
     */
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(getThickness() * 10 + FProperties.BORDER_SIZE,
                getThickness() * 10 + FProperties.BORDER_SIZE,
                getThickness() * 10 + FProperties.BORDER_SIZE,
                getThickness() * 10+ FProperties.BORDER_SIZE);
        return insets;
    }

}
