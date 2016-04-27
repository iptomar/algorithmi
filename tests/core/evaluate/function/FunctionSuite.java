/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.evaluate.function;

import core.evaluate.function.text.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author zulu
 */
public class FunctionSuite extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite("FunctionSuite");
        suite.addTest(TextSuite.suite());
        suite.addTest(new TestSuite(IntTest.class));
        suite.addTest(new TestSuite(SizeOfTest.class));
        return suite;
    }

}
