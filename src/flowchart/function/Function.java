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

import core.FunctionCall;
import core.Memory;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import core.parser.Mark;
import i18n.FkeyWord;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.Program;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import flowchart.terminator.Begin;
import flowchart.terminator.End;
import flowchart.utils.ExpressionUtils;
import i18n.FkeywordToken;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import languages.PseudoLanguage;

/**
 *
 * @author ZULU
 */
public class Function extends Begin {

    //----------------TYPE OF INSTRUCTION -------------------------
    static String KEYWORD_FUNCTION = FkeyWord.get("KEYWORD.function");

    private String functionName = "";  //fuction name
    private Fsymbol returnSymbol = core.data.Fvoid.createVoid();// return symbol   
    private boolean isReturnArray = false;
    public List<FunctionParameter> parameters = new ArrayList<>(); //list of parameters

    static MenuPattern menu = new MenuFunction();

    public Function() {
        super(null);
    }

    public Function(AlgorithmGraph algorithm) {
        super(algorithm.getFunctionName(), algorithm);
        functionName = algorithm.getFunctionName();
        setInstruction(getTextualInstruction());
    }

    @Override
    public void editMenu(int x, int y) {
        setSelected(true);
        menu.showDialog(this, x, y);
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        buildInstruction(getInstruction(), comments);
        return true;
    }

    public void updateDefinition(Function template) throws FlowchartException {
        if (!functionName.equalsIgnoreCase(template.functionName) //------------------ duplicated name
                && algorithm.myProgram.getFunctionByName(template.functionName) != null) {
            throw new FlowchartException("FUNCTION.invalidDuplicateName", template.functionName);
        }
        functionName = template.functionName;
        returnSymbol = template.returnSymbol;
        isReturnArray = template.isReturnArray;
        parameters = template.parameters;
        algorithm.setName(functionName);
        comments = template.comments;
        setInstruction(getTextualInstruction());
    }

