package src.day12;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day12_Part1 {

    public record Pair(List<Spring> springs, List<Integer> damagedGroups){}


    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day12/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        List<Pair> fullSpringList = new ArrayList<>();
        for (String line : lines) {
            List<Spring> springs = new ArrayList<>();
            for (char c : line.split(" ")[0].toCharArray()) {
                springs.add(Spring.toEnum(c));
            }
            List<Integer> damagedGroups = new ArrayList<>();
            for (String weight : line.split(" ")[1].split(",")) {
                damagedGroups.add(Integer.parseInt(weight));
            }
            fullSpringList.add(new Pair(springs, damagedGroups));
        }

        for (Pair pair : fullSpringList) {
            
        }


        System.out.println(input);
    }
}

enum Spring {
    Normal, Damaged, Unknown;

    public static Spring toEnum(char c) {
        return switch (c) {
            case '#' -> Damaged;
            case '?' -> Unknown;
            default -> Normal;
        };
    }
}