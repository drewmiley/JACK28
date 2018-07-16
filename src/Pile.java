import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Pile {

    private List<Card> cards;

    List<Card> getCardsBelowTopCard() {
        return this.cards.subList(0, this.cards.size() - 2);
    }

    Pile(Card card) {
        this.cards = Stream.of(card).collect(Collectors.toList());
    }

    Card topCard() {
        return this.cards.get(this.cards.size() - 1);
    }

    boolean play(Rules rules, List<Card> cards) {
        boolean allowedPlay = rules.isAllowedPlay(this.topCard(), cards);
        if (allowedPlay) {
            this.cards.addAll(cards);
        }
        return allowedPlay;
    }
}
