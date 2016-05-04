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

package ui.utils.fontChooser;

 
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
 
 
public class FieldListener implements DocumentListener {
 
    private JList list;
 
    private JTextField textField;
 
    private ListModel model;
 
    private SelectionListener selectionListener;
 
    public void setList(JList list) {
        this.list = list;
    }
 
    public void setTextField(JTextField textField) {
        this.textField = textField;
    }
 
    public void setModel(ListModel model) {
        this.model = model;
    }
 
    public void setSelectionListener(SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }
 
    @Override
    public void changedUpdate(DocumentEvent event) {
    }
 
    @Override
    public void insertUpdate(DocumentEvent event) {
        setListSelection(event);
    }
 
    @Override
    public void removeUpdate(DocumentEvent event) {
        setListSelection(event);
    }
 
    private void setListSelection(DocumentEvent event) {
        Document document = textField.getDocument();
        try {
            String s = document.getText(0, document.getLength());
            if (s.equals("")) {
                list.clearSelection();
                list.ensureIndexIsVisible(0);
            } else {
                ComparisonResult result = searchModel(s);
                list.removeListSelectionListener(selectionListener);
                list.ensureIndexIsVisible(model.getSize() - 1);
                if (result.isExactMatch()) {
                    list.setSelectedIndex(result.getListIndex());
                } else {
                    list.clearSelection();
                    list.ensureIndexIsVisible(result.getListIndex());
                }
                list.addListSelectionListener(selectionListener);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
 
    private ComparisonResult searchModel(String searchTerm) {
        for (int i = 0; i < model.getSize(); i++) {
            Object object = model.getElementAt(i);
            String s = object.toString().toLowerCase();
            String t = searchTerm.toLowerCase();
            if (s.equals(t)) {
                return new ComparisonResult(i, true);
            } else if (s.startsWith(t)) {
                return new ComparisonResult(i, false);
            }
        }
        return new ComparisonResult(0, false);
    }
 
}