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
package core.data.complexData;

import core.Memory;
import core.data.Finteger;
import core.data.Fsymbol;
import static core.data.Fvoid.TYPE_TOKEN;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import core.parser.Mark;
import core.parser.tokenizer.BreakMarks;
import flowchart.algorithm.Program;
import flowchart.utils.ExpressionUtils;
import flowchart.utils.IteratorArrayIndex;
import ui.FLog;
import i18n.Fi18N;
import i18n.FkeywordToken;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10/set/2015, 10:42:23
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class Farray extends FcomplexSymbol {

    List<Fsymbol> elements; // elements
    List<Expression> indexExpression; // index expressions
    Memory memory; // to evaliate index expressions

    /**
     * private constructor to build subArrays
     *
     * @param name
     * @throws FlowchartException
     */
    private Farray(String name) throws FlowchartException {
        super(name, new ArrayList<Fsymbol>());
        elements = (List<Fsymbol>) value; // get elements
    }

    /**
     * Define one array with full definition inteiro x[20][50]
     *
     * @param fullDefinition ful definition of the array with indexes
     * @param mem memory to evaluate indexes
     * @throws FlowchartException
     */
    public Farray(String fullDefinition, Memory mem, Program prog) throws FlowchartException {
        super(new ArrayList<Fsymbol>());
        fullDefinition = fullDefinition.trim();
        //verify parentesis
        BreakMarks.checkParentesis(fullDefinition);
        elements = (List<Fsymbol>) value; // get elements
        indexExpression = new ArrayList<>(); // create indexes
        this.memory = mem;
        //get type of elements
        String def = fullDefinition;
        //invalid definition
        if (def.indexOf(" ") < 0) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException("DEFINE.invalidDefininition", fullDefinition);
        }
        String type = def.substring(0, def.indexOf(" ")); // get Type
        def = def.substring(def.indexOf(" ") + 1); // cut Type 
        //invalid definition
        if (def.indexOf(Mark.SQUARE_OPEN) < 0) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException("DEFINE.invalidDefininition", fullDefinition);
        }

        String name = def.substring(0, def.indexOf(Mark.SQUARE_OPEN)).trim(); // get name
        def = def.substring(def.indexOf(Mark.SQUARE_OPEN)); // get indexes
        //create type of data
        Fsymbol typeData = Fsymbol.create(type, name);
        //add the type element to the begining of the list
        // contain the seed of other elements
        elements.add(typeData);
        //create expressions of indexes
        IteratorArrayIndex it = new IteratorArrayIndex(def);
        while (it.hasNext()) {
            Expression ex = new Expression(it.next().trim(), mem, prog);
            indexExpression.add(ex);
        }
        if (indexExpression.isEmpty()) {
            throw new FlowchartException("TYPE.array.emptyDimension", fullDefinition);
        }
        //parse name
        Fsymbol memVar = mem.getByName(name);
        if (memVar != null) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException(
                    fullDefinition,
                    "DEFINE.duplicatedName",
                    new String[]{
                        memVar.getName(),
                        memVar.getInstruction()
                    }
            );
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        }
        setName(name);
    }

    /**
     * change the expression of the indexes
     *
     * @param def
     * @param mem
     * @throws FlowchartException
     */
    public void setTextIndexes(String def, Memory mem, Program prog) throws FlowchartException {
        List<Expression> newIndexes = new ArrayList<>();
        //create expressions of indexes
        IteratorArrayIndex it = new IteratorArrayIndex(def);
        while (it.hasNext()) {
            Expression ex = new Expression(it.next(), mem, prog);
            newIndexes.add(ex);
        }
        if (newIndexes.size() != indexExpression.size()) {
            throw new FlowchartException(
                    "EXECUTE.array.invalidNumberOfIndexes",
                    getFullName(),
                    indexExpression.size() + "",
                    newIndexes.size() + ""
            );
        }
        indexExpression = newIndexes;
    }

    public void parseArray(Memory mem) throws FlowchartException {
        for (Expression ex : indexExpression) {
            Fsymbol rets = ex.getReturnType(mem);
            if (!(rets instanceof Finteger)) {//-----------------------------index not integer  
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                throw new FlowchartException(
                        "DEFINE.indexNotInteger",
                        ex.getIdented()
                );
            }
            if (rets.isConstant() && ((Finteger) rets).getIntValue() < 1) {// index invalid
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //::::::::::::::::::: FLOWCHART ERROR :::::::::::::::::::::::::
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                throw new FlowchartException(
                        "DEFINE.invalidIndexValue",
                        ex.getReturnType().getTextValue(),
                        ex.getIdented()
                );
            }
        }
    }

    /**
     * gets the dimensions of the arrays Evaliated by the current memory
     *
     * @return
     */
    public List<Integer> getDimensions() {
        List<Integer> list = new ArrayList<>();
        for (Expression ex : indexExpression) {
            list.add(((Finteger) ex.getReturnType()).getIntValue());
        }
        return list;
    }

    /**
     * gets the elements
     *
     * @return
     */
    public List<Fsymbol> getElements() {
        return elements;
    }

    /**
     * return the number of dimensions
     *
     * @return
     */
    public int getNumberOfIndexes() {
        return indexExpression.size();
    }

    /**
     * return the indexes expression rounded by square brackets
     *
     * @return
     */
    @Override
    public String getFullName() {
        return getName() + getIndexesDefinition();
    }
    /**
     * return the indexes expression rounded by square brackets
     *
     * @return
     */
    public String getFullNameToken() {
         StringBuilder txt = new StringBuilder(getName());
        for (Expression ex : indexExpression) {
            txt.append(" " +Mark.SQUARE_OPEN_TOKEN +" ");
            txt.append( FkeywordToken.translateTokensToWords( ex.getIdented() ) + " ");
            txt.append(Mark.SQUARE_CLOSE_TOKEN);
        }
        return txt.toString();
    }

    /**
     * return the indexes expression rounded by square brackets
     *
     * @return
     */
    public String getIndexesDefinition() {
        StringBuilder txt = new StringBuilder();
        for (Expression ex : indexExpression) {
            txt.append(Mark.SQUARE_OPEN + ex.getIdented() + Mark.SQUARE_CLOSE);
        }
        return txt.toString();
    }

    public List<Expression> getIndexes() {
        return indexExpression;
    }

    /**
     * sets the index expressions
     *
     * @param indexExpression new expressions to indexes
     */
    public void setIndexExpressions(List<Expression> indexExpression) {
        this.indexExpression = indexExpression;
    }

    /**
     * @return the full Textual definition
     */
    @Override
    public String getInstruction() {
        StringBuilder txt = new StringBuilder(getTypeName() + " " + getName());
        txt.append(getIndexesDefinition());
        return txt.toString();
    }

    /**
     * replace index expressions to values create elements
     *
     * @param mem memory to evaluate indexes
     * @throws FlowchartException
     */
    public void createArrayElements(Memory mem, Program prog) throws FlowchartException {
        this.memory = mem;
        List<Fsymbol> dims = new ArrayList<>();
        for (Expression ex : indexExpression) {
            dims.add((ex.evaluate(memory)));
        }
        //modify index expressions
        indexExpression.clear();
        for (Fsymbol dim : dims) {
            indexExpression.add(new Expression(dim.getTextValue(), mem, prog));
        }

        //create elements  
        //initialy position 0 of elements contains the template
        Fsymbol template = elements.get(0);
        template.setValue(template.getDefaultValue()); // set values by default
        elements.clear();;
        createElements(getName(), template, dims);
    }

    private void createElements(String name, Fsymbol template, List<Fsymbol> list) {
        //gets and remove the first index
        int dim = ((Finteger) list.remove(0)).getIntValue();

        //not the last dimension
        if (!list.isEmpty()) {
            for (int i = 0; i < dim; i++) {
                createElements(name + Mark.SQUARE_OPEN + i + Mark.SQUARE_CLOSE, template, new ArrayList<>(list));
            }
        } else {
            for (int i = 0; i < dim; i++) {
                Fsymbol elem = (Fsymbol) template.clone();
                elem.setRawName(name + Mark.SQUARE_OPEN + i + Mark.SQUARE_CLOSE);
                elements.add(elem);
            }
        }

    }

    @Override
    public String getFullInfo() {
        StringBuilder txt = new StringBuilder();
        txt.append(Fi18N.get("DEFINE.type.title") + " :" + getTypeName());
        txt.append("\n" + Fi18N.get("DEFINE.name.title") + " :" + getName());
        txt.append("\n" + Fi18N.get("DEFINE.level.title") + " :" + getLevel());
        txt.append("\n" + Fi18N.get("DEFINE.value.title") + "\n");
        //get textual representation of values
        __indexText = 0;
        txt.append(getTextElements(new ArrayList<Expression>(indexExpression)));

        if (!comments.isEmpty()) {
            txt.append("\n" + Fi18N.get("DEFINE.comments.title") + " :\n" + getComments());
        }
        return txt.toString();
    }

    private int __indexText;

    private String getTextElements(List<Expression> list) {
        //gets and remove the first index        
        int dim = ((Finteger) list.remove(0).getReturnType()).getIntValue();
        StringBuilder txt = new StringBuilder();
        //not the last dimension
        if (!list.isEmpty()) {
            for (int i = 0; i < dim; i++) {
                txt.append(getTextElements(new ArrayList<>(list)));
            }
        } else {
            for (int i = 0; i < dim; i++) {
                txt.append(elements.get(__indexText++).getTextValue() + "  ");
            }
            txt.append("\n");
        }
        return txt.toString();
    }

    public int getDimension(int i) {
        return ((Finteger) indexExpression.get(i).getReturnType()).getIntValue();
    }

    public Finteger getDimensionSymbol(int i) {
        return ((Finteger) indexExpression.get(i).getReturnType());
    }

    public Fsymbol getTemplateElement() {
        return elements.get(0);
    }
    
    public String getIndexDimensions(){
        StringBuilder txtDimensions = new StringBuilder();
        for (int i = 0; i < indexExpression.size() ; i++) {
            txtDimensions.append(Mark.SQUARE_OPEN+" " + getDimension(i) + " " + Mark.SQUARE_CLOSE);
        }
        return txtDimensions.toString();
    }

    public Fsymbol getElement(Memory mem) throws FlowchartException {
        return getElement(indexExpression, mem);
    }

    public Fsymbol getElement(List<Expression> indexes, Memory mem) throws FlowchartException {
        
        StringBuilder txtIndexes = new StringBuilder(); // to produce the exception
        
        
        if (elements.size() == 1) { // array not initialized - programming time
            return elements.get(0);
        }
        //evaluate indexes and calculate position
        int pos = 0;
        Finteger value;
        //calculate intermediate positions
        for (int i = 0; i < indexes.size() - 1; i++) {
            value = (Finteger) indexes.get(i).evaluate(mem);
            pos += getDimension(i) * value.getIntValue();
            txtIndexes.append(Mark.SQUARE_OPEN+" " + value + " " + Mark.SQUARE_CLOSE);
        }
        //add alldimension
        value = (Finteger) indexes.get(indexes.size() - 1).evaluate(mem);
        txtIndexes.append(Mark.SQUARE_OPEN+" " + value.getIntValue() + " " + Mark.SQUARE_CLOSE);
        pos += value.getIntValue();
        //Value not defined
        if (pos >= elements.size()) {
            throw new FlowchartException("EXECUTE.array.indexInvalid",
                   txtIndexes.toString(),
                    getName(),
                    getIndexDimensions());
        }
        return elements.get(pos);
    }

    public void setElementValue(String newValue, List<Expression> indexes, Memory mem) throws FlowchartException {
        setElementValue(Fsymbol.createByValue(newValue), indexes, mem);
    }

    /**
     * set the value of the array position
     *
     * @param newValue new value
     * @param indexes index expression
     * @param mem memory to done calculus
     * @throws FlowchartException
     */
    public void setElementValue(Fsymbol newValue, List<Expression> indexes, Memory mem) throws FlowchartException {
         StringBuilder txtIndexes = new StringBuilder(); // to produce the exception
        //evaluate indexes and calculate position
        int pos = 0;
        Finteger value;
        //calculate intermediate positions
        for (int i = 0; i < indexes.size() - 1; i++) {
            value = (Finteger) indexes.get(i).evaluate(mem);
            pos += getDimension(i) * value.getIntValue();
            txtIndexes.append(Mark.SQUARE_OPEN+" " + value + " " + Mark.SQUARE_CLOSE);
        }
        //add alldimension
        value = (Finteger) indexes.get(indexes.size() - 1).evaluate(mem);
        txtIndexes.append(Mark.SQUARE_OPEN+" " + value.getIntValue() + " " + Mark.SQUARE_CLOSE);
        pos += value.getIntValue();
        //Value not defined
        if (pos >= elements.size()) {
            throw new FlowchartException("EXECUTE.array.indexInvalid",
                   txtIndexes.toString(),
                    getName(),
                    getIndexDimensions());
        }
//        System.out.println(elements.get(pos).getInstruction() + "SET " + pos + " = " + newValue.getValue());
        elements.get(pos).setValue(newValue);
//        System.out.println(" done " + elements.get(pos).getInstruction() );                        
    }

    @Override
    public String getTypeName() {
        return elements.get(0).getTypeName();
    }

    @Override
    public Object toValue(Object value) throws FlowchartException {
        if (value instanceof List) {
            return value;
        }
        // :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        throw new FlowchartException("TYPE.array.arrayExpected");
    }

    @Override
    public String getDefaultValue() {
        return elements.get(0).getDefaultValue();
    }

    @Override
    public boolean acceptValue(Object value) {
        return elements.get(0).acceptValue(value);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::: methods to GUI :::::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public int getlastDimension() {
        return ((Finteger) indexExpression.get(indexExpression.size() - 1).getReturnType()).getIntValue();
    }

    /**
     * returns the number of elements that are contained in the dimension i
     *
     * @param dimensionNumber array [][i][][][]
     * @return elements in array [][i][][][]
     */
    private int getDimensionSize(int dimensionNumber) {
        /**
         * multiply de dimensions greather than dimensinNumber
         */
        List<Integer> dims = getDimensions();
        int dim = 1;
        for (int j = dims.size() - 1; j > dimensionNumber; j--) {
            dim *= dims.get(j);
        }
        return dim;
    }

    /**
     * gets the reference of the elements in array
     *
     * @param index list of indexes
     * @return List of referenced elements
     */
    public Farray getsubArray(List<Integer> index) throws FlowchartException {
        Farray subArray = new Farray(this.getName());
        subArray.memory = memory;
        subArray.indexExpression = new ArrayList<>();

        int startIndex = 0;
        List<Integer> dims = getDimensions();
        int numElements = 1;
        for (int i = index.size(); i < dims.size(); i++) {
            numElements *= dims.get(i);
        }
        for (int i = 0; i < index.size(); i++) {
            startIndex += index.get(i) * getDimensionSize(i);
        }
        //updatet indexes of sub array
        for (int i = index.size(); i < indexExpression.size(); i++) {
            subArray.indexExpression.add(indexExpression.get(i));
        }
        for (int i = 0; i < numElements; i++) {
            subArray.elements.add(elements.get(startIndex + i));
        }

        subArray.setRawName(getIndexedName(index));
        return subArray;
    }

    private String getIndexedName(List<Integer> index) {
        StringBuilder txt = new StringBuilder(getName());
        for (Integer i : index) {
            txt.append("" + Mark.SQUARE_OPEN + i + Mark.SQUARE_CLOSE);
        }
        return txt.toString();
    }

    @Override
    public Object clone() {
        try { // call clone in Object.
            Farray clone = (Farray) super.clone();
            clone.elements = new ArrayList<>();
            for (Fsymbol element : elements) {
                clone.elements.add(element);
            }
            clone.indexExpression = new ArrayList<>();
            for (Expression expr : indexExpression) {
                clone.indexExpression.add(expr);
            }
            return clone;
        } catch (Exception e) {
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            FLog.printLn(Fsymbol.class.getName() + " Clone ERROR");
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: LOG
            return null;
        }
    }
    
     @Override
    public String getTypeToken() {
        return elements.get(0).getTypeToken();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
