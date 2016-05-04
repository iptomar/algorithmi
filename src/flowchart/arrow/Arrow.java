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
package flowchart.arrow;

import core.data.exception.FlowchartException;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.shape.BorderFlowChart;
import flowchart.shape.Fshape;
import java.awt.Graphics;

/**
 *
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public abstract class Arrow extends Fshape{

    //----------------TYPE OF INSTRUCTION -------------------------
    static String type = "";

    @Override
    public String getType() {
        return type;
    }
    //----------------TYPE OF INSTRUCTION -------------------------

    public abstract void updatePosition();

    public abstract void setLink(Fshape begin, Fshape end);

    public static MenuArrow menu = new MenuArrow();

    public Arrow(BorderFlowChart border, Fshape begin, Fshape end) {
        super("", border, begin.algorithm);
        this.parent = begin;
        this.next = end;
        this.level = begin.level; // by default level of arrow is the level of start
    }


    //always is ok
    public String parseShape(String txt) throws FlowchartException {
        return txt;
    }

    //do nothing
    @Override
    public void popupMenu(int x, int y) {
    }

    @Override
    public void editMenu(int x, int y) {
        menu.showDialog(this, x, y);
    }
    public void buildInstruction(String instr, String comments) throws FlowchartException{
        //do nothing
    }

    /**
     * overrided to dinamicaly calculate positions
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        updateSize();
        super.paintComponent(g);
    }

    @Override
    public String getPseudoCode() throws FlowchartException {
        return "";
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public  Fshape execute(GraphExecutor exe) throws FlowchartException{
        return next;
    }    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  T O O L T I P :::::::::::::::::
    /**
     * Runtime tooltip
     *
     * @return plain text of runtime Tooltip
     */
    public String getExecutionResult() {
        return "";
    }
    /**
     * Intruction int plain text
     * @return 
     */
    public  String getIntructionPlainText(){
        return "";
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     *  gets the tokens of the instruction
     * @return tokens of the instruction
     */
     public  String getPseudoTokens(){
         return "";
     }
   //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
     
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

   
}
