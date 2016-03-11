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

import core.CoreToken;
import i18n.FkeyWord;

/**
 * Created on 23/dez/2015, 10:24:27 
 * @author zulu - computer
 */
public class Comments extends CoreToken{
    public static String commentToken = FkeyWord.get("KEYWORD.comments"); 
    
    /**
     * build a comment
     * @param s string
     */
    public Comments(String s ){
        //set comment removing token
        this.setDescriptor(s);
    }
    /**
     * verify if one string contains a comment in the position pos
     * @param s string
     * @param pos position where the comment start
     * @return 
     */
    public static boolean isComment(String s , int pos ){
        if( s.length() < pos + commentToken.length()) return false;
        for (int i = 0; i < commentToken.length(); i++) {
            if( s.charAt(pos+i) != commentToken.charAt(i))
                return false;
        }
        return true;
    }


    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201512231024L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}