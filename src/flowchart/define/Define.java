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
package flowchart.define;

import core.Memory;
import core.data.Fsymbol;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import core.parser.Mark;
import ui.FProperties;
import i18n.FkeyWord;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.shape.BorderFlowChart;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import flowchart.utils.ExpressionUtils;
import i18n.FkeywordToken;
import languages.AbstractLang;
import ui.FLog;
import languages.PseudoLanguage;

/**
 *
 * @author ZULU
 */
public class Define extends Fshape {

    //----------------TYPE OF INSTRUCTION -------------------------
    static String KEYWORD = FkeyWord.get("KEYWORD.define");
    //----------------TYPE OF INSTRUCTION -------------------------
    protected Expression varExpression; // expressions to the initial value
    public Fsymbol varSymbol;

    static MenuPattern menu = new MenuDefineVar();

    public Define(AlgorithmGraph algorithm) {
        super(KEYWORD, new DefineShape(FProperties.defineColor), algorithm);
    }

    public Define(AlgorithmGraph algorithm, BorderFlowChart border) {
        super(KEYWORD, border, algorithm);
    }

    //-----------------------------------------------------------------------------
    @Override
    public String getType() {
        return KEYWORD;
    }

    @Override
    public void editMenu(int x, int y) {
        setSelected(true);
        menu.showDialog(this, x, y);
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        if (getVarSymbol() == null) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException(
                    "EXECUTE.variableNotExists",
                    super.getInstruction());
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        }
        Memory mem = algorithm.getMemory(parent);
        if (mem.isDefined(getVarSymbol().getName())) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException(
                    getVarSymbol().getTypeName() + " " + getVarSymbol().getName(),
                    "DEFINE.duplicatedName",
                    new String[]{
                        getVarSymbol().getName(),
                        mem.getByName(getVarSymbol().getName()).getInstruction()}
            );
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        }
        if (FkeyWord.isReservedWord(getVarSymbol().getName())) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException(
                    getVarSymbol().getTypeName() + " " + getVarSymbol().getName(),
                    "DEFINE.reservedWord",
                    new String[]{
                        getVarSymbol().getName(),}
            );
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        }
        if (getVarSymbol() instanceof Farray) { //---------------------------------array var
            ((Farray) getVarSymbol()).parseArray(mem); // verify index expressions
        } else {//---------------------------------------------------------------Single var
            //verify if all the variables are defined 
            varExpression.myProgram = algorithm.myProgram;
            varExpression.parse(mem);
            //verify if is all ok in the expression
            Fsymbol ret = getVarExpression().getReturnType(mem);
            if (getVarSymbol() == null) {
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                throw new FlowchartException(
                        "EXECUTE.returnNotCatch",
                        getVarExpression().getIdented(),
                        ret.getTypeName()
                );
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            }
            if (!varSymbol.acceptValue(ret.getDefaultValue())) {
                {
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    throw new FlowchartException(
                            super.getInstruction(),
                            "EXECUTE.returnTypeInvalid",
                            new String[]{
                                getVarSymbol().getTypeName(),
                                getVarSymbol().getName(),
                                ret.getTypeName(),
                                getVarExpression().getIdented(),}
                    );
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                }
            }
        }//-----------------------------------------------------------Single var
