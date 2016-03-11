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

/**
 *
 * @author zulu
 */
public abstract class FabstractNumber extends Fsymbol {

    public FabstractNumber(Object value) throws FlowchartException {
        super(value);
    }

    public FabstractNumber(String name, Object value, int level) throws FlowchartException {
        super(name, value, level);
    }

    public abstract double getDoubleValue();

    public long getLongValue() {
        return (long) getDoubleValue();
    }

    public int getIntValue() {
        return (int) getDoubleValue();
    }
    
    public static boolean isNumber(String value){
        return Finteger.isValueValid(value) || Freal.isValueValid(value);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
