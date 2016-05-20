/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.autoEvaluate;

import core.data.exception.FlowchartException;
import flowchart.algorithm.Program;
import flowchart.algorithm.run.GraphExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.flowchart.console.Console;

/**
 *
 * @author Luis
 */
public class AutoExecute extends Thread{

    public static String TIME_OVER = "TIME OVER";
    public static String EXCEPTION = "ERROR";
    public static long TIME_TO_RUN = (long) 1E6;
    public String error = "";
    public String output = ""; // output do programa
    ExecuteProgram execute;

    String input;
    Program prog;

    public AutoExecute(String input, Program prog) {
        this.input = input;
        this.prog = prog;

        execute = new ExecuteProgram(this);
        start();
    }

    @Override
    public void run() { // verify if the execution is ok
        long timeToPeek = TIME_TO_RUN / 100;
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(timeToPeek);
                if (!execute.isRunning) { // execution is done
                    return;
                }
            } catch (InterruptedException ex) {
                error = EXCEPTION + " " + ex.toString();
            }
        }
        error = TIME_OVER;
    }

    private class ExecuteProgram extends Thread {

        AutoExecute program;
        public boolean isRunning = true;

        public ExecuteProgram(AutoExecute runningThread) {
            this.program = runningThread;
            start();
        }

        public void run() {
            try {
                Console myConsole = new Console();
                myConsole.setInput(input);
                GraphExecutor run = new GraphExecutor(prog, myConsole);
                while (!run.isDone()) {
                    run.executeFast();
                }
                output = myConsole.getOutput();
            } catch (FlowchartException ex) {
                error = EXCEPTION + " " + ex.getMessage();
            }
            isRunning = false;
        }

    }

}
