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
package flowchart.algorithm;

import core.Memory;
import core.data.exception.FlowchartException;
import ui.flowchart.dialogs.Fdialog;
import flowchart.arrow.Arrow;
import flowchart.arrow.BT.ArrowNext;
import flowchart.function.Function;
import flowchart.shape.Fshape;
import flowchart.terminator.End;
import javax.swing.JPanel;

/**
 *
 * @author zulu
 */
public class FunctionGraph extends AlgorithmGraph {

    /**
     * create a new function graph
     *
     * @param container canvas
     * @param start definition of the function
     * @throws FlowchartException
     */
    public FunctionGraph(JPanel container, Program prog) {
        super(container, prog);
      //  initProgram();
    }

    /**
     * update the definition of function used to menu to change parameters
     *
     * @param template
     */
    public void updateDefinition(Function template) {
        try {
            
            Function begin = (Function) getBegin();
            begin.updateDefinition(template);
            begin.repaint();
//            name = template.getFunctionName(); // update name
//            begin.buildInstruction(template.getTextualInstruction(), template.getComments());
//            myLocalMemory.setMemoryName(begin.getDefinitionWithValues());
            alignPatterns();
        } catch (FlowchartException ex) {
            Fdialog.compileException(ex);
        }

    }
    
    

    @Override
    public void initProgram() {
//        try {
        clear();
        Function begin = new Function(this); //begin        
        begin.level = 1;
//            begin.buildInstruction("funcao()", "");
        Fshape end = new End(this); //end
        end.level = 1;
        Arrow arrow = new ArrowNext(begin, end); // begin -> end
        add(begin);
        add(arrow);
        add(end);
        myLocalMemory = new Memory(name); // create memory
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