    public void buildInstruction(String fullExpression, String comments) throws FlowchartException {
        this.comments = comments;
        fullExpression = fullExpression.trim();
        // 0 - no spaces in definition
        if (!fullExpression.contains(" ")) {
            throw new FlowchartException("KEYWORD.notDefinedError");
        }
        String txtInstr = fullExpression;
        //---------------------------------------------------------------------- no parenthesis
        if (txtInstr.indexOf(Mark.ROUND_OPEN) < 0 || txtInstr.indexOf(Mark.ROUND_CLOSE) < 0) {
            throw new FlowchartException("FUNCTION.invalidBrackets",
                    Mark.ROUND_OPEN + " ", Mark.ROUND_CLOSE + "");
        }
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  
        //1- -----------------------------------------------------------------------------------------------return 
        //calculate return type   
        int index = 0;
        while (txtInstr.charAt(index) != Mark.SQUARE_OPEN && txtInstr.charAt(index) != ' ') {
            index++;
        }
        String returnType = txtInstr.substring(0, index).trim(); // get Return Type
        txtInstr = txtInstr.substring(index).trim(); // cut returnType
        if (txtInstr.charAt(0) == Mark.SQUARE_OPEN) { //---------------------------ARRAY IN THE RETURN
            txtInstr = txtInstr.substring(1).trim(); // cut [
            if (txtInstr.charAt(0) != Mark.SQUARE_CLOSE) {
                throw new FlowchartException("FUNCTION.invalidReturn.Array", returnType + Mark.SQUARE_OPEN);
            }
            isReturnArray = true; //------------------- RETURN IS ARRAY
            txtInstr = txtInstr.substring(1).trim(); // cut ]
        }
        this.setReturnSymbol(Fsymbol.createSymbolByType(returnType));
        if (this.getReturnSymbol() == null) {
            throw new FlowchartException("FUNCTION.invalidReturn", returnType);
        }
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //2- ----------------------------------------------------------------------------------------------- name
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        if (txtInstr.indexOf(Mark.ROUND_OPEN) < 0 || txtInstr.indexOf(Mark.ROUND_CLOSE) < 0) {
            throw new FlowchartException("FUNCTION.invalidBrackets",
                    Mark.ROUND_OPEN + " ", Mark.ROUND_CLOSE + "");
        }

        this.setFunctionName(txtInstr.substring(0, txtInstr.indexOf(Mark.ROUND_OPEN)).trim());
        txtInstr = txtInstr.substring(txtInstr.indexOf(Mark.ROUND_OPEN));//cut function name
        //Validate Name
        validateName(getFunctionName());
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //------------------------------------------------------------------------------------------------ parameters      
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        if (!txtInstr.startsWith(Mark.ROUND_OPEN + "") || !txtInstr.endsWith(Mark.ROUND_CLOSE + "")) {
            throw new FlowchartException("FUNCTION.invalidBrackets",
                    Mark.ROUND_OPEN + " ", Mark.ROUND_CLOSE + "");
        }

        txtInstr = txtInstr.substring(1, txtInstr.length() - 1).trim(); // cut parenthesis
        //3 parametros
        parameters.clear();
        if (!txtInstr.isEmpty()) { // not empty parameters0
            String defs[] = txtInstr.split(Mark.COMMA_CHAR + ""); // Split by comma
            for (String defParameter : defs) {
                String original = defParameter; // save original parameter
                boolean parameterIsArray = false;
                String parameterName = "";

                defParameter = defParameter.trim();

                if (!defParameter.contains(" ")) {
                    throw new FlowchartException("FUNCTION.invalidParameterDef", defParameter);
                }
                //parameter type
                String parameterType = defParameter.substring(0, defParameter.indexOf(" "));
                defParameter = defParameter.substring(parameterType.length() + 1);

                // parameter name
                if (defParameter.indexOf(Mark.SQUARE_OPEN) > 0) {
                    parameterName = defParameter.substring(0, defParameter.indexOf(Mark.SQUARE_OPEN)).trim();
                    defParameter = defParameter.substring(defParameter.indexOf(Mark.SQUARE_OPEN));
                } else {
                    parameterName = defParameter.trim();
                    defParameter = "";
                }

                if (!defParameter.isEmpty() && defParameter.charAt(0) == Mark.SQUARE_OPEN) { //---------------------------ARRAY IN THE PARAMETER
                    defParameter = defParameter.substring(1).trim(); // cut [
                    if (defParameter.charAt(0) != Mark.SQUARE_CLOSE) {
                        throw new FlowchartException("FUNCTION.invalidParameter.Array", original);
                    }
                    parameterIsArray = true; //------------------- PARAMETER IS ARRAY                    
                }

                validateName(parameterName); // validade name of var
                Fsymbol param = Fsymbol.create(parameterType, parameterName);
                //-------------------------------------------------------------------------------------------build define instructions
                FunctionParameter defParam = new FunctionParameter(param, parameterIsArray, algorithm);
                //------------------------------------------------------------------------------------------------------ build function parameter
                parameters.add(defParam);
            }
        }//parameters empty

        setInstruction(getTextualInstruction().trim());
    }

    public String getTextualInstruction() {
        StringBuilder txt = new StringBuilder();
        txt.append(returnSymbol.getTypeName());
        if (isReturnAnArray()) {
            txt.append("" + Mark.SQUARE_OPEN + Mark.SQUARE_CLOSE);
        }
        txt.append(" " + functionName);
        txt.append(Mark.ROUND_OPEN + toTxtPrameters() + Mark.ROUND_CLOSE);
        return txt.toString();
    }

    public String toTxtPrameters() {
        StringBuilder txt = new StringBuilder();
        for (int i = 0; i < getParameters().size(); i++) {
            txt.append(" " + getParameters().get(i).toString());
            if (i < getParameters().size() - 1) {
                txt.append(" " + Mark.COMMA_CHAR + " ");
            }
        }
        return txt.toString().trim();
    }

    /**
     * name( p, p, p)
     *
     * @return
     */
    public String getDefinitionWithTypes() {
        StringBuilder params = new StringBuilder();
        params.append(getReturnSymbol().getTypeName() + " ");
        params.append(getFunctionName() + " " + Mark.ROUND_OPEN);
        for (int i = 0; i < getParameters().size(); i++) {
            params.append(" " + getParameters().get(i).getVarSymbol().getTypeName());
            params.append("_" + getParameters().get(i).getVarSymbol().getName() + " ");
            if (i < getParameters().size() - 1) {
                params.append(Mark.COMMA_CHAR + " ");
            }
        }
        return params.toString().trim() + " " + Mark.ROUND_CLOSE;
    }

