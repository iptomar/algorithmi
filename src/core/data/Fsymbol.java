/*
 * Copyright (c) 2015 Instituto Politecnico de Tomar. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */
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


import core.CoreToken;
import core.Memory;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import core.parser.Mark;
import flowchart.algorithm.Program;
import ui.FLog;
import i18n.Fi18N;
import i18n.FkeyWord;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zulu
 */
public abstract class Fsymbol extends CoreToken implements Cloneable, Serializable {

    public static String ANONYM_VAR = "_anonym_var";
    private String name;
    protected Object value;
    protected int level;
    protected String comments = "";
    protected boolean isConstant = false;

    //-----------------------ABSTRACT---------------------------------------
    public abstract String getTypeName();
    public abstract String getTypeToken();

    public abstract Object toValue(Object value) throws FlowchartException;

    public abstract String getDefaultValue();

    //----------------------------------------------------------------------
    public Fsymbol(Object value) throws FlowchartException {
        this.name = ANONYM_VAR;
        this.isConstant = true;
        setValue(value);
        this.level = 0;
        setDescriptor(value.toString());
    }

    public boolean isVariable() {
        return !isConstant;
    }

    public boolean isConstant() {
        return isConstant;
    }

    public void setIsConstant(boolean isConstantant) {
        this.isConstant = isConstantant;
    }

    public Fsymbol(String name, Object value, int level) throws FlowchartException {
        if (!name.equals(ANONYM_VAR)) {
            validadeName(name);
        } else {
            this.isConstant = true;
        }
        this.name = name;
        setValue(value);
        this.level = level;
    }

    public String toString() {
        return getInstruction() ;
    }

    public static String defaultValue(String type) {

        if (type.equalsIgnoreCase(FkeyWord.get("TYPE.integer"))) {
            return Finteger.defaultValue;
        }
        if (type.equalsIgnoreCase(FkeyWord.get("TYPE.real"))) {
            return Freal.defaultValue;
        }
        if (type.equalsIgnoreCase(FkeyWord.get("TYPE.character"))) {
            return Fcharacter.defaultValue;
        }
        if (type.equalsIgnoreCase(FkeyWord.get("TYPE.string"))) {
            return Ftext.defaultValue;
        }
        if (type.equalsIgnoreCase(FkeyWord.get("TYPE.boolean"))) {
            return Flogic.defaultValue;
        }
        return Fi18N.get("STRING.error");

    }

    public static Fsymbol createSymbolByType(String type) {
        try {
            return create(type, ANONYM_VAR);
        } catch (FlowchartException ex) {
            return null;
        }
    }

    public static Fsymbol defineSymbol(String definition, Memory mem, Program prog) throws FlowchartException {
        if (definition.equals(FkeyWord.get("KEYWORD.define"))) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException("KEYWORD.notDefinedError");
        }
        definition = definition.substring(definition.indexOf(" ")).trim(); //cut define
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        if (!isArray(definition)) {//--------------------------------------------------------------Single Var
            String dataType = definition.substring(0, definition.indexOf(" ")).trim();
            definition = definition.substring(definition.indexOf(" ")).trim(); //cut data type
            
             //-------------------------------- not contains initialization value ----------------------
            if( definition.indexOf(" ") < 0){
                //append default definition
                definition = definition + " " + FkeyWord.OPERATOR_SET + " " + Fsymbol.defaultValue(dataType);
            }
            //---------------------------------------------------------------------------------------------------
            String name = definition.substring(0, definition.indexOf(" ")).trim();
            definition = definition.substring(definition.indexOf(" ")).trim(); // name
            definition = definition.substring(definition.indexOf(" ")).trim(); // =

            Expression ex = new Expression(definition, mem, prog);
            Fsymbol val = ex.getReturnType(mem);
            return create(dataType, name, val.getDefinitionValue());
        } else { //---------------------- Array            
            return new Farray(definition, mem, prog); // create array
        } //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    }

    /**
     * verify if the definitions is one array
     *
     * @param def
     * @return
     */
    private static boolean isArray(String def) {
        int square = def.indexOf(Mark.SQUARE_OPEN);
        if (square < 0) {
            return false; // ! contains [
        }
        int set = def.indexOf(FkeyWord.OPERATOR_SET);
        if (set < 0) {
            return true; // ! contains =
        }
        return square < def.indexOf(FkeyWord.OPERATOR_SET);
    }

    public static Fsymbol createByValue(String value) throws FlowchartException {
        try {
            if (Flogic.isValueValid(value)) {
                return new Flogic(value);
            }
            if (Fcharacter.isValueValid(value)) {
                return new Fcharacter(value);
            }
            if (Ftext.isValueValid(value)) {
                return new Ftext(value);
            }
            if (Finteger.isValueValid(value)) {
                return new Finteger(value);
            }
            if (Freal.isValueValid(value)) {
                return new Freal(value);
            }
        } catch (FlowchartException e) {
        }
        return null;
    }

    public static Fsymbol create(String type, String name, String value) throws FlowchartException {
        if (type.equalsIgnoreCase(FkeyWord.get("TYPE.integer"))) {
            return new Finteger(name, value);
        }
        if (type.equalsIgnoreCase(FkeyWord.get("TYPE.real"))) {
            return new Freal(name, value);
        }
        if (type.equalsIgnoreCase(FkeyWord.get("TYPE.character"))) {
            return new Fcharacter(name, value);
        }
        if (type.equalsIgnoreCase(FkeyWord.get("TYPE.string"))) {
            return new Ftext(name, value);
        }
        if (type.equalsIgnoreCase(FkeyWord.get("TYPE.boolean"))) {
            return new Flogic(name, value);
        }
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        throw new FlowchartException(
                type + " " + name + FkeyWord.getSetOperator() + " " + value,
                "EVALUATE.error.unknowType",
                new String[]{
                    type
                });
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    }

