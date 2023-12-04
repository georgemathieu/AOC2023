package src.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class Day5_Part1 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day5/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");
        int sum = 0;


        System.out.println(sum);
    }
}
