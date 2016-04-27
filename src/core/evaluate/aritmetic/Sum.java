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
import core.data.Flogic;
import core.data.Freal;
import core.data.Fsymbol;
import core.data.Ftext;
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
public class Sum extends CoreElement {

    public static String symbol = FkeyWord.get("OPERATOR.sum");
    public static String help = FkeyWord.get("OPERATOR.sum.help");
    public static String definition = symbol;

    public Sum() {
        super(2, PRIORITY_ARITMETIC);
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
     
    
    public Fsymbol add(Fsymbol n1, Fsymbol n2)throws FlowchartException{
        return execute( new Fsymbol[]{n1,n2});
    }

    @Override
    protected Fsymbol execute(Fsymbol[] p) throws FlowchartException {
        //Inteiros
        if (p[0] instanceof Finteger && p[1] instanceof Finteger) {
            long v = (Long) p[0].getValue() + (Long) p[1].getValue();
            return new Finteger(v);
        }
        //reais e inteiros
        if (p[0] instanceof FabstractNumber && p[1] instanceof FabstractNumber) {
            double v = ((FabstractNumber) p[0]).getDoubleValue() + ((FabstractNumber) p[1]).getDoubleValue();
            return new Freal(v);
        }
        //Texto
        if (p[0] instanceof Ftext || p[1] instanceof Ftext) {
            String txt = p[0].getTextValue() + p[1].getTextValue();
            return new Ftext(Ftext.DELIMITATOR_TEXT + txt + Ftext.DELIMITATOR_TEXT);
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
        if (p[0].ID > ID || p[1].ID < ID) {
         //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
         //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::::::::::
         //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException("TOKEN.error.wrongParams");
        }
        //Inteiros
        if (p[0] instanceof Finteger && p[1] instanceof Finteger) {
            return new Finteger(1);
        }
        if (p[0] instanceof FabstractNumber && p[1] instanceof FabstractNumber) {
            return new Freal(1);
        }
        if (p[0] instanceof Ftext || p[1] instanceof Ftext) {
            return new Ftext(Ftext.DELIMITATOR_TEXT + "" + Ftext.DELIMITATOR_TEXT);
        }
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
        //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::::::::::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        throw new FlowchartException("OPERATOR.error.typeParams");
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //TOKEN OF CALCULATOR
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String TOKEN = FkeywordToken.get("OPERATOR.sum.key");

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

