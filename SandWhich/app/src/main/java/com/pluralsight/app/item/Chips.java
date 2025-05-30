package com.pluralsight.app.item;

public class Chips extends Item{
    public Chips(String name) {
        super(name, null);
    }

    @Override
    public double getPrice() {
        return 1.50;
    }
    @Override
    public String toString() {
        return String.format("%s Chips - $%.2f", this.name, this.getPrice());
    }

    public void changeFlavor(String name){
        this.name = name;
    }
}
