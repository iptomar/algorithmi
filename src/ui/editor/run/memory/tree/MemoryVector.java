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
//::                                                               (c)2015   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package ui.editor.run.memory.tree;

import core.Memory;
import core.data.Fsymbol;
import java.util.Vector;

/**
 * Created on 26/out/2015, 7:41:33
 *
 * @author zulu - computer
 */
public class MemoryVector extends Vector {

    String name;

    public MemoryVector(Memory mem) {
        this.name = mem.getMemoryName();
        for (Fsymbol var : mem.getMem()) {
            add(var);
        }
    }

    public MemoryVector(String name, Vector[] memArray) {
        this.name = name;
        for (Object mem : memArray) {
            add(mem);
        }
    }

    public String toString() {
        return name;
    }

    public boolean sameMemory(Vector v) {
        if (v.size() != size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (!get(i).equals(v.get(i))) {
                return false;
            }
        }
        return true;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510260741L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
