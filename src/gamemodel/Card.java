package gamemodel;

import java.util.Arrays;

public class Card {

    private FaceValue faceValue;
    private Suit suit;

    public FaceValue getFaceValue() {
        return this.faceValue;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public Card(FaceValue faceValue, Suit suit) {
        this.faceValue = faceValue;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return (this.getFaceValue() != null ? this.getFaceValue().toString() + " " : "") + this.getSuit().toString();
    }

    @Override
    public boolean equals(Object obj) {
        Card card = (Card) obj;
        return this.getFaceValue() == card.getFaceValue() &&
                this.getSuit() == card.getSuit();
    }

    @Override
    public int hashCode() {
        return Arrays.asList(Suit.values()).indexOf(suit) * FaceValue.values().length +
                Arrays.asList(FaceValue.values()).indexOf(faceValue);
    }
}
