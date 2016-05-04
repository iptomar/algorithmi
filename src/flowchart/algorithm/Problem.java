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
package flowchart.algorithm;

import flowchart.utils.Base64;
import flowchart.utils.UserName;
import i18n.FkeyWord;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ui.FLog;

/**
 * Problem to be solved by system
 *
 * Created on 13/dez/2015, 17:58:43
 *
 * @author zulu - computer
 */
public class Problem implements Serializable {

    public static String PROBLEM_TAG = "PROBLEM";

    public UserName user = null;
    public String problemID = "";
    public String title = "";
    public String description = "";
    public Program solver = null;
    public List<String> input = new ArrayList<>();
    public byte[] image = {};
    public long creationDate = (new Date()).getTime(); // clock date of creation
    public long updtateDate = (new Date()).getTime(); // clock date of update

    public Problem(UserName user) {
        this.user = user;
    }

    public String serialize() {
        //empty problem
        if (description.isEmpty()) {
            return PROBLEM_TAG;
        }

        try {
            return Base64.encodeObject(this, Base64.GZIP);
        } catch (IOException ex) {
            FLog.printLn("User diditalSignature() " + ex.getMessage());
            return "NOT SIGNED";
        }
    }

    public static Problem load(String program) {
        Problem myProblem;
        try {
            myProblem = (Problem) Base64.decodeToObject(program, Base64.GZIP, Problem.class.getClassLoader());
        } catch (Exception ex) {
            myProblem = new Problem(new UserName()); // default user
        }
        return myProblem;

    }

    /**
     * sets the solver to the problem
     *
     * @param prog
     */
    public void setProgramSolver(Program prog) {
        this.solver = prog.getClone();
        this.solver.cleanNonExecutableElements();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static long serialVersionUID = 201512131758L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
