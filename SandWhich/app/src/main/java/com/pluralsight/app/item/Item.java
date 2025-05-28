package com.pluralsight.app.item;

public abstract class Item {
    private String name;
    protected Size size;
    protected Item(String name, Size size){
        this.name = name;
        this.size = size;
    }
    public final String getName(){
        return name;
    }

    public abstract double getPrice();
}
