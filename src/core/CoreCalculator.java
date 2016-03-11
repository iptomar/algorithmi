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
package core;

import core.evaluate.CoreElement;
import core.evaluate.logic.And;
import core.evaluate.logic.Not;
import core.evaluate.logic.Or;
import core.evaluate.aritmetic.Sum;
import core.evaluate.aritmetic.Div;
import core.evaluate.aritmetic.Mod;
import core.evaluate.aritmetic.Mult;
import core.evaluate.aritmetic.Negative;
import core.evaluate.aritmetic.Power;
import core.evaluate.aritmetic.Sub;
import core.evaluate.function.trigonometry.ACos;
import core.evaluate.function.trigonometry.ASin;
import core.evaluate.function.trigonometry.ATan;
import core.evaluate.function.trigonometry.Abs;
import core.evaluate.function.trigonometry.Cos;
import core.evaluate.function.trigonometry.CosH;
import core.evaluate.function.math.Exp;
import core.evaluate.function.Int;
import core.evaluate.function.math.Ln;
import core.evaluate.function.math.Log;
import core.evaluate.function.math.Max;
import core.evaluate.function.math.Min;
import core.evaluate.function.math.Pow;
import core.evaluate.function.math.Random;
import core.evaluate.function.math.Round;
import core.evaluate.function.trigonometry.Sin;
import core.evaluate.function.trigonometry.SinH;
import core.evaluate.function.math.Sqrt;
import core.evaluate.function.text.Align;
import core.evaluate.function.trigonometry.Tan;
import core.evaluate.function.trigonometry.TanH;
import core.evaluate.relational.Different;
import core.evaluate.relational.Equal;
import core.evaluate.relational.Greater;
import core.evaluate.relational.GreaterOrEqual;
import core.evaluate.relational.Less;
import core.evaluate.relational.LessOrEqual;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class CoreCalculator {

    public static String OPERATOR_CHARS;// char of operators
    public final static List<CoreElement> operators = new ArrayList<>();
    public final static List<CoreElement> functions = new ArrayList<>();

    static {
        operators.add(new Negative());
        operators.add(new Sum());
        operators.add(new Sub());
        operators.add(new Mult());
        operators.add(new Mod());
        operators.add(new Div());
        operators.add(new Power());

        operators.add(new Different());
        operators.add(new Equal());
        operators.add(new Greater());
        operators.add(new GreaterOrEqual());
        operators.add(new Less());
        operators.add(new LessOrEqual());

        operators.add(new Not());
        operators.add(new And());
        operators.add(new Or());
        //------------------------ char of operators ---
        OPERATOR_CHARS = getOperatorCharSet();
        

        functions.add(new Abs());
        functions.add(new Random());
        functions.add(new Pow());
        functions.add(new Exp());
        functions.add(new Sqrt());
        functions.add(new Round());
        functions.add(new Int());
        functions.add(new Log());
        functions.add(new Ln());

        functions.add(new Min());
        functions.add(new Max());

        functions.add(new Sin());
        functions.add(new Cos());
        functions.add(new Tan());
        functions.add(new ASin());
        functions.add(new ACos());
        functions.add(new ATan());
        functions.add(new SinH());
        functions.add(new CosH());
        functions.add(new TanH());
        
        functions.add(new Align());

    }

    public static CoreElement getBySymbol(String symbol) {
        for (CoreElement operator : operators) {
            if (operator.getSymbol().equalsIgnoreCase(symbol)) {
                return operator;
            }
        }
        for (CoreElement func : functions) {
            if (func.getSymbol().equalsIgnoreCase(symbol)) {
                return func;
            }
        }
        return null;
    }

    public static String getOperatorCharSet() {
        Set<Character> set = new TreeSet();
        for (CoreElement c : operators) {
            for (int i = 0; i < c.getSymbol().length(); i++) {
                set.add(c.getSymbol().charAt(i));
            }
        }
        StringBuilder txt = new StringBuilder();
        for (Character s : set) {
            txt.append(s);
        }
        return txt.toString();
    }

    public static List<CoreElement> getOperatorsStartedWith(String txt) {
        ArrayList<CoreElement> list = new ArrayList<>();
        for (CoreElement op : operators) {
            //verify chars
            String symbol = op.getSymbol();
            boolean match = true;
            for (int i = 0; i < symbol.length(); i++) {
                if (i >= txt.length() || symbol.charAt(i) != txt.charAt(i)) //not match
                {
                    match = false;
                    break;
                }
            }
            if (match) {
                list.add((CoreElement) op.clone());
            }
        }
        return list;
    }

    public static boolean isFunction(Object x) {
        if (!(x instanceof CoreElement)) {
            return false;
        }
        CoreElement calc = (CoreElement) x;
        for (CoreElement func : functions) {
            if (func.getSymbol().equalsIgnoreCase(calc.getSymbol())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUnary(Object x) {
        if (!(x instanceof CoreElement)) {
            return false;
        }
        CoreElement calc = (CoreElement) x;
        if (calc.getNumberOfParameters() != 1) {
            return false;
        }
        for (CoreElement func : operators) {
            if (func.getSymbol().equalsIgnoreCase(calc.getSymbol())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCalculatorElement(Object x) {
        return x instanceof CoreElement;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
