package src.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Day1 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day1/input.txt");
        String input = Files.readString(inputPath);

        System.out.println(input);
    }
}
