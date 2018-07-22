package gamemodel;

public class Card {

    private FaceValue faceValue;
    private Suit suit;

    FaceValue getFaceValue() {
        return this.faceValue;
    }

    Suit getSuit() {
        return this.suit;
    }

    public Card(FaceValue faceValue, Suit suit) {
        this.faceValue = faceValue;
        this.suit = suit;
    }
}
