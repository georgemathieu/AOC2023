package src.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Day10_Part2 {


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
        Pair pair = new Pair(Direction.Bas, new Coord(startX, startY + 1));
        weightedCarte[pair.coord.x][pair.coord.y] = 1;
        loopCarte(carte, weightedCarte, pair);

        for(int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lineLength; j++) {
                if (carte[j][i].equals(Pipe.Starting)) continue;
                if (weightedCarte[j][i] == 0) {
                    int nbVerticalPipesLeft = 0;
                    int nbVerticalPipesRight = 0;
                    boolean foundJ = false;
                    boolean found7 = false;
                    for(int k = j; k >= 0; k--) {
                        if (k == j) continue;
                        if (weightedCarte[k][i] < 1) {
                            foundJ = false;
                            found7 = false;
                            continue;
                        }
                        if (carte[k][i].equals(Pipe.Vertical)) {
                            nbVerticalPipesLeft++;
                        } else if (carte[k][i].equals(Pipe.North_West)) {
                            foundJ = true;
                        } else if (carte[k][i].equals(Pipe.South_West)) {
                            found7 = true;
                        } else if (foundJ && carte[k][i].equals(Pipe.South_East)) {
                            nbVerticalPipesLeft++;
                            foundJ = false;
                        } else if (found7 && carte[k][i].equals(Pipe.North_East)) {
                            nbVerticalPipesLeft++;
                            found7 = false;
                        } else if ((foundJ || found7) && !carte[k][i].equals(Pipe.Horizontal)) {
                            foundJ = false;
                            found7 = false;
                        }
                    }

                    boolean foundL = false;
                    boolean foundF = false;
                    for(int k = j; k < lineLength; k++) {
                        if (k == j) continue;
                        if (weightedCarte[k][i] < 1) {
                            foundL = false;
                            foundF = false;
                            continue;
                        }
                        if (carte[k][i].equals(Pipe.Vertical)) {
                            nbVerticalPipesRight++;
                        } else if (carte[k][i].equals(Pipe.North_East)) {
                            foundL = true;
                        } else if (carte[k][i].equals(Pipe.South_East)) {
                            foundF = true;
                        } else if (foundL && carte[k][i].equals(Pipe.South_West)) {
                            nbVerticalPipesRight++;
                            foundL = false;
                        } else if (foundF && carte[k][i].equals(Pipe.North_West)) {
                            nbVerticalPipesRight++;
                            foundF = false;
                        } else if ((foundL || foundF) && !carte[k][i].equals(Pipe.Horizontal)) {
                            foundL = false;
                            foundF = false;
                        }
                    }
                    if (nbVerticalPipesLeft % 2 == 1
                            && nbVerticalPipesRight % 2 == 1) {
                        weightedCarte[j][i] = -1;
                    }
                }
            }
        }

        for(int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lineLength; j++) {
                System.out.print(String.format("%5d", weightedCarte[j][i])  + " ");
            }
            System.out.println();
        }
        long count = Arrays.stream(weightedCarte)
                .flatMapToInt(Arrays::stream)
                .filter(i -> i == -1)
                .count();
        System.out.println(count);
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