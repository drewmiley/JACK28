import gamemodel.*;
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
    private boolean playerTurnDirection = true;

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
        if (validPlay) {
            boolean missAGo = rules.isMissAGo(cardsToPlay);
            if (missAGo) {
                playerIndexTurn++;
            }
            boolean nomination = rules.isNomination(cardsToPlay);
            if (nomination) {
                Suit nominatedSuit = player.nomination(rules, deck, pile, nonTurnPlayers);
                pile.nominate(nominatedSuit);
            }
            boolean switchDirection = rules.isSwitchDirection(cardsToPlay);
            if (switchDirection) {
                playerTurnDirection = !playerTurnDirection;
            }
        } else {
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
        if (playerTurnDirection) {
            playerIndexTurn++;
        } else {
            playerIndexTurn--;
        }
        if (playerIndexTurn >= players.size()) {
            playerIndexTurn -= players.size();
        }
        if (playerIndexTurn < 0) {
            playerIndexTurn += players.size();
        }
    }
}
