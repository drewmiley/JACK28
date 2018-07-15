import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JACK28 {

    public static void main(String[] args) {
        int NUMBER_OF_PLAYERS = 2;
        int INITIAL_HAND_SIZE = 7;
        Deck deck = new Deck();
        Rules rules = new Rules();
        Card topCard = deck.draw();
        Pile pile = new Pile(topCard, rules);
        List<Player> players = Arrays.stream(new Integer[NUMBER_OF_PLAYERS])
                .map(a -> {
                    List<Card> hand = Arrays.stream(new Integer[INITIAL_HAND_SIZE])
                            .map(b -> deck.draw())
                            .collect(Collectors.toList());
                    return new Player(hand, rules, topCard);
                })
                .collect(Collectors.toList());
        playGame(deck, pile, players);
    }

    private static void playGame(Deck deck, Pile pile, List<Player> players) {
        System.out.println("Hello, World");
    }

}
