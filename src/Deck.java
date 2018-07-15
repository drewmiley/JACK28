import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Deck {

    private List<Card> cards;
    private boolean orderSeen;

    Deck() {
        List<Card> cards = Stream.of(FaceValue.values())
                .flatMap(faceValue -> Stream.of(Suit.values()).map(suit -> new Card(faceValue, suit)))
                .collect(Collectors.toList());
        Collections.shuffle(cards);
        this.cards = cards;
        this.orderSeen = false;
    }

    Deck(List<Card> cards) {
        this.cards = cards;
        this.orderSeen = true;
    }

    List<Card> orderSeenCards() {
        return this.orderSeen ? this.cards : new ArrayList<>();
    }

    Card draw() {
        Card card = this.cards.get(this.cards.size() - 1);
        this.cards.remove(card);
        return card;
    }
}
