package players;

import gamemodel.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SimplePlayer extends Player {

    SimplePlayer(int playerIndex, List<Card> hand) {
        super(playerIndex, hand);
    }

    @Override
    public List<Card> cardsToPlay(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        // TODO: Play max number of cards in a go
        List<List<Card>> possibleCardsToPlay = this.possibleCardsToPlay(rules, pile);
        return possibleCardsToPlay.get((int) Math.floor(possibleCardsToPlay.size() * Math.random()));
    }

    @Override
    public Suit nomination(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        // TODO: Nominate suit with most cards in
        return Suit.values()[(int) Math.floor(Suit.values().length * Math.random())];
    }

}
