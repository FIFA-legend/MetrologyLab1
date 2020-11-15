package by.bsuir.holshed;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Container {

    private List<String> lexemes;

    private Map<String, Integer> operands;

    private Map<String, Integer> operators;

    public Container() {
        lexemes = new LinkedList<>();
    }

    public List<String> getLexemes() {
        return lexemes;
    }

    public void setLexemes(List<String> lexemes) {
        this.lexemes = lexemes;
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

}
