package src.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day5_Part2 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day5/input.txt");
        String input = Files.readString(inputPath);

        String[] components = input.split("\r\n\r\n");

        List<List<Range>> maps = new ArrayList<>();
        for (int componentId = 1; componentId < components.length; componentId++) {
            String[] componentContent = components[componentId].split("\r\n");
            List<Range> ranges = new ArrayList<>();
            for(int i = 1; i < componentContent.length; i++) { // Ignore first line
                List<Long> l = Arrays.stream(componentContent[i].split(" ")).map(Long::parseLong).toList();
                ranges.add((new Range(l.get(0), l.get(1), l.get(2))));
            }
            maps.add(ranges);
        }

        long lowestLocation = Long.MAX_VALUE;
        String[] seedsContent = components[0].split(" ");
        for(int i = 1; i < seedsContent.length; i += 2) {
            long start = Long.parseLong(seedsContent[i]);
            long end = Long.parseLong(seedsContent[i + 1]);
            for(long j = 0; j < end; j++) {
                long seed = start + j;
                for (List<Range> ranges : maps) {
                    for (Range range : ranges) {
                        long newSeed = range.getMapping(seed);
                        if (seed != newSeed) {
                            seed = newSeed;
                            break;
                        }
                    }
                }
                if (seed < lowestLocation) {
                    lowestLocation = seed;
                }
            }
            System.out.println("finished " + seedsContent[i]);
        }
        System.out.println(lowestLocation);
    }

}
