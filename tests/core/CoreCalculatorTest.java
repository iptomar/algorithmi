/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import core.evaluate.CoreElement;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pedro Dias
 */
public class CoreCalculatorTest {
  
  public CoreCalculatorTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of getBySymbol method, of class CoreCalculator.
   */
  @Test
  public void testGetBySymbol() {
    System.out.println("getBySymbol");
    String symbol = "";
    CoreElement expResult = null;
    CoreElement result = CoreCalculator.getBySymbol(symbol);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getOperatorCharSet method, of class CoreCalculator.
   */
  @Test
  public void testGetOperatorCharSet() {
    System.out.println("getOperatorCharSet");
    String expResult = "";
    String result = CoreCalculator.getOperatorCharSet();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of getOperatorsStartedWith method, of class CoreCalculator.
   */
  @Test
  public void testGetOperatorsStartedWith() {
    System.out.println("getOperatorsStartedWith");
    String txt = "";
    List<CoreElement> expResult = null;
    List<CoreElement> result = CoreCalculator.getOperatorsStartedWith(txt);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isFunction method, of class CoreCalculator.
   */
  @Test
  public void testIsFunction() {
    System.out.println("isFunction");
    Object x = null;
    boolean expResult = false;
    boolean result = CoreCalculator.isFunction(x);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isUnary method, of class CoreCalculator.
   */
  @Test
  public void testIsUnary() {
    System.out.println("isUnary");
    Object x = null;
    boolean expResult = false;
    boolean result = CoreCalculator.isUnary(x);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of isCalculatorElement method, of class CoreCalculator.
   */
  @Test
  public void testIsCalculatorElement() {
    System.out.println("isCalculatorElement");
    Object x = null;
    boolean expResult = false;
    boolean result = CoreCalculator.isCalculatorElement(x);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
  
}
