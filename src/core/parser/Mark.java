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
package core.parser;

import core.CoreToken;
import i18n.FkeyWord;
import i18n.FkeywordToken;
import java.io.Serializable;

/**
 * Created on 7/set/2015
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class Mark extends CoreToken implements Serializable {

    char specialChar; // char MARK

    public static String COMMA_CHAR_TOKEN = FkeywordToken.get("SEPARATOR.comma.key");
    public static final char COMMA_CHAR = FkeyWord.get("SEPARATOR.comma").charAt(0);

    public static String ROUND_OPEN_TOKEN = FkeywordToken.get("BRACKET.round.open.key");
    public static final char ROUND_OPEN = FkeyWord.get("BRACKET.round.open").charAt(0);

    public static String ROUND_CLOSE_TOKEN = FkeywordToken.get("BRACKET.round.close.key");
    public static final char ROUND_CLOSE = FkeyWord.get("BRACKET.round.close").charAt(0);

    public static String SQUARE_OPEN_TOKEN = FkeywordToken.get("BRACKET.square.open.key");
    public static final char SQUARE_OPEN = FkeyWord.get("BRACKET.square.open").charAt(0);

    public static String SQUARE_CLOSE_TOKEN = FkeywordToken.get("BRACKET.square.close.key");
    public static final char SQUARE_CLOSE = FkeyWord.get("BRACKET.square.close").charAt(0);

    public static String BRACE_OPEN_TOKEN = FkeywordToken.get("BRACKET.brace.open.key");
    public static final char BRACE_OPEN = FkeyWord.get("BRACKET.brace.open").charAt(0);

    public static final String BRACE_CLOSE_TOKEN = FkeywordToken.get("BRACKET.brace.close.key");
    public static final char BRACE_CLOSE = FkeyWord.get("BRACKET.brace.close").charAt(0);

    public static final String BRACKETS = "" + ROUND_OPEN + ROUND_CLOSE + SQUARE_OPEN + SQUARE_CLOSE + BRACE_OPEN + BRACE_CLOSE;

    public static final String ALL_CHAR_MARKS = BRACKETS + COMMA_CHAR;

    public static boolean isMark(char ch) {
        return ALL_CHAR_MARKS.indexOf(ch) >= 0;
    }

    public Mark(char special) {
        this.specialChar = special;
    }

    @Override
    public String toString() {
        return specialChar + "";
    }

    public String getDescriptor() {
        return toString();
    }

    public static boolean isBracket(char ch) {
        return BRACKETS.contains(ch + "");
    }

    public static Mark getBracket(char ch) {
        if (isBracket(ch)) {
            return new Mark(ch);
        }
        return null;
    }

    public boolean match(Mark b) {
        if (specialChar == ROUND_OPEN && b.specialChar == ROUND_CLOSE
                || specialChar == SQUARE_OPEN && b.specialChar == SQUARE_CLOSE
                || specialChar == BRACE_OPEN && b.specialChar == BRACE_CLOSE) {
            return true;
        }
        return false;
    }

    public static boolean bracketsMatch(Object a, Object b) {
        if (!(a instanceof Mark) || !(b instanceof Mark)) {
            return false;
        }
        return ((Mark) a).match((Mark) b);
    }

    public boolean isOpenBracket() {
        if (specialChar == ROUND_OPEN || specialChar == SQUARE_OPEN || specialChar == BRACE_OPEN) {
            return true;
        }
        return false;
    }

    public static boolean isOpenBracket(Object x) {
        return (x instanceof Mark) && ((Mark) x).isOpenBracket();
    }

    public boolean isCloseBracket() {
        if (specialChar == ROUND_CLOSE || specialChar == SQUARE_CLOSE || specialChar == BRACE_CLOSE) {
            return true;
        }
        return false;
    }

    public static boolean isBracket(Object x) {
        return isOpenBracket(x) || isCloseBracket(x);
    }

    public static boolean isCloseBracket(Object x) {
        return (x instanceof Mark) && ((Mark) x).isCloseBracket();
    }

    public static boolean isComma(Object x) {
        return (x instanceof Mark) && ((Mark) x).isComma();
    }

    public boolean isComma() {
        return specialChar == COMMA_CHAR;
    }

    public boolean isSquareOpenBracket() {
        return specialChar == SQUARE_OPEN;
    }

    public boolean isSquareCloseBracket() {
        return specialChar == SQUARE_CLOSE;
    }

    public boolean isRoundOpenBracket() {
        return specialChar == ROUND_OPEN;
    }

    public boolean isRoundCloseBracket() {
        return specialChar == ROUND_CLOSE;
    }

    /**
     * gets toke of the elements
     *
     * @return
     */
    public String getTokenID() {
        if (specialChar == COMMA_CHAR) {
            return COMMA_CHAR_TOKEN;
        }

        if (specialChar == ROUND_OPEN) {
            return ROUND_OPEN_TOKEN;
        }
        if (specialChar == ROUND_CLOSE) {
            return ROUND_CLOSE_TOKEN;
        }

        if (specialChar == SQUARE_OPEN) {
            return SQUARE_OPEN_TOKEN;
        }
        if (specialChar == SQUARE_CLOSE) {
            return SQUARE_CLOSE_TOKEN;
        }

        if (specialChar == BRACE_OPEN) {
            return BRACE_OPEN_TOKEN;
        }
        if (specialChar == BRACE_CLOSE) {
            return BRACE_CLOSE_TOKEN;
        }

        return "MARK ERROR";
    }

    /**
     * gets toke of the elements
     *
     * @return
     */
    public static String getTokenID(String str) {
        char markChar = str.charAt(0);
        if (markChar == COMMA_CHAR) {
            return COMMA_CHAR_TOKEN;
        }

        if (markChar == ROUND_OPEN) {
            return ROUND_OPEN_TOKEN;
        }
        if (markChar == ROUND_CLOSE) {
            return ROUND_CLOSE_TOKEN;
        }

        if (markChar == SQUARE_OPEN) {
            return SQUARE_OPEN_TOKEN;
        }
        if (markChar == SQUARE_CLOSE) {
            return SQUARE_CLOSE_TOKEN;
        }

        if (markChar == BRACE_OPEN) {
            return BRACE_OPEN_TOKEN;
        }
        if (markChar == BRACE_CLOSE) {
            return BRACE_CLOSE_TOKEN;
        }

        return "MARK ERROR";
    }

    /**
     * gets toke of the elements
     *
     * @return
     */
    public static Mark getMark(String token) {
        if (token.equalsIgnoreCase(COMMA_CHAR_TOKEN)) {
            return new Mark(COMMA_CHAR);
        }

        if (token.equalsIgnoreCase(ROUND_OPEN_TOKEN)) {
            return new Mark(ROUND_OPEN);
        }
        if (token.equalsIgnoreCase(ROUND_CLOSE_TOKEN)) {
            return new Mark(ROUND_CLOSE);
        }

        if (token.equalsIgnoreCase(SQUARE_OPEN_TOKEN)) {
            return new Mark(SQUARE_OPEN);
        }
        if (token.equalsIgnoreCase(SQUARE_CLOSE_TOKEN)) {
            return new Mark(SQUARE_CLOSE);
        }

        if (token.equalsIgnoreCase(BRACE_OPEN_TOKEN)) {
            return new Mark(BRACE_OPEN);
        }
        if (token.equalsIgnoreCase(BRACE_CLOSE_TOKEN)) {
            return new Mark(BRACE_CLOSE);
        }

        return null;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071200L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
