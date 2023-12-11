package src.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day11_Part2 {

    private static final int INCREMENT = 999_999;

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day11/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        final int lineLength = lines[0].length();

        // Parse
        List<List<Character>> carte = new ArrayList<>();
        for (String line : lines) {
            List<Character> charList = new ArrayList<>();
            for (int j = 0; j < lineLength; j++) {
                charList.add(line.charAt(j));
            }
            carte.add(charList);
        }

        // No more Expand :(
        List<Integer> emptyRowIds = new ArrayList<>();
        for (int i = 0; i < carte.size(); i++) {
            if (carte.get(i).stream().noneMatch(c -> c == '#')) {
                emptyRowIds.add(i);
            }
        }

        Map<Integer, Boolean>  emptyColumns = new HashMap<>();
        for (List<Character> characters : carte) {
            for (int j = 0; j < characters.size(); j++) {
                if (emptyColumns.get(j) != null && !emptyColumns.get(j)) continue;
                if (characters.get(j) == '.') {
                    emptyColumns.put(j, true);
                } else {
                    emptyColumns.put(j, false);
                }
            }
        }
        List<Integer> emptyColumnsIds = emptyColumns.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).toList();

        Map<Pair, Long> distanceByPair = new HashMap<>();
        List<Coord> galaxies = new ArrayList<>();
        for (int i = 0; i < carte.size(); i++) {
            for (int j = 0; j < carte.get(i).size(); j++) {
                if (carte.get(i).get(j) == '#') {
                    galaxies.add(new Coord(i, j));
                }
            }
        }

        for (Coord c1 : galaxies) {
            for (Coord c2 : galaxies) {
                long distance = 0l;
                for (Integer emptyColumnsId : emptyColumnsIds) {
                    if ((c2.y < emptyColumnsId && emptyColumnsId < c1.y)
                            || (c1.y < emptyColumnsId && emptyColumnsId < c2.y)) {
                        distance += INCREMENT;
                    }
                }

                for (Integer emptyRowId : emptyRowIds) {
                    if ((c2.x < emptyRowId && emptyRowId < c1.x)
                            || (c1.x < emptyRowId && emptyRowId < c2.x)) {
                        distance += INCREMENT;
                    }
                }

                distance += (long) Math.abs(c2.x - c1.x) + Math.abs(c2.y - c1.y);
                distanceByPair.put(new Pair(c1, c2), distance);
            }
        }

        long sum = distanceByPair.values().stream().mapToLong(Long::longValue).sum();
        System.out.println(sum);
        // 597714117556
    }
}