package src.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8_Part2 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day8/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        List<Character> instructions = new ArrayList<>();
        for (char c : lines[0].toCharArray()) {
            instructions.add(c);
        }

        // Build nodes map
        Map<String, Day8_Part1.Pair> nodes = new HashMap<>();
        List<String> startingNodes = new ArrayList<>();
        for(int i = 2; i < lines.length; i++) {
            String line = lines[i].replaceAll("\\s+", "");
            String[] nodeContent = line.split("="); // 0 - name, 1 directions
            String nodeName = nodeContent[0];
            String[] directions = nodeContent[1].replace("(", "").replace(")", "").split(",");
            Day8_Part1.Pair nodeDirections = new Day8_Part1.Pair(directions[0], directions[1]);
            nodes.put(nodeName, nodeDirections);

            if (nodeName.endsWith("A")) startingNodes.add(nodeName);
        }



        List<Long> numbers = new ArrayList<>();
        for (String startingNode : startingNodes) {
            boolean foundZ = false;
            int stepCount = 0;
            int instructionIndex = 0;
            String currentNode = startingNode;
            while (!foundZ) {
                char instruction = instructions.get(instructionIndex);
                boolean isLeft = instruction == 'L';
                currentNode = isLeft ? nodes.get(currentNode).left() : nodes.get(currentNode).right();
                if (currentNode.endsWith("Z")) {
                    foundZ = true;
                }

                stepCount++;
                instructionIndex++;
                if (instructionIndex == instructions.size()) {
                    instructionIndex = 0; // Reset index if list of input is finished
                }
            }
            numbers.add((long) stepCount);
        }

        long ppcm = 1;
        Long previousNumber = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            long currentNumber = numbers.get(i);
            long product = previousNumber * currentNumber;
            long leftover = previousNumber % currentNumber;
            while(leftover != 0){
                previousNumber = currentNumber;
                currentNumber = leftover;
                leftover = previousNumber % currentNumber;
            }
            ppcm = Math.abs(product) / currentNumber;
            previousNumber = ppcm;
        }
        System.out.println(ppcm);
        // 14321394058031
    }
}
