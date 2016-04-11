/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowchart.algorithm;

import flowchart.shape.Fshape;
import java.io.Serializable;

/**
 *
 * @author 3nvy
 */
public class StorableShape implements Cloneable, Serializable{

    private final Fshape parent;

    private final Fshape shape;
    private final String type;
    private final String arrow;
    private Boolean toRemove;

    /**
     *
     * @param arrow
     * @param parent
     * @param shape
     * @param type
     * @param toRemove
     */
    public StorableShape(Fshape parent, Fshape shape, String type, String arrow, Boolean toRemove) {
        this.parent = parent;
        this.shape = shape;
        this.type = type;
        this.arrow = arrow;
        this.toRemove = toRemove;
    }


    public Fshape getShape() {
        return shape;
    }

    public String getType() {
        return type;
    }

    public Fshape getParent() {
        return parent;
    }

    public String getArrow() {
        return arrow;
    }

    public Boolean toRemove() {
        return toRemove;
    }

    public void invertRemove(){
        this.toRemove = !toRemove;
    }
     
}
