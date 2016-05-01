/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data.complexData;

import core.Memory;
import core.data.Finteger;
import core.data.Freal;
import core.data.Fsymbol;
import core.parser.Expression;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author zulu
 */
public class FarrayTest extends TestCase {

    public FarrayTest(String testName) {
        super(testName);
    }

    /**
     * Test of setTextIndexes method, of class Farray.
     */
    public void testContructor() throws Exception {
        System.out.println(" FARRAY - Constructor of list od elements ");
        int dim = 10;
        // list of elements
        ArrayList<Fsymbol> lst = new ArrayList<>();
        for (int i = 0; i < dim; i++) {
            lst.add(new Finteger(i));
        }
        //indexes
        List<Expression> index = new ArrayList<>();
        index.add(new Expression(new Finteger(dim)));
        Farray a1 = new Farray("v", lst, index, Memory.constants);
        System.out.println(a1.toString());

        for (int i = 0; i < dim; i++) {
            ArrayList<Expression> indexA = new ArrayList<>();
            indexA.add(new Expression(new Finteger(i)));
            Finteger elem = (Finteger) a1.getElement(indexA, Memory.constants);
            assertEquals(elem.getIntValue(), i);
            System.out.print(" " + elem.getIntValue());
        }
        System.out.println(" OK");

    }

