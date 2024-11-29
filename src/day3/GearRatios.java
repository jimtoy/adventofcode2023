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

        List<String> foundNumbers = new ArrayList<>();

        for (Map.Entry<Integer, List<Character>> schematic : schematicMap.entrySet()) {
            int row = schematic.getKey();
            StringBuffer numberStore = new StringBuffer();
            boolean symbolfound = false;
            for (int i = 0; i < schematic.getValue().size(); i++) {
                char current = schematic.getValue().get(i);


                int left = i - 1;
                int right = i + 1;
                int up = row - 1;
                int down = row + 1;

                Character rightValue = getValue(schematicMap, row, right);
                Character rightUpValue = getValue(schematicMap, up, right);
                Character upValue = getValue(schematicMap, up, i);
                Character leftUpValue = getValue(schematicMap, up, left);
                Character leftValue = getValue(schematicMap, row, left);
                Character leftDownValue = getValue(schematicMap, down, left);
                Character downValue = getValue(schematicMap, down, i);
                Character downRightValue = getValue(schematicMap, down, i);

                boolean rightValueIsSymbol = isSymbol(rightValue);
                boolean rightUpValueIsSymbol = isSymbol(rightUpValue);
                boolean upValueIsSymbol = isSymbol(upValue);
                boolean leftValueIsSymbol = isSymbol(leftValue);
                boolean leftUpValueIsSymbol = isSymbol(leftUpValue);
                boolean leftDownValueIsSymbol = isSymbol(leftDownValue);
                boolean downValueIsSymbol = isSymbol(downValue);
                boolean downRightValueIsSymbol = isSymbol(downRightValue);

                if (rightUpValueIsSymbol || rightValueIsSymbol || upValueIsSymbol || leftValueIsSymbol || leftUpValueIsSymbol || leftDownValueIsSymbol || downValueIsSymbol || downRightValueIsSymbol) {
                    symbolfound = true;
                }

                if (isNumeric(current)) {
                    if (!isSymbol(current)) {
                        numberStore.append(current);
                    }
                } else {
                    if (!numberStore.isEmpty() && symbolfound) {
                        foundNumbers.add(numberStore.toString());
                    }
                    numberStore = new StringBuffer();
                    symbolfound = false;
                }


            }

        }
        System.out.println(foundNumbers);

        int totalValue = 0;
        for (String number : foundNumbers) {
            totalValue += Integer.parseInt(number);
        }


        System.out.println(totalValue);


    }

    public static Character getValue(Map<Integer, List<Character>> schematicMap, Integer row, Integer column) {

        try {
            return schematicMap.get(row).get(column);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isSymbol(Character c) {
        if (c == null || c == '.') {
            return false;
        } else {
            try {
                Integer.parseInt(c.toString());
                return false;
            } catch (NumberFormatException e) {
                return true;
            }
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
