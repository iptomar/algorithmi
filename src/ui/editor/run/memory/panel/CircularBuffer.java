//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package ui.editor.run.memory.panel;

import java.util.LinkedList;

/**
 * Circular queue optimized for insertion of elements do not suppot removes
 *
 * Created on 30/abr/2016, 17:11:26
 *
 * @author zulu - computer
 */
public class CircularBuffer<E> {

    private int maxSize;
    private LinkedList<E> myList;

    public CircularBuffer(int size) {
        this.maxSize = size; 
        myList = new LinkedList<>();
    }

    /**
     * add an element if not null
     *
     * @param elem element
     */
    public void add(E elem) {
        myList.remove(elem); // remove if exists
        if (myList.size() >= maxSize) { // remove last
            myList.removeLast();
        }
        myList.addFirst(elem);
    }

    /**
     * verify if the queue is empty
     *
     * @return
     */
    public boolean isEmpty() {
        return myList.isEmpty();
    }

    /**
     * get the element i in the circular way
     *
     * @param i index
     * @return queue[index]
     */
    public E get(int i) {
        //get array[start] if array[i] is null
        return myList.get(i);

    }
    
        /**
     * get the element i in the circular way
     *
     * @param i index
     * @return queue[index]
     */
    public E getLast() {
        //get array[start] if array[i] is null
        return myList.getLast();

    }
 
    /**
     * size of the queue
     *
     * @return
     */
    public int size() {
        return myList.size();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604301711L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
