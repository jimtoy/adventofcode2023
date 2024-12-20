package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GearRatios {

    public static void main(String[] args) throws IOException {
        part1();

    }

    public static List<Integer> part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day3/input"));
        Map<Integer, List<Character>> schematicMap = new HashMap<>();
        int lineCount = 0;
        for (String line : lines) {
            lineCount++;
            List<Character> details = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                details.add(line.charAt(i));
            }
            schematicMap.put(lineCount, details);

        }

        Map<Integer, List<String>> foundMap = new HashMap<>();
        List<Integer> foundNumbers = new ArrayList<>();

        for (Map.Entry<Integer, List<Character>> schematic : schematicMap.entrySet()) {
            int row = schematic.getKey();
            StringBuilder numberStore = new StringBuilder();
            List<String> rowFoundNumbers = new ArrayList<>();
            boolean symbolfound = false;

            for (int i = 0; i < schematic.getValue().size(); i++) {
                char current = schematic.getValue().get(i);

                if (isNumeric(current)) {
                    //only check if we didnt already find a symbol
                    if (!symbolfound) {
                        symbolfound = isNearSymbol(schematicMap, row, i);
                    }
                    if (!isSymbol(current)) {
                        numberStore.append(current);
                    }
                } else {
                    if (!numberStore.isEmpty() && symbolfound) {
                        foundNumbers.add(Integer.parseInt(numberStore.toString()));
                        rowFoundNumbers.add(numberStore.toString());
                    }
                    numberStore.setLength(0);
                    symbolfound = false;
                }

                //eol hack
                if (i == schematic.getValue().size() - 1) {
                    if (!numberStore.isEmpty() && symbolfound) {
                        foundNumbers.add(Integer.parseInt(numberStore.toString()));
                        rowFoundNumbers.add(numberStore.toString());
                    }
                    numberStore.setLength(0);
                    symbolfound = false;
                }


            }
            foundMap.put(row, rowFoundNumbers);
        }

        int totalValue = 0;
        for (Integer number : foundNumbers) {
            totalValue += number;
        }
        System.out.println("Gear Ratio: " + totalValue);
        return foundNumbers;

    }

    public static boolean isNearSymbol(Map<Integer, List<Character>> schematicMap, int row, int i) {
        Character rightValue = getValue(schematicMap, row, i + 1);
        Character leftValue = getValue(schematicMap, row, i - 1);

        Character upValue = getValue(schematicMap, row - 1, i);
        Character downValue = getValue(schematicMap, row + 1, i);

        Character rightUpValue = getValue(schematicMap, row - 1, i + 1);
        Character leftUpValue = getValue(schematicMap, row - 1, i - 1);

        Character downRightValue = getValue(schematicMap, row + 1, i + 1);
        Character leftDownValue = getValue(schematicMap, row + 1, i - 1);

        boolean rightValueIsSymbol = isSymbol(rightValue);
        boolean leftValueIsSymbol = isSymbol(leftValue);
        boolean upValueIsSymbol = isSymbol(upValue);
        boolean downValueIsSymbol = isSymbol(downValue);
        boolean rightUpValueIsSymbol = isSymbol(rightUpValue);
        boolean leftUpValueIsSymbol = isSymbol(leftUpValue);
        boolean leftDownValueIsSymbol = isSymbol(leftDownValue);
        boolean downRightValueIsSymbol = isSymbol(downRightValue);

        return (rightUpValueIsSymbol || rightValueIsSymbol || upValueIsSymbol || leftValueIsSymbol || leftUpValueIsSymbol || leftDownValueIsSymbol || downValueIsSymbol || downRightValueIsSymbol);
    }

    public static Character getValue(Map<Integer, List<Character>> schematicMap, Integer row, Integer column) {
        try {
            return schematicMap.get(row).get(column);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isSymbol(Character c) {
        if (c == null || Character.isDigit(c) || c == '.') {
            return false;
        } else {
            return true;
        }

    }

    public static boolean isNumeric(Character c) {
        try {
            Integer.parseInt(c.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
