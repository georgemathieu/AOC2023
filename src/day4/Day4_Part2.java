package src.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Day4_Part2 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day4/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Map<Integer, Integer> scoreByCard = new HashMap<>();
        Map<Integer, Integer> numberOfCards = new HashMap<>();
        for (String line : lines) {
            line = line.replaceAll("\\s+", ";");
            String[] splitNumbers = line.split(":;")[1].split(";\\|;");
            String[] winningNumbers = splitNumbers[0].split(";");
            String[] myNumbers = splitNumbers[1].split(";");
            int cardId = Integer.parseInt(line.split(":;")[0].split(";")[1]);
            numberOfCards.put(cardId, 1);
            Set<String> winningNumbersSet = Set.of(winningNumbers);
            int lineScore = 0;
            for (String myNumber : myNumbers) {
                if (winningNumbersSet.contains(myNumber)) {
                    lineScore++;
                }
            }
            scoreByCard.put(cardId, lineScore);
        }

        for (Map.Entry<Integer, Integer> entry : scoreByCard.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            for (int i = key + 1; i <= key + value; i++) {
                numberOfCards.put(i, numberOfCards.get(i) + numberOfCards.get(key));
            }
        }
        int sum = numberOfCards.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);
    }
}
