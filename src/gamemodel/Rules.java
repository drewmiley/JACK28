package gamemodel;

import java.util.List;
import java.util.stream.IntStream;

public class Rules {

    public final FaceValue NOMINATE_SUIT = FaceValue.JACK;
    public final boolean NOMINATE_SUIT_MUST_FOLLOW = false;

    public final FaceValue DRAW_CARD = FaceValue.TWO;
    public final int DRAW_CARD_NUMBER = 2;

    public final FaceValue MISS_A_GO = FaceValue.EIGHT;

    public final FaceValue SWITCH_DIRECTION = FaceValue.SEVEN;
    public final boolean CAN_SWITCH_DIRECTION = false;

    public final boolean RUN_FACEVALUE = true;
    public final boolean RUN_UP_IN_SUIT = false;
    public final boolean RUN_DOWN_IN_SUIT = false;

    private boolean firstCardValid(Pile pile, List<Card> cardsToPlay) {
        return !cardsToPlay.isEmpty() &&
                ((pile.getDrawCardActiveRun() == 0 || cardsToPlay.get(0).getFaceValue() == DRAW_CARD) &&
                (cardsToPlay.get(0).getFaceValue() == pile.topCard().getFaceValue() ||
                        cardsToPlay.get(0).getSuit() == pile.topCard().getSuit()) ||
                !NOMINATE_SUIT_MUST_FOLLOW && cardsToPlay.get(0).getFaceValue() == NOMINATE_SUIT);
    }

    private boolean runFaceValue(List<Card> cardPair) {
        return RUN_FACEVALUE &&
                cardPair.get(1).getFaceValue() == cardPair.get(0).getFaceValue();
    }

    private boolean runUpInSuit(List<Card> cardPair) {
        return RUN_UP_IN_SUIT &&
                cardPair.get(1).getSuit() == cardPair.get(0).getSuit() &&
                (cardPair.get(1).getFaceValue().ordinal() % FaceValue.values().length ==
                        (cardPair.get(0).getFaceValue().ordinal() + 1) % FaceValue.values().length);
    }

    private boolean runDownInSuit(List<Card> cardPair) {
        return RUN_DOWN_IN_SUIT &&
                cardPair.get(1).getSuit() == cardPair.get(0).getSuit() &&
                (cardPair.get(1).getFaceValue().ordinal() % FaceValue.values().length ==
                        (cardPair.get(0).getFaceValue().ordinal() - 1) % FaceValue.values().length);
    }

    public boolean isAllowedPlay(Pile pile, List<Card> cardsToPlay) {
        return IntStream.range(0, cardsToPlay.size() - 1)
                .mapToObj(i -> cardsToPlay.subList(i, i + 2))
                .map(cardPair ->
                        runFaceValue(cardPair) ||
                        runUpInSuit(cardPair) ||
                        runDownInSuit(cardPair)
                )
                .reduce(firstCardValid(pile, cardsToPlay), (a, b) -> a && b);
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

    public boolean isDrawCard(List<Card> cardsToPlay) {
        return cardsToPlay.get(cardsToPlay.size() - 1).getFaceValue() == DRAW_CARD;
    }
}
