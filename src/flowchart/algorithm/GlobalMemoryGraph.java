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
package flowchart.algorithm;

import core.Memory;
import i18n.Fi18N;
import i18n.FkeyWord;
import flowchart.arrow.BT.ArrowNextGlobalMemory;
import flowchart.shape.Fshape;
import flowchart.terminator.End;
import flowchart.terminator.BeginGlobalMemory;
import javax.swing.JPanel;

/**
 * Created on 7/set/2015, 12:15:34
 *
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class GlobalMemoryGraph extends AlgorithmGraph {

   

    public GlobalMemoryGraph(JPanel container, Program prog) {
        super(container, prog);
        name = Fi18N.get("MAINGRAPH.globalMemoryName");
    }

    public String getFunctionName() {
        return name;
    }

    @Override
    public void initProgram() {
        clear();
        Fshape begin = new BeginGlobalMemory(this); //begin
        begin.level = 0;
        Fshape end = new End(this); //end
        end.level = 1;
        ArrowNextGlobalMemory arrow = new ArrowNextGlobalMemory(begin, end); // begin -> end
        add(begin);
        add(arrow);
        add(end);

//        try {
//            //-----------------------------------------------------
//            Define v0 = new Define(this);
//            addShape(v0, "real v1[3][3]", "um taraay 3x3", begin.next);
//            //-------------------------------------------------------------
//            Define v1 = new Define(this);
//            addShape(v1, "inteiro v2[10]", "um vetor de 10 elementos", begin.next);
//            //-----------------------------------------------------
//            Define v2 = new Define(this);
//            addShape(v2, "real r = 1.5", "um real", begin.next);
//            //-----------------------------------------------------
//            Define v3 = new Define(this);
//            addShape(v3, "Inteiro i = 10", "um inteiro", begin.next);
//            //-----------------------------------------------------
//            Define v4 = new Define(this);
//            addShape(v4, "Texto txt = \"um gato\"", "um texto", begin.next);
//            //-----------------------------------------------------
//            Define v5 = new Define(this);
//            addShape(v5, "Caracter ch = 'x'", "um caracter", begin.next);
//            //-----------------------------------------------------
//            Define v6 = new Define(this);
//            addShape(v6, "Caracter ch6 = 'x'", "um caracter", begin.next);
//            //-----------------------------------------------------
//            Define v7 = new Define(this);
//            addShape(v7, "Caracter ch7 = 'x'", "um caracter", begin.next);

//        } catch (FlowchartException ex) {
//            Logger.getLogger(GlobalMemoryGraph.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
   

    public Memory getGlobalMemory() {
        return super.getMemory(getEnd());
    }

}
