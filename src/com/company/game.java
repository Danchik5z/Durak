package com.company;

import java.util.Scanner;

public class game {

    public static void begin(){
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        deck.shuffle();
        Player player1 = new Player("Player1");
        player1.distributionOfCards(deck);

        Player player2 = new Player("Player2");
        player2.distributionOfCards(deck);

        Call call = new Call();

        boolean attacker = player1.first(deck) <= player2.first(deck);
        Card trump = deck.getTrump();
        Suit trumpSuit = trump.getSuit();



        while (deck.gameIsOn(player1, player2)) {
            while (call.size() != 12 && deck.gameIsOn(player1, player2)) {
                if (attacker) {
                    picture(player1, call, trump);
                    int numberAttack = scanner.nextInt();
                    if (numberAttack == 0) {
                        attacker = false;
                        break;
                    }
                    player1.attack(call, numberAttack);
                    picture(player2, call, trump);
                    int numberDefend = scanner.nextInt();
                    if (numberDefend == 0) {
                        player2.pickCards(call);
                        break;
                    }
                    player2.defender(call, numberDefend, trumpSuit);

                } else {
                    picture(player2, call, trump);
                    int numberAttack = scanner.nextInt();
                    if (numberAttack == 0) {
                        attacker = true;
                        break;
                    }
                    player2.attack(call, numberAttack);
                    picture(player1, call, trump);
                    int numberDefend = scanner.nextInt();
                    if (numberDefend == 0) {
                        player1.pickCards(call);
                        break;
                    }
                    player1.defender(call, numberDefend, trumpSuit);
                }
            }
            if (call.size() == 12) {
                attacker = !attacker;
            }
            call.clear();
            if (attacker) {
                player1.takeCardsFromDeck(deck);
                player2.takeCardsFromDeck(deck);
            } else {
                player2.takeCardsFromDeck(deck);
                player1.takeCardsFromDeck(deck);
            }
        }
    winner(player1, player2);
}

    private static void winner(Player player1, Player player2){
        if(player1.win()){
            pictureWin(player1);
        }
        else {
            pictureWin(player2);
        }
    }

    private static void picture(Player player, Call call, Card trump) {
        for (int k = 0; k < 250; k++) {
            System.out.print("-");
        }
        System.out.print('\n'+ player.getName() + "\n" + "Козырь: " + trump.getSuit() + " " + trump.getMean() + "\n" + "\n");
        for (int i = 0; i < call.size(); i++) {
            for (int d = 0; d < 100; d++) {
                System.out.print(" ");
            }
            System.out.println(call.get(i).getSuit() + "  " + call.get(i).getMean());
        }
        for(int i = 0; i < player.size(); i++) {
            System.out.println(i + 1 + ")" + player.get(i).getSuit() + "  " + player.get(i).getMean());
        }
        if((call.size() % 2) == 0) {
            System.out.print("\n" + "Введите номер карты, которой хотите походить(если не хотите ходить введите - 0):");
        }
        else {
            System.out.print("\n" + "Введите номер карты, которой хотите побить(чтобы взять введите - 0):");
        }
    }

    private static void pictureWin(Player player){
        String p = "                                                                               ";
        System.out.print("\n\n\n\n\n\n\n" + p + "Поздравляем, " + player.getName() + ", Вы победили");
    }


}
