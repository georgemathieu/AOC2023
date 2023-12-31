package src.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day7_Part1 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day7/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        TreeMap<Hand, Integer> handMap = new TreeMap<>();
        for (String line : lines) {
            String[] content = line.split(" ");
            int bid = Integer.parseInt(content[1]);

            List<CardType> cards = new ArrayList<>();
            for (char c : content[0].toCharArray()) {
                cards.add(CardType.toEnum(c));
            }
            handMap.put(new Hand(cards), bid);
        }

        long sum = 0;
        int cpt = 1;
        for (Integer handBid : handMap.values()) {
            sum += (cpt * handBid);
            cpt++;
        }
        System.out.println(sum);
    }
}

class Hand implements Comparable<Hand> {
    private List<CardType> cards = new ArrayList<>();
    private HandType type;

    public Hand(List<CardType> cards) {
        this.cards = cards;
        this.type = HandType.toEnum(cards);
    }

    @Override
    public int compareTo(Hand o) {
        if (this.type.ordinal() < o.type.ordinal()) return 1;
        if (this.type.ordinal() > o.type.ordinal()) return -1;
        if (this.type.ordinal() == o.type.ordinal()) {
            for(int i = 0; i < cards.size(); i++) {
                if (this.cards.get(i).ordinal() < o.cards.get(i).ordinal()) return 1;
                if (this.cards.get(i).ordinal() > o.cards.get(i).ordinal()) return -1;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hand hand = (Hand) o;
        return cards.equals(hand.cards) && type == hand.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards, type);
    }
}

enum CardType {
    A, K, Q, J, T, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO, ONE;

    public static CardType toEnum(char letter) {
        if (letter == 'A') return A;
        if (letter == 'K') return K;
        if (letter == 'Q') return Q;
        if (letter == 'J') return J;
        if (letter == 'T') return T;
        return CardType.values()[14 - Integer.parseInt("" + letter)];
    }
}

enum HandType {
    Perfect, Carre, Full, Brelan, Double, Pair, Normal;

    public static HandType toEnum(List<CardType> cards) {
        Map<CardType, Integer> count = new HashMap<>();
        for (CardType card : cards) {
            Integer cardCount = count.get(card);
            if (cardCount == null) {
                count.put(card, 1);
            } else {
                count.put(card, cardCount + 1);
            }
        }

        switch (count.entrySet().size()) {
            case 1: return Perfect;
            case 4: return Pair;
            case 5: return Normal;
            case 2:
                OptionalInt max2 = count.values().stream().mapToInt(Integer::intValue).max();
                if (max2.isPresent() && max2.getAsInt() > 3) {
                    return Carre;
                } else {
                    return Full;
                }
            case 3:
                OptionalInt max3 = count.values().stream().mapToInt(Integer::intValue).max();
                if (max3.isPresent() && max3.getAsInt() > 2) {
                    return Brelan;
                } else {
                    return Double;
                }
            default: return Normal;
        }
    }
}