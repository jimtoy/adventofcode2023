package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        int total = 0;
        List<String> lines = Files.readAllLines(Paths.get("src/day1/input"));
        for (String line : lines) {
            total += getValue(line);
        }
        System.out.println(total);
    }

    public static int getValue(String input) {
        StringBuffer sb = new StringBuffer();
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

        int value = Integer.parseInt(min + max);
        return value;

    }

}