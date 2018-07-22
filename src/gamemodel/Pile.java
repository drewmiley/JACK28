package gamemodel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pile {

    private List<Card> cards;

    private int drawCardActiveRun = 0;

    public List<Card> getCardsBelowTopCard() {
        return this.cards.subList(0, this.cards.size() - 1)
                .stream()
                .filter(card -> card.getFaceValue() != null)
                .collect(Collectors.toList());
    }

    public Pile(Card card) {
        this.cards = Stream.of(card).collect(Collectors.toList());
    }

    public Card topCard() {
        return this.cards.get(this.cards.size() - 1);
    }

    public void play(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public void nominate(Suit nominatedSuit) {
        this.cards.add(new Card(null, nominatedSuit));
    }

    public int getDrawCardActiveRun() {
        return drawCardActiveRun;
    }

    public void resetDrawCardActiveRun() {
        this.drawCardActiveRun = 0;
    }

    public void incrementDrawCardActiveRun() {
        this.drawCardActiveRun++;
    }
}
