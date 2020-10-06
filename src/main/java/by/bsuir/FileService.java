package by.bsuir;

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

    public void initLexems(File file) {
        CONTAINER.setLexems(this.getLexemsFromFile(CONTAINER.getLexems(), file));
        List<String> operator = new LinkedList<>();
        List<String> operands = new LinkedList<>();
        LEXICAL_ANALYZER.lexAlloc(operands, operator, CONTAINER.getLexems());
        CONTAINER.setOperands(LEXICAL_ANALYZER.getLexemCount(operands));
        CONTAINER.setOperators(LEXICAL_ANALYZER.getLexemCount(operator));
    }

    private List<String> getLexemsFromFile(List<String> lexems, File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines().forEach(line -> LEXICAL_ANALYZER.lexemsFromLine(line.concat("\n"), lexems));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lexems;
    }

}
