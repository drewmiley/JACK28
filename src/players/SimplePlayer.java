package players;

import gamemodel.*;

import java.util.*;
import java.util.stream.Collectors;

class SimplePlayer extends Player {

    SimplePlayer(int playerIndex, List<Card> hand) {
        super(playerIndex, hand);
    }

    @Override
    public List<Card> cardsToPlay(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        return this.possibleCardsToPlay(rules, pile).stream()
                .max(Comparator.comparingInt(List::size))
                .get();
    }

    @Override
    public Suit nomination(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        Map<Suit, Long> suitTotals = Arrays.stream(Suit.values())
                .collect(Collectors.toMap(s -> s,
                        s -> hand.stream()
                                .filter(card -> card.getSuit() == s)
                                .count()));
        return suitTotals.entrySet().stream()
                .max(Comparator.comparingInt(a -> a.getValue().intValue()))
                .get()
                .getKey();
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.SIMPLE_PLAYER;
    }

}
