/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.editor.run.memory.panel;

import core.data.Fsymbol;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import core.parser.Mark;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author antoniomanso
 */
public class MemorySymbol extends JPanel {

    protected Fsymbol symbol;
    public List<MemoryCellVar> symbolList = new ArrayList<>(); // labels of arrays elements

    public MemorySymbol(Fsymbol symbol) {
        this.symbol = symbol;
        createUI();
        revalidate();
    }

    public String toString() {
        return "< " + symbolList.toString() + " >";
    }

    /**
     * update the display of the symbol if needed
     * returns the cell updated
     * 
     * @param newValue new value to cells
     * @return value updated
     */
    public MemoryCellVar update(Fsymbol newValue) {
        // cell changed
        MemoryCellVar changed = null;
        try {
            // is an Array
            if (newValue instanceof Farray) {
                //get new values of the arrays
                List<Fsymbol> elements = ((Farray) newValue).getElements();
                for (int i = 0; i < elements.size(); i++) {
                    //verify updates
                    if (!symbolList.get(i).isEqual(elements.get(i))) {
                        changed = symbolList.get(i);
                        changed.update(elements.get(i));
                    }
                }
            } //simple symbol
            else if (!symbolList.get(0).isEqual(newValue)) {
                changed = symbolList.get(0);
                changed.update(newValue);
            }
        } catch (Exception e) {
            //something wrong happens!!!!!! 
        }
        symbol = newValue; //update symbol
        return changed;
    }

    public void createUI() {
        if (symbol instanceof Farray) {
            Farray a = (Farray) symbol;
            int size = a.getNumberOfIndexes();
            switch (size) {
                case 1:
                    createArray2D(this, a.getElements(), 0, 1, a.getDimension(0), a.getName());
                    break;
                case 2:
                    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                    createArray2D(this, a.getElements(), 0, a.getDimension(0), a.getDimension(1), a.getName());
                    break;
                default:
                    buildPanel(this, symbol.getName());
                    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                    generatePanels(this, new ArrayList<Integer>(), a.getDimensions());
            }
        } else {
            buildPanel(this, symbol.getName());
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            MemoryCellVar var = new MemoryCellVar(symbol);
            symbolList.add(var);
            this.add(var);
        }
    }

    private void createArray2D(JPanel panel, List<Fsymbol> elements, int start, int lines, int rows, String title) {
        panel.setLayout(new GridLayout(lines, rows, 0, 0));
        buildPanel(panel, title);
        for (int i = 0; i < lines * rows; i++) {
            MemoryCellVar txt = new MemoryCellVar(elements.get(start + i));
            symbolList.add(txt);
            panel.add(txt);
        }
    }

    private static List<List<Integer>> generateIndexes(List<Integer> current, List<Integer> indexes) {
        List<List<Integer>> newList = new ArrayList<>();
        //stop recursivity
        if (indexes.isEmpty()) {
            newList.add(current);
            return newList;
        }
        //remove first dimentosn
        int size = indexes.remove(0);
        //iterate indexes
        for (int i = 0; i < size; i++) {
            List<Integer> elem = new ArrayList<>();
            //not first element
            if (!current.isEmpty()) {
                //clone current
                elem.addAll(current);
            }
            //add current index
            elem.add(i);
            //call with clone of indexes
            List<List<Integer>> result = generateIndexes(elem, new ArrayList<Integer>(indexes));
            //add lists
            newList.addAll(result);
        }
        //return result symbolList
        return newList;
    }

    private List<List<Integer>> generatePanels(JPanel myPanel, List<Integer> current, List<Integer> indexes) {
        List<List<Integer>> newList = new ArrayList<>();
        //:::::::::::::::::::::::::::::::::::: PANELS :::::::::::::
        //stop recursivity
        if (indexes.size() == 2) { // 2D
            newList.add(current);
            //:::::::::::::::::::::::::::::::::::: PANELS :::::::::::::
            Farray a = (Farray) symbol;
            int start = 0;
            int factor = indexes.get(0) * indexes.get(1);
            String txtIndex = "";
            for (int i = current.size() - 1; i >= 0; i--) {

                //add indexes to name
                txtIndex = Mark.SQUARE_OPEN + "" + current.get(i) + Mark.SQUARE_CLOSE + txtIndex;
                //update start
                start += current.get(i) * factor;
                factor *= a.getDimension(i);
            }
            txtIndex = symbol.getName() + txtIndex;
//            start += indexes.get(0) * a.getDimension(current.size()+1);
            System.out.println(txtIndex + " -> " + start);
            JPanel aPanel = new JPanel();
            createArray2D(aPanel, ((Farray) symbol).getElements(), start, indexes.get(0), indexes.get(1), txtIndex);
            myPanel.add(aPanel);
            //:::::::::::::::::::::::::::::::::::: PANELS :::::::::::::
            return newList;
        }
        //remove first dimentosn
        int size = indexes.remove(0);
        if (!current.isEmpty()) {
            //:::::::::::::::::::::::::::::::::::: PANELS :::::::::::::
            JPanel newPanel = new JPanel();
            newPanel.setLayout(new GridLayout(size, 0, 0, 0));
            buildPanel(newPanel, current);
            myPanel.add(newPanel);
            myPanel = newPanel;
            //:::::::::::::::::::::::::::::::::::: PANELS :::::::::::::
        }
        //iterate indexes
        for (int i = 0; i < size; i++) {
            List<Integer> elem = new ArrayList<>();
            List<List<Integer>> result;
            //not first element
            if (!current.isEmpty()) {
                //clone current
                elem.addAll(current);;
            }
            //add current index
            elem.add(i);
            //call with clone of indexes
            result = generatePanels(myPanel, elem, new ArrayList<Integer>(indexes));

            //add lists
            newList.addAll(result);
        }
        //return result symbolList
        return newList;
    }

    public void buildPanel(JPanel panel, List<Integer> lst) {
        StringBuilder txt = new StringBuilder(symbol.getName());
        for (Integer integer : lst) {
            txt.append(Mark.SQUARE_OPEN + "" + integer + Mark.SQUARE_CLOSE);
        }
        buildPanel(panel, txt.toString());
    }

    public void buildPanel(JPanel panel, String title) {
        panel.setBorder(BorderFactory.createTitledBorder(
                null, title,
                TitledBorder.LEFT,
                TitledBorder.BELOW_TOP,
                new Font("Courier New", Font.BOLD, 12))
        );

    }

    public static void main(String[] args) {
        List<Integer> lst = new ArrayList<>();
        lst.add(2);
        lst.add(3);
        lst.add(3);
        List<List<Integer>> x = generateIndexes(new ArrayList<Integer>(), lst);
        for (List<Integer> list : x) {
            System.out.println(list);
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604280910L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
