package players;

import gamemodel.*;

import java.util.*;
import java.util.stream.Collectors;

class ProportionalPlayer extends Player {

    ProportionalPlayer(int playerIndex, List<Card> hand) {
        super(playerIndex, hand);
    }

    @Override
    public List<Card> cardsToPlay(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        // TODO: Excluding JACK and 2 if possible, play the highest amount of cards that give highest proportion of a suit to play
        return this.possibleCardsToPlay(rules, pile).stream()
                .max(Comparator.comparingInt(List::size))
                .get();
    }

    @Override
    public Suit nomination(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        // TODO: Excluding JACK, nominate the suit that will leave you the greatest proportion of a suit that is left to play
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
        return PlayerType.PROPORTIONAL_PLAYER;
    }

}
