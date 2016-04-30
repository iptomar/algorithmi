/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.editor.run.memory.panel;

import core.Memory;
import core.data.Fsymbol;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import ui.utils.VerticalFlowLayout;

/**
 *
 * @author antoniomanso
 */
public class SingleMemoryDisplay extends JPanel {

    Memory mem;

    public SingleMemoryDisplay(Memory mem) {
        this.mem = mem;
        this.setBorder(BorderFactory.createTitledBorder(
                null, mem.getMemoryName(),
                TitledBorder.LEFT,
                TitledBorder.BELOW_TOP,
                new Font("Courier New", Font.BOLD, 12))
        );
        //setLayout(new FlowLayout(FlowLayout.LEFT));
        Box left = Box.createVerticalBox();
        this.setLayout(new VerticalFlowLayout());
        for (Fsymbol var : mem.getMem()) {
            MemorySymbol memVar = new MemorySymbol(var);
            memVar.setAlignmentX(Component.LEFT_ALIGNMENT);//0.0
            //left.add(memVar);
            this.add(memVar);
        }
        //this.add(left);

    }

}
