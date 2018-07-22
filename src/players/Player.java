package players;

import gamemodel.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        List<List<Card>> possibleCardsToPlay = new ArrayList<>();
        final List<List<Card>>[] result = new List[]{new ArrayList<>()};
        result[0] = hand.stream()
                .map(card -> Stream.of(card).collect(Collectors.toList()))
                .filter(card -> rules.isAllowedPlay(pile, card))
                .collect(Collectors.toList());
        possibleCardsToPlay.addAll(result[0]);
        IntStream.range(0, hand.size() - 1)
                .mapToObj(i -> hand)
                .flatMap(Collection::stream)
                .forEach(handCard -> {
                    result[0] = result[0].stream()
                            .filter(cards -> {
                                List<Card> cardPair = Stream.of(cards.get(cards.size() - 1), handCard)
                                        .collect(Collectors.toList());
                                return !cards.contains(handCard) &&
                                        (rules.runFaceValue(cardPair) || rules.runUpInSuit(cardPair) || rules.runDownInSuit(cardPair));
                            })
                            .map(cards -> Stream.concat(
                                            cards.stream(),
                                            Stream.of(handCard).collect(Collectors.toList()).stream()
                                    ).collect(Collectors.toList()))
                            .collect(Collectors.toList());
                    possibleCardsToPlay.addAll(result[0]);
                });
        possibleCardsToPlay.add(new ArrayList<>());
        return possibleCardsToPlay;
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
