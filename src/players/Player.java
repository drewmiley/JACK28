package players;

import gamemodel.Card;
import gamemodel.Deck;
import gamemodel.Pile;
import gamemodel.Rules;

import java.util.List;

public abstract class Player {
    List<Card> hand;

    Player(List<Card> hand) {
        this.hand = hand;
    }

    public abstract List<Card> cardsToPlay(Rules rules, Deck deck, Pile pile, List<Player> nonTurnPlayers);

    public void addCardToHand(Card card) {
        this.hand.add(card);
    }

    public boolean handEmpty() {
        return this.hand.isEmpty();
    }
}
