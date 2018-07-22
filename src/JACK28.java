import gamemodel.Card;
import gamemodel.Deck;
import gamemodel.Pile;
import gamemodel.Rules;
import players.Player;
import players.PlayerGenerator;
import players.PlayerTypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JACK28 {

    public static void main(String[] args) {
        int NUMBER_OF_PLAYERS = 2;
        int INITIAL_HAND_SIZE = 7;
        int NUMBER_OF_GAMES = 1000;
        IntStream.range(0, NUMBER_OF_GAMES)
                .forEach(i -> {
                    State initialState = initialState(NUMBER_OF_PLAYERS, INITIAL_HAND_SIZE);
                    State finalState = playGame(initialState);
                    System.out.println("JACK28 Complete");
                });
    }

    private static State initialState(int numberOfPlayers, int initialHandSize) {
        Rules rules = new Rules();
        Deck deck = new Deck();
        Card topCard = deck.draw();
        Pile pile = new Pile(topCard);
        PlayerGenerator playerGenerator = new PlayerGenerator();
        List<Player> players = IntStream.range(0, numberOfPlayers)
                .mapToObj(playerIndex -> {
                    List<Card> hand = Arrays.stream(new Integer[initialHandSize])
                            .map(a -> deck.draw())
                            .collect(Collectors.toList());
                    return playerGenerator.fromEnum(playerIndex, hand, PlayerTypes.DUMMY_PLAYER);
                })
                .collect(Collectors.toList());
        return new State(rules, deck, pile, players);
    }

    private static State playGame(State state) {
        while (!state.gameComplete()) {
            state.takeTurn();
        }
        return state;
    }

}
