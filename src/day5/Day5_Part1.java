package src.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day5_Part1 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day5/input.txt");
        String input = Files.readString(inputPath);

        String[] components = input.split("\r\n\r\n");

        List<Range> maps = new ArrayList<>();
        for (int componentId = 1; componentId < components.length; componentId++) {
            String[] componentContent = components[componentId].split("\r\n");
            Range range = null;
            for(int i = 1; i < componentContent.length; i++) { // Ignore first line
                List<Long> l = Arrays.stream(componentContent[i].split(" ")).map(Long::parseLong).toList();
                map = addRange(map, l.get(0), l.get(1), l.get(2));
            }
            maps.add(map);
        }

        List<Long> seeds = new ArrayList<>();
        String[] seedsContent = components[0].split(" ");
        for(int i = 1; i < seedsContent.length; i ++) {
            seeds.add(Long.parseLong(seedsContent[i]));
        }

        long lowestLocation = Long.MAX_VALUE;
        for (Long seed : seeds) {
            for (Map<Long, Long> map : maps) {
                seed = map.getOrDefault(seed, seed);
            }
            if (seed < lowestLocation) {
                lowestLocation = seed;
            }
        }

        System.out.println(lowestLocation);
    }

    private static Map addRange(Map<Long, Long> map, long destinationRange, long sourceRange, long rangeLength) {
        for (int i = 0; i < rangeLength; i++) {
            map.put(sourceRange + i, destinationRange + i);
        }
        return map;
    }
}

class Range {
    long destinationRange;
    long sourceRange;
    long rangeLength;

    public Range(long destinationRange, long sourceRange, long rangeLength) {
        this.destinationRange = destinationRange;
        this.sourceRange = sourceRange;
        this.rangeLength = rangeLength;
    }

    public long getMapping(long value) {
        long delta = destinationRange - sourceRange;
        if (value >= sourceRange && value < sourceRange + rangeLength) {
            return value + delta;
        }
        return value;
    }
}