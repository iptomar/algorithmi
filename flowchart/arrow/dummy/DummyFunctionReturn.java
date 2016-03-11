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
package flowchart.arrow.dummy;

import core.FunctionCall;
import flowchart.function.FunctionCallShape;
import core.data.exception.FlowchartException;
import ui.FProperties;
import i18n.Fi18N;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.function.Function;
import flowchart.shape.Fshape;
import java.awt.Color;

/**
 * Created on 26/set/2015, 11:10:43
 *
 * @author zulu - computer
 */
public class DummyFunctionReturn extends Fshape {

    public static Color fillColor = FProperties.terminatorColor;
    FunctionCall funcCall;
    Function functionHead;
    //----------------TYPE OF INSTRUCTION -------------------------
    static String KEYWORD = Fi18N.get("RUNTIME.dummyFunctionCall");

    @Override
    public String getType() {
        return KEYWORD;
    }

    //----------------TYPE OF INSTRUCTION -------------------------

    /**
     *
     * @param call element of expression that call function
     * @param func Head of the fuction that has the return var
     */
    public DummyFunctionReturn(FunctionCall call, Function func) {
        super("", new FunctionCallShape(FProperties.selectedColor), func.algorithm);
        this.functionHead = func;
        this.funcCall = call;

    }

    @Override
    public Fshape execute(GraphExecutor exe) throws FlowchartException {
        //update value of the function call
        funcCall.myReturn.setValue( functionHead.getReturnSymbol().getValue());
        exe.removeLastFunction();
        return next;
    }

    @Override
    public void editMenu(int x, int y) {
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        return true;
    }

    //do nothing
    public void popupMenu(int x, int y) {
    }

    @Override
    public String getPseudoCode() throws FlowchartException {
        return "";
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  T O O L T I P :::::::::::::::::
    public String getExecutionResult() {
        return "";
    }
    public void buildInstruction(String instr, String comments) throws FlowchartException{
        //do nothing
    }

    /**
     * Intruction int plain text
     *
     * @return
     */
    public String getIntructionPlainText() {
        return "";
    }
     //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     *  gets the tokens of the instruction
     * @return tokens of the instruction
     */
     public  String getPseudoTokens(){
         return "";
     }
   //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
