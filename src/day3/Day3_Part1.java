package src.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Part 1 : The input file is translated to an integer two-dimensional array.
 * The idea was to put the whole number in every slots its digits are in, and 0s everywhere else.
 * Example : ..467... becomes 0;0;467;467;467;0;0;0.
 * Non-alphanumeric symbols are translated to -1.
 * The integer array is then processed to get the full sum of values adjacent to -1 values.
 */
public class Day3_Part1 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day3/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        Pattern p = Pattern.compile("\\d+");
        final int maxRow = lines.length;
        final int maxColumn = lines[0].length();

        int[][] fullNumberArray = new int[maxRow][maxColumn];
        int rowCount = 0;
        for (String line : lines) {
            int columnCount = 0;
            Matcher m = p.matcher(line);
            ArrayList<Integer> numbers = new ArrayList<>();
            while(m.find()) {
               numbers.add(Integer.parseInt(m.group()));
            }

            int numbersCount = 0;
            boolean processingNumber = false;
            for (char c : line.toCharArray()) {
                if (!Character.isDigit(c) && c != '.') {
                    fullNumberArray[rowCount][columnCount] = -1;
                }
                if (!Character.isDigit(c) && processingNumber) {
                    numbersCount++;
                    processingNumber = false;
                } else if (Character.isDigit(c)) {
                    processingNumber = true;
                    int currentNumber = numbers.get(numbersCount);
                    fullNumberArray[rowCount][columnCount] = currentNumber;
                }
                columnCount++;
            }
            rowCount++;
        }

        int sum = 0;
        for (int i = 0; i < maxRow - 1; i++) {
            for (int j = 0; j < maxColumn - 1; j++) {
                // Only add for specific symbols
                if (fullNumberArray[i][j] == -1) {
                    sum += computeAdjacentSum(i, j, fullNumberArray, maxRow, maxColumn);
                }
            }
        }
        System.out.println(sum);
    }

    private static int computeAdjacentSum(int row, int column, int[][] fullNumberArray, int maxRow, int maxColumn) {
        Set<Integer> adjacentNumbers = new HashSet<>();
        if (row != 0) { // Up
            adjacentNumbers.add(fullNumberArray[row - 1][column]);

            if (column != 0) { // Up-Left
                adjacentNumbers.add(fullNumberArray[row - 1][column - 1]);
            }
            if (column != maxColumn) { // Up-Right
                adjacentNumbers.add(fullNumberArray[row - 1][column + 1]);
            }
        }

        if (row != maxRow) { // Down
            adjacentNumbers.add(fullNumberArray[row + 1][column]);

            if (column != 0) { // Down-Left
                adjacentNumbers.add(fullNumberArray[row + 1][column - 1]);
            }
            if (column != maxColumn) { // Down-Right
                adjacentNumbers.add(fullNumberArray[row + 1][column + 1]);
            }
        }

        if (column != 0) { // Left
            adjacentNumbers.add(fullNumberArray[row][column - 1]);
        }
        if (column != maxColumn) { // Right
            adjacentNumbers.add(fullNumberArray[row][column + 1]);
        }

        return adjacentNumbers.stream().mapToInt(Integer::intValue).sum();
    }
}
