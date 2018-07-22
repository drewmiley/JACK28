package players;

import gamemodel.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class DummyPlayer extends Player {

    DummyPlayer(int playerIndex, List<Card> hand) {
        super(playerIndex, hand);
    }

    @Override
    public List<Card> cardsToPlay(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        List<List<Card>> possibleCardsToPlay = this.possibleCardsToPlay(rules, pile);
        List<Card> cardOptions = this.hand.stream()
                .filter(d -> rules.isAllowedPlay(pile, Stream.of(d).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return cardOptions.isEmpty() ? cardOptions : cardOptions.subList(0, 1);
    }

    @Override
    public Suit nomination(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        return Suit.values()[(int) Math.floor(Suit.values().length * Math.random())];
    }

}
