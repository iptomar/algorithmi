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
import flowchart.arrow.Arrow;
import flowchart.decide.Do_Connector;
import flowchart.decide.Do_While;
import flowchart.decide.IfElse.IfThenElse;
import flowchart.decide.While_Do;
import flowchart.decide.forNext.For_Next;
import flowchart.define.Define;
import flowchart.execute.Execute;
import flowchart.function.Function;
import flowchart.jump.Jump;
import flowchart.read.Read;
import flowchart.returnFunc.Return;
import flowchart.shape.Fshape;
import flowchart.terminator.Begin;
import flowchart.terminator.BeginGlobalMemory;
import flowchart.terminator.End;
import flowchart.write.Write;
import i18n.Fi18N;
import i18n.FkeywordToken;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.JPanel;
import ui.FProperties;
import ui.flowchart.dialogs.Fdialog;

/**
 * Created on 30/dez/2015, 12:01:41
 *
 * @author zulu - computer
 */
public class ProgramFile {

    public static final String userTagName = "USER";
    public static final String userTagCode = "CODE";
    public static final String userTagFullName = "NAME";
    public static final String fileCreationTime = "DATE";
    public static final String coreVersion = "VERSION ";
    public static final String problemID = "ID";

    public static String getProgramHeader(Program prog) {
        String COMMENT = FkeywordToken.get("KEYWORD.comments.key");
        StringBuilder code = new StringBuilder();
        UserName user = UserName.createUser(prog.digitalSignature);
        code.append(COMMENT + MyString.setSize(" " + problemID, 10) + " " + prog.problemID + "\n");
        code.append(COMMENT + MyString.setSize(" " + userTagCode, 10) + " " + user.getCode() + "\n");
        code.append(COMMENT + MyString.setSize(" " + userTagName, 10) + " " + user.getName() + "\n");
        code.append(COMMENT + MyString.setSize(" " + userTagFullName, 10) + " " + user.getFullName() + "\n");
        code.append(COMMENT + MyString.setSize(" " + fileCreationTime, 10) + " " + prog.timeOfCreation + "\n");
        code.append(COMMENT + MyString.setSize(" " + coreVersion, 10) + " " + Fi18N.get("FLOWCHART.application.title")
                + " " + MyString.toString(new Date(prog.timeOfCreation)) + "\n");
        code.append("\n\n");
        return code.toString();
    }

    public static Program loadFromFile(String tokProgram) throws FlowchartException {
        String txt = FileUtils.readGZipStringFromFile(FileUtils.changeExtension(tokProgram, FileUtils.FILTER_PROG_EXT));
        Program prog = buildFromTokens(txt);
        return prog;//createProgram(txt);
    }
    /**
     * remove the program information of the program and sets it to program
     * @param tokProgram tokens of program
     * @return iterator to the code
     */
    private static IteratorLines getHeaderOfProgram(String tokProgram, Program prog) {
        String COMMENT = FkeywordToken.get("KEYWORD.comments.key");
        IteratorLines it = new IteratorLines(tokProgram);
        while (it.hasNext() && it.peek().trim().startsWith(COMMENT)) {
            //remove Comment
            String h = it.next().substring(COMMENT.length()).trim()+ " "; // insert space to prevent error
            String tag = h.substring(0,h.indexOf(" ")).trim();
            String code = h.substring(h.indexOf(" ")).trim();
            switch (tag) {
                case userTagName:
                    UserName user = FProperties.load(code);
                    prog.digitalSignature = user.digitalSignature();
                    break;
                case problemID:
                    prog.problemID = Long.parseLong(code);
                    break;
                case fileCreationTime:
                    prog.timeOfCreation = Long.parseLong(code);
                    break;
            }
        }
        return it;
    }

