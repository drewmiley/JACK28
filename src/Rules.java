import java.util.List;

class Rules {

    Rules() {

    }

    boolean isAllowedPlay(Card topCard, List<Card> cardsToPlay) {
        return cardsToPlay.size() == 1 &&
                (cardsToPlay.get(0).getFaceValue() == topCard.getFaceValue() ||
                cardsToPlay.get(0).getSuit() == topCard.getSuit());
    }
}
