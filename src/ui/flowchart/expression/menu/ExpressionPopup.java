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
package ui.flowchart.expression.menu;

import core.Memory;
import core.data.Fsymbol;
import core.evaluate.CoreElement;
import core.evaluate.aritmetic.Sum;
import core.evaluate.aritmetic.Div;
import core.evaluate.aritmetic.Mod;
import core.evaluate.aritmetic.Mult;
import core.evaluate.aritmetic.Negative;
import core.evaluate.aritmetic.Power;
import core.evaluate.aritmetic.Sub;
import core.evaluate.function.trigonometry.ACos;
import core.evaluate.function.trigonometry.ASin;
import core.evaluate.function.trigonometry.ATan;
import core.evaluate.function.trigonometry.Abs;
import core.evaluate.function.trigonometry.Cos;
import core.evaluate.function.trigonometry.CosH;
import core.evaluate.function.math.Exp;
import core.evaluate.function.Int;
import core.evaluate.function.math.Ln;
import core.evaluate.function.math.Log;
import core.evaluate.function.math.Max;
import core.evaluate.function.math.Min;
import core.evaluate.function.math.Pow;
import core.evaluate.function.math.Random;
import core.evaluate.function.math.Round;
import core.evaluate.function.trigonometry.Sin;
import core.evaluate.function.trigonometry.SinH;
import core.evaluate.function.math.Sqrt;
import core.evaluate.function.text.Align;
import core.evaluate.function.trigonometry.Tan;
import core.evaluate.function.trigonometry.TanH;
import core.evaluate.logic.And;
import core.evaluate.logic.Not;
import core.evaluate.logic.Or;
import core.evaluate.relational.Different;
import core.evaluate.relational.Equal;
import core.evaluate.relational.Greater;
import core.evaluate.relational.GreaterOrEqual;
import core.evaluate.relational.Less;
import core.evaluate.relational.LessOrEqual;
import flowchart.algorithm.FunctionGraph;
import flowchart.algorithm.Program;
import ui.flowchart.expression.TextExpression;
import i18n.Fi18N;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;

import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.ToolTipManager;
import javax.swing.text.BadLocationException;