    /**
     * build a program from a string
     *
     * @param tokProgram string of the program
     * @return Program
     */
    public static Program buildFromTokens(String tokProgram) throws FlowchartException {
        try {
            Program prog = new Program(); // program to load
            AlgorithmGraph currentAlgorithm = null;  // algorithm of instructions
            LinkedList<Instruction> instructions = new LinkedList<>();
            //information of program  
            IteratorLines it = getHeaderOfProgram(tokProgram, prog);


            while (it.hasNext()) {
//                String comment = getComents(it);
//                String line = it.next();
//                Fshape shape = createShape(getType(line), comment, null);
                Instruction newShape = new Instruction(it, currentAlgorithm);
                instructions.add(newShape);
                //-------------------------------------------------------------DEFINED FUNCTION
                if (newShape.shape instanceof Function) {
                    FunctionGraph func = new FunctionGraph(new JPanel(), prog);
                    func.getBegin().setComments(newShape.shape.comments);
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
                    } //----------------------------------------------------------- LOAD MAIN
                    else if (newShape.shape instanceof Begin) {
                        AlgorithmGraph main = new AlgorithmGraph(new JPanel(), prog);
                        main.getBegin().setComments(newShape.shape.comments);
                        prog.setMain(main);
                        currentAlgorithm = main;
                        // loadAlgoritmhFunction(it, main);
                    }
            }
            createProgram(prog, instructions);
            return prog;
        } catch (Exception e) {
            throw new FlowchartException(e);
        }

    }

    /**
     * build a program from a string
     *
     * @param tokProgram string of the program
     * @return Program
     */
    protected static void createProgram(Program prog, LinkedList<Instruction> instructions) throws FlowchartException {
        try {

            while (!instructions.isEmpty()) {
                //get next instruction
                Instruction newShape = instructions.removeFirst();
                //-------------------------------------------------------------DEFINED FUNCTION
                if (newShape.shape instanceof Function) {
                    FunctionGraph func = prog.getFunctionByName(((Function) newShape.shape).getFunctionName());
                    loadAlgoritmhFunction(func, instructions);
                } else//----------------------------------------------------------LOAD GLOBAL MEMORY
                 if (newShape.shape instanceof BeginGlobalMemory) {
                        GlobalMemoryGraph mem = prog.getGlobalMem();
                        loadAlgoritmhFunction(mem, instructions);
                    } //----------------------------------------------------------- LOAD MAIN
                    else if (newShape.shape instanceof Begin) {
                        AlgorithmGraph main = prog.getMain();
                        loadAlgoritmhFunction(main, instructions);
                    } //----------------ERROR
                    else {
                        throw new FlowchartException(new RuntimeException("Prograaming ERROR public static Program createProgram(String tokProgram"));
                    }
            }
        } catch (Exception e) {
            throw new FlowchartException(e);
        }

    }

    protected static void loadAlgoritmhFunction(AlgorithmGraph alg, LinkedList<Instruction> instructions) throws FlowchartException {
        Arrow cursor = (Arrow) alg.getBegin().next;
        while (!instructions.isEmpty()) {
            Instruction newShape = instructions.removeFirst();
            //terminate function
            if (newShape.isEnd()) {
                alg.getEnd().setComments(newShape.comments);
                alg.alignPatterns();
                return;
            }

            cursor = addShape(cursor, newShape, instructions, alg);

        }
    }

