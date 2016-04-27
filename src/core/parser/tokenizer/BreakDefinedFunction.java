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
package core.parser.tokenizer;

import core.FunctionCall;
import core.Memory;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import core.parser.Mark;
import flowchart.algorithm.FunctionGraph;
import flowchart.algorithm.Program;
import flowchart.utils.ExpressionUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10/set/2015, 15:03:57
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class BreakDefinedFunction {

    public static List<Object> execute(List<Object> exp, Memory mem, Program prog) throws FlowchartException {
        List<Object> result = new ArrayList<>();
        // for all tokens starting in the second
        for (int indexInExpression = 0; indexInExpression < exp.size(); indexInExpression++) {
            //begin of indexes
            if (indexInExpression>0 && exp.get(indexInExpression) instanceof Mark && ((Mark) exp.get(indexInExpression)).isRoundOpenBracket()) {
                if (!(exp.get(indexInExpression - 1) instanceof String)) {
                    // defined symbol - not a function
                    result.add(exp.get(indexInExpression));
                    continue;
                } else {
                    //-------------------------------------------------------------------------
                    //add Function call token
                    //------------------------------------------------------------------------
                    String previous = exp.get(indexInExpression - 1).toString();
                    //remove name of function from result
                    Object old = result.remove(result.size() - 1);
                    //1 - extract name from the previous symbol
                    FunctionGraph func = getFunctionName(previous, prog);
                    if (func == null) {
                         result.add(old); // add the removed element
                        // defined symbol - not a function
                        result.add(exp.get(indexInExpression));
                        continue;
                    } else {                        
                        //1 - make object to call functions
                        FunctionCall callFunc = new FunctionCall(func);                        
                        //append remain of definition
                        String remainDef = previous.substring(0, previous.length() - func.getFunctionName().length());
                        if (!remainDef.isEmpty()) {
                            result.add(remainDef);
                        }
                        // 2 - extract list of parameters
                        List< List<Object>> definedParams = extractParams(exp, indexInExpression+1); 
                        // 3 - build expression of indexes
                        List<Expression> expressions = new ArrayList<>();
                        for (List<Object> index : definedParams) {
                            expressions.add(new Expression(index, mem, prog));  //build expressions                 
                        }
                        // 4 - set indexes to var
                        callFunc.setParameters(expressions);;
                        //5 - add function to expression
                        result.add(callFunc);
                        //6 - set descriptot of token 
                        callFunc.setDescriptor(callFunc.getFullName());
                        //because de definition of the array consumes the current position
//                        indexInExpression++;
                    }//func not null
                }//not a defined token
            }//not open bracket
            else {
                result.add(exp.get(indexInExpression));
            }
        }//all tokens
        return result;
    }
    
    private static FunctionGraph getFunctionName(String str,  Program prog) {
        int i = str.length() - 1;
        while (i >= 0
                && (Character.isDigit(str.charAt(i)) // 0..9
                || Character.isUpperCase(str.charAt(i)) // a..z A..Z
                || Character.isLowerCase(str.charAt(i)))) {
            i--;
        }
        FunctionGraph func = prog.getFunctionByName(str.substring(i + 1));
        if (func != null ) {
            return func;
        }
        return null;
    }

    //extract indexes from expression
    private static List< List<Object>> extractParams(List<Object> expression, int start) throws FlowchartException {

        List< List<Object>> parameters = new ArrayList<>();
        List<Object> current = new ArrayList<>();
        int openBracks = 1;

        while (expression.size() > start) {

            //get first element
            Object elem = expression.remove(start);
            if (elem instanceof Mark) {
                Mark mark = (Mark) elem;
                if (mark.isComma() && openBracks == 1) {  //-------------------- ,
                    parameters.add(current);
                    current = new ArrayList<>();
                    continue;
                } else if (mark.isRoundOpenBracket()) {//------------------------ (
                    openBracks++;
                } else if (mark.isRoundCloseBracket()) { //---------------------- )
                    if (openBracks == 1) {
                        break;
                    } else {
                        openBracks--;
                    }
                }
            }// is Mark
            current.add(elem);
        }// all tokens
        if (!current.isEmpty()) {
            parameters.add(current);
        }
        return parameters;
    }
    
    
     
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509101503L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////


}
