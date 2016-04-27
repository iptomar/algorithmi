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
package core.evaluate.function.lang;

import core.data.FabstractNumber;
import core.data.Finteger;
import core.data.Freal;
import core.data.Fsymbol;
import core.data.Ftext;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import core.evaluate.CoreElement;
import core.parser.Mark;
import i18n.FkeyWord;
import i18n.FkeywordToken;
import java.util.ArrayList;

/**
 *
 * Created on 7/set/2015, 11:19:08
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class ElementsOf extends CoreElement {

    public static String symbol = FkeyWord.get("FUNCTION.elementsof");
    public static String help = FkeyWord.get("FUNCTION.elementsof.help");
    public static String definition = FkeyWord.get("FUNCTION.elementsof.definition");

    public ElementsOf() {
        super(1, PRIORITY_FUNCTION);
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
        //---------------------------------------
        if (p[0] instanceof Farray) {
            Farray a = (Farray) p[0];
            return a.getDimensionSymbol(0); // return first dimension
        }
        if (p[0] instanceof Ftext) {
            Ftext t = (Ftext) p[0];
            return new Finteger(t.getTextValue().length()); // return the number of characters
        }
        return new Finteger(1); // one element
    }

    @Override
    protected Fsymbol returnType(Fsymbol[] p) throws FlowchartException {
        //verify the position of the parameters in the expression
        //  function p[0] 
        if (p[0].ID < ID) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::        
            //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException("TOKEN.error.wrongParams");
        }
        //---------------------------------------
        return new Finteger(1); // returns an integer

    }

    public static void main(String[] args) {
        ElementsOf calc = new ElementsOf();
        try {
            ArrayList<Fsymbol> lst = new ArrayList<>();
            lst.add(new Freal(232432423.876));
            //            lst.add( new Int(3));
//            lst.add(new Text("\"ola\""));
//            lst.add(new Text("\" mundo\""));
//            lst.add(new Logic(true));
//            lst.add(new Logic(false));

            Fsymbol res = calc.evaluate(lst);
            System.out.println(calc.getClass().getSimpleName() + " " + res);

        } catch (FlowchartException ex) {
            ex.show("Test " + calc.getClass());
        }
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //TOKEN OF CALCULATOR
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//    public static String TOKEN = FkeywordToken.get("FUNCTION.elementsof.key");

    @Override
    public String getTokenID() {
        return FkeywordToken.get("FUNCTION.elementsof.key");
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071200L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
