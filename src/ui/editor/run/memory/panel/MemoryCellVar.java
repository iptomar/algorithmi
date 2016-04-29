/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.editor.run.memory.panel;

import core.data.Fsymbol;
import core.data.complexData.Farray;
import flowchart.utils.Theme;
import flowchart.utils.UtilsFlowchart;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author antoniomanso
 */
public class MemoryCellVar extends JLabel {

    public static int VAR_WITH = 50;
    public static int VAR_HEIGHT = 20;

    protected Fsymbol symbol;

    public MemoryCellVar(Fsymbol symbol) {
        super(Theme.valueToHtml(symbol));
        this.symbol = symbol;
        create();

    }

    private void create() {

         setLayout(new java.awt.GridLayout(1, 1, 0, 0));
        setFont(new java.awt.Font("Courier New", 0, 12));

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        setToolTipText(Theme.toHtml(symbol.getFullInfo()));
        
        Dimension dim = UtilsFlowchart.getTextDimension(symbol.getDefinitionValue(), getFont());
        if (symbol instanceof Farray) {
            setPreferredSize(new Dimension(Math.max(dim.width + 2, VAR_WITH), Math.max(dim.height, VAR_HEIGHT)));
        } else {
            setPreferredSize(new Dimension(Math.max(VAR_WITH, dim.width + 7), VAR_HEIGHT));
        }
       

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, symbol.getFullInfo());
            }
        });

    }

}
