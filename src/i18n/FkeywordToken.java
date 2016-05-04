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
package i18n;

import core.data.Ftext;
import core.parser.Mark;
import core.parser.tokenizer.BreakSpaces;
import java.util.ArrayList;
import ui.FLog;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class FkeywordToken {

    private static String LANG_FILE = "i18n.lang_Keys";
    public static String OPERATOR_SET_KEY = "";
    //--------------------------------------------------------------------------
    public static HashMap<String, String> token_word = new HashMap<>();
    public static HashMap<String, String> word_Token = new HashMap<>();
    //--------------------------------------------------------------------------
    static ResourceBundle keys;

    static {
        init();
    }

    public static void init() {
        keys = ResourceBundle.getBundle(LANG_FILE);
        OPERATOR_SET_KEY = keys.getString("OPERATOR.set.key");
        // get the keys
        Enumeration<String> enumeration = keys.getKeys();
        String key, token, word;
        // print all the keys
        while (enumeration.hasMoreElements()) {
            key = enumeration.nextElement();
            token = keys.getString(key);;
            //remove the word ".key"
            String newKey = key.substring(0, key.lastIndexOf(".key"));
            word = FkeyWord.get(newKey);
            //get word for native language
            token_word.put(token, word);
            word_Token.put(word, token);
          //  System.out.println(key + " \t" + word + " \t" + token);
        }
    }

    public static final String get(String key) {
        return keys.getString(key).trim();
    }

    public static final String getTokenOfWord(String word) {
        return word_Token.get(word.trim());
    }

    public static final String getWordOfToken(String token) {
        return token_word.get(token.trim());
    }

    //----------------------------------------------------------------------------------
    public static void printLog() {
        FLog.printSeparator();
        FLog.printLn(" load KEYS " + keys.getBaseBundleName());
    }
    //----------------------------------------------------------------------------------

    /**
     * translate one expression in tokens language to natural language 
     *
     * @param tokens expression in tokens language
     * @return natural language 
     */
    public static String translateTokensToWords(String tokens) {
        StringBuilder txt = new StringBuilder();
        for (String elem : tokens.split(" ")) {
            if (token_word.get(elem) != null) {
                txt.append(token_word.get(elem) + " ");
            } else {
                txt.append(elem + " ");
            }
        }
        return txt.toString();
    }

    /**
     * translate one expression in natural language to tokens language
     *
     * @param words expression in natural language
     * @return tokens language
     */
    public static String translateWordsToTokens(String words) {
        List<String> mylst = BreakSpaces.breakSpacesAndMarks(words, Mark.ALL_CHAR_MARKS);
        StringBuilder txt = new StringBuilder();
        for (String elem : mylst) {
            if (word_Token.get(elem) != null) {
                txt.append(word_Token.get(elem) + " ");
            } else {
                txt.append(elem + " ");
            }
        }
        return txt.toString();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
