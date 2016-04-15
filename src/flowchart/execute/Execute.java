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
package flowchart.execute;

import ui.flowchart.console.Console;
import core.FunctionCall;
import core.Memory;
import core.data.Finteger;
import core.data.Fsymbol;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import core.parser.Mark;
import core.parser.tokenizer.BreakMarks;
import ui.FProperties;
import ui.flowchart.dialogs.Fdialog;
import i18n.FkeyWord;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.Program;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import flowchart.utils.ExpressionUtils;
import flowchart.utils.IteratorArrayIndex;
import i18n.FkeywordToken;
import languages.PseudoLanguage;
import java.util.ArrayList;
import java.util.List;
import languages.AbstractLang;

/**
 *
 * @author ZULU
 */
public class Execute extends Fshape {

    //----------------TYPE OF INSTRUCTION -------------------------
    static String KEYWORD = FkeyWord.get("KEYWORD.execute");

    @Override
    public String getType() {
        return KEYWORD;
    }
    //----------------TYPE OF INSTRUCTION -------------------------

    public Expression expressionToCalculate;
    public Fsymbol var = null;

    static MenuPattern menu = new MenuExecute();

    public Execute(AlgorithmGraph algorithm) {
        super(KEYWORD, new ExecuteShape(FProperties.executeColor), algorithm);
    }

