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
package core;

import core.data.Flogic;
import core.data.Freal;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;

import i18n.FkeyWord;
import flowchart.algorithm.Program;
import ui.flowchart.dialogs.Fdialog;
import i18n.FkeywordToken;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class Memory implements Serializable, Cloneable {

    private String memoryName;
    ArrayList<Fsymbol> memoryArray = new ArrayList<>();

    public Memory(String name) {
        memoryArray = new ArrayList<>();
        this.memoryName = name;
    }

    public Memory(Memory copy) {
        memoryArray = new ArrayList<>();
        for (Fsymbol symb : copy.memoryArray) {
            memoryArray.add(symb);
        }
    }

    public void setMemory(Memory mem) {
        this.memoryArray = mem.memoryArray;
    }

    public Memory getClone() {
        return new Memory(this);
    }

    public ArrayList<Fsymbol> getMem() {
        return memoryArray;
    }

    public void add(Fsymbol var) {
        memoryArray.add(var);
    }

    public void remove(Fsymbol var) {
        memoryArray.remove(var);
    }

    public Fsymbol define(String var, Program prog) {
        try {
            Fsymbol s = Fsymbol.defineSymbol(var, this, prog);
            memoryArray.add(s);
            return s;
        } catch (Exception e) {
            Fdialog.compileException(new FlowchartException(e));
        }
        return null;
    }

    public void clear() {
        memoryArray.clear();
    }

    public void clearLevel(int level) {
        Iterator<Fsymbol> it = memoryArray.iterator();
        while (it.hasNext()) {
            if (it.next().getLevel() >= level) {
                it.remove();
            }
        }
    }

    public int size() {
        return memoryArray.size();
    }

    public boolean isEmpty() {
        return memoryArray.isEmpty();
    }

    public boolean isDefined(String name) {
        for (Fsymbol mem1 : memoryArray) {
            if (mem1.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public Fsymbol getByName(String name) {
        for (int i = memoryArray.size() - 1; i >= 0; i--) {
            if (memoryArray.get(i).getName().equalsIgnoreCase(name)) {
                return memoryArray.get(i);
            }
        }
        return null;
    }
    //-------------------------------------------------------------------------

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::                   C O N S T A N T S                      :::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static Memory constants;

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    static {
        try {
            constants = new Memory(FkeyWord.get("KEYWORD.globalMemoryName"));

            Freal pi = new Freal(FkeyWord.get("CONSTANT.PI"));
            pi.setIsConstant(true);
            pi.setRawName(FkeyWord.get("CONSTANT.PI.name"));
            pi.setComments(FkeyWord.get("CONSTANT.PI.help"));
            constants.add(pi);

            Freal e = new Freal(FkeyWord.get("CONSTANT.E"));
            e.setIsConstant(true);
            e.setRawName(FkeyWord.get("CONSTANT.E.name"));
            e.setComments(FkeyWord.get("CONSTANT.E.help"));
            constants.add(e);

            Flogic v = new Flogic(FkeyWord.get("CONSTANT.true"));
            v.setIsConstant(true);
            v.setRawName(FkeyWord.get("CONSTANT.true.name"));
            v.setComments(FkeyWord.get("CONSTANT.true.help"));
            constants.add(v);

            Flogic f = new Flogic(FkeyWord.get("CONSTANT.false"));
            f.setIsConstant(true);
            f.setRawName(FkeyWord.get("CONSTANT.false.name"));
            f.setComments(FkeyWord.get("CONSTANT.false.help"));
            constants.add(f);

        } catch (FlowchartException ex) {
            ex.show("MEMORY");
            Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    /**
//     * get token of constant
//     * @param varName name of the constant
//     * @return toke
//     */ 
//    public String getConstantToken(String varName) {
//        if (varName.equalsIgnoreCase(FkeyWord.get("CONSTANT.PI.name"))) {
//            return FkeywordToken.get("CONSTANT.PI.key");
//        }
//        if (varName.equalsIgnoreCase(FkeyWord.get("CONSTANT.E.name"))) {
//            return FkeywordToken.get("CONSTANT.E.key");
//        }
//        if (varName.equalsIgnoreCase(FkeyWord.get("CONSTANT.true.name"))) {
//            return FkeywordToken.get("CONSTANT.true.key");
//        }
//        if (varName.equalsIgnoreCase(FkeyWord.get("CONSTANT.false.name"))) {
//            return FkeywordToken.get("CONSTANT.false.key");
//        }
//        return null;
//    }
//    /**
//     * gets the constant symbol of the token
//     * @param token token
//     * @return constant
//     */
//    public Fsymbol getConstant(String token) {
//        if (token.equalsIgnoreCase(FkeywordToken.get("CONSTANT.PI.key"))) {
//            return constants.getByName(FkeyWord.get("CONSTANT.PI.name"));
//        }
//        if (token.equalsIgnoreCase(FkeywordToken.get("CONSTANT.E.key"))) {
//            return constants.getByName(FkeyWord.get("CONSTANT.E.name"));
//        }
//        if (token.equalsIgnoreCase(FkeywordToken.get("CONSTANT.true.key"))) {
//            return constants.getByName(FkeyWord.get("CONSTANT.true.name"));
//        }
//        if (token.equalsIgnoreCase(FkeywordToken.get("CONSTANT.false.key"))) {
//            return constants.getByName(FkeyWord.get("CONSTANT.false.name"));
//        }
//        return null;
//    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::                   C O N S T A N T S                    :::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //----------------------------------------------------------------------------
    public String toString() {
        if (memoryArray.isEmpty()) {
            return memoryName + " <EMPTY>";
        }
        StringBuilder txt = new StringBuilder("\n---------------- " + memoryName + " ----------------\n");
        for (Fsymbol var : memoryArray) {
            txt.append(var.getTypeName() + " ");
            txt.append(var.getName() + " ");
            txt.append(FkeyWord.getSetOperator() + " ");
            txt.append(var.getDefinitionValue() + " ");
            txt.append(var.getLevel() + "\n");

        }
        txt.append("\n==============================================\n");
        return txt.toString().trim();
    }

    //----------------------------------------------------------------------------
    public String toFlatString() {
        if (memoryArray.isEmpty()) {
            return "<EMPTY>";
        }
        StringBuilder txt = new StringBuilder(" name " + memoryName + ":");
        for (Fsymbol var : memoryArray) {
            txt.append("<< ");
            txt.append(var.getTypeName() + " ");
            txt.append(var.getName() + " ");
            txt.append(FkeyWord.getSetOperator() + " ");
            txt.append(var.getDefinitionValue());
            txt.append(" >> ");

        }
        return txt.toString().trim();
    }

    /**
     * @return the memoryName
     */
    public String getMemoryName() {
        return memoryName;
    }

    /**
     * @param memoryName the memoryName to set
     */
    public void setMemoryName(String memoryName) {
        this.memoryName = memoryName;
    }

    private static String defaultVarName = FkeyWord.get("TYPE.defaultVarName");

    public String getnextDefaultVarName() {
        int number = 0;
        for (Fsymbol var : memoryArray) {
            if (var.getName().startsWith(defaultVarName)) {
                try {
                    int value = Integer.parseInt(var.getName().substring(defaultVarName.length()));
                    if (value > number) {
                        number = value;
                    }
                } catch (Exception e) {
                }
            }
        }
        return defaultVarName + (number + 1);

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
