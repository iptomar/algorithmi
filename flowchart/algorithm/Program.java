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

import flowchart.utils.FileUtils;
import core.Memory;
import core.data.exception.FlowchartException;
import flowchart.utils.ProgramFile;
import i18n.Fi18N;
import flowchart.shape.Fshape;
import flowchart.utils.UserName;
import i18n.FkeyWord;
import i18n.FkeywordToken;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.FLog;
import ui.FProperties;
import ui.utils.Crypt;

/**
 *
 * @author zulu
 */
public class Program implements Cloneable, Serializable {

    transient private static final String PROGRAM_EXTENSION = Fi18N.get("PROGRAM.file.filterExt");
    transient public static final String defaultFileName = Fi18N.get("PROGRAM.file.defaultFileName")
            + "."
            + PROGRAM_EXTENSION;

    public String digitalSignature; // digital signature of programmer
    public long timeOfCreation; // time of creation
    public String txtProblem; // description of the problem.
    public String fileName;
    private AlgorithmGraph main;  // main program
    private GlobalMemoryGraph globalMem; // definition of global memory
    private List<FunctionGraph> functions; // user defined functions

    public Program() {
        main = null;
        functions = new ArrayList<>();
        globalMem = null;
        fileName = defaultFileName;
        digitalSignature = FProperties.get(FProperties.keyDigitalSignature);
        timeOfCreation = new Date().getTime();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (!fileName.endsWith(FileUtils.FILTER_PROG_EXT)) {
            fileName += "." + FileUtils.FILTER_PROG_EXT;
        }
        this.fileName = fileName;
    }

    public AlgorithmGraph getMain() {
        return main;
    }

    public GlobalMemoryGraph getGlobalMem() {
        return globalMem;
    }

    public void setGlobalMem(GlobalMemoryGraph globalMem) {
        this.globalMem = globalMem;
    }

