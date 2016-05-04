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
public class Finteger extends FabstractNumber {

    public static final String TYPE_INT_NAME = FkeyWord.get("TYPE.integer");
    public static String defaultValue = "0";

    public Finteger(String name, String value, int level) throws FlowchartException {
        super(name, value, level);
    }

    public Finteger(String name, String value) throws FlowchartException {
        super(name, value, 0);
    }

    public Finteger(String value) throws FlowchartException {
        super(value);
    }

    public Finteger(long v) throws FlowchartException {
        super(new Long(v));
    }

    @Override
    public String getTypeName() {
        return TYPE_INT_NAME;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public double getDoubleValue() {
        return (Long) value;
    }

    @Override
    public Object toValue(Object value) throws FlowchartException {
        if (value instanceof Long) {
            return value;
        }
        try {
            return new Long(value.toString());
        } catch (Exception e) {
            throw new FlowchartException("DEFINE.invalidValue", value.toString(), getTypeName());
        }
    }

    public static boolean isValueValid(Object value) {
        try {
            new Long(value.toString());
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // token to type
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //public static final String TYPE_TOKEN = FkeywordToken.get("TYPE.integer.key");

    @Override
    public String getTypeToken() {
        return FkeywordToken.get("TYPE.integer.key");
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
