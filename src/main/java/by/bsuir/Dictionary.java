package by.bsuir;

public class Dictionary {

    public final String[] PY_IDENTIFIER;

    public final String[] PY_KEYWORDS;

    private final String[] PY_STATEMENTS;

    {
        PY_IDENTIFIER = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "_", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    }

    {
        PY_KEYWORDS = new String[] { "False", "None", "True", "and", "as", "assert", "break", "class",
            "continue", "def", "del", "except", "finally", "for", "from",
            "global", "if", "import", "in", "is", "lambda", "nonlocal", "not",
            "or", "pass", "raise", "return", "try", "while", "with", "yield",
            "else", "elif" };
    }

    {
        PY_STATEMENTS = new String[] { "+", "-", "*", "**", "/", "//", "%", "@", "<<", ">>", "&", "|",
                "^", "~", "<", ">", "<=", ">=", "==", "!=", "+=", "-=", "=",
                "*=", "/=", "%=", "**=", "//=", "." };
    }

    public boolean isInPyStatements(String lexem) {
        if (!lexem.equals(" ")) {
            for (String pyStatement : this.PY_STATEMENTS) {
                if (lexem.equals(pyStatement)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInPyKeywords(String lexem) {
        if (!lexem.equals(" ")) {
            for (String pyKeyword : this.PY_KEYWORDS) {
                if (lexem.equals(pyKeyword)) {
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

