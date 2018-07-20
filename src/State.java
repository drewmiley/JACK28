import gamemodel.Card;
import gamemodel.Deck;
import gamemodel.Pile;
import gamemodel.Rules;
import players.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class State {

    private Rules rules;
    private Deck deck;
    private Pile pile;
    private List<Player> players;
    private int playerIndexTurn = 0;

    State(Rules rules, Deck deck, Pile pile, List<Player> players) {
        this.rules = rules;
        this.deck = deck;
        this.pile = pile;
        this.players = players;
    }

    boolean gameComplete() {
        return this.players.stream().anyMatch(Player::handEmpty);
    }

    void takeTurn() {
        Player player = players.get(playerIndexTurn);
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
            boolean missAGo = rules.isMissAGo(cardsToPlay);
            if (missAGo) {
                playerIndexTurn++;
            }
        }
        playerIndexTurn++;
        if (playerIndexTurn >= players.size()) {
            playerIndexTurn -= players.size();
        }
    }
}
