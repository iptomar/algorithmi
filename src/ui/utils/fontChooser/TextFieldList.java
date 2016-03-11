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

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;
 

 
public class TextFieldList extends JPanel {
 
    private static final long serialVersionUID = 3537247762597141685L;
 
    private int rightMargin;
 
    private JList jlist;
 
    private JTextField textField;
 
    private ListModel model;
 
    private SelectionListener selectionListener;
 
    public TextFieldList(ListModel model) {
        this(model, 10);
    }
 
    public TextFieldList(ListModel model, int rightMargin) {
        this.model = model;
        this.rightMargin = rightMargin;
        this.selectionListener = new SelectionListener();
        createPartControl();
    }
 
    private void createPartControl() {
        FieldListener fieldListener = new FieldListener();
 
        new JPanel();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
 
        textField = new JTextField();
        Document document = textField.getDocument();
        document.addDocumentListener(fieldListener);
        add(textField);
 
        jlist = new JList(model);
        jlist.addListSelectionListener(selectionListener);
        jlist.setCellRenderer(new BorderListCellRenderer(rightMargin));
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(jlist);
        add(scrollPane);
 
        fieldListener.setList(jlist);
        fieldListener.setModel(model);
        fieldListener.setSelectionListener(selectionListener);
        fieldListener.setTextField(textField);
 
        selectionListener.setFieldListener(fieldListener);
        selectionListener.setList(jlist);
        selectionListener.setTextField(textField);
    }
 
    public void setVisibleRowCount(int count) {
        jlist.setVisibleRowCount(count);
    }
 
    public void setSelectedValue(Object object) {
        jlist.setSelectedValue(object, true);
    }
 
    public void setSelectedIndex(int index) {
        jlist.setSelectedIndex(index);
    }
 
    public Object getSelectedValue() {
        return jlist.getSelectedValue();
    }
 
    public int getSelectedIndex() {
        return jlist.getSelectedIndex();
    }
 
    public void addListSelectionListener(ListSelectionListener listener) {
        jlist.addListSelectionListener(listener);
    }
 
    public JList getJList() {
        return jlist;
    }
 
    public JTextField getTextField() {
        return textField;
    }
 
}