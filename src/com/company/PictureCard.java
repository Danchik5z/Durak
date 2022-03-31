package com.company;


import javafx.scene.image.ImageView;

public class PictureCard {

    private final Card card;
    private final ImageView imageView;


    public PictureCard(Card card, ImageView imageView) {
        this.card = card;
        this.imageView = imageView;
    }


    public Card getCard() {
        return card;
    }

    public ImageView getImageView() {
        return imageView;
    }


}
