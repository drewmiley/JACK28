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
        if (rules.isAllowedPlay(pile.topCard(), cardsToPlay)) {
            playCards(player, pile, cardsToPlay);
            if (rules.isMissAGo(cardsToPlay)) playerIndexTurn += (clockwise ? 1 : -1);
            if (rules.isNomination(cardsToPlay)) pile.nominate(player.nomination(rules, deck, pile));
            if (rules.isSwitchDirection(cardsToPlay)) clockwise = !clockwise;
        } else {
            player.addCardToHand(deck.draw());
            if (deck.empty()) newDeck();
        }
        setPlayerIndexTurn();
    }

    private void playCards(Player player, Pile pile, List<Card> cardsToPlay) {
        cardsToPlay.forEach(player::removeCardFromHand);
        pile.play(cardsToPlay);
    }

    private void newDeck() {
        List<Card> newDeck = pile.getCardsBelowTopCard();
        Collections.reverse(newDeck);
        deck = new Deck(newDeck);
        Card pileTopCard = pile.topCard();
        pile = new Pile(pileTopCard);
    }

    private void setPlayerIndexTurn() {
        playerIndexTurn += (clockwise ? 1 : -1);
        if (playerIndexTurn >= players.size()) playerIndexTurn -= players.size();
        if (playerIndexTurn < 0) playerIndexTurn += players.size();
    }
}
