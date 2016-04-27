/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.parser;

import core.Memory;
import core.data.Finteger;
import core.data.Freal;
import core.data.Fsymbol;
import core.data.complexData.Farray;
import core.evaluate.aritmetic.Sum;
import core.evaluate.function.lang.ElementsOf;
import flowchart.algorithm.Program;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author zulu
 */
public class ExpressionArrayTest extends TestCase {

    public ExpressionArrayTest(String testName) {
        super(testName);
    }

    /**
     * Test of getReturnType method, of class Expression.
     */
    public void testAritmetic() throws Exception {
        int x = 10;
        int y = 40;
        String add = Sum.symbol;

        String strExp = x + add + y;
        Finteger expected = new Finteger(x + y + "");

        Program prog = new Program();
        Memory mem = new Memory("mainMemory");
        mem.add(new Finteger("x", "" + x));
        mem.add(new Finteger("y", "" + y));

        Expression exp = new Expression(strExp, mem, prog);
        Fsymbol result = exp.evaluate(mem);
        assertTrue(result.equalsValue(expected));
    }

    /**
     * Test of getReturnType method, of class Expression.
     */
    public void testArrays() throws Exception {
        System.out.println("Expression Array 1D");
        Program prog = new Program();
        Memory mem = new Memory("mainMemory");
        int size = 10;
        String def = Freal.TYPE_REAL_NAME + " v" + Mark.SQUARE_OPEN + size + Mark.SQUARE_CLOSE;
        //define an array
        Farray v = new Farray(def, mem, prog);
        v.createArrayElements(mem, prog);
        mem.add(v);
        //list of indexes
        List<Expression> lst = new ArrayList<>();
        // variable x is an index
        Finteger x = new Finteger("x", "" + 0);
        mem.add(x);
        lst.add(new Expression(x));
        System.out.println("GET and SET");
        for (int i = 0; i < size; i++) {
            x.setValue(i);
            Fsymbol expected = new Freal(i);
            v.setElementValue(expected, lst, mem);
            Fsymbol result = v.getElement(lst, mem);
            //inserted is equal to get
            assertTrue(result.equalsValue(expected));
//            System.out.println(" V " + v.getElement(lst, mem));
        }
        System.out.println("ElementsOF");
        String exp = ElementsOf.symbol + Mark.ROUND_OPEN + "v" + Mark.ROUND_CLOSE;
        Expression e = new Expression(exp, mem, prog);
        //expected value
        Fsymbol expected = new Finteger(size);
        //evaluation Value
        Fsymbol result = e.evaluate(mem);
//        System.out.println(exp + " " + result.getTextValue());
        assertTrue(result.equalsValue(expected));

    }

    /**
     * Test of getReturnType method, of class Expression. array [10][20]
     */
    public void testArrays2D() throws Exception {
        System.out.println("Expression Array 2D");
        Program prog = new Program();
        Memory mem = new Memory("mainMemory");
        int size1 = 5;
        int size2 = 10;
        String def = Freal.TYPE_REAL_NAME + " v" + Mark.SQUARE_OPEN + size1 + Mark.SQUARE_CLOSE + Mark.SQUARE_OPEN + size2 + Mark.SQUARE_CLOSE;
        //define an array
        Farray v = new Farray(def, mem, prog);
        v.createArrayElements(mem, prog);
        mem.add(v);

        //list of indexes
        List<Expression> lst = new ArrayList<>();
        Finteger d1 = new Finteger("y", "" + 0);
        mem.add(d1);
        lst.add(new Expression(d1));
        Finteger d2 = new Finteger("x", "" + 0);
        mem.add(d2);
        lst.add(new Expression(d2));
        System.out.println("GET and SET");
        for (int y = 0; y < size1; y++) {
            for (int x = 0; x < size2; x++) {
                //update indexes

                d1.setValue(y);
                d2.setValue(x);
                //verify set and get 
                Fsymbol expected = new Freal(y * 10 + x);
                v.setElementValue(expected, lst, mem);
                Fsymbol result = v.getElement(lst, mem);
                //inserted is equal to get
                assertTrue(result.equalsValue(expected));
                // System.out.println(" V " + v.getElement(lst, mem));
            }

        }
        System.out.println("Elements OF");
        for (int y = 0; y < size1; y++) {
            String exp = ElementsOf.symbol + Mark.ROUND_OPEN + "v" + Mark.SQUARE_OPEN + y + Mark.SQUARE_CLOSE + Mark.ROUND_CLOSE;
            Expression e = new Expression(exp, mem, prog);
            //expected value
            Fsymbol expected = new Finteger(size2);
            //evaluation Value
            Fsymbol result = e.evaluate(mem);
            //System.out.println(exp + " " + result.getTextValue());
            assertTrue(result.equalsValue(expected));

            for (int x = 0; x < size2; x++) {
                exp = ElementsOf.symbol + Mark.ROUND_OPEN + "v" + Mark.SQUARE_OPEN + y + Mark.SQUARE_CLOSE + Mark.SQUARE_OPEN + x + Mark.SQUARE_CLOSE + Mark.ROUND_CLOSE;
                e = new Expression(exp, mem, prog);
                //expected value
                expected = new Finteger(1);
                //evaluation Value
                result = e.evaluate(mem);
                // System.out.println(exp + " " + result.getTextValue());
                assertTrue(result.equalsValue(expected));
            }

        }

        String exp = ElementsOf.symbol + Mark.ROUND_OPEN + "v" + Mark.SQUARE_OPEN + "0" + Mark.SQUARE_CLOSE + Mark.ROUND_CLOSE;
        Expression e = new Expression(exp, mem, prog);
        //expected value
        Fsymbol expected = new Finteger(size2);
        //evaluation Value
        Fsymbol result = e.evaluate(mem);
        //  System.out.println(exp + " " + result.getTextValue());
        assertTrue(result.equalsValue(expected));
    }

