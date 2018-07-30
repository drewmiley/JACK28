package gamemodel;

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
}
