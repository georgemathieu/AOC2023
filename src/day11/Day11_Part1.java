package src.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day11_Part1 {

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

        // Expand
        for (int i = 0; i < carte.size(); i++) {
            if (carte.get(i).stream().noneMatch(c -> c == '#')) {
                carte.add(i, new ArrayList<>(carte.get(i)));
                i++;
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

        for (List<Character> characters : carte) {
            for (int j = characters.size() - 1; j >= 0; j--) {
                if (emptyColumns.get(j) != null && emptyColumns.get(j)) {
                    characters.add(j, '.');
                }
            }
        }

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
                distanceByPair.put(new Pair(c1, c2), (long) Math.abs(c2.x - c1.x) + Math.abs(c2.y - c1.y));
            }
        }

        long sum = distanceByPair.values().stream().mapToLong(Long::longValue).sum();

        // Print debug
        /*for (List<Character> characters : carte) {
            for (Character character : characters) {
                System.out.print(character);
            }
            System.out.println();
        }*/
        System.out.println(sum);
        // 9312968
    }
}

class Pair {
    Coord c1;
    Coord c2;

    public Pair(Coord c1, Coord c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Objects.equals(c1, pair.c1) && Objects.equals(c2, pair.c2) || Objects.equals(c1, pair.c2) && Objects.equals(c2, pair.c1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(c1, c2) + Objects.hash(c2, c1);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "c1=" + c1 +
                ", c2=" + c2 +
                '}';
    }
}

class Coord {
    int x;
    int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coord{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
