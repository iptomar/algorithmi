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
package flowchart.decide.forNext;

import core.Memory;
import core.data.FabstractNumber;
import core.data.Finteger;
import core.data.Flogic;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import core.evaluate.aritmetic.Sum;
import core.evaluate.relational.GreaterOrEqual;
import core.evaluate.relational.LessOrEqual;
import core.parser.Expression;
import ui.FProperties;
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
public class For_Next extends Fshape {
//----------------TYPE OF INSTRUCTION -------------------------

    static String KEYWORD = FkeyWord.get("KEYWORD.for");
    static String _FROM = FkeyWord.get("KEYWORD.from");
    static String _TO = FkeyWord.get("KEYWORD.to");
    static String _STEP = FkeyWord.get("KEYWORD.step");

    public Expression start;
    public Expression stop;
    public Expression step;
    public Fsymbol var;
    //-------------------------------  EXECUTION variables------------
    private boolean firstTimeToExecute = true; // to define or update iteration variable
    public boolean defineVariableToMemory = false; // to define iteration variable   
    //-----------------------------------------------------------------------
    static MenuPattern menu = new MenuForNext();

    public For_Next(AlgorithmGraph algorithm) {
        super("", new For_NextShape(FProperties.iterationColor), algorithm);
    }

    @Override
    public void editMenu(int x, int y) {
        setSelected(true);
        menu.showDialog(this, x, y);
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        if (start == null) {
            throw new FlowchartException("FOR.exception.expressionsNotDefined");
        }
        Fsymbol result;
        Memory mem = algorithm.getMemory(parent);
        if (mem.getByName(var.getName()) != null) {
            defineVariableToMemory = false;
        } else {
            defineVariableToMemory = true;
        }
        firstTimeToExecute = true;

        //start = new Expression(getInstruction(), mem, algorithm.getMyProgram());
        result = start.getReturnType();
        if (result == null || !(result instanceof FabstractNumber)) {
            throw new FlowchartException(
                    "FOR.exception.resultNotNumeric",
                    start.getIdented(),
                    result.getTypeName()
            );
        }
        result = stop.getReturnType();
        if (result == null || !(result instanceof FabstractNumber)) {
            throw new FlowchartException(
                    "FOR.exception.resultNotNumeric",
                    stop.getIdented(),
                    result.getTypeName()
            );
        }
        result = step.getReturnType();
        if (result == null || !(result instanceof FabstractNumber)) {
            throw new FlowchartException(
                    "FOR.exception.resultNotNumeric",
                    step.getIdented(),
                    result.getTypeName()
            );
        }

        return true;
    }

    public void buildInstruction(Fsymbol _var, Expression _from, Expression _to, Expression _step, String comments) throws FlowchartException {
        StringBuilder txt = new StringBuilder();
//        txt.append(FkeyWord.get("KEYWORD.for") + " ");
        Memory mem = algorithm.getMemory(parent);
        if (mem.getByName(_var.getName()) == null) {
            txt.append(_var.getTypeName() + " ");
        }
        txt.append(_var.getName() + " ");
        txt.append(_FROM);
        txt.append(_from.getIdented() + " \n");
        txt.append(_TO);
        txt.append(_to.getIdented() + " ");
        txt.append(_STEP);
        txt.append(_step.getIdented());

        var = _var;
        start = _from;
        stop = _to;
        step = _step;
        setInstruction(txt.toString().trim());
        setComments(comments);
        parseShape();
    }

