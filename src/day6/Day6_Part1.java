package src.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Day6_Part1 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day6/input.txt");
        String input = Files.readString(inputPath);

        String[] lines = input.split("\r\n");
        lines[0] = lines[0].replaceAll("\\s+", ";");
        lines[1] = lines[1].replaceAll("\\s+", ";");

        int nbRaces = lines[0].split(";").length;
        int recordProduct = 1;
        for(int i = 1; i < nbRaces; i++) {
            int waysToWin = 0;
            int raceTime = Integer.parseInt(lines[0].split(";")[i]);
            int recordDistance = Integer.parseInt(lines[1].split(";")[i]);

            for(int loadingTime = 0; loadingTime < raceTime; loadingTime++) {
                int timeLeft = raceTime - loadingTime;
                int distance = loadingTime * timeLeft;
                if (distance > recordDistance) {
                    waysToWin++;
                }
            }
            recordProduct *= waysToWin;
        }


        System.out.println(recordProduct);
    }
}
