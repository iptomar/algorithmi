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
package core.evaluate.function.text;

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
import java.util.Locale;

/**
 * Created on 7/set/2015, 11:19:08
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class Align extends CoreElement {

    public static String symbol = FkeyWord.get("FUNCTION.align");
    public static String help = FkeyWord.get("FUNCTION.align.help");
    public static String definition = FkeyWord.get("FUNCTION.align.definition");

    public Align() {
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
        if (p[1] instanceof Finteger) {
            String txt = p[0].getTextValue();
            int size = ((Long) p[1].getValue()).intValue();
            if ((p[0] instanceof Freal)) {
                txt = String.format(Locale.US, "%." + Math.abs(size) + "f", ((Freal) p[0]).getDoubleValue());
            }

            if (txt.length() > Math.abs(size)) {
                txt = txt.substring(0, Math.abs(size));
            } else {
                int spaces = Math.abs(size) - txt.length();
                StringBuilder txtSpaces = new StringBuilder(spaces);
                for (int i = 0; i < spaces; i++) {
                    txtSpaces.append(' ');
                }
                if (size < 0) {
                    txt = txt + txtSpaces;
                } else {
                    txt = txtSpaces + txt;
                }
            }
            return new Ftext(Ftext.addTerminators(txt));
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

        //the second must be an integer
        if (p[1] instanceof Finteger) {
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

  

        //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //TOKEN OF CALCULATOR
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String TOKEN = FkeywordToken.get("FUNCTION.align.key");

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