    @Override
    public void buildInstruction(String txtExpr, String comments) throws FlowchartException {

        if (txtExpr.startsWith(KEYWORD)) {
            txtExpr = txtExpr.substring(KEYWORD.length()).trim();
        }
        StringBuilder instr = new StringBuilder();

        String exp = txtExpr.trim();
        String _var = exp.substring(0, exp.indexOf(_FROM)).trim();//var

        exp = exp.substring(exp.indexOf(_FROM) + _FROM.length()).trim(); // CUT

        String expStart = exp.substring(0, exp.indexOf(_TO));// start expression
        exp = exp.substring(exp.indexOf(_TO) + _TO.length()).trim(); // CUT

        String expStop = exp.substring(0, exp.indexOf(_STEP));// stop expression
        String expStep = exp.substring(exp.indexOf(_STEP) + _STEP.length()).trim(); // step

        try {
            Memory mem = algorithm.getMemory(parent);
            instr.append(_var + " ");
            //create variable
            if (_var.indexOf(" ") > 0) { // definition
                String type = _var.substring(0, _var.indexOf(" ")).trim();
                _var = _var.substring(_var.indexOf(" ")).trim();
                var = Fsymbol.create(type, _var);
            } else {
                var = mem.getByName(_var);
            }
            start = new Expression(expStart, mem, algorithm.getMyProgram());
            instr.append(_FROM + " " + start.getIdented() + "\n");
            stop = new Expression(expStop, mem, algorithm.getMyProgram());
            instr.append(_TO + " " + stop.getIdented() + " ");
            step = new Expression(expStep, mem, algorithm.getMyProgram());
            instr.append(_STEP + " " + step.getIdented());

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

    /**
     * define symbo to the running program
     *
     * @param exe
     * @return next Shape to execute
     * @throws FlowchartException Runtime error of instruction
     */
    public Fshape execute(GraphExecutor exe) throws FlowchartException {

        Fsymbol _step = step.evaluate(exe.getRuntimeMemory());

        //first executions
        if (firstTimeToExecute) {
            Fsymbol _start = start.evaluate(exe.getRuntimeMemory());
            if (!defineVariableToMemory) {
                var = exe.getRuntimeMemory().getByName(var.getName());
            } else {
                exe.addSymbolToMemory(var);
                var.setLevel(this.level);
            }
            var.setValue(_start);
            firstTimeToExecute = false;
        } //another executions
        else {
            Sum sumOperator = new Sum(); // operator sum
            Fsymbol newVal = sumOperator.add(var, _step);
            var.setValue(newVal);
        }
        //clean memory inside cicle
        exe.clearMemoryLevel(level + 1);

        //verify condition
        Fsymbol _stop = stop.evaluate(exe.getRuntimeMemory());
        Flogic decision;
        Fsymbol zero = new Finteger(0);
        GreaterOrEqual ge = new GreaterOrEqual();
        //select comparator - lessOrEqual or greatherEqual
        if (ge.compare(_step, zero).booleanValue()) {
            decision = ge.compare(_stop, var);
            //update tooltip   
            resultValue = var.getValue() + " " + LessOrEqual.symbol + " " + _stop.getTextValue() + " =  " + decision.getValue();
        } else {
            decision = ge.compare(var, _stop);
            //update tooltip   
            resultValue = var.getValue() + " " + GreaterOrEqual.symbol + " " + _stop.getTextValue() + " =  " + decision.getValue();
        }

        //decid the mext shape
        if (decision.isTrue()) {
            return right;
        } else {
            cleanFor(exe);
            return next;
        }
    }

    public void cleanFor(GraphExecutor exe) {
        //clear defined variable if necessary
        if (defineVariableToMemory) {
            exe.removeSymbolFromMemory(var);
        }
        //clear execution flag
        firstTimeToExecute = true;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  T O O L T I P :::::::::::::::::
    String resultValue = "";

    /**
     * Runtime tooltip
     *
     * @return plain text of runtime Tooltip
     */
    public String getExecutionResult() {
        return resultValue;
    }

    @Override
    public String getIntructionPlainText() {
        return getInstruction().replaceAll("\n", " ");
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String KEY = FkeywordToken.get("KEYWORD.for.key");
    public static String KEY_FROM = FkeywordToken.get("KEYWORD.from.key");
    public static String KEY_TO = FkeywordToken.get("KEYWORD.to.key");
    public static String KEY_STEP = FkeywordToken.get("KEYWORD.step.key");

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    public String getPseudoTokens() {
        Memory mem = algorithm.getMemory(parent);

        StringBuilder txt = new StringBuilder(PseudoLanguage.ident(this));
        txt.append(KEY + " ");
        //append definition
        if (mem.getByName(var.getName()) == null) {
            txt.append(var.getTypeToken() + " ");
        }
        txt.append(var.getName() + " " + KEY_FROM + " ");
        txt.append(ExpressionUtils.getExpressionTokens(start) + " " + KEY_TO + " ");
        txt.append(ExpressionUtils.getExpressionTokens(stop) + " " + KEY_STEP + " ");
        txt.append(ExpressionUtils.getExpressionTokens(step) + "\n");
        String inst = getForTokens();
        if (inst.isEmpty()) {
            txt.append("\n");
        } else {
            txt.append(inst);
        }
        txt.append(PseudoLanguage.ident(this) + End.KEY + " " + KEY);
        return txt.toString();
    }

    private String getForTokens() {
        StringBuilder txt = new StringBuilder();
        Fshape node = right;
        while (node != this) {
            if (!(node instanceof Arrow)) {
                txt.append(node.getPseudoTokensWithComments() + "\n");
            }
            node = node.next;
        }

        return txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    @Override
    public String getPseudoCode() throws FlowchartException {
        StringBuilder txt = new StringBuilder(PseudoLanguage.ident(this));
        txt.append(FkeyWord.get("KEYWORD.for") + " " + getIntructionPlainText() + "\n");
        String instructions = getForInstructions();
        txt.append(instructions);
        if (instructions.isEmpty()) {
            txt.append("\n");
        }
        txt.append(PseudoLanguage.ident(this) + FkeyWord.get("KEYWORD.end") + " " + FkeyWord.get("KEYWORD.for"));
        return txt.toString();
    }
    
    @Override
    public String getLanguage() throws FlowchartException {
        StringBuilder txt = new StringBuilder(AbstractLang.lang.getCommentedString(this.comments,this)+AbstractLang.lang.ident(this));
        txt.append(AbstractLang.lang.getFor(this) + "\n");
        String instructions = getForLangInstructions();
        txt.append(instructions);
        if (instructions.isEmpty()) {
            txt.append("\n");
        }
        txt.append(AbstractLang.lang.ident(this) + AbstractLang.lang.getEnd(this));
        return txt.toString();
    }
    
    private String getForLangInstructions() throws FlowchartException {
        StringBuilder txt = new StringBuilder();
        Fshape node = right;
        while (node != this) {
            if (!(node instanceof Arrow)) {
                String txtNode = node.getLanguage();
                if (!txtNode.isEmpty()) {
                    txt.append(txtNode + "\n");
                }
            }
            node = node.next;
        }
        return txt.toString();
    }

    private String getForInstructions() throws FlowchartException {
        StringBuilder txt = new StringBuilder();
        Fshape node = right;
        while (node != this) {
            if (!(node instanceof Arrow)) {
                String txtNode = node.getPseudoCodeWithComments();
                if (!txtNode.isEmpty()) {
                    txt.append(txtNode + "\n");
                }
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
