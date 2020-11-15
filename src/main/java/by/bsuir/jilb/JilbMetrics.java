package by.bsuir.jilb;

import by.bsuir.holshed.Container;
import by.bsuir.holshed.FileService;
import by.bsuir.holshed.LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JilbMetrics {

    private int prevDepth;

    private int maxNestingLevel;

    private int flushLevel;

    private final Map<Integer, Integer> simpleOperands;

    private final Map<Integer, Integer> tempElif;

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
        tempElif = new TreeMap<>();
        simpleOperands = new TreeMap<>();
    }

    public void initLexemes() {
        service.initLexemes(file);
    }

    public int getMaxNestingLevel() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                this.getMaxNestingLevel(line.concat("\n"));
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.compareMaps(0);
        return maxNestingLevel;
    }

    private void getMaxNestingLevel(String line) {
        int depth = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ') depth++;
            else break;
        }
        if (depth < prevDepth) {
            this.compareMaps(depth);
            List<Integer> simpleList = new LinkedList<>(simpleOperands.keySet());
            for (int l : simpleList) {
                if (l >= depth) simpleOperands.remove(l);
            }
            /*for (int conditionalKey : simpleOperands.keySet()) {
                if (conditionalKey >= depth) simpleOperands.remove(conditionalKey);
            }*/
            List<Integer> list = new LinkedList<>(tempElif.keySet());
            for (int l : list) {
                if (l > depth) tempElif.remove(l);
            }
            /*for (int elifKey : tempElif.keySet()) {
                if (elifKey > depth) tempElif.remove(elifKey);
            }*/
        }
        if (this.isCycleOperatorsInLine(line)) {
            simpleOperands.put(depth, 1);
        }
        if (this.isElifInLine(line) || this.isIfInLine(line)) {
            if (depth == flushLevel) maxNestingLevel++;
            if (tempElif.containsKey(depth)) {
                tempElif.put(depth, tempElif.get(depth) + 1);
            } else {
                tempElif.put(depth, 1);
            }
        }
        prevDepth = depth;
    }

    private void compareMaps(int depth) {
        int sum = 0;
        for (int conditionals : simpleOperands.values()) {
            sum += conditionals;
        }
        for (int elifs : tempElif.values()) {
            sum += elifs;
        }
        if (sum >= maxNestingLevel) {
            maxNestingLevel = sum;
            flushLevel = depth;
        }
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

    private boolean isCycleOperatorsInLine(String line) {
        List<String> lexemes = new LinkedList<>();
        analyzer.lexemesFromLine(line, lexemes);
        for (String lexeme : lexemes) {
            if (((lexeme.equals("for") || lexeme.equals("while")))) {
                return true;
            }
        }
        return false;
    }
}
