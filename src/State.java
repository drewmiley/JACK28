import java.util.List;

class State {
    Deck deck;
    Pile pile;
    List<Player> players;
    int playerIndexTurn = 0;

    State(Deck deck, Pile pile, List<Player> players) {
        this.deck = deck;
        this.pile = pile;
        this.players = players;
    }

    void takeTurn() {
        // TODO: Implement takeTurn
    }
}
