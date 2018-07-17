package players;

import gamemodel.Card;

import java.util.List;

public class PlayerGenerator {

    public Player fromEnum(List<Card> initialHand, PlayerTypes playerType) {
        Player player = null;
        switch (playerType) {
            case DUMMY_PLAYER:
                player = new DummyPlayer(initialHand);
                break;
            default:
                break;
        }
        return player;
    }
}
