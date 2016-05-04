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

import core.CoreCalculator;
import core.CoreToken;
import core.FunctionCall;
import core.data.Fcharacter;
import core.data.Finteger;
import core.data.Flogic;
import core.data.Freal;
import core.data.Ftext;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import core.evaluate.CoreElement;
import core.parser.Mark;
import i18n.Fi18N;
import i18n.FkeyWord;
import flowchart.arrow.Arrow;
import flowchart.shape.Fshape;
import static flowchart.utils.ExpressionUtils.removeSpaces;
import java.util.List;

/**
 *
 * @author zulu
 */
public class Theme {

    public static String HTML_BEGIN = "<html>";
    public static String HTML_END = "</html>";
    public static String HTML_NEWLINE = "<br>";
    public static String HTML_LESSTHAN = "&lt";

    public static String COLOR_EXCEPTION = "<strong><font color=\"#FF0000\">";
    public static String COLOR_NORMAL = "</strong><font color=\"#000000\">";

    public static String COLOR_KEYWORD = "<font color=\"#0000FF\">";
    public static String COLOR_VAR = "<font color=\"#0000FF\">";
    public static String COLOR_OPERATOR = "<font color=\"#0000FF\">";
    public static String COLOR_FUNCTION = "<font color=\"#00FF00\">";

    public static String COLOR_INT = "<font color=\"#0000FF\">";
    public static String COLOR_REAL = "<font color=\"#FF0000\">";
    public static String COLOR_TEXT = "<font color=\"#FF00FF\">";
    public static String COLOR_CHAR = "<font color=\"#FF00FF\">";
    public static String COLOR_LOGIC = "<font color=\"#008000\">";

    public static String COLOR_PARENTESIS = "<font color=\"#FF00FF\">";

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::            S I N T A  X    H I G H L I G H T             :::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String expressionToHTML(List theList) {
        StringBuilder html = new StringBuilder(HTML_BEGIN);
        for (Object elem : theList) {
            if (elem instanceof CoreToken) {
                CoreToken token = (CoreToken) elem;
                if (CoreCalculator.isFunction(token)
                        || CoreCalculator.isUnary(token)) {
                    html.append(colorizeElement(token));
                } else {
                    html.append(colorizeElement(token) + " ");
                }
            } else {
                html.append(colorizeElement(elem) + " ");
            }
        }
        return html.toString() + HTML_END;
    }

    public static String colorizeElement(Object elem) {
        if (FkeyWord.isReservedWord(elem.toString())) {
            return COLOR_KEYWORD + elem.toString();
        }
        if (elem instanceof Fsymbol) {
            return symbolToColor((Fsymbol) elem);
        } else if (elem instanceof FunctionCall) {
            return COLOR_FUNCTION + ((FunctionCall) elem).getDescriptor();
        } else if (elem instanceof CoreElement) {
            return COLOR_OPERATOR + ((CoreElement) elem).getDescriptor();
        } else if (elem instanceof Mark) {
            return COLOR_PARENTESIS + ((Mark) elem).getDescriptor();
        }
        return COLOR_NORMAL + elem.toString();
    }

