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
package languages;

import flowchart.shape.Fshape;
import flowchart.terminator.Begin;
import flowchart.terminator.End;

/**
 * Created on 6/set/2015, 12:51:07
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class PseudoLanguage {

    static int IDENTATATION_SPACE = 4;
    private static String SPACE;

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    static {
        StringBuilder txt = new StringBuilder(IDENTATATION_SPACE);
        for (int i = 0; i < IDENTATATION_SPACE; i++) {
            txt.append(" ");
        }
        SPACE = txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::  

    public static String ident(Fshape s) {
        int level = s.level;
        if ((s instanceof Begin) || (s instanceof End) ) {
            level--;
        }
        StringBuilder txt = new StringBuilder(IDENTATATION_SPACE * (s.level+1));
        for (int i = 0; i < level+1; i++) {
            txt.append(SPACE);

        }
        return txt.toString();
    }
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::::::
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
}
