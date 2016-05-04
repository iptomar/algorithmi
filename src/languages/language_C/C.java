///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2015                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****     This software was build with the purpose of investigate and    ****/
///****     learning.                                                      ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languages.language_C;

import core.CoreToken;
import core.data.Fsymbol;
import i18n.FkeyWord;
import flowchart.define.Define;
import flowchart.execute.Execute;
import flowchart.shape.Fshape;
import flowchart.terminator.Begin;
import flowchart.terminator.End;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author zulu
 */
public class C {

    static String END_LINE = ";";
    static HashMap<String, String> lang = new HashMap<String, String>();

    static {
        lang.put(FkeyWord.get("TYPE.integer"), "int");
        lang.put(FkeyWord.get("TYPE.real"), "float");
        lang.put(FkeyWord.get("TYPE.character"), "char");
        lang.put(FkeyWord.get("TYPE.string"), "char *");
        lang.put(FkeyWord.get("TYPE.boolean"), "int");

        lang.put(FkeyWord.get("CONSTANT.true"), "1");
        lang.put(FkeyWord.get("CONSTANT.false"), "0");
        lang.put(FkeyWord.get("CONSTANT.PI"), "3.14159");
        lang.put(FkeyWord.get("CONSTANT.E"), "2.71828");

        lang.put(FkeyWord.get("SEPARATOR.comma"), ",");
        lang.put(FkeyWord.get("BRACKET.round.open"), "(");
        lang.put(FkeyWord.get("BRACKET.round.close"), ")");
        lang.put(FkeyWord.get("BRACKET.square.open"), "[");
        lang.put(FkeyWord.get("BRACKET.square.close"), "]");
        lang.put(FkeyWord.get("BRACKET.brace.open"), "{");
        lang.put(FkeyWord.get("BRACKET.brace.close"), "}");

        lang.put(FkeyWord.get("OPERATOR.set"), "=");

        lang.put(FkeyWord.get("OPERATOR.negative"), "-");

        lang.put(FkeyWord.get("OPERATOR.sum"), "+");
        lang.put(FkeyWord.get("OPERATOR.sub"), "-");
        lang.put(FkeyWord.get("OPERATOR.mult"), "*");
        lang.put(FkeyWord.get("OPERATOR.div"), "/");
        lang.put(FkeyWord.get("OPERATOR.mod"), "%");
        lang.put(FkeyWord.get("OPERATOR.power"), "pow");

        lang.put(FkeyWord.get("OPERATOR.equal"), "=");
        lang.put(FkeyWord.get("OPERATOR.different"), "!=");
        lang.put(FkeyWord.get("OPERATOR.greater"), ">");
        lang.put(FkeyWord.get("OPERATOR.greaterOrEqual"), ">=");
        lang.put(FkeyWord.get("OPERATOR.less"), "<");
        lang.put(FkeyWord.get("OPERATOR.lessOrEqual"), ">=");

        lang.put(FkeyWord.get("OPERATOR.not"), "!");
        lang.put(FkeyWord.get("OPERATOR.and"), "&&");
        lang.put(FkeyWord.get("OPERATOR.or"), "||");

        lang.put(FkeyWord.get("FUNCTION.sqrt"), "sqrt");
        lang.put(FkeyWord.get("FUNCTION.abs"), "abs");
        lang.put(FkeyWord.get("FUNCTION.random"), "random");
        lang.put(FkeyWord.get("FUNCTION.pow"), "pow");
    }

    public static String compile(ArrayList<Fshape> graph) {
        StringBuilder prog = new StringBuilder(""
                + "\n#include<stdio.h>"
                + "\n#include<math.h>"
                + "\n");

        Fshape shape = graph.get(0);
        while (shape != null) {
            if (!shape.comments.isEmpty()) {
                prog.append("/*" + shape.getComments() + "*/\n");
            }
            if (shape instanceof Begin) {
                prog.append(toBegin(shape));
            } else if (shape instanceof End) {
                prog.append(toEnd(shape));
            } else if (shape instanceof Define) {
                prog.append(toDefine((Define) shape) + END_LINE);
            } else if (shape instanceof Execute) {
                prog.append(toExecute((Execute) shape) + END_LINE);
            }
            prog.append("\n");
            shape = shape.next;
        }

        return prog.toString();
    }

    public static String toBegin(Fshape shape) {
        return "void main(void){";
    }

    public static String toEnd(Fshape shape) {
        return "}";
    }

    public static String toDefine(Define shape) {
        Fsymbol var = shape.getVarSymbol();
        StringBuilder txt = new StringBuilder();
        txt.append(lang.get(var.getTypeName()) + " ");
        txt.append(var.getName() + " ");
        txt.append(lang.get(FkeyWord.getSetOperator()) + " ");
//        txt.append(expression(shape.expression.getTokens()));
        return txt.toString();
    }

    private static String toExecute(Execute shape) {
        Fsymbol var = shape.var;
        StringBuilder txt = new StringBuilder();
        if (var != null) {
            txt.append(lang.get(var.getTypeName()) + " ");
            txt.append(lang.get(FkeyWord.getSetOperator()) + " ");
        }
        txt.append(expression(shape.expressionToCalculate.getTokens()));
        return txt.toString();
    }

    private static String expression(List exp) {
        StringBuilder txt = new StringBuilder();
        for (int i = 0; i < exp.size(); i++) {
            Object elem = exp.get(i);
            String c_txt = lang.get(elem);
            if (c_txt != null) {
                txt.append(c_txt);
            } else if (elem instanceof CoreToken) {
                txt.append(((CoreToken) elem).getDescriptor());
            }
            txt.append(" ");
        }
        return txt.toString();
    }

}