    public static String symbolToColor(Fsymbol var) {
        if (var instanceof Finteger) {
            return COLOR_INT + var.getDescriptor();
        } else if (var instanceof Freal) {
            return COLOR_REAL + var.getDescriptor();
        } else if (var instanceof Fcharacter) {
            return COLOR_CHAR + var.getDescriptor();
        } else if (var instanceof Flogic) {
            return COLOR_LOGIC + var.getDescriptor();
        } else if (var instanceof Ftext) {
            String txt = var.getDescriptor();
            if (txt.startsWith(Ftext.DELIMITATOR_TEXT + "")) {
                txt = "&quot;" + txt.substring(1, txt.length() - 2) + "&quot;";
            }
            return COLOR_TEXT + txt;
        }
        return var.getTextValue();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String valueToHtml(Fsymbol var) {
        if (var instanceof Finteger) {
            return HTML_BEGIN + COLOR_INT + var.getTextValue() + HTML_END;
        } else if (var instanceof Freal) {
            return HTML_BEGIN + COLOR_REAL + var.getTextValue() + HTML_END;
        } else if (var instanceof Fcharacter) {
            return HTML_BEGIN + COLOR_CHAR + var.getDefinitionValue() + HTML_END;
        } else if (var instanceof Flogic) {
            return HTML_BEGIN + COLOR_LOGIC + var.getTextValue() + HTML_END;
        } else if (var instanceof Ftext) {
            return HTML_BEGIN + COLOR_TEXT + var.getDefinitionValue() + HTML_END;
        }
        return var.getTextValue();
    }

    public static String replaceParametersHTML(String title, String key, String txt, String[] params) {
        if (title.isEmpty()) {
            title = Fi18N.get("STRING.error");
        }
        txt = replaceHTMLspecialChars(txt);

        for (int i = 0; i < params.length; i++) {
            txt = txt.replace("%" + (i + 1), COLOR_EXCEPTION + replaceHTMLspecialChars(params[i]) + COLOR_NORMAL);
        }
        //remome parameters not used
        for (int i = params.length; i < 100; i++) {
            if (txt.contains("%" + (i + 1))) {
                txt = txt.replace("%" + (i + 1), "");
            } else {
                break;
            }

        }

        return HTML_BEGIN
                + "<P style=\"BACKGROUND-COLOR: #A9D0F5\" align=center> "
                + "<strong><br>" + key + "</strong> <br><br></P>"
                + "<strong><br>" + title + "</strong> <br><br></P>"
                + txt + HTML_END;
    }

    public static String replaceParameters(String title, String key, String txt, String[] params) {
        if (title.isEmpty()) {
            title = Fi18N.get("STRING.error");
        }
        for (int i = 0; i < params.length; i++) {
            txt = txt.replace("%" + (i + 1), params[i]);
        }
        //remome parameters not used
        for (int i = params.length; i < 100; i++) {
            if (txt.contains("%" + (i + 1))) {
                txt = txt.replace("%" + (i + 1), "");
            } else {
                break;
            }

        }

        return txt;
    }

    public static String exceptionHtml(FlowchartException ex) {
        String key = ex.getKey();
        String message = ex.getMessage();
        String[] params = ex.getParams();
        message = replaceHTMLspecialChars(message);
        for (int i = 0; i < params.length; i++) {
            message = message.replace("%" + (i + 1), COLOR_EXCEPTION + replaceHTMLspecialChars(params[i]) + COLOR_NORMAL);
        }
        //remame parameters not used
        for (int i = params.length; i < 100; i++) {
            if (message.contains("%" + (i + 1))) {
                message = message.replace("%" + (i + 1), "");
            } else {
                break;
            }
        }
        return HTML_BEGIN
                + "<P style=\"BACKGROUND-COLOR: #A9D0F5\" align=center> "
                + "<strong><br>" + key + "</strong> <br><br></P>"
                + message + HTML_END;
    }

    /**
     *
     * @param message message with %1 %2 . . .
     * @param params parameters to replace %1 %2 .. .
     * @return
     */
    public static String messageHtml(String message, String[] params) {
        message = replaceHTMLspecialChars(message);
        for (int i = 0; params != null && i < params.length; i++) {
            message = message.replace("%" + (i + 1), COLOR_EXCEPTION + replaceHTMLspecialChars(params[i]) + COLOR_NORMAL);
        }
        return HTML_BEGIN
                + "<P style=\"BACKGROUND-COLOR: #A9D0F5\" align=center> "
                + message + HTML_END;
    }

    public static String replaceParameters(String message, String[] params) {
        message = replaceHTMLspecialChars(message);
        for (int i = 0; i < params.length; i++) {
            message = message.replace("%" + (i + 1), COLOR_EXCEPTION + (params[i]) + COLOR_NORMAL);
        }
        //remame parameters not used
        for (int i = params.length; i < 100; i++) {
            if (message.contains("%" + (i + 1))) {
                message = message.replace("%" + (i + 1), "");
            } else {
                break;
            }
        }
        return message;
    }

    public static String htmlParameters(String key, String message, String[] params) {

        message = replaceParameters(message, params);

        return HTML_BEGIN
                + "<P style=\"BACKGROUND-COLOR: #A9D0F5\" align=center> "
                + "<strong><br>" + key + "</strong> <br><br></P>"
                + message + HTML_END;
    }

    public static String replaceHTMLspecialChars(String txt) {
        if (txt == null) {
            return "";
        }
        txt = txt.replaceAll("<", HTML_LESSTHAN);
        return txt.replaceAll("\n", HTML_NEWLINE);
    }

    public static String helpMenuExpression(String def, String help) {
        help = replaceHTMLspecialChars(help);
        return HTML_BEGIN
                // +"<font size=\"" + (ShapeMenuDialog.getFontSize()/2) + "\">"
                + "<P style=\"BACKGROUND-COLOR: #A9D0F5\" align=center> "
                + "<strong>" + def + "</strong></P>"
                // +"<\font>"
                + help + HTML_END;
    }

    public static String toHtml(String txt) {
        txt = replaceHTMLspecialChars(txt);
        return HTML_BEGIN
                //                + "<font face=\"monospaced\">"
                + txt
                //                + "</font>"
                + HTML_END;
    }

    public static String shapeText(Fshape shape) {
        String txtShape = shape.getInstruction();
        String str = replaceHTMLspecialChars(txtShape);
        StringBuilder txt = new StringBuilder(HTML_BEGIN);
        txt.append("<p align=center>");
        txt.append(COLOR_NORMAL + str);
        txt.append("</p>\n" + HTML_END);
        return txt.toString();
    }

    public static String shapeComments(Fshape shape) {
        if (shape instanceof Arrow) {
            return null;
        }
        String txt = HTML_BEGIN
                + "<P style=\"BACKGROUND-COLOR: #96F4C8\" align=center> "
                + "<strong>" + shape.type + "</strong>"
                + HTML_NEWLINE
                + "<P style=\"BACKGROUND-COLOR: #EEF19F\" align=center> "
                + shape.getInstruction() + "</P>";
        //+ HTML_NEWLINE;
//        if (!shape.getComments().isEmpty()) {
            txt += "<P style=\"BACKGROUND-COLOR: #A9D0F5\" align=center> "
                    + "<strong>" + Fi18N.get("STRING.comments") + "</strong>"
                    + HTML_NEWLINE + "</P>"
                    + "<P style=\"BACKGROUND-COLOR: #EEF19F\" align=left>"
                    + replaceHTMLspecialChars(shape.comments);
//        }
        return txt + HTML_END;
    }

    public static String shapeDebugComments(Fshape shape) {
        return HTML_BEGIN
                + "<P style=\"BACKGROUND-COLOR: #009900\" align=center> "
                + "<strong>" + "Type : " + shape.type + "</strong>"
                + HTML_NEWLINE
                + "<strong>" + "Editable " + shape.isEditable + "</strong>"
                + HTML_NEWLINE
                + "<P style=\"BACKGROUND-COLOR: #A9D0F5\" align=center> "
                + "<strong>" + "DEBUG" + "</strong>"
                + HTML_NEWLINE + "</P>"
                + "<P style=\"BACKGROUND-COLOR: #BCF5A9\" align=left>"
                + replaceHTMLspecialChars(shape.toDebugTooltip())
                + HTML_END;
    }

    public static String runtimeTooltip(String txt) {
        return HTML_BEGIN
                + "<P style=\"BACKGROUND-COLOR: #009900\" align=center> "
                + "<strong>" + "RUNTIME" + "</strong>"
                + HTML_NEWLINE
                + "<P style=\"BACKGROUND-COLOR: #A9D0F5\" align=center> "
                + "<strong>" + "DEBUG" + "</strong>"
                + HTML_NEWLINE + "</P>"
                + "<P style=\"BACKGROUND-COLOR: #BCF5A9\" align=left>"
                + replaceHTMLspecialChars(txt)
                + HTML_END;
    }

}
