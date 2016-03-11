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
//::                                                               (c)2015   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package zzTests.styled;

import core.CoreCalculator;
import core.FunctionCall;
import core.Memory;
import core.data.Fcharacter;
import core.data.Finteger;
import core.data.Flogic;
import core.data.Freal;
import core.data.Fsymbol;
import core.data.Ftext;
import core.data.exception.FlowchartException;
import core.evaluate.CoreElement;
import core.evaluate.aritmetic.Negative;
import core.parser.Mark;
import core.parser.tokenizer.BreakArrays;
import core.parser.tokenizer.BreakDefinedFunction;
import core.parser.tokenizer.BreakMarks;
import core.parser.tokenizer.BreakOperators;
import core.parser.tokenizer.BreakNumbers;
import core.parser.tokenizer.BreakSpaces;
import core.parser.tokenizer.ReplaceSymbols;
import i18n.FkeyWord;
import flowchart.algorithm.Program;
import static flowchart.utils.Theme.COLOR_CHAR;
import static flowchart.utils.Theme.COLOR_FUNCTION;
import static flowchart.utils.Theme.COLOR_INT;
import static flowchart.utils.Theme.COLOR_KEYWORD;
import static flowchart.utils.Theme.COLOR_LOGIC;
import static flowchart.utils.Theme.COLOR_NORMAL;
import static flowchart.utils.Theme.COLOR_OPERATOR;
import static flowchart.utils.Theme.COLOR_PARENTESIS;
import static flowchart.utils.Theme.COLOR_REAL;
import static flowchart.utils.Theme.COLOR_TEXT;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * Created on 21/nov/2015, 10:15:18
 *
 * @author zulu - computer
 */
public class Style extends DefaultStyledDocument {

    final StyleContext cont = StyleContext.getDefaultStyleContext();
    final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
    final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);

    final AttributeSet styleInteger = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
    final AttributeSet styleReal = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.GREEN);
    final AttributeSet styleLogic = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
    final AttributeSet styleText = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.PINK);

    public AttributeSet getStringStyle(String str) {
        System.out.println("<<<" + str + ">>>");
        str = str.trim();
        if (Finteger.isValueValid(str)) {
            return styleInteger;
        }
        if (Freal.isValueValid(str)) {
            return styleReal;
        }

        if (Flogic.isValueValid(str)) {
            return styleLogic;
        }
        if (Ftext.isValueValid(str)) {
            return styleText;
        }
        return attrBlack;
    }
   

    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offset, str, a);
        
        List exp = parse(str, null, null);
        for (Object exp1 : exp) {
            System.out.println(exp1);
        }
        
