/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languages;

import core.parser.Expression;
import flowchart.read.Read;
import flowchart.shape.Fshape;
import flowchart.terminator.Begin;
import flowchart.terminator.End;
import static languages.PseudoLanguage.IDENTATATION_SPACE;

/**
 *
 * @author Xavier
 */
public abstract class AbstractLang {
    
    public static AbstractLang lang = new JavaLang();
    
    
    public abstract String getStartOfProgram(Fshape shape);
    public abstract String getEndOfProgram(Fshape shape);
    
    public abstract String getBeginProgram(Fshape shape);
    public abstract String getEndProgram(Fshape shape);
    public abstract String getEnd(Fshape shape);
    public abstract String getRead(Fshape shape);
    public abstract String getWrite(Fshape shape);
    public abstract String getIf(Fshape shape);
    public abstract String getElse(Fshape shape);
    public abstract String getDefine(Fshape shape);
    public abstract String getExecute(Fshape shape);
    public abstract String getWhile(Fshape shape);
    public abstract String getDo(Fshape shape);
    public abstract String getDoWhile(Fshape shape);
    public abstract String getFor(Fshape shape);
    public abstract String getFunction(Fshape shape);
    public abstract String getReturn(Fshape shape);
    
    public abstract String getCommentedString(String txt,Fshape shape);
    public abstract String getExpression(Expression ex);

    public static int DEFAULT_IDENTATATION = 1;
    public static int IDENTATATION_SPACE = 4;
    private static String SPACE="   ";
    
    public static String ident(Fshape s) {
        int level = s.level;
        if ((s instanceof Begin) || (s instanceof End) ) {
            level--;
        }
        StringBuilder txt = new StringBuilder(IDENTATATION_SPACE * (s.level+DEFAULT_IDENTATATION));
        for (int i = 0; i < level+DEFAULT_IDENTATATION; i++) {
            txt.append(SPACE);

        }
        return txt.toString();
    }  
    
}
