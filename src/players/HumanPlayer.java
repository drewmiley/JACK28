package players;

import gamemodel.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class HumanPlayer extends Player {

    HumanPlayer(int playerIndex, List<Card> hand) {
        super(playerIndex, hand);
    }

    @Override
    public List<Card> cardsToPlay(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        List<List<Card>> possibleCardsToPlay = this.possibleCardsToPlay(rules, pile);

        System.out.println("Select cards to play");
        for (int i = 0; i < possibleCardsToPlay.size(); i++) {
            String cardsToPlayString = possibleCardsToPlay.get(i).stream()
                    .reduce("", (acc, val) -> acc + " " + val.toString(), (a, b) -> a + b);
            System.out.println(i + ": " + cardsToPlayString);
        }

        Scanner in = new Scanner(System.in);
        int index = in.nextInt();

        return possibleCardsToPlay.get(index);
    }

    @Override
    public Suit nomination(Rules rules, Deck deck, Pile pile, List<VisiblePlayer> visiblePlayers) {
        System.out.println("Select suit to nominate");
        for (int i = 0; i < Suit.values().length; i++) {
            System.out.println(i + ": " + Suit.values()[i]);
        }

        Scanner in = new Scanner(System.in);
        int index = in.nextInt();

        return Suit.values()[index];
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.HUMAN_PLAYER;
    }

}
