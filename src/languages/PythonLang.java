/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package languages;

import core.CoreToken;
import core.data.Finteger;
import core.data.Flogic;
import core.data.Freal;
import core.data.Fsymbol;
import core.data.Ftext;
import core.data.complexData.Farray;
import core.data.exception.FlowchartException;
import core.parser.Expression;
import flowchart.decide.Do_Connector;
import flowchart.decide.Do_While;
import flowchart.decide.IfElse.IfThenElse;
import flowchart.decide.While_Do;
import flowchart.decide.forNext.For_Next;
import flowchart.define.Define;
import flowchart.execute.Execute;
import flowchart.function.Function;
import flowchart.function.FunctionParameter;
import flowchart.read.Read;
import flowchart.returnFunc.Return;
import flowchart.shape.Fshape;
import flowchart.write.Write;
import i18n.FkeyWord;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vanheelsing
 */
public class PythonLang extends AbstractLang {

    public PythonLang() {
        super.IDENTATATION_SPACE=1;
        super.DEFAULT_IDENTATATION=-1;
    }
    
    static HashMap<String, String> lang = new HashMap<String, String>();

    static {
        lang.put(FkeyWord.get("TYPE.integer"), "");
        lang.put(FkeyWord.get("TYPE.real"), "");
        lang.put(FkeyWord.get("TYPE.string"), "");
        lang.put(FkeyWord.get("TYPE.boolean"), "");

        lang.put(FkeyWord.get("CONSTANT.true"), "true");
        lang.put(FkeyWord.get("CONSTANT.false"), "");
        lang.put(FkeyWord.get("CONSTANT.PI.name"), "Math.PI");
        lang.put(FkeyWord.get("CONSTANT.E"), "2.71828");

        lang.put(FkeyWord.get("SEPARATOR.comma"), ",");
        lang.put(FkeyWord.get("BRACKET.round.open"), "(");
        lang.put(FkeyWord.get("BRACKET.round.close"), ")");
        lang.put(FkeyWord.get("BRACKET.square.open"), "[");
        lang.put(FkeyWord.get("BRACKET.square.close"), "]");
        lang.put(FkeyWord.get("BRACKET.brace.open"), "{");
        lang.put(FkeyWord.get("BRACKET.brace.close"), "}");

        lang.put(FkeyWord.get("OPERATOR.set"), "=");

        lang.put(FkeyWord.get("OPERATOR.negative"), "-");

        lang.put(FkeyWord.get("OPERATOR.sum"), "+");
        lang.put(FkeyWord.get("OPERATOR.sub"), "-");
        lang.put(FkeyWord.get("OPERATOR.mult"), "*");
        lang.put(FkeyWord.get("OPERATOR.div"), "/");
        lang.put(FkeyWord.get("OPERATOR.mod"), "%");
        lang.put(FkeyWord.get("OPERATOR.power"), "Math.pow");

        lang.put(FkeyWord.get("OPERATOR.equal"), "==");
        lang.put(FkeyWord.get("OPERATOR.different"), "!=");
        lang.put(FkeyWord.get("OPERATOR.greater"), ">");
        lang.put(FkeyWord.get("OPERATOR.greaterOrEqual"), ">=");
        lang.put(FkeyWord.get("OPERATOR.less"), "<");
        lang.put(FkeyWord.get("OPERATOR.lessOrEqual"), ">=");

        lang.put(FkeyWord.get("OPERATOR.not"), "!");
        lang.put(FkeyWord.get("OPERATOR.and"), "&&");
        lang.put(FkeyWord.get("OPERATOR.or"), "||");

        lang.put(FkeyWord.get("FUNCTION.sin"), "Math.sin");
        lang.put(FkeyWord.get("FUNCTION.sqrt"), "Math.sqrt");
        lang.put(FkeyWord.get("FUNCTION.abs"), "Math.abs");
        lang.put(FkeyWord.get("FUNCTION.random"), "Math.random");
        lang.put(FkeyWord.get("FUNCTION.pow"), "Math.pow");
    }

    @Override
    public String getStartOfProgram(Fshape shape) {
        String className = shape.algorithm.myProgram.getFileName();
        return "";      // inicio da classe    
    }

    @Override
    public String getEndOfProgram(Fshape shape) {
        return ""; // fechar a class
    }

    @Override
    public String getBeginProgram(Fshape shape) {
        return "";
    }

    @Override
    public String getEnd(Fshape shape) {
        return "";
    }

    @Override
    public String getEndProgram(Fshape shape) {
        return "";
    }

