package src.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Day6_Part2 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day6/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");
        lines[0] = lines[0].replaceAll("\\s+", "");
        lines[1] = lines[1].replaceAll("\\s+", "");

        int waysToWin = 0;
        long raceTime = Long.parseLong(lines[0].split(":")[1]);
        long recordDistance = Long.parseLong(lines[1].split(":")[1]);

        for(long loadingTime = 0; loadingTime < raceTime; loadingTime++) {
            long timeLeft = raceTime - loadingTime;
            long distance = loadingTime * timeLeft;
            if (distance > recordDistance) {
                waysToWin++;
            }
        }

        System.out.println(waysToWin);
    }
}
