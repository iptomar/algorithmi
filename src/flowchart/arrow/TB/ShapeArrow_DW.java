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
package flowchart.arrow.TB;

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
public class ShapeArrow_DW extends BorderFlowChart {

    /**
     * Creates a line border with the specified color, thickness, and corner
     * shape.
     *
     * @param color the color of the border
     */
    public ShapeArrow_DW(Color color) {
        super(color);
    }

    /**
     * Paints the border for the specified component with the specified position
     * and size.
     *
     * @param g the paint graphics
     * @param x the x position of the painted border
     * @param y the y position of the painted border
     * @param width the width of the painted border
     * @param fillColor
     * @param height the height of the painted border
     */
    @Override
    public void paint(Graphics g, int x, int y, int width, int height, Color fillColor) {

        int S = getBorderSize() / 2 + 1;
        int xm = x + width / 2;
        int y2 = y + height;

        Polygon p = new Polygon();

        p.addPoint(xm - S / 2, y + 2 * S);//SETA
        p.addPoint(xm - S - S / 2, y + 3 * S);//SETA
        p.addPoint(xm, y);//SETA
        p.addPoint(xm + S + S / 2, y + 3 * S);//SETA        
        p.addPoint(xm + S / 2, y + 2 * S);//SETA

        p.addPoint(xm + S / 2, y2);
        p.addPoint(xm - S / 2, y2);

        p.addPoint(p.xpoints[0], p.ypoints[0]);  //fechar

        g.setColor(fillColor);
        g.fillPolygon(p);
        g.setColor(Color.BLACK);
        g.drawPolygon(p);
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
