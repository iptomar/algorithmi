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
package zzTests;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Created on 6/set/2015, 16:56:32
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class NewClass {

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setLayout(null);
        f.setDefaultCloseOperation(3);
        f.setSize(500, 500);
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Dimension arcs = new Dimension(15, 15);
                int width = getWidth();
                int height = getHeight();
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //Draws the rounded opaque panel with borders.
                graphics.setColor(getBackground());
                graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);//paint background
                graphics.setColor(getForeground());
                graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);//paint border
            }
        };
        p.setBounds(10, 10, 100, 30);
        p.setOpaque(false);
        f.getContentPane().setBackground(Color.red);
        f.add(p);
        f.setVisible(true);
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::::::
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
}
