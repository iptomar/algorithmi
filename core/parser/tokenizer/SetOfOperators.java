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

import core.evaluate.CoreElement;
import core.CoreCalculator;
import core.data.exception.FlowchartException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class SetOfOperators {

    String oper;
    public List<List<CoreElement>> listOfOperators;

    public SetOfOperators(String oper) {
        this.oper = oper;
        build();
    }
    public boolean isValid(){
        //first list
        return !listOfOperators.get(0).isEmpty();
    }

    public void build(){
        listOfOperators = new ArrayList<>();
        List<CoreElement> start = new ArrayList<>();
        listOfOperators.add(start);
        processString(0, start);
    }

    public void processString(int index, List<CoreElement> list){
        //stop recusivity
        if (index >= oper.length()) {
            return;
        }

        List<CoreElement> op = CoreCalculator.getOperatorsStartedWith(oper.substring(index));
       //no Operator
        if (op.isEmpty()) {
            listOfOperators.remove(list);
            return;
        }
        for (int i = 1; i < op.size(); i++) {
            List<CoreElement> clone = getClone(list);
            listOfOperators.add(clone);
            CoreElement operator = op.get(i);
            clone.add(operator);
            processString(index + operator.getSymbol().length(), clone);
        }
        //process first
        list.add(op.get(0));
        processString(index + op.get(0).getSymbol().length(), list);

    }

    public List<CoreElement> getClone(List<CoreElement> l) {
        List<CoreElement> clone = new ArrayList<>();
        for (CoreElement op : l) {
            clone.add(op);
        }
        return clone;
    }

    public void print() {
        for (List<CoreElement> operators : listOfOperators) {
            System.out.print("\n ");
            for (CoreElement op : operators) {
                System.out.print(" [" + op.getHelp() + "]");
            }
        }
    }

    public String toString() {
       return oper;
    }

    public static void main(String[] args) {
        SetOfOperators s = new SetOfOperators("!>=-");
        s.print();
        System.out.println(s);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
