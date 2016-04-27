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
package flowchart.decide.IfElse;

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
import flowchart.decide.DecideShape;
import flowchart.decide.MenuDecide;
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
public class IfThenElse extends Fshape {

    //----------------TYPE OF INSTRUCTION -------------------------
    public static String KEYWORD = FkeyWord.get("KEYWORD.if");

    public Expression logicExpression;

    static MenuPattern menu = new MenuDecide();

    public IfThenElse(AlgorithmGraph algorithm) {
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

        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
        //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
        Fshape nextShape = exe.expandExpression(this, logicExpression);
        if (nextShape != this) { // Function call
            return nextShape;
        }
        //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

        Memory mem = exe.getRuntimeMemory();
        Flogic decision = (Flogic) logicExpression.evaluate(mem);
        //update tooltip
        resultString = decision.getTextValue();
        if (decision.isTrue()) {
            return right;
        } else {
            return left;
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  T O O L T I P :::::::::::::::::
    String resultString = "";

    /**
     * Runtime tooltip
     *
     * @return plain text of runtime Tooltip
     */
    public String getExecutionResult() {
        return resultString;
    }

    /**
     * Intruction int plain text
     *
     * @return
     */
    public String getIntructionPlainText() {
        return logicExpression.getIdented();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String KEY = FkeywordToken.get("KEYWORD.if.key");
    public static String KEY_ELSE = FkeywordToken.get("KEYWORD.else.key");

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    public String getPseudoTokens() {
        StringBuilder txt = new StringBuilder(PseudoLanguage.ident(this));
        String ifThen = getThenToken();
        String ifElse = getElseToken();
        txt.append(KEY + " " + ExpressionUtils.getExpressionTokens(logicExpression) + "\n");
        if (!ifThen.isEmpty()) {
            txt.append(ifThen);
            txt.append("\n");
        }
        if (!ifElse.isEmpty()) {
            txt.append(PseudoLanguage.ident(this) + KEY_ELSE + "\n");
            txt.append(ifElse);
            txt.append("\n");
        }
        txt.append(PseudoLanguage.ident(this) + End.KEY + " " + KEY);
        return txt.toString();
    }

    private String getThenToken() {
        StringBuilder txt = new StringBuilder();
        Fshape node = right;
        while (node != next) {
            if (!(node instanceof Arrow)) {
                if (txt.length() > 0) { // append \n to previous line
                    txt.append("\n");
                }
                txt.append(node.getPseudoTokens());
            }
            node = node.next;
        }
        return txt.toString();
    }

    private String getElseToken() {
        StringBuilder txt = new StringBuilder();
        Fshape node = left;
        while (node != next) {
            if (!(node instanceof Arrow)) {
                if (txt.length() > 0) { // append \n to previous line
                    txt.append("\n");
                }
                txt.append(node.getPseudoTokensWithComments());
            }
            node = node.next;
        }
        return txt.toString();
    }

    //----------------TYPE OF INSTRUCTION -------------------------
    @Override
    public String getPseudoCode() throws FlowchartException {
        StringBuilder txt = new StringBuilder(PseudoLanguage.ident(this));
        String ifThen = getThen();
        String ifElse = getElse();
        txt.append(Fi18N.get("IF.if") + " " + logicExpression.getIdented() + " " + Fi18N.get("IF.then") + "\n");
        if (!ifThen.isEmpty()) {
            txt.append(ifThen);
            txt.append("\n");
        }
        if (!ifElse.isEmpty()) {
            txt.append(PseudoLanguage.ident(this) + Fi18N.get("IF.else") + "\n");
            txt.append(ifElse);
            txt.append("\n");
        }
        txt.append(PseudoLanguage.ident(this) + FkeyWord.get("KEYWORD.end") + " " + Fi18N.get("IF.if"));
        return txt.toString();
    }
    
    @Override
    public String getLanguage() throws FlowchartException {
        StringBuilder txt = new StringBuilder(AbstractLang.lang.getCommentedString(this.comments,this)+AbstractLang.lang.ident(this));
        String ifThen = getThenLang();
        String ifElse = getElseLang();
        txt.append(AbstractLang.lang.getIf(this) + "\n");
        if (!ifThen.isEmpty()) {
            txt.append(ifThen);
            //txt.append(AbstractLang.lang.ident(this) + AbstractLang.lang.getEnd(this));
        }
        if (!ifElse.isEmpty()) {
            txt.append("\n"+AbstractLang.lang.ident(this)+AbstractLang.lang.getElse(this) + "\n");
            txt.append(ifElse);
        }
        //txt.append(AbstractLang.lang.ident(this) + AbstractLang.lang.getEnd(this));
        return txt.toString();
    }
    
    private String getThenLang() throws FlowchartException {
        StringBuilder txt = new StringBuilder();
        Fshape node = right;
        while (node != next) {
            if (!(node instanceof Arrow)) {
                if (txt.length() > 0) { // append \n to previous line
                    txt.append("\n");
                }
                txt.append(node.getLanguage());
            }
            node = node.next;
        }
        return txt.toString();
    }

    private String getElseLang() throws FlowchartException {
        StringBuilder txt = new StringBuilder();
        Fshape node = left;
        while (node != next) {
            if (!(node instanceof Arrow)) {
                if (txt.length() > 0) { // append \n to previous line
                    txt.append("\n");
                }
                txt.append(node.getLanguage());
            }
            node = node.next;
        }
        return txt.toString();
    }

    private String getThen() throws FlowchartException {
        StringBuilder txt = new StringBuilder();
        Fshape node = right;
        while (node != next) {
            if (!(node instanceof Arrow)) {
                if (txt.length() > 0) { // append \n to previous line
                    txt.append("\n");
                }
                txt.append(node.getPseudoCodeWithComments());
            }
            node = node.next;
        }
        return txt.toString();
    }

    private String getElse() throws FlowchartException {
        StringBuilder txt = new StringBuilder();
        Fshape node = left;
        while (node != next) {
            if (!(node instanceof Arrow)) {
                if (txt.length() > 0) { // append \n to previous line
                    txt.append("\n");
                }
                txt.append(node.getPseudoCodeWithComments());
            }
            node = node.next;
        }
        return txt.toString();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
