/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzTests;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author zulu
 */
public class ColoredToolTipExample extends JFrame {

    private static final long serialVersionUID = 1L;

    public ColoredToolTipExample() {
        Border line, raisedbevel, loweredbevel, title, empty;
        line = BorderFactory.createLineBorder(Color.black);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        loweredbevel = BorderFactory.createLoweredBevelBorder();
        title = BorderFactory.createTitledBorder("");
        empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
        Border compound;
        compound = BorderFactory.createCompoundBorder(empty, line);
        UIManager.put("ToolTip.foreground", new ColorUIResource(Color.red));
        UIManager.put("ToolTip.background", new ColorUIResource(Color.yellow));
        UIManager.put("ToolTip.font", new FontUIResource(new Font("Verdana", Font.PLAIN, 18)));
        UIManager.put("ToolTip.border", new BorderUIResource(compound));
        JButton button = new JButton("Hello, world");
        button.setToolTipText("<html> - myText <br> - myText <br> - myText <br>");
        getContentPane().add(button);
        JFrame frame = new JFrame("Colored ToolTip Example");
        frame.add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ColoredToolTipExample();
            }
        });
    }
}