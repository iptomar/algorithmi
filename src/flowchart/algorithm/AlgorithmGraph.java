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
package flowchart.algorithm;

import core.Memory;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import ui.flowchart.dialogs.CompileExection;
import flowchart.algorithm.utils.ShapePositions;
import flowchart.arrow.Arrow;
import flowchart.arrow.BL.ArrowBL;
import flowchart.arrow.BR.ArrowBR;
import flowchart.arrow.BT.ArrowNext;
import flowchart.arrow.BT.ArrowNextGlobalMemory;
import flowchart.arrow.LB.ArrowLB_while;
import flowchart.arrow.LL.ArrowLL_IF;
import flowchart.arrow.LT.ArrowLT;
import flowchart.arrow.RB.ArrowRB_While;
import flowchart.arrow.RR.Arrow_RR_IF;
import flowchart.arrow.RT.ArrowRT;
import flowchart.decide.Do_While;
import flowchart.decide.Do_Connector;
import flowchart.arrow.RR.Arrow_RR_DW;
import flowchart.arrow.TB.Arrow_Wile_Do;
import flowchart.decide.IfElse.IF_Connector;
import flowchart.decide.forNext.For_Next;
import flowchart.decide.IfElse.IfThenElse;
import flowchart.decide.While_Do;
import flowchart.define.Define;
import flowchart.function.Function;
import flowchart.function.FunctionParameter;
import flowchart.read.Read;
import flowchart.shape.Fshape;
import flowchart.terminator.Begin;
import flowchart.terminator.End;
import i18n.FkeyWord;
import ui.FLog;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ui.FProperties;
import ui.flowchart.dialogs.Fdialog;

