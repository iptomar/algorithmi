//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2015   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package ui.editor.run.memory.panel;

import core.Memory;
import core.data.Fsymbol;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import flowchart.algorithm.Program;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JFrame;

/**
 * Created on 26/out/2015, 7:43:38
 *
 * @author zulu - computer
 */
public class MemPanelTest {

    public static void main(String args[]) {
        try {
            Program prog = new Program();

            Memory mem0 = new Memory("Global asd d sd");
            mem0.add(Fsymbol.create("inteiro", "i", "10"));
            mem0.add(Fsymbol.create("real", "pi", "3.14"));
            mem0.add(Fsymbol.create("logico", "l1", "verdadeiro"));
            mem0.add(Fsymbol.create("texto", "txt", "\"era uma vez um gato preto com rabo branco\""));

            Memory mem1 = new Memory("Programa muito principal fsa d ddsad");
            mem1.add(Fsymbol.create("inteiro", "i", "10"));
            mem1.add(Fsymbol.create("real", "pi", "3.14"));
            mem1.add(Fsymbol.create("logico", "l1", "verdadeiro"));
            mem1.add(Fsymbol.create("texto", "txt", "\"era uma vez um gato preto com rabo branco\""));

            Memory mem2 = new Memory("função sdfx sdaf dsaf sdaf ");
            mem2.add(Fsymbol.create("inteiro", "i2", "10"));
            mem2.add(Fsymbol.create("real", "pi2", "3.14"));

            Farray a = new Farray("inteiro vetor[2][3][4]", mem1, prog);
            a.createArrayElements(mem2, prog);
            mem2.add(a);
            mem2.add(Fsymbol.create("logico", "l12", "verdadeiro"));
            mem2.add(Fsymbol.create("texto", "txt2", "\"era uma vez um gato\""));

            List<Memory> lst = new ArrayList<>();
            lst.add(mem0);
            lst.add(mem1);
            lst.add(mem2);
            JFrame frame = new JFrame("Programa");

            MemorySymbol s = new MemorySymbol(a);
            //SingleMemoryDisplay memoryD = new SingleMemoryDisplay(mem2);

            MemoryDisplayPanel mem = new MemoryDisplayPanel(lst);
            frame.getContentPane().add(mem, BorderLayout.CENTER);
            frame.setSize(300, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        } catch (FlowchartException ex) {
            Logger.getLogger(MemPanelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510260743L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
