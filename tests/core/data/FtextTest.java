/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data;

import core.data.exception.FlowchartException;
import core.parser.Expression;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author zulu
 */
public class FtextTest extends TestCase {

    public FtextTest(String testName) {
        super(testName);
    }

    public void testSetString() {
        try {
            System.out.println(this.getClass().getName() + " indexed SET");
            Ftext txt = new Ftext("str", "\"abcd\"");
            Ftext p = new Ftext("p", "\"xpto\"");
            System.out.println("TXT = " + txt + " value: ["+txt.getTextValue()+"]");            
            Finteger i = new Finteger(4);
            Expression e = new Expression(new Finteger(4));
            txt.setText(p, i);
            System.out.println("TXT = " + txt + " value: ["+txt.getTextValue()+"]");
            
            Finteger j = new Finteger(4);
            Ftext t2 = txt.getText(i, j);
            System.out.println("t2 = " + t2 + " value: ["+t2.getTextValue()+"]");  
            
            
        } catch (FlowchartException ex) {
            Logger.getLogger(FtextTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getTxtMessage());
        }
    }

}
