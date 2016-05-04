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
package ui.flowchart.expression;

import core.CoreCalculator;
import core.Memory;
import core.data.Fcharacter;
import core.data.Finteger;
import core.data.Flogic;
import core.data.Freal;
import core.data.Fsymbol;
import core.data.Ftext;
import core.data.complexData.Farray;
import core.evaluate.CoreElement;
import core.parser.Mark;
import flowchart.algorithm.Program;
import flowchart.utils.Comments;
import ui.FProperties;
import i18n.FkeyWord;
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
public class FsyntaxHighlight extends DefaultStyledDocument {

    private Memory memory;
    private Program program;

    AttributeSet sInteger;
    AttributeSet sFunction;
    AttributeSet sKeyword;
    AttributeSet sLogic;
    AttributeSet sNormal;
    AttributeSet sOperator;
    AttributeSet sReal;
    AttributeSet sString;
    AttributeSet sComment;

    public FsyntaxHighlight() {
        StyleContext context = StyleContext.getDefaultStyleContext();

        sNormal = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxNormal));
        // sNormal = context.addAttribute(sNormal, StyleConstants.Background, FProperties.getColor(FProperties.keySintaxBackground));

        sInteger = context.addAttribute(sNormal, StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxInteger));
        sFunction = context.addAttribute(sNormal, StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxFunction));
        sLogic = context.addAttribute(sNormal, StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxLogic));
        sNormal = context.addAttribute(sNormal, StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxNormal));
        sOperator = context.addAttribute(sNormal, StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxOperator));
        sReal = context.addAttribute(sNormal, StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxReal));
        sString = context.addAttribute(sNormal, StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxString));
        sComment = context.addAttribute(sNormal, StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxComments));

        sKeyword = context.addAttribute(sNormal, StyleConstants.Foreground, FProperties.getColor(FProperties.keySintaxKeyword));
        sKeyword = context.addAttribute(sKeyword, StyleConstants.Underline, true);
        sKeyword = context.addAttribute(sKeyword, StyleConstants.Bold, true);
    }

    public AttributeSet getAttibute(Object elem) {
        if (elem instanceof Comments) {
            return sComment;
        } else if (elem instanceof Fsymbol) {
            return symbolToColor((Fsymbol) elem);
        } else if (elem instanceof CoreElement) {
            if (CoreCalculator.isFunction(elem)) {
                return sFunction;
            } else {
                return sOperator;
            }
        } else if (elem instanceof Mark || elem.equals(FkeyWord.OPERATOR_SET)) {
            return sOperator;
        }
        if (FkeyWord.isWord(elem.toString())) {
            return sKeyword;
        } else if (FkeyWord.isdataType(elem.toString())) {
            return typeToColor(elem.toString());
        }
        return sNormal;
    }

    public AttributeSet symbolToColor(Fsymbol var) {
        //if is one array return the type
        if (var instanceof Farray) {
            var = ((Farray) var).getTemplateElement();
        }

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

    public AttributeSet typeToColor(String type) {
        if (type.equalsIgnoreCase(Finteger.TYPE_INT_NAME)) {
            return sInteger;
        } else if (type.equalsIgnoreCase(Freal.TYPE_REAL_NAME)) {
            return sReal;
        } else if (type.equalsIgnoreCase(Fcharacter.TYPE_CHAR_NAME)) {
            return sString;
        } else if (type.equalsIgnoreCase(Flogic.TYPE_LOGIC_NAME)) {
            return sLogic;
        } else if (type.equalsIgnoreCase(Ftext.TYPE_TEXT_NAME)) {
            return sString;
        }
        return sNormal;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offset, str, sNormal);
        if (str.isEmpty()) { // empty string
            return;
        }
        String fullText = getText(0, getLength());
        String[] lines = fullText.split("\n");
        int pos = 0;
        for (String code : lines) {
            List expression = Identation.tokenizer(code, memory, program);
            for (Object elem : expression) {
                String txt = Identation.getText(elem);
                AttributeSet attr = getAttibute(elem);
                pos = fullText.indexOf(txt, pos);
                setCharacterAttributes(pos, txt.length(), attr, false);
                pos += txt.length();
                setCharacterAttributes(pos, 1, sNormal, false);
            }
            pos++;
        }
    }

    public void remove(int offs, int len) throws BadLocationException {
        super.remove(offs, len);
        insertString(0, "", sNormal);

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201511211015L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param memory the memory to set
     */
    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    /**
     * @param program the program to set
     */
    public void setProgram(Program program) {
        this.program = program;
    }
}
