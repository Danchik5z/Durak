package com.company;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final int PLAYER_CARDS = 6;
    private final List<Card> player;
    private final String name;

    Player(String name){
        this.name = name;
        this.player = new ArrayList<>();
    }

    public void distributionOfCards(Deck deck){
        for(int i = 0; i < PLAYER_CARDS; i++) {
            this.player.add(deck.getCard(i));
        }
    }

    public String getName() {
        return name;
    }


    public Card get(int k){
        return this.player.get(k);
    }

    public int size(){
        return this.player.size();
    }

    public void attack(Call call, int number){
        int count = 0;
        if (number != 0) {
            if (call.size() == 0) {
                call.add(player.remove(number - 1));
                count++;

            } else if (call.size() > 1) {
                for (int i = 0; i < call.size(); i++) {
                    if (call.get(i).getMean().equals(player.get(number - 1).getMean())) {
                        call.add(player.remove(number - 1));
                        count++;
                        break;
                    }
                }
            }
            if(count == 0) {
                attack(call, number);
            }
        }
    }

    public void defender(Call call, int number, Suit trump) {
        Suit lastCardSuit = call.get(call.size() - 1).getSuit();
        if ((player.get(number - 1).getSuit().equals(lastCardSuit) && player.get(number - 1).getMean().ordinal() > call.get(call.size() - 1).getMean().ordinal())
                || ((player.get(number - 1).getSuit().equals(trump) && !(lastCardSuit.equals(trump))))) {
            call.add(player.remove(number - 1));
        } else {
            defender(call, number, trump);
        }

    }


    public int first(Deck major){
        int nom = 20;
        for (int k = 0; k < PLAYER_CARDS; k++) {
            if(this.player.get(k).getSuit().equals(major.deck.get(major.deck.size()-1).getSuit()) && this.player.get(k).getMean().ordinal() < nom) {
                nom = this.player.get(k).getMean().ordinal();
            }
        }
        return nom;
    }

    public void pickCards(Call call){
        while(call.size() != 0){
            this.player.add(call.remove(0));
        }
    }

    public void takeCardsFromDeck(Deck major){
        while(player.size() < PLAYER_CARDS && major.deck.size() != 0) {
            player.add(major.getCard(0));
        }
    }

    public boolean win(){
        return player.size() == 0;
    }







}
