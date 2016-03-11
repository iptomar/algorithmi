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
package flowchart.arrow.LB;

import core.data.exception.FlowchartException;
import ui.FProperties;
import flowchart.arrow.Arrow;
import flowchart.shape.BorderFlowChart;
import flowchart.shape.Fshape;
import java.awt.Dimension;

/**
 *
 * @author ZULU
 */
public class ArrowLB_while extends Arrow {

    public ArrowLB_while(Fshape node, Fshape whileDo) {
        super(new ShapeArrowLB_while(FProperties.arrowColor), node, whileDo);
        setLink(node, whileDo);
    }

    /**
     * node ---> whileDo
     *
     * @param node
     * @param whileDo
     */
    @Override
    public void setLink(Fshape node, Fshape whileDo) {
        node.next = this;
        this.parent = node;
        this.next = whileDo;
        //left point points to arrow
        whileDo.left = this;

    }

    @Override
    public void updatePosition() {
        int x0 = next.getX() + next.getWidth() / 2;
        int y0 = next.getY() + next.getHeight() + border.getBorderSize();

        setLocation(x0 + BorderFlowChart.getThickness() * 2, y0);
    }

    protected Dimension getPreferedDimension() {
        int DY = 2 * BorderFlowChart.getThickness();
        if (parent != null && next != null) {
            int w = parent.getX() + parent.getWidth() / 2 - next.getX() - next.getWidth() / 2;
            int height = parent.getY() + parent.getHeight() - next.getY() - next.getHeight();

            return new Dimension(
                    w - BorderFlowChart.getThickness() * 2,
                    height + DY);

        } else {
            return super.getPreferedDimension();
        }
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        return true;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
