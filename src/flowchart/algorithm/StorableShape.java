/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowchart.algorithm;

import flowchart.arrow.Arrow;
import flowchart.shape.Fshape;
import java.io.Serializable;

/**
 *
 * @author 3nvy
 */
public class StorableShape implements Cloneable, Serializable{

    private final Arrow arrow;
    private final Fshape shape;

    /**
     *
     * @param arrow
     * @param shape
     */
    public StorableShape(Arrow arrow, Fshape shape) {
        this.arrow = arrow;
        this.shape = shape;
    }

    public Arrow getArrow() {
        return arrow;
    }

    public Fshape getShape() {
        return shape;
    }
    
    
    
}
