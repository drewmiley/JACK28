import gamemodel.Card;
import gamemodel.Deck;
import gamemodel.Pile;
import gamemodel.Rules;
import players.Player;
import players.PlayerGenerator;
import players.PlayerType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JACK28 {

    public static void main(String[] args) {
        PlayerType[] PLAYER_TYPES = new PlayerType[]{
                PlayerType.DUMMY_PLAYER,
                PlayerType.SIMPLE_PLAYER,
                PlayerType.DUMMY_PLAYER,
                PlayerType.SIMPLE_PLAYER
        };
        int INITIAL_HAND_SIZE = 7;
        int NUMBER_OF_GAMES = 1000;
        List<PlayerType> winners = Arrays.stream(new Integer[NUMBER_OF_GAMES])
                .map(i -> {
                    State initialState = initialState(PLAYER_TYPES, INITIAL_HAND_SIZE);
                    State finalState = playGame(initialState);
                    return finalState.winner().getPlayerType();
                })
                .collect(Collectors.toList());
        Map<PlayerType, Long> winnersMap = Arrays.stream(PlayerType.values())
                .collect(Collectors.toMap(pt -> pt,
                        pt -> winners.stream()
                                .filter(winner -> winner == pt)
                                .count()
                ));
        System.out.println("Simple Player Wins: " + winnersMap.get(PlayerType.SIMPLE_PLAYER));
        System.out.println("Dummy Player Wins: " + winnersMap.get(PlayerType.DUMMY_PLAYER));
    }

    private static State initialState(PlayerType[] playerTypes, int initialHandSize) {
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
