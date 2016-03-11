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
package flowchart.jump;

import core.data.exception.FlowchartException;
import ui.FProperties;
import i18n.FkeyWord;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.decide.Do_Connector;
import flowchart.decide.While_Do;
import flowchart.decide.forNext.For_Next;
import static flowchart.read.Read.KEY;
import flowchart.shape.MenuPattern;
import flowchart.shape.Fshape;
import flowchart.terminator.Begin;
import flowchart.utils.ExpressionUtils;
import i18n.FkeywordToken;
import languages.PseudoLanguage;

/**
 *
 * @author ZULU
 */
public class Jump extends Fshape {

    public static final String breakTxt = FkeyWord.get("KEYWORD.break");
    public static final String continueTxt = FkeyWord.get("KEYWORD.continue");

    String typeOfJump = breakTxt;

    static MenuPattern menu = new MenuJump();

    public Jump(AlgorithmGraph algorithm) {
        super("", new JumpShape(FProperties.jumpColor), algorithm);
    }

    //-----------------------------------------------------------------------------
    @Override
    public void editMenu(int x, int y) {
        setSelected(true);
        menu.showDialog(this, x, y);
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        String txt = getInstruction();
        if( txt.indexOf(" ") > 0){
            txt = txt.substring(0, txt.indexOf(" "));
        }
        if (txt.equalsIgnoreCase(breakTxt) || txt.equalsIgnoreCase(continueTxt)) {
            Fshape cicle = getCicle(parent);
            return cicle != null;
        }
        return false;
    }

    public Fshape getCicle(Fshape shape) {
//        if (shape == null) {
//            return null;
//        }
        return getCicle(shape, shape.level);
    }

    public Fshape getCicle(Fshape shape, int level) {
        if (shape instanceof Begin) {
            return null;
        }
        
         if (shape.level >= level) { // ignore same level
            return getCicle(shape.parent, level);
        }
         
        if (shape instanceof While_Do || shape instanceof Do_Connector || shape instanceof For_Next) {
            return shape;
        }

       

        return getCicle(shape.parent, level);
    }

    public void buildInstruction(String txtExpr, String comments) {
        Fshape cicle = getCicle(this);
        typeOfJump = txtExpr.trim();
        setInstruction(typeOfJump + " " + cicle.type);
        setComments(comments);
    }

    @Override
    public String getType() {
        return typeOfJump;
    }

    /**
     * define symbo to the running program
     *
     * @param exe
     * @return next Shape to execute
     * @throws FlowchartException Runtime error of instruction
     */
    @Override
    public Fshape execute(GraphExecutor exe) throws FlowchartException {

        Fshape cicle = getCicle(parent);
        if (cicle == null) {
            throw new FlowchartException("JUMP.exception.noCicle");
        }
        executionTXT = typeOfJump + " " + cicle.getIntructionPlainText();
        if (cicle instanceof While_Do) {
            if (getInstruction().equalsIgnoreCase(breakTxt)) {
                return cicle.next;
            }
            return cicle;
        }
        if (cicle instanceof Do_Connector) {
            if (getInstruction().equalsIgnoreCase(breakTxt)) {
                return cicle.next.next.next;
            }
            return cicle.next.next;
        }
        if (cicle instanceof For_Next) {

            if (getInstruction().equalsIgnoreCase(breakTxt)) {
                For_Next _for = (For_Next) cicle;
                _for.cleanFor(exe);
                return cicle.next;
            }
            return cicle;
        }
        throw new FlowchartException("JUMP.exception.noCicle");
    }
    String executionTXT = "";

    /**
     * Runtime tooltip
     *
     * @return plain text of runtime Tooltip
     */
    public String getExecutionResult() {
        return executionTXT;
    }

    @Override
    public String getIntructionPlainText() {
        return typeOfJump;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String KEY_BREAK = FkeywordToken.get("KEYWORD.break.key");
    public static String KEY_CONTINUE = FkeywordToken.get("KEYWORD.continue.key");
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    public String getPseudoTokens() {
        String key = FkeywordToken.translateWordsToTokens(getIntructionPlainText());
        return PseudoLanguage.ident(this) + key + " " + key;
    }

    //----------------TYPE OF INSTRUCTION -------------------------
    @Override
    public String getPseudoCode() throws FlowchartException {
        return PseudoLanguage.ident(this) + typeOfJump;
    }
   //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
