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

import core.CoreCalculator;
import core.data.FabstractNumber;
import core.data.Fcharacter;
import core.data.Ftext;
import core.parser.Mark;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *
 * @author zulu
 */
public class ParseText {

    /**
     * split string to line "\r\n|\r|\n" respect strings and comments
     *
     * @param txt string
     * @return lines of string
     */
    public static String[] splitBySpace(String txt) {
        txt = txt.trim();
        //string vazia
        if (txt.isEmpty()) {
            return new String[]{""};
        }

        ArrayList<String> elems = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        int index = 0;
        boolean inString = false;
        boolean inChar = false;
        char lastChar = ' ';

        while (index < txt.length()) {
            //in char or in string
            if (inString && txt.charAt(index) != Ftext.DELIMITATOR_TEXT
                    || inChar && txt.charAt(index) != Fcharacter.DELIMITATOR_CHAR) {
                word.append(txt.charAt(index++));
            } //TEXT
            else if (txt.charAt(index) == Ftext.DELIMITATOR_TEXT && lastChar != Fcharacter.ESCAPE_CHAR) {
                String elem = word.toString();
                if (inString) {
                    elems.add(elem + txt.charAt(index++)); // append "
                    //new word
                    word = new StringBuilder();
                } else {
                    if (!elem.isEmpty()) {
                        elems.add(elem);
                    }
                    word = new StringBuilder(txt.charAt(index++) + ""); // start new word
                }
                inString = !inString;

            } else if (txt.charAt(index) == Fcharacter.DELIMITATOR_CHAR && lastChar != Fcharacter.ESCAPE_CHAR) {
                String newChar = word.toString();
                if (inChar) {
                    elems.add(newChar + txt.charAt(index++)); // append "
                    //new word
                    word = new StringBuilder();
                } else {
                    if (!newChar.isEmpty()) {
                        elems.add(newChar);
                    }
                    word = new StringBuilder(txt.charAt(index++) + ""); // start new word
                }
                inChar = !inChar;
            } else if (Comments.isComment(txt, index)) {//coments
                String elem = word.toString().trim(); //add last element
                if (!elem.isEmpty()) {
                    elems.add(elem);
                }
                elems.add(txt.substring(index).trim()); // add comment
                break; // finish string processing
            } else if (Character.isSpaceChar(txt.charAt(index))) {//Empty space
                String elem = word.toString().trim();
                if (!elem.isEmpty()) {
                    elems.add(elem);
                }
                //new word
                word = new StringBuilder();
                index++;
            } else {
                word.append(txt.charAt(index++));
            }
            lastChar = txt.charAt(index - 1);
        }
        //--------- fim
        String elem = word.toString().trim();
        if (!elem.isEmpty()) {
            elems.add(elem);
        }

        String[] array = new String[elems.size()];
        return elems.toArray(array);

    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * split string to line "\r\n|\r|\n Coma Parenthesis and operators
     *
     * @param txt string
     * @return lines of string
     */
    public static String[] splitBySpecialChars(String txt) {
        String[] elems = splitBySpace(txt);
        ArrayList<String> result = new ArrayList<>(Arrays.asList(elems));
        result = splitByParenthesis(result); // splite by parenthesis
        result = splitByOperators(result); // split by operators
        String[] array = new String[result.size()];
        return result.toArray(array);
    }

    public static ArrayList<String> splitByParenthesis(ArrayList<String> elems) {
        ArrayList<String> result = new ArrayList<>();

        for (String elem : elems) {
            if (elem.isEmpty()
                    || elem.charAt(0) == Ftext.DELIMITATOR_TEXT
                    || elem.startsWith(Comments.commentToken) // comments
                    ) { // string complete or incomplete
                result.add(elem);
                continue; // next elem
            }
            //break by parentesis and comas
            StringTokenizer tok = new StringTokenizer(elem, Mark.CHAR_MARKS, true);
            while (tok.hasMoreElements()) {
                result.add(tok.nextElement().toString());
            }
        }
        return result;
    }

    public static ArrayList<String> splitByOperators(ArrayList<String> elems) {
        ArrayList<String> result = new ArrayList<>();
        String ops = CoreCalculator.getOperatorCharSet();

        for (String elem : elems) {
            if (elem.isEmpty() || // empty element
                    elem.charAt(0) == Ftext.DELIMITATOR_TEXT // string complete or incomplete
                    || elem.startsWith(Comments.commentToken) // comments
                    || elem.length() == 1 // one char
                    || FabstractNumber.isNumber(elem)) // one number
            {
                result.add(elem);
                continue; // next elem
            }
            //------------------------------------------
            int index = 0; // pointer to char
            String word = ""; // current word
            boolean inOperator = ops.indexOf(elem.charAt(index)) >= 0; // flag to operators
            while (index < elem.length()) { // for all chars

                //add one operator
                if (inOperator && ops.indexOf(elem.charAt(index)) < 0) {//end of operators
                    result.add(word);
                    word = "" + elem.charAt(index);
                    inOperator = false;
                }//add one word
                else if (!inOperator && ops.indexOf(elem.charAt(index)) >= 0) {//start the operator
                    result.add(word);
                    word = "" + elem.charAt(index);
                    inOperator = true;
                } else {
                    //add the current char
                    word += elem.charAt(index);
                }
                index++; // next char
            }
            result.add(word);
        }
        return result;
    }

    public static void main(String[] args) {
        String[] intructions = {
            "\"(2+3)\"*/// -p\"in//teli\" *+ (45)"
        };

        for (String test1 : intructions) {
            String[] elems = splitBySpecialChars(test1);
            System.out.print("\n\n" + test1 + " = \n");
            for (String elem : elems) {
                System.out.print("[" + elem + "]");
            }

        }
    }
}
