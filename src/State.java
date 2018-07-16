import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class State {

    Rules rules;
    Deck deck;
    Pile pile;
    List<DummyPlayer> players;
    int playerIndexTurn = 0;

    State(Rules rules, Deck deck, Pile pile, List<DummyPlayer> players) {
        this.rules = rules;
        this.deck = deck;
        this.pile = pile;
        this.players = players;
    }

    void takeTurn() {
        DummyPlayer player = players.get(playerIndexTurn);
        List<Player> nonTurnPlayers = players.stream()
                .filter(d -> d != player)
                .collect(Collectors.toList());
        List<Card> cardsToPlay = player.cardsToPlay(rules, deck, pile, nonTurnPlayers);
        boolean validPlay = pile.play(rules, cardsToPlay);
        if (!validPlay) {
            cardsToPlay.forEach(player::addCardToHand);
            player.addCardToHand(deck.draw());
            if (deck.empty()) {
                List<Card> newDeck = pile.getCardsBelowTopCard();
                Collections.reverse(newDeck);
                deck = new Deck(newDeck);
                Card pileTopCard = pile.topCard();
                pile = new Pile(pileTopCard);
            }
        }
        playerIndexTurn++;
        if (playerIndexTurn == players.size()) {
            playerIndexTurn = 0;
        }
    }
}