    public List<FunctionGraph> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionGraph> functions) {
        this.functions = functions;
    }

    public void setGlobalMemory(GlobalMemoryGraph mem) {
        this.setGlobalMem(mem);
        mem.myProgram = this;
    }

    public void addFunction(FunctionGraph func) {
        this.functions.add(func);
        func.myProgram = this;
    }

    public void setMain(AlgorithmGraph newMain) {
        this.main = newMain;
        newMain.myProgram = this;
    }

    public void remove(AlgorithmGraph flux) {
        if (flux == getGlobalMem()) {
            setGlobalMem(null);
        } else {
            this.getFunctions().remove(flux);
        }
    }

    /**
     * save file in silence
     */
    public void tryToSave() {
        if (fileName == null) {
            return;
        }
        try {
            FileUtils.saveProgram(this, fileName);
        } catch (IOException ex) {
            FLog.printLn("ERROR tryToSave " + fileName + " " + ex.getMessage());
        }
    }

    public void save() throws Exception {
        FileUtils.saveProgram(this, fileName);
    }

    public void saveAs() throws Exception {
        FileUtils.saveProgramAs(this);
    }

    /**
     * Load a program from a file
     *
     * @param filename PROPERTIES_PATH and file name
     * @return return Program
     * @throws FlowchartException
     */
    public static Program loadProgram(String filename) throws Exception {
        Program prog =  ProgramFile.loadFromFile(filename);
        prog.fileName = filename;
        return prog;
    }

   
    /**
     * NOTE : myProgram is transient in Algorithm graph update the Myprogram
     * property of all the algorithms in the program
     */
    public void updateProgramOfAlgorithms() {
        main.myProgram = this;
        if (globalMem != null) {
            globalMem.myProgram = this;
        }
        for (FunctionGraph function : functions) {
            function.myProgram = this;
        }
    }

    public Program getClone() {
        try {
            Program prog = ProgramFile.buildFromTokens(getTokens());
            prog.setFileName(fileName);
            return prog;
        } catch (FlowchartException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setZoom(int zoom) {
        main.setZoom(zoom);
        if (getGlobalMem() != null) {
            globalMem.setZoom(zoom);
        }
        for (FunctionGraph function : getFunctions()) {
            function.setZoom(zoom);
        }
    }

    public int getZoom() {
        return getMain().getZoom();
    }

    public void zoomIn() {
        int zoom = main.getZoom();
        setZoom((int) Math.ceil(zoom * 1.25));
    }

    public void zoomOut() {
        int zoom = main.getZoom();
        setZoom((int) Math.ceil(zoom * 0.75));
    }

    /**
     * verify if the new name is valid to the program duplicate names are not
     * allowed
     *
     * @param txt name of newSymbol
     * @return
     */
    public boolean isNewNameValid(String newName) {
        try {
            validateNewName(newName);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * verify if the new name is valid to the program
     *
     * @param txt name of newSymbol
     * @return
     */
    public void validateNewName(String newName) throws FlowchartException {
        //main program
        if (newName.equalsIgnoreCase(FkeyWord.get("KEYWORD.mainFunctionName"))) {
            throw new FlowchartException("FUNCTION.invalidNameMain", newName);
        }
        // duplicate memory
        if (getGlobalMem() != null && newName.equalsIgnoreCase(FkeyWord.get("KEYWORD.globalMemoryName"))) {
            throw new FlowchartException("FUNCTION.invalidNameMemory", newName);
        }
        //duplicated name
        for (FunctionGraph function : getFunctions()) {
            //function with same name 
            if (function.getFunctionName().equalsIgnoreCase(newName)) {
                throw new FlowchartException("FUNCTION.invalidDuplicateName", newName);
            }
        }
        //name equal to the same name of the function
        if (getGlobalMem() != null) {
            Memory mem = getGlobalMem().getGlobalMemory();
            if (mem.isDefined(newName)) {
                throw new FlowchartException("FUNCTION.invalidNameMemoryVar", newName);
            }
        }
    }

    /**
     * verify if the name is valid to the program
     *
     * @param txt name of newSymbol
     * @return
     */
    public void validateName(String newName) throws FlowchartException {
        // main program name is not valid
        if (newName.equalsIgnoreCase(FkeyWord.get("KEYWORD.mainFunctionName"))) {
            throw new FlowchartException("FUNCTION.invalidNameMain", newName);
        }
        //memory is defined and is new memory
        if (getGlobalMem() != null && newName.equalsIgnoreCase(FkeyWord.get("KEYWORD.globalMemoryName"))) {
            throw new FlowchartException("FUNCTION.invalidNameMemory", newName);
        }
        //duplicate function name
        int countNameFunction = 0; // number of functions with the same name
        for (FunctionGraph function : getFunctions()) {
            //same name 
            if (function.getFunctionName().equalsIgnoreCase(newName)) {
                countNameFunction++;
            }
            //duplicate name
            if (countNameFunction > 1) {
                throw new FlowchartException("FUNCTION.invalidDuplicateName", newName);
            }
        }
    }

    public String getDefaultNewName() {
        int num = 0;
        String defaultName = Fi18N.get("FUNCTION.defaultName");
        for (FunctionGraph func : getFunctions()) {
            if (func.getFunctionName().startsWith(defaultName)) {
                try {
                    int value = Integer.parseInt(func.getFunctionName().substring(defaultName.length()));
                    if (value > num) {
                        num = value;
                    }
                } catch (Exception e) {
                }

            }
        }
        return defaultName + (num + 1);
    }

    public boolean isCompilationOK() {
        return compile().isEmpty();
    }

    public List<Fshape> compile() {
        List<Fshape> erros = new ArrayList<>();
        if (globalMem != null) {
            erros.addAll(globalMem.parse());
        }
        erros.addAll(main.parse());
        for (FunctionGraph func : functions) {
            erros.addAll(func.parse());
        }
        return erros;
    }

    public FunctionGraph getFunctionByName(String name) {
        for (FunctionGraph function : getFunctions()) {
            if (function.getFunctionName().equalsIgnoreCase(name)) {
                return function;
            }
        }
        return null;
    }

    public String getPseudoCode() {
        StringBuilder code = new StringBuilder();
        UserName user = UserName.createUser(digitalSignature);
        code.append(FkeyWord.get("KEYWORD.comments") + Fi18N.get("TRANSLATOR.user") + " " + user.getName() + "\n");
        code.append(FkeyWord.get("KEYWORD.comments") + Fi18N.get("TRANSLATOR.code") + " " + user.getCode() + "\n");
        code.append(FkeyWord.get("KEYWORD.comments") + Fi18N.get("TRANSLATOR.fullName") + " " + user.getFullName() + "\n");
        code.append(FkeyWord.get("KEYWORD.comments") + Fi18N.get("TRANSLATOR.fileName") + " " + FileUtils.getFileFromPath(getFileName()) + "\n");
        code.append("\n\n");

        if (globalMem != null) {
            code.append(globalMem.getPseudoCode());
            code.append("\n");
        }
        code.append(main.getPseudoCode());
        for (FunctionGraph function : getFunctions()) {
            code.append("\n");
            code.append(function.getPseudoCode());
        }
        return code.toString() + "\n";
    }

    public String getTokens() {
        StringBuilder code = new StringBuilder();
        code.append(digitalSignature + "\n");
        if (globalMem != null) {
            code.append(globalMem.getPseudoTokens());
            code.append("\n");
        }
        code.append(main.getPseudoTokens());
        for (FunctionGraph function : getFunctions()) {
            code.append("\n");
            code.append(function.getPseudoTokens());
        }
        return code.toString().trim();
    }

    public String txtLog() {
        StringBuilder txt = new StringBuilder();
        txt.append("\nFile Name " + fileName);
        if (globalMem != null) {
            txt.append("\nGlobal Memory " + globalMem.txtLog());
        }
        txt.append("\nMAIN          " + main.txtLog());
        for (FunctionGraph function : functions) {
            txt.append("\nFUNCTION          " + function.txtLog());
        }
        return txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
