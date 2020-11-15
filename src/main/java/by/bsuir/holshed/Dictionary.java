package by.bsuir.holshed;

public class Dictionary {

    public final String[] PY_IDENTIFIER;

    public final String[] PY_KEYWORDS_OPERATORS;

    public final String[] PY_KEYWORDS_OPERANDS;

    public final String[] PY_HELP_KEYWORDS;

    private final String[] PY_STATEMENTS;

    {
        PY_IDENTIFIER = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "_", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    }

    {
        PY_KEYWORDS_OPERATORS = new String[] { "and", "assert", "break", "continue",
            "del", "for", "if", "import", "in", "is", "not", "except", "finally",
            "or", "pass", "raise", "return", "try", "while", "yield", "elif" };
    }

    {
        PY_KEYWORDS_OPERANDS = new String[] { "False", "True", "None" };
    }

    {
        PY_HELP_KEYWORDS = new String[] { "as", "else", "class", "def", "from", "global", "nonlocal", "lambda", "with" };
    }

    {
        PY_STATEMENTS = new String[] { "+", "-", "*", "**", "/", "//", "%", "@", "<<", ">>", "&", "|",
                "^", "~", "<", ">", "<=", ">=", "==", "!=", "+=", "-=", "=",
                "*=", "/=", "%=", "**=", "//=", "." };
    }

    public boolean isInPyStatements(String lexeme) {
        if (!lexeme.equals(" ")) {
            for (String pyStatement : this.PY_STATEMENTS) {
                if (lexeme.equals(pyStatement)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInPyKeywordsOperators(String lexeme) {
        if (!lexeme.equals(" ")) {
            for (String pyKeyword : this.PY_KEYWORDS_OPERATORS) {
                if (lexeme.equals(pyKeyword)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInPyKeywordsOperands(String lexeme) {
        if (!lexeme.equals(" ")) {
            for (String pyKeyword : this.PY_KEYWORDS_OPERANDS) {
                if (lexeme.equals(pyKeyword)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInPyHelpKeywords(String lexeme) {
        if (!lexeme.equals(" ")) {
            for (String pyKeyword : this.PY_HELP_KEYWORDS) {
                if (lexeme.equals(pyKeyword)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInPyIdentifier(String str) {
        if (!str.equals(" ")) {
            for (String s : this.PY_IDENTIFIER) {
                if (str.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }
}