/**
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class AlgorithmGraph implements Cloneable, Serializable {
     
    public ShapePositions positionCalc;// to reorganize patterns
    public JPanel graph;//to display and support to graph objects
    protected String name; // name of the algorithm

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // NOTE : TRANSIENT because recursive call
    // NOTE : clone of this object does not contain this atribute
    // NOTE : to serialize object - Not work without transient
    transient public Program myProgram; // program where the algorithm belongs  ( definition of global memory , other functions, and main )
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public Memory myLocalMemory; //local memory of algorithm  (used to display in runtime mode)

    public AlgorithmGraph(JPanel container, Program prog) {
//        name = FkeyWord.get("KEYWORD.defaultFunctionName");
        setProgram(prog);    // set main program to the algorithm     
        positionCalc = new ShapePositions(this);
        this.graph = container;
        this.graph.setLayout(null); // to suport x,y positions
        graph.removeAll();
        initProgram();
        alignPatterns();
        graph.revalidate();
        graph.repaint();
    }

    /**
     * sets the main program to the algorithm
     *
     * @param prog
     */
    public void setProgram(Program prog) {
        this.myProgram = prog; // update my program
        if (this.getClass().equals(AlgorithmGraph.class)) { //------------------ main program
            prog.setMain(this); // update main            
            name = FkeyWord.get("KEYWORD.mainFunctionName"); //------------------ main name
        } else if (this.getClass().equals(GlobalMemoryGraph.class)) {//--------- global memory
            prog.setGlobalMem((GlobalMemoryGraph) this);
            name = FkeyWord.get("KEYWORD.globalMemoryName"); //------------------ global memory name
        } else {
            if (name == null) { //create name
                name = prog.getDefaultNewName();
            }
            prog.addFunction((FunctionGraph) this);
        }
        myLocalMemory = new Memory(name); // create memory
    }

    /**
     * gets the program where the algorithm belongs
     *
     * @return Program
     */
    public Program getMyProgram() {
        return myProgram;
    }

    /**
     * change the name of the algorithm
     *
     * @param newName
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * gets the algorithm name
     *
     * @return
     */
    public String getFunctionName() {
        return name;
    }

    /**
     * adds one shape to the algorithm replacing one oarrow
     *
     * @param shape shape
     * @param instruction instruction of the shape
     * @param comments commnets to the shape
     * @param arrow arrow to replace
     * @throws FlowchartException
     */
    protected void addShape(Fshape shape, String instruction, String comments, Fshape arrow) throws FlowchartException {
        addSimpleShape((Arrow) arrow, shape); // replace arrow
        shape.buildInstruction(instruction, comments); // updateSystemProperties comments

    }

    /**
     * build an empty Begin->End fluxogram
     */
    public void initProgram() {
        clear(); // begin --> end
        Fshape begin = new Begin(this); //begin
        begin.level = 1;
        Fshape end = new End(this); //end
        end.level = 1;
        Arrow arrow = new ArrowNext(begin, end); // begin -> end
        add(begin);
        add(arrow);
        add(end);
    }

    /**
     * init the function
     */
    public void clear() {
        graph.removeAll(); // removel all the shapes       
    }

    /**
     * add a shape to the graph shapes are introduced in the fron arrows are
     * inserted in the back
     *
     * @param p
     */
    public void add(Fshape p) {
        if (p instanceof Arrow) {
            graph.add(p, -1); //end of graph
        } else {
            graph.add(p, 0); //begin of graph
        }
    }

    /**
     * remove an shape from the graph
     *
     * @param p
     */
    private void remove(Fshape p) {
        graph.remove(p);
    }

    /**
     * Align patterns in the draw area and updateSystemProperties draw area size
     */
    public void alignPatterns() {
        if (getBegin() != null) {
            alignPatterns(getZoom() * 2, 10);

        }
    }

    /**
     * Align patterns in the draw area and updateSystemProperties draw area size the
 graph is translated to StartX , StartY
     *
     * @param startX position X of graph
     * @param startY position Y of graph
     */
    public void alignPatterns(int startX, int startY) {
        //calculate positions          
        positionCalc.calculateNodePositions();
        Rectangle bounds = positionCalc.getDrawBounds(graph);
        // 20% of free empty space in the shape editor
        graph.setPreferredSize(new Dimension(bounds.x + (int) (bounds.width * 1.2), bounds.y + (int) (bounds.height * 1.2)));
        positionCalc.translateTo(startX, startY);
        graph.revalidate();
        graph.repaint();
    }

    /**
     * gets the begin of the fluxogram
     *
     * @return begin or null
     */
    public Fshape getBegin() {
        for (Component c : graph.getComponents()) {
            if (c instanceof Begin) {
                return (Fshape) c;
            }
        }
        return null;//something wrong happens!!
    }

    /**
     * gets the begin of the fluxogram
     *
     * @return begin or null
     */
    public Fshape getEnd() {
        for (Component c : graph.getComponents()) {
            if (c instanceof End) {
                return (Fshape) c;
            }
        }
        return null;//something wrong happens!!
    }

    /**
     * change font of all patterns
     *
     * @param f
     */
    public void setFont(Font f) {
        Fshape.setMytFont(f);
        for (Component c : graph.getComponents()) {
            Fshape node = (Fshape) c;
            node.updateFont();
        }
    }

    /**
     * change the size of the components
     *
     * @param size
     */
    public void refresh() {
        setZoom(getZoom());
    }

    /**
     * change the size of the components
     *
     * @param size
     */
    public void zoomIn() {
        setZoom((int) (getZoom() * 1.25) + 1);
    }

    /**
     * change the size of the components
     *
     * @param size
     */
    public void zoomOut() {
        setZoom((int) (getZoom() * 0.75) - 1);
    }

    /**
     * change the size of the components to equal zoom
     *
     * @param zoom size of zoom
     */
    public void setZoom(int zoom) {
        for (Component c : graph.getComponents()) {
            ((Fshape) c).setZoom(zoom);
        }
        Fshape.setMytFont(getBegin().getTxtFont());
        alignPatterns();
    }

    /**
     * change the size of the components to equal zoom
     *
     * @param zoom size of zoom
     */
    public int getZoom() {
        return getBegin().getZoom();
    }

    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------
    //------------------      REMOVE NODES                    --------------------------------------------
    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------
    /**
     * removes a single node in the graph
     *
     * @param node
     */
    private void removeSimpleNode(Fshape node) {
        //REMOVE next-next
        Arrow arrowTop = (Arrow) node.parent;
        Arrow arrowBottom = (Arrow) node.next;

        //delete simple arrow in TOP
        if (arrowTop instanceof ArrowNext) {
            arrowBottom.setLink(arrowTop.parent, arrowBottom.next);
            remove(node);
            remove(arrowTop);
        } //delete simple arrow in bottom
        else if (arrowBottom instanceof ArrowNext) {
            arrowTop.setLink(arrowTop.parent, arrowBottom.next);
            remove(node);
            remove(arrowBottom);
        } //build empty if-ele
        else if (arrowTop.parent instanceof IfThenElse && arrowBottom.next instanceof IF_Connector) {
            //is the left
            if (arrowTop.parent.left == arrowTop) {
                add(new ArrowLL_IF(arrowTop.parent, arrowBottom.next));
            } //is the right
            else {
                add(new Arrow_RR_IF(arrowTop.parent, arrowBottom.next));
            }
            remove(node);
            remove(arrowTop);
            remove(arrowBottom);
        } else if (arrowTop.parent instanceof Do_Connector && arrowBottom.next instanceof Do_While) {
            add(new Arrow_RR_DW(arrowTop.parent, arrowBottom.next));
            remove(node);
            remove(arrowTop);
            remove(arrowBottom);
        } else if (arrowTop.parent == arrowBottom.next) { //while and for        
            remove(node);
            remove(arrowTop);
            remove(arrowBottom);
            add(new ArrowRB_While(arrowTop.parent));
        }

    }

