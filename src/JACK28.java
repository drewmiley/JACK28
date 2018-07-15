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
        State initialState = new State(deck, pile, players);
        State finalState = playGame(initialState);
        System.out.println("JACK28 Complete");
    }

    private static State playGame(State state) {
        // TODO: Implement correct game end condition- one player has zero cards
        for (int i = 0; i < 50; i++) {
            state.takeTurn();
        }
        return state;
    }

}
