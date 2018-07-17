package gamemodel;

import java.util.List;

public class Rules {

    public Rules() {

    }

    public boolean isAllowedPlay(Card topCard, List<Card> cardsToPlay) {
        return cardsToPlay.size() == 1 &&
                (cardsToPlay.get(0).getFaceValue() == topCard.getFaceValue() ||
                cardsToPlay.get(0).getSuit() == topCard.getSuit());
    }
}
