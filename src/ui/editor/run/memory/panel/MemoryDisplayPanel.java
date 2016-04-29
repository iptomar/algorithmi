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
package ui.editor.run.memory.panel;

import ui.editor.run.memory.tree.*;
import ui.editor.run.memory.tree.MemoryCellRenderer;
import ui.editor.run.memory.tree.MemoryVector;
import core.Memory;
import flowchart.algorithm.run.GraphExecutor;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 * panel of memory
 *
 * Created on 14/set/2015, 13:32:57
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class MemoryDisplayPanel extends JPanel {

    GraphExecutor runningProgram;
    JTabbedPane tabs;

    public MemoryDisplayPanel(List<Memory> lst) {
        this.setLayout(new BorderLayout());
        createMemory(lst);
    }

    public void createMemory(List<Memory> lst) {
        this.removeAll();
        setLayout(new BorderLayout());
        tabs = new JTabbedPane(JTabbedPane.LEFT);
        tabs.setTabLayoutPolicy(javax.swing.JTabbedPane.WRAP_TAB_LAYOUT);
        tabs.setTabPlacement(javax.swing.JTabbedPane.TOP);
        for (Memory memory : lst) {
            tabs.add(memory.getMemoryName(), new SingleMemoryDisplay(memory));
        }
        this.add(tabs, BorderLayout.CENTER);

//        updateListeners();
        revalidate();
//        repaint();
    }

    public final void updateMemory() {

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509141332L;

    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
