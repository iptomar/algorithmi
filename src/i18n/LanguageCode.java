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
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package i18n;

import java.util.Locale;

/**
 * Created on 26/abr/2016, 14:04:42
 *
 * @author zulu - computer
 */
public class LanguageCode implements Comparable<Object> {

    String country = "PT";
    String language = "pt";

    public String getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public LanguageCode(String fileName) {
        if (fileName.contains(".properties")) {
            fileName = fileName.substring(0, fileName.lastIndexOf(".properties"));
        }
        if (fileName.contains("_")) {
            country = fileName.substring(fileName.lastIndexOf("_") + 1);
            fileName = fileName.substring(0, fileName.lastIndexOf("_"));
        }
        if (fileName.contains("_")) {
            language = fileName = fileName.substring(fileName.lastIndexOf("_") + 1);
        }
    }

    public String getLanguageCountry() {
        Locale l = new Locale(language, country);
        return l.getDisplayLanguage(l) + " " + l.getDisplayCountry(l);
    }
    
    public String toString() {
        return getLanguageCountry();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604261404L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public int compareTo(Object o) {
        LanguageCode l = (LanguageCode) o;
        return country.compareTo(l.country) + language.compareTo(l.language);
    }
}
