/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzTests;

import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

/**
 *
 * @author pesta
 */
public class DnD_Test {
    
    static class MyTransferHandler extends TransferHandler{

        @Override
        public int getSourceActions(JComponent jc) {
            return COPY_OR_MOVE;
        }
        
        @Override
        protected Transferable createTransferable(JComponent jc) {
            JList l = (JList)jc;
            return new StringSelection(l.getSelectedValue().toString());   
        }
        
        @Override
        protected void exportDone(JComponent jc, Transferable t, int action) {
            if(action == MOVE){
                JList l = (JList)jc;
                DefaultListModel dlm = (DefaultListModel)l.getModel();
                dlm.remove(l.getSelectedIndex());
            }
        }

        @Override
        public boolean canImport(TransferSupport ts) {
            if(!ts.isDataFlavorSupported(DataFlavor.stringFlavor))
                return false;
            return true;
        }

        @Override
        public boolean importData(TransferSupport ts) {
            if(!ts.isDrop())
                return false;
            
            JList l = (JList)ts.getComponent();
            String data;
            
            try{
                data = (String)ts.getTransferable().getTransferData(DataFlavor.stringFlavor);
            }
            catch(Exception ex){
                return false;
            }
            
            DefaultListModel dlm = (DefaultListModel)l.getModel();
            JList.DropLocation dl = (JList.DropLocation)ts.getDropLocation();
            int index = dl.getIndex();
            dlm.add(index, data);
            return true;
        }

    }
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame();
                f.setLayout(new FlowLayout());
                
            }
        });
        
    }
    
}
