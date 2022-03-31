package com.company;

public class Card {
    private final Meaning mean;
    private final Suit suit;


    public Card(Meaning mean, Suit suit) {
            this.mean = mean;
            this.suit = suit;
    }

    public Meaning getMean() {
        return mean;
    }

    public Suit getSuit() {
        return suit;
    }
}