    private String getType(Fsymbol symbol){
        String code="";
        if(symbol instanceof Freal){
            code += "long";
        } else if (symbol instanceof Finteger) {
            code += "int";
        } else if (symbol instanceof Ftext) {
            code += "String";
        } else if (symbol instanceof Flogic) {
            code += "boolean";
        }
        return code;
    }
    
    @Override
    public String getDefine(Fshape shape) {
        Define def = (Define) shape;
        String code = "";
        boolean isArray=false;
        if (def.varSymbol instanceof Farray){
            isArray=true;
        }
        code += def.varSymbol.getName() + " = ";
        if(!isArray){
            code += AbstractLang.lang.getExpression(def.getVarExpression());
        }
        else{
            code+="[]";
        }
        return code;
    }

    @Override
    public String getExecute(Fshape shape) {
        Execute ex = (Execute) shape;
        String code = "";
        code += ex.var.getFullName() + " = " + AbstractLang.lang.getExpression(ex.expressionToCalculate);
        return code;
    }

    @Override
    public String getWrite(Fshape shape) {
        Write w = (Write) shape;
        String code = "";
        code += "print (" + w.getInstruction()+")";
        return code;
    }

    @Override
    public String getRead(Fshape shape) {
        Read r = (Read) shape;
        Fsymbol memoryVar = shape.algorithm.getMemory(shape.parent).getByName(r.var.getName());
        boolean isDefined = memoryVar == null;
        String code = "";
        code += r.var.getName() + " = input()";
        return code;
    }

    @Override
    public String getIf(Fshape shape) {
        IfThenElse ifElse = (IfThenElse) shape;
        String code = "";
        code += "if " + AbstractLang.lang.getExpression(ifElse.logicExpression) + ":";
        return code;
    }

    @Override
    public String getElse(Fshape shape) {
        String code = "else:";
        return code;
    }

    @Override
    public String getWhile(Fshape shape) {
        While_Do whileDo = (While_Do) shape;
        String code = "";
        code += "while " + AbstractLang.lang.getExpression(whileDo.logicExpression) + ":";
        return code;
    }

    @Override
    public String getDo(Fshape shape) {
        Do_Connector doWhile = (Do_Connector) shape;
        String code = "While True:";
        return code;
    }
    
    @Override
    public String getDoWhile(Fshape shape) {
        Do_While doWhile = (Do_While) shape;
        String code = "";
        code += "   if not " + AbstractLang.lang.getExpression(doWhile.logicExpression) + ":\n"+AbstractLang.ident(doWhile)+"       break";
        return code;
    }

    @Override
    public String getFor(Fshape shape) {
        For_Next forNext = (For_Next) shape;
        String code = "";
        code += "for ";
        Fsymbol memoryVar = shape.algorithm.getMemory(shape.parent).getByName(forNext.var.getName());
        code += " in range("
                +AbstractLang.lang.getExpression(forNext.start).trim()+","
                +AbstractLang.lang.getExpression(forNext.stop).trim()+","
                +AbstractLang.lang.getExpression(forNext.step).trim()+")";
        return code;
    }

    @Override
    public String getCommentedString(String txt, Fshape shape) {
        if (txt.length() > 0) {
            return AbstractLang.ident(shape) + "#" + txt + "\n";
        } else {
            return "";
        }
    }

    @Override
    public String getExpression(Expression ex) {
        StringBuffer code = new StringBuffer();
        List<Object> tokens = ex.getTokens();
        for (int i = 0; i < tokens.size(); i++) {
            CoreToken tok = (CoreToken) tokens.get(i);

            if (lang.get(tok.getDescriptor()) != null) {
                code.append(lang.get(tok.getDescriptor()));
            } else {
                code.append(tok.getDescriptor());
            }
            code.append(" ");

        }

        return code.toString();
    }

    @Override
    public String getFunction(Fshape shape) {
        Function func = (Function) shape;
        String code = "";
        code += "def ";
        code += func.getFunctionName() + "(";
        for (FunctionParameter current : func.parameters) {
            try {
                code += current.getLanguage().substring(0, current.getLanguage().length() - 5).trim() + ", ";
            } catch (FlowchartException ex) {
                Logger.getLogger(JavaLang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (code.endsWith(", ")) {
            code = code.substring(0, code.length() - 2);
        } else {
            code = code.substring(0, code.length());
        }
        code += ":";
        return code;
    }

    @Override
    public String getReturn(Fshape shape) {
        Return rturn = (Return) shape;
        return "return " + getExpression(rturn.returnExpression) + "";
    }

  

}
