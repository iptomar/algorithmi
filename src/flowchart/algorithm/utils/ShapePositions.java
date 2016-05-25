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
package flowchart.algorithm.utils;

import ui.FProperties;
import ui.flowchart.dialogs.Fdialog;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.arrow.Arrow;
import flowchart.arrow.BT.ArrowNext;
import flowchart.arrow.TB.Arrow_Wile_Do;
import flowchart.decide.Do_Connector;
import flowchart.decide.forNext.For_Next;
import flowchart.decide.IfElse.IfThenElse;
import flowchart.decide.While_Do;
import flowchart.function.FunctionParameter;
import flowchart.shape.Fshape;
import flowchart.terminator.Begin;
import flowchart.terminator.End;
import ui.FLog;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class ShapePositions implements Serializable {

    AlgorithmGraph flux;

    public ShapePositions(AlgorithmGraph flux) {
        this.flux = flux;
    }

    public void moveToXY(int px, int py) {
        Rectangle bounds = getDrawBounds(flux.graph);
        int dimx = bounds.width - bounds.x;
        int dimy = bounds.height - bounds.y;
        flux.graph.setBounds(0, 0, px + (int) (dimx * 1.25), py + (int) (dimy * 1.25));
        for (Component c : flux.graph.getComponents()) {
            Fshape node = (Fshape) c;
            node.xLocation = node.getLocation().x - bounds.x + px;
            node.yLocation = node.getLocation().y - bounds.y + py;
            node.setBounds((int) node.xLocation, (int) node.yLocation, node.getWidth(), node.getHeight());
        }
    }

    public void calculateNodePositions() {
        if (flux.graph.getComponentCount() == 0) {
            return;
        }
        calculatePositions();
    }

    /**
     * calculate the bounds of the flowchart
     *
     * @return
     */
    public static Rectangle getDrawBounds(Container container) {
        if (container.getComponentCount() == 0) {
            return new Rectangle(0, 0, 0, 0);
        }
        int x0 = Integer.MAX_VALUE;
        int y0 = Integer.MAX_VALUE;
        int x1 = 0;
        int y1 = 0;
        Rectangle pb;
        Fshape node;
        for (Component c : container.getComponents()) {
            node = (Fshape) c;
            pb = node.getBounds();
            if (pb.x < x0) {
                x0 = pb.x;
            }
            if (pb.y < y0) {
                y0 = pb.y;
            }
            if (pb.x + pb.width > x1) {
                x1 = pb.x + pb.width;
            }
            if (pb.y + pb.height > y1) {
                y1 = pb.y + pb.height;
            }

        }
        return new Rectangle(x0, y0, x1 - x0, y1 - y0);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //:::::::::::   calculate ID o levels  :::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private TreeMap<Double, Integer> IDSize; // size of each ID
    private TreeMap<Double, Integer> IDCenter; // center of each ID

    /**
     * Sort shapes by size and init locations and IDs sorting shapes sizes
     * enables the possibility to select all the shapes otherwise gig shapes
     * hides the small ones
     */
    private void initializeShapes() {

        ArrayList<Fshape> shapes = new ArrayList<>();
        for (Component c : flux.graph.getComponents()) {
            shapes.add((Fshape) c);
        }
        Collections.sort(shapes);
        for (int i = 0; i < shapes.size(); i++) {
            Fshape node = shapes.get(i);
            node.xLocation = 0;
            node.yLocation = 0;
            node.maxY = 0;
            node.POSITION_LEVEL = -1;
            //change the order in the panel
            flux.graph.setComponentZOrder(node, i);
        }
    }

    private void removeNotLinked() {
        ArrayList<Fshape> shapes = new ArrayList<>();
        for (Component c : flux.graph.getComponents()) {
            shapes.add((Fshape) c);
        }
        for (Fshape shape : shapes) {
            if (shape.POSITION_LEVEL == -1) {
                flux.graph.remove(shape);
            }
        }
    }

    public void calculatePositions() {
        IDSize = new TreeMap<>();
        IDCenter = new TreeMap<>();
        initializeShapes();

        Fshape begin = flux.getBegin();
        Fshape end = flux.getEnd();

        try {
            begin.POSITION_LEVEL = 1;
            calculateID(begin.next, end, begin.POSITION_LEVEL); // IGNORE DEFINITON
        } catch (Exception e) {
            FLog.runError("void calculatePositions()" + e.getMessage());
            return;
        }
//        printDebug();
        calculateIDSize(begin, end);
        //printDebug();
        calculateIDCenter();
        //printDebug();
        calculateXY(begin, end, 0);
        //normalize header of functions
        normalizeXYparameters(begin);
        //remove shapes not linked
        removeNotLinked();

    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //:::::::::::   calculate ID of levels :::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //                    1-begin
    //          0.5                  1.5
    //          0.5                  1.5
    //          0.5                  1.5
    //    0.25       0.75      1.25       1.75
    //    0.25       0.75      1.25       1.75
    //    0.25       0.75      1.25       1.75
    //    0.25       0.75      1.25       1.75
    //          0.5                  1.5
    //          0.5                  1.5
    //          0.5                  1.5
    //                    1
    //                    1
    //                    1-end
    //
    //  Algorithm
    //       1 - start with Value 1 in the begin
    //       2 - set node.ID = value        
    //       3 - if right is defined
    //           3.1 - process Right with value + 1/ (2^level);    
    //       4 - if left is defined
    //           4.1 - process left with value - 1/ (2^level);    
    //       5 - if next is defined
    //           5.1 - process next with value
    //       6 - if node != end goto 2
    //--------------------------------------------------------------------------   
    public void calculateID(Fshape node, Fshape end, double positionLevel) {
        node.POSITION_LEVEL = positionLevel;
        //stop recursivity
        if (node == end) {
            return;
        }
        if (node instanceof IfThenElse) {
            calculateID(node.right, node.next, positionLevel + 1.0 / Math.pow(2.0, node.level));
            calculateID(node.left, node.next, positionLevel - 1.0 / Math.pow(2.0, node.level));
            node.POSITION_LEVEL = positionLevel; // update ID
        }
        if (node instanceof Do_Connector) {
            calculateID(node.right, node.next.next, positionLevel + 1.0 / Math.pow(2.0, node.level));
            node.POSITION_LEVEL = positionLevel; // update ID           
        }
        if (node instanceof While_Do || node instanceof For_Next) {
            calculateID(node.right, node, positionLevel + 1.0 / Math.pow(2.0, node.level));
            node.POSITION_LEVEL = positionLevel; // update ID
        }
        //process next
        if (!(node instanceof End)) {
//        if (node.next != null) {
            calculateID(node.next, end, positionLevel);
        }
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //:::::::::::   calculate ID Size  :::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //maximum width to shape with the same ID
    public void calculateIDSize(Fshape node, Fshape end) {
//        printDebug();
//        System.out.println("PROCESS NODE " + node.toString());
        if (node == null) {
            return;
        }
        //ignore arrows
        if (node instanceof Arrow) {
            calculateIDSize(node.next, end);
            return;
        }
        //calculate node size with current Zoom
        node.updateSize();
        //create ID
        if (IDSize.get(node.POSITION_LEVEL) == null) {
            if (node instanceof Begin) { // ignore size of Function definition
                // funtion definition my be very big and 
                IDSize.put(node.POSITION_LEVEL, 150);
            } else {
                IDSize.put(node.POSITION_LEVEL, node.getWidth());
            }
        } else //update size of the ID
         if (IDSize.get(node.POSITION_LEVEL) < node.getWidth()) {
                IDSize.put(node.POSITION_LEVEL, node.getWidth());
            }
        //stop recursivity
        if (node == end) {
            return;
        } else if (node instanceof IfThenElse) {
            calculateIDSize(node.right, node.next); // process left
            calculateIDSize(node.left, node.next); // process right
            calculateIDSize(node.next, end); // process next
        } else if (node instanceof While_Do || node instanceof For_Next) {
            calculateIDSize(node.right, node); // process right 
            calculateIDSize(node.next, end);   // process next
        } else if (node instanceof Do_Connector) {
            calculateIDSize(node.right, node.next.next); // process right
            calculateIDSize(node.next, end); // process next
        } //process next
        else if (!(node instanceof End)) {
            calculateIDSize(node.next, end); // process next
        }

    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //:::::::::::   calculate ID CENTER  :::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //maximum width to shape with the same ID
    private void calculateIDCenter() {
        int center = 0;
        for (Map.Entry<Double, Integer> entry : IDSize.entrySet()) {
            Double key = entry.getKey();
            Integer value = entry.getValue();
            //add half
            center += value / 2;
            //add center
            IDCenter.put(key, center);
            //add half
            center += value / 2;
        }
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //:::::::::::   calculate XY  :::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //maximum width to shape with the same ID
    public void calculateXY(Fshape node, Fshape end, int y) {
        try {

            //ignore arrows  
            if (node instanceof Arrow) {
                if (node.next instanceof FunctionParameter) { // parameter
                    calculateXY(node.next, end, y);
                } else if ((node instanceof ArrowNext) || (node instanceof Arrow_Wile_Do)) {
                    //double the space in the next arrow
                    calculateXY(node.next, end, y + 5 + (int) (node.getZoom() * FProperties.ARROW_LENGHT_RATIO));
                } else {
                    calculateXY(node.next, end, y);
                }
                return;
            }

            //get maximum y
            if (node.yLocation > y) {
                y = node.yLocation;
            }

            //update location
            node.xLocation = IDCenter.get(node.POSITION_LEVEL) - node.getWidth() / 2;
            node.yLocation = y;
            int oldY = y;
            if (node.next != null && node.next.next instanceof FunctionParameter) {
                //y += node.getHeight();
                y += node.getHeight() - FProperties.BORDER_SIZE;
            } else //increase y position     
            {
                y += node.getHeight() + (int) (node.getZoom() * FProperties.ARROW_LENGHT_RATIO);
            }

            //stop recursivity
            if (node == end) {
                return;
            }

            if (node instanceof IfThenElse) {
                calculateXY(node.right, node.next, y);
                calculateXY(node.left, node.next, y);
            }
            if (node instanceof While_Do || node instanceof For_Next) {
                calculateXY(node.right, node, y);
                node.yLocation = oldY;
                node.maxY = Math.max(node.left.parent.yLocation + node.left.parent.getHeight(), node.left.parent.maxY);
                y = node.maxY + Fshape.getDistanceBetweenShapes();

            }
            if (node instanceof Do_Connector) {
                if (node.next instanceof Arrow) {
                    calculateXY(node.right, node.next.next, y);
                } else {
                    calculateXY(node.right, node.next, y);
                }

            }
            //process next
            if (!(node instanceof End)) {
                calculateXY(node.next, end, y);
            }
        } catch (Exception e) {
            FLog.runError("void calculateXY(Fshape node, Fshape end, int y) " + e.getMessage());
            Fdialog.showMessage("Error in public void calculateXY(Fshape node, Fshape end, int y)");
            //System.exit(1);
        }
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //:::::::::::   calculate XY  :::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //maximum width to shape with the same ID
    public void normalizeXYparameters(Fshape begin) {
        //node.xLocation = IDCenter.get(node.POSITION_LEVEL) - node.getWidth() / 2;
        int maxX = begin.getWidth();
        //-----------------------------
        //PARAMETERS OF FUNCTION
        Fshape node = begin.next;
        while ((node.next instanceof FunctionParameter)) {
            maxX = Math.max(maxX, node.next.getWidth());
            node = node.next.next;
        }
        //-----------------------------------------------------
        node = begin;
        do {
            node.xLocation = IDCenter.get(node.POSITION_LEVEL) - maxX / 2;
            node.setSize(maxX, node.getHeight());
            node = node.next;
        } while (node.next instanceof FunctionParameter);

    }

    public void translateTo(int dx, int dy) {
        for (Component c : flux.graph.getComponents()) {
            Fshape node = (Fshape) c;
            if (!(node instanceof Arrow)) {
                node.setLocation(node.xLocation + dx, node.yLocation + dy);
            }
        }
        for (Component c : flux.graph.getComponents()) {
            Fshape node = (Fshape) c;
            if (node instanceof Arrow) {
                ((Arrow) node).updatePosition();
            }
        }
    }

    public void printDebug() {
        FLog.printLn("\n\nID CENTER");
        for (Map.Entry<Double, Integer> entry : IDCenter.entrySet()) {
            FLog.printLn("ID " + entry.getKey() + "\t SIZE  " + entry.getValue());
        }
        FLog.printLn("\n\nPOSITIONS");
        for (Map.Entry<Double, Integer> entry : IDSize.entrySet()) {
            FLog.printLn("ID " + entry.getKey() + "\t SIZE  " + entry.getValue() + " \t CENTER " + IDCenter.get(entry.getKey()));
        }
//        System.out.println("\nShapes");
//        for (Component c : flux.graph.getComponents()) {
//            Fshape node = (Fshape) c;
////            if (!(node instanceof Arrow)) {
//            System.out.println("ID " + node.POSITION_LEVEL + " \tLevel " + node.level + "\t SIZE  " + node.getWidth() + node.toString());
////            }
//        }

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
