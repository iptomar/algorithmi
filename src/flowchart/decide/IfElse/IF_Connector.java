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

import core.data.exception.FlowchartException;
import ui.FProperties;
import i18n.FkeyWord;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.decide.ConnectorShape;
import flowchart.shape.Fshape;
import static flowchart.shape.Fshape.myFONT;
import java.awt.Dimension;
import java.awt.Insets;
import languages.AbstractLang;
import languages.PseudoLanguage;

/**
 *
 * @author ZULU
 */
public class IF_Connector extends Fshape {
 //----------------TYPE OF INSTRUCTION -------------------------
     static String KEYWORD = FkeyWord.get("KEYWORD.end");
   
    public IF_Connector(AlgorithmGraph algorithm) {
        super("", new ConnectorShape(FProperties.connectorColor), algorithm);
    }

  
    //-----------------------------------------------------------------------------

    @Override
    //edit menu of the while
    public void editMenu(int x, int y) {
        parent.editMenu(x, y);
    }

    public void popupMenu(int x, int y) {
        parent.popupMenu(x, y);
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        return true;
    }

    public void buildInstruction(String txtExpr, String comments) {
    }

    @Override
    public String getInstruction() {
        return "";
    }

    @Override
    protected Dimension getPreferedDimension() {
        Insets border = getBorder().getBorderInsets(this);
        return new Dimension(border.left + border.right + myFONT.getSize(), border.top + border.bottom + myFONT.getSize());
    }


    @Override
    public String getType() {
        return KEYWORD;
    }

    @Override
    public String getPseudoCode() throws FlowchartException {
           return ""; // if prints end
    }   
    @Override
    public String getIntructionPlainText() {
        return KEYWORD + " " + IfThenElse.KEYWORD;
    }

    @Override
    public Fshape execute(GraphExecutor exe) throws FlowchartException {
        exe.clearMemoryLevel(level+1);
        return next;
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  T O O L T I P :::::::::::::::::
    /**
     * Runtime tooltip
     *
     * @return plain text of runtime Tooltip
     */
    public String getExecutionResult() {
        return KEYWORD;
    }
    
     //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     *  gets the tokens of the instruction
     * @return tokens of the instruction
     */
     public  String getPseudoTokens(){
         return "";
     }
     
     @Override
    public String getLanguage() throws FlowchartException {
        return PseudoLanguage.ident(this) + AbstractLang.lang.getEnd(this);
    }
   //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
     
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    
}
