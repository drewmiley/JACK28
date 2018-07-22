package players;

import gamemodel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Player {
    int playerIndex;
    List<Card> hand;
    List<Card> knownHand = new ArrayList<>();

    Player(int playerIndex, List<Card> hand) {
        this.playerIndex = playerIndex;
        this.hand = hand;
    }

    List<List<Card>> possibleCardsToPlay(Rules rules, Pile pile) {
        List<List<Card>> result = new ArrayList<>();
        List<List<Card>> firstCardsValid = hand.stream()
                .map(card -> Stream.of(card).collect(Collectors.toList()))
                .filter(card -> rules.isAllowedPlay(pile, card))
                .collect(Collectors.toList());
        result.addAll(firstCardsValid);
        List<List<Card>> twos = hand.stream()
                .flatMap(handCard -> firstCardsValid.stream()
                        .filter(cards -> {
                            List<Card> cardPair = Stream.of(cards.get(cards.size() - 1), handCard)
                                    .collect(Collectors.toList());
                            return !cards.contains(handCard) &&
                                    (rules.runFaceValue(cardPair) ||
                                    rules.runUpInSuit(cardPair) ||
                                    rules.runDownInSuit(cardPair));
                        })
                        .map(cards -> {
                            List<Card> blah = new ArrayList<>(cards);
                            blah.add(handCard);
                            return blah;
                        })
                )
                .collect(Collectors.toList());
        result.addAll(twos);
        List<List<Card>> threes = hand.stream()
                .flatMap(handCard -> twos.stream()
                        .filter(cards -> {
                            List<Card> cardPair = Stream.of(cards.get(cards.size() - 1), handCard)
                                    .collect(Collectors.toList());
                            return !cards.contains(handCard) &&
                                    (rules.runFaceValue(cardPair) ||
                                            rules.runUpInSuit(cardPair) ||
                                            rules.runDownInSuit(cardPair));
                        })
                        .map(cards -> {
                            List<Card> blah = new ArrayList<>(cards);
                            blah.add(handCard);
                            return blah;
                        })
                )
                .collect(Collectors.toList());
        result.addAll(threes);
        return result;
    }

    public abstract List<Card> cardsToPlay(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers);

    public abstract Suit nomination(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers);

    public void addCardToHand(Card card, boolean deckOrderSeen) {
        this.hand.add(card);
        if (deckOrderSeen) this.knownHand.add(card);
    }

    public void removeCardFromHand(Card card) {
        this.hand.remove(card);
        this.knownHand.remove(card);
    }

    public boolean handEmpty() {
        return this.hand.isEmpty();
    }
}