    private static Arrow addShape(Arrow cursor, Instruction newShape, LinkedList<Instruction> instructions, AlgorithmGraph alg) throws FlowchartException {
//::::::::: IF ::::::::::::::::::::::::::::::::::::::::::::::::::::::::: IF
        if (newShape.shape instanceof IfThenElse) {
            //convert to If Else
            IfThenElse ifElse = (IfThenElse) newShape.shape;
            //Add shape to algorithm
            alg.addShapeIfElse(cursor, ifElse);
            try {
                //build shape
                newShape.buildShape();
            } catch (FlowchartException ex) {
                Fdialog.compileException(ex);
            }
            //add instructions
            addIf(ifElse, instructions, alg);
            //return next
            return (Arrow) ifElse.next.next;
        } else //::::::::: WHILE :::::::::::::::::::::::::::::::::::::::::::::::WHILE
         if (newShape.shape instanceof While_Do) {
                //convert to If Else
                While_Do whileDo = (While_Do) newShape.shape;
                //Add shape to algorithm
                alg.addShapeWhileDo(cursor, whileDo);
                //build shape            
                try {
                    newShape.buildShape();
                } catch (FlowchartException ex) {
                    Fdialog.compileException(ex);
                }
                //add instructions
                addWhile(whileDo, instructions, alg);
                //return next
                return (Arrow) whileDo.next;
            } else //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::FOR
             if (newShape.shape instanceof For_Next) {
                    //convert to If Else
                    For_Next forNext = (For_Next) newShape.shape;
                    //Add shape to algorithm
                    alg.addShapeWhileDo(cursor, forNext);
                    //build shape            
                    try {
                        newShape.buildShape();
                    } catch (FlowchartException ex) {
                        Fdialog.compileException(ex);
                    }
                    //add instructions
                    addFor(forNext, instructions, alg);
                    //return next 
                    return (Arrow) forNext.next;
                } else //::::::::: DO . . .WHILE :::::::::::::::::::::::::::::::::::::::DO WHILE
                 if (newShape.shape instanceof Do_Connector) {
                        //convert to If Else
                        Do_While doWhile = new Do_While(alg);
                        //Add shape to algorithm
                        alg.addShapeDoWhile(cursor, doWhile);
                        //build shape            
                        try {
                            newShape.buildShape();
                        } catch (FlowchartException ex) {
                            Fdialog.compileException(ex);
                        }
                        //add instructions
                        addDoWhile(doWhile, instructions, alg);
                        //return next
                        return (Arrow) doWhile.next;
                    }//::::::::::::::::::::: SIMPLE SHAPE :::::::::::::::::::::::::::::
                    else {
                        //add shape to the algorithm
                        alg.addSimpleShape(cursor, newShape.shape);
                        //build shape            
                        try {
                            newShape.buildShape();
                        } catch (FlowchartException ex) {
                            Fdialog.compileException(ex);
                        }
                        //update cursor
                        return (Arrow) newShape.shape.next;
                    }
    }

    private static void addIf(IfThenElse shapeIf, LinkedList<Instruction> instructions, AlgorithmGraph alg) throws FlowchartException {
        Arrow cursor = (Arrow) shapeIf.right;
        //while have instructions
        while (!instructions.isEmpty()) {
            //new instruction
            Instruction newShape = instructions.removeFirst();
            //end if
            if (newShape.isEnd()) {
                alg.alignPatterns();
                return;
            }
            //else
            if (newShape.type.equalsIgnoreCase(IfThenElse.KEY_ELSE)) {
                cursor = (Arrow) shapeIf.left;
                continue;
            }
            //add instruction
            cursor = addShape(cursor, newShape, instructions, alg);
        }
    }

    private static void addDoWhile(Do_While shapeDoWhile, LinkedList<Instruction> instructions, AlgorithmGraph alg) throws FlowchartException {
        Arrow cursor = (Arrow) shapeDoWhile.parent.parent.right; // right of do
        //while have instructions
        while (!instructions.isEmpty()) {
            //new instruction
            Instruction newShape = instructions.removeFirst();
            //end if
            if (newShape.type.equalsIgnoreCase(Do_While.KEY)) {
                shapeDoWhile.buildInstruction(newShape.instruction, newShape.comments);
                alg.alignPatterns();
                return;
            }
            //add instruction
            cursor = addShape(cursor, newShape, instructions, alg);
        }
    }

    private static void addWhile(While_Do shapeWhile, LinkedList<Instruction> instructions, AlgorithmGraph alg) throws FlowchartException {
        Arrow cursor = (Arrow) shapeWhile.right;
        //while have instructions
        while (!instructions.isEmpty()) {
            //new instruction
            Instruction newShape = instructions.removeFirst();
            //end if
            if (newShape.isEnd()) {
                alg.alignPatterns();
                return;
            }
            //add instruction
            cursor = addShape(cursor, newShape, instructions, alg);
        }
    }

