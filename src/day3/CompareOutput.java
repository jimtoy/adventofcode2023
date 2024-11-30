package day3;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CompareOutput {
    public static void main(String[] args) throws IOException {
        List<Integer> myFound = GearRatios.part1();
        List<Integer> theyFound = Day03.part1();

        System.out.println("My found: " + myFound.size());
        System.out.println("They found: " + theyFound.size());

        Collections.sort(myFound);
        Collections.sort(theyFound);

        System.out.println(myFound);
        System.out.println(theyFound);

    }
}
