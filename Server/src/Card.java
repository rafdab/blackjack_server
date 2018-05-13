package src;

public class Card implements java.io.Serializable{
    private String suit;
    private int value;

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Card() {
    }

    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }
}
