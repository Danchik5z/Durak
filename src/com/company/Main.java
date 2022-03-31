package com.company;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    public static void main(String[] args) {
        launch();
        game.begin();
    }

    @Override
    public void start(Stage stage)   {
        mainMenu();
    }

    private void mainMenu()  {
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Дурак");

        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.LIGHTYELLOW);


        double r=50;
        Button start = new Button("Начать игру");
        start.setShape(new Circle(r));
        start.setMinSize(2.5*r, 1.5*r);
        start.setMaxSize(2*r, 2*r);
        start.setLayoutX(190);
        start.setLayoutY(130);
        start.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> game(stage));
        Button rules = new Button("Правила");
        rules.setShape(new Circle(r));
        rules.setMinSize(2.5*r, 1.5*r);
        rules.setMaxSize(2*r, 2*r);
        rules.setLayoutX(190);
        rules.setLayoutY(230);
        rules.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> rules(stage));

        root.getChildren().addAll(start, rules);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
    }

    public void rules(Stage s){
        s.setTitle("Durak");
        Group group = new Group();
        Button buttonBack = new Button("Вернуться назад");
        buttonBack.setPrefHeight(40);
        buttonBack.setPrefWidth(150);
        buttonBack.setLayoutX(400);
        buttonBack.setLayoutY(720);
        buttonBack.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            s.close();
            mainMenu();
        });
        group.getChildren().addAll(new ImageView(new Image("pictures/rules.jpg")), buttonBack);
        Scene scene = new Scene(group, 1024, 767);
        s.setScene(scene);
        s.centerOnScreen();
        s.show();

    }

    public void game(Stage s)   {

        s.setTitle("Durak");
        Group group = new Group();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scene scene = new Scene(group, screenSize.getWidth(), screenSize.getHeight() - 65, Color.LIGHTSEAGREEN);
        Deck deck = new Deck();
        deck.shuffle();
        Player player1 = new Player("player1");
        player1.distributionOfCards(deck);
        Player player2 = new Player("player2");
        player2.distributionOfCards(deck);
        Call call = new Call();
        Card trump = deck.getTrump();
        Suit trumpSuit = trump.getSuit();
            System.out.println("йцуууууу");
            drawTable(player1, player2, call, trump, group, trumpSuit, deck, true);


            s.setScene(scene);
            s.centerOnScreen();
            s.show();


    }



    public static ImageView getImageView(Card card) {
        String str = "pictures/"+ (card.getMean().ordinal() + 6) +"." + (card.getSuit().ordinal()+1)+ ".png";
        return new ImageView(new Image(str));
    }

    public static ImageView sizeImageView(ImageView iv, double x, double y, double sizeH, double sizeW){
        iv.setX(x);
        iv.setY(y);
        iv.setFitHeight(sizeH);
        iv.setFitWidth(sizeW);
        return iv;
    }

    public static void drawTable(Player attacker, Player defender, Call call, Card trump, Group group, Suit trumpSuit, Deck deck, boolean att){
        group.getChildren().clear();
        List<ImageView> imageViews = new ArrayList();
        for(int i = 0; i < attacker.size(); i++){
            imageViews.add(sizeImageView(getImageView(attacker.get(i)), 450 + 900/attacker.size()*i, 796, 726*0.3, 500*0.3));
        }
        for(int i = 0; i < defender.size(); i++){
            imageViews.add(sizeImageView(new ImageView(new Image("com/company/back.png")), 450 + 900/defender.size()*i, 0, 726*0.3, 500*0.3));
        }
        for(int i = 0; i < call.size(); i++){
            if(i%2 == 0) {
                imageViews.add(sizeImageView(getImageView(call.get(i)), 450 + (100 * i), 440, 726 * 0.3, 500 * 0.3));
            }else {
                imageViews.add(sizeImageView(getImageView(call.get(i)), 470 + (100 * (i-1)), 470, 726 * 0.3, 500 * 0.3));
            }
        }
        TextField textFieldChoosePlayers = new TextField();
        textFieldChoosePlayers.setLayoutX(1478);
        textFieldChoosePlayers.setLayoutY(900);
        textFieldChoosePlayers.setPrefWidth(50);
        group.getChildren().add(textFieldChoosePlayers);
        if(call.size()%2 == 0){
            Button attack = but(1450, 850, 30, 100, "ходить");
            if(att) {
                attack.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    attacker.attack(call, Integer.parseInt(textFieldChoosePlayers.getText()));
                    drawTable(defender, attacker, call, trump, group, trumpSuit, deck, false);
                });
            }
            else {
                attack.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    defender.attack(call, Integer.parseInt(textFieldChoosePlayers.getText()));
                    drawTable(attacker, defender, call, trump, group, trumpSuit, deck, true);
                });
            }
            group.getChildren().add(attack);

            Button stop = but(220, 900, 30, 100, "бито");
                if(att) {
                    stop.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                        call.clear();
                        attacker.takeCardsFromDeck(deck);
                        defender.takeCardsFromDeck(deck);
                        drawTable(defender, attacker, call, trump, group, trumpSuit,deck, true);
                    });
                }
                else {
                    stop.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                        call.clear();
                        defender.takeCardsFromDeck(deck);
                        attacker.takeCardsFromDeck(deck);
                        drawTable(attacker, defender, call, trump, group, trumpSuit, deck, false);
                    });
                }
                group.getChildren().add(stop);
        }
        else {
            Button save = but(1450, 850, 30, 100, "отбить");
            save.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                attacker.defender(call, Integer.parseInt(textFieldChoosePlayers.getText()),trumpSuit);
                drawTable(defender, attacker,call, trump, group, trumpSuit, deck, true);
            });
            group.getChildren().add(save);

            Button take = but(220, 900, 30, 100, "взять");
            if(att) {
                take.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    attacker.takeCardsFromDeck(deck);
                    defender.pickCards(call);
                    drawTable(attacker, defender, call, trump, group, trumpSuit,deck, false);
                });
            }
            else {
                take.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    defender.takeCardsFromDeck(deck);
                    attacker.pickCards(call);
                    drawTable(defender, attacker, call, trump, group, trumpSuit, deck, true);
                });
            }
            group.getChildren().add(take);
        }
        ImageView s = sizeImageView(getImageView(trump), 1450, 428, 726*0.3, 500*0.3);
        s.setRotate(90);
        imageViews.add(s);
        imageViews.add(sizeImageView(new ImageView(new Image("com/company/back.png")), 1500, 400, 726*0.3, 500*0.3));
        for (ImageView q : imageViews) {
            group.getChildren().add(q);
        }
    }

    public static Button but(int x, int y, int sizeX, int sizeY, String name){

        Button buttonChoose = new Button(name);
        buttonChoose.setLayoutX(x);
        buttonChoose.setLayoutY(y);
        buttonChoose.setPrefHeight(sizeX);
        buttonChoose.setPrefWidth(sizeY);
        return buttonChoose;
    }



}


