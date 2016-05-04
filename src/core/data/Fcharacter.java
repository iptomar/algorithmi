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
public class Fcharacter extends Fsymbol {
    
    public static final String TYPE_CHAR_NAME = FkeyWord.get("TYPE.character");
   

    public static final char ESCAPE_CHAR = FkeyWord.get("TYPE.character.escape").charAt(0);
    public static final char DELIMITATOR_CHAR = FkeyWord.get("TYPE.character.limitator").charAt(0);

    public static String defaultValue = DELIMITATOR_CHAR + " " + DELIMITATOR_CHAR;

    public static String addTerminators(char value) {
        return ""+DELIMITATOR_CHAR + value + DELIMITATOR_CHAR;
    }

    public Fcharacter(String name, String value, int level) throws FlowchartException {
        super(name, value, level);
    }

    public Fcharacter(String name, String value) throws FlowchartException {
        this(name, value, 0);
    }

    public Fcharacter(String value) throws FlowchartException {
        super(value);
    }

    public String getTypeName() {
        return TYPE_CHAR_NAME;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Object toValue(Object value) throws FlowchartException {
        if (value instanceof Character) {
            return value;
        }
        try {
            String v = value.toString();
            if (v.length() < 3) {
                throw new FlowchartException("DEFINE.invalidValue", value.toString(), getTypeName());
            }
            if (v.charAt(0) != DELIMITATOR_CHAR || v.charAt(v.length() - 1) != DELIMITATOR_CHAR) {
                throw new FlowchartException("DEFINE.invalidValue", value.toString(), getTypeName());
            }
            Character c = getEscapeChar(v.substring(1, v.length() - 1));
            if (c == null) {
                throw new FlowchartException("DEFINE.invalidValue", value.toString(), getTypeName());
            }
            return c;
        } catch (Exception e) {
            throw new FlowchartException("DEFINE.invalidValue", value.toString(), getTypeName());
        }
    }

    public static Character getEscapeChar(String ch) {
        if (ch.charAt(0) == ESCAPE_CHAR) {
            ch = ch.substring(1);
        }
        switch (ch) {
            case "t":
                return '\t';
            case "n":
                return '\n';
            case "\"":
                return '\"';
            case "\'":
                return '\'';
            case "\\":
                return '\\';
        }
        if (ch.length() > 1) {
            return null;
        }
        return ch.charAt(1);
    }

    public static String getStringChar(char ch) {
        switch (ch) {
            case '\t':
                return "\\t";
            case '\n':
                return "\\n";
            case '\"':
                return "\\\"";
            case '\'':
                return "\'";
            case '\\':
                return "\\\\";
        }
        return ch + "";
    }

    /**
     * @return the Textual value
     */
    @Override
    public String getDefinitionValue() {
        return addTerminators((Character)value);
    }

    public static boolean isValueValid(Object value) {
        String v = value.toString();
        if (v.length() < 3) {
            return false;
        }
        if (v.charAt(0) != DELIMITATOR_CHAR || v.charAt(v.length() - 1) != DELIMITATOR_CHAR) {
            return false;
        }
        Character c = getEscapeChar(v.substring(1, v.length() - 1));
        if (c == null) {
            return false;
        }
        return true;
    }

    /**
     * @return the Textual value
     */
    @Override
    public String getTextValue() {
        return ((Character) value).toString();
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // token to type
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static final String TYPE_TOKEN = FkeywordToken.get("TYPE.character.key");
     @Override
    public String getTypeToken() {
        return TYPE_TOKEN;
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    
}
