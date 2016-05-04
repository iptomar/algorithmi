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
package flowchart.arrow.LR;

import ui.flowchart.console.Console;
import core.Memory;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import ui.FProperties;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.arrow.Arrow;
import flowchart.function.Function;
import flowchart.shape.Fshape;
import java.awt.Dimension;

/**
 * NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED 
 * NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED NOT USED 
 * 
 * ArrowEndOfFunction arrow from the end of function to the shape that call the
 * function Right - pointers to the next shape to execute next - point to the
 * shpa that call the function (necessary to paint if next of the callfunction
 * is null)
 *
 * @author ZULU
 */
public final class ArrowEndOfFunction extends Arrow {

    Memory oldMemory;  // memory before call function
    Fsymbol returnVar; // return var to set the value of the expression
    Fshape myFunc;

    public ArrowEndOfFunction(Fshape begin, Fshape end, Memory memory, Fsymbol varReturn, Fshape startFunc) {
        super(new ShapeArrowLR(FProperties.arrowColor), begin, end);
        setLink(begin, end);
        oldMemory = memory.getClone();
        this.returnVar = varReturn;
        this.algorithm = begin.algorithm;
        myFunc = startFunc;
        isEditable = false;
    }
   

    public void setLink(Fshape begin, Fshape end) {
        begin.right = this;
        end.parent = this;

        this.level = begin.level;
        this.next = end;
        updatePosition();
    }

    @Override
    public void updatePosition() {
        int x0 = next.getX() + next.getWidth();
        int y0 = Math.min(parent.getY() + parent.getHeight() / 2, next.getY() + next.getHeight() / 2);
        setLocation(x0, y0 - border.getBorderSize() / 2);
    }

    @Override
    protected Dimension getPreferedDimension() {
        if (parent != null && next != null) {
            int w = parent.getX() - (next.getX() + next.getWidth());
            int h = Math.max(parent.getY() + parent.getHeight() / 2, next.getY() + next.getHeight())
                    - Math.min(parent.getY() + parent.getHeight() / 2, next.getY() + next.getHeight());

            return new Dimension(w, h);

        } else {
            return super.getPreferedDimension();
        }
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        return true;
    }

    /**
     * Execute node
     *
     * @param console console to display results
     * @param mem memory of node
     * @return
     */
    public Fshape executeInstruction(Console console, Memory mem) {
        return right;
    }

    public Fshape execute(GraphExecutor exe) throws FlowchartException {        
        if (returnVar != null) {
            returnVar.setValue(((Function) myFunc).getReturnSymbol().getValue());
        }
        exe.removeLastFunction();
        
        return right;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
