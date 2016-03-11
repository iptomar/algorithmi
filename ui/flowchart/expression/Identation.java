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
package ui.flowchart.expression;

import core.CoreCalculator;
import core.CoreToken;
import core.Memory;
import core.data.Fsymbol;
import core.parser.Mark;
import flowchart.algorithm.Program;
import flowchart.utils.Comments;
import flowchart.utils.ParseText;
import i18n.FkeyWord;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 29/nov/2015, 3:56:37
 *
 * @author zulu - computer
 */
public class Identation {

    public static String ident(String txt, Memory memory, Program program) {
        List tok = tokenizer(txt, memory, program);       
        StringBuilder exp = new StringBuilder();
        for (int i = 0; i < tok.size(); i++) {
            exp.append(getText(tok.get(i)));
            if (!Mark.isBracket(tok.get(i)) && i < tok.size() - 1 && !Mark.isBracket(tok.get(i + 1))) {
                exp.append(" ");
            }
        }
        return exp.toString();
    }

    public static String getText(Object elem) {
        if (FkeyWord.isReservedWord(elem.toString())) {
            return elem.toString();
        }
        if (elem instanceof CoreToken) {
            return ((CoreToken) elem).getDescriptor();
        }
        return elem.toString();
    }

    public static List<Object> tokenizer(String exp, Memory memory, Program program) {
        List<Object> result = new ArrayList<>();
        String[] txtArray = ParseText.splitBySpecialChars( exp);
        for (String txt : txtArray) {
            try {
                if (txt.startsWith(Comments.commentToken)) { // comments                    
                    result.add(new Comments(txt));
                }
                else if (memory != null && memory.getByName(txt) != null) { // variable
                    Fsymbol s = memory.getByName(txt);
                    s.setDescriptor(txt);
                    result.add(s);
                } else if (Fsymbol.createByValue(txt) != null) { // operators and functions                    
                    result.add(Fsymbol.createByValue(txt));
                } else if (CoreCalculator.getBySymbol(txt) != null) { // operators and functions
                    result.add(CoreCalculator.getBySymbol(txt));
                }else if (Mark.isMark(txt.charAt(0))) { // parenthesis and comma
                    result.add(new Mark(txt.charAt(0)));
                } else {
                    result.add(txt);
                }
            } catch (Exception e) {
                result.add(txt);
            }
        }
        return result;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201511290356L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
   
}
