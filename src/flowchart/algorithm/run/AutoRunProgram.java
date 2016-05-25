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
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package flowchart.algorithm.run;

import flowchart.algorithm.Program;
import i18n.Fi18N;
import java.util.ArrayList;
import ui.flowchart.console.Console;

/**
 * Created on 23/mai/2016, 15:24:40
 *
 * @author zulu - computer
 */
public class AutoRunProgram implements Runnable {

    // maximum of running time ( 1 minute )
    public static long TIME_TO_EXECUTE = (long) 60E3;//1 minute
    public static long LIMIT_OF_OUTPUT = (long) 10E3; // 10 KB
    ExecuteProgram exe; // thread to buildOutput the program
    Program prog; // program to buildOutput
    String input;

    public AutoRunProgram(Program prog) {
        this.prog = prog;
    }

    public void buildOutput() {
        prog.myProblem.output = new ArrayList<>();
        for (String myInput : prog.myProblem.input) {
            input = myInput;
            exe = new ExecuteProgram(input, prog); // thread to buildOutput the program
            exe.start(); // start running
            Thread thr = new Thread(this);
            thr.start();
            try {
                thr.join();
            } catch (InterruptedException ex) {
            }
            if (exe.message.isEmpty()) {
                prog.myProblem.output.add(exe.console.getOutput());
            } else {
                prog.myProblem.output.add(exe.message);
            }
        }
    }

    public ArrayList<String> testProgram(Program studentProgram) {
        ArrayList<String> result = new ArrayList<>();
        for (String myInput : prog.myProblem.input) {
            exe = new ExecuteProgram(myInput, studentProgram); // thread to buildOutput the program
            exe.start(); // start running
            input = myInput;
            Thread thr = new Thread(this);
            thr.start();
            try {
                thr.join();
            } catch (InterruptedException ex) {
            }
            if (exe.message.isEmpty()) {
                result.add(exe.console.getOutput());
            } else {
                result.add(exe.message);
            }
        }
        return result;
    }

    @Override
    public void run() {
        //wait for termination
        long stepTime = TIME_TO_EXECUTE / 100;
        for (int i = 0; i < 100; i++) {
            try {
                //is Running
                if (!exe.isAlive()) {
                    return; // break thread
                }
                //wait
                Thread.sleep(stepTime);
            } catch (InterruptedException ex) {
            }
        }
        exe.interrupt(); // interrupt exe
        exe.message = Fi18N.get("RUNTIME.error.timeExceded");
    }

    private class ExecuteProgram extends Thread {

        Program prog; // program to buildOutput
        Console console; // console
        public String message = "";// Message of execution  

        public ExecuteProgram(String input, Program prog) {
            this.console = new Console();
            console.setInput(input);
            this.prog = prog;
        }

        public void run() {
            try {
                GraphExecutor run = new GraphExecutor(prog, console);
                while (!run.isDone()) {
                    run.executeFast();
                    if (console.getOutput().length() > LIMIT_OF_OUTPUT) {
                        message = Fi18N.get("RUNTIME.error.outputTooLong");
                        return;
                    }
                }
            } catch (Throwable ex) {
                message = Fi18N.get("RUNTIME.error.runtime") + " " + ex.toString();
            }

        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201605231524L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
