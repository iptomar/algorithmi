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
package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FLog from Flowchart Created on 24/out/2015, 10:59:55
 *
 * @author zulu - computer
 */
public class FLog {

    public static boolean DEBUG_APP = true; // write debug in file ?

    public static String EXCEPTION_ERROR_STR = "EXCEPTION ERROR "; // message of Compile Error
    public static String COMPILE_ERROR_STR   = "COMPILE   ERROR "; // message of Compile Error
    public static String RUNNING_ERROR_STR   = "RUN       ERROR "; // message of Running error

    private static PrintStream logFile = null;
    private static String fileName = System.getProperty("user.dir") + File.separator + "bin" + File.separator + "log.txt";
    private static SimpleDateFormat frmDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static int SEPARATOR_LINE_SIZE = 160;
    private static char SEPARATOR_LINE_CHAR = ':';

    static {
        if (DEBUG_APP) {
            try {
                File file = new File(fileName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }
                logFile = new PrintStream(new FileOutputStream(fileName, true));
            } catch (FileNotFoundException ex) {
                logFile = null;
            }
        }
    }

    public static void printLn(String msg) {
        msg = msg.replaceAll("\n", "</n>");
        print(msg + System.lineSeparator());
    }

    public static void runError(String msg) {
        printSeparator();
        print(RUNNING_ERROR_STR + msg + System.lineSeparator());
        printSeparator();
    }
    public static void compileError(String msg) {
        printSeparator();
        print(COMPILE_ERROR_STR + msg + System.lineSeparator());
        printSeparator();
    }

    public static void printSeparator() {
        printSeparator(SEPARATOR_LINE_SIZE);
    }

    public static void printSeparator(int size) {
        StringBuilder txt = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            txt.append(SEPARATOR_LINE_CHAR);
        }
        printLn(txt.toString());
    }

    public static void print(String msg) {
        msg = getLogMessage(msg);
        if (DEBUG_APP) {
            System.out.print(msg);
        }
        if (logFile != null) {
            logFile.print(msg);
        }
    }

    public static void close() {
        if (logFile != null) {
            logFile.close();
        }
    }

    private static String getLogMessage(String msg) {
        return frmDate.format(new Date()) + " " + msg;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510241059L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
