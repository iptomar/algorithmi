/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.JFrame;

/**
 *
 * @author antoniomanso
 */
public class TestListMemeories {
    
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
    
}
