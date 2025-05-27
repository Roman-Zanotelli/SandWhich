package com.pluralsight.app.item;

public class Drink extends Item{
    public Drink(String name) {
        super(name, 0);
    }



    public enum DrinkSize {
        Small(2), Medium(2.5), Large(3);
        final double price;
        String selection;
        final String options[] = new String[]{
                "Pepsi",
                "Mountain Dew",
                "Sprite",
                "RootBeer",
                "Fruit Punch"
        };

        DrinkSize(double price){
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        public String[] getOptions() {
            return options;
        }

    }
}
