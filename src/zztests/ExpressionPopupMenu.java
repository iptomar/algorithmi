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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzTests;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

/**
 *
 * @author zulu
 */
public class ExpressionPopupMenu extends JPopupMenu {

    JTextComponent txt;

    public ExpressionPopupMenu(JTextComponent txt) {
        this.txt = txt;
        this.add("teste");
        this.add("teste");
        this.add("teste");
        this.add("teste");
        
    }
    public void show(int x, int y){
        Point location = new Point(x,y);
        try {
            location= txt.modelToView( txt.getCaretPosition() ).getLocation();
        } catch (BadLocationException ex) {
        }
        

    }
    
   

}
