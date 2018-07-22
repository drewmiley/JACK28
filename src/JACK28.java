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
        PlayerTypes[] PLAYER_TYPES = new PlayerTypes[]{
                PlayerTypes.DUMMY_PLAYER,
                PlayerTypes.SIMPLE_PLAYER,
                PlayerTypes.DUMMY_PLAYER,
                PlayerTypes.SIMPLE_PLAYER
        };
        int INITIAL_HAND_SIZE = 7;
        int NUMBER_OF_GAMES = 1000;
        IntStream.range(0, NUMBER_OF_GAMES)
                .forEach(i -> {
                    State initialState = initialState(PLAYER_TYPES, INITIAL_HAND_SIZE);
                    State finalState = playGame(initialState);
                    System.out.println(finalState.winner().getClass().getSimpleName());
                });
    }

    private static State initialState(PlayerTypes[] playerTypes, int initialHandSize) {
        Rules rules = new Rules();
        Deck deck = new Deck();
        Card topCard = deck.draw();
        Pile pile = new Pile(topCard);
        PlayerGenerator playerGenerator = new PlayerGenerator();
        List<Player> players = IntStream.range(0, playerTypes.length)
                .mapToObj(playerIndex -> {
                    List<Card> hand = Arrays.stream(new Integer[initialHandSize])
                            .map(a -> deck.draw())
                            .collect(Collectors.toList());
                    return playerGenerator.fromEnum(playerIndex, hand, playerTypes[playerIndex]);
                })
                .collect(Collectors.toList());
        return new State(rules, deck, pile, players);
    }

    private static State playGame(State state) {
        while (state.winner() == null) {
            state.takeTurn();
        }
        return state;
    }

}
