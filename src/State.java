import gamemodel.*;
import players.Player;

import java.util.Collections;
import java.util.List;

class State {

    private Rules rules;
    private Deck deck;
    private Pile pile;
    private List<Player> players;
    private int playerIndexTurn = 0;
    private boolean clockwise = true;

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
        List<Card> cardsToPlay = player.cardsToPlay(rules, deck, pile);
        boolean validPlay = rules.isAllowedPlay(pile.topCard(), cardsToPlay);
        if (validPlay) {
            cardsToPlay.forEach(player::removeCardFromHand);
            pile.play(cardsToPlay);
            boolean missAGo = rules.isMissAGo(cardsToPlay);
            if (missAGo) {
                playerIndexTurn++;
            }
            boolean nomination = rules.isNomination(cardsToPlay);
            if (nomination) {
                Suit nominatedSuit = player.nomination(rules, deck, pile);
                pile.nominate(nominatedSuit);
            }
            boolean switchDirection = rules.isSwitchDirection(cardsToPlay);
            if (switchDirection) {
                clockwise = !clockwise;
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
        if (clockwise) {
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