//        //set text
//        if (var instanceof Farray) {
//            //setInstruction(var.getInstruction());
//        } else {
//            setInstruction(var.getTypeName() + " " + var.getFunctionName() + " " + FkeyWord.OPERATOR_SET + " " + expression.getIdented());
//        }
        return true;
    }

    public void buildDefine(Fsymbol variable) {
        try {
            Memory mem = algorithm.getMemory(parent);
            varExpression = new Expression(variable.getDefinitionValue(), mem, this.algorithm.getMyProgram());
            buildInstruction(variable, varExpression.getIdented(), variable.getComments(), mem);
        } catch (Exception e) {
            FLog.runError("void buildDefine(Fsymbol variable) " + e.getMessage());
        }

    }

    public void buildInstruction(Fsymbol variable, String txtExpr, String comments, Memory mem) throws FlowchartException {

        StringBuilder instr = new StringBuilder();
        if (variable instanceof Farray) {
            instr.append(variable.getInstruction()); //definition of the array
        } else {
            instr.append(variable.getTypeName() + " ");
            instr.append(variable.getName() + " ");
            instr.append(FkeyWord.getSetOperator() + " ");
            setVarExpression(new Expression(txtExpr, mem, algorithm.getMyProgram()));
            instr.append(getVarExpression().getIdented());
        }
        setVarSymbol(variable);
        getVarSymbol().setComments(comments);
        varSymbol.setComments(comments);

        super.setInstruction(instr.toString().trim());
        setComments(comments);
    }

    public String getExpression() {
        String instr = super.getInstruction();
        String set = FkeyWord.getSetOperator();
        //Array
        if (instr.contains(Mark.SQUARE_OPEN + "")) {
            return instr.substring(instr.indexOf(Mark.SQUARE_OPEN));
        }
        //nao esta definido
        if (!instr.contains(set)) {
            return "";
        }
        return instr.substring(instr.indexOf(set) + set.length()).trim();
    }

    public void buildInstruction(String fullExpression, String comments) throws FlowchartException {
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
        //Log.printLn(Define.class.getSimpleName() + " buildInstruction " + fullExpression  + " Comments " + comments);
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
        Memory mem = algorithm.getMemory(parent);
        //simple instruction
        if (fullExpression.indexOf(FkeyWord.OPERATOR_SET) > 0) {
            String defVar = fullExpression.substring(0, fullExpression.indexOf(FkeyWord.OPERATOR_SET));
            String exp = fullExpression.substring(fullExpression.indexOf(FkeyWord.OPERATOR_SET) + FkeyWord.OPERATOR_SET.length());

            String typeName = defVar.substring(0, fullExpression.indexOf(" "));
            String nameVar = defVar.substring(fullExpression.indexOf(" ") + 1).trim();

            Fsymbol variable = Fsymbol.create(typeName.trim(), nameVar.trim());
            buildInstruction(variable, exp, comments, mem);
        } else {
            Fsymbol variable = new Farray(fullExpression, mem, algorithm.getMyProgram());
            buildInstruction(variable, "", comments, mem);

        }
        parseShape();
    }

    /**
     * @return the varExpression
     */
    public Expression getVarExpression() {
        return varExpression;
    }

    /**
     * @param varExpression the varExpression to set
     */
    public void setVarExpression(Expression expression) {
        this.varExpression = expression;
        updateInstruction();
    }

    /**
     * @return the varSymbol
     */
    public Fsymbol getVarSymbol() {
        return varSymbol;
    }

    /**
     * @param varSymbol the varSymbol to set
     */
    public void setVarSymbol(Fsymbol var) {
        this.varSymbol = var;
        updateInstruction();
    }

    public void updateInstruction() {
        if (varSymbol != null && varExpression != null) {
            setInstruction(varSymbol.getTypeName() + " "
                    + varSymbol.getName() + " "
                    + FkeyWord.OPERATOR_SET + " " + varExpression.getIdented());
        }
    }

    @Override
    public Fshape execute(GraphExecutor exe) throws FlowchartException {
        if (varSymbol instanceof Farray) {
            //clone is necessary because the execution evaluate the indexes and
            //replaces the expression by values
            Farray array = (Farray) varSymbol.clone();
            //use runtime memory to evaluate indexes
            array.createArrayElements(exe.getRuntimeMemory(), algorithm.getMyProgram());
            //update level and tooltip
            array.setLevel(this.level);
            defineResult = array.getName() + array.getIndexesDefinition();
            array.setComments(comments);
            //add symbol to memory
            exe.addSymbolToMemory(array);
        } else {
            //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
            //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
            Fshape nextShape = exe.expandExpression(this, varExpression);
            if (nextShape != this) { // Function call
                return nextShape;
            }
            //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
            //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //use runtime memory to evaluate indexes
            Fsymbol dimension = getVarExpression().evaluate(exe.getRuntimeMemory());
            //update level and value and tooltip
            varSymbol.setLevel(this.level);
            varSymbol.setValue(dimension);
            varSymbol.setComments(comments);
            defineResult = varSymbol.getName() + " " + FkeyWord.OPERATOR_SET + " " + dimension.getTextValue();
            exe.addSymbolToMemory(varSymbol);
        }

        return next;
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  T O O L T I P :::::::::::::::::
    protected String defineResult = KEYWORD;

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

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public String toString() {
        StringBuilder txt = new StringBuilder();
        if (varSymbol != null) {
            txt.append(varSymbol.getTypeName());
            txt.append(" " + varSymbol.getName());
        }
        if (varExpression != null) {
            txt.append(" " + FkeyWord.OPERATOR_SET + " ");
            txt.append(" " + varExpression.getIdented());
        }
        return txt.toString();
    }

    public boolean isCompatible(Define other) {
            return varSymbol.isCompatible(other.varSymbol);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String KEY = FkeywordToken.get("KEYWORD.define.key");

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    public String getPseudoTokens() {
        if (varExpression != null) {
            return PseudoLanguage.ident(this) + KEY + " "
                    + varSymbol.getTypeToken() + " "
                    + varSymbol.getFullNameToken() + " "
                    + FkeywordToken.OPERATOR_SET_KEY + " "
                    + ExpressionUtils.getExpressionTokens(varExpression);
        } else {
            //arrays
            return PseudoLanguage.ident(this) + KEY +
                    " " + varSymbol.getTypeToken() +
                    " "+ varSymbol.getFullNameToken();
        }
    }

    @Override
    public String getPseudoCode() throws FlowchartException {
        return PseudoLanguage.ident(this) + KEYWORD + " " + getIntructionPlainText();
    }
    
     @Override
    public String getLanguage() throws FlowchartException {
        return AbstractLang.lang.ident(this) + AbstractLang.lang.getDefine(this);
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
