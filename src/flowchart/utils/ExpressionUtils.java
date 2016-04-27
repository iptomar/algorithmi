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
package flowchart.utils;

import core.CoreToken;
import core.CoreCalculator;
import core.Memory;
import core.data.Finteger;
import core.data.Freal;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import core.evaluate.CoreElement;
import core.parser.Expression;
import flowchart.algorithm.Program;
import i18n.FkeyWord;
import i18n.FkeywordToken;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zulu
 */
public class ExpressionUtils {

    private static String toString(Object elem) {
        if (elem instanceof CoreToken) {
            return ((CoreToken) elem).getDescriptor();
        } else {
            return elem.toString().trim();
        }
    }

    public static String head(List theList, Object o) {
        StringBuilder txt = new StringBuilder();
        int last = theList.indexOf(o);
        for (int index = 0; index < last; index++) {
            txt.append(toString(theList.get(index)) + " ");
        }
        return txt.toString().trim();

    }

    public static String tail(List theList, Object o) {
        StringBuilder txt = new StringBuilder();
        for (int index = theList.indexOf(o) + 1; index < theList.size(); index++) {
            txt.append(toString(theList.get(index)) + " ");
        }
        return txt.toString().trim();
    }

    public static String between(List theList, Object from, Object to) {
        StringBuilder txt = new StringBuilder();
        int first = theList.indexOf(from);
        int last = theList.indexOf(to);
        for (int index = first; index <= last; index++) {
            txt.append(toString(theList.get(index)) + " ");
        }
        return txt.toString().trim();
    }

    public static String removeSpaces(String str) {
        str = str.trim();
        str = str.replaceAll(" \\)", "\\)");
        str = str.replaceAll("\\( ", "\\(");
        return str;
    }

    public static String identation(List theList) {
        StringBuilder txt = new StringBuilder();
        for (Object elem : theList) {
            if (elem instanceof CoreToken) {
                CoreToken token = (CoreToken) elem;
                if (CoreCalculator.isFunction(token)
                        || CoreCalculator.isUnary(token)) {
                    txt.append(token.getDescriptor());
                } else {
                    txt.append(token.getDescriptor() + " ");
                }
            } else {
                txt.append(elem.toString() + " ");
            }
        }
        return removeSpaces(txt.toString());
    }

    public static String DEBUG(List theList) {
        StringBuilder txt = new StringBuilder();
        for (Object elem : theList) {
            if (elem instanceof CoreToken) {
                CoreToken token = (CoreToken) elem;
                txt.append("< " + token + " ID " + token.ID + " >");
            } else {
                txt.append("< " + elem + " ERROR >");
            }
        }
        return removeSpaces(txt.toString());
    }

    public static String withValues(List theList) {
        StringBuilder txt = new StringBuilder();
        for (Object elem : theList) {
            if (CoreCalculator.isFunction(elem)
                    || CoreCalculator.isUnary(elem)) {
                txt.append(elem.toString().trim());
            } else {
                txt.append(elem.toString().trim() + " ");
            }
        }
        return removeSpaces(txt.toString());
    }

    public static String toString(List theList) {
        StringBuilder txt = new StringBuilder();
        for (Object elem : theList) {
            txt.append(elem.toString().trim() + " ");
        }
        return removeSpaces(txt.toString());
    }

    public static String debug(List theList) {
        StringBuilder txt = new StringBuilder();
        for (Object elem : theList) {
            if (elem instanceof CoreToken) {
                txt.append("    <" + ((CoreToken) elem).ID + ">");
            }
            txt.append("[" + elem.getClass().getSimpleName() + "]");
            if (CoreCalculator.isFunction(elem)) {
                txt.append(elem.toString().trim());
            } else {
                txt.append(elem.toString().trim() + " ");
            }

        }
        return txt.toString().trim();
    }

    /**
     * Convert an expression to tokens
     *
     * @param ex expression
     * @return string of tokens
     */
    public static String getExpressionTokens(Expression ex) {
        StringBuilder txt = new StringBuilder();
        if (ex != null) {
            for (Object tok : ex.getTokens()) {
                if (tok instanceof Fsymbol //--------------------------------------- constants
                        && Memory.constants.isDefined(((Fsymbol) tok).getName())) {
                    txt.append(FkeywordToken.getTokenOfWord(((Fsymbol) tok).getName()) + " ");
                } else if (tok instanceof CoreToken) { //--------------------------- coreTokens
                    txt.append(((CoreToken) tok).getTokenID() + " ");
                } else {
                    txt.append(tok.toString() + " ");
                }
            }
        }
        return txt.toString().trim();
    }

    /**
     * convert a string of tokens into an expression
     *
     * @param expression string of tokens
     * @param mem memory
     * @param prog program
     * @return
     * @throws FlowchartException
     */
    public static Expression buidTokenExpression(String expression, Memory mem, Program prog) throws FlowchartException {
        return new Expression(FkeywordToken.translateTokensToWords(expression), mem, prog);
    }

   
}