    private static void addFor(For_Next shapeFor, LinkedList<Instruction> instructions, AlgorithmGraph alg) throws FlowchartException {
        Arrow cursor = (Arrow) shapeFor.right;
        //while have instructions
        while (!instructions.isEmpty()) {
            //new instruction
            Instruction newShape = instructions.removeFirst();
            //end if
            if (newShape.isEnd()) {
                alg.alignPatterns();
                return;
            }
            //add instruction
            cursor = addShape(cursor, newShape, instructions, alg);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    protected static class Instruction {

        private static String COMMENTS = FkeywordToken.get("KEYWORD.comments.key");
        public String type;
        public String instruction;
        public String comments;

        Fshape shape;

        public Instruction(IteratorLines it, AlgorithmGraph alg) {
            this.comments = getMyComents(it);
            String line = it.next();
            this.type = getMyType(line);
            this.instruction = FkeywordToken.translateTokensToWords(getMyInstruction(line));
            createShape(alg);
        }

        /**
         * create a shape
         *
         * @param prog
         * @param line
         * @param comment
         */
        private final void createShape(AlgorithmGraph alg) {
            try {
                if (type.equalsIgnoreCase(Function.KEY)) {//------------------------------FUNCTION
                    shape = new Function(); // header function do not have algorithm
                } else if (type.equalsIgnoreCase(BeginGlobalMemory.KEY)) {//----------BEGIN MEMORY
                    shape = new BeginGlobalMemory(null);
                } else if (type.equalsIgnoreCase(Begin.KEY)) {//------------------------------BEGIN
                    shape = new Begin(null);
                } else if (type.equalsIgnoreCase(End.KEY)) {//-------------------------END
                    shape = new End(alg);
                } else if (type.equalsIgnoreCase(Define.KEY)) {//-----------------------DEFINE
                    shape = new Define(alg);
                } else if (type.equalsIgnoreCase(Write.KEY)) {//-------------------------WRITE
                    shape = new Write(alg);
                } else if (type.equalsIgnoreCase(Read.KEY)) {//---------------------------READ
                    shape = new Read(alg);
                } else if (type.equalsIgnoreCase(Execute.KEY)) {//------------------------EXECUTE
                    shape = new Execute(alg);
                } else if (type.equalsIgnoreCase(IfThenElse.KEY)) {//------------------IF ELSE
                    shape = new IfThenElse(alg);
                } else if (type.equalsIgnoreCase(While_Do.KEY)) {//------------------While DO
                    shape = new While_Do(alg);
                } else if (type.equalsIgnoreCase(Do_Connector.KEY)) {//------------------ DO WHILE
                    shape = new Do_Connector(alg);
                } else if (type.equalsIgnoreCase(For_Next.KEY)) {//------------------ for
                    shape = new For_Next(alg);
                } else if (type.equalsIgnoreCase(Return.KEY)) {//------------------ return
                    shape = new Return(alg);
                } else if (type.equalsIgnoreCase(Jump.KEY_BREAK) || type.equalsIgnoreCase(Jump.KEY_CONTINUE)) {//------------------ jump
                    shape = new Jump(alg);
                }
                shape.setComments(comments);
            } catch (Exception e) {
            }
        }

        private String getMyType(String line) {
            if (line.indexOf(" ") > 0) {
                return line.substring(0, line.indexOf(" ")).trim();
            }
            return line.trim();
        }

        private String getMyInstruction(String line) {
            if (line.indexOf(" ") > 0) {
                return line.substring(line.indexOf(" ") + 1).trim();
            }
            return line.trim();
        }

        private String getMyComents(IteratorLines it) {
            StringBuilder txt = new StringBuilder();
            while (it.hasNext() && getMyType(it.peek()).equalsIgnoreCase(COMMENTS)) {
                txt.append(getMyInstruction(it.next()) + "\n");
            }
            return txt.toString();
        }

        public boolean isEnd() {
            return type.equalsIgnoreCase(End.KEY);
        }

        public void buildShape() throws FlowchartException {
            shape.buildInstruction(instruction, comments);
            shape.parseShape();
        }

        public String toString() {
            return shape.getType() + " " + instruction;
        }

    }
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201512301201L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
