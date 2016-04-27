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
package core.evaluate.aritmetic;

import core.data.FabstractNumber;
import core.data.Finteger;
import core.data.Freal;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import core.evaluate.CoreElement;
import static core.evaluate.CoreElement.PRIORITY_ARITMETIC;
import i18n.FkeyWord;
import i18n.FkeywordToken;
import java.util.ArrayList;

/**
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class Div extends CoreElement {

    public static String symbol = FkeyWord.get("OPERATOR.div");
    public static String help = FkeyWord.get("OPERATOR.div.help");
    public static String definition = symbol;

    public Div() {
        super(2, PRIORITY_ARITMETIC + 1);
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String getHelp() {
        return help;
    }

    @Override
    public String getDifinition() {
        return definition;
    }

    @Override
    protected Fsymbol execute(Fsymbol[] p) throws FlowchartException {
        //Inteiros
        if (p[0] instanceof Finteger && p[1] instanceof Finteger) {
            long p0 = (Long) p[0].getValue();
            long p1 = (Long) p[1].getValue();
            if (p1 == 0) {
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
                //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::::::::::
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                throw new FlowchartException("OPERATOR.error.divisionByZero",
                        p[0].getTypeName() + " " + getSymbol() + " " + p[1].getTypeName(),
                        p[0].getDefinitionValue() + " " + getSymbol() + " " + p[1].getDefinitionValue()
                );
            }

            return new Finteger(p0 / p1);
        }
        //reais
        if (p[0] instanceof FabstractNumber && p[1] instanceof FabstractNumber) {
            double p0 = ((FabstractNumber) p[0]).getDoubleValue();
            double p1 = ((FabstractNumber) p[1]).getDoubleValue();
            if (p1 == 0) {
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
                //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::::::::::
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                throw new FlowchartException("OPERATOR.error.divisionByZero",
                        p[0].getTypeName() + " " + getSymbol() + " " + p[1].getTypeName(),
                        p[0].getDefinitionValue() + " " + getSymbol() + " " + p[1].getDefinitionValue()
                );
            }
            return new Freal(p0 / p1);
        }
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
        //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::::::::::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        throw new FlowchartException("OPERATOR.error.typeParams",
                getHelp(),
                p[0].getTypeName() + " " + getSymbol() + " " + p[1].getTypeName(),
                p[0].getDefinitionValue() + " " + getSymbol() + " " + p[1].getDefinitionValue()
        );
    }

    @Override
    protected Fsymbol returnType(Fsymbol[] p) throws FlowchartException {
        //--------------------------------------------------------------------
        //verify the position of the parameters in the expression
        // p[0] op p[1]
        if (p[0].ID > ID || p[1].ID < ID) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
            //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException("TOKEN.error.wrongParams");
        }
        //--------------------------------------------------------------------
        if (p[0] instanceof Finteger && p[1] instanceof Finteger) {
            return new Finteger(1);
        }
        if (p[0] instanceof FabstractNumber && p[1] instanceof FabstractNumber) {
            return new Freal(1);
        }
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
        //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::::::::::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        throw new FlowchartException("OPERATOR.error.typeParams",
                getHelp(),
                p[0].getTypeName() + " " + getSymbol() + " " + p[1].getTypeName(),
                p[0].getDefinitionValue() + " " + getSymbol() + " " + p[1].getDefinitionValue()
        );
    }

  
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //TOKEN OF CALCULATOR
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String TOKEN = FkeywordToken.get("OPERATOR.div.key");

    @Override
    public String getTokenID() {
        return TOKEN;
    }
   //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
