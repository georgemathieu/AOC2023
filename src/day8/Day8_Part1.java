package src.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8_Part1 {

    public record Pair(String left, String right) {};

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day8/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        List<Character> instructions = new ArrayList<>();
        for (char c : lines[0].toCharArray()) {
            instructions.add(c);
        }

        // Build nodes map
        Map<String, Pair> nodes = new HashMap<>();
        for(int i = 2; i < lines.length; i++) {
            String line = lines[i].replaceAll("\\s+", "");
            String[] nodeContent = line.split("="); // 0 - name, 1 directions
            String nodeName = nodeContent[0];
            String[] directions = nodeContent[1].replace("(", "").replace(")", "").split(",");
            Pair nodeDirections = new Pair(directions[0], directions[1]);
            nodes.put(nodeName, nodeDirections);
        }

        int stepCount = 0;
        int instructionIndex = 0;
        String currentNode = "AAA";
        final String lastNode = "ZZZ";
        while (!currentNode.equals(lastNode)) {
            char instruction = instructions.get(instructionIndex);
            if (instruction == 'L') currentNode = nodes.get(currentNode).left();
            if (instruction == 'R') currentNode = nodes.get(currentNode).right();
            stepCount ++;
            instructionIndex++;

            if (instructionIndex == instructions.size()) {
                instructionIndex = 0; // Reset index if list of input is finished
            }
        }
        System.out.println(stepCount);
    }
}
