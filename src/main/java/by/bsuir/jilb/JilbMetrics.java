package by.bsuir.jilb;

import by.bsuir.holshed.Container;
import by.bsuir.holshed.FileService;
import by.bsuir.holshed.LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JilbMetrics {

    private int tempIf = 0;

    private int tempElif = 0;

    private boolean isIfFirst;

    private final Container container;

    private final LexicalAnalyzer analyzer;

    private final File file;

    private final FileService service;

    public Container getContainer() {
        return container;
    }

    public JilbMetrics(Container container, LexicalAnalyzer analyzer, File file, FileService service) {
        this.container = container;
        this.analyzer = analyzer;
        this.file = file;
        this.service = service;
    }

    public void initLexemes() {
        service.initLexemes(file);
    }

    public int getMaxNestingLevel() {
        int maxNestingLevel = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                maxNestingLevel = this.getMaxNestingLevel(line.concat("\n"), maxNestingLevel);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxNestingLevel / 4;
    }

    private int getMaxNestingLevel(String line, int deep) {
        int tempDeep = 0;

        for (int i = 0; i < line.length(); i++) {
            if ((line.charAt(i) == ' ') && (isConditionsOperatorsInLine(line))) {
                tempDeep++;
            } else {
                break;
            }
        }

        if (isIfInLine(line)) {
            if (!isIfFirst) {
                tempIf = tempDeep;
                isIfFirst = true;
            }

            if (tempDeep <= tempIf) {
                tempIf = tempDeep;
                tempElif = 0;
            }
        }

        if (isElifInLine(line)) {
            tempElif++;
            tempDeep = tempDeep + 4 * tempElif;
        }

        if ((tempDeep > deep) && (tempDeep % 4 == 0)) {
            deep = tempDeep;
        }
        return deep;
    }

    private boolean isIfInLine(String line) {
        List<String> lexemes = new LinkedList<>();
        analyzer.lexemesFromLine(line, lexemes);
        for (String lexeme : lexemes) {
            if (lexeme.equals("if")) {
                return true;
            }
        }
        return false;
    }

    private boolean isElifInLine(String line) {
        List<String> lexemes = new LinkedList<>();
        analyzer.lexemesFromLine(line, lexemes);
        for (String lexeme : lexemes) {
            if (lexeme.equals("elif")) {
                return true;
            }
        }
        return false;
    }

    private boolean isConditionsOperatorsInLine(String line) {
        List<String> lexemes = new LinkedList<>();
        analyzer.lexemesFromLine(line, lexemes);
        for (String lexeme : lexemes) {
            if ((lexeme.equals("elif")) || (lexeme.equals("if")) ||
                    (lexeme.equals("for")) || (lexeme.equals("while"))) {
                return true;
            }
        }
        return false;
    }
}
