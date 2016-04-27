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

import ui.FLog;
import core.CoreCalculator;
import core.Memory;
import flowchart.utils.TextUtils;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class FkeyWord {
    

    private static String LANG_FILE = "i18n.lang";
    //--------------------------------------------------------------------------
    public static String OPERATOR_SET; // set operator symbol

    static ArrayList<String> dataType;
    static ArrayList<String> words;

    static ResourceBundle messages = null;
    static String language = "pt";
    static String country = "PT";

    static {
        init();
    }

    public static void init() {
        if (messages == null) {
            messages = ResourceBundle.getBundle(LANG_FILE, new Locale(language, country));
            reloadLanguage();
        }

    }

    public static void load(String lang, String land) {
        //do not reload properties
        if (!country.equals(land) || !language.equals(lang)) {
            language = lang;
            country = land;
            messages = ResourceBundle.getBundle(LANG_FILE, new Locale(language, country));
            reloadLanguage();
        }
//        printLog();

    }

    public static final String get(String key) {
        return messages.getString(key).trim();
    }

    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
    public static void reloadLanguage() {
        OPERATOR_SET = messages.getString("OPERATOR.set");

        dataType = new ArrayList<>();
        dataType.add(messages.getString("TYPE.integer"));
        dataType.add(messages.getString("TYPE.real"));
//        dataType.add(messages.getString("TYPE.character"));
        dataType.add(messages.getString("TYPE.string"));
        dataType.add(messages.getString("TYPE.boolean"));

        words = new ArrayList<>();

        words.add(messages.getString("KEYWORD.begin").trim());
        words.add(messages.getString("KEYWORD.end").trim());
        words.add(messages.getString("KEYWORD.define").trim());
        words.add(messages.getString("KEYWORD.execute").trim());
        words.add(messages.getString("KEYWORD.write").trim());
        words.add(messages.getString("KEYWORD.read").trim());

        words.add(messages.getString("KEYWORD.if").trim());
        words.add(messages.getString("KEYWORD.then").trim());
        words.add(messages.getString("KEYWORD.else").trim());

        words.add(messages.getString("KEYWORD.while").trim());
        words.add(messages.getString("KEYWORD.do").trim());

        words.add(messages.getString("KEYWORD.for").trim());
        words.add(messages.getString("KEYWORD.from").trim());
        words.add(messages.getString("KEYWORD.to").trim());
        words.add(messages.getString("KEYWORD.step").trim());

        words.add(messages.getString("KEYWORD.function").trim());
        words.add(messages.getString("KEYWORD.return").trim());

        words.add(messages.getString("KEYWORD.break").trim());
        words.add(messages.getString("KEYWORD.continue").trim());

    }

    public static String[] getDataTypes() {
        String[] txt = new String[dataType.size()];
        return dataType.toArray(txt);
    }

    public static boolean isdataType(String word) {
        return dataType.contains(word);
    }

    public static boolean isWord(String word) {
        return words.contains(word);
    }

    public static boolean isReservedWord(String word) {
        return isdataType(word)
                || isWord(word)
                || Memory.constants.isDefined(word)
                || CoreCalculator.getBySymbol(word) != null;
    }

    public static boolean containsKey(String key) {
        return messages.containsKey(key);
    }

    public static String getSetOperator() {
        return OPERATOR_SET;
    }

    public static String getResources() {
        StringBuilder txt = new StringBuilder(System.lineSeparator());
        // get the keys
        Enumeration<String> enumeration = messages.getKeys();
        // print all the keys
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            txt.append(TextUtils.setSize(key, 50, "_"));
            txt.append(" ");
            txt.append(messages.getString(key));
            txt.append(System.lineSeparator());
        }
        return txt.toString();
    }

    //----------------------------------------------------------------------------------
    public static void printLog() {
        FLog.printSeparator();
        FLog.printLn(" load " + messages.getBaseBundleName());
        FLog.printSeparator();
        FLog.printLn(getResources());
        FLog.printSeparator();
    }
    //----------------------------------------------------------------------------------

    public static void main(String[] args) {
        String key = "KEYWORD.begin";
        System.out.println(" " + FkeyWord.OPERATOR_SET);
        System.out.println(key + "  = " + FkeyWord.get(key));
        load("JAVA", "");
        System.out.println(containsKey(key));
        System.out.println(key + "  = " + FkeyWord.get(key));
        String teste = FkeyWord.get(key);
        teste = teste.replaceFirst("&", "teste");
        System.out.println(teste);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
