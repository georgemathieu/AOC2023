package src.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.OptionalInt;

public class Day10_Part1 {


    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day10/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        final int lineLength = lines[0].length();
        Pipe[][] carte = new Pipe[lineLength][lines.length];

        int startX = 0;
        int startY = 0;
        for (int i = 0; i < lines.length; i++) {
            for(int j = 0; j < lineLength; j++) {
                carte[j][i] = Pipe.toEnum(lines[i].charAt(j));

                if (carte[j][i].equals(Pipe.Starting)) {
                    startX = j;
                    startY = i;
                }
            }
        }

        int[][] weightedCarte = new int[lineLength][lines.length];

        // Brute force start directions
        // Pair pair = new Pair(Direction.Droite, new Coord(startX + 1, startY));
        Pair pair = new Pair(Direction.Gauche, new Coord(startX - 1, startY));
        weightedCarte[pair.coord.x][pair.coord.y] = 1;
        loopCarte(carte, weightedCarte, pair);

        pair = new Pair(Direction.Bas, new Coord(startX, startY + 1));
        weightedCarte[pair.coord.x][pair.coord.y] = 1;
        loopCarte(carte, weightedCarte, pair);

        /*for(int i = 0; i < weightedCarte.length; i++) {
            for (int j = 0; j < lineLength; j++) {
                System.out.print(weightedCarte[j][i] + " ");
            }
            System.out.println();
        }*/

        OptionalInt count = Arrays.stream(weightedCarte).flatMapToInt(Arrays::stream).max();
        System.out.println(count.isPresent() ? count.getAsInt() : 0);
        // 7005
    }

    private static void loopCarte(Pipe[][] carte, int[][] weightedCarte, Pair pair) {
        int stepCount = 1;
        while (!carte[pair.coord.x][pair.coord.y].equals(Pipe.Starting)) {
            stepCount++;
            Pipe pipe = carte[pair.coord.x][pair.coord.y];
            pair = pipe.apply(pair);

            if (weightedCarte[pair.coord.x][pair.coord.y] == 0 ||  stepCount < weightedCarte[pair.coord.x][pair.coord.y] ) {
                if (!carte[pair.coord.x][pair.coord.y].equals(Pipe.Starting)) {
                    weightedCarte[pair.coord.x][pair.coord.y] = stepCount;
                }
            }
        }
    }
}

enum Pipe {
    Starting{
        @Override
        Pair apply(Pair pair) {
            return pair;
        }
    },
    Ground{
        @Override
        Pair apply(Pair pair) {
            return pair;
        }
    },
    Horizontal{
        @Override
        Pair apply(Pair pair) {
            Coord newCoord;
            switch (pair.dir) {
                case Droite -> newCoord = new Coord(pair.coord.x + 1, pair.coord.y);
                case Gauche -> newCoord = new Coord(pair.coord.x - 1, pair.coord.y);
                default -> newCoord = pair.coord;
            }
            pair.coord = newCoord;
            return pair;
        }
    },
    Vertical{
        @Override
        Pair apply(Pair pair) {
            Coord newCoord;
            switch (pair.dir) {
                case Haut -> newCoord = new Coord(pair.coord.x, pair.coord.y - 1);
                case Bas -> newCoord = new Coord(pair.coord.x, pair.coord.y + 1);
                default -> newCoord = pair.coord;
            }
            pair.coord = newCoord;
            return pair;
        }
    },
    North_East{
        @Override
        Pair apply(Pair pair) {
            Coord newCoord;
            Direction newDir;
            switch (pair.dir) {
                case Bas -> {
                    newCoord = new Coord(pair.coord.x + 1, pair.coord.y);
                    newDir = Direction.Droite;
                }
                case Gauche -> {
                    newCoord = new Coord(pair.coord.x, pair.coord.y - 1);
                    newDir = Direction.Haut;
                }
                default -> {
                    newCoord = pair.coord;
                    newDir = pair.dir;
                }
            }
            pair.coord = newCoord;
            pair.dir = newDir;
            return pair;
        }
    },
    North_West{
        @Override
        Pair apply(Pair pair) {
            Coord newCoord;
            Direction newDir;
            switch (pair.dir) {
                case Bas -> {
                    newCoord = new Coord(pair.coord.x - 1, pair.coord.y);
                    newDir = Direction.Gauche;
                }
                case Droite -> {
                    newCoord = new Coord(pair.coord.x, pair.coord.y - 1);
                    newDir = Direction.Haut;
                }
                default -> {
                    newCoord = pair.coord;
                    newDir = pair.dir;
                }
            }
            pair.coord = newCoord;
            pair.dir = newDir;
            return pair;
        }
    },
    South_East{
        @Override
        Pair apply(Pair pair) {
            Coord newCoord;
            Direction newDir;
            switch (pair.dir) {
                case Haut -> {
                    newCoord = new Coord(pair.coord.x + 1, pair.coord.y);
                    newDir = Direction.Droite;
                }
                case Gauche -> {
                    newCoord = new Coord(pair.coord.x, pair.coord.y + 1);
                    newDir = Direction.Bas;
                }
                default -> {
                    newCoord = pair.coord;
                    newDir = pair.dir;
                }
            }
            pair.coord = newCoord;
            pair.dir = newDir;
            return pair;
        }
    },
    South_West{
        @Override
        Pair apply(Pair pair) {
            Coord newCoord;
            Direction newDir;
            switch (pair.dir) {
                case Haut -> {
                    newCoord = new Coord(pair.coord.x - 1, pair.coord.y);
                    newDir = Direction.Gauche;
                }
                case Droite -> {
                    newCoord = new Coord(pair.coord.x, pair.coord.y + 1);
                    newDir = Direction.Bas;
                }
                default -> {
                    newCoord = pair.coord;
                    newDir = pair.dir;
                }
            }
            pair.coord = newCoord;
            pair.dir = newDir;
            return pair;
        }
    };

    abstract Pair apply(Pair pair);
    public static Pipe toEnum(char c) {
        return switch (c) {
            case 'S' -> Starting;
            case '-' -> Horizontal;
            case '|' -> Vertical;
            case 'L' -> North_East;
            case 'J' -> North_West;
            case 'F' -> South_East;
            case '7' -> South_West;
            default -> Ground;
        };
    }
}

enum Direction {
    Haut, Bas, Gauche, Droite
}

class Coord {
    int x;
    int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Pair {
    Direction dir;
    Coord coord;

    public Pair(Direction dir, Coord coord) {
        this.dir = dir;
        this.coord = coord;
    }
}
