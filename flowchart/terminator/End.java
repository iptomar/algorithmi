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
package flowchart.terminator;

import core.data.exception.FlowchartException;
import ui.FProperties;
import i18n.Fi18N;
import i18n.FkeyWord;
import flowchart.algorithm.FunctionGraph;
import flowchart.algorithm.GlobalMemoryGraph;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.shape.Fshape;
import i18n.FkeywordToken;
import java.text.SimpleDateFormat;
import java.util.Date;
import languages.PseudoLanguage;

/**
 *
 * @author ZULU
 */
public class End extends Fshape {

    //----------------TYPE OF INSTRUCTION -------------------------
    static String KEYWORD = FkeyWord.get("KEYWORD.end");
    static String MESSAGE = "\n" + Fi18N.get("END.endProgram") + "\n";

    @Override
    public String getType() {
        return KEYWORD;
    }
    //----------------TYPE OF INSTRUCTION -------------------------

    public End(AlgorithmGraph algorithm) {
        super(KEYWORD, new TerminatorShape(FProperties.terminatorColor), algorithm);
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        return true;
    }

    //do nothing
    @Override
    public void editMenu(int x, int y) {
    }

    //do nothing
    public void popupMenu(int x, int y) {
    }
    public void buildInstruction(String instr, String comments) throws FlowchartException{
        //do nothing
    }
//
//    //do nothing
//    public void setSelected(boolean selected) {
//
//    }

   

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::   
    @Override
    public Fshape execute(GraphExecutor exe) throws FlowchartException {

        if (algorithm instanceof GlobalMemoryGraph) {
            //execute main
            return exe.addMain();
        }
        if (algorithm instanceof FunctionGraph) {
            exe.getRuntimeMemory().clearLevel(level);
            return next;

        } else {
            //  exe.getRuntimeMemory().clearLevel(level);
            exe.getConsole().write(MESSAGE);
            exe.getConsole().end();
            return null;
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  T O O L T I P :::::::::::::::::
    @Override
    public String getExecutionResult() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        return sdfDate.format(now);
    }

    @Override
    public String getIntructionPlainText() {
        return algorithm.getFunctionName();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    @Override
    public String getPseudoTokens() {
        String funcName = algorithm.getFunctionName();
        if (funcName.equalsIgnoreCase(MEMORY)) {
            return  PseudoLanguage.ident(this)  +KEY + " " + KEY_MEMORY ;
        }
        if (funcName.equalsIgnoreCase(MAIN)) {
            return  PseudoLanguage.ident(this)  +KEY + " " + KEY_MAIN ;
        }
        return  PseudoLanguage.ident(this)  + KEY + " " + funcName ;
    }
    
     @Override
    public String getPseudoCode() throws FlowchartException {
        return PseudoLanguage.ident(this)  + KEYWORD + " " + getIntructionPlainText();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public static String KEY = FkeywordToken.get("KEYWORD.end.key");
    static String KEY_MEMORY = FkeywordToken.get("KEYWORD.globalMemoryName.key");
    static String KEY_MAIN = FkeywordToken.get("KEYWORD.mainFunctionName.key");
    static String MEMORY = FkeyWord.get("KEYWORD.globalMemoryName");
    static String MAIN = FkeyWord.get("KEYWORD.mainFunctionName");
   //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
