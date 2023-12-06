package src.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Day6_Part2_2ndVer {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day6/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");
        lines[0] = lines[0].replaceAll("\\s+", "");
        lines[1] = lines[1].replaceAll("\\s+", "");

        long raceTime = Long.parseLong(lines[0].split(":")[1]);
        long recordDistance = Long.parseLong(lines[1].split(":")[1]);

        // loadingTime * (raceTime - loadingTime) -  recordDistance
        // loadingTime ^2 + loadingTime * raceTime - recordDistance
        // Inéquation second degré
        // a > 0, parabole vers le bas

        long delta = (long) Math.pow(raceTime, 2) - 4 * 1 * recordDistance;
        long solution1 = (long) (-1 * raceTime - Math.sqrt(delta)) / 2;
        long solution2 = (long) (-1 * raceTime + Math.sqrt(delta)) / 2;

        long result = solution2 - solution1;
        System.out.println(result);
    }
}