    public static Fsymbol create(String type, String name) throws FlowchartException {
        if (type.equalsIgnoreCase(Finteger.TYPE_INT_NAME)) {
            return new Finteger(name, Finteger.defaultValue);
        }
        if (type.equalsIgnoreCase(Freal.TYPE_REAL_NAME)) {
            return new Freal(name, Freal.defaultValue);
        }
        if (type.equalsIgnoreCase(Fcharacter.TYPE_CHAR_NAME)) {
            return new Fcharacter(name, Fcharacter.defaultValue);
        }
        if (type.equalsIgnoreCase(Ftext.TYPE_TEXT_NAME)) {
            return new Ftext(name, Ftext.defaultValue);
        }
        if (type.equalsIgnoreCase(Flogic.TYPE_LOGIC_NAME)) {
            return new Flogic(name, Flogic.defaultValue);
        }
        if (type.equalsIgnoreCase(Fvoid.TYPE_VOID_NAME)) {
            return new Fvoid();
        }
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        throw new FlowchartException("DEFINE.unknowType", type + " " + name);
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * full name of symbol ( array have indexes)
     *
     * @return
     */
    public String getFullName() {
        return name;
    }
    public String getFullNameToken() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) throws FlowchartException {
        validadeName(name);
        this.name = name;
    }

    /**
     * @return the value
     */
    public Object getValue() throws FlowchartException {
        return value;
    }

    /**
     * @return the Textual value
     */
    public String getDefinitionValue() {
        return value.toString();
    }

    /**
     * @return the full Textual definition
     */
    public String getInstruction() {
        return getTypeName() + " " + getName() + " " + FkeyWord.OPERATOR_SET + " " + getDefinitionValue();
    }

    public String getFullInfo() {
        StringBuilder txt = new StringBuilder();
        txt.append(Fi18N.get("DEFINE.type.title") + " :" + getTypeName());
        txt.append("\n  " + Fi18N.get("DEFINE.name.title") + " :" + getName());
        txt.append("\n  " + Fi18N.get("DEFINE.value.title") + " :" + getDefinitionValue() + " (" + value.getClass().getSimpleName() + ")");
        txt.append("\n  " + Fi18N.get("DEFINE.level.title") + " :" + getLevel());
        if (!comments.isEmpty()) {
            txt.append("\n " + Fi18N.get("DEFINE.comments.title") + " :\n" + getComments());
        }
        return txt.toString();
    }

    /**
     * @return the Textual value
     */
    public String getTextValue() {
        return value.toString();
    }

    /**
     * @param newValue the value to set
     */
    public void setValue(Object newValue) throws FlowchartException {
        if( newValue == null){
            throw new FlowchartException("DEFINE.invalidValue", "",getTypeName());
        }
        //if is a varable with the same type
        if (newValue != null && newValue.getClass().equals(this.getClass())) {
            this.value = ((Fsymbol) newValue).value;
        }//if is symbol
        else if (newValue != null && newValue instanceof Fsymbol) {
            this.value = toValue(((Fsymbol) newValue).value);
        } else //convert to value
        {
            this.value = toValue(newValue);
        }
    }

    /**
     * @param value the value to set
     */
    public void setRawName(String name) {
        this.name = name;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * atribui um nome a variavel verifica se o nome e valido
     */
    public static boolean validadeName(String name) throws FlowchartException {

        if (name.equals(ANONYM_VAR)) { // variaveis anonimas
            return true;
        }

        name = name.trim();
        if (name.isEmpty()) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException("DEFINE.invalidName.empty");
        }
        if (FkeyWord.isReservedWord(name)) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException("DEFINE.invalidName.reservedWord", name);
        }
        char[] ch = name.toCharArray();
        //REGRA 1 - a primeira letra tem de ser uma letra
        if (!Character.isLetter(ch[0])) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException("DEFINE.invalidName.notChar", name, ch[0] + "");
        }
        //testar os restantes caracteres
        for (int i = 1; i < ch.length; i++) {
            //REGRA 2 - a letras seguintes podem ser
            if ( //REGRA 2.1 - Letra do alfabeto
                    !Character.isAlphabetic(ch[i])
                    && //REGRA 2.2 - digito
                    !Character.isDigit(ch[i])) {
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                throw new FlowchartException(
                        "DEFINE.invalidName.notChar",
                        name,
                        ch[i] + "");
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

            }
        }
        return true;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean acceptValue(Object value) {
        try {
            toValue(value);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean isCompatible(Fsymbol other) {
       
            //same type
            if(other.getClass().getSimpleName().equals(getClass().getSimpleName()))
                return true;
            //compatible value
            return acceptValue(other.value);
       
    }

    @Override
    public Object clone() {
        try { // call clone in Object.
            return super.clone();
        } catch (CloneNotSupportedException e) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            FLog.printLn(Fsymbol.class.getName() + " Clone not alowed");
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            return this;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Fsymbol)) return false;
        Fsymbol var = (Fsymbol)other;
        return var.level == level && var.getName().equalsIgnoreCase(name);
    }
    
  
    public boolean equalsValue(Fsymbol var){
        try {
            return var.getValue().toString().equalsIgnoreCase(getValue().toString());
        } catch (FlowchartException ex) {
        }
        return false;
    }
    
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
