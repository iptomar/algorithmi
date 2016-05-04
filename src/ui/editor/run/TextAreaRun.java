/*
 * Copyright (c) 2015 Instituto Politecnico de Tomar. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
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
package ui.editor.run;

import flowchart.shape.Fshape;
import flowchart.utils.TextUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * Created on 14/set/2015, 18:50:54
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class TextAreaRun extends JScrollPane {

    JTextPane txt;
    int counter = 0;

    public TextAreaRun() {
        super();
        txt = new JTextPane();
        JPanel noWrapPanel = new JPanel(new BorderLayout());
        noWrapPanel.add(txt,BorderLayout.CENTER);
        setViewportView(noWrapPanel);
        txt.setFont(new Font("monospaced", Font.BOLD, 14));
        txt.setBackground(Color.LIGHT_GRAY);
        txt.setForeground(Color.BLACK);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(20, 20);
    }

    public void reset() {
        txt.setText("");
        counter = 0;
    }

    public void append(Fshape shape) {
        if (shape != null) {
            counter++;
            addText(shape.getType().trim(), shape.getExecutionResult().trim(), shape.getIntructionPlainText().trim());
        }
    }

    public void append(String str) {
        addText("", str.trim(), "");
    }

    public void setText(String str) {
        txt.setText(str);
       
    }

    public void addText(final String type, final String result, final String instruction) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                int pos = txt.getText().length();
                try {                     
                    StyleContext sc = new StyleContext();
                    Style style = sc.getStyle(StyleContext.DEFAULT_STYLE);
                    Document doc = txt.getDocument();
                    StyleConstants.setForeground(style, Color.BLACK);
                    doc.insertString(doc.getLength(), TextUtils.setSize("" + counter, 10), style);
                    StyleConstants.setForeground(style, Color.BLUE);
                    doc.insertString(doc.getLength(), TextUtils.setSize(" " + result, 25) + " ", style);                                        
                    StyleConstants.setForeground(style, Color.RED);
                    doc.insertString(doc.getLength(), TextUtils.setSize(type, 12), style);
                    StyleConstants.setForeground(style, Color.BLACK);
//                    StyleConstants.setForeground(style, Color.RED);
                    doc.insertString(doc.getLength(), instruction+"\n", style);
                    txt.setCaretPosition(pos);
                } catch (Exception ex) {
                }
                
            }
        });
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509141850L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
