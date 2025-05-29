package com.pluralsight.app.item;

public abstract class Item {
    protected String name;
    protected Size size;
    protected Item(String name, Size size){
        this.name = name;
        this.size = size;
    }
    public String getName(){
        return name;
    }
    public final Size getSize(){return size;}

    public abstract double getPrice();

    @Override
    public String toString() {
        return String.format("%s %s - $%.2f", this.size, this.name, this.getPrice());
    }
}
