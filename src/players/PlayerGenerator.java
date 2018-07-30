package players;

import gamemodel.Card;

import java.util.List;

public class PlayerGenerator {

    public Player fromEnum(int playerIndex, List<Card> initialHand, PlayerType playerType) {
        Player player = null;
        switch (playerType) {
            case DUMMY_PLAYER:
                player = new DummyPlayer(playerIndex, initialHand);
                break;
            case SIMPLE_PLAYER:
                player = new SimplePlayer(playerIndex, initialHand);
                break;
            case PROPORTIONAL_PLAYER:
                player = new ProportionalPlayer(playerIndex, initialHand);
                break;
            case HUMAN_PLAYER:
                player = new HumanPlayer(playerIndex, initialHand);
                break;
            default:
                break;
        }
        return player;
    }
}
