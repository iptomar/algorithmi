///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2015                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****     This software was build with the purpose of investigate and    ****/
///****     learning.                                                      ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package ui.utils;

import i18n.EditorI18N;
import ui.dialogs.FMessages;
import ui.flowchart.FeditorScrool;
import i18n.FkeyWord;
import flowchart.algorithm.GlobalMemoryGraph;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.Program;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Created on 6/set/2015, 11:33:03
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class ButtonsFluxTab extends JPanel {

    private static final int SIZE = 12;
    private final JTabbedPane pane;
    private final Program program;

    public ButtonsFluxTab(final JTabbedPane pane, Program program) {
        //unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        this.program = program;
        setOpaque(false);
        //----------------------------------------------------------------------
        //make JLabel read titles from JTabbedPane
        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(ButtonsFluxTab.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };

        add(label);
        //----------------------------------------------------------------------
        //add more space between the label and the button
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        //tab button
//        JButton saveButton = new SaveButton();
//        add(saveButton);
        JButton closeButton = new CloseButton();
        add(closeButton);

        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
                button.setEnabled(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
                button.setEnabled(false);
            }
        }
    };

    private class CloseButton extends JButton implements ActionListener {

        public CloseButton() {
            super(EditorI18N.loadIcon("APPLICATION.tabedPane.close.icon", SIZE));
            setToolTipText(EditorI18N.get("APPLICATION.tabedPane.close.help"));
            setPreferredSize(new Dimension(SIZE + 4, SIZE));
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorderPainted(false);
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            //Close the proper tab by clicking the button
            addActionListener(this);
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(ButtonsFluxTab.this);
            if (i != -1) {
                if (pane.getSelectedIndex() != i) {
                    pane.setSelectedIndex(i);
                } else {
                    if (program == null) {
                        pane.remove(i);
                        return;
                    }
                    int resp = FMessages.dialogYesNo("warning", EditorI18N.get("MESSAGE.closeTabQuestion"));
                    if (resp == 0) {
                        if (pane.getComponentAt(i) instanceof FeditorScrool) {
                            FeditorScrool fluxeditor = (FeditorScrool) pane.getComponentAt(i);
                            program.remove(fluxeditor.getFluxogramGraph());
                        }
                        pane.remove(i);

                    }
                }
            }
        }

    } // --------------------CLOSE BUTTON --------------------------------------

    private class SaveButton extends JButton implements ActionListener {

        public SaveButton() {
            super(EditorI18N.loadIcon("APPLICATION.tabedPane.save.icon", SIZE));
            setToolTipText(EditorI18N.get("APPLICATION.tabedPane.save.help"));
            setPreferredSize(new Dimension(SIZE + 4, SIZE));
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorderPainted(false);
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            //Close the proper tab by clicking the button
            addActionListener(this);
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(ButtonsFluxTab.this);
            if (i != -1) {
                if (pane.getSelectedIndex() != i) {
                    pane.setSelectedIndex(i);
                } else {
                    AlgorithmGraph flux = ((FeditorScrool) pane.getComponentAt(i)).getFluxogramGraph();
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    FileNameExtensionFilter filter;
                    //---------------------------------------------------------- MEMORY
                    if (flux instanceof GlobalMemoryGraph) {
                        filter = new FileNameExtensionFilter(
                                FkeyWord.get("PROGRAM.file.memory.filterText"),
                                FkeyWord.get("PROGRAM.file.memory.filterExt"));
                        chooser.setDialogTitle(FkeyWord.get("PROGRAM.file.memory.filterText"));
                    } else { //--------------------------------------------------- FUNCTIONS
                        filter = new FileNameExtensionFilter(
                                FkeyWord.get("PROGRAM.file.function.filterText"),
                                FkeyWord.get("PROGRAM.file.function.filterExt"));
                        chooser.setDialogTitle(FkeyWord.get("PROGRAM.file.function.filterText"));
                    }
                    chooser.setFileFilter(filter);
                    int returnVal = chooser.showSaveDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        String path = chooser.getSelectedFile().getAbsolutePath();
                        try {
                            flux.save(path);
                        } catch (Exception ex) {
                            FMessages.dialog("error", EditorI18N.get("APPLICATION.fileSavedError", path, ex.getMessage()));
                        }
                    }
                }
            }
        }

    } // --------------------CLOSE BUTTON --------------------------------------
    //---------------------------- BOTTONS -------------------------------------
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
