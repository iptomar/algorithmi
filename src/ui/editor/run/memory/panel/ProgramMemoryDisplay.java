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
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package ui.editor.run.memory.panel;

import core.Memory;
import flowchart.algorithm.run.GraphExecutor;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;

/**
 * Created on 30/abr/2016, 12:51:08
 *
 * @author zulu - computer
 */
public class ProgramMemoryDisplay extends JPanel {


    GraphExecutor program; // executor of the memory

    /**
     * memory to the program
     * @param program the program
     */
    public ProgramMemoryDisplay(GraphExecutor program) {
        this(program.getProgramMemory());
        this.program = program;
    }

    public ProgramMemoryDisplay(List<Memory> lst) {
         setLayout(new VerticalLayout(VerticalLayout.TOP));
        for (Memory memory : lst) {
            addMemory(memory);
        }
    }

    /**
     * refresh screen
     */
    public void redraw() {
        revalidate();
        repaint();
    }

    /**
     * normalize the size of panels in the memory
     */
    public void normalizeWidth() {
        int max = 0;
        for (Component c : getComponents()) {
            if (c.getWidth() > max) {
                max = c.getWidth();
            }
        }
        for (Component c : getComponents()) {
            c.setPreferredSize(new Dimension(max, c.getHeight()));
            c.revalidate();
        }
        revalidate();
    }

  


    private void addMemory(Memory mem) {
        SingleMemoryDisplay display = new SingleMemoryDisplay(mem, this);
        this.add(display);
//        revalidate();
    }

    public final void updateMemory() {
        List<Memory> mem = program.getProgramMemory();

        //size is different create or remove memories
        if (getComponentCount() != mem.size()) {
            //remove
            if (getComponentCount() > mem.size()) { //remove last memory
                while (getComponentCount() > mem.size()) {
                    this.remove(mem.size()-1);
                }
            } else {
                //create
                while (getComponentCount() < mem.size()) // add memories
                {
                    addMemory(mem.get(getComponentCount()));
                }
            }        
        }
        //display last memory
        ((SingleMemoryDisplay)getComponent(getComponentCount() - 1)).setDisplayed(true);
        //update symbols in the visible memories
        for (int i = 0; i < mem.size(); i++) {
            //is visible
            if (((SingleMemoryDisplay)getComponent(i)).isDisplayed()) {
                ((SingleMemoryDisplay)getComponent(i)).updateMemory(mem.get(i));
            }
        }
        redraw();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604301251L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
//
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(LauncherTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(LauncherTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(LauncherTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(LauncherTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        try {
//
//            Program prog = new Program();
//
//            Memory mem0 = new Memory("Memory 1  0000000000");
//            Farray a0 = new Farray("inteiro vetor[4][2][10]", mem0, prog);
//            a0.createArrayElements(mem0, prog);
//            mem0.add(Fsymbol.create("inteiro", "i2", "10"));
//            mem0.add(Fsymbol.create("real", "pi2", "3.14"));
//
//            mem0.add(a0);
//            mem0.add(Fsymbol.create("logico", "l12", "verdadeiro"));
//            mem0.add(Fsymbol.create("texto", "txt2", "\"Funcao funcao \""));
//
//            Memory mem1 = new Memory("Memory 1  ");
//            mem1.add(Fsymbol.create("inteiro", "i2", "10"));
//            mem1.add(Fsymbol.create("real", "pi2", "3.14"));
//            Farray a1 = new Farray("inteiro vetor[4][2]", mem1, prog);
//            a1.createArrayElements(mem1, prog);
//            mem1.add(a1);
//            mem1.add(Fsymbol.create("logico", "l12", "verdadeiro"));
//            mem1.add(Fsymbol.create("texto", "txt2", "\"Funcao funcao \""));
//
//            Memory mem2 = new Memory("Memory 2  ");
//            mem2.add(Fsymbol.create("inteiro", "i2", "10"));
//            mem2.add(Fsymbol.create("real", "pi2", "3.14"));
//            Farray a = new Farray("inteiro vetor[2][4]", mem2, prog);
//            a.createArrayElements(mem2, prog);
////            mem2.add(a);
//            mem2.add(Fsymbol.create("logico", "l12", "verdadeiro"));
//            mem2.add(Fsymbol.create("texto", "txt2", "\"Funcao funcao \""));
//
//            List<Memory> lst = new ArrayList<>();
//            lst.add(mem0);
//            lst.add(mem1);
//            lst.add(mem2);
//            lst.add(mem1);
//            lst.add(mem2);
//
//            JFrame frame = new JFrame("Programa");
//            frame.setLayout(new BorderLayout());
//            ProgramMemoryDisplay mem = new ProgramMemoryDisplay(lst);
//            SingleMemoryDisplay sm = new SingleMemoryDisplay(mem2, mem);
//
//            frame.getContentPane().add(mem, BorderLayout.CENTER);
//            frame.setSize(600, 600);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setVisible(true);
//
//        } catch (FlowchartException ex) {
//
//        }
//    }
}
