package src.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.OptionalInt;

public class Day11_Part1 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day11/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        final int lineLength = lines[0].length();

        char[][] carte = new char[lineLength][lines.length];
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            for (int j = 0; j < lineLength; j++) {
                carte[i][j] = line.charAt(j);
            }
        }

        System.out.println(input);
    }
}
