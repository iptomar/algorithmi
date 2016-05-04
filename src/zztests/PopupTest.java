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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.DefaultEditorKit;

/**
 *
 * @author zulu
 */
public class PopupTest extends JFrame implements MouseListener
{
     JPopupMenu popup;

     public PopupTest()
     {
          popup = new JPopupMenu();
          popup.add( new DefaultEditorKit.CutAction() );
          popup.add( new DefaultEditorKit.CopyAction() );
          popup.add( new DefaultEditorKit.PasteAction() );

          JTextField textField = new JTextField("Right Click For Popup");
          textField.addMouseListener( this );
          getContentPane().add(textField);
     }

     public void mouseReleased(MouseEvent e)
     {
          if (e.isPopupTrigger())
          {
               popup.show(e.getComponent(), e.getX(), e.getY());
          }
     }

     public void mousePressed(MouseEvent e) {}
     public void mouseClicked(MouseEvent e) {}
     public void mouseEntered(MouseEvent e) {}
     public void mouseExited(MouseEvent e) {}

     public static void main(String[] args)
     {
          JFrame frame = new PopupTest();
          frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
          frame.pack();
          frame.setLocationRelativeTo( null );
          frame.setVisible(true);
     }
}