/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data;

import core.data.complexData.FarrayTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author zulu
 */
public class DataSuite extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite("DataSuite");
        suite.addTest(new TestSuite(FtextTest.class));        
        suite.addTest(new TestSuite(FarrayTest.class));        
        return suite;
    }

}
