/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowchart.algorithm;

import javax.swing.JPanel;
import junit.framework.TestCase;

/**
 *
 * @author zulu
 */
public class AlgorithmGraphTest extends TestCase {
    
    public AlgorithmGraphTest(String testName) {
        super(testName);
    }

    /**
     * Test of setProgram method, of class AlgorithmGraph.
     */
    public void testNew() {
        System.out.println(this.getClass().getSimpleName() + " NEW");
        AlgorithmGraph alg = new AlgorithmGraph(new JPanel(), new Program());
    }

   
    
}
