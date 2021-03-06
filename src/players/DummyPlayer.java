package players;

import gamemodel.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class DummyPlayer extends Player {

    DummyPlayer(int playerIndex, List<Card> hand) {
        super(playerIndex, hand);
    }

    @Override
    public List<Card> cardsToPlay(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        List<List<Card>> possibleCardsToPlay = this.possibleCardsToPlay(rules, pile);
        return possibleCardsToPlay.get((int) Math.floor(possibleCardsToPlay.size() * Math.random()));
    }

    @Override
    public Suit nomination(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        return Suit.values()[(int) Math.floor(Suit.values().length * Math.random())];
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.DUMMY_PLAYER;
    }

}
