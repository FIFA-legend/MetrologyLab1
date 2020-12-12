package by.bsuir.chepin;

import by.bsuir.holshed.Container;
import by.bsuir.holshed.Dictionary;
import by.bsuir.holshed.FileService;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ChepinMetrics {

    private final Container CONTAINER;

    private final File FILE;

    private final FileService SERVICE;

    public Container getContainer() {
        return CONTAINER;
    }

    public ChepinMetrics(Container CONTAINER, File FILE) {
        this.CONTAINER = CONTAINER;
        this.FILE = FILE;
        this.SERVICE = new FileService(CONTAINER);
    }

    public void initLexemes() {
        SERVICE.initLexemes(FILE);
    }

    public Map<String, Integer> getOperatorsCount(Map<String, Integer> map) {
        List<String> list = new LinkedList<>(map.keySet());
        Dictionary dictionary = new Dictionary();
        for (String l : list) {
            if (l.contains("\"") || l.contains("'") || Character.isDigit(l.charAt(0)) || dictionary.isInPyKeywordsOperands(l)) {
                map.remove(l);
            }
        }
        map.replaceAll((k, v) -> map.get(k) - 1);
        return map;
    }

    public Map<String, String> chepinMetrics(Map<String, Integer> map) {
        Map<String, String> chepinMap = new HashMap<>();
        for (String str : map.keySet()) {
            if (isConditional(str)) {
                chepinMap.put(str, "C");
            } else if (isModifiable(str)) {
                chepinMap.put(str, "M");
            } else if (isInput(str)) {
                chepinMap.put(str, "P");
            } else {
                chepinMap.put(str, "T");
            }
        }
        return chepinMap;
    }

    public Map<String, String> chepinInputOutputMetrics(Map<String, String> map) {
        List<String> tempList = new LinkedList<>(map.keySet());
        Map<String, String> newMap = new HashMap<>();
        for (String key : tempList) {
            try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
                String inputPattern = key + "\\s*=[\\s|\\S]*input([\\s|\\S]*)[\\s|\\S]*";
                String outputPattern = "\\s*print([\\s|\\S]*" + key + "[\\s|\\S]*)[\\s|\\S]*";
                boolean isMatch = br.lines()
                    .anyMatch(line -> line.matches(inputPattern) || line.matches(outputPattern));
                if (isMatch) newMap.put(key, map.get(key));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newMap;
    }

    private boolean isConditional(String str) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            return br.lines().anyMatch(line -> line.contains(str) && (line.trim().startsWith("for") ||
                    line.trim().startsWith("while") || line.trim().startsWith("if") || line.trim().startsWith("elif")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isModifiable(String str) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String regex = str + "\\s*=[^=]+";
            long assignmentsNumber = br.lines()
                    .filter(line -> line.contains(str) && line.trim().matches(regex))
                    .count();
            return assignmentsNumber > 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isInput(String str) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String regex = str + "\\s*=[\\s|\\S]*input([\\s|\\S]*)[\\s|\\S]*";
            return br.lines().anyMatch(line -> line.trim().matches(regex));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
