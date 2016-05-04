//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
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
package flowchart.utils;

import core.data.exception.FlowchartException;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.FunctionGraph;
import flowchart.algorithm.GlobalMemoryGraph;
import flowchart.algorithm.Program;
import flowchart.function.Function;
import flowchart.terminator.Begin;
import flowchart.terminator.BeginGlobalMemory;
import static flowchart.utils.ProgramFile.loadAlgoritmhFunction;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 * Created on 30/dez/2015, 12:01:41
 *
 * @author zulu - computer
 */
public class FunctionFile extends ProgramFile {

    /**
     * loads a funtion from token file
     *
     * @param fileName file name
     * @param prog program to function
     * @return function
     * @throws FlowchartException
     */
    public static AlgorithmGraph loadFromFile(String fileName, Program prog) throws FlowchartException {
        String txt = FileUtils.readGZipStringFromFile(FileUtils.changeExtension(fileName, FileUtils.FILTER_FLUX_EXT));
        return createFunctionToTokens(txt, prog);
    }
    /**
     * build a program from a string
     *
     * @param tokProgram string of the program
     * @return Program
     */
    public static AlgorithmGraph createFunctionToTokens(String tokProgram, Program prog) throws FlowchartException {
         AlgorithmGraph currentAlgorithm = null;  // algorithm of instructions
        try {
           
            LinkedList<Instruction> instructions = new LinkedList<>();
            IteratorLines it = new IteratorLines(tokProgram);
            //load header
            Instruction newShape = new Instruction(it, currentAlgorithm);
            // instructions.add(newShape);
            //-------------------------------------------------------------DEFINED FUNCTION
            if (newShape.shape instanceof Function) {
                FunctionGraph func = new FunctionGraph(new JPanel(), prog);
//                func.getBegin().setComments(newShape.shape.comments);
                newShape.shape.algorithm = func;
                newShape.buildShape();
                func.updateDefinition((Function) newShape.shape);
                currentAlgorithm = func;

                // loadAlgoritmhFunction(it, func);
            } else//----------------------------------------------------------LOAD GLOBAL MEMORY
            if (newShape.shape instanceof BeginGlobalMemory) {

                GlobalMemoryGraph mem = new GlobalMemoryGraph(new JPanel(), prog);
                mem.getBegin().setComments(newShape.shape.comments);
                prog.setGlobalMemory(mem);
                currentAlgorithm = mem;
                //loadAlgoritmhFunction(it, mem);
            } else //----------------------------------------------------------- LOAD MAIN
            if (newShape.shape instanceof Begin) {
                //---------------------------------------------
                prog = new Program(prog.myProblem.user); // create new program
                AlgorithmGraph main = new AlgorithmGraph(new JPanel(), prog);
                main.getBegin().setComments(newShape.shape.comments);
                prog.setMain(main);
                currentAlgorithm = main;
                // loadAlgoritmhFunction(it, main);
            } else {
                throw new FlowchartException(new RuntimeException("Connot load function " + newShape.toString()));
            }

            //load shapes
            while (it.hasNext()) {
                newShape = new Instruction(it, currentAlgorithm);
                instructions.add(newShape);
            }
            loadAlgoritmhFunction(currentAlgorithm, instructions);
            return currentAlgorithm;
        } catch (Exception e) {
            prog.remove(currentAlgorithm);
            throw new FlowchartException(e);
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201512301201L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    private static AlgorithmGraph createFunctionToTokens(String txt, AlgorithmGraph prog) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
