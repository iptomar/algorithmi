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
package flowchart.arrow.LB;

import i18n.Fi18N;
import i18n.FkeyWord;
import flowchart.decide.While_Do;
import flowchart.decide.forNext.For_Next;
import flowchart.shape.BorderFlowChart;
import flowchart.utils.UtilsFlowchart;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Polygon;

/**
 *
 * @author ZULU
 */
public class ShapeArrowLB_while extends BorderFlowChart {

    static String VALUE_TRUE = Fi18N.get("STRING.arrow.true");

    /**
     * Creates a line border with the specified color, thickness, and corner
     * shape.
     *
     * @param color the color of the border
     */
    public ShapeArrowLB_while(Color color) {
        super(color);
    }

    /**
     * Paints the border for the specified component with the specified position
     * and size.
     *
     * @param c the component for which this border is being painted
     * @param g the paint graphics
     * @param x1 the x position of the painted border
     * @param y the y position of the painted border
     * @param width the width of the painted border
     * @param fillColor
     * @param height the height of the painted border
     */
    @Override
    public void paint(Graphics g, int x, int y, int width, int height, Color fillColor) {

        int S = getBorderSize() / 2 + 1;
//
        int y2 = y + height;
        int x2 = x + width;
        int x1 = x + 3*S;
        
//
        Polygon p = new Polygon();

        p.addPoint(x2, y2);
        p.addPoint(x1 + S, y2);
//        
        p.addPoint(x1 + S, y + 2 * S);
        p.addPoint(x1, y + 3 * S);
        p.addPoint(x1 + S + S / 2, y);
        p.addPoint(x1 + 3 * S, y + 3 * S);
        p.addPoint(x1 + 2 * S, y + 2 * S);
//        
//        

        p.addPoint(x1 + 2 * S, y2 - S);

        p.addPoint(x2 - S, y2 - S);
        p.addPoint(x2 - S, y2 - 2 * S);
        p.addPoint(x2, y2 - 2 * S);

        p.addPoint(x2, y2 - S);

//
        p.addPoint(p.xpoints[0], p.ypoints[0]);  //fechar
//
        g.setColor(fillColor);
        g.fillPolygon(p);

        g.setColor(Color.BLACK);
        g.drawPolygon(p);
        String txt="";
         if (shape.next instanceof While_Do) {
            txt = FkeyWord.get("KEYWORD.do");
        }
        if (shape.next instanceof For_Next) {
            txt = FkeyWord.get("KEYWORD.next");
        }
        Dimension dimText = UtilsFlowchart.getTextDimension(txt, getLabelFont());
        drawLabel(g, txt, x1+3*S, y2 - dimText.height+5);

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
