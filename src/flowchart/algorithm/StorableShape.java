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
    private final String arrow;
    private Boolean toRemove;
    private String currentText;
    

    /**
     *
     * @param arrow
     * @param parent
     * @param shape
     * @param toRemove
     */
    public StorableShape(Fshape parent, Fshape shape, String arrow, Boolean toRemove) {
        this.parent = parent;
        this.shape = shape;
        this.arrow = arrow;
        this.toRemove = toRemove;
        this.currentText = shape.getInstruction();
    }


    public Fshape getShape() {
        return shape;
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
