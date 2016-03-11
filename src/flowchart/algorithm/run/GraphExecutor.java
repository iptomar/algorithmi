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
package flowchart.algorithm.run;

import ui.FLog;
import ui.flowchart.console.Console;
import core.FunctionCall;
import core.Memory;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import ui.FProperties;
import ui.flowchart.dialogs.Fdialog;
import i18n.Fi18N;
import flowchart.algorithm.FunctionGraph;
import flowchart.algorithm.GlobalMemoryGraph;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.Program;
import flowchart.algorithm.utils.ShapePositions;
import flowchart.arrow.dummy.DummyFunctionReturn;
import flowchart.function.Function;
import flowchart.shape.Fshape;
import flowchart.utils.FluxImage;
import flowchart.utils.UserName;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

/**
 *
 * @author zulu
 */
public class GraphExecutor  {

    JPopupMenu rightMenu; // left button menu

    public static int POSITION_Y = 10;//---------------------------------------- distance from the top
    public static final int GAP_X = 50; //-------------------------------------- distance between functions

    private Program template; //------------------------------------------------ original program with functions templates
    public JPanel graph;//------------------------------------------------------ to suppor graph objects
    public JScrollPane view;//-------------------------------------------------- to scrool and display  graph objects

    // used to remove nodes and connection arrows when the functions return
    Stack<List<Fshape>> graphInExecution = new Stack<>(); //-------------------- Graph of functions in execution to display 
    //used to display memory
    Stack<AlgorithmGraph> functionsInExecution = new Stack<>(); //--------------Functions in execution

    private Fshape nodeInExecution; //------------------------------------------ node in execution    
    Fshape lastExecuted; //---------------------------------------------------- last node executed

    private Console runtimeConsole = new Console();  //------------------------- Console of the program

    private Memory runtimeMemory = null; //------------------------------------- Memory of current algorithm

    public GraphExecutor(Program template) {
        this.template = template.getClone(); //clone template to preserve original in the editor
        graph = new JPanel(null);
        this.graph.setLayout(null); // to suport x,y positions
        view = new JScrollPane(graph);
        createPopupMenu();
        graph.setComponentPopupMenu(rightMenu);
        restart();
    }

    public void restart() {
        lastExecuted = null;
        graph.removeAll(); // remove all the shapes

        runtimeConsole.clear(); // clear console
        graphInExecution.clear(); // clear functions graph
        functionsInExecution.clear(); // clear functions

        if (template.getGlobalMem() != null) { //------------------------------- global memory defined
            AlgorithmGraph mem = template.getGlobalMem().getClone();
            nodeInExecution = mem.getBegin();
            addFunction(mem); // put memory in execution

        } else { //------------------------------------------------------------- main functions
            AlgorithmGraph main = getTemplate().getMain().getClone();
            nodeInExecution = main.getBegin();
            addFunction(main); // put main function in execution
        }

        runtimeMemory = new Memory(Fi18N.get("RUNTIME.memoryName"));

        graph.repaint();
        view.updateUI();
        view.repaint();
        nodeInExecution.setColorInExecution(true);
        graph.setComponentPopupMenu(rightMenu);
    }

    /**
     * expand function calls in the expression
     *
     * @param caller shape with expression
     * @param exp expression to expand
     * @return node to execute in next step (begin of the function or the shape)
     */
    public Fshape expandExpression(Fshape caller, Expression exp) {
        if (exp == null) {
            return caller;
        }
        FunctionCall func = exp.getFirstDefinedFunction();
        if (func != null) { // defined function to execute
            return addNewFunction(func, caller);
        }
        return caller; // not have funvtionCalls
    }

