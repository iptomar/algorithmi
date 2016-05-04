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

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 *
 * @author zulu
 */
public class TestFrame extends JFrame {

    private JTextArea area;
    private JLabel l;


    public TestFrame() {
        init();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void init() {
        area = new JTextArea(5,5);
        area.addMouseMotionListener(getListener());
        l = new JLabel(" ");

        add(new JScrollPane(area));
        add(l,BorderLayout.SOUTH);
    }

    private MouseMotionListener getListener() {
        return new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent e) {

                int viewToModel = area.viewToModel(e.getPoint());
                if(viewToModel != -1){
                    try {
                        l.setText("line: "+(1+area.getLineOfOffset(viewToModel)));
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            }
        };
    }


    public static void main(String args[]) {
        new TestFrame();
    }

}
