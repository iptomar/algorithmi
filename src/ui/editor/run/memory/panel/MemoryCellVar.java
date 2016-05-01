/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.editor.run.memory.panel;

import core.data.Fsymbol;
import core.data.complexData.Farray;
import core.parser.Mark;
import flowchart.utils.Theme;
import flowchart.utils.UtilsFlowchart;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author antoniomanso
 */
public class MemoryCellVar extends JLabel {

    public static Color clearColor = Color.WHITE;
    public static int VAR_WITH = 40;
    public static int VAR_HEIGHT = 20;

    public String txtSymbol;
    public final Fsymbol mySymbol;

    public MemoryCellVar(Fsymbol symbol) {
        this.mySymbol = symbol;
        create(symbol);
        update(symbol);
    }

    public void update(Fsymbol symbol) {
        setText(Theme.valueToHtml(symbol));
        txtSymbol = symbol.getTextValue();
    }

    public boolean isEqual(Fsymbol symbol) {
        return symbol == this.mySymbol && txtSymbol.equals(symbol.getTextValue());
    }

    public void setColor(Color c) {
        this.setBackground(c);
    }

    public void clearColor() {
          this.setBackground(clearColor);
    }

    public String toString() {
        return "[" + txtSymbol.toString() + "]";
    }

    private void create(Fsymbol symbol) {
//        setColor(clearColor);
        setLayout(new java.awt.GridLayout(1, 1, 0, 0));
        setFont(new java.awt.Font("Courier New", 0, 12));

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        setToolTipText(Theme.toHtml(symbol.getFullInfo()));

        Dimension dim = UtilsFlowchart.getTextDimension(symbol.getDefinitionValue(), getFont());
        if (symbol.getName().contains(Mark.SQUARE_OPEN + "")) {
            setPreferredSize(new Dimension(Math.max(dim.width + 2, VAR_WITH), Math.max(dim.height, VAR_HEIGHT)));
        } else {
            setPreferredSize(new Dimension(Math.max(VAR_WITH * 3, dim.width + 7), VAR_HEIGHT));
        }

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, mySymbol.getFullInfo());
            }
        });

    }

}
