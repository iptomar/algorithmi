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
package ui.flowchart.expression;

import core.Memory;
import flowchart.algorithm.Program;
import ui.FProperties;

import ui.flowchart.expression.menu.ExpressionPopup;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;
import javax.swing.UIDefaults;

/**
 *
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class TextExpression extends JTextPane {

    ExpressionPopup popup = null;  // popup menu
    FsyntaxHighlight sintax = null; // syntac coloring
    String prefixKeyword = "";
    private Memory memory;
    private Program program;

    public TextExpression() {
        setOpaque(false);

        sintax = new FsyntaxHighlight();
        setDocument(sintax);
        popup = new ExpressionPopup(this);
        this.add(popup);
        final TextExpression me = this;
        //---------------------------------------------------- MOUSE LISTENER
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
               // System.out.println(evt.getButton());
                if (evt.getButton() == 3) {
                    Point point = getCaret().getMagicCaretPosition();
                    popup.show(me, point.x, point.y);
                }
            }
        });
        
        //---------------------------------------------------- KEY LISTENER
        addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if (prefixKeyword.isEmpty()) {
                    return;
                }
                if (getCaretPosition() <= prefixKeyword.length() + 1
                        && (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE
                        || ke.getKeyCode() == KeyEvent.VK_DELETE)) {
                    ke.consume();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (prefixKeyword.isEmpty()) {
                    return;
                }
                if (getCaretPosition() <= prefixKeyword.length()) {
                    e.consume();
                }
                if (getText().length() <= prefixKeyword.length()) {
                    setText(prefixKeyword + " ");
                    e.consume();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                }

            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (prefixKeyword.isEmpty()) {
                    return;
                }
                if (getCaretPosition() <= prefixKeyword.length() 
                        && (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE
                        || ke.getKeyCode() == KeyEvent.VK_DELETE)) {
                    ke.consume();
                }
            }
        });

    }

    public void paintBackground() {

        UIDefaults defaults = new UIDefaults();
        defaults.put("TextPane[Enabled].backgroundPainter", FProperties.colorSintaxBackGround);
        putClientProperty("Nimbus.Overrides", defaults);
        putClientProperty("Nimbus.Overrides.InheritDefaults", true);
        setBackground(FProperties.colorSintaxBackGround);
    }

    public void setKeyword(String exp) {
        this.prefixKeyword = exp;
        setText(prefixKeyword + " ");
    }

    public String getInstruction() {
        String str = getText().substring(prefixKeyword.length()).trim();
        return str.replaceAll("\n", " ");
    }

    public void setInstruction(String instruction) {
        if (!prefixKeyword.isEmpty()) {
            setText((prefixKeyword + " " + Identation.ident(instruction, memory, program)));
        } else {
            setText(Identation.ident(instruction, memory, program));
        }
        setCaretPosition(getText().length());
    }

    public void updateMenu(Memory memory, Program prog) {
        this.memory = memory;
        this.program = prog;
        popup.updateMenu(memory, prog);
        sintax.setMemory(memory);
        sintax.setProgram(prog);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
