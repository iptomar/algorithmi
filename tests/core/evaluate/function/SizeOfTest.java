/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.evaluate.function;

import core.evaluate.function.lang.ElementsOf;
import core.data.FabstractNumber;
import core.data.Finteger;
import core.data.Freal;
import core.data.Fsymbol;
import core.data.Ftext;
import core.data.exception.FlowchartException;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.Program;
import flowchart.define.Define;
import java.util.ArrayList;
import javax.swing.JPanel;
import junit.framework.TestCase;

/**
 *
 * @author zulu
 */
public class SizeOfTest extends TestCase {
    
    public SizeOfTest(String testName) {
        super(testName);
    }

    /**
     * Test of getSymbol method, of class ElementsOf.
     */
    public void testReal() {
        
         ElementsOf calc = new ElementsOf();
        try {            
            Fsymbol s = new Freal(232432423.876);
            //information to console
            System.out.println(calc.getClass().getName() + " - " + s.getClass().getSimpleName() );
            ArrayList<Fsymbol> lst = new ArrayList<>();
            lst.add(s);
            Fsymbol res = calc.evaluate(lst);
            // result type must be integer
            assertTrue(res instanceof Finteger);
            // result must be 1
            assertEquals( ((FabstractNumber)res).getIntValue(), 1);

        } catch (FlowchartException ex) {
           fail(ex.getMessage());
        }
    }
      /**
     * Test of getSymbol method, of class ElementsOf.
     */
    public void testInteger() {
        
         ElementsOf calc = new ElementsOf();
        try {            
            Fsymbol s = new Finteger(23243);
            //information to console
            System.out.println(calc.getClass().getName() + " - " + s.getClass().getSimpleName() );
            ArrayList<Fsymbol> lst = new ArrayList<>();
            lst.add(s);
            Fsymbol res = calc.evaluate(lst);
            // result type must be integer
            assertTrue(res instanceof Finteger);
            // result must be 1
            assertEquals( ((FabstractNumber)res).getIntValue(), 1);

        } catch (FlowchartException ex) {
           fail(ex.getMessage());
        }
    }
    
     /**
     * Test of getSymbol method, of class ElementsOf.
     */
    public void testText() {
        
         ElementsOf calc = new ElementsOf();
        try { 
            String str = "Hello World";
            Fsymbol s = new Ftext("\""+ str + "\"");
            //information to console
            System.out.println(calc.getClass().getName() + " - " + s.getClass().getSimpleName() );
            ArrayList<Fsymbol> lst = new ArrayList<>();
            lst.add(s);
            Fsymbol res = calc.evaluate(lst);
            // result type must be integer
            assertTrue(res instanceof Finteger);
            // result must be 3
            assertEquals( ((FabstractNumber)res).getIntValue(), str.length());

        } catch (FlowchartException ex) {
           fail(ex.getMessage());
        }
    }
    
      /**
     * Test of getSymbol method, of class ElementsOf.
     */
    public void testArray() {
        
         ElementsOf calc = new ElementsOf();
        try { 
            //define instruction
            Define defArray = new Define(new AlgorithmGraph(new JPanel(), new Program()));
            defArray.buildInstruction("inteiro v[10]", "teste");
            //information to console
            System.out.println(calc.getClass().getName() + " - " + defArray.varSymbol.getClass().getSimpleName() );
            ArrayList<Fsymbol> lst = new ArrayList<>();
            lst.add(defArray.varSymbol);
            Fsymbol res = calc.evaluate(lst);
            // result type must be integer
            assertTrue(res instanceof Finteger);
            // result must be 3
            assertEquals( ((FabstractNumber)res).getIntValue(), 10);

        } catch (FlowchartException ex) {
           fail(ex.getMessage());
        }
    }
    
    

    
}
