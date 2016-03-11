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
package ui.flowchart;

import i18n.Fi18N;
import flowchart.utils.FileUtils;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.FunctionGraph;
import flowchart.algorithm.GlobalMemoryGraph;
import flowchart.shape.Fshape;
import flowchart.utils.FluxImage;
import flowchart.utils.UserName;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import ui.flowchart.dialogs.CodeText;

/**
 * Created on 6/set/2015, 18:42:03
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class FeditorScrool extends JScrollPane {

    JPanel graphics;
    private AlgorithmGraph flux;

    JPopupMenu rightMenu;

    public FeditorScrool(AlgorithmGraph newFlux) {
        this.flux = newFlux;
        setViewportView(flux.graph);
        setAutoscrolls(true);
        revalidate();
        repaint();
        getVerticalScrollBar().setUnitIncrement(100);

        createPopupMenu();
        flux.graph.setComponentPopupMenu(rightMenu);
    }

    public AlgorithmGraph getFluxogramGraph() {
        return flux;
    }

    public void setZoom(int zoom) {
        if (zoom > 1) {
            flux.setZoom(zoom);
        }
    }

    public void ZoomIn() {
        setZoom(flux.getBegin().getZoom() + 2);
    }

    public void Zoomout() {
        setZoom(flux.getBegin().getZoom() + 2);
    }

    public JComponent getPortugolCode() {
        JTextArea code = new JTextArea(flux.getPseudoCode());
        code.setFont(new Font("monospaced", Font.BOLD, 16));
        return code;
    }

    public void save(String path) throws Exception {
        flux.save(path);
    }

    public static FeditorScrool load(String path) throws Exception {
        AlgorithmGraph flux = AlgorithmGraph.load(path);
        return new FeditorScrool(flux);
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: POPUP MENU
    public void createPopupMenu() {

        rightMenu = new JPopupMenu();
        JMenuItem clipboardImg = new JMenuItem();
        Fi18N.loadMenuItem(clipboardImg, "BUTTON.clipboardImg", 24);
        clipboardImg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FluxImage.copyToClipBoard(flux.graph, UserName.createUser(flux.getMyProgram().digitalSignature));
            }
        });

        JMenuItem savePNG = new JMenuItem();
        Fi18N.loadMenuItem(savePNG, "BUTTON.saveImage", 24);
        savePNG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FluxImage.saveTofile(flux.graph,
                        UserName.createUser(flux.getMyProgram().digitalSignature));
            }
        });

        JMenuItem clipboardTxt = new JMenuItem();
        Fi18N.loadMenuItem(clipboardTxt, "BUTTON.clipboardTxt", 24);
        clipboardTxt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CodeText.copyTextToClipboard(flux.getPseudoCode());
            }
        });

        rightMenu.add(clipboardImg);
        rightMenu.add(clipboardTxt);
        rightMenu.add(new JSeparator()); // SEPARATOR
        rightMenu.add(savePNG);
        //------------------------------------------------------------------------------ SAVE FLUX
        if ((flux instanceof FunctionGraph) || (flux instanceof GlobalMemoryGraph)) {
            JMenuItem saveFlux = new JMenuItem();
            Fi18N.loadMenuItem(saveFlux, "BUTTON.saveFlux", 24);
            saveFlux.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    FileUtils.saveFluxogram(flux);
                }
            });
            rightMenu.add(new JSeparator()); // SEPARATOR
            rightMenu.add(saveFlux);
        }

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
