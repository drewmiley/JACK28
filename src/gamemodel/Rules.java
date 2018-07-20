package gamemodel;

import java.util.List;
import java.util.stream.IntStream;

public class Rules {

    public final FaceValue NOMINATE_SUIT = FaceValue.JACK;
    public final boolean NOMINATE_SUIT_MUST_FOLLOW = false;

    public final FaceValue DRAW_CARD = FaceValue.TWO;
    public final int DRAW_CARD_NUMBER = 2;

    private final FaceValue MISS_A_GO = FaceValue.EIGHT;

    public final FaceValue SWITCH_DIRECTION = FaceValue.SEVEN;
    public final boolean CAN_SWITCH_DIRECTION = false;

    public final boolean RUN_FACEVALUE = true;

    public boolean isAllowedPlay(Card topCard, List<Card> cardsToPlay) {
        if (cardsToPlay.size() == 0) {
            return false;
        }
        boolean firstCardValid = (cardsToPlay.get(0).getFaceValue() == topCard.getFaceValue() ||
                cardsToPlay.get(0).getSuit() == topCard.getSuit()) ||
                !NOMINATE_SUIT_MUST_FOLLOW && cardsToPlay.get(0).getFaceValue() == NOMINATE_SUIT;
        boolean validRun = IntStream.range(0, cardsToPlay.size() - 1)
                .mapToObj(i -> cardsToPlay.subList(i, i + 2))
                .map(cardPair -> RUN_FACEVALUE && cardPair.get(1).getFaceValue() == cardPair.get(0).getFaceValue())
                .reduce((a, b) -> a && b)
                .orElse(true);
        return firstCardValid && validRun;
    }

    public boolean isMissAGo(List<Card> cardsToPlay) {
        return cardsToPlay.get(cardsToPlay.size() - 1).getFaceValue() == MISS_A_GO;
    }

    public boolean isNomination(List<Card> cardsToPlay) {
        return cardsToPlay.get(cardsToPlay.size() - 1).getFaceValue() == NOMINATE_SUIT;
    }

    public boolean isSwitchDirection(List<Card> cardsToPlay) {
        return CAN_SWITCH_DIRECTION && cardsToPlay.get(cardsToPlay.size() - 1).getFaceValue() == SWITCH_DIRECTION;
    }
}
