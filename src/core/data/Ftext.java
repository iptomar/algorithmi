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

import core.data.exception.FlowchartException;
import i18n.FkeyWord;
import i18n.FkeywordToken;

/**
 *
 * @author zulu
 */
public class Ftext extends Fsymbol {

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
                txt.append(Fcharacter.getEscapeChar(text.substring(i,i+2)));
                i++;
                //txt.append(Fcharacter.getEscapeChar(text.charAt(i) + ""));
            } else {
                txt.append(text.charAt(i));
            }
        }
        return txt.toString();
    }
   //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // token to type
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static final String TYPE_TOKEN = FkeywordToken.get("TYPE.string.key");

    @Override
    public String getTypeToken() {
        return TYPE_TOKEN;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
