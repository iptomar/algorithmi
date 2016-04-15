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
package flowchart.read;

import core.Memory;
import core.data.Fcharacter;
import core.data.Fsymbol;
import core.data.Ftext;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import core.parser.Mark;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.execute.Execute;
import flowchart.shape.Fshape;
import flowchart.shape.MenuPattern;
import ui.FProperties;

import i18n.FkeyWord;
import i18n.FkeywordToken;
import languages.AbstractLang;
import languages.PseudoLanguage;

/**
 *
 * @author ZULU
 */
public class Read extends Fshape {

    //----------------TYPE OF INSTRUCTION -------------------------
    public final static String KEYWORD = FkeyWord.get("KEYWORD.read");

    public Fsymbol var;

    static MenuPattern menu = new MenuRead();

    public Read(AlgorithmGraph algorithm) {
        super(KEYWORD, new ReadShape(FProperties.readColor), algorithm);
//        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    //-----------------------------------------------------------------------------
    @Override
    public void editMenu(int x, int y) {
        setSelected(true);
        menu.showDialog(this, x, y);
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        buildInstruction(getInstruction(), comments);
        Memory mem = algorithm.getMemory(parent);
//        if (mem.getMem().isEmpty()) {
//            var = null;
//            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
//            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//            throw new FlowchartException(
//                    "READ.noneVariablesDefined");
//        }
        //update definition of var
        if (var == null) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException(
                    "READ.variableNotExists"
            );
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        }
        //update definition of var
        if (mem.getByName(var.getName()) == null) {
//            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
//            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//            throw new FlowchartException(
//                    "READ.variableNotExists",
//                    var.getName());
//            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            setInstruction(var.getTypeName() + " " + var.getName());
            return true;
        }
        if (var instanceof Farray) {
            Farray array = (Farray) var;
            Farray memArray = (Farray) mem.getByName(var.getName());
            if (array.getNumberOfIndexes() != memArray.getNumberOfIndexes()) {
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                throw new FlowchartException(
                        "READ.variableArrayWrongIndexes",
                        var.getName(),
                        memArray.getNumberOfIndexes() + "",
                        array.getNumberOfIndexes() + "");

                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            }
        }
        setInstruction(var.getFullName());
        return true;
    }

    public void buildInstruction(String fullExpression, String comments) throws FlowchartException {
        fullExpression = fullExpression.trim();
//        //--------------------------  get KEYWORD --------------------------------------------------
//        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//        if (fullExpression.indexOf(' ') < 0) {
//            throw new FlowchartException("EXCEPTION.invalidInstruction", fullExpression, KEYWORD);
//        }
//        String keyword = fullExpression.substring(0, fullExpression.indexOf(' '));
//        if (!keyword.equalsIgnoreCase(KEYWORD)) {
//            throw new FlowchartException("EXCEPTION.invalidKeyword", KEYWORD, fullExpression);
//        }
//        fullExpression = fullExpression.substring(fullExpression.indexOf(' ') + 1).trim();
//        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//        //-------------------------------------------------------------------------------------------

        Memory mem = algorithm.getMemory(parent);

        //define new var
        if (fullExpression.indexOf(Mark.SQUARE_OPEN) < 0 && fullExpression.indexOf(' ') > 0) {
            String newType = fullExpression.substring(0, fullExpression.indexOf(' ')).trim();
            String newName = fullExpression.substring(fullExpression.indexOf(' ') + 1).trim();
            //define if not defined
            if (mem.getByName(newName) == null) {
                var = Fsymbol.create(newType, newName);
                setInstruction(newType + " " + newName);
                var.setLevel(level);
                setComments(comments);
                return;
            }
        }
        //variable defined   
        Fsymbol variable;
        if (fullExpression.contains(Mark.SQUARE_OPEN + "")) { //array
            variable = Execute.buildArray(fullExpression, mem, algorithm.getMyProgram());
            //System.out.println("CREATE " + variable.getFullInfo());
        } else {
            variable = mem.getByName(fullExpression.trim());
            if (variable instanceof Farray) {
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                throw new FlowchartException(
                        "READ.variableArrayWrongIndexes",
                        variable.getName(),
                        ((Farray) variable).getNumberOfIndexes() + "",
                        "0");

                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            }

        }
        buildInstruction(variable, comments);
    }

    public void buildInstruction(Fsymbol variable, String comments) {
        StringBuilder instr = new StringBuilder();
        if (variable != null) {
            this.var = variable;
            instr.append(variable.getFullName());
        }
        setInstruction(instr.toString().trim());
        setComments(comments);
    }

    @Override
    public String getType() {
        return KEYWORD;
    }

    ///::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * define symbo to the running program
     *
     * @param exe
     * @return next Shape to execute
     * @throws FlowchartException Runtime error of instruction
     */
    public Fshape execute(GraphExecutor exe) throws FlowchartException {

        Fsymbol memoryVar = exe.getRuntimeMemory().getByName(var.getName());
        if (memoryVar != null) {
            //if is an array, get Element
            if (memoryVar instanceof Farray) {
                Farray array = (Farray) memoryVar;
                //get indexes of var
                memoryVar = array.getElement(((Farray) var).getIndexes(), exe.getRuntimeMemory());
            }
        } else {
            memoryVar = var;
            memoryVar.setLevel(this.level);
            exe.addSymbolToMemory(memoryVar);
        }
        //Read from console
        String result = exe.getConsole().read(memoryVar, getComments());
        if( result == null){
            throw new FlowchartException("READ.abortedInput");
        }

        if (memoryVar instanceof Ftext) {
            result = Ftext.addTerminators(result);
        } else if (memoryVar instanceof Fcharacter) {
            result = Fcharacter.addTerminators(result.charAt(0));
        }
        if (memoryVar instanceof Farray) {
            ((Farray) memoryVar).setElementValue(result, ((Farray) var).getIndexes(), exe.getRuntimeMemory());
        } else {
            memoryVar.setValue(result);
        }
        return next;
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
        return getInstruction().replaceAll("\n", " ");
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String KEY = FkeywordToken.get("KEYWORD.read.key");

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    public String getPseudoTokens() {
        return  PseudoLanguage.ident(this) +KEY + " " + FkeywordToken.translateWordsToTokens(getIntructionPlainText());
    }

    //----------------TYPE OF INSTRUCTION -------------------------
    @Override
    public String getPseudoCode() throws FlowchartException {
        return  PseudoLanguage.ident(this) + KEYWORD + " " + getIntructionPlainText();
    }
    
    
    @Override
    public String getLanguage() throws FlowchartException {
        return AbstractLang.lang.getCommentedString(this.comments,this)+AbstractLang.lang.ident(this) + AbstractLang.lang.getRead(this);
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
