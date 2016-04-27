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
package ui.editor.run;

import ui.editor.run.memory.MemoryCellRenderer;
import ui.editor.run.memory.MemoryVector;
import core.Memory;
import flowchart.algorithm.run.GraphExecutor;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 * panel of memory
 *
 * Created on 14/set/2015, 13:32:57
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class MemoryDisplay extends JPanel {

    GraphExecutor runningProgram;
    JTree tree = null;
    Vector rootVector;

    public MemoryDisplay(GraphExecutor program) {
        this.setLayout(new BorderLayout());
        this.runningProgram = program;

        createMemory();
    }

//    private void updateListeners() {
//        tree.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                TreePath path = tree.getPathForLocation(e.getX(), e.getY());
//                if (path == null || path.getLastPathComponent() == null) {
//                    return;
//                }
//                System.out.println(path.getLastPathComponent());
//            }
//        });
//    }
    public void createMemory() {
        this.removeAll();
        //list of memorys
        List<Memory> lst = runningProgram.getProgramMemory();
        //System.out.println(lst);
        //array of tree nodes
        Vector[] nodes = new Vector[lst.size()];
        //create variables nodes
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new MemoryVector(lst.get(i));
        }
        //crerate root nodes
        rootVector = new MemoryVector("", nodes);
        tree = new JTree(rootVector);
        //register to tooltip
//        ToolTipManager.sharedInstance().registerComponent(tree);
        //set cell Renderer
        TreeCellRenderer renderer = new MemoryCellRenderer();
        tree.setCellRenderer(renderer);
        //scroolable memory
        JScrollPane scrollPane = new JScrollPane(tree);
        this.add(scrollPane, BorderLayout.CENTER);
        //Expand last row
        tree.expandRow(nodes.length - 1);
//        updateListeners();
        revalidate();
//        repaint();
    }

    public final void updateMemory() {
            createMemory();        
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509141332L;

    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
