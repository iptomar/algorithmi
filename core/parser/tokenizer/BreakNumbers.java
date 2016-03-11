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

import core.data.Freal;
import core.data.exception.FlowchartException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * separa os numeros reais de uma expressao
 *
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class BreakNumbers {

    //protected static Pattern realRegex = Pattern.compile("[-+]?[0-9]+(.)?+[0-9]+([eE][-+]?[0-9]+)?");
   // nao incluir o + e - porque podem ser operadores
    protected static Pattern realRegex = Pattern.compile("[0-9]*\\.[0-9]+([eE][-+]?[0-9]+)?");
    // (.)? pode ter um ponto 
    // [0-9]+ seguido de pelo menos um digito
    // ([eE][-+]?[0-9]+)? pode ter um E e um inteiro

    /**
     * parte a expressao nos numeros reais nos elementos do tipo STRING Os
     * elementos do tipo string ainda nao foram processados
     *
     * @param expression Arrays de elementos da express?o
     * @throws core.data.exception.FlowchartException
     * @returnArray de elementos com elementos do tipo Real
     */
    public static List<Object> execute(List<Object> expression) throws FlowchartException{
        List<Object> lst = new ArrayList<>();
        Matcher matcher;
        int begin, end;
        for (Object element : expression) {
            //se nao for uma string continua para o proximo
            if (!(element instanceof String)) {
                lst.add(element);
                continue;
            }
            //----------------------------------------------------------------
            // extrair numeros reais
            //-----------------------------------------------------------------
            String exp = (String) element.toString().trim();
            matcher = realRegex.matcher(exp);
            begin = 0;
            end = exp.length();
            while (matcher.find()) {
                //adicionar o que fica antes
                if (begin != matcher.start()) {
                    //adicionar como String o fica antes do numero
                    lst.add(exp.substring(begin, matcher.start()));
                }
                try {
                    //adicionar o elemento do tipo REAL
                    String txtNum = exp.substring(matcher.start(), matcher.end());
                    Freal num = new Freal(txtNum);
                    num.setDescriptor(txtNum);
                    lst.add(num);

                } catch (Exception e) {
                    //nao e do tipo ral
                    lst.add(exp.substring(matcher.start(), matcher.end()));
                }
                //actualizar o ponteiro
                begin = matcher.end();
            }
            //adicionar o que fica no fim
            if (begin != end) {
                lst.add(exp.substring(begin, end));
            }

        } //next element of the list
        return lst;
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