//        
//        
//        System.out.print(str + " ( " + offset + " ) ");
//        String styleText = getText(0, getLength());
//        //start of word
//        int before = findLastNonWordChar(styleText, offset - 1);
//        if (before < 0) {
//            before = 0;
//        }
//        //end of word
//        int after = findFirstNonWordChar(styleText, before);
//        if (before == after) {
//            after++;
//        }
//        AttributeSet attr = getStringStyle(styleText.substring(before, after));
//        setCharacterAttributes(before, after - before, attr, false);
////        // colorize next words
////        if (after < styleText.length()) {
////            insertString(findFirstNonWordChar(styleText, after), str, a);
////        }
    }

    public void remove(int offs, int len) throws BadLocationException {
        super.remove(offs, len);

        String text = getText(0, getLength());
        int before = findLastNonWordChar(text, offs);
        if (before < 0) {
            before = 0;
        }
        int after = findFirstNonWordChar(text, offs);

        if (text.substring(before, after).matches("(\\W)*(private|public|protected)")) {
            setCharacterAttributes(before, after - before, attr, false);
        } else {
            setCharacterAttributes(before, after - before, attrBlack, false);
        }
    }

    private int findLastNonWordChar(String text, int index) {
        int bginStr = beginOfString(text, index);
        if (bginStr >= 0) {
            return bginStr;
        }
//        while (--index >= 0) {
//            if (String.valueOf(styleText.charAt(index)).matches("\\W")) {
//                break;
//            }
//        }
//        return index;

        while (--index >= 0) {
            char ch = text.charAt(index);
            if (Character.isWhitespace(ch)) {
                return index + 1; // space
            }
            if (Mark.isMark(ch)) {
                return index + 1; // parentesis coma
            }
            if (index > 0 && text.charAt(index - 1) == Fcharacter.ESCAPE_CHAR) {
                continue; // skip special Char
            }
            if (ch == Ftext.DELIMITATOR_TEXT) {
                return index + 1; // TExt
            }
            if (ch == Fcharacter.DELIMITATOR_CHAR) {
                return index; // Char
            }

            if (index > 0 && text.charAt(index - 1) == Negative.symbol.charAt(0) && Character.isDigit(ch)) {
                continue; // negative signal - skip number
            }

            if (CoreCalculator.OPERATOR_CHARS.indexOf(ch) >= 0) {
                return index + 1;
            }
        }
        return index;
    }

    private int beginOfString(String txt, int pos) {
        int posStr = -1;
        boolean inString = false;
        for (int i = 0; i < pos; i++) {
            if (txt.charAt(i) == Ftext.DELIMITATOR_TEXT) {
                if (inString) {
                    posStr = -1;
                    inString = false;
                } else {
                    posStr = i;
                    inString = true;
                }
            }//DELIMITATOR
            if (txt.charAt(i) == Fcharacter.ESCAPE_CHAR) {
                i++; //skip escapechar
            }
        }
        return posStr;
    }

    private int findFirstNonWordChar(String text, int index) {

//        while (index < styleText.length()) {
//            if (String.valueOf(styleText.charAt(index)).matches("\\W")) {
//                break;
//            }
//            index++;
//        }
//        return index;
        boolean inString = false;
        while (index < text.length()) {
            char ch = text.charAt(index);
            //Strings
            if (ch == Ftext.DELIMITATOR_TEXT) {
                if (inString) {
                    return index + 1;
                }
                inString = true;
            }
            //add all chars in the string
            if (inString) {
                if (ch == Fcharacter.ESCAPE_CHAR) { // ignore escape chars
                    index++;
                }
                index++;
                continue;
            }

            if (Character.isWhitespace(ch)) {
                return index; // space
            }

            if (Mark.isMark(ch)) {
                return index; // parentesis coma
            }

            if (ch == Negative.symbol.charAt(0) && index < text.length() - 2 && Character.isDigit(text.charAt(index + 1))) {
                continue; // negative signal - skip signal
            }

            if (CoreCalculator.OPERATOR_CHARS.indexOf(ch) >= 0) {
                return index;
            }
            //process next
            index++;
        }
        return index;

    }

    public static List parse(String exp, Memory memory, Program myProgram) {
        List expression = new ArrayList();  // tokens of the expression

        expression.add(exp);
        try {
            expression = BreakSpaces.execute(expression);
        } catch (Exception ex) {
        }
        try {
            expression = BreakMarks.execute(expression);
        } catch (FlowchartException ex) {
        }
        try {
            expression = BreakNumbers.execute(expression);
        } catch (Exception ex) {
        }
        try {
            //----------------- ARRAYS ------------------------
            expression = BreakArrays.execute(expression, memory, myProgram);
        } catch (Exception ex) {
        }
        //-------------------------------------------------
        //----------------USER DEFINED FUNCTIONS -------------
        if (myProgram != null) {
            try {
                expression = BreakDefinedFunction.execute(expression, memory, myProgram);
            } catch (Exception ex) {
            }
        }
        try {
            //-------------------------------------------------
            expression = BreakOperators.execute(expression);
        } catch (Exception ex) {
        }
        try {
            expression = ReplaceSymbols.execute(expression, memory);
        } catch (Exception ex) {
        }
        return expression;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201511211015L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
