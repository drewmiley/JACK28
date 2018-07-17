import java.util.ArrayList;
import java.util.List;

class DummyPlayer extends Player {

    DummyPlayer(List<Card> hand) {
        super(hand);
    }

    @Override
    List<Card> cardsToPlay(Rules rules, Deck deck, Pile pile, List<Player> nonTurnPlayers) {
        List<Card> cardsToPlay = new ArrayList<>();
        Card card = this.hand.stream()
                .filter(d ->
                        d.getFaceValue() == pile.topCard().getFaceValue() ||
                                d.getSuit() == pile.topCard().getSuit()
                ).findFirst()
                .orElse(null);
        if (card != null) {
            cardsToPlay.add(card);
            this.hand.remove(card);
        }
        return cardsToPlay;
    }

}
