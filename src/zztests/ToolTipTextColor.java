/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzTests;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
 
import javax.swing.*;
 
public class ToolTipTextColor {
 
  private static void createAndShowUI() {
    JPanel tooltipPanel = new JPanel() {
      @Override
      public JToolTip createToolTip() {
        JToolTip tooltip = super.createToolTip();
        tooltip.setFont(tooltip.getFont().deriveFont(Font.BOLD, 32));
        tooltip.setForeground(Color.blue);
        tooltip.setOpaque(false);
         
        return tooltip;
      }
    };
    tooltipPanel.setPreferredSize(new Dimension(100, 100));
    tooltipPanel.setBackground(Color.pink);
    tooltipPanel.setToolTipText("hello");
     
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(400, 300));
     
    panel.add(tooltipPanel);
     
    JFrame frame = new JFrame("ToolTipTextColor");
    frame.getContentPane().add(panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
 
  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        createAndShowUI();
      }
    });
  }
}