/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import core.data.DataSuite;
import core.evaluate.function.*;
import core.evaluate.function.text.*;
import core.parser.ExpressionSuite;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author zulu
 */
public class Algorithmi_ALL_test extends TestCase {

    public Algorithmi_ALL_test(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Algorithmi_ALL_test");
        suite.addTest(FunctionSuite.suite());
        suite.addTest(TextSuite.suite());
        suite.addTest(ExpressionSuite.suite());
        suite.addTest(DataSuite.suite());
        return suite;
    }

}
