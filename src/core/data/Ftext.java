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
package core.data;

import core.Memory;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import core.parser.Mark;
import i18n.FkeyWord;
import i18n.FkeywordToken;
import java.util.List;
import ui.FLog;

/**
 *
 * @author zulu
 */
public class Ftext extends Fsymbol {

    /**
     * expression to update indexed text
     */
    public Expression indexExpression = null;

    public static final String TYPE_TEXT_NAME = FkeyWord.get("TYPE.string");
    public static final char DELIMITATOR_TEXT = FkeyWord.get("TYPE.string.limitator").charAt(0);

    public static String defaultValue = DELIMITATOR_TEXT + "" + DELIMITATOR_TEXT;

    public static String addTerminators(String value) {
        return DELIMITATOR_TEXT + value + DELIMITATOR_TEXT;
    }

    public Ftext(String name, String value, int level) throws FlowchartException {
        super(name, value, level);
    }

    public Ftext(Ftext original) throws FlowchartException {
        super(ANONYM_VAR, DELIMITATOR_TEXT + original.getValue().toString() + DELIMITATOR_TEXT, original.level);
    }

    public Ftext(String name, String value) throws FlowchartException {
        this(name, value, 0);
    }

    public Ftext(String value) throws FlowchartException {
        super(value);
    }

    public String getTypeName() {
        return TYPE_TEXT_NAME;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Object toValue(Object value) throws FlowchartException {
        String v = value.toString().trim();
        if (v.length() < 2) {
            throw new FlowchartException("DEFINE.invalidValue", value.toString(), getTypeName());
        }
        if (v.startsWith(DELIMITATOR_TEXT + "") && v.endsWith(DELIMITATOR_TEXT + "")) {
            if (v.length() == 2) {
                return "";
            }
            return v.substring(1, v.length() - 1);
        }
        throw new FlowchartException("DEFINE.invalidValue", value.toString(), getTypeName());
    }

    public static boolean isValueValid(Object value) {
        String v = value.toString();
        if (v.length() >= 2 && v.startsWith(DELIMITATOR_TEXT + "") && v.endsWith(DELIMITATOR_TEXT + "")) {
            return true;
        }
        return false;
    }

    /**
     * @return the Textual value
     */
    @Override
    public String getDefinitionValue() {
        return addTerminators(value.toString());
    }

    /**
     * @return the Textual value
     */
    @Override
    public String getTextValue() {
        return convertEscapeChars(value.toString());

    }

    /**
     * converts a string with textual escape chars to escape chars
     *
     * @param text
     * @return
     */
    public static String convertEscapeChars(String text) {
        StringBuilder txt = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == Fcharacter.ESCAPE_CHAR) {
                txt.append(Fcharacter.getEscapeChar(text.substring(i, i + 2)));
                i++;
                //txt.append(Fcharacter.getEscapeChar(text.charAt(i) + ""));
            } else {
                txt.append(text.charAt(i));
            }
        }
        return txt.toString();
    }

    @Override
    public String getTypeToken() {
        return FkeywordToken.get("TYPE.string.key");
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //                                Indexed access                            INDEXED ACCESS
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public void setElementValue(Ftext newValue, Memory mem) throws FlowchartException {
        Finteger position = (Finteger) indexExpression.evaluate(mem);
        setText(newValue, position);
    }

    public Ftext getElementValue(Memory mem) throws FlowchartException {
        Finteger position = (Finteger) indexExpression.evaluate(mem);
        return getText(position);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public void setText(Ftext txt, Finteger position) throws FlowchartException {
        String original = getTextValue();
        int start = position.getIntValue();
        if (original.length() < start || start < 0) {
            throw new FlowchartException("TYPE.text.invalidPosition", position.getTextValue(), getName() + " [" + original + "]");
        }
        String param = txt.getTextValue();
        StringBuilder str = new StringBuilder(original);
        for (int i = 0; i < param.length(); i++) {
            if (start + i >= str.length()) {
                str.append(param.charAt(i));
            } else {
                str.setCharAt(i + start, param.charAt(i));
            }
        }
        setValue(DELIMITATOR_TEXT + str.toString() + DELIMITATOR_TEXT);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // Indexed access
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public Ftext getText(Finteger position) throws FlowchartException {
        String original = getTextValue();
        int start = position.getIntValue();
        if (original.length() < start || start < 0) {
            throw new FlowchartException("TYPE.text.invalidPosition", position.getTextValue(), getName() + "=[" + original + "]");
        }
        return new Ftext("" + DELIMITATOR_TEXT + original.charAt(start) + DELIMITATOR_TEXT);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // Indexed access
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public Ftext getText(Finteger begin, Finteger end) throws FlowchartException {
        String original = getTextValue();
        int start = begin.getIntValue();
        int stop = end.getIntValue();
        if (original.length() < start || start < 0) {
            throw new FlowchartException("TYPE.text.invalidPosition", begin.getTextValue(), getName() + "=[" + original + "]");
        }
        if (original.length() < stop + 1 || stop < 0) {
            throw new FlowchartException("TYPE.text.invalidPosition", end.getTextValue(), getName() + "=[" + original + "]");
        }
        // invert positions if begin is greather than end
        if (start > stop) {
            return getText(end, begin);
        }
        stop++; // to include stop position

        return new Ftext("" + DELIMITATOR_TEXT + original.substring(start, stop) + DELIMITATOR_TEXT);
    }

    public boolean isIndexed() {
        return this.indexExpression != null;
    }

    /**
     * return the indexes expression rounded by square brackets
     *
     * @return
     */
    @Override
    public String getFullName() {
        String indexes = indexExpression != null
                ? Mark.SQUARE_OPEN + indexExpression.toString() + Mark.SQUARE_CLOSE : "";
        return getName() + indexes;
    }

    @Override
    public Object clone() {
        try { // call clone in Object.
            Ftext txt = (Ftext) super.clone();
            txt.indexExpression = indexExpression;
            return txt;
        } catch (Exception e) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            FLog.printLn(Fsymbol.class.getName() + " Clone not alowed");
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            return this;
        }
    }
    public String toString(){
        String txt = super.toString();
        if( indexExpression != null){
            txt += indexExpression.toString();
        }
        return txt;
    }
    public String getFullNameToken(){
        StringBuilder txt = new StringBuilder(getName());
        if( indexExpression != null)        {
            txt.append(" " + Mark.SQUARE_OPEN_TOKEN + " ");
            txt.append(FkeywordToken.translateTokensToWords(indexExpression.getIdented()) + " ");
            txt.append(Mark.SQUARE_CLOSE_TOKEN);
        }
        return txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
