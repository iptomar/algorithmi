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
import i18n.EditorI18N;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import ui.LauncherTeacher;

/**
 *
 * display one memory
 *
 * @author antoniomanso
 */
public class SingleMemoryDisplay extends JScrollPane {

    public static int SIZE_OF_SYMBOLS_CHANGED = 5; // number of elements to highlight
    private static ImageIcon memoryIcon = EditorI18N.loadIcon("RUN.memoryDisplay.icon", 24); // icon to memory button

    Memory myMemory; // my memory

    JPanel memDisplay; // display
    JToggleButton bt; // button
    JPanel memPanel; // button + display to insert in jscrollpane

    //cells to highlight
    CircularBuffer<MemoryCellVar> symbolsChanged = new CircularBuffer<>(SIZE_OF_SYMBOLS_CHANGED);

    public SingleMemoryDisplay(Memory mem, final ProgramMemoryDisplay parentPanel) {
        setBorder(BorderFactory.createTitledBorder(""));// title is displayed in button        
//        setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.myMemory = mem;
        //symbolLst = new ArrayList<>();

//        super(new VerticalFlowLayout());
        memPanel = new JPanel(new BorderLayout(0, 0));
        setViewportView(memPanel);
        memDisplay = new JPanel();
        memDisplay.setLayout(new VerticalLayout(VerticalLayout.TOP));

        //add simbols to memory display
        for (int i = 0; i < mem.getMem().size(); i++) {
            MemorySymbol memVar = new MemorySymbol(mem.getMem().get(i));
            memDisplay.add(memVar);
            // symbolLst.add(memVar);

        }
        memPanel.add(memDisplay, BorderLayout.CENTER);
        //build button
        bt = new JToggleButton(mem.getMemoryName(), false);
        bt.setIcon(memoryIcon);

        bt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bt.setSelected(true);
        memPanel.add(bt, BorderLayout.NORTH);
        //---------------------------------------------------------------------- action to button 
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bt.isSelected()) {
                    memPanel.add(memDisplay, BorderLayout.CENTER);
                } else {
                    memPanel.remove(memDisplay);
                }
                parentPanel.redraw();
            }
        });
    }

    public boolean isDisplayed() {
        return bt.isSelected();
    }

    public void setDisplayed(boolean visible) {
        bt.setSelected(visible);
    }

    public void updateMemory(Memory mem) {

        boolean changed = false; // optimization to display
        if (!symbolsChanged.isEmpty()) {
            symbolsChanged.getLast().clearColor(); //clear last symbol highlighted
        }        //memories are differents ?
        if (mem.size() != memDisplay.getComponentCount()) {
            //new Simbols ?
            if (mem.size() > memDisplay.getComponentCount()) {
                //add new simbols
                while (mem.size() != memDisplay.getComponentCount()) {
                    memDisplay.add(new MemorySymbol(mem.getMem().get(memDisplay.getComponentCount())));
                }
            } //Remove Symbols?
            else if (mem.size() < memDisplay.getComponentCount()) {
                //remove simbols
                while (mem.size() != memDisplay.getComponentCount()) {
                    memDisplay.remove(mem.size());
                }
            }
            // this memory needs to be displayed
            changed = true;
        }
        //cells changes ? 
        MemoryCellVar cellChanged = null;
        // for each  memory
        for (int i = 0; i < memDisplay.getComponentCount(); i++) {
            //update memory
            cellChanged = ((MemorySymbol) memDisplay.getComponent(i)).update(mem.getMem().get(i));
            //symbols are changed
            if (cellChanged != null) {
                //highlight cell
                symbolsChanged.add(cellChanged);
                changed = true;
            }
        }
        if (changed && !symbolsChanged.isEmpty()) {
            float hue = 0.2f/ symbolsChanged.size(); //0 = red 60= yellow
            for (int i = symbolsChanged.size() - 1; i >= 0; i--) {
                symbolsChanged.get(i).setColor(Color.getHSBColor(hue*i, 1-hue*i, 1-hue*i));
            }
        }

    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LauncherTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LauncherTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LauncherTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LauncherTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        try {

            Program prog = new Program();

            Memory mem0 = new Memory("Memory 1  0000000000");
            Farray a0 = new Farray("inteiro vetor[4][2][10]", mem0, prog);
            a0.createArrayElements(mem0, prog);
            mem0.add(Fsymbol.create("inteiro", "i2", "10"));
            mem0.add(Fsymbol.create("real", "pi2", "3.14"));

            mem0.add(a0);
            mem0.add(Fsymbol.create("logico", "l12", "verdadeiro"));
            mem0.add(Fsymbol.create("texto", "txt2", "\"Funcao funcao \""));

            Memory mem1 = new Memory("Memory 1  ");
            mem1.add(Fsymbol.create("inteiro", "i2", "10"));
            mem1.add(Fsymbol.create("real", "pi2", "3.14"));
            Farray a1 = new Farray("inteiro vetor[4][2]", mem1, prog);
            a1.createArrayElements(mem1, prog);
            mem1.add(a1);
            mem1.add(Fsymbol.create("logico", "l12", "verdadeiro"));
            mem1.add(Fsymbol.create("texto", "txt2", "\"Funcao funcao \""));

            Memory mem2 = new Memory("Memory 2  ");
            mem2.add(Fsymbol.create("inteiro", "i2", "10"));
            mem2.add(Fsymbol.create("real", "pi2", "3.14"));
            Farray a = new Farray("inteiro vetor[2][4]", mem2, prog);
            a.createArrayElements(mem2, prog);
//            mem2.add(a);
            mem2.add(Fsymbol.create("logico", "l12", "verdadeiro"));
            mem2.add(Fsymbol.create("texto", "txt2", "\"Funcao funcao \""));

            List<Memory> lst = new ArrayList<>();
            lst.add(mem0);
            lst.add(mem1);
            lst.add(mem2);
            lst.add(mem1);
            lst.add(mem2);

            JFrame frame = new JFrame("Programa");
            frame.setLayout(new BorderLayout());

            ProgramMemoryDisplay mem = new ProgramMemoryDisplay(lst);
            SingleMemoryDisplay sm = new SingleMemoryDisplay(mem2, mem);
            System.out.println("Dims " + sm.getPreferredSize());

            frame.getContentPane().add(sm, BorderLayout.CENTER);
            frame.setSize(600, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        } catch (FlowchartException ex) {

        }
    }

}
