import java.util.List;

abstract class Player {
    List<Card> hand;

    Player(List<Card> hand) {
        this.hand = hand;
    }

    abstract List<Card> cardsToPlay(Rules rules, Deck deck, Pile pile, List<Player> nonTurnPlayers);

    void addCardToHand(Card card) {
        this.hand.add(card);
    }

    boolean handEmpty() {
        return this.hand.isEmpty();
    }
}
