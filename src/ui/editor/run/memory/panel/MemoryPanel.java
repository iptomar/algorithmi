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
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package ui.editor.run.memory.panel;

import core.Memory;
import i18n.EditorI18N;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import static javax.swing.border.TitledBorder.ABOVE_BOTTOM;
import static javax.swing.border.TitledBorder.ABOVE_TOP;
import static javax.swing.border.TitledBorder.BELOW_BOTTOM;
import static javax.swing.border.TitledBorder.BELOW_TOP;
import static javax.swing.border.TitledBorder.BOTTOM;
import static javax.swing.border.TitledBorder.DEFAULT_POSITION;
import static javax.swing.border.TitledBorder.TOP;

/**
 * Created on 30/abr/2016, 9:16:06
 *
 * @author zulu - computer
 */
public class MemoryPanel extends JPanel {

    Memory myMem;
    public static ImageIcon memoryIcon = EditorI18N.loadIcon("RUN.memoryDisplay.icon", 16);

    public MemoryPanel(Memory myMem) {

        this.myMem = myMem;
    }

    private class TitledIconBorder extends TitledBorder {

        ImageIcon icon;

        public TitledIconBorder(String title, ImageIcon ic) {
            super(title);
            icon = ic;
        }

        /**
         * Paints the border for the specified component with the specified
         * position and size.
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
            Border border = getBorder();
            String title = getTitle();
            if ((title != null) && !title.isEmpty()) {
                int edge = (border instanceof TitledBorder) ? 0 : EDGE_SPACING;
                JLabel label = new JLabel(this.title);
                Dimension size = label.getPreferredSize();
                Insets insets = new Insets(0, 0, 0, 0);

                int borderX = x + edge;
                int borderY = y + edge;
                int borderW = width - edge - edge;
                int borderH = height - edge - edge;

                int labelY = y;
                int labelH = size.height;
                int position = getPosition();

                insets.top = edge + insets.top / 2 - labelH / 2;
                if (insets.top < edge) {
                    borderY -= insets.top;
                    borderH += insets.top;
                } else {
                    labelY += insets.top;
                }

                insets.left += edge + TEXT_INSET_H;
                insets.right += edge + TEXT_INSET_H;

                int labelX = x;
                int labelW = width - insets.left - insets.right;
                if (labelW > size.width) {
                    labelW = size.width;
                }
                labelX += insets.left;
                if (border != null) {
                    if ((position != TOP) && (position != BOTTOM)) {
                        border.paintBorder(c, g, borderX, borderY, borderW, borderH);
                    } else {
                        Graphics g2 = g.create();
                        if (g2 instanceof Graphics2D) {
                            Graphics2D g2d = (Graphics2D) g2;
                            Path2D path = new Path2D.Float();
                            path.append(new Rectangle(borderX, borderY, borderW, labelY - borderY), false);
                            path.append(new Rectangle(borderX, labelY, labelX - borderX - TEXT_SPACING, labelH), false);
                            path.append(new Rectangle(labelX + labelW + TEXT_SPACING, labelY, borderX - labelX + borderW - labelW - TEXT_SPACING, labelH), false);
                            path.append(new Rectangle(borderX, labelY + labelH, borderW, borderY - labelY + borderH - labelH), false);
                            g2d.clip(path);
                        }
                        border.paintBorder(c, g2, borderX, borderY, borderW, borderH);
                        g2.dispose();
                    }
                }
                g.translate(labelX, labelY);
                label.setSize(labelW, labelH);
                label.paint(g);
                g.translate(-labelX, -labelY);
            } else if (border != null) {
                border.paintBorder(c, g, x, y, width, height);
            }
        }

        private int getPosition() {
            int position = getTitlePosition();
            if (position != DEFAULT_POSITION) {
                return position;
            }
            Object value = UIManager.get("TitledBorder.position");
            if (value instanceof Integer) {
                int i = (Integer) value;
                if ((0 < i) && (i <= 6)) {
                    return i;
                }
            } else if (value instanceof String) {
                String s = (String) value;
                if (s.equalsIgnoreCase("ABOVE_TOP")) {
                    return ABOVE_TOP;
                }
                if (s.equalsIgnoreCase("TOP")) {
                    return TOP;
                }
                if (s.equalsIgnoreCase("BELOW_TOP")) {
                    return BELOW_TOP;
                }
                if (s.equalsIgnoreCase("ABOVE_BOTTOM")) {
                    return ABOVE_BOTTOM;
                }
                if (s.equalsIgnoreCase("BOTTOM")) {
                    return BOTTOM;
                }
                if (s.equalsIgnoreCase("BELOW_BOTTOM")) {
                    return BELOW_BOTTOM;
                }
            }
            return TOP;
        }

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604300916L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

  
}