    @Override
    public void editMenu(int x, int y) {
        setSelected(true);
        menu.showDialog(this, x, y);
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        if (expressionToCalculate == null) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException("EVALUATE.error.expressionNotDefined");
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        }
        Memory mem = algorithm.getMemory(parent);
        //parse expression elements
        expressionToCalculate.myProgram = algorithm.myProgram;
        expressionToCalculate.parse(mem);
        //calculate return type
        Fsymbol ret = expressionToCalculate.getReturnType(mem);
        if (var != null) {
            //get var from memory
            Fsymbol memoryVar = mem.getByName(var.getName());
            if (memoryVar == null) {
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                throw new FlowchartException(
                        "EXECUTE.variableNotExists",
                        var.getName());
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            }
            if ((memoryVar instanceof Farray) && !(var instanceof Farray)) {
                throw new FlowchartException("EXECUTE.array.notIndexes", var.getName());
            }
            if (!(memoryVar instanceof Farray) && (var instanceof Farray)) {
                throw new FlowchartException("EXECUTE.array.notArray", var.getName());
            }

            if (!memoryVar.acceptValue(ret.getDefaultValue())) {
                {
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    throw new FlowchartException(
                            super.getInstruction(),
                            "EXECUTE.returnTypeInvalid",
                            new String[]{
                                memoryVar.getTypeName(),
                                memoryVar.getName(),
                                ret.getTypeName(),
                                expressionToCalculate.getIdented(),}
                    );
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                }
            }
        }
//        if (ret != null && var == null) {
//            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
//            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//            throw new FlowchartException(
//                    "EXECUTE.returnNotCatch",
//                    expression.getIdented(),
//                    ret.getTypeName()
//           );
//            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//        }
        return true;
    }

    public void buildInstruction(String fullExpression, String comments) throws FlowchartException {
        Fsymbol variable = null;
        Memory mem = algorithm.getMemory(parent);
        String nameVar = "";
        String expression = "";
        //separate var from expression
        int indexSet = fullExpression.indexOf(FkeyWord.OPERATOR_SET);
        if (indexSet >= 0) {
            nameVar = fullExpression.substring(0, indexSet).trim();
            expression = fullExpression.substring(indexSet + FkeyWord.OPERATOR_SET.length()).trim();
        } else {
            expression = fullExpression.trim();
        }

        if (nameVar.contains(Mark.SQUARE_OPEN + "")) { //array
            variable = buildArray(nameVar, mem, algorithm.getMyProgram());
            //System.out.println("CREATE " + variable.getFullInfo());
        } else {
            variable = mem.getByName(nameVar);
        }
        buildInstruction(variable, expression, comments);
    }

    public static Fsymbol buildArray(String fullDefinition, Memory memory, Program prog) throws FlowchartException {
        try {
            String txtDefinition = fullDefinition.trim(); // to use in exceptions
            //check parentesis
            BreakMarks.checkParentesis(fullDefinition);
            if (!txtDefinition.contains(Mark.SQUARE_OPEN + "") || !txtDefinition.contains(Mark.SQUARE_CLOSE + "")) {
                throw new FlowchartException("EXECUTE.array.indexInvalidNotChar",
                        Mark.SQUARE_OPEN + "", Mark.SQUARE_OPEN + "");
            }
            String nameVar = fullDefinition.substring(0, fullDefinition.indexOf(Mark.SQUARE_OPEN)).trim();
            fullDefinition = fullDefinition.substring(fullDefinition.indexOf(Mark.SQUARE_OPEN)).trim(); // remove Name

            if (!fullDefinition.contains(Mark.SQUARE_OPEN + "")) {
                throw new FlowchartException("EXECUTE.array.indexInvalidStart", Mark.SQUARE_OPEN + "");
            }
            if (!fullDefinition.endsWith(Mark.SQUARE_CLOSE + "")) {
                throw new FlowchartException("EXECUTE.array.indexInvalidEnd", Mark.SQUARE_CLOSE + "");
            }
            //definition of the array in memory
            Fsymbol memSymb = memory.getByName(nameVar);
            if (!(memSymb instanceof Farray)) {
                throw new FlowchartException("EXECUTE.array.notArray", nameVar);
            }
            Farray definitionOfArray = (Farray) memSymb;
            //clone of the array to suport indexes
            Farray array = (Farray) definitionOfArray.clone();
//            System.out.println("\n CURRENT " + array.getFullInfo()); //--------------------------DEBUG
            IteratorArrayIndex it = new IteratorArrayIndex(fullDefinition);
            if (it.numElements() > array.getNumberOfIndexes()) {
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                throw new FlowchartException(
                        txtDefinition,
                        "EXECUTE.array.invalidNumberOfIndexes",
                        new String[]{
                            array.getName(),
                            array.getNumberOfIndexes() + "",
                            fullDefinition,
                            it.numElements() + "",}
                );
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::                
            }// invalid number of indexes
            List<Expression> indexes = new ArrayList<>();
            int numIndex = 0;
            while (it.hasNext()) {
                String index = it.next();
                //create expression
                Expression expressionOfIndex = new Expression(index, memory, prog);
                //return type of the expression
                Fsymbol rets = expressionOfIndex.getReturnType();
                if (!(expressionOfIndex.getReturnType() instanceof Finteger)) { //------not integer
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    throw new FlowchartException(
                            txtDefinition,
                            "EXECUTE.array.indexNotInteger",
                            new String[]{
                                index,
                                array.getName(),}
                    );
                }
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Finteger indexExpTxt = (Finteger) rets;
                Finteger dimDefined = definitionOfArray.getDimensionSymbol(numIndex);

                if (indexExpTxt.isConstant()) {//---expression defined by constants
                    if (indexExpTxt.getIntValue() < 0) { //--------------------- index negative
                        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                        //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
                        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                        throw new FlowchartException(
                                txtDefinition,
                                "EXECUTE.array.indexNegative",
                                new String[]{
                                    index,
                                    array.getName(),}
                        );
                    }
                    //---------------------------------------------------------- index not defined
                    if (dimDefined.isConstant() && indexExpTxt.getIntValue() >= dimDefined.getIntValue()) {
                        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                        //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
                        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                        throw new FlowchartException(
                                txtDefinition,
                                "EXECUTE.array.indexInvalid",
                                new String[]{
                                    index,
                                    array.getName(),
                                    dimDefined.getIntValue() + ""
                                }
                        );
                    }
                }//expression constant
                numIndex++;
                indexes.add(expressionOfIndex);
            }//verify indexes
            array.setIndexExpressions(indexes);
//            System.out.println("\n Complete " + array.getFullInfo()); //--------------------------DEBUG
//            System.out.println("In Memory \n" + definitionOfArray.getFullInfo()); //--------------------------DEBUG
            return array;
        } catch (FlowchartException e) {
            e.setInstruction(fullDefinition);
            throw e;
        }
    }

    public void buildInstruction(Fsymbol variable, String txtExpr, String comments) throws FlowchartException {
        StringBuilder instr = new StringBuilder();
        if (variable != null) {
            this.var = variable;
            instr.append(variable.getFullName() + " ");
            instr.append(FkeyWord.getSetOperator() + " ");
        }

        Memory mem = algorithm.getMemory(parent);
        expressionToCalculate = new Expression(txtExpr, mem, algorithm.getMyProgram());
        instr.append(expressionToCalculate.getIdented());

        setInstruction(instr.toString().trim());
        setComments(comments);
    }

    public String getExpressionToCalculate() {
        if (expressionToCalculate != null) {
            return expressionToCalculate.getIdented();
        } else {
            return "";
        }
    }

    public Fshape execute(GraphExecutor exe) throws FlowchartException {

        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
        //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
        Fshape nextShape = exe.expandExpression(this, expressionToCalculate);
        if (nextShape != this) { // Function call
            return nextShape;
        }
        //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        if (var != null) { // Expressions
            Fsymbol memVar = exe.getRuntimeMemory().getByName(var.getName());
            Fsymbol result = expressionToCalculate.evaluate(exe.getRuntimeMemory());
            //tooltip
            this.resultOfExpression = result.getTextValue();
            if (memVar != null) {
                if (memVar instanceof Farray) {
                    //convert to array 
                    Farray myArray = (Farray) var;
                    //get array from memory
                    Farray memoryArray = (Farray) memVar;
                    //update using myvar indexes
                    memoryArray.setElementValue(result, myArray.getIndexes(), exe.getRuntimeMemory());
                } else {
                    memVar.setValue(result);
                }
            } else {
                Fdialog.showMessage("Programming error EXECUTE " + getInstruction());
            }
        } // funtions
        return next;

