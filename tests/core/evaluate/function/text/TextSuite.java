/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.evaluate.function.text;

import core.evaluate.function.IntTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author zulu
 */
public class TextSuite extends TestCase {
    
    public TextSuite(String testName) {
        super(testName);
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("TextSuite");
        suite.addTest(new TestSuite(AlignTest.class));
        return suite;
    }
    
}
