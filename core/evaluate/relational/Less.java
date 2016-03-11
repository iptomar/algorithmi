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
package core.evaluate.relational;


import core.data.FabstractNumber;
import core.data.Fcharacter;
import core.data.Finteger;
import core.data.Flogic;
import core.data.Freal;
import core.data.Fsymbol;
import core.data.Ftext;
import core.data.exception.FlowchartException;
import core.evaluate.CoreElement;
import i18n.FkeyWord;
import i18n.FkeywordToken;
import java.util.ArrayList;

/**
 * Created on 7/set/2015
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class Less extends CoreElement {

    public static String symbol = FkeyWord.get("OPERATOR.less");
    public static String help = FkeyWord.get("OPERATOR.less.help");
    public static String definition = symbol;

    public Less() {
        super(2, PRIORITY_RELATIONAL + 1);
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
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
        //String log = p[0].getTextValue() + " " + symbol + " " + p[1].getTextValue() + " = ";
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG

        if (p[0] instanceof Fcharacter && p[1] instanceof Fcharacter) {
            Boolean v = ((Character) p[0].getValue()).compareTo((Character) p[1].getValue()) < 0;
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            //Log.printLn(log + v);
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            return new Flogic(v);
        }
        if (p[0] instanceof Ftext && p[1] instanceof Ftext) {
            Boolean v = ((String) p[0].getValue()).compareTo((String) p[1].getValue()) < 0;
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            //Log.printLn(log + v);
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            return new Flogic(v);
        }

        if (p[0] instanceof FabstractNumber && p[1] instanceof FabstractNumber) {
            Boolean v = ((FabstractNumber) p[0]).getDoubleValue() < ((FabstractNumber) p[1]).getDoubleValue();
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            //Log.printLn(log + v);
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            return new Flogic(v);
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
        if (p[0] instanceof Fcharacter && p[1] instanceof Fcharacter) {
            return new Flogic(true);
        }
        if (p[0] instanceof Ftext && p[1] instanceof Ftext) {
            return new Flogic(true);
        }

        if (p[0] instanceof FabstractNumber && p[1] instanceof FabstractNumber) {
            return new Flogic(true);
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

    public static void main(String[] args) {
        Less calc = new Less();
        try {
            ArrayList<Fsymbol> lst = new ArrayList<>();
            lst.add(new Freal(3.0));
            lst.add(new Finteger(3));
//            lst.add(new Ftext("\"ola\""));
//            lst.add(new Ftext("\" mundo\""));
//            lst.add(new Flogic(true));
//            lst.add(new Flogic(false));

            Fsymbol res = calc.evaluate(lst);
            System.out.println(res);

        } catch (FlowchartException ex) {
            ex.show("Test " + calc.getClass());
        }
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //TOKEN OF CALCULATOR
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String TOKEN = FkeywordToken.get("OPERATOR.less.key");

    @Override
    public String getTokenID() {
        return TOKEN;
    }
   //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071200L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
