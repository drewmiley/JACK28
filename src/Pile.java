import java.util.ArrayList;
import java.util.List;

class Pile {

    private List<Card> cards = new ArrayList<>();
    private Rules rules;

    List<Card> getCards() {
        return this.cards;
    }

    Pile(Card card, Rules rules) {
        this.cards.add(card);
        this.rules = rules;
    }

    Card topCard() {
        return this.cards.get(this.cards.size() - 1);
    }

    boolean play(List<Card> cards) {
        boolean allowedPlay = this.rules.isAllowedPlay(this.topCard(), cards);
        if (allowedPlay) {
            this.cards.addAll(cards);
        }
        return allowedPlay;
    }
}
