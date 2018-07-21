package players;

import gamemodel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Player {
    int playerIndex;
    List<Card> hand;
    List<Card> knownHand = new ArrayList<>();

    Player(int playerIndex, List<Card> hand) {
        this.playerIndex = playerIndex;
        this.hand = hand;
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
