package by.bsuir;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexicalAnalyzer {

    private final Dictionary DICTIONARY = new Dictionary();

    public Map<String, Integer> getLexemCount(List<String> arrayOfLexems) {
        Map<String, Integer> map = new HashMap<>();
        for (String lexem : arrayOfLexems) {
            if (map.containsKey(lexem)) {
                map.put(lexem, map.get(lexem) + 1);
            } else {
                map.put(lexem, 1);
            }
        }
        return map;
    }

    public void lexAlloc(List<String> operands, List<String> operators, List<String> arrOfLexems) {
        for (String lexem : arrOfLexems) {
            if (DICTIONARY.isInPyHelpKeywords(lexem)) {
                continue;
            } else if (DICTIONARY.isInPyKeywordsOperands(lexem)) {
                operands.add(lexem);
            } else if (DICTIONARY.isInPyStatements(lexem) || DICTIONARY.isInPyKeywordsOperators(lexem)) {
                operators.add(lexem);
            } else if (lexem.contains("()")) {
                operators.add(lexem);
            } else if (lexem.contains("[]")) {
                operators.add(lexem);
            } else {
                operands.add(lexem);
            }
        }
    }

    public void lexemsFromLine(String str, List<String> result) {
        int lexemPos = 0;
        int i;
        String lexem;
        while (lexemPos < str.length()) {
            while (str.charAt(lexemPos) == ' ') {
                lexemPos++;
            }

            if (str.charAt(lexemPos) == '#') {
                break;
            }

            i = lexemPos;
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

            if (i == lexemPos) {
                while (DICTIONARY.isInPyStatements(Character.toString(str.charAt(i)))) {
                    i++;
                }
            }

            lexem = str.substring(lexemPos, i);
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
            if (lexemPos < i) {
                lexemPos = i;
            } else {
                lexemPos = i + 1;
            }
        }
    }

}
