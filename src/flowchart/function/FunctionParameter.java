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
package flowchart.function;

import core.Memory;
import core.data.Fsymbol;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import core.parser.Mark;
import ui.FProperties;
import ui.flowchart.dialogs.Fdialog;
import i18n.FkeyWord;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.arrow.Arrow;
import flowchart.define.Define;
import flowchart.shape.Fshape;
import flowchart.terminator.TerminatorShape;
import java.util.ArrayList;

/**
 * Created on 26/set/2015, 17:37:13
 *
 * @author zulu - computer
 */
public class FunctionParameter extends Define {

    private Function myFunction; // function where the parameter belongs 
    private int index; // function where the parameter belongs 
    private Memory myPersonalMemory; // memory to evaluate expression ( algorithm tha call function)

    public FunctionParameter(final Define definition) {
        super(definition.algorithm, new TerminatorShape(FProperties.terminatorColor));
        this.setVarExpression(definition.getVarExpression());
        this.setVarSymbol(definition.getVarSymbol());
        this.setInstruction(definition.getInstruction());
        //  myPersonalMemory = null; // updated in running time        
    }

    public FunctionParameter(Fsymbol var, boolean isArray, AlgorithmGraph algorithm) {
        super(algorithm, new TerminatorShape(FProperties.terminatorColor));
        if (isArray) { // convert var to array
            var = Farray.createParameter(var);
        }
        this.setVarSymbol(var);
        this.setInstruction(toStringParameter());
        varExpression = new Expression(Fsymbol.createSymbolByType(var.getTypeName()));
        myPersonalMemory = null; // updated in running time        
    }

    public FunctionParameter getClone() {
        FunctionParameter par = new FunctionParameter(this);
        par.index = index;
        par.myPersonalMemory = myPersonalMemory;
        return par;
    }

    public boolean isArray() {
        return this.varSymbol instanceof Farray;
    }

    public void setisArray(boolean array) {
        if (isArray() && !array) {
            this.varSymbol = ((Farray) this.varSymbol).getTemplateElement();
        } else if (!isArray() && array) {
            this.varSymbol = Farray.createParameter(varSymbol);
        }
    }

    /**
     * build a shape to execute the parameter
     *
     * @param begin benfin shape
     * @param mem runtime memory
     * @param exp template to build a definition shape
     * @param index index of the parameter
     */
    public void expandParameter(Fshape begin, Memory mem, FunctionParameter exp, int index) {
        this.index = index;
        this.myFunction = (Function) (begin.parent);
        this.myPersonalMemory = mem;
        this.varSymbol = exp.varSymbol;
        this.varExpression = exp.varExpression;
        updateInstruction();
        algorithm.addSimpleShape((Arrow) begin, this);

    }

    /**
     * verify if the parameters is ok
     *
     * @return
     * @throws FlowchartException
     */
    public boolean parseShape() throws FlowchartException {
        return true;
    }

    public String toStringParameter() {
        StringBuilder txt = new StringBuilder();
        txt.append(this.varSymbol.getTypeName());
        txt.append(" ");
        txt.append(this.varSymbol.getName());
        if (varSymbol instanceof Farray) {
            txt.append("" + Mark.SQUARE_OPEN + Mark.SQUARE_CLOSE);
        }
        return txt.toString();
    }

    public String toStringParameterNoArray() {
        StringBuilder txt = new StringBuilder();
        txt.append(this.varSymbol.getTypeName());
        txt.append(" ");
        txt.append(this.varSymbol.getName());
        return txt.toString();
    }

    @Override
    public Fshape execute(GraphExecutor exe) throws FlowchartException {

        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
        //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
        Fshape nextShape = exe.expandExpression(this, varExpression);
        if (nextShape != this) { // Function call
            return nextShape;
        }
        //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//        if (varSymbol instanceof Farray) {            
//               Farray resultExpr = (Farray)myPersonalMemory.getByName(getVarExpression().toString()); // name of the original array
//                resultExpr = (Farray)resultExpr.getElementArray(new ArrayList<Expression>(), myPersonalMemory);
//                resultExpr.setName(varSymbol.getName());
//                varSymbol = resultExpr;
//                
//                varSymbol.setLevel(this.level);
//                varSymbol.setComments(comments);
//                 exe.addSymbolToMemory(varSymbol);
//            
//        } else {
        //use runtime memory to evaluate 
        Fsymbol resultExpr = getVarExpression().evaluate(myPersonalMemory);
        //update level and value and tooltip
        varSymbol.setLevel(this.level);
        if (varSymbol instanceof Farray) {
            ((Farray) varSymbol).setArrayValue((Farray) resultExpr);
        } else {
            varSymbol.setValue(resultExpr);
        }
        varSymbol.setComments(comments);
        defineResult = varSymbol.getName() + " " + FkeyWord.OPERATOR_SET + " " + resultExpr.getTextValue();
        exe.addSymbolToMemory(varSymbol);
        //UPDATE the name of the function
        if (varSymbol instanceof Farray) {                        
            //TODO 
           // varExpression = new Expression(varSymbol.getDefinitionValue(), exe.getRuntimeMemory(), exe.getTemplate());
        }else{
            varExpression = new Expression(varSymbol.getDefinitionValue(), exe.getRuntimeMemory(), exe.getTemplate());
            myFunction.updateParameter(index, this);
        }
        return next;
    }

    /**
     * Runtime tooltip
     *
     * @return plain text of runtime Tooltip
     */
    public String getExecutionResult() {
        return defineResult.trim();
    }

    @Override
    public String getIntructionPlainText() {
        StringBuilder txt = new StringBuilder(varSymbol.getTypeName() + " " + varSymbol.getName());
        if (varSymbol instanceof Farray) {
            txt.append(((Farray) varSymbol).getIndexesDefinition());
        } else {
            txt.append(" " + FkeyWord.OPERATOR_SET + " " + varExpression.getIdented());
        }
        return txt.toString().trim();
    }

    public String toString() {
        return toStringParameterNoArray();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
