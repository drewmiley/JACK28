import java.util.ArrayList;
import java.util.List;

class Player {

    private List<Card> hand;
    private Rules rules;

    Player(List<Card> hand, Rules rules) {
        this.hand = hand;
        this.rules = rules;
    }

    List<Card> cardsToPlay() {
        List<Card> cardsToPlay = new ArrayList<>();
        Card card = this.hand.get(this.hand.size() - 1);
        cardsToPlay.add(card);
        this.hand.remove(card);
        return cardsToPlay;
    }

    void addCardsToHand(List<Card> cards) {
        this.hand.addAll(cards);
    }
}
