/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowchart.algorithm;

import flowchart.shape.Fshape;

/**
 *
 * @author 3nvy
 */
public class StorableCicleShape extends StorableShape{

    private final Fshape connector;

    /**
     *
     * @param connector
     * @param parent
     * @param shape
     * @param arrow
     * @param toRemove
     */
    public StorableCicleShape(Fshape connector, Fshape parent, Fshape shape, String arrow, Boolean toRemove) {
        super(parent, shape, arrow, toRemove);
        this.connector = connector;
    }

    public Fshape getConnector() {
        return connector;
    }
    
}
