/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.editor.run.memory.panel;

import core.Memory;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import ui.utils.VerticalFlowLayout;

/**
 *
 * @author antoniomanso
 */
public class ListOfMemories extends JPanel {

    List<Memory> lst = new ArrayList<>();

    public ListOfMemories(List<Memory> lst) {
        this.lst = lst;
        createMemory(lst);
    }

    public void createMemory(List<Memory> mem) {
         this.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP));
        for (Memory memory : mem) {
            this.add(new MemoryButton(memory.getMemoryName(), this));

        }

        this.add(new SingleMemoryDisplay(mem.get(mem.size() - 1)));

    }

    private class MemoryButton extends JButton {

        final ListOfMemories mem;
        final JButton myself;

        public MemoryButton(String txt, ListOfMemories memory) {
            super(txt);
            this.mem = memory;
            this.myself = this;
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Component> elements = new ArrayList<>(Arrays.asList(mem.getComponents()));
                    //search for this button
                    int myIndex = 0;
                    while (myIndex < elements.size()) {
                        if (elements.get(myIndex) == myself) {
                            break;
                        }
                        myIndex++;
                    }
                    //search for memory
                    int memoryIndex = 0;
                    while (elements.get(memoryIndex) instanceof JButton) {
                        memoryIndex++;
                    }
                    // remove memory
                    if (memoryIndex == myIndex + 1) {
                        return;
                    } else if (memoryIndex > myIndex) {
                        elements.remove(memoryIndex);
                    } else {
                        myIndex--;
                        elements.remove(memoryIndex);
                    }

                    elements.add(myIndex+1, new SingleMemoryDisplay(mem.lst.get(myIndex)));

                    mem.removeAll();
                    for (Component component : elements) {
                        mem.add(component);
                    }
                    mem.revalidate();
                }
            });
        }

    }

}
