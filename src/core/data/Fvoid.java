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
public class Fvoid extends Fsymbol {

    public static String TYPE_VOID_NAME = FkeyWord.get("TYPE.void");
    private static String anonimVoid = "anonimVoidVariable";

    //creates a void varable

    public static Fvoid createVoid() {
        try {
            return new Fvoid();
        } catch (FlowchartException ex) {
            return null;
        }
    }

    public Fvoid() throws FlowchartException {
        super(ANONYM_VAR, anonimVoid, 0);
    }

    public String getTypeName() {
        return TYPE_VOID_NAME;
    }

    public String getDefaultValue() {
        return null;
    }

    @Override
    public Object toValue(Object value) throws FlowchartException {
        return value;
    }

    /**
     * @return the Textual value
     */
    public String getDefinitionValue() {
        return "ERROR VOID " + TYPE_VOID_NAME;
    }

    public static boolean isValueValid(Object value) {
        return true;
    }

    /**
     * @return the Textual value
     */
    public String getTextValue() {
        return "ERROR VOID " + TYPE_VOID_NAME;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // token to type
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//    public static final String TYPE_TOKEN = FkeywordToken.get("TYPE.void.key");

    @Override
    public String getTypeToken() {
        return FkeywordToken.get("TYPE.void.key");
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
