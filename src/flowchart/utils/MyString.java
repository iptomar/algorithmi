//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 5/out/2015, 6:32:00
 *
 * @author zulu - computer
 */
public class MyString {

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

    /**
     * Align the string to the left inserting spaces in the end
     *
     * @param text string to align
     * @param len lenght of the string
     * @return string with spaces in the end
     */
    public static String setSize(String text, int len) {
        return String.format("%-" + len + "s", text);
    }


    /**
     * Align a number to the center
     *
     * @param text text to align
     * @param len lenght of the string
     * @return string in the center
     */
    public static String center(String text, int len) {
        String out = String.format("%" + len + "s%s%" + len + "s", "", text, "");
        float mid = (out.length() / 2);
        float start = mid - (len / 2);
        float end = start + len;
        return out.substring((int) start, (int) end);
    }

    /**
     * Align a number to the center
     *
     * @param text text to align
     * @param len lenght of the string
     * @return string in the center
     */
    public static String center(String text, int len, char fill) {
        StringBuilder out = new StringBuilder(center(text, len));
        for (int i = 0; i < out.length() && out.charAt(i) == ' '; i++) {
            out.setCharAt(i, fill);
        }
        for (int i = out.length() - 1; i >= 0 && out.charAt(i) == ' '; i--) {
            out.setCharAt(i, fill);
        }
        return out.toString();
    }

    public static String toString(Date d) {
        return dateFormat.format(d);
    }

    public static String toFileString(String txt) {
        return txt.replaceAll("\n", "\r\n");
    }

    public static String[] splitByWhite(String txt) {
        return txt.trim().split("\\s+");
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510050632L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
