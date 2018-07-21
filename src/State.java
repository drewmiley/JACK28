import gamemodel.*;
import players.Player;
import players.VisiblePlayer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class State {

    private Rules rules;
    private Deck deck;
    private Pile pile;
    private List<Player> players;
    private int playerIndexTurn = 0;
    private boolean clockwise = true;

    private List<VisiblePlayer> visiblePlayers() {
        return this.players.stream()
                .map(VisiblePlayer::new)
                .collect(Collectors.toList());
    }

    State(Rules rules, Deck deck, Pile pile, List<Player> players) {
        this.rules = rules;
        this.deck = deck;
        this.pile = pile;
        this.players = players;
        applySpecialCardRules(Stream.of(pile.topCard()).collect(Collectors.toList()), players.get(playerIndexTurn));
    }

    boolean gameComplete() {
        return this.players.stream().anyMatch(Player::handEmpty);
    }

    void takeTurn() {
        Player player = players.get(playerIndexTurn);
        List<Card> cardsToPlay = player.cardsToPlay(rules, deck, pile, visiblePlayers());
        if (rules.isAllowedPlay(pile, cardsToPlay)) {
            playCards(player, cardsToPlay);
            applySpecialCardRules(cardsToPlay, player);
        } else {
            int cardsToDraw = pile.getDrawCardActiveRun() == 0 ? 1 :
                    pile.getDrawCardActiveRun() * rules.DRAW_CARD_NUMBER;
            pile.resetDrawCardActiveRun();
            IntStream.range(0, cardsToDraw)
                    .forEach(i -> {
                        player.addCardToHand(deck.draw(), deck.orderSeen());
                        if (deck.empty()) newDeck();
                    });
        }
        setPlayerIndexTurn();
    }

    private void playCards(Player player, List<Card> cardsToPlay) {
        cardsToPlay.forEach(player::removeCardFromHand);
        pile.play(cardsToPlay);
    }

    private void applySpecialCardRules(List<Card> cardsToPlay, Player player) {
        if (rules.isMissAGo(cardsToPlay)) playerIndexTurn += (clockwise ? 1 : -1);
        if (rules.isNomination(cardsToPlay)) pile.nominate(player.nomination(rules, deck, pile, visiblePlayers()));
        if (rules.isSwitchDirection(cardsToPlay)) clockwise = !clockwise;
        if (rules.isDrawCard(cardsToPlay)) pile.incrementDrawCardActiveRun();
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
