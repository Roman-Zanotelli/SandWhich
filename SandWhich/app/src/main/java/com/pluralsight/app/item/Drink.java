package com.pluralsight.app.item;

public class Drink extends Item{
    public Drink(String name, Size size) {
        super(name, size);
    }

    @Override
    public double getPrice() {
        return switch (this.size){
            case Small -> 2;
            case Medium -> 2.50;
            case Large -> 3;
        };
    }
}
