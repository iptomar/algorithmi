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
public class Freal extends FabstractNumber {

    /**
     * string name of the real type
     */
    public static final String TYPE_REAL_NAME = FkeyWord.get("TYPE.real");
    public static String defaultValue = "0.0";

    public Freal(String name, String value, int level) throws FlowchartException {
        super(name, value, level);
    }

    public Freal(String name, String value) throws FlowchartException {
        super(name, value, 0);
    }

    public Freal(String value) throws FlowchartException {
        super(value);
    }

    public Freal(double v) throws FlowchartException {
        super(new Double(v));
    }

    public String getTypeName() {
        return TYPE_REAL_NAME;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public double getDoubleValue() {
        return (Double) value;
    }

    /**
     * @return the Textual value
     */
    public String getDefinitionValue() {
        return value.toString();
    }

    @Override
    public Object toValue(Object value) throws FlowchartException {
        if (value instanceof Double) {
            return value;
        }
        try {
            return new Double(value.toString());
        } catch (Exception e) {
            throw new FlowchartException("DEFINE.invalidValue", value.toString(), getTypeName());
        }
    }

    public static boolean isValueValid(Object value) {
        try {
            new Double(value.toString());
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // token to type
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
 //   public static final String TYPE_TOKEN = FkeywordToken.get("TYPE.real.key");

    @Override
    public String getTypeToken() {
        return FkeywordToken.get("TYPE.real.key");
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
