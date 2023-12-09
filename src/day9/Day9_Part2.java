package src.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9_Part2 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day9/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        long sum = 0;
        for (String line : lines) {
            String[] inputNumbers = line.split(" ");
            List<Long> numbers = Arrays.stream(inputNumbers).map(Long::parseLong).toList();
            sum += computeDiffList(numbers);
        }
        System.out.println(sum);
    }

    private static long computeDiffList(List<Long> numbers) {
        List<Long> diffList = new ArrayList<>();
        for(int i = 1; i < numbers.size(); i++) {
            long currentNb = numbers.get(i);
            long previousNb = numbers.get(i - 1);
            diffList.add(currentNb - previousNb);
        }

        long firstNumber = numbers.get(0);
        if (diffList.stream().anyMatch(n -> n != 0)) {
            return firstNumber - computeDiffList(diffList);
        } else {
            return firstNumber;
        }
    }
}
