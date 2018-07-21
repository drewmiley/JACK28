package players;

import gamemodel.Card;

import java.util.List;

public class VisiblePlayer {

    private int handSize;
    private List<Card> knownHand;

    public VisiblePlayer(Player player) {
        this.handSize = player.hand.size();
        this.knownHand = player.knownHand;
    }

    public int getHandSize() {
        return handSize;
    }

    public List<Card> getKnownHand() {
        return knownHand;
    }
}
