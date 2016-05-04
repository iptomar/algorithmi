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
package flowchart.utils;

import core.parser.Mark;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created on 13/set/2015, 21:43:58
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class IteratorArrayIndex implements Iterator<String> {

    ArrayList<String> list;
    Iterator<String> it;

    public IteratorArrayIndex(String expression) {
        buildIndexes(expression);
        it = list.iterator();
    }
    
    public int numElements(){
        return list.size();
    }

    public void buildIndexes(String txt) {
        list = new ArrayList<>();
        int index = txt.indexOf("" + Mark.SQUARE_OPEN);
        if (index < 0) {
            return; // no indexes
        }
        int opens = 1;
        StringBuilder dim = new StringBuilder();
        for (int i = index + 1; i < txt.length(); i++) {
            //dimension complete            
            if (txt.charAt(i) == Mark.SQUARE_CLOSE && opens == 1) {
                list.add(dim.toString());
                dim = null;
                opens = 0;
            } else if (txt.charAt(i) == Mark.SQUARE_CLOSE && opens > 1) {
                opens--;
                dim.append(txt.charAt(i));
            } else if (txt.charAt(i) == Mark.SQUARE_OPEN && opens == 0) {
                opens = 1;
                dim = new StringBuilder();
            } else if (txt.charAt(i) == Mark.SQUARE_OPEN && opens > 0) {
                opens++;
                dim.append(txt.charAt(i));
            } else if (opens > 0){
                dim.append(txt.charAt(i));
            }
        }

    }

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public String next() {
        return it.next();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509132143L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////


}
