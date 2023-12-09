package src.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day9_Part1 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day9/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        long sum = 0;
        for (String line : lines) {
            String[] inputNumbers = line.split(" ");
            List<Long> numbers = Arrays.stream(inputNumbers).map(Long::parseLong).toList();

            long lastNumber = numbers.get(numbers.size() - 1);
            sum += computeDiffList(numbers, lastNumber);
        }

        System.out.println(sum);
        // 2098530125
    }

    private static long computeDiffList(List<Long> numbers, long acc) {
        List<Long> diffList = new ArrayList<>();
        for(int i = 1; i < numbers.size(); i++) {
            long currentNb = numbers.get(i);
            long previousNb = numbers.get(i - 1);
            diffList.add(currentNb - previousNb);
        }

        if (diffList.stream().anyMatch(n -> n != 0)) {
            long lastNumber = diffList.get(diffList.size() - 1);
            return computeDiffList(diffList, acc + lastNumber);
        } else {
            return acc;
        }
    }
}