//        List<FunctionCall> defs = expressionToCalculate.getDefinedFunctionToExecution();
//
//        if (defs.isEmpty()) {// not defined functions to execute
//
//            if (var != null) { // Expressions
//                Fsymbol memVar = exe.getRuntimeMemory().getByName(var.getName());
//                Fsymbol result = expressionToCalculate.evaluate(exe.getRuntimeMemory());
//                //tooltip
//                this.resultOfExpression = result.getTextValue();
//                if (memVar != null) {
//                    if (memVar instanceof Farray) {
//                        //convert to array 
//                        Farray myArray = (Farray) var;
//                        //get array from memory
//                        Farray memoryArray = (Farray) memVar;
//                        //update using myvar indexes
//                        memoryArray.setElementValue(result, myArray.getIndexes(), exe.getRuntimeMemory());
//                    } else {
//                        memVar.setValue(result);
//                    }
//                }               
//            } // funtions
//             return next;
//        } else {
//            //expand definitions
//            Fshape firstNode = parent;// previous arrow
//            for (int i = defs.size() - 1; i >= 0; i--) {                
//                //firstNode = exe.addNewFunction(defs.get(i), firstNode, exe.getRuntimeMemory()); // introced in the parent                      
//                firstNode = exe.addNewFunction(defs.get(i), this); // introced in the parent                      
//            }
//            return firstNode;
//        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  T O O L T I P :::::::::::::::::
    String resultOfExpression = "";

    /**
     * Runtime tooltip
     *
     * @return plain text of runtime Tooltip
     */
    public String getExecutionResult() {
        return resultOfExpression;
    }

    @Override
    public String getIntructionPlainText() {
        StringBuilder txt = new StringBuilder();
        if (var != null) {
            txt.append(var.getFullName() + " ");
            txt.append(FkeyWord.getSetOperator() + " ");
        }
        txt.append(expressionToCalculate.getIdented());
        return txt.toString();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String KEY = FkeywordToken.get("KEYWORD.execute.key");

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    public String getPseudoTokens() {
        StringBuilder toks = new StringBuilder(PseudoLanguage.ident(this) + KEY + " ");
        //add var
        if (var != null) {
            toks.append(var.getFullNameToken() + " " + FkeywordToken.OPERATOR_SET_KEY + " ");
        }
        //add Expression
        toks.append(ExpressionUtils.getExpressionTokens(expressionToCalculate));
        return toks.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    @Override
    public String getPseudoCode() throws FlowchartException {
        return PseudoLanguage.ident(this) + KEYWORD + " " + getIntructionPlainText();
    }
    
    @Override
    public String getLanguage() throws FlowchartException {
        return AbstractLang.lang.getCommentedString(this.comments,this)+AbstractLang.lang.ident(this) + AbstractLang.lang.getExecute(this);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
