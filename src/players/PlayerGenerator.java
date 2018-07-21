package players;

import gamemodel.Card;

import java.util.List;

public class PlayerGenerator {

    public Player fromEnum(int playerIndex, List<Card> initialHand, PlayerTypes playerType) {
        Player player = null;
        switch (playerType) {
            case DUMMY_PLAYER:
                player = new DummyPlayer(playerIndex, initialHand);
                break;
            default:
                break;
        }
        return player;
    }
}
