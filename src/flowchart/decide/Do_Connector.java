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

import core.data.exception.FlowchartException;
import core.parser.Expression;
import ui.FProperties;
import i18n.FkeyWord;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.arrow.Arrow;
import flowchart.shape.Fshape;
import static flowchart.shape.Fshape.myFONT;
import i18n.FkeywordToken;
import languages.PseudoLanguage;
import java.awt.Dimension;
import java.awt.Insets;
import languages.AbstractLang;

/**
 *
 * @author ZULU
 */
public class Do_Connector extends Fshape {

    //----------------TYPE OF INSTRUCTION -------------------------
    static String KEYWORD = FkeyWord.get("KEYWORD.do");
    public Expression expression;

    public Do_Connector(AlgorithmGraph algorithm) {
        super("", new ConnectorShape(FProperties.connectorColor), algorithm);
    }

    //-----------------------------------------------------------------------------
    @Override
    //edit menu of the while
    public void editMenu(int x, int y) {
        next.next.editMenu(x, y);
    }

    public void popupMenu(int x, int y) {
        next.next.popupMenu(x, y);
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
    public String getIntructionPlainText() {
        return KEYWORD;
    }

    @Override
    public Fshape execute(GraphExecutor exe) throws FlowchartException {
        exe.clearMemoryLevel(level + 1);
        return right;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  T O O L T I P :::::::::::::::::
    /**
     * Runtime tooltip
     *
     * @return plain text of runtime Tooltip
     */
    public String getExecutionResult() {
        return FkeyWord.get("CONSTANT.true");
    } //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String KEY = FkeywordToken.get("KEYWORD.do.key");

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * gets the tokens of the instruction
     *
     * @return tokens of the instruction
     */
    public String getPseudoTokens() {
        StringBuilder txt = new StringBuilder(PseudoLanguage.ident(this));
        txt.append(KEY + "\n"); // do
        txt.append(getDoTokens()); // instructions
        //txt.append("\n"+this.next.next.getPseudoTokens()); // while
        return txt.toString();
    }

    private String getDoTokens() {
        StringBuilder txt = new StringBuilder();
        Fshape node = right;
        while (node != this.next.next) { // While
            if (!(node instanceof Arrow)) {
                txt.append("\n" + node.getPseudoTokensWithComments());
            }
            node = node.next;
        }

        return txt.toString();
    }
    
    
    @Override
    public String getPseudoCode() throws FlowchartException {
        StringBuilder txt = new StringBuilder(PseudoLanguage.ident(this));
        txt.append(KEYWORD);
        txt.append(getDoInstructions());
        return txt.toString();
    }

    private String getDoInstructions() throws FlowchartException {
        StringBuilder txt = new StringBuilder();
        Fshape node = right;
        while (node != this.next.next) { // While
            if (!(node instanceof Arrow)) {
                txt.append("\n" + node.getPseudoCodeWithComments());
            }
            node = node.next;
        }
        return txt.toString();
    }
    
     @Override
    public String getLanguage() throws FlowchartException {
        StringBuilder txt = new StringBuilder(AbstractLang.lang.getCommentedString(this.comments,this)+AbstractLang.lang.ident(this));
        txt.append(AbstractLang.lang.getDo(this) + "\n");
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
       while (node != this.next.next) {
            if (!(node instanceof Arrow)) {
                txt.append(node.getLanguage() + "\n");
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
