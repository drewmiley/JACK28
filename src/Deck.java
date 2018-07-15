import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Deck {

    private List<Card> cards;
    private boolean orderSeen;

    Deck() {
        List<Card> unshuffledCards = Stream.of(FaceValue.values())
                .flatMap(faceValue -> Stream.of(Suit.values()).map(suit -> new Card(faceValue, suit)))
                .collect(Collectors.toList());
        this.cards = shuffle(unshuffledCards);
        this.orderSeen = false;
    }

    Deck(List<Card> cards) {
        this.cards = cards;
        this.orderSeen = true;
    }

    private List<Card> shuffle(List<Card> unshuffledCards) {
        // TODO: Implement shuffle
        return unshuffledCards;
    }

    List<Card> orderSeenCards() {
        return this.orderSeen ? this.cards : new ArrayList<>();
    }

    Card draw() {
        Card card = this.cards.get(this.cards.size() - 1);
        this.cards.remove(this.cards.size() - 1);
        return card;
    }
}
