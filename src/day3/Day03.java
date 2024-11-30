package day3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/*
This code isn't mine.  Credit goes to zebalu who's code I used to help me find logic flaws in my own code.
https://github.com/zebalu/advent-of-code-2023/blob/9ec6dd44e461967e2b6704449100aacdfb6a3d96/aoc2023/src/main/java/io/github/zebalu/aoc2023/days/Day03.java#L14

 */

public class Day03 {

    public static void main(String[] args) {
        List<String> matrix = readInput().lines().toList();
        part1();
        part2(matrix);
    }

    public static List<Integer> part1() {
        List<String> matrix = readInput().lines().toList();

        Set<Coord> startCoords = new HashSet<>();
        for (int y = 0; y < matrix.size(); ++y) {
            String line = matrix.get(y);
            for (int x = 0; x < line.length(); ++x) {
                Coord coord = new Coord(x, y);
                char chr = line.charAt(x);
                if (isSymbol(chr)) {
                    coord.adjecents().stream()
                            .filter(c -> c.isValid(matrix) && Character.isDigit(matrix.get(c.y()).charAt(c.x())))
                            .map(c -> findFirstChar(matrix, c)).forEach(startCoords::add);
                }
            }
        }
        List<Integer> foundValues = new ArrayList<>();
        int sum = startCoords.stream().mapToInt(c -> {
            int value = readNumberFrom(matrix, c);
            foundValues.add(value);
            return value;
        }).sum();

        System.out.println("Day03 Sum: " + sum);
        return foundValues;
    }

    private static void part2(List<String> matrix) {
        int sum = 0;
        for (int y = 0; y < matrix.size(); ++y) {
            String line = matrix.get(y);
            for (int x = 0; x < line.length(); ++x) {
                Coord coord = new Coord(x, y);
                char chr = line.charAt(x);
                if (chr == '*') {
                    Set<Coord> candidates = coord.adjecents().stream()
                            .filter(c -> c.isValid(matrix) && Character.isDigit(matrix.get(c.y()).charAt(c.x())))
                            .map(c -> findFirstChar(matrix, c)).collect(Collectors.toSet());
                    if (candidates.size() == 2) {
                        sum += candidates.stream().mapToInt(c -> readNumberFrom(matrix, c)).reduce(1, (a, b) -> a * b);
                    }
                }
            }
        }
        System.out.println("Day03 Sum: " + sum);
    }

    private static Coord findFirstChar(List<String> matrix, Coord coord) {
        String line = matrix.get(coord.y());
        int x = coord.x();
        while (x > 0 && Character.isDigit(line.charAt(x - 1))) {
            --x;
        }
        return new Coord(x, coord.y());
    }

    private static int readNumberFrom(List<String> matrix, Coord startCoord) {
        String line = matrix.get(startCoord.y());
        int x = startCoord.x();
        while (x < line.length() && Character.isDigit(line.charAt(x))) {
            ++x;
        }
        int readValue = Integer.parseInt(line.substring(startCoord.x(), x));
        return readValue;
    }

    private static boolean isSymbol(char c) {
        if (c == '.' || Character.isDigit(c)) {
            return false;
        }
        return true;
    }

    private static String readInput() {
        try {
            return Files.readString(new File("src/day3/input").getAbsoluteFile().toPath());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private record Coord(int x, int y) {
        List<Coord> adjecents() {
            return List.of(new Coord(x - 1, y - 1), new Coord(x, y - 1), new Coord(x + 1, y - 1), new Coord(x - 1, y),
                    new Coord(x + 1, y), new Coord(x - 1, y + 1), new Coord(x, y + 1), new Coord(x + 1, y + 1));
        }

        boolean isValid(List<String> matrix) {
            return 0 <= y && y < matrix.size() && 0 <= x && x < matrix.get(y).length();
        }
    }
}