/**
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class ExpressionPopup extends JPopupMenu {

    TextExpression txtComponent;
    public Memory mem;
    JMenu memoryMenu;
    JMenu functionMenu;

    public ExpressionPopup(TextExpression txt) {

        this.txtComponent = txt;

        memoryMenu = new JMenu(Fi18N.get("STRING.variables"));
//        memoryMenu.setVisible(false);
        add(memoryMenu);
        functionMenu = new JMenu(Fi18N.get("STRING.funcoes"));
//        functionMenu.setVisible(false);
        add(functionMenu);

        JMenu consts = new JMenu(Fi18N.get("STRING.constants"));
        for (Fsymbol var : Memory.constants.getMem()) {
            consts.add(new PopupExpressionSymbol(var, this));
        }
        add(consts);

        JMenu menuOperators = new JMenu(Fi18N.get("OPERATOR.name"));
        add(menuOperators);

        JMenu arithmetics = new JMenu(Fi18N.get("OPERATOR.arithmetics.name"));
        arithmetics.add(buildItem(new Sum()));
        arithmetics.add(buildItem(new Sub()));
        arithmetics.add(new JSeparator());
        arithmetics.add(buildItem(new Mult()));
        arithmetics.add(buildItem(new Div()));
        arithmetics.add(buildItem(new Mod()));
        arithmetics.add(new JSeparator());
        arithmetics.add(buildItem(new Power()));
        arithmetics.add(new JSeparator());
        arithmetics.add(buildItem(new Negative()));
        menuOperators.add(arithmetics);

        JMenu relationals = new JMenu(Fi18N.get("OPERATOR.relationals.name"));
        relationals.add(buildItem(new Equal()));
        relationals.add(buildItem(new Different()));
        relationals.add(new JSeparator());
        relationals.add(buildItem(new Greater()));
        relationals.add(buildItem(new GreaterOrEqual()));
        relationals.add(buildItem(new Less()));
        relationals.add(buildItem(new LessOrEqual()));
        menuOperators.add(relationals);

        JMenu logics = new JMenu(Fi18N.get("OPERATOR.logics.name"));
        logics.add(buildItem(new Or()));
        logics.add(buildItem(new And()));
        logics.add(buildItem(new Not()));
        menuOperators.add(logics);

        JMenu funcs = new JMenu(Fi18N.get("FUNCTION.popupMenu.functions"));
        add(funcs);
        //-----------------------------------------------------------------------
        JMenu trigonomery = new JMenu(Fi18N.get("FUNCTION.popupMenu.functions.trigonometry"));
        trigonomery.add(buildItem(new Sin()));
        trigonomery.add(buildItem(new Cos()));
        trigonomery.add(buildItem(new Tan()));
        trigonomery.add(buildItem(new ASin()));
        trigonomery.add(buildItem(new ACos()));
        trigonomery.add(buildItem(new ATan()));
        trigonomery.add(buildItem(new SinH()));
        trigonomery.add(buildItem(new CosH()));
        trigonomery.add(buildItem(new TanH()));
        funcs.add(trigonomery);
        //-----------------------------------------------------------------------
        //-----------------------------------------------------------------------
        JMenu math = new JMenu(Fi18N.get("FUNCTION.popupMenu.functions.math"));
        math.add(buildItem(new Abs()));
        math.add(buildItem(new Exp()));
        math.add(buildItem(new Int()));
        math.add(buildItem(new Log()));
        math.add(buildItem(new Ln()));
        math.add(buildItem(new Min()));
        math.add(buildItem(new Max()));
        math.add(buildItem(new Pow()));
        math.add(buildItem(new Sqrt()));
        math.add(buildItem(new Random()));
        math.add(buildItem(new Round()));
        funcs.add(math);
        //-----------------------------------------------------------------------
        //-----------------------------------------------------------------------
        //-----------------------------------------------------------------------
        JMenu text = new JMenu(Fi18N.get("FUNCTION.popupMenu.functions.text"));
        text.add(buildItem(new Align()));
        funcs.add(text);
        //-----------------------------------------------------------------------

//        for (CoreElement func : CoreCalculator.functions) {
//            funcs.add(buildItem(func));
//        }
        //----------------------------------------------------------------------
        //ESCUTAR O RATO
        //----------------------------------------------------------------------
        final JPopupMenu popup = this;
        txtComponent.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
//                ToolTipManager.sharedInstance().setDismissDelay(60000);
//                ToolTipManager.sharedInstance().setInitialDelay(0);
                if (e.isPopupTrigger()) {
                    try {
                        Point p = txtComponent.modelToView(txtComponent.getCaretPosition()).getLocation();
                        popup.show(e.getComponent(), p.x, p.y);
                    } catch (BadLocationException ex) {
                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }

                }
            }
        });
        //----------------------------------------------------------------------
        //----------------------------------------------------------------------    
//        txtComponent.setSelectionColor(Color.YELLOW);
//        txtComponent.setSelectedTextColor(Color.RED);
    }

    public void updateMenu(Memory mem, Program prog) {
        memoryMenu.removeAll();
        if (mem != null && !mem.isEmpty()) { // se não estiver vazio
            for (Fsymbol var : mem.getMem()) {
                memoryMenu.add(new PopupExpressionSymbol(var, this));
            }
            memoryMenu.setVisible(true);
        } else {
            memoryMenu.setVisible(false);
        }
        functionMenu.removeAll();
        if (!prog.getFunctions().isEmpty()) {
            for (FunctionGraph func : prog.getFunctions()) {
                functionMenu.add(new PopupExpressionFunction(func, this));
            }
            functionMenu.setVisible(true);
        } else {
            functionMenu.setVisible(false);
        }
    }

    private PopupExpressionItem buildItem(CoreElement calc) {
        return new PopupExpressionItem(
                calc.getSymbol(),
                calc.getDifinition(),
                calc.getHelp(),
                this);
    }

    public void updateText(String newTxt) {
        int start = txtComponent.getSelectionStart();
        int end = txtComponent.getSelectionEnd();

        if (start != end) {
            String x1 = txtComponent.getText().substring(0, start);
            String x2 = txtComponent.getText().substring(end);
            txtComponent.setText(x1 + newTxt + x2);
            txtComponent.setCaretPosition(x1.length() + newTxt.length());
            txtComponent.requestFocus();
        } else {
            String x1 = txtComponent.getText().substring(0, txtComponent.getCaretPosition());
            String x2 = txtComponent.getText().substring(txtComponent.getCaretPosition());
            txtComponent.setText(x1 + " " + newTxt + " " + x2);
            txtComponent.setCaretPosition(x1.length() + newTxt.length() + 2);
            txtComponent.requestFocus();
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
