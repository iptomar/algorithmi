/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowchart.algorithm;

import flowchart.utils.ProgramFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import junit.framework.TestCase;

/**
 *
 * @author zulu
 */
public class ProgramTest extends TestCase {
    
    public ProgramTest(String testName) {
        super(testName);
    }

    /**
     * Test of getFileName method, of class Program.
     */
    public void testGetFileName() {
        try {
            System.out.println(this.getClass().getSimpleName() + " SAVE");
            Program prog = new Program();
            
            prog.setMain(new AlgorithmGraph(new JPanel(), prog));
//            System.out.println(prog.getTokens());
            prog.setFileName("teste");
            prog.save();
            Program load = ProgramFile.loadFromFile("teste");
//            System.out.println(load.getTokens());
        } catch (Exception ex) {
            Logger.getLogger(ProgramTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    
}
