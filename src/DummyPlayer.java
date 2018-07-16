import java.util.ArrayList;
import java.util.List;

class DummyPlayer extends Player {

    DummyPlayer(List<Card> hand) {
        super(hand);
    }

    @Override
    List<Card> cardsToPlay(Rules rules, Deck deck, Pile pile, List<Player> nonTurnPlayers) {
        List<Card> cardsToPlay = new ArrayList<>();
        Card card = this.hand.get(this.hand.size() - 1);
        cardsToPlay.add(card);
        this.hand.remove(card);
        return cardsToPlay;
    }

}
