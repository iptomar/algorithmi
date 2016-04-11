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
package flowchart.shape;

import core.data.exception.FlowchartException;
import ui.FProperties;
import ui.flowchart.dialogs.Fdialog;
import i18n.Fi18N;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.run.GraphExecutor;
import flowchart.arrow.Arrow;
import flowchart.utils.Theme;
import flowchart.utils.UtilsFlowchart;
import i18n.FkeyWord;
import i18n.FkeywordToken;
import ui.FLog;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.ToolTipManager;
import languages.PseudoLanguage;

/**
 *
 * @author ZULU
 */
public abstract class Fshape extends JPanel implements MouseListener, Serializable, Cloneable, Comparable<Fshape> {

    public static boolean TOOLTIP_DEBUG = false;

    //change tooltips behaviour int he application
    // in the furure this is moved to another place
    static {
        //https://stackoverflow.com/questions/1190290/is-there-a-way-to-set-the-delay-time-of-tooltips-being-displayed-on-a-particular
        ToolTipManager.sharedInstance().setDismissDelay(10000);
        ToolTipManager.sharedInstance().setInitialDelay(10);
    }
    //background color of the shapes
    public static Color BACKGROUND_COLOR = Color.WHITE;
    //minimum Size of Patterns
    public static int MINIMUM_SIZE = 5; // factor to multiply zoom

    //font of the text - changing the font will change the size of the shape
    public static Font myFONT = FProperties.getFont();
    //connectors
    public Fshape parent = null;
    public Fshape next = null;
    public Fshape left = null;
    public Fshape right = null;
    //level of the shape in the flowchart
    public int level = 0;
    //coments to the instruction
    public String comments = "";

    //Logical flowchar where the pattern is included
    public AlgorithmGraph algorithm;
    //Text of the intruction type
    public String type = "";
    //Text of the instruction
    private String instruction = "";

//-------- GUI of the shape --------------------------------------    
//pattern interface - show the shape
    protected BorderFlowChart border;
    //text of the shape
    protected JLabel txt = new JLabel();

    //menu of the left mouse button
    public abstract void editMenu(int x, int y);
    //enabled the edition of the shape
    public boolean isEditable = true;
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::    
//-------- API of the shape --------------------------------------

    public abstract boolean parseShape() throws FlowchartException; //---------- verify if the text of the shape is OK

    public abstract String getType();//----------------------------------------- textual type of shape    

    public abstract String getIntructionPlainText();  //----------------------------- Instruction in plain text

    public abstract Fshape execute(GraphExecutor exe) throws FlowchartException; // Execute shape

    public abstract String getExecutionResult(); //------------------------------ gets the value at runtime ( usualy the evaluations of the expression)

    public abstract String getPseudoTokens(); //------------------------------ return  textual tokens to translate to another language.

    public abstract String getPseudoCode() throws FlowchartException; //------ build Portugol code

    /**
     * build a shape components based in the text instruction
     *
     * @param instr textual intruction
     * @param comments comments
     */
    public abstract void buildInstruction(String instr, String comments) throws FlowchartException;//{
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::    

    /**
     * Create a shape
     *
     * @param text thext of the
     * @param border GUI of shape
     * @param algorithm flowchart of the shape
     */
    public Fshape(String text, BorderFlowChart border, AlgorithmGraph algorithm) {
        setBorder(border);
        this.border = border;
        this.algorithm = algorithm;
        type = getType();
        instruction = text;
        initComponents();
    }

