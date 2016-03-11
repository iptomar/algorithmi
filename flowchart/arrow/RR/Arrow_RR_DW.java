///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2015                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****     This software was build with the purpose of investigate and    ****/
///****     learning.                                                      ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package flowchart.arrow.RR;

import flowchart.arrow.*;
import core.data.exception.FlowchartException;
import ui.FProperties;
import flowchart.shape.Fshape;
import java.awt.Dimension;

/**
 *
 * @author ZULU
 */
public final class Arrow_RR_DW extends Arrow {

    public Arrow_RR_DW(Fshape begin, Fshape end) {
        super(new ShapeArrow_RR_DW(FProperties.arrowColor), begin, end);
        setLink(begin, end);
    }

    public void setLink(Fshape begin, Fshape end) {
        begin.right = this;
//        begin.next = end; // next is an arrow

        end.right = this;
//        end.parent = begin; // parent of end is an arrow

        this.level = begin.level + 1;
        this.parent = begin;
        this.next = end;
    }

    @Override
    public void updatePosition() {
        int x0 = parent.getX() + parent.getWidth();
        int y0 = parent.getY() + parent.getHeight() / 2;

        setLocation(x0, y0);
    }

    /**
     * returns the prefered size of the component
     *
     * @return
     */
    @Override
    protected Dimension getPreferedDimension() {
        if (parent != null && next != null) {
            int w = next.getWidth() / 2 - parent.getWidth() / 2;
            int h = next.getY() + next.getHeight() / 2 - (parent.getY() + parent.getHeight() / 2);
            return new Dimension(
                    w + 3 * border.getBorderSize(), 
                    h + border.getBorderSize());

        } else {
            return super.getPreferedDimension();
        }
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        return true;
    }

}
