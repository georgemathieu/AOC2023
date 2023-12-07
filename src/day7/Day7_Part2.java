package src.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day7_Part2 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Paths.get("src/day7/input.txt");
        String input = Files.readString(inputPath);
        String[] lines = input.split("\r\n");

        TreeMap<Hand2, Integer> handMap = new TreeMap<>();
        for (String line : lines) {
            String[] content = line.split(" ");
            int bid = Integer.parseInt(content[1]);

            List<CardType2> cards = new ArrayList<>();
            for (char c : content[0].toCharArray()) {
                cards.add(CardType2.toEnum(c));
            }
            handMap.put(new Hand2(cards), bid);
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

class Hand2 implements Comparable<Hand2> {
    private List<CardType2> cards = new ArrayList<>();
    private HandType2 type;

    public Hand2(List<CardType2> cards) {
        this.cards = cards;
        this.type = HandType2.toEnum(cards);
    }

    @Override
    public int compareTo(Hand2 o) {
        if (this.type.ordinal() < o.type.ordinal()) return 1;
        if (this.type.ordinal() > o.type.ordinal()) return -1;
        if (this.type.ordinal() == o.type.ordinal()) {
            for (int i = 0; i < cards.size(); i++) {
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
        Hand2 hand = (Hand2) o;
        return cards.equals(hand.cards) && type == hand.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards, type);
    }
}

enum CardType2 {
    A, K, Q, T, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO, ONE, J;

    public static CardType2 toEnum(char letter) {
        if (letter == 'A') return A;
        if (letter == 'K') return K;
        if (letter == 'Q') return Q;
        if (letter == 'J') return J;
        if (letter == 'T') return T;
        return CardType2.values()[13 - Integer.parseInt("" + letter)];
    }
}

enum HandType2 {
    Perfect, Carre, Full, Brelan, Double, Pair, Normal;

    public static HandType2 toEnum(List<CardType2> cards) {
        Map<CardType2, Integer> count = new HashMap<>();
        for (CardType2 card : cards) {
            Integer cardCount = count.get(card);
            if (cardCount == null) {
                count.put(card, 1);
            } else {
                count.put(card, cardCount + 1);
            }
        }

        HandType2 handType = null;
        Integer nbJ = count.get(CardType2.J); // Store nb of J
        count.remove(CardType2.J);
        handType = getHandType2(count, nbJ == null ? 0 : nbJ);


        if (nbJ != null) {
            handType = adjustHandWithJ(handType, nbJ);
        }
        return handType;
    }

    private static HandType2 getHandType2(Map<CardType2, Integer> count, Integer nbJ) {
        HandType2 handType;
        switch (count.entrySet().size() + nbJ) { // Translate nb of J to garbage
            case 1:
                handType = Perfect;
                break;
            case 4:
                handType = Pair;
                break;
            case 2:
                OptionalInt max2 = count.values().stream().mapToInt(Integer::intValue).max();
                if (max2.isPresent() && max2.getAsInt() > 3) {
                    handType = Carre;
                } else {
                    handType = Full;
                }
                break;
            case 3:
                OptionalInt max3 = count.values().stream().mapToInt(Integer::intValue).max();
                if (max3.isPresent() && max3.getAsInt() > 2) {
                    handType = Brelan;
                } else {
                    handType = Double;
                }
                break;
            default:
                handType = Normal;
                break;
        }
        return handType;
    }

    private static HandType2 adjustHandWithJ(HandType2 handType, Integer nbJ) {
        for (int i = 1; i <= nbJ; i++) {
            switch (handType) {
                case Carre -> handType = Perfect;
                case Brelan -> handType = Carre;
                case Double -> handType = Full;
                case Pair -> handType = Brelan;
                case Normal -> handType = Pair;
            }
        }
        return handType;
    }
}