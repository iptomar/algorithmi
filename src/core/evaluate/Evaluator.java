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
package core.evaluate;

import core.CoreCalculator;
import core.Memory;
import core.CoreToken;
import core.FunctionCall;
import core.data.Fsymbol;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import core.parser.Mark;
import core.parser.Expression;
import flowchart.algorithm.Program;
import flowchart.utils.ExpressionUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * http://scriptasylum.com/tutorials/infix_postfix/infix_postfix.html
 * http://cs.nyu.edu/courses/Fall12/CSCI-GA.1133-002/notes/InfixToPostfixExamples.pdf
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class Evaluator {

    public static void setTokensID(List expression) {
        int NUMBER = 1;
        for (Object tok : expression) {
            if (tok instanceof CoreToken) {
                ((CoreToken) tok).ID = NUMBER++;
            }
        }
    }

    /**
     * met?do est?tico va converte infixa em posfixa
     *
     * @return express?o posfixa
     * @param infix express?o infixa
     * @throws java.lang.Exception erro
     */
    public static List fromInfix(List expression) throws FlowchartException {
        //set ID Tokens
        setTokensID(expression);

        Stack stackOfOperators = new Stack();
        ArrayList posFix = new ArrayList();
        Iterator it = expression.iterator();
        while (it.hasNext()) {
            Object elem = it.next();
            if (elem instanceof String) {
                //---------------FLOWCHART ERROR-----------------------
                throw new FlowchartException(
                        ExpressionUtils.identation(expression),
                        "EVALUATE.error.unknowElement",
                        new String[]{
                            elem.toString(),
                            ExpressionUtils.head(expression, elem),
                            ExpressionUtils.between(expression, elem, elem),
                            ExpressionUtils.tail(expression, elem),}
                );
                //---------------FLOWCHART ERROR-----------------------
            }

            //----------------------------------------------------------------
            // VIRGULA
            //----------------------------------------------------------------
            if (Mark.isComma(elem)) {
                //retirar todos elementos ate ao parentesis a abrir
                do {
                    if (stackOfOperators.isEmpty()) {
                        //---------------FLOWCHART ERROR-----------------------
                        throw new FlowchartException(
                                ExpressionUtils.identation(expression),
                                "EVALUATE.error.parentesisNotOpen",
                                new String[]{
                                    elem.toString(),
                                    ExpressionUtils.head(expression, elem),
                                    ExpressionUtils.between(expression, elem, elem),
                                    ExpressionUtils.tail(expression, elem),}
                        );
                        //---------------FLOWCHART ERROR-----------------------
                    }
                    //verificar um elementos
                    Object topStack = stackOfOperators.peek();
                    //se for um parentesis a abrir sai
                    if (Mark.isOpenBracket(topStack)) {
                        break;
                    }
                    //introduzir na posFix
                    posFix.add(stackOfOperators.pop());
                } while (true);

                //--------------------------------------------------------------eliminar a virgula
                continue;
            } //----------------------------------------------------------------
            // PARENTESIS A ABRIR
            //----------------------------------------------------------------
            else if (Mark.isOpenBracket(elem)) {
                //retirar da pilha
                stackOfOperators.push(elem);
                continue;//continuar para o proximo elemento
            } //----------------------------------------------------
            //PARENTESIS A FECHAR
            //--------------------------------------------------
            else if (Mark.isCloseBracket(elem)) {
                //retirar todos elementos ate ao parentesis a abrir
                do {
                    if (stackOfOperators.isEmpty()) {
                        //---------------FLOWCHART ERROR-----------------------
                        throw new FlowchartException(
                                ExpressionUtils.identation(expression),
                                "EVALUATE.error.parentesisNotOpen",
                                new String[]{
                                    elem.toString(),
                                    ExpressionUtils.head(expression, elem),
                                    ExpressionUtils.between(expression, elem, elem),
                                    ExpressionUtils.tail(expression, elem),}
                        );
                        //---------------FLOWCHART ERROR-----------------------
                    }
                    //retirar um elementos
                    Object topStack = stackOfOperators.pop();
                    //se for um parentesis a abrir sai
                    if (Mark.isOpenBracket(topStack)) {

                        // ERROR parentesis nao combinam
                        if (!Mark.bracketsMatch(topStack, elem)) {
                            //---------------FLOWCHART ERROR-----------------------
                            throw new FlowchartException(
                                    ExpressionUtils.identation(expression),
                                    "EVALUATE.error.parentesisNotMatch",
                                    new String[]{
                                        topStack.toString(),
                                        elem.toString(),
                                        ExpressionUtils.head(expression, topStack),
                                        ExpressionUtils.between(expression, topStack, elem),
                                        ExpressionUtils.tail(expression, elem),}
                            );
                            //---------------FLOWCHART ERROR-----------------------
                        }
                        break;
                    }
                    //introduzir na posFix
                    posFix.add(topStack);
                } while (true);
                continue;//continuar para o proximo elemento
            } else if (CoreCalculator.isCalculatorElement(elem)) {
                CoreElement calc = (CoreElement) elem;
                //----------------------------------------------------------------
                //OPERADOR DE CALCULO
                //----------------------------------------------------------------
                int priority = calc.getPriority();
                //retirar todos operator com maior prioridade
                do {
                    //fim da pilha
                    if (stackOfOperators.empty()) {
                        break;
                    }
                    //se for um parentesis a abrir sai
                    if (Mark.isOpenBracket(stackOfOperators.peek())) {
                        break;
                    }
                    //elemento com maior prioridade sai
                    if (((CoreElement) stackOfOperators.peek()).getPriority() < priority) {
                        break;
                    }
                    //adiconar o topo da pilha
                    posFix.add(stackOfOperators.pop());
                } while (true);
                //adicionar o elemento a pilha
                stackOfOperators.push(elem);
                continue;//continuar para o proximo elemento
            }//abstractCalculus
            //----------------------------------------------------------------
            //VALORES
            //----------------------------------------------------------------
            //adicionar directamente o elemento a expressao
            posFix.add(elem);
        }
        //esvaziar a pilha
        while (!stackOfOperators.isEmpty()) {
            Object elem = stackOfOperators.pop();
            if (Mark.isOpenBracket(elem)) {
                //---------------FLOWCHART ERROR-----------------------
                throw new FlowchartException(
                        ExpressionUtils.identation(expression),
                        "EVALUATE.error.parentesisNotClose",
                        new String[]{
                            elem.toString(),
                            ExpressionUtils.head(expression, elem),
                            ExpressionUtils.between(expression, elem, elem),
                            ExpressionUtils.tail(expression, elem),}
                );
                //---------------FLOWCHART ERROR-----------------------
            }
            posFix.add(elem);
        }
        return posFix;
    }

    public static Fsymbol evaluate(List expression, Memory mem) throws FlowchartException {
        return evaluate(expression, true, mem);
    }

    //verifica se a expressao e executavel (não olha para o valor das variaveis mas apenas para os tipos
    public static Fsymbol verify(List expression, Memory mem) throws FlowchartException {
        return evaluate(expression, false, mem);
    }

    public static Fsymbol evaluate(List expression, boolean trueValues, Memory mem) throws FlowchartException {
//        System.out.println("EVALUATE : " + ExpressionUtils.identation(expression));
        //conversao para posfixa
        List posFix = fromInfix(expression);
//        System.out.println("POSFIX  : " + ExpressionUtils.DEBUG(posFix));
        //parametros dos elementos de calculo
        ArrayList<Fsymbol> parametros = new ArrayList<>();
        //stack para os valores
        Stack stack = new Stack();
        //percorrer a expressao posfixa
        Iterator it = posFix.iterator();
        while (it.hasNext()) {
            Object elem = it.next();
            //------------------------------------------
            // se for uma funçao definida pelo utilizador
            // usar o valor de retorno
            //--------------------------------------------
            if (elem instanceof FunctionCall) {
                Fsymbol rets = ((FunctionCall) elem).getResult();
                rets.ID = ((FunctionCall) elem).ID; // actualizar o ID
                stack.add(rets);
            } //------------------------------------------
            //se for um valor
            //------------------------------------------
            else if (elem instanceof Fsymbol) {
                //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //:::::::::: updated 15-09-2015 - get arrays from memory
                //::::::::::                      get vars from memory
                //::::::::::                      get constants from expression
                //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //convert to symbol
                Fsymbol symbol = (Fsymbol) elem;
                //get the symbol in memory if is defined
                Fsymbol memoryValue = mem != null ? (Fsymbol) mem.getByName(symbol.getName()) : null;
                if (memoryValue == null) { //--------------------------------------constants are not in memory                    
                    stack.add(symbol);
                } else {//----------------------------------- MEMORY --------------

                    if (memoryValue instanceof Farray) {//--------------ARRAY
                        //get the index from token
                        Farray indexes = (Farray) elem;
                        //get the indexed element of memory variable
                        if (trueValues) { // true values extract value
                            memoryValue = (Fsymbol) ((Farray) memoryValue).getElement(indexes.getIndexes(), mem);
                        } else { // fake values extract tamplate
                            memoryValue = ((Farray) memoryValue).getTemplateElement();
                        }
                    }
                    //clone value 
                    memoryValue = (Fsymbol) memoryValue.clone();
                    //set ID
                    memoryValue.ID = symbol.ID;
                    //ADD to stack
                    stack.add(memoryValue);
                }
                //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  
            }//---------------------------------------------
            //se for um calculo
            //------------------------------------------
            else if (CoreCalculator.isCalculatorElement(elem)) {
                CoreElement exe = (CoreElement) elem;
                //----------- parametros do operador ---------------------
                int nParams = exe.getNumberOfParameters();
                //comecar os parametros
                parametros.clear();
                while (nParams > 0) {
                    //necessito de um parametro e ele nao esta la
                    if (stack.isEmpty()) {
                        //---------------FLOWCHART ERROR-----------------------
                        throw new FlowchartException(
                                ExpressionUtils.identation(expression),
                                "EVALUATE.error.numberParameters",
                                new String[]{
                                    elem.toString(),
                                    ExpressionUtils.head(expression, elem),
                                    ExpressionUtils.between(expression, elem, elem),
                                    ExpressionUtils.tail(expression, elem),
                                    exe.getNumberOfParameters() + "",
                                    parametros.size() + ""
                                }
                        );
                        //---------------FLOWCHART ERROR-----------------------
                    }
                    //adicionar o parametro no inicio
                    parametros.add(0, (Fsymbol) stack.pop());
                    nParams--;
                }
                //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Fsymbol result;
                //:::::::::::::::::::::::: executar o calculo
                if (trueValues) {
                    result = exe.evaluate(parametros);
                } //::::::::::::::::::::::: simular o calculo
                else {
                    result = exe.returnType(parametros);
                }
                //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                stack.push(result);

            }//------------------------------------------
            //ERRO
            //------------------------------------------
            else {
                //---------------FLOWCHART ERROR-----------------------
                throw new FlowchartException(
                        ExpressionUtils.identation(expression),
                        "EVALUATE.error.unknow",
                        new String[]{
                            ExpressionUtils.identation(expression)
                        }
                );
                //---------------FLOWCHART ERROR-----------------------
            }
        }
        if (stack.isEmpty()) {
            //---------------FLOWCHART ERROR-----------------------
            throw new FlowchartException(
                    ExpressionUtils.identation(expression),
                    "EVALUATE.error.expressionError",
                    new String[]{
                        ExpressionUtils.identation(expression)
                    }
            );
        }
        //---------------FLOWCHART ERROR-----------------------
        Fsymbol result = (Fsymbol) stack.pop();
        //a pilha nao ficou vazia entao a expressao
        //nao esta correcta
        if (!stack.isEmpty()) {
            //---------------FLOWCHART ERROR-----------------------
            throw new FlowchartException(
                    ExpressionUtils.identation(expression),
                    "EVALUATE.error.expressionError",
                    new String[]{
                        ExpressionUtils.identation(expression)
                    }
            );
            //---------------FLOWCHART ERROR-----------------------
        }
        //devolver o resultado
        return result;
    }

    public static void main(String[] args) {

        Memory mem = new Memory("test Memory");
        String exp[] = {
            //            "2 + 3",
            //            "3+4*5/6",
            //            "(300+23)*(43-21)/(84+7)>5&&3+4*5/6!=-(4*7^(2^2))",
            //            "(4+8)*(6-5)/((3-2)*(2+2))",
            //            "abs( 2+ 3 * 5 ^2) - 3",
            "-(-3--4)"
        };
        for (String calc : exp) {
            try {
                Expression ex = new Expression(calc, mem, new Program());
                List infix = ex.getTokens();
                List posfix = fromInfix(infix);
                System.out.println("\n\nINFIX \t:" + ExpressionUtils.debug(infix));

                System.out.println("\nPOSFIX \t:" + ExpressionUtils.debug(posfix));
                System.out.println("\nRESULT \t:" + evaluate(ex.getTokens(), mem));
//                System.out.println("\nTYPE \t:" + verify(ex.get(0)).getTypeName());
            } catch (FlowchartException ex1) {
                ex1.show("Posfix");
            }

        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
