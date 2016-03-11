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
package core.evaluate.function.math;

import core.data.FabstractNumber;
import core.data.Finteger;
import core.data.Flogic;
import core.data.Freal;
import core.data.Fsymbol;
import core.data.Ftext;
import core.data.exception.FlowchartException;
import core.evaluate.CoreElement;
import core.parser.Mark;
import i18n.FkeyWord;
import i18n.FkeywordToken;
import java.util.ArrayList;

/**
 * Created on 7/set/2015, 11:19:08
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class Max extends CoreElement {

    public static String symbol = FkeyWord.get("FUNCTION.max");
    public static String help = FkeyWord.get("FUNCTION.max.help");
    public static String definition = FkeyWord.get("FUNCTION.max.definition");

    public Max() {
        super(2, PRIORITY_FUNCTION);
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
        if (p[0] instanceof Finteger && p[1] instanceof Finteger) {
            long p0 = (Long) p[0].getValue();
            long p1 = (Long) p[1].getValue();
            return new Finteger((int) Math.max(p0, p1));
        }
        if (p[0] instanceof FabstractNumber && p[1] instanceof FabstractNumber) {
            double p0 = ((FabstractNumber) p[0]).getDoubleValue();
            double p1 = ((FabstractNumber) p[1]).getDoubleValue();
            return new Freal(Math.max(p0, p1));
        }
        if (p[0] instanceof Ftext && p[1] instanceof Ftext) {
            if (p[0].getTextValue().compareTo(p[1].getTextValue()) > 0) {
                return new Ftext((Ftext) p[0]);
            }
            return new Ftext((Ftext) p[1]);
        }

        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
        //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::::::::::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        throw new FlowchartException("FUNCTION.error.typeParams",
                getSymbol(),
                getSymbol() + Mark.ROUND_OPEN + p[0].getTypeName() + Mark.COMMA_CHAR + p[1].getTypeName() + Mark.ROUND_CLOSE,
                getSymbol() + Mark.ROUND_OPEN + p[0].getDefinitionValue() + Mark.COMMA_CHAR + p[1].getDefinitionValue() + Mark.ROUND_CLOSE
        );
    }

    @Override
    protected Fsymbol returnType(Fsymbol[] p) throws FlowchartException {

//        System.out.println("ID " + ID + "( " + p[0].ID + " , " + p[1].ID);
        //--------------------------------------------------------------------
        //verify the position of the parameters in the expression
        //  function p[0] p[1]
        //--------------------------------------------------------------------
        if (p[0].ID < ID || p[1].ID < p[0].ID) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
            //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException("TOKEN.error.wrongParams");
        }
        //------------------------------------------------------------------------

        //Inteiros
        if (p[0] instanceof Finteger && p[1] instanceof Finteger) {
            return new Finteger(1);
        }
        if (p[0] instanceof FabstractNumber && p[1] instanceof FabstractNumber) {
            return new Freal(1);
        }
        if (p[0] instanceof Ftext && p[1] instanceof Ftext) {
            return new Ftext("\"\"");
        }

        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
        //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::::::::::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        throw new FlowchartException("FUNCTION.error.typeParams",
                getSymbol(),
                getSymbol() + Mark.ROUND_OPEN + p[0].getTypeName() + Mark.COMMA_CHAR + p[1].getTypeName() + Mark.ROUND_CLOSE,
                getSymbol() + Mark.ROUND_OPEN + p[0].getDefinitionValue() + Mark.COMMA_CHAR + p[1].getDefinitionValue() + Mark.ROUND_CLOSE
        );
    }

    public static void main(String[] args) {
        Max calc = new Max();
        try {
            ArrayList<Fsymbol> lst = new ArrayList<>();
            lst.add(new Freal(2));
//            lst.add( new Finteger(3));
//            lst.add(new Text("\"ola\""));
//            lst.add(new Text("\" mundo\""));
//            lst.add(new Flogic(true));
            lst.add(new Flogic(false));

            Fsymbol res = calc.evaluate(lst);
            System.out.println(res);

        } catch (FlowchartException ex) {
            ex.show("Test " + calc.getClass());
        }
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //TOKEN OF CALCULATOR
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String TOKEN = FkeywordToken.get("FUNCTION.max.key");

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
