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
package flowchart.arrow.TB;

import flowchart.arrow.TB.ShapeArrow_DW;
import flowchart.arrow.*;
import core.data.exception.FlowchartException;
import ui.FProperties;
import flowchart.shape.BorderFlowChart;
import flowchart.shape.Fshape;
import java.awt.Dimension;

/**
 *
 * @author ZULU
 */
public final class Arrow_Wile_Do extends Arrow {

    public Arrow_Wile_Do(Fshape _do_, Fshape _while_) {
        super(new ShapeArrow_DW(FProperties.arrowColor), _do_, _while_);
        setLink(_do_, _while_);
    }

    public void setLink(Fshape _do_, Fshape _while_) {
        _do_.next = this;
        _while_.parent = this;
        
        this.level = _do_.level;
        
        this.parent = _do_;
        this.next = _while_;

    }

    @Override
    public void updatePosition() {
        int x1 = parent.getX() + parent.getWidth() / 2 - border.getBorderSize()*2;
        int y1 = parent.getY() + parent.getHeight();
        setLocation(x1, y1);
    }

    protected Dimension getPreferedDimension() {
        if (parent != null && next != null) {
            int y1 = parent.getY() + parent.getHeight();
            int h = next.getY() - y1;
            return new Dimension(border.getBorderSize()*4, h);
        } else {
            return super.getPreferedDimension();
        }
    }

    @Override
    public boolean parseShape() throws FlowchartException {
        return true;
    }
    @Override
    public void editMenu(int x, int y) {
        next.editMenu(x, y);
    }

    @Override
    public void setSelected(boolean selected) {
        parent.setSelected(selected);
        next.setSelected(selected);
        super.setSelected(selected);
    }
    

}
