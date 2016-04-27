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
package core.evaluate;

import core.CoreToken;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import java.io.Serializable;

import java.util.List;
import ui.FLog;

/**
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public abstract class CoreElement extends CoreToken  {

    public static int PRIORITY_LOGIC = 10;
    public static int PRIORITY_RELATIONAL = 20;
    public static int PRIORITY_ARITMETIC = 30;
    public static int PRIORITY_FUNCTION = 40;
    /**
     * numero de parametros do operador
     */
    private int nParams;
    /**
     * prioridade do operador
     */
    private int priority;

    public CoreElement(int nParams, int priority) {
        this.nParams = nParams;
        this.priority = priority;
    }

    public abstract String getSymbol();
    

    public abstract String getHelp();

    public abstract String getDifinition();

    protected abstract Fsymbol execute(Fsymbol[] params) throws FlowchartException;

    protected abstract Fsymbol returnType(Fsymbol[] params) throws FlowchartException;

    public Fsymbol evaluate(List<Fsymbol> params) throws FlowchartException {
        if (params.size() != getNumberOfParameters()) {
            throw new FlowchartException(
                    "OPERATOR.error.numParams",
                    getSymbol(),
                    "" + getNumberOfParameters(),
                    "" + params.size());
        }
        Fsymbol[] p = new Fsymbol[getNumberOfParameters()];
        Fsymbol ret = execute(params.toArray(p));
        ret.ID = ID; // set ID of symbol the ID of Calculator
        return ret;
    }

    public Fsymbol returnType(List<Fsymbol> params) throws FlowchartException {
        if (params.size() != getNumberOfParameters()) {
            throw new FlowchartException(
                    "OPERATOR.error.numParams",
                    getSymbol(),
                    "" + getNumberOfParameters(),
                    "" + params.size());
        }
        Fsymbol[] p = new Fsymbol[getNumberOfParameters()];
        Fsymbol ret = returnType(params.toArray(p));
        ret.ID = ID; // set ID of symbol the ID of Calculator
        return ret;
    }

    public String toString() {      
        return getSymbol();
    }

    /**
     * @return the nParams
     */
    public int getNumberOfParameters() {
        return nParams;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    public String getDescriptor() {
        return getSymbol();
    }

    @Override
    public Object clone() {
        try { // call clone in Object.
            return super.clone();
        } catch (CloneNotSupportedException e) {
            FLog.printLn(" Cloning not allowed. ");
            return this;
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
