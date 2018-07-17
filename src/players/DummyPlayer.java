package players;

import gamemodel.Card;
import gamemodel.Deck;
import gamemodel.Pile;
import gamemodel.Rules;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DummyPlayer extends Player {

    public DummyPlayer(List<Card> hand) {
        super(hand);
    }

    @Override
    public List<Card> cardsToPlay(Rules rules, Deck deck, Pile pile, List<Player> nonTurnPlayers) {
        List<Card> cardOptions = this.hand.stream()
                .filter(d -> rules.isAllowedPlay(pile.topCard(), Stream.of(d).collect(Collectors.toList())))
                .collect(Collectors.toList());
        List<Card> cardsToPlay = cardOptions.isEmpty() ? cardOptions : cardOptions.subList(0, 1);
        this.hand.removeAll(cardsToPlay);
        return cardsToPlay;
    }

}
