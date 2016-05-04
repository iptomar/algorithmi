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
package core.parser;

import core.Memory;
import core.FunctionCall;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import core.evaluate.CoreElement;
import core.evaluate.Evaluator;
import core.parser.tokenizer.BreakArrays;
import core.parser.tokenizer.BreakDefinedFunction;
import core.parser.tokenizer.BreakMarks;
import core.parser.tokenizer.BreakOperators;
import core.parser.tokenizer.BreakNumbers;
import core.parser.tokenizer.BreakSpaces;
import core.parser.tokenizer.ReplaceSymbols;
import core.parser.tokenizer.SetOfOperators;
import flowchart.algorithm.Program;
import flowchart.utils.ExpressionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.FLog;

/**
 * Created on 7/set/2015
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class Expression implements Serializable {

    public static boolean debug = false;
    public transient Program myProgram; // program of the expression to replace userdefined functions
    public Memory memory; // memory of the expression
    String text;  // original text
    private List<Object> tokens;  // tokens of the expression

    boolean isConstant; // expression that have always the same value (not use variables)
    // 2+3 = 5 (always)

    Fsymbol returnType; // if expression is contant returnType contains a value of expression
    // else contaisn the type of expression return

    private List<List<Object>> allExpressions; //auxilar list to verify the correcteness of the possibilities
    // of the expression

    //to create subexpressions
    private Expression() {
        tokens = new ArrayList<>();
    }

    public Expression(Fsymbol var) {
        this(var, null, null);
    }

    public Expression(Fsymbol var, Memory mem, Program prog) {
        this.myProgram = prog;
        this.text = var.getInstruction();
        this.memory = mem;
        //----------- TOKENIZER EXPRESION -------------------
        tokens = new ArrayList<>();
        tokens.add(var);
        isConstant = var.isConstant();
        returnType = var;
    }
//     public Expression(Fsymbol var, Memory mem) throws FlowchartException {
//         this(var, mem, null);
//     }

    public Expression(String expressionTxt, Memory mem, Program prog) throws FlowchartException {
        this.myProgram = prog;
        this.text = expressionTxt;
        this.memory = mem;
        //----------- TOKENIZER EXPRESION -------------------
        tokens = new ArrayList<>();
        tokens.add(expressionTxt);
        processTokens();
    }

//    public Expression(String expressionTxt, Memory mem) throws FlowchartException {
//        this(expressionTxt, mem, null);
//    }
    public Expression(List<Object> lst, Memory mem, Program prog) throws FlowchartException {
        this.myProgram = prog;
        this.text = ExpressionUtils.toString(lst);
        this.memory = mem;
        //----------- TOKENIZER EXPRESION -------------------
        tokens = lst;
        processTokens();

    }

//    public Expression(List<Object> lst, Memory mem) throws FlowchartException {
//        this(lst, mem, null);
//    }
    private void processTokens() throws FlowchartException {
        debug("Original", getTokens());
        tokens = BreakSpaces.execute(getTokens());
        debug("BreakSpaces", getTokens());
        tokens = BreakMarks.execute(getTokens());
        debug("BreakMarks", getTokens());
        tokens = BreakNumbers.execute(getTokens());
        debug("BreakRealNumbers", getTokens());
        //----------------- ARRAYS ------------------------
        tokens = BreakArrays.execute(getTokens(), memory, myProgram);
        debug("BreakArrays", getTokens());
        //-------------------------------------------------
        //----------------USER DEFINED FUNCTIONS -------------
        if (myProgram != null) {
            tokens = BreakDefinedFunction.execute(getTokens(), memory, myProgram);
            debug("BreakDefinedFunction", getTokens());
        }
        //-------------------------------------------------
        tokens = BreakOperators.execute(getTokens());
        debug("BreakOperators", getTokens());
        tokens = ReplaceSymbols.execute(getTokens(), memory);
        debug("ReplaceSymbols", getTokens());

        verifyTokens(getTokens());
        //------------ BUILD ALL POSSIBILITIES -------------
        buildAllExpressions();
//        System.out.println( debug());
        //--------- CHOOSE CORRECT ONE --------------------
        for (List<Object> test : allExpressions) {
            debug("Expression TEST ", test);
        }

        tokens = getCorrectTokens();
        debug("Expression ", getTokens());
        returnType = getReturnType(memory);
    }

    public Fsymbol getReturnType(Memory mem) throws FlowchartException {
        for (int i = 0; i < getTokens().size(); i++) {
            if (getTokens().get(i) instanceof Fsymbol) {
                Fsymbol varToken = (Fsymbol) getTokens().get(i);
                if (varToken.isVariable()) {
                    isConstant = false;
                    return Evaluator.verify(getTokens(), mem);
                }
            }
        }
        isConstant = true;
        return Evaluator.verify(getTokens(), mem);
    }

    public void parse(Memory mem) throws FlowchartException {
        //verify if all the tokens are defined
        verifyTokens(getTokens());
        //verify the defined simbols in memory
        for (int i = 0; i < getTokens().size(); i++) {
            if (getTokens().get(i) instanceof Fsymbol) {
                Fsymbol varToken = (Fsymbol) getTokens().get(i);
                Fsymbol memSymbol = mem.getByName(varToken.getName());
                if (varToken.isVariable() && memSymbol == null) {
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    throw new FlowchartException(
                            "DEFINE.variableNotDefined",
                            varToken.getName());
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                }
            }else if (getTokens().get(i) instanceof FunctionCall) {
                FunctionCall func = (FunctionCall) getTokens().get(i);    
                func.myAlgorithm = myProgram.getFunctionByName(func.getName());
                func.parse(mem);
                
            }

        }
    }

    public boolean isConstant() {
        return isConstant;
    }

    /**
     * get text of expression idented
     *
     * @return
     */
    public String getIdented() {
        return ExpressionUtils.identation(getTokens());
    }

    /**
     * get expression with values
     *
     * @return
     */
    public String getWithValue() {
        return ExpressionUtils.withValues(getTokens());
    }

    /**
     * evaluate expression
     *
     * @return
     * @throws FlowchartException
     */
    public Fsymbol evaluate(Memory mem) throws FlowchartException {
        return Evaluator.evaluate(getTokens(), mem);
    }

    /**
     * gets the type of expression result
     *
     * @return
     * @throws FlowchartException
     */
    public Fsymbol getReturnType() {
        return returnType;
    }

    /**
     * Get list of tokens
     *
     * @return
     */
    public List getTokens() {
        return tokens;
    }

    private void buildAllExpressions() {
        allExpressions = new ArrayList<>();
        List<Object> start = new ArrayList<>();
        allExpressions.add(start);
        processElement(0, start);
    }

    private void processElement(int index, List list) {
        //stop recusivity
        if (index >= getTokens().size()) {
            return;
        }
        //process Element
        if (getTokens().get(index) instanceof SetOfOperators) {
            //------------------------------------------------------------------
            //get all the posibilities of operators the starts in the string position
            List<List<CoreElement>> ops = ((SetOfOperators) getTokens().get(index)).listOfOperators;
            //------------------------------------------------------------------
            //if simbols cannot be convert in operators
            if (ops.isEmpty()) {
                //Wrong way : remove this list
                allExpressions.remove(list);
                return;
            }
            //------------------------------------------------------------------
            //add new lists to the operators possibilities 
            // (the first continues in the current list)
            //------------------------------------------------------------------
            //process elements (not the first) 
            for (int i = 1; i < ops.size(); i++) {
                //clone current list
                List clone = getClone(list);
                //add operator
                clone.addAll(ops.get(i));
                //add list to allExpressions list
                allExpressions.add(clone);
                //process next
                processElement(index + 1, clone);
            }
            //------------------------------------------------------------------
            //add first operator
            list.addAll(ops.get(0));
            //process next
            processElement(index + 1, list);
            //------------------------------------------------------------------
        } else {
            //------------------------------------------------------------------
            //add element (not operators)
            list.add(getTokens().get(index));
            //process next
            processElement(index + 1, list);
            //------------------------------------------------------------------
        }
    }

    private List getClone(List l) {
        List clone = new ArrayList<>();
        for (Object op : l) {
            clone.add(op);
        }
        return clone;
    }

    //--------------------------------------------------------------------------------------
    /**
     * get the list of tokens of the correct expression
     *
     * @return correct expression
     * @throws FlowchartException
     */
    private List<Object> getCorrectTokens() throws FlowchartException {
        List<List<Object>> correctExpressions = new ArrayList<>();
        for (List exp : allExpressions) {
            try {
                //verify if all elements are valid tokens
                verifyTokens(exp);
                //verify the return type
                Fsymbol test = Evaluator.verify(exp, memory);
                if (test != null) {
                    //store return type
                    this.returnType = test;
                    correctExpressions.add(exp);
                }
            } catch (Exception e) {
            }
        }
        //no valid expressions
        if (correctExpressions.isEmpty()) {
            throw new FlowchartException("EVALUATE.error.expressionError", text);
        }
        //many valid expressions
        if (correctExpressions.size() > 1) {
            //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::::::::::
            throw new FlowchartException(
                    text,
                    "EVALUATE.error.expressionAmbiguous",
                    new String[]{
                        ExpressionUtils.identation(correctExpressions.get(0)),
                        ExpressionUtils.identation(correctExpressions.get(1))
                    });
            //---------------FLOWCHART ERROR-----------------------
        }
        //all ok
        return correctExpressions.get(0);
    }
    /**
     * verify if all tokens are defined
     *
     * @param expression
     * @throws FlowchartException
     */
    private void verifyTokens(List expression) throws FlowchartException {
        for (Object elem : expression) {
            if (elem instanceof String) {
                //---------------FLOWCHART ERROR-----------------------
                throw new FlowchartException(
                        text,
                        "EVALUATE.error.unknowElement",
                        new String[]{
                            elem.toString(),
                            ExpressionUtils.head(expression, elem),
                            ExpressionUtils.between(expression, elem, elem),
                            ExpressionUtils.tail(expression, elem),}
                );
                //---------------FLOWCHART ERROR-----------------------
            }
        }
    }
    //-----------------------------------------------------------------------------------------------  

    public String toString() {
        return ExpressionUtils.identation(getTokens());
    }

    public String debug() {
        StringBuilder txt = new StringBuilder();
        for (List exp : allExpressions) {
            try {

                List pf = Evaluator.fromInfix(exp);
                txt.append("\n" + ExpressionUtils.debug(exp) + "\t");

                Fsymbol v = Evaluator.verify(exp, memory);
                if (v != null) {
                    try {
                        txt.append("\tRETURN " + v.getTypeName() + "\t " + ExpressionUtils.identation(exp));
                        txt.append(" \t= " + Evaluator.evaluate(exp, memory).getTextValue());
                    } catch (FlowchartException ex) {
                        Logger.getLogger(Expression.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (FlowchartException ex) {
                Logger.getLogger(Expression.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return txt.toString().trim();
    }

    private void debug(String title, List l) {
        if (debug) {
            FLog.print(title + "\nSize = " + l.size() + " =>");
            for (Object l1 : l) {
                FLog.print("[" + l1.getClass().getSimpleName() + " - " + l1.toString() + "] ");
            }
            FLog.printLn("");
        }
    }

    public Expression getClone() {
        try {
            return new Expression(text, memory, myProgram);
        } catch (FlowchartException ex) {
            return null;
        }
    }

    public Expression subExpression(int first, int last) {
        Expression sub = new Expression();
        sub.memory = memory;

        for (int i = first; i <= last; i++) {
            sub.getTokens().add(getTokens().get(i));
        }

        sub.text = sub.toString();
        return sub;
    }

    private void setNotInExecution() {
        for (Object token : getTokens()) {
            if (token instanceof FunctionCall) {
                FunctionCall f = (FunctionCall) token;
                f.inExecution = false; // change flag
            }
        }
    }

    /**
     * gets the reference of the first function Call in the expression
     *
     * @return first functionCall or null
     */
    public List<FunctionCall> getDefinedFunctionToExecution() {
        List<FunctionCall> defs = new ArrayList<>();
        for (Object token : getTokens()) {
            if (token instanceof FunctionCall) {
                FunctionCall f = (FunctionCall) token;
                //if in execution
                if (!f.inExecution) {
                    f.inExecution = true; // mark to execution
                    defs.add(f);  // add to execution
                    break; // one function at time
                }
            }
        }
        if (defs.isEmpty()) { // if all the defined functions are executed
            setNotInExecution();
        }
        return defs;
    }

    /**
     * gets the reference of the first function Call in the expression
     *
     * @return first functionCall or null
     */
    public FunctionCall getFirstDefinedFunction() {

        for (Object token : getTokens()) {
            if (token instanceof FunctionCall) {
                FunctionCall f = (FunctionCall) token;
                 //if in execution
                if (!f.inExecution) {
                    f.inExecution = true; // mark to execution
                    return f;
                }               
            }
        }
        setNotInExecution();
        return null;
    }

    public boolean isEmpty() {
        return getTokens().isEmpty();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071200L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
