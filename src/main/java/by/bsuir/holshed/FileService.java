package by.bsuir.holshed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileService {

    private final LexicalAnalyzer LEXICAL_ANALYZER;

    private final Container CONTAINER;

    {
        LEXICAL_ANALYZER = new LexicalAnalyzer();
    }

    public FileService(Container container) {
        this.CONTAINER = container;
    }

    public void initLexemes(File file) {
        CONTAINER.setLexemes(this.getLexemesFromFile(CONTAINER.getLexemes(), file));
        List<String> operator = new LinkedList<>();
        List<String> operands = new LinkedList<>();
        LEXICAL_ANALYZER.lexAlloc(operands, operator, CONTAINER.getLexemes());
        CONTAINER.setOperands(LEXICAL_ANALYZER.getLexemeCount(operands));
        CONTAINER.setOperators(LEXICAL_ANALYZER.getLexemeCount(operator));
    }

    private List<String> getLexemesFromFile(List<String> lexemes, File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines().forEach(line -> LEXICAL_ANALYZER.lexemesFromLine(line.concat("\n"), lexemes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lexemes;
    }

}
