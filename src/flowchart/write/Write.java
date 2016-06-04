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
package flowchart.write;

import core.Memory;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import ui.FProperties;
import i18n.FkeyWord;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import flowchart.utils.ExpressionUtils;
import i18n.FkeywordToken;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import languages.AbstractLang;
import languages.PseudoLanguage;
import ui.FLog;

/**
 *
 * @author ZULU
 */
public class Write extends Fshape {

    //----------------TYPE OF INSTRUCTION -------------------------
    public static String KEYWORD = FkeyWord.get("KEYWORD.write");

    public Expression expressionToCalculate;

    static MenuPattern menu = new MenuWrite();

    public Write(AlgorithmGraph algorithm) {
        super("", new WriteShape(FProperties.writeColor), algorithm);
    }

    @Override
    public void editMenu(int x, int y) {
        setSelected(true);
        menu.showDialog(this, x, y);
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        Memory mem = algorithm.getMemory(parent);
        expressionToCalculate = new Expression(getInstruction(), mem, algorithm.getMyProgram());
        Fsymbol ret = expressionToCalculate.getReturnType();
        if (ret == null) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException(
                    "WRITE.noReturnExpression",
                    expressionToCalculate.getIdented()
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
            expressionToCalculate = new Expression(txtExpr, mem, algorithm.getMyProgram());
            instr.append(expressionToCalculate.getIdented());
        } catch (Exception e) {
            FLog.printLn("WRITE buildInstruction " +txtExpr + " EXCEPTION " +  e.getMessage());
            instr.append(txtExpr);
        }
        setInstruction(instr.toString().trim());
        setComments(comments);
    }

    @Override
    public String getType() {
        return KEYWORD;
    }

    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    @Override
    public Fshape execute(GraphExecutor exe) throws FlowchartException {
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
        //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
        Fshape nextShape = exe.expandExpression(this, expressionToCalculate);
        if (nextShape != this) { // Function call
            return nextShape;
        }
        //:::::::::::::::::::::::::::::::: FUNCTION CALL :::::::::::::::::::::::FUNCTION CALL :::::::::::::::::::::::
        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

        Fsymbol result = expressionToCalculate.evaluate(exe.getRuntimeMemory());
        //tooltip
        this.result = result.getTextValue();
        exe.getConsole().write(result.getTextValue());
        return next;
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  T O O L T I P :::::::::::::::::
    String result = "";

    @Override
    public String getExecutionResult() {
        return result;
    }

    @Override
    public String getIntructionPlainText() {
        return expressionToCalculate.getIdented();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String KEY = FkeywordToken.get("KEYWORD.write.key");

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    public String getPseudoTokens() {
        return  PseudoLanguage.ident(this) +KEY + " " + ExpressionUtils.getExpressionTokens(expressionToCalculate);
    }
//----------------TYPE OF INSTRUCTION -------------------------
    @Override
    public String getPseudoCode() throws FlowchartException {
        return PseudoLanguage.ident(this) + KEYWORD + " " + expressionToCalculate.getIdented();
    }
    
    @Override
    public String getLanguage() throws FlowchartException {
        return AbstractLang.lang.getCommentedString(this.comments,this)+AbstractLang.lang.ident(this) + AbstractLang.lang.getWrite(this);
    }
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
