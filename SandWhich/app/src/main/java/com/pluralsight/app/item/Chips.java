package com.pluralsight.app.item;

public class Chips extends Item{
    public Chips(String name) {
        super(name, null);
    }

    @Override
    public double getPrice() {
        return 1.50;
    }
}
