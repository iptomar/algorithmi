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

import java.io.Serializable;

/**
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class CoreToken implements  Cloneable, Serializable {

    //Position in the expression
    public int ID = -1; //used in expressions to identify the position the token int the inordem version
    //text of token
    private String descriptor = "";

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescriptor() {
        return descriptor;
    }
    
    /**
     * gets toke of the elements 
     * @return 
     */
    public String getTokenID(){
        return descriptor;
    }
    
   

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor.trim();
    }
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
