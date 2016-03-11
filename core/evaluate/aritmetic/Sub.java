///****************************************************************************/
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
import core.data.exception.FlowchartException;
import core.evaluate.CoreElement;
import static core.evaluate.CoreElement.PRIORITY_ARITMETIC;
import i18n.FkeyWord;
import i18n.FkeywordToken;
import java.util.ArrayList;

/**
 *
 * Created on 7/set/2015, 11:19:08
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class Sub extends CoreElement {

    public static String symbol = FkeyWord.get("OPERATOR.sub");
    public static String help = FkeyWord.get("OPERATOR.sub.help");
    public static String definition = symbol;

    public Sub() {
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

    @Override
    protected Fsymbol execute(Fsymbol[] p) throws FlowchartException {
        if (p[0] instanceof Finteger && p[1] instanceof Finteger) {
            long v = (Long) p[0].getValue() - (Long) p[1].getValue();
            return new Finteger(v);
        }
        if (p[0] instanceof FabstractNumber && p[1] instanceof FabstractNumber) {
            double v = ((FabstractNumber) p[0]).getDoubleValue() - ((FabstractNumber) p[1]).getDoubleValue();
            return new Freal(v);
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
        throw new FlowchartException("OPERATOR.error.typeParams");
    }

    public static void main(String[] args) {
        Sub calc = new Sub();
        try {
            ArrayList<Fsymbol> lst = new ArrayList<>();
//            lst.add( new Freal(2));
//            lst.add( new Finteger(3));
//            lst.add(new Text("\"ola\""));
//            lst.add(new Text("\" mundo\""));
            lst.add(new Flogic(true));
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
    public static String TOKEN = FkeywordToken.get("OPERATOR.sub.key");

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