    /**
     * insert a function call in the program
     *
     * @param func function call (algorithm and parameters)
     * @param callArrow Arrow that
     * @param mem
     * @return
     */
    public Fshape addNewFunction(FunctionCall func, Fshape caller) {

        //show in execution the call Func
        lastExecuted = null;
//        ((BorderFlowChart) caller.getBorder()).setInExecution();
        //Log.printLn(template.txtLog());
        //clone function template
        FunctionGraph newFunc = (FunctionGraph) template.getFunctionByName(func.getName()).getClone();
        //get begin of functions
        Function begin = (Function) newFunc.getBegin();
        Fshape end = newFunc.getEnd();
        //update parameters of newFunc with expressions of func
        begin.expandParameters(runtimeMemory, func); //create symbols to compute parameters
        //align patterns
        newFunc.alignPatterns();
        //add new function to graph
        addNewFunction(newFunc);
        // create dummy to cleanup graph in the end
        DummyFunctionReturn dummy = new DummyFunctionReturn(func, begin);
        end.next = dummy;
        dummy.next = caller;
        return begin;

    }

    public Fshape addMain() {
        return addFunction(template.getMain().getClone());
    }

    private Fshape addFunction(AlgorithmGraph newFunc) {
        //show in execution the call Func
        lastExecuted = null;
        //get begin of functions
        Fshape begin = newFunc.getBegin();
        //add new function to graph
        addNewFunction(newFunc);
        return begin;
    }

    /**
     * add a new functionto the execution graph
     *
     * @param module new funtions or memory or main program
     */
    public void addNewFunction(AlgorithmGraph module) {
        List<Fshape> newFunctionShapes = new ArrayList<>(); // shapes of the new function
        Rectangle r = ShapePositions.getDrawBounds(graph);  // limits of the new function
        // move shapes in the graph
        module.moveToXY(FProperties.SPACE_BETWEEN_LEVELS + r.x + r.width, POSITION_Y); // move function shapes
        //insert graph
        for (Component c : module.graph.getComponents()) { //------------------- add shapes and arrows
            if (c instanceof Fshape) {
                Fshape instr = (Fshape) c;
                instr.setEditable(false);
//                instr.setToolTipText(null);
                graph.add(instr);
                newFunctionShapes.add(instr); //----------------------------------- save ref to shape elimination
            }
        }
        graphInExecution.push(newFunctionShapes); // add new function shapes
        //update bounds
        r = ShapePositions.getDrawBounds(graph);
        graph.setPreferredSize(new Dimension(r.x + r.width, r.y + r.height));
        graph.repaint();
        graph.revalidate();
    }

    /**
     * Execute Instruction
     * @throws FlowchartException 
     */
    public void executeNext() throws FlowchartException {

        if (getNodeInExecution() == null) {//----------------------------------- program is terminated
            return;
        }
        lastExecuted = nodeInExecution; // save the current node to clean color
        nodeInExecution = nodeInExecution.execute(this); //--------------------- execute node
        if (lastExecuted != null) {
            lastExecuted.setColorInExecution(false);
        }
        if (nodeInExecution != null) {
            nodeInExecution.setColorInExecution(true);//display in execution
        }

        Rectangle vis = view.getViewport().getViewRect();//--------------------- scroll if necessary
        if (nodeInExecution != null && !vis.contains(nodeInExecution.getBounds())) {
            view.getViewport().setViewPosition(nodeInExecution.getLocation()); // put shape in the top left corner of the viewport
            view.repaint();
        }
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: jump over arrows :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//        if (nodeInExecution instanceof Arrow) {
//            executeNext();
//        }
    }


    /**
     * @return the template
     */
    public Program getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(Program template) {
        this.template = template;
    }

    /**
     * @return the runtimeConsole
     */
    public Console getConsole() {
        return runtimeConsole;
    }

    /**
     * @param console the runtimeConsole to set
     */
    public void setConsole(Console console) {
        this.runtimeConsole = console;
    }

    /**
     * @return the runtimeMemory
     */
    public Memory getRuntimeMemory() {
        return runtimeMemory;
    }

    //-################################################################################### OPTIMIZATION NEEDED #######
    /**
     * @return the runtimeMemory
     */
    public List<Memory> getProgramMemory() {
        List<Memory> mem = new ArrayList<>();
        for (AlgorithmGraph alg : functionsInExecution) {
            mem.add(alg.myLocalMemory);
        }
        mem.add(runtimeMemory);
        return mem;
    }

    /**
     * @param mem the runtimeMemory to set
     */
    public void addNewMemory(Memory mem) {
        this.runtimeMemory = mem;
    }

