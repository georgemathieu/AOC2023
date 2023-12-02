package src.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Day2_Part2 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day2/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        Map<Integer, Integer> productByGameId = new HashMap<>();
        for (String line : lines) {
            String[] components = line.split(": ");
            Integer gameId = Integer.parseInt(components[0].split(" ")[1]);

            Map<ColorCube, Integer> maxAmountByColor = new HashMap<>();
            for (String cubeSet : components[1].split("; ")) {
                Map<ColorCube, Integer> bagContent = new HashMap<>();

                for (String singleCube : cubeSet.split(", ")) {
                    String[] cubeContent = singleCube.split(" "); // 0 - Color, 1 - value
                    ColorCube cube = ColorCube.valueOf(cubeContent[1].toUpperCase());
                    int cubeAmount = Integer.parseInt(cubeContent[0]);
                    bagContent.put(cube, cubeAmount);
                }

                // Update max Amount by color
                for (Map.Entry<ColorCube, Integer> cubeEntry: bagContent.entrySet()) {
                    ColorCube cube = cubeEntry.getKey();
                    int amount = cubeEntry.getValue();
                    if (maxAmountByColor.get(cube) == null || maxAmountByColor.get(cube) < amount) {
                        maxAmountByColor.put(cube, amount);
                    }
                }
            }

            productByGameId.put(gameId, maxAmountByColor.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .reduce(1, (a, b) -> a * b));
        }
        int sum = productByGameId.entrySet().stream().mapToInt(e -> e.getValue().intValue()).sum();
        System.out.println("Part 2 : " + sum);
    }


}
