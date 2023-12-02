package src.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day2_Part1 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day2/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        Set<Integer> gameIds = new HashSet<>();
        for (String line : lines) {
            String[] components = line.split(": ");
            Integer gameId = Integer.parseInt(components[0].split(" ")[1]);

            boolean isValid = true;
            for (String cubeSet : components[1].split("; ")) {
                Map<ColorCube, Integer> bagContent = new HashMap<>();

                for (String singleCube : cubeSet.split(", ")) {
                    String[] cubeContent = singleCube.split(" "); // 0 - Color, 1 - value
                    ColorCube cube = ColorCube.valueOf(cubeContent[1].toUpperCase());
                    int cubeAmount = Integer.parseInt(cubeContent[0]);
                    bagContent.put(cube, cubeAmount);
                }
                long invalidBags = bagContent.entrySet().stream().filter(entry -> entry.getKey().isTooMuch(entry.getValue())).count();
                if (invalidBags > 0) {
                    isValid = false;
                }
            }
            if (isValid) {
                gameIds.add(gameId);
            }
        }
        int sum = gameIds.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Part 1 : " + sum);
    }
}

enum ColorCube {
    RED {
        @Override
        boolean isTooMuch(int cubeAmount) {
            return cubeAmount > 12;
        }
    },
    BLUE {
        @Override
        boolean isTooMuch(int cubeAmount) {
            return cubeAmount > 14;
        }
    },
    GREEN{
        @Override
        boolean isTooMuch(int cubeAmount) {
            return cubeAmount > 13;
        }
    } ;

    abstract boolean isTooMuch(int cubeAmount);
}
