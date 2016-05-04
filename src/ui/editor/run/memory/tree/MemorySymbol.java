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
package ui.editor.run.memory.tree;

import core.data.Fsymbol;
import core.data.complexData.Farray;
import flowchart.utils.Theme;
import flowchart.utils.UtilsFlowchart;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Created on 14/set/2015, 14:04:12
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class MemorySymbol extends JPanel {

    public static Color COLOR_SELECTED = new Color(50, 255, 50);
    public static Color COLOR_NOT_SELECTED = new Color(255, 255, 255);

    public static int VAR_WITH = 50;
    public static int VAR_HEIGHT = 20;

    List<JLabel> list = new ArrayList<>();
    /**
     * Creates new form MemoryVariable
     */
    protected Fsymbol symbol;

    public MemorySymbol(Fsymbol symbol) {
        this.symbol = symbol;
        this.setBorder(BorderFactory.createTitledBorder(
                null, symbol.getName(),
                TitledBorder.LEFT,
                TitledBorder.BELOW_TOP,
                new Font("Courier New", Font.BOLD, 16))
        );
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createGUIMemory();
    }

    private void createArrayGUI(List<Fsymbol> elements, int maxy, int maxx, String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                null, title,
                TitledBorder.CENTER,
                TitledBorder.BELOW_TOP,
                new Font("Courier New", Font.BOLD, 12))
        );
        panel.setLayout(new java.awt.GridLayout(maxy, maxx, 0, 0));
        for (Fsymbol var : elements) {
            JLabel txt = createVarLabel(var, var.getFullInfo());
            panel.add(txt);
            list.add(txt);
        }
        this.add(panel);
    }

    private void createArrayGUI(Farray array) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                null, array.getName(),
                TitledBorder.CENTER,
                TitledBorder.BELOW_TOP,
                new Font("Courier New", Font.BOLD, 12))
        );
        panel.setToolTipText(Theme.toHtml(array.getFullInfo()));
        List<Integer> dims = array.getDimensions();
        if (dims.size() == 1 || dims.size() > 3) {
            panel.setLayout(new java.awt.GridLayout(1, dims.get(0), 0, 0));
        } else if (dims.size() == 2) {
            panel.setLayout(new java.awt.GridLayout(dims.get(0), dims.get(0), 0, 0));
        }
        List<Fsymbol> elements = array.getElements();
        for (Fsymbol var : elements) {
            JLabel txt = createVarLabel(var, var.getFullInfo());
            panel.add(txt);
            list.add(txt);
        }
        this.add(panel);

    }

    public final void createGUIMemory() {
        this.setToolTipText(symbol.getFullInfo());
        if (symbol instanceof Farray) {

            Farray a = (Farray) symbol;
            List<Integer> dims = a.getDimensions();
            //create a line
            if (dims.size() == 1 || dims.size() > 3) {
                List<Fsymbol> elem = a.getElements();
                createArrayGUI(elem, 1, elem.size(), "");
            } //create squares
            else if (dims.size() == 2) {
                List<Fsymbol> elem = a.getElements();
                int xDim = dims.remove(dims.size() - 1);
                int yDim = dims.remove(dims.size() - 1);
                createArrayGUI(elem, yDim, xDim, "");

            } else {
                int xDim = dims.remove(dims.size() - 1);
                int yDim = dims.remove(dims.size() - 1);
                int zDim = dims.remove(dims.size() - 1);
                for (int i = 0; i < zDim; i++) {
                    List<Integer> arrayindex = new ArrayList<>();
                    arrayindex.add(i);
                    try {
                        Farray subArray = a.getsubArray(arrayindex);
                        createArrayGUI(subArray);
//                        List<SymbolType> elem = subArray.getElements();
//                        createArrayGUI(elem, yDim, xDim, a.getIndexedName(arrayindex));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        } else {
            setLayout(new java.awt.GridLayout(1, 1, 0, 0));
            JLabel txt = createVarLabel(symbol, symbol.getInstruction());
            Dimension dim = UtilsFlowchart.getTextDimension(symbol.getDefinitionValue(), txt.getFont());
            txt.setPreferredSize(new Dimension(Math.max(VAR_WITH * 3, dim.width + 7), VAR_HEIGHT));
            this.add(txt);
            list.add(txt);
        }
        revalidate();
    }

    public final void updateMemory() {
        this.setToolTipText(Theme.toHtml(symbol.getFullInfo()));
        if (symbol instanceof Farray) {
            Farray a = (Farray) symbol;
            List<Integer> dims = a.getDimensions();
            List<Fsymbol> elem = a.getElements();
            for (int i = 0; i < elem.size(); i++) {
                if (Theme.valueToHtml(elem.get(i)).equals(list.get(i).getText())) {
                    list.get(i).setBackground(COLOR_NOT_SELECTED);
                } else {
                    list.get(i).setBackground(COLOR_SELECTED);
                    list.get(i).setText(Theme.valueToHtml(elem.get(i)));
                    list.get(i).setToolTipText(Theme.toHtml(elem.get(i).getFullInfo()));
                }
            }
        } else {
            if (Theme.valueToHtml(symbol).equals(list.get(0).getText())) {
                list.get(0).setBackground(COLOR_NOT_SELECTED);
            } else {
                list.get(0).setBackground(COLOR_SELECTED);
                list.get(0).setText(Theme.valueToHtml(symbol));
                list.get(0).setToolTipText(Theme.toHtml(symbol.getFullInfo()));
            }
        }
        revalidate();
    }

    private JLabel createVarLabel(final Fsymbol var, String tooltip) {
        JLabel txt = new JLabel(Theme.valueToHtml(var));
        txt.setFont(new java.awt.Font("Courier New", 0, 12));
        txt.setBackground(color[symbol.getLevel() % color.length]);
        txt.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt.setPreferredSize(new java.awt.Dimension(VAR_WITH, VAR_HEIGHT));
        txt.setToolTipText(Theme.toHtml(tooltip));
        txt.setBackground(COLOR_NOT_SELECTED);
        Dimension dim = UtilsFlowchart.getTextDimension(var.getDefinitionValue(), txt.getFont());
        txt.setPreferredSize(new Dimension(Math.max(dim.width + 2, VAR_WITH), Math.max(dim.height, VAR_HEIGHT)));
        txt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, var.getFullInfo());
            }
        });
        return txt;
    }

    private static Color color[] = {
        new Color(200, 250, 200),
        new Color(250, 250, 50),
        new Color(250, 128, 50),
        new Color(250, 50, 50),
        new Color(250, 128, 128),
        new Color(250, 50, 128),
        new Color(128, 50, 250),
        new Color(50, 50, 250),};

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509141404L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
