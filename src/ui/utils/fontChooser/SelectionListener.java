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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;
 
public class SelectionListener implements ListSelectionListener {
 
    private FieldListener fieldListener;
 
    private JList list;
 
    private JTextField textField;
 
    public void setFieldListener(FieldListener fieldListener) {
        this.fieldListener = fieldListener;
    }
 
    public void setList(JList list) {
        this.list = list;
    }
 
    public void setTextField(JTextField textField) {
        this.textField = textField;
    }
 
    @Override
    public void valueChanged(ListSelectionEvent event) {
        setFieldValue();
    }
 
    private void setFieldValue() {
        Document document = textField.getDocument();
        document.removeDocumentListener(fieldListener);
        Object object = list.getSelectedValue();
        if (object != null) {
            textField.setText(object.toString());
        }
        document.addDocumentListener(fieldListener);
    }
 
}