    protected void initComponents() {
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());
        txt.setBorder(null);
        txt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(txt, java.awt.BorderLayout.CENTER);
        setInstruction(instruction);
        updateFont();
        addMouseListener(this);
        repaint();
    }

    public boolean isOk() {
        try {
            return parseShape();
        } catch (Exception ex) {
            return false;
        }
    }

    public void updateFont() {
        updateFont(myFONT);
    }

    public void updateFont(Font newFont) {
        txt.setFont(newFont);
        updateSize();
        this.border.setBorderSize(newFont.getSize() / 4 + 2);
    }

    /**
     * return cont of the Font of Text
     *
     * @return
     */
    public Font getTxtFont() {
        return txt.getFont();
    }

    public void setInstruction(String text) {
        if (instruction.compareTo(text) != 0) {
            this.instruction = text;
            algorithm.alignPatterns();
        }
        txt.setText(Theme.shapeText(this));
    }

    public void setRawInstruction(String text) {
        this.instruction = text;
        txt.setText(Theme.shapeText(this));
    }

    public String getInstruction() {
        return instruction;
    }

    public String getFullInstruction() {
        return type + " " + instruction;
    }

    public void updateSize() {
//        Dimension oldDims = getSize();
        Dimension dims = getPreferedDimension();
//        int cx = getX() + oldDims.width/2;
//        int cy = getY() + oldDims.height/2;
        setSize(dims.width, dims.height);
//        setLocation(cx + (dims.width - oldDims.width)/2,
//                cy+ (dims.height-oldDims.height)/2);
    }

    protected Dimension getPreferedDimension() {
        Dimension text = UtilsFlowchart.getTextDimension(this);
        if (text.getWidth() < MINIMUM_SIZE * getZoom()) {
            text.width = MINIMUM_SIZE * getZoom();
        }
        Insets limit = getBorder().getBorderInsets(this);
        return new Dimension(text.width + limit.left + limit.right, text.height + limit.top + limit.bottom);
    }

    public void setSelected(boolean selected) {
        //parse instruction
        try {
            parseShape();
//            if (!comments.isEmpty()) {
            this.setToolTipText(Theme.shapeComments(this));
//            } else {
//                this.setToolTipText(null);
//            }
            if (TOOLTIP_DEBUG) {
                this.setToolTipText(Theme.shapeDebugComments(this));//-------------------- DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG  DEBUG 
            }//            }
        } catch (FlowchartException ex) {
            FLog.printLn("FShape public void setSelected(boolean selected) " + getInstruction());
            border.setError();
            this.setToolTipText(ex.getHtmlMessage());
            return;
        }

        if (selected) {
            border.setSelected();
        } else {
            border.setNotselected();
        }
        repaint();
//        if (this instanceof Arrow) {
//            parent.repaint();
//        }

    }

    //--------------------------------------------------
    //   MOUSE EVENTS 
    //---------------------------------------------------
    public void mousePressed(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {

        //   System.out.println(this.getClass().getSimpleName() + " " + getToolTipText());
        if (isEditable) {
            setSelected(true);
        } else {
            //Runtime tooltip
//            String result = getExecutionResult().trim();
//            if (result.isEmpty()) {
//                this.setToolTipText(null);
//            } else {
            this.setToolTipText(getExecutionResult());
//            }
        }
    }

    public void mouseExited(MouseEvent e) {
        if (isEditable) {
            setSelected(false);
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        if (!isEditable) {
            return;
        }
        int x = getX() + e.getX() + algorithm.graph.getX();
        int y = getY() + e.getY() + algorithm.graph.getY();

        if (e.getButton() == MouseEvent.BUTTON1) {
            editMenu(x, y);
            //algorithm.parse();
            
            //Fire Event
            //algorithm.FireEvent(algorithm.myProgram.getTokens());
            
            algorithm.refresh();

        } else if (e.getButton() == MouseEvent.BUTTON3) {
            popupMenu(e.getX(), e.getY());
        }
    }

    public void popupMenu(int x, int y) {
        final Fshape obj = this;
        JPopupMenu rightMenu = new JPopupMenu();
        //--------------------------------- DELETE --------------
        JMenuItem mnCopy = new JMenuItem();
        Fi18N.loadMenuItem(mnCopy, "BUTTON.copy", 32);
        mnCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Fdialog.showMessage("Not implemented");
            }
        });
        //--------------------------------- DELETE --------------
        JMenuItem mnDelete = new JMenuItem();
        Fi18N.loadMenuItem(mnDelete, "BUTTON.delete", 32);
        mnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                algorithm.removePattern(obj);
                algorithm.parse();
                
                //Fire Event For Shape Removal
                //algorithm.FireEvent(algorithm.myProgram.getTokens());
            }
        });
        rightMenu.add(mnDelete);
        rightMenu.add(new JSeparator());
        rightMenu.add(mnCopy);
        rightMenu.show(this, x, y);
    }

    public void setZoom(int zoom) {
        if (zoom >= 1 && zoom < 250) {
            Font f = txt.getFont();
            setMytFont(new Font(f.getName(), f.getStyle(), zoom));
            updateFont(new Font(txt.getFont().getFamily(), txt.getFont().getStyle(), zoom));
        }
    }

    public int getZoom() {
        return txt.getFont().getSize();
    }

    public static int setStaticZoom(int zoom) {
        return myFONT.getSize();
    }

    public static int getStaticZoom() {
        return myFONT.getSize();
    }

    public void zoomIn() {
        setZoom(getZoom() + 2);
    }

    public void zoomOut() {
        setZoom(getZoom() - 2);
    }

    public static void setMytFont(Font f) {
        myFONT = f;
        BorderFlowChart.setThickness(myFONT.getSize() / 4);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        if (this instanceof Arrow) {
            str.append(" " + UtilsFlowchart.setStringSize(parent.getType(), 8));
            str.append(" =>=> " + UtilsFlowchart.setStringSize(next.getType(), 8));
        } else {
            if (getInstruction().isEmpty()) {
                str.append(" " + UtilsFlowchart.setStringSize(getType(), 20));
            } else {
                str.append(" " + UtilsFlowchart.setStringSize(getInstruction(), 20));
            }
        }
        return str.toString();
    }

    public String toStringDebug() {
        StringBuilder str = new StringBuilder(POSITION_LEVEL + "\t level " + level + " ");
        str.append(UtilsFlowchart.setStringSize(this.getClass().getSimpleName(), 20));
        str.append("<" + UtilsFlowchart.setStringSize(toString(), 20));
        str.append("> UP [" + (parent == null ? UtilsFlowchart.setStringSize("NULO", 20) : UtilsFlowchart.setStringSize(parent.toString(), 20)));
        str.append("] NEXT [" + (next == null ? UtilsFlowchart.setStringSize("NULO", 20) : UtilsFlowchart.setStringSize(next.toString(), 20)));
        str.append("] LEFT [" + (left == null ? UtilsFlowchart.setStringSize("NULO", 20) : UtilsFlowchart.setStringSize(left.toString(), 20)));
        str.append("] RIGHT [" + (right == null ? UtilsFlowchart.setStringSize("NULO", 20) : UtilsFlowchart.setStringSize(right.toString(), 20)));
        return str.toString();
    }

    public String toDebugTooltip() {
        StringBuilder str = new StringBuilder(POSITION_LEVEL + "\t level " + level + " ");
        str.append(UtilsFlowchart.setStringSize(this.getClass().getSimpleName(), 20));
        str.append("<" + UtilsFlowchart.setStringSize(toString(), 20));
        str.append(">\nUP [" + (parent == null ? UtilsFlowchart.setStringSize("NULO", 20) : UtilsFlowchart.setStringSize(parent.toString(), 20)));
        str.append("]\n NEXT [" + (next == null ? UtilsFlowchart.setStringSize("NULO", 20) : UtilsFlowchart.setStringSize(next.toString(), 20)));
        str.append("]\n LEFT [" + (left == null ? UtilsFlowchart.setStringSize("NULO", 20) : UtilsFlowchart.setStringSize(left.toString(), 20)));
        str.append("]\n RIGHT [" + (right == null ? UtilsFlowchart.setStringSize("NULO", 20) : UtilsFlowchart.setStringSize(right.toString(), 20)));

        str.append("\n\n Position [ " + getLocation().x + " , " + getLocation().y + " ]");
        str.append("\nSize [ " + getWidth() + "  , " + getHeight() + " ]");
        str.append("\n maxY " + maxY);
        return str.toString();
    }

    public String getComments() {
        return comments;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: COMMENTS
    public static String COMMENT_STR = FkeyWord.get("KEYWORD.comments");

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public String getCommentsCode() {
        if (comments.isEmpty()) {
            return "";
        }
        StringBuilder txt = new StringBuilder();
        String ident = PseudoLanguage.ident(this);
        for (String com : comments.split("[\\r\\n]+")) {
            txt.append(ident + COMMENT_STR + com + "\n");
        }
        return txt.substring(0, txt.length() - 1); // remove last \n
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: COMMENTS
    private static String COMMENT_TOKEN = FkeywordToken.get("KEYWORD.comments.key");

    public String getCommentsToken() {
        if (comments.isEmpty()) {
            return "";
        }
        StringBuilder txt = new StringBuilder();
        String ident = PseudoLanguage.ident(this);
        for (String com : comments.split("[\\r\\n]+")) {
            txt.append(ident + COMMENT_TOKEN + " " + com + "\n");
        }
        return txt.substring(0, txt.length() - 1); // remove last \n
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public void setComments(String comments) {
        this.comments = comments.trim();
    }

    public static int getDistanceBetweenShapes() {
        return myFONT.getSize();
    }

    public int distanceBetweenShapes() {
        return getTxtFont().getSize();
    }

    public Point getCenter() {
        int x = getLocation().x + getWidth() / 2;
        int y = getLocation().y + getHeight() / 2;
        return new Point(x, y);
    }

    public Point getCenterRight() {
        int x = getLocation().x + getWidth();
        int y = getLocation().y + getHeight() / 2;
        return new Point(x, y);
    }

    public int getTickness() {
        return border.getThickness();
    }

    public void setColorInExecution(boolean inexec) {
        if (inexec) {
            border.setInExecution();
        } else {
            border.setExecuted();
        }
        repaint();
    }

    public void setNormalColor() {
        border.setNotselected();
        repaint();
    }

    /**
     * to sort arrow with size
     *
     * @param t
     * @return
     */
    @Override
    public int compareTo(Fshape t) {
        return getWidth() * getHeight() - t.getWidth() * t.getHeight();
    }

    public void setEditable(boolean edt) {
        this.isEditable = edt;
    }

//------------------------------------------------------------
    //   temporary variables to process graph
    //------------------------------------------------------------
    public int xLocation, yLocation;
    public double POSITION_LEVEL; // to calculate the position of the shape in the fluxogram
    public int maxY;

    public enum Direction {

        PARENT, RIGHT, NEXT, LEFT
    }; // direction of pointers

    /**
     * return pseudoCode with comments
     *
     * @return
     */
    public String getPseudoCodeWithComments() {
        StringBuilder code = new StringBuilder();
        String comments = getCommentsCode();
        if (!comments.isEmpty()) {
            code.append(comments).append("\n");
        }
        try {
            code.append(getPseudoCode());
        } catch (FlowchartException ex) {
            code.append("ERROR " + getInstruction()).append("\n");
        }
        return code.toString();
    }

    /**
     * return Tokens with comments
     *
     * @return
     */
    public String getPseudoTokensWithComments() {
        StringBuilder code = new StringBuilder();
        String comments = getCommentsToken();
        if (!comments.isEmpty()) {
            code.append(comments).append("\n");
        }
        code.append( getPseudoTokens());
        return code.toString();
    }

    @Override
    public Fshape clone() throws CloneNotSupportedException {
        return (Fshape)super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void clean(){
        this.parent = null;
        this.next = null;
        this.left = null;
        this.right = null;
        this.level = 0;
    }
    
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
