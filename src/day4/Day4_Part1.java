package src.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class Day4_Part1 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day4/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        int sum = 0;
        for (String line : lines) {
            line = line.replaceAll("\\s+", ";");
            String[] splitNumbers = line.split(":;")[1].split(";\\|;");
            String[] winningNumbers = splitNumbers[0].split(";");
            String[] myNumbers = splitNumbers[1].split(";");
            Set<String> winningNumbersSet = Set.of(winningNumbers);
            int lineScore = 0;
            int pow = 0;
            for (String myNumber : myNumbers) {
                if (winningNumbersSet.contains(myNumber)) {
                    lineScore = (int) Math.pow(2, pow);
                    pow++;
                }
            }
            sum += lineScore;
        }

        System.out.println(sum);
    }
}
