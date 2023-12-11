package src.day12;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Day12_Part1 {


    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day12/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");


        System.out.println(input);
    }
}