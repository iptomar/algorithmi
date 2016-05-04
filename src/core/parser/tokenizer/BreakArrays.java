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

import core.Memory;
import core.data.Finteger;
import core.data.Fsymbol;
import core.data.Ftext;
import core.data.complexData.Farray;

import core.data.exception.FlowchartException;
import core.parser.Expression;
import core.parser.Mark;
import flowchart.algorithm.Program;
import flowchart.utils.ExpressionUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10/set/2015, 15:03:57
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class BreakArrays {

    public static List<Object> execute(List<Object> exp, Memory mem, Program prog) throws FlowchartException {
        List<Object> result = new ArrayList<>();
        // for all tokens
        for (int indexInExpression = 0; indexInExpression < exp.size(); indexInExpression++) {
            // System.out.println(indexInExpression + " \tEXPRESSION " + ExpressionUtils.toString(exp));
            //begin of indexes
            if (exp.get(indexInExpression) instanceof Mark && ((Mark) exp.get(indexInExpression)).isSquareOpenBracket()) {

                if (indexInExpression == 0 || exp.isEmpty()) {
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    throw new FlowchartException(
                            ExpressionUtils.toString(exp),
                            "EXECUTE.array.indexEmpty",
                            new String[]{}
                    );
                }
                if (!(exp.get(indexInExpression - 1) instanceof String)) {
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    throw new FlowchartException(
                            ExpressionUtils.toString(exp),
                            "TYPE.array.arrayExpected",
                            new String[]{
                                ExpressionUtils.head(exp, exp.get(indexInExpression)),
                                ExpressionUtils.between(exp, exp.get(indexInExpression), exp.get(indexInExpression)),
                                ExpressionUtils.tail(exp, exp.get(indexInExpression))
                            }
                    );
                    //---------------FLOWCHART ERROR-----------------------   
                }

                //-------------------------------------------------------------------------
                //add Array token
                //------------------------------------------------------------------------
                String previous = exp.get(indexInExpression - 1).toString();
                //remove name of array from result
                result.remove(result.size() - 1);
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 1 - extract name from the previous symbol
                Fsymbol memSymb = getSymbol(previous, mem);
                if (memSymb == null) {
                    throw new FlowchartException("EXECUTE.array.notArray", previous);
                }
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 1.1 - TEXT Index
                //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                if (memSymb instanceof Ftext) {
                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::: TEXT 1 - get Text
                    Ftext var = (Ftext) memSymb.clone();
                    //append remain of definition
                    String remainDef = previous.substring(0, previous.length() - var.getName().length());
                    if (!remainDef.isEmpty()) {
                        result.add(remainDef);
                    }

                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::: TEXT  2 - extract list of indexes
                    List<Object> index = extractOneIndexeParameter(exp, indexInExpression);
                    var.indexExpression = new Expression(index, mem, prog);
                    Fsymbol rets = var.indexExpression.getReturnType();
                    if (!(rets instanceof Finteger)) {
                        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                        //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
                        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                        throw new FlowchartException(
                                ExpressionUtils.toString(exp),
                                "TYPE.array.intExpected",
                                new String[]{
                                    ExpressionUtils.toString(index),
                                    rets.getTypeName()
                                }
                        );
                        //---------------FLOWCHART ERROR-----------------------
                    }
                    //::::::::::::::::::::::::::::::::::::::::::::::::::::::TEXT 5 - add array to expression
                    result.add(var);
                    //::::::::::::::::::::::::::::::::::::::::::::::::::::::TEXT 6 - set descriptot of token 
                    var.setDescriptor(var.getFullName());
                    //because de definition of the array consumes the current position
                    indexInExpression--;

                } //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
                //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  ARRAY Index
                //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                else if (memSymb instanceof Farray) {
                    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  ARRAY 1 - get Array
                    Farray var = (Farray) memSymb.clone();
                    //                System.out.println(var.getFullInfo());
                    if (var != null) {
                        //append remain of definition
                        String remainDef = previous.substring(0, previous.length() - var.getName().length());
                        if (!remainDef.isEmpty()) {
                            result.add(remainDef);
                        }

                        //:::::::::::::::::::::::::::::::::::::::::::::::::::::  ARRAY 2 - - extract list of indexes
                        List< List<Object>> indexes;
                        indexes = extractIndexesParameter(exp, var, indexInExpression);

                        //:::::::::::::::::::::::::::::::::::::::::::::::::::::: ARRAY 3 - build expression of indexes
                        List<Expression> expressions = new ArrayList<>();
                        for (List<Object> index : indexes) {
                            Expression newIndex = new Expression(index, mem, prog);
                            Fsymbol rets = newIndex.getReturnType();
                            if (!(rets instanceof Finteger)) {
                                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                                //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
                                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                                throw new FlowchartException(
                                        ExpressionUtils.toString(exp),
                                        "TYPE.array.intExpected",
                                        new String[]{
                                            ExpressionUtils.toString(index),
                                            rets.getTypeName()
                                        }
                                );
                                //---------------FLOWCHART ERROR-----------------------
                            }

                            expressions.add(newIndex);
                        }
                        //::::::::::::::::::::::::::::::::::::::::::::::::::::::ARRAY  4 - set indexes to var
                        var.setIndexExpressions(expressions);
                        //                    System.out.println(var.getFullInfo());
                        //::::::::::::::::::::::::::::::::::::::::::::::::::::::ARRAY 5 - add array to expression
                        result.add(var);
                        //::::::::::::::::::::::::::::::::::::::::::::::::::::::ARRAY 6 - set descriptot of token 
                        var.setDescriptor(var.getFullName());
                        //because de definition of the array consumes the current position
                        indexInExpression--;

                    } // var != null
                    else {
                        // a name of the array was expected
                        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                        //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
                        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                        throw new FlowchartException(
                                ExpressionUtils.toString(exp),
                                "TYPE.array.arrayExpected",
                                new String[]{
                                    ExpressionUtils.head(exp, exp.get(indexInExpression)),
                                    ExpressionUtils.between(exp, exp.get(indexInExpression), exp.get(indexInExpression)),
                                    ExpressionUtils.tail(exp, exp.get(indexInExpression))}
                        );
                        //---------------FLOWCHART ERROR-----------------------
                    }
                }//-------------------------------------------------------------ARRAY
            } else {
                //normal token
                result.add(exp.get(indexInExpression));
            }

        }
        return result;

    }

    private static Fsymbol getSymbol(String str, Memory mem) {
        int i = str.length() - 1;
        while (i >= 0
                && (Character.isDigit(str.charAt(i)) // 0..9
                || Character.isUpperCase(str.charAt(i)) // a..z A..Z
                || Character.isLowerCase(str.charAt(i)))) {
            i--;
        }
        Fsymbol var = mem.getByName(str.substring(i + 1));
        if (var instanceof Farray || var instanceof Ftext) {
            return var;
        }
        return null;
    }

    //extract indexes from expression
    private static List< List<Object>> extractIndexesParameter(List<Object> expression, Farray var, int start) throws FlowchartException {
        List< List<Object>> indexes = new ArrayList<>();
        List<Object> current = null;
        int openBracks = 0;

        while (start < expression.size()) {
//            System.out.println(ExpressionUtils.debug(expression));

            //get first element
            Object elem = expression.get(start);
            expression.remove(start);
            if (!(elem instanceof Mark)) {
                if (current == null) {
                    //push back element
                    expression.add(start, elem);
                    break; // end of indexes
                }
                if (openBracks == 0) { // end of array
                    return indexes;
                }
                current.add(elem);
                continue;
            }// not MARK

            Mark mark = (Mark) elem;
            if (mark.isSquareOpenBracket()) { // [
                if (openBracks > 0) { //inside index
                    current.add(elem);
                } else { // begin of index
                    current = new ArrayList<>();
                }
                openBracks++;
            } else if (mark.isSquareCloseBracket()) { // ]
                openBracks--;
                if (openBracks > 0) {
                    current.add(elem);
                } else {
                    //remove  ]
                    indexes.add(current);
                    current = null;
                }
            } else {
                if (openBracks == 0) {//push back element
                    expression.add(start, elem);
                    break;
                } //                if (current == null) {
                //                    // a name of the array was expected
                //                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //                    //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
                //                    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //                    throw new FlowchartException(
                //                            ExpressionUtils.toString(expression),
                //                            "TYPE.array.arrayExpected",
                //                            new String[]{
                //                                ExpressionUtils.head(expression, elem),
                //                                ExpressionUtils.between(expression, elem, elem),
                //                                ExpressionUtils.tail(expression, elem)}
                //                    );
                //                    //---------------FLOWCHART ERROR-----------------------
                current.add(elem);  //other mark
            }
        }

        return indexes;
    }

    //extract indexes from expression
    private static List<Object> extractOneIndexeParameter(List<Object> expression, int start) throws FlowchartException {
        List<Object> index = new ArrayList<>();
        int openBracks = 0;

        while (start < expression.size()) {
//            System.out.println(ExpressionUtils.debug(expression));

            //get first element
            Object elem = expression.get(start);
            expression.remove(start);
            if (!(elem instanceof Mark)) {
                index.add(elem);
                continue;
            }// not MARK

            Mark mark = (Mark) elem;
            if (mark.isSquareOpenBracket()) { // [
                if (openBracks > 0) {
                    index.add(elem); // indexes in indexes
                }
                openBracks++;
            } else if (mark.isSquareCloseBracket()) { // ]
                if (openBracks > 1) {// indexes in indexes
                    index.add(elem);
                    openBracks--;
                } else {
                    break;
                }
            } else {
                index.add(elem);  //other mark
            }
        }
        if (start < expression.size() && expression.get(start) instanceof Mark && ((Mark) expression.get(start)).isSquareOpenBracket()) {
            throw new FlowchartException("TYPE.text.invalidIndexDimensions");
        }

        return index;
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509101503L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
