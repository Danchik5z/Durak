package com.company;

import java.util.*;

public class Deck {


    List<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
        for(Meaning v : Meaning.values()) {
            for(Suit k : Suit.values()) {
                deck.add(new Card(v, k));
            }
        }
    }

    public Card getCard(int i) {
        return deck.remove(i);
    }

    public Card getCar(int i) {
        return deck.get(i);
    }

    public void shuffle(){
        Collections.shuffle(this.deck);
    }

    public boolean gameIsOn(Player player1, Player player2){
        return deck.size() != 0 || (player1.size() != 0 && player2.size() != 0);
    }

    public Card getTrump(){
        return deck.get(deck.size() - 1);
    }
}

