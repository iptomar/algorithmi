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
package core.parser.tokenizer;

import core.data.exception.FlowchartException;
import core.parser.Mark;
import flowchart.utils.ExpressionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created on 7/set/2015
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class BreakMarks {

    public static List<Object> execute(List<Object> exp) throws FlowchartException {

        List<Object> result = new ArrayList<>();
        for (Object token : exp) {
            if (token instanceof String) {
                //------------------------------------------------------------------------------------------
                String txt = token.toString().trim();
                StringBuilder word = new StringBuilder();
                int index = 0;

                while (index < txt.length()) {
                    //verify if is one mark
                    if (Mark.CHAR_MARKS.contains(txt.charAt(index) + "")) {
                        //append last word
                        String elem = word.toString().trim();
                        if (!elem.isEmpty()) {
                            result.add(elem);
                        }
                        //add mark
                        result.add(new Mark(txt.charAt(index)));
                        //begin new word
                        word = new StringBuilder();
                        index++;
                    } else {
                        word.append(txt.charAt(index++));
                    }
                }
                //append last word
                String elem = word.toString().trim();
                if (!elem.isEmpty()) {
                    result.add(elem);
                }
                //------------------------------------------------------------------------------------------

            } else {
                result.add(token);
            }
        }
        //verify parentesis
        BreakMarks.checkParentesis(result);
        return result;
    }
    
    public static boolean checkParentesis(String txt)throws FlowchartException{
            List<Object> lst = new ArrayList<>();
            lst.add(txt);
            lst = execute(lst);
            return BreakMarks.checkParentesis(lst);        
    }

    public static boolean checkParentesis(List<Object> expression) throws FlowchartException {
        if (expression.isEmpty()) {
            return true;
        }

        Stack<Mark> stack = new Stack();
        for (int i = 0; i < expression.size(); i++) {
            //not bracket
            if (!(expression.get(i) instanceof Mark)) {
                continue;
            }
            //get bracket
            Mark current = (Mark) expression.get(i);
            //if is open add to stack
            if (current.isOpenBracket()) {
                stack.push(current);
            }
            //if close verify
            if (current.isCloseBracket()) {
                //erro bracket not open
                if (stack.isEmpty()) {
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    throw new FlowchartException(
                            ExpressionUtils.toString(expression),
                            "EVALUATE.error.parentesisNotOpen",
                            new String[]{
                                current.toString(),
                                ExpressionUtils.head(expression, current),
                                ExpressionUtils.between(expression, current, current),
                                ExpressionUtils.tail(expression, current),}
                    );
                    //---------------FLOWCHART ERROR-----------------------
                }

                Mark last = stack.peek();
                if (last.match(current)) {
                    stack.pop();
                } else {
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    throw new FlowchartException(
                            ExpressionUtils.toString(expression),
                            "EVALUATE.error.parentesisNotMatch",
                            new String[]{
                                last.toString(),
                                current.toString(),
                                ExpressionUtils.head(expression, last),
                                ExpressionUtils.between(expression, last, current),
                                ExpressionUtils.tail(expression, current),}
                    );
                    //---------------FLOWCHART ERROR-----------------------
                }
            }

        }
        if (!stack.isEmpty()) {
            Mark elem = stack.pop();
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException(
                    ExpressionUtils.toString(expression),
                    "EVALUATE.error.parentesisNotClose",
                    new String[]{
                        elem.toString(),
                        ExpressionUtils.head(expression, elem),
                            ExpressionUtils.between(expression, elem, elem),
                            ExpressionUtils.tail(expression, elem)}
            );
            //---------------FLOWCHART ERROR-----------------------
        } else {
            return true;
        }
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071200L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
