import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JACK28 {

    public static void main(String[] args) {
        int NUMBER_OF_PLAYERS = 2;
        int INITIAL_HAND_SIZE = 7;
        Rules rules = new Rules();
        Deck deck = new Deck();
        Card topCard = deck.draw();
        Pile pile = new Pile(topCard);
        List<DummyPlayer> players = Arrays.stream(new Integer[NUMBER_OF_PLAYERS])
                .map(a -> {
                    List<Card> hand = Arrays.stream(new Integer[INITIAL_HAND_SIZE])
                            .map(b -> deck.draw())
                            .collect(Collectors.toList());
                    return new DummyPlayer(hand);
                })
                .collect(Collectors.toList());
        State initialState = new State(rules, deck, pile, players);
        State finalState = playGame(initialState);
        System.out.println("JACK28 Complete");
    }

    private static State playGame(State state) {
        while (state.players.stream().noneMatch(DummyPlayer::handEmpty)) {
            state.takeTurn();
        }
        return state;
    }

}
