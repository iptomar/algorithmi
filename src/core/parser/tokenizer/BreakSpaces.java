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

import core.data.Fcharacter;
import core.data.Ftext;
import core.data.exception.FlowchartException;
import core.parser.Mark;
import flowchart.utils.ExpressionUtils;
import flowchart.utils.ParseText;
import i18n.FkeyWord;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class BreakSpaces {
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * break a string with "" and marks
     *
     * @param txt text string
     * @return array with string string splited by space
     * @throws FlowchartException
     */
    public static List<String> breakSpacesAndMarks(String txt, String marks) {
        try {
            StringTokenizer tok;
            List l = new ArrayList();
            l.add(txt);
            l = execute(l);
            List<String> txtLst = new ArrayList<>(); // break strings \"txt\"  and spaces
            for (Object elem : l) {
                if (elem instanceof Ftext) { // string
                    txtLst.add(((Ftext) elem).getDefinitionValue());
                } else {
                    //break marks
                    tok = new StringTokenizer(elem.toString(), marks, true);
                    while (tok.hasMoreElements()) {
                        txtLst.add(tok.nextToken());
                    }
                }
            }
            return txtLst;
        } catch (FlowchartException ex) {
            List<String> l = new ArrayList();
            l.add(txt);
            return l;
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static List<Object> execute(List<Object> exp) throws FlowchartException {
        List<Object> result = new ArrayList<>();
        for (Object elem : exp) {
            //processar a string
            if (elem instanceof String) {
                String[] txtArray = ParseText.splitBySpace((String) elem);
                for (String txt : txtArray) {
                    //is " " but not only "
                    if (txt.startsWith(Ftext.DELIMITATOR_TEXT + "") && txt.endsWith(Ftext.DELIMITATOR_TEXT + "") && txt.length() > 1) {
                        Ftext t = new Ftext(txt);
                        t.setDescriptor(txt);
                        result.add(t);
                    } //only one "
                    else if (txt.startsWith(Ftext.DELIMITATOR_TEXT + "") || txt.endsWith(Ftext.DELIMITATOR_TEXT + "")) {
                        throw new FlowchartException("DEFINE.invalidTextDefiniton",
                                txt.toString(),
                                ExpressionUtils.toString(exp),
                                Ftext.DELIMITATOR_TEXT + ""
                        );
                    } else //is ' '
                    {
                        if (txt.startsWith(Fcharacter.DELIMITATOR_CHAR + "") && txt.endsWith(Fcharacter.DELIMITATOR_CHAR + "") && txt.length() == 3) {
                            Fcharacter c = new Fcharacter(txt);
                            c.setDescriptor(txt);
                            result.add(c);
                        } else if ((txt.startsWith(Fcharacter.DELIMITATOR_CHAR + "") || txt.endsWith(Fcharacter.DELIMITATOR_CHAR + "")) && txt.length() != 3) {
                            throw new FlowchartException("DEFINE.invalidCharDefiniton",
                                    txt.toString(),
                                    ExpressionUtils.toString(exp),
                                    Fcharacter.DELIMITATOR_CHAR + ""
                            );
                        } else {
                            result.add(txt);
                        }
                    }
                }
            } else {
                //adicionar os outros
                result.add(elem);
            }
        }
        return result;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
