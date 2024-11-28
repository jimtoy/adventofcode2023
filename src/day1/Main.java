package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    static Map<String, String> lookup = new HashMap<>();


    public static void main(String[] args) throws IOException {

        lookup.put("eight", "ei8ht");
        lookup.put("two", "t2o");
        lookup.put("one", "o1e");
        lookup.put("three", "th3ee");
        lookup.put("four", "f4ur");
        lookup.put("five", "f5ve");
        lookup.put("six", "s6x");
        lookup.put("seven", "se7en");
        lookup.put("nine", "n9ne");

        int total = 0;
        List<String> lines = Files.readAllLines(Paths.get("src/day1/input"));
        for (String line : lines) {
            total += getValue(line);
        }
        System.out.println(total);
    }

    public static int getValue(String input) {
        StringBuilder sb = new StringBuilder();
        String orginal = input;

        while (true) {

            String first = "";
            int firstIndex = 1000;
            for (Map.Entry<String, String> key : lookup.entrySet()) {
                int position = input.indexOf(key.getKey());
                if (position > -1 && position < firstIndex) {
                    first = key.getKey();
                    firstIndex = position;
                }
            }

            if (firstIndex == 1000) {
                break;
            } else {
                input = input.replaceAll(first, lookup.get(first));
            }

        }

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            try {
                sb.append(Integer.parseInt(String.valueOf(c)));
            } catch (NumberFormatException e) {
                //System.out.println("Not a number");
            }
        }

        String min = sb.substring(0, 1);
        String max = sb.substring(sb.length() - 1, sb.length());

        System.out.println("Input: " + orginal + " Value:" + min + max);
        return Integer.parseInt(min + max);

    }

}