    /**
     * name( p, p, p)
     *
     * @return
     */
    public String getDefinitionWithValues() {
        StringBuilder params = new StringBuilder();
        params.append(getFunctionName() + Mark.ROUND_OPEN);
        for (int i = 0; i < getParameters().size(); i++) {
            params.append(" " + getParameters().get(i).getVarSymbol().getDefinitionValue() + " ");
            if (i < getParameters().size() - 1) {
                params.append(Mark.COMMA_CHAR + " ");
            }
        }
        return params.toString().trim() + " " + Mark.ROUND_CLOSE;
    }

    public void validateName(String txt) throws FlowchartException {
        Fsymbol.validadeName(txt);
        Program prog = algorithm.getMyProgram();
        prog.validateName(txt);
        for (FunctionParameter parameter : getParameters()) {
            if (parameter.getVarSymbol().getName().equalsIgnoreCase(txt)) {
                throw new FlowchartException("FUNCTION.invalidNameDuplicateParameter", txt);
            }
        }
    }

    @Override
    public String getType() {
        return KEYWORD_FUNCTION;
    }

    //----------------TYPE OF INSTRUCTION -------------------------
    @Override
    public String getPseudoCode() throws FlowchartException {
        return KEYWORD_FUNCTION + " " + getInstruction();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * Execute the shape in the program
     *
     * @param exe executable
     * @return
     * @throws FlowchartException
     */
    @Override
    public Fshape execute(GraphExecutor exe) throws FlowchartException {
        exe.createFunctionToProgram(algorithm);
        return next;
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public void expandParameters(Memory mem, FunctionCall call) {
        StringBuilder txt = new StringBuilder();
        txt.append(functionName + Mark.ROUND_OPEN + " ");

        this.memoryOfFunctionCaller = mem.getClone();
        List<FunctionParameter> callParams = call.getParameters();
        for (int i = 0; i < callParams.size(); i++) {
            if (i < callParams.size() - 1) {
                txt.append(parameters.get(i).getVarSymbol().getName() + " , ");
            } else {
                txt.append(parameters.get(i).getVarSymbol().getName());
            }
            parameters.get(i).expandParameter(next, this.memoryOfFunctionCaller, callParams.get(i), i);
        }
        txt.append(" " + Mark.ROUND_CLOSE);
        setInstruction(call.toString());
        border.setSelected();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * @return the functionName
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * @return the returnSymbol
     */
    public Fsymbol getReturnSymbol() {
        return returnSymbol;
    }

    /**
     * @return the parameters
     */
    public List<FunctionParameter> getParameters() {
        return parameters;
    }

    /**
     * @param returnSymbol the returnSymbol to set
     */
    public void setReturnSymbol(Fsymbol returnSymbol) throws FlowchartException {
        this.returnSymbol = returnSymbol;
    }

    /**
     * @param returnSymbol the returnSymbol to set
     */
    public void setReturnSymbolValue(Fsymbol newValue) throws FlowchartException {
        returnSymbol.setValue(newValue.getValue());
    }

    /**
     * @param functionName the functionName to set
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(List<FunctionParameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return getTextualInstruction();
    }

    /**
     * @return the isReturnArray
     */
    public boolean isReturnAnArray() {
        return isReturnArray;
    }

    /**
     * @param isArray the isReturnArray to set
     */
    public void setIsArray(boolean isArray) {
        this.isReturnArray = isArray;
    }

    public void updateSize() {
        Dimension oldDims = getSize();
        Dimension dims = getPreferedDimension();
        int cx = getX();
        int cy = getY();
        setSize(dims.width, dims.height);
        setLocation(cx - (dims.width - oldDims.width) / 2,
                cy - (dims.height - oldDims.height) / 2);
    }

    public void updateParameter(int index, FunctionParameter fp) {
        parameters.set(index, fp);
        setRawInstruction(getDefinitionWithValues());
        updateSize();
        algorithm.myLocalMemory.setMemoryName(getDefinitionWithValues());
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String KEY = FkeywordToken.get("KEYWORD.function.key");

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    public String getPseudoTokens() {
        StringBuilder txt = new StringBuilder(KEY + " ");

        txt.append(FkeywordToken.getTokenOfWord(returnSymbol.getTypeName()));
        if (isReturnAnArray()) {
            txt.append(" " + Mark.SQUARE_OPEN_TOKEN + " " + Mark.SQUARE_CLOSE_TOKEN);
        }
        txt.append(" " + functionName + " ");
        txt.append(Mark.ROUND_OPEN_TOKEN + " ");
        txt.append(FkeywordToken.translateWordsToTokens(toTxtPrameters()));
        txt.append(Mark.ROUND_CLOSE_TOKEN);
        return txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201518071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