    /**
     * Test of getReturnType method, of class Expression. array [10][20]
     */
    public void testArrays3D() throws Exception {
        System.out.println("Expression Array 3D");
        Program prog = new Program();
        Memory mem = new Memory("mainMemory");
        int size1 = 3;
        int size2 = 5;
        int size3 = 7;
        String def = Freal.TYPE_REAL_NAME + " v" + Mark.SQUARE_OPEN + size1 + Mark.SQUARE_CLOSE + Mark.SQUARE_OPEN + size2 + Mark.SQUARE_CLOSE + Mark.SQUARE_OPEN + size3 + Mark.SQUARE_CLOSE;
        //define an array
        Farray v = new Farray(def, mem, prog);
        v.createArrayElements(mem, prog);
        mem.add(v);

        //list of indexes
        List<Expression> lst = new ArrayList<>();
        Finteger d1 = new Finteger("z", "" + 0);
        mem.add(d1);
        lst.add(new Expression(d1));
        Finteger d2 = new Finteger("y", "" + 0);
        mem.add(d2);
        lst.add(new Expression(d2));
        Finteger d3 = new Finteger("x", "" + 0);
        mem.add(d3);
        lst.add(new Expression(d3));
        System.out.println("GET and SET");
        for (int z = 0; z < size1; z++) {
            for (int y = 0; y < size2; y++) {
                for (int x = 0; x < size3; x++) {
                    //update indexes
                    d1.setValue(z);
                    d2.setValue(y);
                    d3.setValue(x);
                    //verify set and get 
                    Fsymbol expected = new Freal(z * 100 + y * 10 + x);
                    v.setElementValue(expected, lst, mem);
                    Fsymbol result = v.getElement(lst, mem);
                    //inserted is equal to get
                    assertTrue(result.equalsValue(expected));
                }

            }
        }
        System.out.println("ElementsOF");
        for (int z = 0; z < size1; z++) {
            String exp = ElementsOf.symbol + Mark.ROUND_OPEN + "v" + Mark.SQUARE_OPEN + z + Mark.SQUARE_CLOSE + Mark.ROUND_CLOSE;
            Expression e = new Expression(exp, mem, prog);
            //expected value
            Fsymbol expected = new Finteger(size2);
            //evaluation Value
            Fsymbol result = e.evaluate(mem);
            //System.out.println(exp + " " + result.getTextValue());
            assertTrue(result.equalsValue(expected));
            for (int y = 0; y < size1; y++) {
                exp = ElementsOf.symbol + Mark.ROUND_OPEN + "v" + Mark.SQUARE_OPEN + z + Mark.SQUARE_CLOSE + Mark.SQUARE_OPEN + y + Mark.SQUARE_CLOSE + Mark.ROUND_CLOSE;
                e = new Expression(exp, mem, prog);
                //expected value
                expected = new Finteger(size3);
                //evaluation Value
                result = e.evaluate(mem);
                // System.out.println(exp + " " + result.getTextValue());
                assertTrue(result.equalsValue(expected));

                for (int x = 0; x < size2; x++) {
                    exp = ElementsOf.symbol + Mark.ROUND_OPEN + "v" + Mark.SQUARE_OPEN + z + Mark.SQUARE_CLOSE + Mark.SQUARE_OPEN + y + Mark.SQUARE_CLOSE + Mark.SQUARE_OPEN + x + Mark.SQUARE_CLOSE + Mark.ROUND_CLOSE;
                    e = new Expression(exp, mem, prog);
                    //expected value
                    expected = new Finteger(1);
                    //evaluation Value
                    result = e.evaluate(mem);
                    // System.out.println(exp + " " + result.getTextValue());
                    assertTrue(result.equalsValue(expected));
                }

            }
        }

    }

}
