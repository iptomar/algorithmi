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
package ui.editor.run.memory;

import core.data.Fsymbol;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 * Created on 26/out/2015, 7:43:11
 *
 * @author zulu - computer
 */
public class MemoryCellRenderer implements TreeCellRenderer {

    CellMemorySymbol renderer;

    DefaultTreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer();
    Color backgroundSelectionColor;
    Color backgroundNonSelectionColor;

    public MemoryCellRenderer() {

        backgroundSelectionColor = defaultRenderer
                .getBackgroundSelectionColor();
        backgroundNonSelectionColor = defaultRenderer
                .getBackgroundNonSelectionColor();
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        Component returnValue = null;
        if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
            Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
            if (userObject instanceof Fsymbol) {
                renderer = new CellMemorySymbol((Fsymbol) userObject);

                if (selected) {
                    renderer.setBackground(backgroundSelectionColor);
                } else {
                    renderer.setBackground(backgroundNonSelectionColor);
                }
                renderer.setEnabled(tree.isEnabled());
                returnValue = renderer;
            }
        }
        if (returnValue == null) {
            returnValue = defaultRenderer.getTreeCellRendererComponent(tree,
                    value, selected, expanded, leaf, row, hasFocus);
        }
        return returnValue;
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510260743L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
