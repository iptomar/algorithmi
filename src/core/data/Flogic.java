/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data;

import core.data.exception.FlowchartException;
import i18n.FkeyWord;
import i18n.FkeywordToken;

/**
 *
 * @author zulu
 */
public class Flogic extends Fsymbol {
    public static final String TYPE_LOGIC_NAME = FkeyWord.get("TYPE.boolean");
    public static final String TRUE = FkeyWord.get("TYPE.boolean.true");
    public static final String FALSE = FkeyWord.get("TYPE.boolean.false");

    public static final String defaultValue = FALSE;

    public Flogic(String name, String value, int level) throws FlowchartException {
        super(name, value, level);
    }

    public Flogic(String name, String value) throws FlowchartException {
        this(name, value, 0);
    }

    public Flogic(String value) throws FlowchartException {
        super(value);
    }

    public Flogic(boolean value) throws FlowchartException {
        this(value ? TRUE : FALSE);
    }

    public String getTypeName() {
        return TYPE_LOGIC_NAME;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Object toValue(Object value) throws FlowchartException {
        if (value instanceof Boolean) {
            return value;
        }
        String v = value.toString();
        if (v.equalsIgnoreCase(TRUE)) {
            return new Boolean(true);
        }
        if (v.equalsIgnoreCase(FALSE)) {
            return new Boolean(false);
        }
        if (v.equalsIgnoreCase("1")) {
            return new Boolean(true);
        }
        if (v.equalsIgnoreCase("0")) {
            return new Boolean(false);
        }

        throw new FlowchartException("DEFINE.invalidValue", value.toString(), getTypeName());
    }

    /**
     * @return the Textual value
     */
    public String getDefinitionValue() {
        if ((Boolean) value) {
            return TRUE;
        }
        return FALSE;
    }
    public boolean booleanValue(){
        return (Boolean)value;
    }
    public boolean isTrue(){
        return (Boolean)value==true;
    }
     public boolean isFalse(){
        return (Boolean)value==false;
    }

    public static boolean isValueValid(Object value) {
        String v = value.toString();
        if (v.isEmpty()) {
            return false;
        }
        if (TRUE.equalsIgnoreCase(v) || FALSE.equalsIgnoreCase(v)) {
            return true;
        }
        return false;
    }

    /**
     * @return the Textual value
     */
    public String getTextValue() {
        if ((Boolean) value) {
            return TRUE;
        }
        return FALSE;
    }

    public static void main(String[] args) {
        String[] val = {
            "verdadeiro",
            "falso",
            "0",
            "1",
            ""
        };
        for (String v : val) {
            System.out.println(v + " = " + isValueValid(v) + " ");
        }
        for (String v : val) {
            try {
                Flogic i = new Flogic(v);
                System.out.println(v + " \t " + i + "\t ->  [" + i.getTextValue() + "]");
            } catch (FlowchartException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // token to type
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //public static final String TYPE_TOKEN = FkeywordToken.get("TYPE.boolean.key");

    @Override
    public String getTypeToken() {
        return FkeywordToken.get("TYPE.boolean.key");
    }
   
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
