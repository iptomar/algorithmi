/*
 * Copyright (c) 2015 Instituto Politecnico de Tomar. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */
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
import core.data.Fsymbol;
import core.evaluate.CoreElement;
import core.CoreCalculator;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class ReplaceSymbols {

    public static List<Object> execute(List<Object> exp, Memory mem) throws FlowchartException {
        List<Object> result = new ArrayList<>();
        for (Object token : exp) {
            if (token instanceof String) {
                String txt = ((String) token).trim();
                //limpar os vazios
                if (txt.isEmpty()) {
                    continue;
                }
                //symbolo do token
                Fsymbol symbol;
                //------------------------------------------------------------------------------------------
                // MEMORIA
                //------------------------------------------------------------------------------------------
                //tentar fazer uma variavel com o nome
                if (mem != null) {
                    //verificar o nome
                    symbol = (Fsymbol) mem.getByName(txt);
                    if (symbol != null) { // Full array ( not elements)
                        symbol = (Fsymbol) symbol.clone();  //clone symbol                              
                        if (symbol instanceof Farray) {
                            Farray a = (Farray) symbol;
                            a.indexExpression.clear();
                        }
                        symbol.setDescriptor(txt); //name of var
                        result.add(symbol);
                        continue;
                    }
                }
                //------------------------------------------------------------------------------------------
                // VALORES
                //------------------------------------------------------------------------------------------
                //tentar fazer uma variavel com o valor
                symbol = Fsymbol.createByValue(txt);
                //adicionar o simbolo
                if (symbol != null) {
                    symbol.setDescriptor(txt); //value
                    result.add(symbol);
                    continue;
                }
                //------------------------------------------------------------------------------------------
                // CONSTANTES
                //------------------------------------------------------------------------------------------
                //tentar fazer uma constante
                symbol = Memory.constants.getByName(txt);
                //adicionara constante
                if (symbol != null) {
                    symbol.setDescriptor(txt); //name
                    result.add((Fsymbol) symbol.clone());
                    continue;
                } //adicionar a string
                //------------------------------------------------------------------------------------------
                // FUNCAO
                //------------------------------------------------------------------------------------------
                //tentar fazer uma funcao                    
                CoreElement func = CoreCalculator.getBySymbol(txt);
                if (func != null) {
                    result.add(func.clone());
                    continue;
                }
                //adicionar como string
                result.add(token);

            } else {
                result.add(token);
            }
        }
        return result;
    }

//    public static void main(String[] args) {
//        String s = " _PI + _E*(2 + x[3] * 4.5 + {1E3 , 4.5E-3 , 3.5E3.4 } \"ola]\" + 'x' '\\n}' && (falso)";
//        List<Object> exp = new ArrayList<>();
//        exp.add(s);
//        exp = BreakSpaces.execute(exp);
//        exp = BreakMarks.execute(exp);
//        exp = ReplaceSymbols.execute(exp, new Memory());
//        for (Object elem : exp) {
//            System.out.println(elem.getClass().getSimpleName() + " \t " + elem.toString());
//        }
//
//    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
