package com.pluralsight;

import com.pluralsight.item.Chips;
import com.pluralsight.item.Drink;
import com.pluralsight.item.Item;
import com.pluralsight.item.Sandwich;

import java.util.ArrayList;
import java.util.Comparator;

public final class Cart {
    static ArrayList<Item> items;
    private Cart(){}

    static {
        items = new ArrayList<>();
    }

    public void displayEach(){
        items.forEach(UserOutput::display);
    }
    public double getTotal(){
        return items.stream().mapToDouble(Item::getPrice).sum();
    }
    public void sort(){
        items.sort(Comparator.comparing(Item::getName));
    }
    public void addSandwich(){
        Sandwich sandwich = UserInput.promptSandwichSelection();
        if(sandwich == null) return;
        items.add(sandwich);
        UserOutput.display("Sandwich Added - $" + sandwich.getPrice());
    }
    public void addDrink(){
        Drink drink = UserInput.promptDrinkSelection();
        if(drink == null) return;
        items.add(drink);
        UserOutput.display("Drink Added - " + drink.getName());
    }
    public void addChips(){
        Chips chips = UserInput.promptChipsSelection();
        if(chips == null) return;
        items.add(chips);
        UserOutput.display("Chips Added - " + chips.getName());
    }
    public void checkOut(){
        Reciept.process(items);
        items.clear();
    }
    public void clear(){
        items.clear();
    }
}
