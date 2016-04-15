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
package flowchart.decide;

import core.Memory;
import core.data.Flogic;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import ui.FProperties;
import i18n.Fi18N;
import i18n.FkeyWord;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.arrow.Arrow;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import flowchart.terminator.End;
import flowchart.utils.ExpressionUtils;
import i18n.FkeywordToken;
import languages.AbstractLang;
import languages.PseudoLanguage;

/**
 *
 * @author ZULU
 */
public class While_Do extends Fshape {

    //----------------TYPE OF INSTRUCTION -------------------------
    public static String KEYWORD = FkeyWord.get("KEYWORD.while");

    public Expression logicExpression;

    static MenuPattern menu = new MenuDecide();

    public While_Do(AlgorithmGraph algorithm) {
        super("", new DecideShape(FProperties.decisionColor), algorithm);
    }

    //-----------------------------------------------------------------------------
    @Override
    public void editMenu(int x, int y) {
        setSelected(true);
        menu.showDialog(this, x, y);
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        Memory mem = algorithm.getMemory(parent);
        logicExpression = new Expression(getInstruction(), mem, algorithm.getMyProgram());
        Fsymbol ret = logicExpression.getReturnType();
        if (ret == null || !(ret instanceof Flogic)) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException(
                    "DECIDE.noLogicReturn",
                    logicExpression.getIdented()
            );
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        }
        return true;
    }

    public void buildInstruction(String txtExpr, String comments) {
        StringBuilder instr = new StringBuilder();
        try {
            Memory mem = algorithm.getMemory(parent);
            logicExpression = new Expression(txtExpr, mem, algorithm.getMyProgram());
            instr.append(logicExpression.getIdented());
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


    ///::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * define symbo to the running program
     *
     * @param exe
     * @return next Shape to execute
     * @throws FlowchartException Runtime error of instruction
     */
    public Fshape execute(GraphExecutor exe) throws FlowchartException {
        exe.clearMemoryLevel(level + 1);
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
        //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
        Fshape nextShape = exe.expandExpression(this, logicExpression);
        if (nextShape != this) { // Function call
            return nextShape;
        }
        //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //exe.clearMemoryLevel(level+1);
        Memory mem = exe.getRuntimeMemory();
        Flogic decision = (Flogic) logicExpression.evaluate(mem);
        //update tooltip
        Result = decision.getTextValue();
        if (decision.isTrue()) {
            return right;
        } else {
            return next;
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  T O O L T I P :::::::::::::::::
    String Result = "";

    /**
     * Runtime tooltip
     *
     * @return plain text of runtime Tooltip
     */
    public String getExecutionResult() {
        return Result;
    }

    @Override
    public String getIntructionPlainText() {
        return logicExpression.getIdented();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String KEY = FkeywordToken.get("KEYWORD.while.key");

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    public String getPseudoTokens() {
        StringBuilder txt = new StringBuilder(PseudoLanguage.ident(this));
        txt.append(KEY + " " + ExpressionUtils.getExpressionTokens(logicExpression) + "\n");
        String inst = getWhileTokens();
        if (inst.isEmpty()) {
            txt.append("\n");
        } else {
            txt.append(inst);
        }
        txt.append(PseudoLanguage.ident(this) + End.KEY + " " + KEY + "\n");
        return txt.toString();
    }

    private String getWhileTokens() {
        StringBuilder txt = new StringBuilder();
        Fshape node = right;
        while (node != this) {
            if (!(node instanceof Arrow)) {
                txt.append(node.getPseudoTokensWithComments()+ "\n");
            }
            node = node.next;
        }

        return txt.toString();
    }
    
    
    @Override
    public String getPseudoCode() throws FlowchartException {
        StringBuilder txt = new StringBuilder(PseudoLanguage.ident(this));
        txt.append(Fi18N.get("WHILE.while") + " " + logicExpression.getIdented() + " " + Fi18N.get("WHILE.do") + "\n");
        String inst = getWhileInstructions();
        if (inst.isEmpty()) {
            txt.append("\n");
        } else {
            txt.append(inst);
        }
        txt.append(PseudoLanguage.ident(this) + FkeyWord.get("KEYWORD.end") + " " + Fi18N.get("WHILE.while"));
        return txt.toString();
    }
    
    @Override
    public String getLanguage() throws FlowchartException {
        StringBuilder txt = new StringBuilder(AbstractLang.lang.getCommentedString(this.comments,this)+AbstractLang.lang.ident(this));
        txt.append(AbstractLang.lang.getWhile(this) + "\n");
        String inst = getWhileHighLangInstructions();
        if (inst.isEmpty()) {
            txt.append("\n");
        } else {
            txt.append(inst);
        }
        txt.append(AbstractLang.lang.ident(this) + AbstractLang.lang.getEnd(this));
        return txt.toString();
    }
    
    private String getWhileHighLangInstructions() throws FlowchartException {
        StringBuilder txt = new StringBuilder();
        Fshape node = right;
        while (node != this) {
            if (!(node instanceof Arrow)) {
                txt.append(node.getLanguage() + "\n");
            }
            node = node.next;
        }
        return txt.toString();
    }
    
    private String getWhileInstructions() throws FlowchartException {
        StringBuilder txt = new StringBuilder();
        Fshape node = right;
        while (node != this) {
            if (!(node instanceof Arrow)) {
                txt.append(node.getPseudoCodeWithComments() + "\n");
            }
            node = node.next;
        }
        return txt.toString();
    }
   //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
