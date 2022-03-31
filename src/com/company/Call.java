package com.company;

import java.util.ArrayList;
import java.util.List;

public class Call {
    private final List<Card> call;

    Call(){
        this.call = new ArrayList<>();

    }
    public int size(){
        return this.call.size();
    }

    public void clear(){
        this.call.clear();
    }

    public void add(Card card){
        this.call.add(card);
    }

    public Card get(int i){
        return this.call.get(i);
    }

    public Card remove(int k){
        return this.call.remove(k);
    }

}
