package gamemodel;

import java.util.List;

public class Rules {

    public final FaceValue NOMINATE_SUIT = FaceValue.JACK;
    public final boolean NOMINATE_SUIT_MUST_FOLLOW = false;

    public final FaceValue DRAW_CARD = FaceValue.TWO;
    public final int DRAW_CARD_NUMBER = 2;

    private final FaceValue MISS_A_GO = FaceValue.EIGHT;

    public final FaceValue SWITCH_DIRECTION = FaceValue.SEVEN;
    public final boolean CAN_SWITCH_DIRECTION = false;

    public final boolean RUN_UP_IN_SUIT = false;
    public final boolean RUN_DOWN_IN_SUIT = false;
    public final boolean RUN_FACEVALUE = true;

    public boolean isAllowedPlay(Card topCard, List<Card> cardsToPlay) {
        return cardsToPlay.size() == 1 &&
                (cardsToPlay.get(0).getFaceValue() == topCard.getFaceValue() ||
                cardsToPlay.get(0).getSuit() == topCard.getSuit());
    }

    public boolean isMissAGo(List<Card> cardsToPlay) {
        return cardsToPlay.get(cardsToPlay.size() - 1).getFaceValue() == MISS_A_GO;
    }
}