    /**
     * @return the nodeInExecution
     */
    public Fshape getNodeInExecution() {
        return nodeInExecution;
    }

    /**
     * @return the nodeInExecution
     */
    public Fshape getNodeExecuted() {
        return lastExecuted;
    }

    /**
     * @return the nodeInExecution
     */
    public void stopExecution() {
        nodeInExecution = null;
    }

    public void zoomOut() {
        for (Component c : graph.getComponents()) {
            Fshape instr = (Fshape) c;
            instr.zoomOut();
        }
    }

    public void log() {
        StringBuilder txt = new StringBuilder();
        for (int i = 0; i < functionsInExecution.size(); i++) {
            txt.append("\n FUNCTION " + i + " " + functionsInExecution.get(i).getFunctionName());
            txt.append("\t MEMORY " + i + " " + functionsInExecution.get(i).myLocalMemory.toFlatString());
        }

        txt.append("\n\n RUNTIME MEMORY  " + runtimeMemory.toFlatString());

        txt.append("\n Node Execution :" + nodeInExecution);

        FLog.printLn(txt.toString());
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
    /**
     * add a symbol to the memory
     *
     * @param var variable do add
     */
    public void addSymbolToMemory(Fsymbol var) {
        runtimeMemory.add(var); // add to runtime memory
        functionsInExecution.peek().myLocalMemory.add(var); // add to function memory
    }

    /**
     * add a symbol to the memory
     *
     * @param var variable do add
     */
    public void removeSymbolFromMemory(Fsymbol var) {
        runtimeMemory.remove(var); // remove to runtime memory
        functionsInExecution.peek().myLocalMemory.remove(var); // remove to function memory
    }

    /**
     * remove the symbols in the memory whith level greater or equal to
     * parameter.
     *
     * @param level level to clear
     */
    public void clearMemoryLevel(int level) {
        runtimeMemory.clearLevel(level);
        functionsInExecution.peek().myLocalMemory.clearLevel(level);

    }

    public void initRuntimeProgram() {
        //create runtime memory
        runtimeMemory = new Memory(Fi18N.get("RUNTIME.memoryName"));
        //init console
        runtimeConsole.clear();
    }

    public void createFunctionToProgram(AlgorithmGraph func) {
        //addFunction
        functionsInExecution.push(func);
        //create local memory 
        func.myLocalMemory = new Memory(func.getFunctionName());
        //create new Runtime Memory
        runtimeMemory.clear();
        //add global memory
        addGlobalMemoryToRuntime();
    }

    private void addGlobalMemoryToRuntime() {
        if (functionsInExecution.get(0) instanceof GlobalMemoryGraph) {
            ArrayList<Fsymbol> global = functionsInExecution.get(0).myLocalMemory.getMem();
            for (Fsymbol var : global) {
                runtimeMemory.add(var);
            }
        }
    }

    public void removeLastFunction() {
        functionsInExecution.pop(); // remove function
        //remove nodes and arrow from the visual graph
        List<Fshape> last = graphInExecution.pop();
        for (Fshape shape : last) {
            graph.remove(shape);
        }
        //update runtimeMemory
        runtimeMemory.clear();
        //Add global memory
        addGlobalMemoryToRuntime();
        //add oldMemory
        Memory oldMem = functionsInExecution.peek().myLocalMemory;
        for (Fsymbol var : oldMem.getMem()) {
            runtimeMemory.add(var);
        }
        graph.revalidate();
        graph.repaint();
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: POPUP MENU
    public void createPopupMenu() {

        rightMenu = new JPopupMenu();
        JMenuItem clipboardImg = new JMenuItem();
        Fi18N.loadMenuItem(clipboardImg, "BUTTON.clipboardImg", 24);
        clipboardImg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FluxImage.copyToClipBoard(graph,UserName.createUser(template.digitalSignature));
            }
        });

        JMenuItem savePNG = new JMenuItem();
        Fi18N.loadMenuItem(savePNG, "BUTTON.saveImage", 24);
        savePNG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FluxImage.saveTofile(graph,UserName.createUser(template.digitalSignature));
            }
        });

        rightMenu.add(clipboardImg);
        rightMenu.add(savePNG);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
