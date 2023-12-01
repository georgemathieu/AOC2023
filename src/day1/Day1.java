package src.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Day1 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day1/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\n");

        // Part 1
        /*int sum = 0;
        for (String l : lines) {
            int[] arr = l.chars().filter(Character::isDigit).toArray();
            sum += Integer.parseInt("" + (char) arr[0] + (char) arr[arr.length - 1]);

        }
        System.out.println(sum);*/

        // Part 2
        int sum = 0;

        HashMap<String, Integer> dict = new HashMap<>();
        dict.put("one", 1);
        dict.put("two", 2);
        dict.put("three", 3);
        dict.put("four", 4);
        dict.put("five", 5);
        dict.put("six", 6);
        dict.put("seven", 7);
        dict.put("eight", 8);
        dict.put("nine", 9);
        dict.put("1", 1);
        dict.put("2", 2);
        dict.put("3", 3);
        dict.put("4", 4);
        dict.put("5", 5);
        dict.put("6", 6);
        dict.put("7", 7);
        dict.put("8", 8);
        dict.put("9", 9);

        for (String l : lines) {
            int lowestIndex = 999;
            int lowestValue = 999;
            int maxIndex = 0;
            int maxValue = 0;
            for (String key: dict.keySet()) {
                int index = l.indexOf(key);
                int lastIndex = l.lastIndexOf(key);

                if (index == -1) continue;
                if (index <= lowestIndex) {
                    lowestIndex = index;
                    lowestValue = dict.get(key);
                }

                if (lastIndex >= maxIndex) {
                    maxIndex = lastIndex;
                    maxValue = dict.get(key);
                }
            }
            sum += Integer.parseInt("" + lowestValue + maxValue);
        }
        System.out.println(sum);
    }
}
