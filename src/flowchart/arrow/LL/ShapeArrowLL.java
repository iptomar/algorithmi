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
package flowchart.arrow.LL;

import ui.FProperties;
import i18n.Fi18N;
import flowchart.decide.IfElse.IfThenElse;
import flowchart.shape.BorderFlowChart;
import flowchart.utils.UtilsFlowchart;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Polygon;

/**
 *
 * @author ZULU
 */
public class ShapeArrowLL extends BorderFlowChart {

    /**
     * Creates a line border with the specified color, thickness, and corner
     * shape.
     *
     * @param color the color of the border
     */
    public ShapeArrowLL(Color color) {
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
        int y2 = y + height;
        int x2 = x + width;
        int S = getBorderSize() / 2 + 1;
        int W = getBorderSize() * 3;

        Polygon p = new Polygon();

        p.addPoint(x + W - S, y + S);  //1 inicio SETA
        p.addPoint(x + W, y + S / 2);  //1 inicio SETA
        p.addPoint(x + W - S, y);  //1 inicio SETA

        p.addPoint(x, y);  //2

        p.addPoint(x, y2 - S);  //3
        //------------------------------
        p.addPoint(x2 - 2 * S, y2 - S);  //4 - SETA
        p.addPoint(x2 - 3 * S, y2);  //4 - SETA        
        p.addPoint(x2, y2 - (int) (1.5 * S));  //4 - SETA
        p.addPoint(x2 - 3 * S, y2 - 3 * S);  //4 - SETA
        p.addPoint(x2 - 2 * S, y2 - 2 * S);  //4 - SETA

        //--------------------
        p.addPoint(x + S, y2 - 2 * S);  //5

        p.addPoint(x + S, y + S);  //6

//        p.addPoint(x + W, y + S);  //7
        p.addPoint(p.xpoints[0], p.ypoints[0]);  //8 fechar

        g.setColor(fillColor);
        g.fillPolygon(p);

        g.setColor(Color.BLACK);
        g.drawPolygon(p);

        if (shape.parent instanceof IfThenElse) {
            String txt = Fi18N.get("IF.else");
            Dimension dimText = UtilsFlowchart.getTextDimension(txt, getLabelFont());
            drawLabel(g, txt, x +3*S, y2 - dimText.height+5);
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

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