    /**
     * Test of setTextIndexes method, of class Farray.
     */
    public void testContructor2() throws Exception {
        System.out.println(" FARRAY - Constructor of list od elements ");
        double[][] v = {
            {1, 2, 3, 4, 5},
            {5, 4, 3, 2, 1},
            {9, 8, 7, 6, 5},};

        // list of elements
        ArrayList<Fsymbol> lst = new ArrayList<>();
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                lst.add(new Freal(v[i][j]));
            }
        }
        //indexes
        List<Expression> index = new ArrayList<>();
        index.add(new Expression(new Finteger(v.length)));
        index.add(new Expression(new Finteger(v[0].length)));
        Farray a1 = new Farray("v", lst, index, Memory.constants);
        System.out.println(a1.toString());
                

        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                ArrayList<Expression> indexA = new ArrayList<>();
                indexA.add(new Expression(new Finteger(i)));
                indexA.add(new Expression(new Finteger(j)));
                Freal elem = (Freal) a1.getElement(indexA, Memory.constants);
                assertEquals(elem.getDoubleValue(), v[i][j]);
                System.out.print(" " + elem.getIntValue());
            }
            System.out.println(" OK");
        }

    }

    /**
     * Test of setTextIndexes method, of class Farray.
     */
    public void testContructor3() throws Exception {
        System.out.println(" FARRAY - Constructor of list od elements ");
        double[][][] v = {
            {{0, 1}, {2, 3}, {4, 5}},
            {{6, 7}, {8, 9}, {10, 11}},
            {{12, 13}, {14, 15}, {16, 17}},};

        // list of elements
        ArrayList<Fsymbol> lst = new ArrayList<>();
        for (int k = 0; k < v.length; k++) {
            for (int i = 0; i < v[k].length; i++) {
                for (int j = 0; j < v[k][i].length; j++) {
                    lst.add(new Freal(v[k][i][j]));
                }
            }
        }
        //indexes
        //indexes
        List<Expression> index = new ArrayList<>();
        index.add(new Expression(new Finteger(v.length)));
        index.add(new Expression(new Finteger(v[0].length)));
        index.add(new Expression(new Finteger(v[0][0].length)));
        Farray a1 = new Farray("v", lst, index, Memory.constants);
        System.out.println(a1.toString());
        for (int k = 0; k < v.length; k++) {
            for (int i = 0; i < v[k].length; i++) {
                for (int j = 0; j < v[k][i].length; j++) {
                    ArrayList<Expression> indexA = new ArrayList<>();
                    indexA.add(new Expression(new Finteger(k)));
                    indexA.add(new Expression(new Finteger(i)));
                    indexA.add(new Expression(new Finteger(j)));
                    Freal elem = (Freal) a1.getElement(indexA, Memory.constants);
                    assertEquals(elem.getDoubleValue(), v[k][i][j]);
                    System.out.print(" " + elem.getIntValue());
                }
                System.out.println(" OK");
            }
            System.out.println("-------------------");
        }
    }

    /**
     * Test of setTextIndexes method, of class Farray.
     */
    public void testGETARRAY() throws Exception {
        System.out.println(" FARRAY -GETARRAY ");
        double[][][][] v = {
            {
                {{1, 2}, {3, 4}, {5, 6}},
                {{7, 8}, {9, 10}, {11, 12}}
            }, {
                {{13, 14}, {15, 16}, {17, 18}},
                {{19, 20}, {21, 22}, {23, 24}}
            }
        };

        // list of elements
        ArrayList<Fsymbol> lst = new ArrayList<>();
        for (int x = 0; x < v.length; x++) {
            for (int k = 0; k < v[x].length; k++) {
                for (int i = 0; i < v[x][k].length; i++) {
                    for (int j = 0; j < v[x][k][i].length; j++) {
                        lst.add(new Freal(v[x][k][i][j]));
                    }
                }
            }
        }
        //indexes
        List<Expression> index = new ArrayList<>();
        index.add(new Expression(new Finteger(v.length)));
        index.add(new Expression(new Finteger(v[0].length)));
        index.add(new Expression(new Finteger(v[0][0].length)));
        index.add(new Expression(new Finteger(v[0][0][0].length)));
        Farray a1 = new Farray("v", lst, index, Memory.constants);
        //::::::::::::::::::::::::::::::::::::::: GET ALL ALEMENTS ::::::::::::: 
        System.out.println(a1.toString());
        for (int x = 0; x < v.length; x++) {
            for (int k = 0; k < v[x].length; k++) {
                for (int i = 0; i < v[x][k].length; i++) {
                    for (int j = 0; j < v[x][k][i].length; j++) {
                        ArrayList<Expression> indexA = new ArrayList<>();
                        indexA.add(new Expression(new Finteger(x)));
                        indexA.add(new Expression(new Finteger(k)));
                        indexA.add(new Expression(new Finteger(i)));
                        indexA.add(new Expression(new Finteger(j)));
                        Freal elem = (Freal) a1.getElement(indexA, Memory.constants);
                        assertEquals(elem.getDoubleValue(), v[x][k][i][j]);
                        System.out.print(" " + elem.getIntValue());
                    }
                    System.out.println(" OK");
                }
                System.out.println("-------------------");
            }
        }
        //::::::::::::::::::::::::::::::::::::::: GET ARRAYS ::::::::::::: 
        System.out.println(a1.toString());
        for (int x = 0; x < v.length; x++) {
            for (int k = 0; k < v[x].length; k++) {
                for (int i = 0; i < v[x][k].length; i++) {

//                    for (int j = 0; j < v[x][k][i].length; j++) {
                    ArrayList<Expression> indexA = new ArrayList<>();
                    indexA.add(new Expression(new Finteger(x)));
                    indexA.add(new Expression(new Finteger(k)));
                    indexA.add(new Expression(new Finteger(i)));
                    // indexA.add(new Expression(new Finteger(j)));
                    Farray array = (Farray) a1.getElement(indexA, Memory.constants);
                    System.out.println(array.toString() + " " + array.toTextValue());
                    assertEquals(array.getElements().size(), v[0][0][0].length);
//                    }
                }
                System.out.println("-------------------");
            }
        }
        for (int x = 0; x < v.length; x++) {
            for (int k = 0; k < v[x].length; k++) {
//                for (int i = 0; i < v[x][k].length; i++) {

//                    for (int j = 0; j < v[x][k][i].length; j++) {
                ArrayList<Expression> indexA = new ArrayList<>();
                indexA.add(new Expression(new Finteger(x)));
                indexA.add(new Expression(new Finteger(k)));
//                        indexA.add(new Expression(new Finteger(i)));
                // indexA.add(new Expression(new Finteger(j)));
                Farray array = (Farray) a1.getElement(indexA, Memory.constants);
                System.out.println(array.toString() + " " + array.toTextValue());
                assertEquals(array.getElements().size(), v[0][0][0].length * v[0][0].length);
//                    }
            }
            System.out.println("-------------------");
        }
          for (int x = 0; x < v.length; x++) {
//            for (int k = 0; k < v[x].length; k++) {
//                for (int i = 0; i < v[x][k].length; i++) {

//                    for (int j = 0; j < v[x][k][i].length; j++) {
                ArrayList<Expression> indexA = new ArrayList<>();
                indexA.add(new Expression(new Finteger(x)));
//                indexA.add(new Expression(new Finteger(k)));
//                        indexA.add(new Expression(new Finteger(i)));
                // indexA.add(new Expression(new Finteger(j)));
                Farray array = (Farray) a1.getElement(indexA, Memory.constants);
                System.out.println(array.toString() + " " + array.toTextValue());
                assertEquals(array.getElements(), v[0][0][0].length * v[0][0].length* v[0].length);
//                    }
        }
    }

}
