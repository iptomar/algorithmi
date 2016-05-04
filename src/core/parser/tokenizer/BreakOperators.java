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
package core.parser.tokenizer;

import core.Memory;
import core.CoreCalculator;
import core.data.exception.FlowchartException;
import flowchart.utils.ExpressionUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 7/set/2015
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class BreakOperators {

    public static List<Object> execute(List<Object> exp) throws FlowchartException {

      
            List<Object> result = new ArrayList<>();
            for (Object token : exp) {
                if (token instanceof String) {
                    //------------------------------------------------------------------------------------------
                    String txt = token.toString().trim();
                    StringBuilder word = new StringBuilder();
                    boolean inOperator = false;
                    int index = 0;
                    while (index < txt.length()) {
                        if (CoreCalculator.OPERATOR_CHARS.contains(txt.charAt(index) + "")) {
                            //terminou a palavra
                            if (inOperator == false) {
                                String elem = word.toString().trim();
                                if (!elem.isEmpty()) {
                                    result.add(elem);
                                }
                                //new word
                                word = new StringBuilder();
                            }
                            word.append(txt.charAt(index++));
                            inOperator = true;

                        } else {
                            //terminou o operador
                            if (inOperator == true) {
                                String elem = word.toString().trim();
                                //o operador existe
                                if (!elem.isEmpty()) {
                                    SetOfOperators set = new SetOfOperators(elem);
                                    if (!set.isValid()) {
                                        throw new FlowchartException("DEFINE.invalidSymbol", 
                                                elem,
                                                ExpressionUtils.toString(exp)
                                        );
                                    }
                                    result.add(new SetOfOperators(elem));
                                }
                                //new word
                                word = new StringBuilder();
                            }
                            word.append(txt.charAt(index++));
                            inOperator = false;
                        }
                    }
                    //--------- fim -
                    String elem = word.toString().trim();
                    if (!elem.isEmpty()) {
                        if (inOperator) {
                            result.add(new SetOfOperators(elem));
                        } else {
                            result.add(elem);
                        }
                    }
                    //------------------------------------------------------------------------------------------

                } else {
                    result.add(token);
                }
            }
            return result;
       
    }

   
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071200L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
