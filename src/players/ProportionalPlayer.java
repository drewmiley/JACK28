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
        // TODO: Excluding JACK and 2, play the highest amount of cards that give highest proportion of a suit to play
        List<List<Card>> possibleCardsToPlay = this.possibleCardsToPlay(rules, pile);
        List<Card> unplayedCardsRemainingInGame = unplayedCardsRemainingInGame(pile, hand);
        Long maxCardsToPlaySize = possibleCardsToPlay.stream()
                .mapToLong(List::size)
                .max()
                .getAsLong();
        return possibleCardsToPlay.stream()
                .max(Comparator.comparingInt(List::size))
                .get();
    }

    @Override
    public Suit nomination(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        List<Card> unplayedCardsRemainingInGame = unplayedCardsRemainingInGame(pile, hand);
        Map<Suit, Long> suitProportions = suitProportionsAfterCardsPlayed(
                unplayedCardsRemainingInGame,
                hand,
                new ArrayList<>(),
                Collections.singletonList(FaceValue.JACK)
        );
        return suitProportions.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
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
        // TODO: This should take in suit
        List<Card> unPlayedCardsIgnoringFaceValues = unplayedCardsRemainingInGame.stream()
                .filter(card -> !faceValuesToIgnore.contains(card.getFaceValue()))
                .collect(Collectors.toList());
        return Arrays.stream(Suit.values())
                .collect(Collectors.toMap(s -> s,
                        s -> {
                            Long suitLeftInHand = hand.stream()
                                    .filter(card -> !faceValuesToIgnore.contains(card.getFaceValue()) && !cardsToPlay.contains(card))
                                    .filter(card -> card.getSuit() == s)
                                    .count();
                            Long suitLeftInGame = unPlayedCardsIgnoringFaceValues.stream()
                                    .filter(card -> card.getSuit() == s)
                                    .count();
                            return suitLeftInHand / suitLeftInGame;
                        })
                );
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.PROPORTIONAL_PLAYER;
    }

}
