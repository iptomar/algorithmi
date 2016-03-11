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
package flowchart.arrow.RR;

import i18n.FkeyWord;
import flowchart.decide.Do_Connector;
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
public class ShapeArrow_RR_DW extends BorderFlowChart {

    /**
     * Creates a line border with the specified color, thickness, and corner
     * shape.
     *
     * @param color the color of the border
     */
    public ShapeArrow_RR_DW(Color color) {
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
    public void paint(Graphics g, int x, int y, int width, int height, Color fillColor) {

        int S = getBorderSize() / 2 + 1;
        int W = getBorderSize() * 3;

        int y2 = y + height;
        int x2 = x + width;

        Polygon p = new Polygon();

        p.addPoint(x, y);
        p.addPoint(x2, y);
        p.addPoint(x2, y2 - S);

        p.addPoint(x2 - W + 2 * S, y2 - S);
        p.addPoint(x2 - W + 3 * S, y2);
        p.addPoint(x2 - W, y2 - S - S / 2);
        p.addPoint(x2 - W + 3 * S, y2 - 3 * S);
        p.addPoint(x2 - W + 2 * S, y2 - 2 * S);

        p.addPoint(x2 - S, y2 - 2 * S);
        p.addPoint(x2 - S, y + S);
        p.addPoint(x, y + S);

        p.addPoint(p.xpoints[0], p.ypoints[0]);  //fechar

        g.setColor(fillColor);
        g.fillPolygon(p);
        g.setColor(Color.BLACK);
        g.drawPolygon(p);

        if (shape.parent instanceof Do_Connector) {
            String txt = FkeyWord.get("KEYWORD.do");
            drawLabel(g, txt, x , y +3*S);
        }
    }

    /**
     * Reinitialize the insets parameter with this Border's current Insets.
     *
     * @param c the component for which this border insets value applies
     * @param insets the object to be reinitialized
     */
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(getThickness(), getThickness(), getThickness(), getThickness());
        return insets;
    }

}
