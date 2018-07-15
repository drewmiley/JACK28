import java.util.ArrayList;
import java.util.List;

class Player {

    private List<Card> hand;
    private Rules rules;
    private Card topCard;

    void setTopCard(Card topCard) {
        this.topCard = topCard;
    }

    Player(List<Card> hand, Rules rules, Card topCard) {
        this.hand = hand;
        this.rules = rules;
        this.topCard = topCard;
    }

    List<Card> cardsToPlay() {
        List<Card> cardsToPlay = new ArrayList<>();
        Card card = this.hand.get(this.hand.size() - 1);
        cardsToPlay.add(card);
        this.hand.remove(card);
        return cardsToPlay;
    }

    void addCardToHand(Card card) {
        this.hand.add(card);
    }
}
