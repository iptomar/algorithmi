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
package core.data.exception;

import flowchart.utils.Theme;
import ui.flowchart.dialogs.Fdialog;
import i18n.Fi18N;
import java.util.ArrayList;

/**
 *
 * @author zulu
 */
public class FlowchartException extends Exception {

    String instruction;

    String[] params;
    public String key;

    public FlowchartException(Exception e) {
        super(Fi18N.get("RUNTIME.undefinedException"));
        if (e instanceof FlowchartException) {
            FlowchartException fe = (FlowchartException) e;
            this.instruction = fe.instruction;
            this.key = fe.key;
            this.params = fe.params;
        } else {
            this.key = "RUNTIME.undefinedException";
            this.instruction = e.getMessage();

            ArrayList<String> stackTrace = new ArrayList<>();
            StackTraceElement st[] = e.getStackTrace();
            for (StackTraceElement st1 : st) {
                stackTrace.add(st1.getClassName() + " " + st1.getMethodName());
            }
            this.params = stackTrace.toArray(new String[stackTrace.size()]);
        }
    }

    public FlowchartException(String instruction, String key, String[] params) {
        super(Fi18N.get(key));
        this.key = key;
        this.instruction = instruction;
        this.params = params;
    }

    public FlowchartException(String key) {
        this("", key, new String[]{""});
    }

    public FlowchartException(String key, String p1) {
        this("", key, new String[]{p1});
    }

    public FlowchartException(String key, String p1, String p2) {
        this("", key, new String[]{p1, p2});
    }

    public FlowchartException(String key, String p1, String p2, String p3) {
        this("", key, new String[]{p1, p2, p3});
    }

    public String getHtmlMessage() {
        return Theme.exceptionHtml(this);

    }

    public String getTxtMessage() {
        return Theme.replaceParameters(instruction, getKey(), getMessage(), params);
    }

    public void show(String title) {
        Fdialog.compileException(this);
    }

    public String toString() {
        String txt = getMessage();
        for (int i = 0; i < params.length; i++) {
            txt = txt.replace("%" + (i + 1), params[i]);
        }
        return getKey() + "[" + instruction + "]" + txt;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public static void main(String[] args) {
        FlowchartException e = new FlowchartException("OPERATOR.error.numParams", "a", "b", "c");
        e.show("Teste");
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    public String[] getParams() {
        return params;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 1509071119;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
