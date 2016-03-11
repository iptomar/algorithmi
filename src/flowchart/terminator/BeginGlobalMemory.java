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
package flowchart.terminator;

import ui.flowchart.console.Console;
import core.Memory;
import core.data.exception.FlowchartException;
import i18n.FkeyWord;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.shape.Fshape;
import i18n.FkeywordToken;

/**
 *
 * @author ZULU
 */
public class BeginGlobalMemory extends Begin {

    //----------------TYPE OF INSTRUCTION -------------------------
    static String KEYWORD = FkeyWord.get("KEYWORD.memory");    
    static String MESSAGE = FkeyWord.get("KEYWORD.globalMemoryName") + "\n";   

    static MenuProgram menu = new MenuProgram();

    @Override
    public String getType() {
        return KEYWORD;
    }
    //----------------TYPE OF INSTRUCTION -------------------------

    public BeginGlobalMemory(AlgorithmGraph algorithm) {
        super(KEYWORD, algorithm);
    }

    @Override
    public void editMenu(int x, int y) {
        setSelected(true);
        menu.showDialog(this, x, y);
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        return true;
    }

    //do nothing
    public void popupMenu(int x, int y) {
    }


    public Fshape execute(GraphExecutor exe) throws FlowchartException {
        exe.initRuntimeProgram();
        exe.createFunctionToProgram(algorithm);
        //append message
        exe.getConsole().write(MESSAGE);
        return next;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * Execute node
     *
     * @param console console to display results
     * @param mem memory of node
     * @return next Shape to execute
     * @throws FlowchartException Runtime error of instruction
     */
    @Override
    public Fshape executeInstruction(Console console, Memory mem) throws FlowchartException {
        //create memory of the algorithm
        algorithm.myLocalMemory = new Memory(algorithm.getFunctionName());
        //clean console
        console.begin();
        //clear runtime memory
        mem.clear();
        //append message
        console.write(MESSAGE);
        return next;
    }
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    @Override
    public String getPseudoTokens() {
            return  KEY + " " + KEY_MEMORY_NAME + "\n";
    }
    public static String KEY = FkeywordToken.get("KEYWORD.memory.key");
    static String KEY_MEMORY_NAME = FkeywordToken.get("KEYWORD.globalMemoryName.key");    
    
 
   //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
