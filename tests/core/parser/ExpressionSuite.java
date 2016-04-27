/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.parser;

import core.evaluate.function.*;
import core.evaluate.function.text.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author zulu
 */
public class ExpressionSuite extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite("ExpressionSuite");
        suite.addTest(new TestSuite(ExpressionArrayTest.class));        
        return suite;
    }

}
