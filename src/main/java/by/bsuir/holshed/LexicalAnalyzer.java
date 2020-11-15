package by.bsuir.holshed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexicalAnalyzer {

    private final Dictionary DICTIONARY = new Dictionary();

    public Map<String, Integer> getLexemeCount(List<String> arrayOfLexemes) {
        Map<String, Integer> map = new HashMap<>();
        for (String lexeme : arrayOfLexemes) {
            if (map.containsKey(lexeme)) {
                map.put(lexeme, map.get(lexeme) + 1);
            } else {
                map.put(lexeme, 1);
            }
        }
        return map;
    }

    public void lexAlloc(List<String> operands, List<String> operators, List<String> arrOfLexemes) {
        for (String lexeme : arrOfLexemes) {
            if (!DICTIONARY.isInPyHelpKeywords(lexeme)) {
                if (DICTIONARY.isInPyKeywordsOperands(lexeme)) {
                    operands.add(lexeme);
                } else if (DICTIONARY.isInPyStatements(lexeme) || DICTIONARY.isInPyKeywordsOperators(lexeme)) {
                    operators.add(lexeme);
                } else if (lexeme.contains("()")) {
                    operators.add(lexeme);
                } else if (lexeme.contains("[]")) {
                    operators.add(lexeme);
                } else {
                    operands.add(lexeme);
                }
            }
        }
    }

    public void lexemesFromLine(String str, List<String> result) {
        int lexemePos = 0;
        int i;
        String lexem;
        while (lexemePos < str.length()) {
            while (str.charAt(lexemePos) == ' ') {
                lexemePos++;
            }

            if (str.charAt(lexemePos) == '#') {
                break;
            }

            i = lexemePos;
            while (DICTIONARY.isInPyIdentifier(Character.toString(str.charAt(i)))) {
                i++;
            }

            if (str.matches("[1-9?]]") && str.charAt(i) == '.') {
                i++;
                while (DICTIONARY.isInPyIdentifier(Character.toString(str.charAt(i)))) {
                    i++;
                }
            }

            if (str.charAt(i) == '"' || str.charAt(i) == '\'') {
                i++;
                while (!((str.charAt(i) == '"') || (str.charAt(i) == '\''))) {
                    i++;
                }
                i++;
            }

            if (i == lexemePos) {
                while (DICTIONARY.isInPyStatements(Character.toString(str.charAt(i)))) {
                    i++;
                }
            }

            lexem = str.substring(lexemePos, i);
            if (str.charAt(i) == '(' && !DICTIONARY.isInPyStatements(lexem)) {
                lexem += "()";
                i++;
            }
            if (str.charAt(i) == '[') {
                lexem += "[]";
                i++;
            }
            if (str.charAt(i) == ')') {
                i++;
            }
            if (!lexem.equals("")) {
                result.add(lexem);
            }
            if (lexemePos < i) {
                lexemePos = i;
            } else {
                lexemePos = i + 1;
            }
        }
    }

}
