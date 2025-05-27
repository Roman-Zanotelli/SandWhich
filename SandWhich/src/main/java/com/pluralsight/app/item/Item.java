package com.pluralsight.app.item;

public abstract class Item {
    private String name;
    protected double price;
    protected Item(String name, double price){
        this.name = name;
        this.price = price;
    }
    public final String getName(){
        return name;
    }

    public final double getPrice(){
        return price;
    }
}
