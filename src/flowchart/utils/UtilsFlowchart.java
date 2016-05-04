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
package flowchart.utils;

import flowchart.decide.IfElse.IfThenElse;
import flowchart.decide.While_Do;
import flowchart.shape.Fshape;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ZULU
 */
public class UtilsFlowchart {

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMMM yyyy HH:mm");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    /**
     * split string to line "\r\n|\r|\n"
     *
     * @param txt string
     * @return lines of string
     */
    public static String[] splitLines(String txt) {
        return txt.split("\r\n|\r|\n|<br>");
    }

    public static String deleteEmptyLines(String txt) {
        String[] allLines = splitLines(txt);
        StringBuilder str = new StringBuilder();
        for (String line : allLines) {
            line = line.trim();
            if (!line.isEmpty()) {
                str.append(line.trim() + "<br>");
            }
        }
        return str.toString().trim();
    }

    //to calculate dimension offlin
    static Graphics2D gr2D = (new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB)).createGraphics();

    /**
     * get dimension in pixels of the string with a font dimensions have at
     * minimum one character
     *
     * @param txt text
     * @param font font of text
     * @return dimension of text with font
     */
    public static Dimension getTextDimension(String txt, Font font) {
        int w = 0, h = 0, lines = 0;
        String[] allLines = splitLines(txt);
        for (String line : allLines) {
            Rectangle2D f = font.getStringBounds(line, gr2D.getFontRenderContext());
            if (f.getBounds().width > w || f.getBounds().height > h) {
                w = f.getBounds().width;
                h = f.getBounds().height;
            }
            lines++;
        }
        if (txt.endsWith("\n")) {
            lines++;
        }
        return new Dimension(w, h * lines);
    }

    /**
     * get dimension in pixels of the string with a font dimensions have at
     * minimum one character
     *
     * @param shape shape
     * @return dimension of text with font
     */
    public static Dimension getTextDimension(Fshape shape) {
        int w = 0, h = 0, lines = 0;

        String txt = shape.getInstruction();
////        System.out.println("INSTRUCTION " + txt);
//        if (!shape.type.isEmpty()) {
//            txt = shape.type + "\n" + txt;
//        }
        if (shape instanceof IfThenElse || shape instanceof While_Do) {
            txt += "\n ???";
        }

        String[] allLines = txt.split("\n");
        for (String line : allLines) {
//            System.out.println(shape.getClass().getSimpleName() + " LINE " + line);
            Rectangle2D f = shape.getTxtFont().getStringBounds(line + " ", gr2D.getFontRenderContext());

            h += f.getBounds().height;
            if (f.getBounds().width > w) {
                w = f.getBounds().width;
            }
        }

        return new Dimension(w, h);
    }

    public static String setStringSize(String str, int size) {
        if (str.length() > size) {
            return str.substring(0, size);
        }
        while (str.length() < size) {
            str += " ";
        }
        return str;
    }

    public static Color invertColor(Color c) {
        return new Color(
                255 - c.getRed(),
                255 - c.getGreen(),
                255 - c.getBlue()
        );
    }

    public static Color setTransparency(Color c, boolean transparent) {
        int alpha = transparent ? 0 : 255;
        return new Color(
                255 - c.getRed(),
                255 - c.getGreen(),
                255 - c.getBlue(),
                alpha
        );

    }

    public static int min(int a, int b, int c, int d) {
        return Math.min(Math.min(Math.min(a, b), c), d);
    }

    public static int max(int a, int b, int c, int d) {
        return Math.max(Math.max(Math.max(a, b), c), d);
    }

    public static Rectangle getMaximalRectangle(Rectangle r1, Rectangle r2) {
        int x0 = Math.min(r1.x, r2.x);
        int y0 = Math.min(r1.y, r2.y);
        int w = Math.max(r1.x + r1.width, r2.x + r2.width) - x0;
        int h = Math.max(r1.y + r1.height, r2.y + r2.height) - y0;
        return new Rectangle(x0, y0, w, h);
    }

}
