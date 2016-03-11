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
package flowchart.returnFunc;

import core.Memory;
import core.data.Fsymbol;
import core.data.Fvoid;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import ui.FProperties;
import i18n.FkeyWord;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.function.Function;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import flowchart.terminator.End;
import flowchart.utils.ExpressionUtils;
import i18n.FkeywordToken;
import languages.PseudoLanguage;

/**
 *
 * @author ZULU
 */
public class Return extends Fshape {

    //----------------TYPE OF INSTRUCTION -------------------------
    static String KEYWORD = FkeyWord.get("KEYWORD.return");

    public Expression returnExpression;
    public Function myFunc;
    static MenuPattern menu = new MenuReturn();

    public Return(AlgorithmGraph algorithm) {
        super("", new ReturnShape(FProperties.returnColor), algorithm);
        if (algorithm.getBegin() instanceof Function) {
            myFunc = (Function) algorithm.getBegin();
        } else {
            myFunc = null;
        }
    }

    @Override
    public void editMenu(int x, int y) {
        // setSelected(true);
        menu.showDialog(this, x, y);
    }

    public Fsymbol getReturnOfFunction() {
        Fshape begin = getFunction(parent);
        if (begin == null) {
            return Fvoid.createVoid();
        }
        Function myFunc = (Function) begin;
        return myFunc.getReturnSymbol();
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        Memory mem = algorithm.getMemory(parent);
//        Fshape begin = getFunction(parent);
//        if (begin == null) {
//            return true;
//        }
//        Function myFunc = (Function) begin;
        Fsymbol ret = getReturnOfFunction();
        if (ret instanceof Fvoid) {
            returnExpression = null;
            setInstruction("");
            return true;
        }

        if (getInstruction().isEmpty()) {
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            throw new FlowchartException(
                    "RETURN.returnEInvalidType",
                    ret.getTypeName());
        }
        if (returnExpression == null) {
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            throw new FlowchartException("RETURN.noReturn");
        }
        //rebuild expression
        returnExpression = new Expression(returnExpression.getIdented(), mem, algorithm.myProgram);
        Fsymbol exp = returnExpression.getReturnType();
        if (!ret.acceptValue(exp.getDefaultValue())) {
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException(
                    "RETURN.returnEInvalidType",
                    ret.getTypeName(),
                    returnExpression.getIdented(),
                    exp.getTypeName());
        }
        return true;
    }

    @Override
    public void buildInstruction(String txtExpr, String comments) {
        StringBuilder instr = new StringBuilder();
        try {
            Memory mem = algorithm.getMemory(parent);
            returnExpression = new Expression(txtExpr, mem, algorithm.getMyProgram());
            instr.append(returnExpression.getIdented());
        } catch (Exception e) {
            instr.append(txtExpr);
        }
        setInstruction(instr.toString().trim());
        setComments(comments);
    }

    @Override
    public String getType() {
        return KEYWORD;
    }

    public Fshape getFunction(Fshape node) {
        if (node instanceof Function) { // function definition
            return node;
        }
        if (node == null) { //end of fluxo
            return null;
        }
        return getFunction(node.parent);
    }

    public Fshape getEnd(Fshape node) {
        if (node instanceof End) { // function definition
            return node;
        }
        if (node == null) { //end of fluxo
            return null;
        }
        return getEnd(node.next);
    }

    /**
     * define symbo to the running program
     *
     * @param exe
     * @return next Shape to execute
     * @throws FlowchartException Runtime error of instruction
     */
    public Fshape execute(GraphExecutor exe) throws FlowchartException {

        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
        //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
        Fshape nextShape = exe.expandExpression(this, returnExpression);
        if (nextShape != this) { // Function call
            return nextShape;
        }
        //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        Fshape begin = getFunction(parent);
        Fshape end = getEnd(next);
        if (returnExpression != null && !returnExpression.isEmpty()) {
            Function myFunc = (Function) begin;
            Fsymbol eval = returnExpression.evaluate(exe.getRuntimeMemory());
            myFunc.setReturnSymbolValue(eval);
            result = eval.getTextValue();
            setInstruction(result + "");
        }
        return end;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  T O O L T I P :::::::::::::::::
    String result = "";

    /**
     * Runtime tooltip
     *
     * @return plain text of runtime Tooltip
     */
    public String getExecutionResult() {
        return result;
    }

    @Override
    public String getIntructionPlainText() {
        if (returnExpression != null) {
            returnExpression.getIdented();
        }
        return "";
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String KEY = FkeywordToken.get("KEYWORD.return.key");

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    public String getPseudoTokens() {
        StringBuilder txt = new StringBuilder(PseudoLanguage.ident(this) + KEY);
        if (returnExpression != null) {
            txt.append(" " + ExpressionUtils.getExpressionTokens(returnExpression));
        }
        return txt.toString().trim();

    }
    //----------------TYPE OF INSTRUCTION -------------------------

    @Override
    public String getPseudoCode() throws FlowchartException {
        StringBuilder txt = new StringBuilder(PseudoLanguage.ident(this) + KEYWORD );
        if (returnExpression != null) {
            txt.append(" " + returnExpression.getIdented());
        }
        return txt.toString();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
