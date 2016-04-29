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
package ui.editor.run.memory.tree;

import core.Memory;
import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import java.awt.BorderLayout;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 * Created on 26/out/2015, 7:43:38
 *
 * @author zulu - computer
 */
public class MemTreeTest {

    public static void main(String args[]) {
        try {
            Memory mem1 = new Memory("Global");
            mem1.add(Fsymbol.create("inteiro", "i", "10"));
            mem1.add(Fsymbol.create("real", "pi", "3.14"));
            mem1.add(Fsymbol.create("caracter", "ch", "'x'"));
            mem1.add(Fsymbol.create("logico", "l1", "verdadeiro"));
            mem1.add(Fsymbol.create("texto", "txt", "\"era uma vez um gato preto com rabo branco\""));
            System.out.println(mem1);

            Memory mem2 = new Memory("principal");
            mem2.add(Fsymbol.create("inteiro", "i2", "10"));
            mem2.add(Fsymbol.create("real", "pi2", "3.14"));
            mem2.add(Fsymbol.create("caracter", "ch2", "'x'"));
            mem2.add(Fsymbol.create("logico", "l12", "verdadeiro"));
            mem2.add(Fsymbol.create("texto", "txt2", "\"era uma vez um gato\""));
            System.out.println(mem2);

//          
            JFrame frame = new JFrame("Programa");
//          Book javaBooks[] = {
//              new Book("Core Java 2 Fundamentals", "Cornell/Horstmann",
//                      42.99f),
//              new Book("Taming Java Threads", "Holub", 34.95f),
//              new Book("JavaServer  Pages", "Pekowsky", 39.95f) };
//          Book htmlBooks[] = { new Book("Dynamic HTML", "Goodman", 39.95f),
//              new Book("HTML 4 Bible", "Pfaffenberger/Gutzman", 49.99f) };
            Vector m1 = new MemoryVector(mem1);
            Vector m2 = new MemoryVector(mem2);
//          Vector htmlVector = new NamedVector("HTML Books", htmlBooks);
            Vector rootNodes[] = {m1, m2};
            Vector rootVector = new MemoryVector("Root", rootNodes);
            JTree tree = new JTree(rootVector);
            //register to tooltip
//            ToolTipManager.sharedInstance().registerComponent(tree);
            
            TreeCellRenderer renderer = new MemoryCellRenderer();
            tree.setCellRenderer(renderer);
            JScrollPane scrollPane = new JScrollPane(tree);
            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            frame.setSize(300, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            ((Fsymbol)m1.get(0)).setName("XPTO");
   
        } catch (FlowchartException ex) {
            Logger.getLogger(MemTreeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510260743L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
