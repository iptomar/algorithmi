/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import junit.framework.TestCase;

/**
 *
 * @author zulu
 */
public class FPropertiesTest extends TestCase {
    
    public FPropertiesTest(String testName) {
        super(testName);
    }

    /**
     * Test of reLoad method, of class FProperties.
     */
    public void testReLoad() {
//        FProperties.loadDefaults();
        FProperties.getUser("Algorithmi");
        System.out.println(FProperties.lastProgram);
        
    }

    
}
