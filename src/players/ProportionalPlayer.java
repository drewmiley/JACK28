package players;

import gamemodel.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private List<Card> unplayedCardsRemainingInGame(Pile pile, List<Card> hand) {
        List<Card> cards = Stream.of(FaceValue.values())
                .flatMap(faceValue -> Stream.of(Suit.values()).map(suit -> new Card(faceValue, suit)))
                .collect(Collectors.toList());
        cards.remove(pile.topCard());
        cards.removeAll(pile.getCardsBelowTopCard());
        cards.removeAll(hand);
        return cards;
    }

    private Map<Suit, Long> suitProportionsAfterCardsPlayed(List<Card> unplayedCardsRemainingInGame,
                                                            List<Card> hand,
                                                            List<Card> cardsToPlay,
                                                            List<FaceValue> faceValuesToIgnore) {
        // TODO: Actually implement this correctly
        Map<Suit, Long> suitTotals = Arrays.stream(Suit.values())
                .collect(Collectors.toMap(s -> s,
                        s -> hand.stream()
                                .filter(card -> card.getSuit() == s)
                                .count()));
        return suitTotals;
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.PROPORTIONAL_PLAYER;
    }

}
