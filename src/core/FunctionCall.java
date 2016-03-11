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
package core;

import core.data.Fsymbol;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import core.parser.Mark;
import flowchart.algorithm.FunctionGraph;
import flowchart.function.Function;
import flowchart.function.FunctionParameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 22/set/2015, 15:28:12
 *
 * @author zulu - computer
 */
public class FunctionCall extends CoreToken {

    public transient FunctionGraph myAlgorithm;
    // private transient Function function;
    public String myFunctionName;
    public List<FunctionParameter> myParameters = new ArrayList<>(); //list of parameters to call 
    public Fsymbol myReturn; //myReturn

    public boolean inExecution = false; // flag to runtime

    public FunctionCall(FunctionGraph algorithm) {
        //this.myAlgorithm = (FunctionGraph) algorithm.getClone();
        this.myAlgorithm = (FunctionGraph) algorithm;
        Function function = (Function) myAlgorithm.getBegin();
        for (FunctionParameter p : function.parameters) {
            myParameters.add(p.getClone());
        }
        myFunctionName = function.getFunctionName();
        myReturn = (Fsymbol) function.getReturnSymbol().clone();
        myReturn.setRawName("return" + myFunctionName);

    }

    public void setParameters(List<Expression> funcParams) throws FlowchartException {
        if (funcParams.size() != myParameters.size()) {

            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            throw new FlowchartException("FUNCTIONCALL.exception.invalidNumberOfParameters",
                    myFunctionName,
                    myParameters.size() + "",
                    funcParams.size() + ""
            );
            //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::                        
        }
        for (int i = 0; i < funcParams.size(); i++) {
            Fsymbol p = myParameters.get(i).getVarSymbol();
            if (!p.acceptValue(funcParams.get(i).getReturnType().getDefinitionValue())) {
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                //::::::::::::::::::::: FLOWCHART EXCEPTION ERROR :::::::::::::::::
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                throw new FlowchartException("FUNCTIONCALL.invalidTypeOfParameter",
                        myFunctionName,
                        myParameters.get(i).getVarSymbol().getTypeName() + " "
                        + myParameters.get(i).getVarSymbol().getName() + "",
                        funcParams.get(i).getIdented() + ""
                );
                //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            }
            myParameters.get(i).setVarExpression(funcParams.get(i));
        }
    }

    public void parse(Memory mem) throws FlowchartException {
        try {
            // 1 - the function is defined
            FunctionGraph template = myAlgorithm.myProgram.getFunctionByName(myFunctionName);
            if (template == null) {
                throw new FlowchartException("FUNCTION.invalidName", myFunctionName);
            }
            // 2 - the number of parameters is ok
            Function f = (Function)template.getBegin();
            if( f.parameters.size() != myParameters.size()){
               throw new FlowchartException("FUNCTIONCALL.invalidNumberOfParameters", 
                       f.getFunctionName(),
                       f.parameters.size()+"",
                       myParameters.size()+"");
            }
            // 3 - the type of parameters is compatible
            for (int i = 0; i < f.parameters.size(); i++) {
               FunctionParameter def = f.parameters.get(i);
               FunctionParameter call = myParameters.get(i);
               if( ! def.isCompatible(call)){ // values not compatible
                   throw new FlowchartException("FUNCTIONCALL.invalidTypeOfParameter", 
                           def.getVarSymbol().getInstruction(),
                           f.getFunctionName(),
                           call.getInstruction());
               }                
            }            

        } catch (Exception e) {
            //something wrong happens
            throw new FlowchartException(e);
        }

    }

    /**
     * @return the myAlgorithm
     */
    public FunctionGraph getMyAlgorithm() {
        return myAlgorithm;
    }

    /**
     * @param myAlgorithm the myAlgorithm to set
     */
    public void setMyAlgorithm(FunctionGraph myAlgorithm) {
        this.myAlgorithm = myAlgorithm;
    }

    public Fsymbol getResult() {
        return myReturn;
    }

    public String getName() {
        return myFunctionName;
    }

    public List<FunctionParameter> getParameters() {
        return myParameters;
    }

    public String getFullName() {
        StringBuilder txt = new StringBuilder();
        txt.append(myFunctionName + Mark.ROUND_OPEN);
        for (int i = 0; i < myParameters.size(); i++) {
            txt.append(myParameters.get(i).getVarExpression() + " ");
            if (i < myParameters.size() - 1) {
                txt.append(Mark.COMMA_CHAR + " ");
            }
        }
        return txt.toString().trim() + Mark.ROUND_CLOSE;

    }

    public String getDescriptor() {
        return getFullName();
    }

    public String toString() {
        return getFullName();
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201509071215L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
