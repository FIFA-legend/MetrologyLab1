package by.bsuir;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Container {

    private List<String> lexems;

    private Map<String, Integer> operands;

    private Map<String, Integer> operators;

    public Container() {
        lexems = new LinkedList<>();
    }

    public List<String> getLexems() {
        return lexems;
    }

    public void setLexems(List<String> lexems) {
        this.lexems = lexems;
    }

    public Map<String, Integer> getOperands() {
        return operands;
    }

    public void setOperands(Map<String, Integer> operands) {
        this.operands = operands;
    }

    public Map<String, Integer> getOperators() {
        return operators;
    }

    public void setOperators(Map<String, Integer> operators) {
        this.operators = operators;
    }

    public int getMaxSize (){
        return Math.max(operands.size(), operators.size());
    }

}
