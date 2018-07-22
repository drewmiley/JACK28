package gamemodel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Deck {

    private List<Card> cards;
    private boolean orderSeen;

    public Deck() {
        List<Card> cards = Stream.of(FaceValue.values())
                .flatMap(faceValue -> Stream.of(Suit.values()).map(suit -> new Card(faceValue, suit)))
                .collect(Collectors.toList());
        Collections.shuffle(cards);
        this.cards = cards;
        this.orderSeen = false;
    }

    public Deck(List<Card> cards) {
        this.cards = cards;
        this.orderSeen = true;
    }

    public boolean orderSeen() {
        return this.orderSeen;
    }

    public Card draw() {
        Card card = this.cards.get(this.cards.size() - 1);
        this.cards.remove(card);
        return card;
    }

    public boolean empty() {
        return cards.isEmpty();
    }
}
