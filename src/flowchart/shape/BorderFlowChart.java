/*
 * Copyright (c) 2015 Instituto Politecnico de Tomar. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */
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
package flowchart.shape;

import ui.FProperties;
import i18n.Fi18N;
import flowchart.utils.UtilsFlowchart;
import java.awt.BasicStroke;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.beans.ConstructorProperties;
import javax.swing.border.AbstractBorder;

public abstract class BorderFlowChart extends AbstractBorder {

    protected Fshape shape; // pattern to be painted

    private int borderSize;

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    /**
     * @param aThickness the thickness to set
     */
    public static void setThickness(int aThickness) {
        thickness = aThickness > 2 ? aThickness : 1;
    }

    public void setColor(Color color) {
        this.fillColor = color;
    }
    protected Color fillColor; // normal Color
    protected Color activeColor; // active color

    public void setSelected() {
        activeColor = FProperties.selectedColor;
    }

    public void setNotselected() {
        activeColor = fillColor;
    }

    public void setError() {
        activeColor = FProperties.errorColor;
    }

    public void setInExecution() {
        activeColor = FProperties.executionColor;
    }

    public void setExecuted() {
        activeColor = FProperties.runColor;
    }

//    public static void setTicknessSize(int tick) {
//        setThickness(tick > 0 ? tick : 1);
//    }
//
//    public static int getTicknessSize() {
//        return getThickness();
//    }
    private static int thickness = 2;

    public boolean selected;

    /**
     * Creates a line border with the specified color, thickness, and corner
     * shape.
     *
     * @param color the color of the border
     * @param thickness the thickness of the border
     */
    @ConstructorProperties({"lineColor"})
    public BorderFlowChart(Color color) {
        fillColor = color;
        setNotselected();
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
    public abstract void paint(Graphics g, int x, int y, int width, int height, Color color);

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
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        shape = (Fshape) c;
        //---------------------------------------------------------------------------LINE TICKENESS
        int tickness =Math.max(1,getBorderSize()/6);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(tickness));
        x+=tickness/2;
        y+=tickness/2;
        width-= tickness+1;
        height-=tickness+1;
        //----------------------------------------------------------------------------LINE TICKENESS
//        g.setColor(Color.RED);
//        g.drawRect(x, y, width, height);
        paint(g, x, y, width, height, activeColor);
    }

    /**
     * Reinitialize the insets parameter with this Border's current Insets.
     *
     * @param c the component for which this border insets value applies
     * @param insets the object to be reinitialized
     */
    public abstract Insets getBorderInsets(Component c, Insets insets);

    /**
     * Returns the color of the border.
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Returns the thickness of the border.
     */
    public static int getThickness() {
        return thickness > 4 ? thickness / 4 : 1;
    }

    public java.awt.Shape createArrowShape(int x, int y, double ang) {
        Polygon pts = new Polygon();
        int S = getBorderSize();
        pts.addPoint(0, 0);
        pts.addPoint(3 * S, (int) (1.5 * S));
        pts.addPoint(0, 3 * S);
        pts.addPoint(S, 2 * S);
        pts.addPoint(S, S);
        pts.addPoint(0, 0);

        AffineTransform transform = new AffineTransform();
        transform.translate(1.5 * S, 1.5 * S);
        transform.rotate(ang);
        transform.translate(-1.5 * S, -1.5 * S);
        transform.translate(x, y);
        return transform.createTransformedShape(pts);
    }

    public Font getLabelFont() {
        Font font = shape.parent.getTxtFont();
        return new Font(font.getFontName(), Font.BOLD, font.getSize() / 3 + 5);
    }

    public void drawLabel(Graphics gr, String txt, int x, int y) {
        Font font = getLabelFont();
        gr.setColor(Color.BLUE);
        gr.setFont(font);
        gr.drawString(txt, x, y);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
