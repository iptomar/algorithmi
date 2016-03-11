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
import core.CoreToken;
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
import ui.FProperties;
import i18n.FkeyWord;
import flowchart.algorithm.Program;
import static flowchart.utils.Theme.COLOR_FUNCTION;
import static flowchart.utils.Theme.COLOR_KEYWORD;
import static flowchart.utils.Theme.COLOR_NORMAL;
import static flowchart.utils.Theme.COLOR_OPERATOR;
import static flowchart.utils.Theme.COLOR_PARENTESIS;
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
public class FlowChartStyle extends DefaultStyledDocument {

    final StyleContext cont = StyleContext.getDefaultStyleContext();
    final AttributeSet sInteger = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxInteger));
    final AttributeSet sFunction = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxFunction));
    final AttributeSet sKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxKeyword));
    final AttributeSet sLogic = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxLogic));
    final AttributeSet sNormal = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxNormal));
    final AttributeSet sOperator = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxOperator));
    final AttributeSet sReal = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxReal));
    final AttributeSet sString = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxString));

    public AttributeSet getAttibute(Object elem) {
        if (FkeyWord.isReservedWord(elem.toString())) {
            return sKeyword;
        }
        if (elem instanceof Fsymbol) {
            return symbolToColor((Fsymbol) elem);
        } else if (elem instanceof FunctionCall) {
            return sFunction;
        } else if (elem instanceof CoreElement) {
            return sOperator;
        } else if (elem instanceof Mark) {
            return sNormal;
        }
        return sNormal;
    }

    public String getText(Object elem) {
        if (FkeyWord.isReservedWord(elem.toString())) {
            return elem.toString();
        }
        if (elem instanceof CoreToken) {
            return ((CoreToken) elem).getDescriptor();
        }
        return elem.toString();
    }

    public AttributeSet symbolToColor(Fsymbol var) {
        if (var instanceof Finteger) {
            return sInteger;
        } else if (var instanceof Freal) {
            return sReal;
        } else if (var instanceof Fcharacter) {
            return sString;
        } else if (var instanceof Flogic) {
            return sLogic;
        } else if (var instanceof Ftext) {
            return sString;
        }
        return sNormal;
    }

    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offset, str, a);
        String fullText = getText(0, getLength());
        System.out.println(str + " ( " + offset + " ) ");
        List expression = parse(fullText, null, null);
        int pos = 0;
        for (Object elem : expression) {
            String txt = getText(elem);
            AttributeSet attr = getAttibute(elem);
            pos = fullText.indexOf(txt, pos);
            setCharacterAttributes(pos, txt.length(), attr, false);
        }
    }

//    public void remove(int offs, int len) throws BadLocationException {
//        super.remove(offs, len);
//
//        String text = getText(0, getLength());
//        int before = findLastNonWordChar(text, offs);
//        if (before < 0) {
//            before = 0;
//        }
//        int after = findFirstNonWordChar(text, offs);
//        setCharacterAttributes(before, after - before, sNormal, false);
//
//    }

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