//    //----------------------------------------------------------------------------------------------------
//    //----------------------------------------------------------------------------------------------------
//    private static int DELETED_ID = -99999; //---------------------------------- mark of deleted node
//
//    /**
//     * remove all the nodes between the nodes node->end
//     *
//     * @param start start node
//     * @param stop end node
//     */
//    private void removeAllNodesBetween(Fshape start, Fshape stop) {
//        //stop recursivity
////        if (start == null || start == stop || start.POSITION_LEVEL == DELETED_ID) {
////            return;
////        }
////        //remove
////        graph.remove(start);
////        //mark has removed
////        start.POSITION_LEVEL = DELETED_ID;
////        //remove childrens
////        removeAllNodesBetween(start.next, stop);
////        removeAllNodesBetween(start.left, stop);
////        removeAllNodesBetween(start.right, stop);
//
//    }
    /**
     * calculate the direction TYPE between begin -> end
     *
     * @param begin start shape
     * @param end end shape
     * @return PARENT NEXT RIGHT LEFT or null
     */
    private Fshape.Direction getPointerDirection(Fshape begin, Fshape end) {
        if (begin.parent.next == end) {
            return Fshape.Direction.NEXT;
        }
        if (begin.parent.right == end) {
            return Fshape.Direction.RIGHT;
        }
        if (begin.parent.left == end) {
            return Fshape.Direction.LEFT;
        }
        if (begin.parent.parent == end) {
            return Fshape.Direction.PARENT;
        }
        return null;
    }

    private void linkShapes(Fshape begin, Fshape.Direction ptB, Fshape end, Fshape.Direction ptE) {
        if (begin instanceof IfThenElse) { // IF
            if (begin.parent == end) { // Empty  branch

            }
        }
    }

    private void linkShapes(Fshape arrowFromBegin, Fshape arrowToEnd) {
        //link direction of parent shape
        Fshape.Direction beginDir = getPointerDirection(arrowFromBegin.parent, arrowFromBegin);
        // link direction to next shape
        Fshape.Direction endDir = getPointerDirection(arrowToEnd, arrowToEnd.next);;
        //Begin shape
        Fshape begin = arrowFromBegin.parent;
        //end shape
        Fshape end = arrowToEnd.next;

    }

    //----------------------------------------------------------------------------------------------------
    //------------------      REMOVE IF                      --------------------------------------------
    //----------------------------------------------------------------------------------------------------
    private void removeIf(Fshape ifElse) {
//        removeAllNodesBetween(ifElse.left, ifElse.next);
//        removeAllNodesBetween(ifElse.right, ifElse.next);
        Fshape conector = ifElse.next;
        //redirect parent
        ((Arrow) conector.next).setLink(ifElse, conector.next.next);
        //deleter other
        removeSimpleNode(ifElse);
        remove(conector);

        Fshape previous = ifElse.parent.parent; // shape -> arrow -> ifElse
        Fshape next = ifElse.next.next.next; // conector-> arrow -> shape

    }

    //----------------------------------------------------------------------------------------------------
    //------------------      REMOVE DO WHILE                  --------------------------------------------
    //----------------------------------------------------------------------------------------------------
    private void removeDoWhile(Fshape doWhile) {
        Fshape _do = doWhile.parent.parent;
        //remove all nodes
//        removeAllNodesBetween(_do.right, doWhile);
        //redirect do -> doWhile.next
        ((Arrow) doWhile.next).setLink(_do, doWhile.next.next);
        //delete do
        removeSimpleNode(_do);
        //delete arrow
        remove(doWhile.parent);
        //delete while
        remove(doWhile);
    }
    //----------------------------------------------------------------------------------------------------
    //------------------      REMOVE While                      --------------------------------------------
    //----------------------------------------------------------------------------------------------------

    private void removeWhile(Fshape whileDo) {
//        removeAllNodesBetween(whileDo.right, whileDo);
        removeSimpleNode(whileDo);
    }

    /**
     * remove one pattern from the graph that includes the removal of the arrows
     * of the node and reconnect the remains nodes
     *
     * @param node node to remove
     */
    public void removePattern(Fshape node) {
        if (node instanceof IfThenElse) {
            removeIf(node);
        }
        if (node instanceof While_Do || node instanceof For_Next) {
            removeWhile(node);
        }
        if (node instanceof Do_While) {
            removeDoWhile(node);
        } else {
            removeSimpleNode(node);
        }
        alignPatterns();
    }

    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------
    //------------------      INSERT NODES                    --------------------------------------------
    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------
    /**
     * creates a top arrow to the node insertion
     *
     * @param arrow arrow to replace
     * @param shape shape to insert
     * @return new top arrow
     */
    private Arrow createTopArrow(Arrow arrow, Fshape shape) {
        if (arrow.parent.right == arrow) { // arrow from right
            return new ArrowRT(arrow.parent, shape);
        } else if (arrow.parent.left == arrow) { // arrow from left
            return new ArrowLT(arrow.parent, shape);
        }
        if (arrow instanceof ArrowNextGlobalMemory) { // definine arrow
            return new ArrowNextGlobalMemory(arrow.parent, shape);
        }
        return new ArrowNext(arrow.parent, shape); // normal arrow
    }

    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------
    private Arrow createBottomArrow(Arrow arrow, Fshape shape) {

        if (arrow instanceof ArrowRB_While) { // empty while Right---> bottom
            //While Left ---> Bottom
            return new ArrowLB_while(shape, arrow.next);
        }

        //empty IF or  empty DO..While or  Bootom---> right
        if ((arrow instanceof Arrow_RR_IF) || (arrow instanceof Arrow_RR_DW) || (arrow instanceof ArrowBR)) {
            return new ArrowBR(shape, arrow.next);
        }

        if ((arrow instanceof ArrowLL_IF) || (arrow instanceof ArrowBL)) {
            return new ArrowBL(shape, arrow.next);
        }
        if ((arrow instanceof ArrowLB_while)) {
            return new ArrowLB_while(shape, arrow.next);
        }
        if (arrow instanceof ArrowNextGlobalMemory) { // defini arrow in global memory
            return new ArrowNextGlobalMemory(shape, arrow.next);
        }
        return new ArrowNext(shape, arrow.next);
    }
    //----------------------------------------------------------------------------------------------------
    //------------------      SIMPLE NODES                    --------------------------------------------
    //----------------------------------------------------------------------------------------------------

    /**
     * add a shape to the graph replacing one arrow
     *
     * @param arrow arrow to replace
     * @param shape new pattern
     */
    public static void addNextLink(Fshape begin, Arrow arrow, Fshape end) {
        begin.next = arrow;
        arrow.parent = begin;
        arrow.next = end;
        end.parent = arrow;
    }

    /**
     * add a shape to the graph replacing one arrow
     *
     * @param arrow arrow to replace
     * @param shape new pattern
     */
    public void addSimpleShape(Arrow arrow, Fshape shape) {
        shape.level = arrow.level;
        //remove this arrow
        remove(arrow);
        //previous arrow
        add(createTopArrow(arrow, shape));
        //new pattern               
        add(shape);
        //next arrow
        add(createBottomArrow(arrow, shape));
        alignPatterns();
    }

    /**
     * add a shape to the graph replacing one arrow
     *
     * @param arrow arrow to replace
     * @param shape new pattern
     */
    public void addParameterShape(Fshape begin, Fshape shape) {
        shape.level = begin.level;
        shape.parent = begin;
        shape.next = begin.next;
        begin.next.parent = shape;
        begin.next = shape;
        alignPatterns();
    }

    //----------------------------------------------------------------------------------------------------
    //------------------      IF ELSE NODES                    --------------------------------------------
    //----------------------------------------------------------------------------------------------------
    /**
     * add an if-else strcture to the graph
     *
     * @param arrow arrow to be replaced
     * @param ifElse new shape
     */
    public void addShapeIfElse(Arrow arrow, IfThenElse ifElse) {
        IF_Connector connector = new IF_Connector(this);
        connector.level = arrow.level;
        ifElse.level = arrow.level;
        //remove this arrow
        remove(arrow);
        ///previous arrow
        add(createTopArrow(arrow, ifElse));
        //new pattern               
        add(ifElse);
        add(connector);
        //next arrow
        add(createBottomArrow(arrow, connector));

        //----------------------------------------
        //IF  --- Connector  
        add(new Arrow_RR_IF(ifElse, connector));
        add(new ArrowLL_IF(ifElse, connector));

        alignPatterns();
    }

    //----------------------------------------------------------------------------------------------------
    //------------------      DO WHILE NODES                    --------------------------------------------
    //----------------------------------------------------------------------------------------------------
    /**
     * add an if-else strcture to the graph
     *
     * @param arrow arrow to be replaced
     * @param doWhile new shape
     */
    public void addShapeDoWhile(Arrow arrow, Fshape doWhile) {
        //shape do 
        Do_Connector _do = new Do_Connector(this);
        _do.level = arrow.level;
        doWhile.level = arrow.level;

        //remove this arrow
        remove(arrow);
        ///previous arrow
        add(createTopArrow(arrow, _do));
        //new pattern               
        add(doWhile);
        add(_do);
        //next arrow
        add(createBottomArrow(arrow, doWhile));

        //----------------------------------------
        Arrow arrowDowhile = new Arrow_RR_DW(_do, doWhile);
        arrowDowhile.level = arrow.level + 1;
        add(arrowDowhile);
        Arrow arrowWhileDo = new Arrow_Wile_Do(_do, doWhile);
        arrowWhileDo.level = arrow.level;
        add(arrowWhileDo);
        alignPatterns();
    }

    //----------------------------------------------------------------------------------------------------
    //------------------      WHILE and FOR NODES                    -------------------------------------
    //----------------------------------------------------------------------------------------------------
    /**
     * add an if-else strcture to the graph
     *
     * @param arrow arrow to be replaced
     * @param whileORfor new while or for shape
     */
    public void addShapeWhileDo(Arrow arrow, Fshape whileORfor) {

        whileORfor.level = arrow.level;
        //remove this arrow
        remove(arrow);
        //previous arrow
        add(createTopArrow(arrow, whileORfor));
        //new pattern               
        add(whileORfor);
        //next arrow
        add(createBottomArrow(arrow, whileORfor));
        //create loop arrow
        add(new ArrowRB_While(whileORfor));

        alignPatterns();
    }

    /**
     * @return the memory
     */
    public Memory getMemory(Fshape pattern) {

        LinkedList<Define> defs = new LinkedList<>();
        Memory patternMemory; // memory of one pattern
        //if global memory get empty 
        if (myProgram.getGlobalMem() == null || myProgram.getGlobalMem() == this) {
            patternMemory = new Memory(this.getFunctionName());
        } //memory of others functions - add global memory
        else {
            patternMemory = myProgram.getGlobalMem().getMemory(myProgram.getGlobalMem().getEnd());
        }

        //get define instructions
        while (pattern != null) {
            //------------------------------------------------------------------ For
            if (pattern.parent instanceof For_Next) {
                For_Next _for = (For_Next) pattern.parent;
                if (_for.defineVariableToMemory
                        && // defined in the for
                        _for.right == pattern) { // is inside the cicle
                    Define def = new Define(_for.algorithm);
                    def.buildDefine(_for.var);
                    defs.addFirst(def);
                }
            }
            //------------------------------------------------------------------ define
            if (pattern instanceof Define) {
                defs.addFirst((Define) pattern);
            }
            //------------------------------------------------------------------ READ
            if (pattern instanceof Read) {
                if (pattern.getInstruction().contains(" ")) {
                    Define def = new Define(pattern.algorithm);
                    def.buildDefine(((Read) pattern).var);
                    defs.addFirst(def);
                }
            }
            //------------------------------------------------------------------ function parameters
            if (pattern instanceof Function) {
                Function f = (Function) pattern;
                for (Define def : f.getParameters()) {
                    defs.addFirst(def);

                }
            }
            //stop memory builder
            if (pattern instanceof Begin) {
                break;
            }
            pattern = pattern.parent;
        }
        //build memory
        for (Define def : defs) {
            try {
                if (def instanceof FunctionParameter) {
                    patternMemory.add(((FunctionParameter) def).varSymbol);
                } else {
                    Fsymbol s = patternMemory.define(def.getFullInstruction(), myProgram);
                    s.setComments(pattern.comments);
                }
            } catch (Exception e) {
                FLog.runError("Memory getMemory(Fshape pattern)" + e.getMessage());
            }
        }

        return patternMemory;
    }

    public AlgorithmGraph getClone() {

        try {
//            return FunctionFile.createFunctionToTokens(getPseudoTokens(), myProgram);
            ByteArrayOutputStream bos = null;
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;
            AlgorithmGraph newFlux = null;
            try {
                //write objet to the stream
                bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(this);
                //read object from the stream            
                ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
                newFlux = (AlgorithmGraph) ois.readObject();
            } catch (Exception ex) {
                FLog.runError("AlgorithmGraph getClone()" + ex.getMessage());
            } finally {
                try {
                    bos.close();
                    oos.close();
                    ois.close();
                } catch (IOException ex) {
                    FLog.runError("AlgorithmGraph getClone()" + ex.getMessage());
                }
            }
            return newFlux;
        } catch (Exception ex) {
            Fdialog.compileException(ex);
        }
        return null;
    }

    /**
     * Save graph in the file
     *
     * @param path name of the file
     * @throws Exception
     */
    public void save(String path) throws Exception {
        save(new ObjectOutputStream(new FileOutputStream(path)));
    }

    /**
     * Save graph in the stream
     *
     * @param output object stream
     * @throws Exception
     */
    public void save(ObjectOutputStream output) throws Exception {
        output.writeObject(this);
    }

    /**
     * Load graph from file
     *
     * @param path name of the file
     * @return graph or null
     * @throws Exception
     */
    public static AlgorithmGraph load(String path) throws Exception {
        return load(new ObjectInputStream(new FileInputStream(path)));
    }

    /**
     * Load graph from Object stream
     *
     * @param objectInputStream Object Input stream
     * @return graph or null
     * @throws Exception
     */
    public static AlgorithmGraph load(ObjectInputStream objectInputStream) throws Exception {
        AlgorithmGraph obj = (AlgorithmGraph) objectInputStream.readObject();
        return obj;
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static void doSave(String file) {
        JPanel pn = new JPanel(true);
        JFrame window = new JFrame(file);
        window.setLayout(new BorderLayout());
        window.add(pn, BorderLayout.CENTER);
        AlgorithmGraph flux = new AlgorithmGraph(pn, new Program());
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        flux.alignPatterns();
        window.setVisible(true);
        try {
            flux.save(file);
        } catch (Exception ex) {
            Logger.getLogger(AlgorithmGraph.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void doLoad(String file) throws Exception {
        JFrame window = new JFrame(file);
        window.setLayout(new BorderLayout());
        AlgorithmGraph flux = AlgorithmGraph.load(file);
        window.add(flux.graph, BorderLayout.CENTER);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        flux.alignPatterns();
        flux.positionCalc.translateTo(400, 400);
        window.setVisible(true);

    }

    public void deselectAll() {
        for (Component c : graph.getComponents()) {
            if (c instanceof Fshape) {
                ((Fshape) c).setColorInExecution(false);
            }
        }
    }

    /**
     * get
     *
     * @return
     */
    public Rectangle getFluxBounds() {
        return positionCalc.getDrawBounds(graph);
    }

    public void moveToXY(int px, int py) {
        positionCalc.moveToXY(px, py);
    }

    public List<Fshape> parse() {
        ArrayList<Fshape> errors = new ArrayList<>();
        for (Component c : graph.getComponents()) {
            if (c instanceof Fshape) {
                Fshape shape = (Fshape) c;
                shape.algorithm = this;
                try {
                    shape.algorithm = this;
                    shape.algorithm.myProgram = this.myProgram;
                    shape.parseShape();
                } catch (FlowchartException ex) {
                    shape.setSelected(true);
                    errors.add(shape);
                    CompileExection.show(ex, shape);
                }
            }
        }
        graph.repaint();
        return errors;
    }

    public String txtLog() {
        StringBuilder txt = new StringBuilder();
        txt.append("\n NAME :" + name);
        txt.append("\n PROGRAM :" + myProgram.fileName);
        txt.append("\n Local Memory :" + myLocalMemory);
        for (Component c : graph.getComponents()) {
            if (c instanceof Fshape) {
                txt.append("\n" + ((Fshape) c).getFullInstruction());
            }
        }
        return txt.toString();
    }

    public String toString() {
        return name;
    }

    public static String INIT_COMMENTS = FkeyWord.get("KEYWORD.comments")
            + ":::::::::::::::::::::::::::::::::::::::::::::::::::::::";
//    private static String FIN_COMMENTS = Fshape.COMMENT_STR
//            + "......................................................";

    public String getPseudoCode() {
        StringBuilder code = new StringBuilder(INIT_COMMENTS + "\n");

        Fshape current = getBegin();
        do {
            //ignore arrows
            if (!(current instanceof Arrow)) {
                code.append(current.getPseudoCodeWithComments()).append("\n");
            }
            current = current.next;
        } while (current != null);

        return code.toString();
    }

    public String getPseudoTokens() {
        StringBuilder code = new StringBuilder();

        Fshape current = getBegin();
        do {
            //ignore arrows
            if (!(current instanceof Arrow)) {
                code.append(current.getPseudoTokensWithComments()).append("\n");
            }
            current = current.next;
        } while (current != null);

        return code.toString();
    }

    public String getLanguage() {
        StringBuilder code = new StringBuilder();

        Fshape current = getBegin();
        do {
            //ignore arrows
            if (!(current instanceof Arrow) && !(current instanceof End)) {
                try {
                    code.append(current.getLanguage()).append("\n");
                } catch (FlowchartException ex) {
                    Logger.getLogger(AlgorithmGraph.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            current = current.next;
        } while (current != null);

        return code.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
    
    public static void main(String[] args) {
        FProperties.init();
        AlgorithmGraph alg = new AlgorithmGraph(new JPanel(), new Program());
    }

}
