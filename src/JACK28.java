import gamemodel.Card;
import gamemodel.Deck;
import gamemodel.Pile;
import gamemodel.Rules;
import players.Player;
import players.PlayerGenerator;
import players.PlayerTypes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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
        List<String> winners = Arrays.stream(new Integer[NUMBER_OF_GAMES])
                .map(i -> {
                    State initialState = initialState(PLAYER_TYPES, INITIAL_HAND_SIZE);
                    State finalState = playGame(initialState);
                    return finalState.winner().getClass().getSimpleName();
                })
                .collect(Collectors.toList());
        System.out.println("Simple Player Wins: " + winners.stream().filter(winner -> winner.equals("SimplePlayer")).count());
        System.out.println("Dummy Player Wins: " + winners.stream().filter(winner -> winner.equals("DummyPlayer")).count());
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
