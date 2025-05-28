package com.pluralsight.app;

import com.pluralsight.app.ui.UserInput;
import com.pluralsight.app.ui.UserOutput;
import com.pluralsight.app.item.Chips;
import com.pluralsight.app.item.Drink;
import com.pluralsight.app.item.Item;
import com.pluralsight.app.item.Sandwich;
import com.pluralsight.annotation.menu.OnMenuLoad;
import com.pluralsight.annotation.menu.option.OnOptionSelect;

import java.util.ArrayList;
import java.util.Comparator;

public final class Cart {
    private static ArrayList<Item> items;
    private Cart(){}

    static {
        items = new ArrayList<>();
    }

    @OnMenuLoad(menu = "CheckOutMenu")
    @OnMenuLoad(menu = "OrderMenu")
    public static void displayEach(){
        items.forEach(UserOutput::display);
    }

    @OnMenuLoad(menu = "CheckOutMenu", wave = 1)
    @OnMenuLoad(menu = "OrderMenu", wave = 1)
    public static void displayTotal(){
        UserOutput.display(getTotal());
    }

    public static double getTotal(){
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    private static void sort(){
        items.sort(Comparator.comparing(Item::getName));
    }

    @OnOptionSelect(menu = "OrderMenu", option = "add_sandwich")
    public static void addSandwich(){
        Sandwich sandwich = UserInput.promptSandwichSelection();
        if(sandwich == null) return;
        items.add(sandwich);
        UserOutput.display("Sandwich Added - $" + sandwich.getPrice());
        sort();
    }

    @OnOptionSelect(menu = "OrderMenu", option = "add_drink")
    public static void addDrink(){
        Drink drink = UserInput.promptDrinkSelection();
        if(drink == null) return;
        items.add(drink);
        UserOutput.display("Drink Added - " + drink.getName());
        sort();
    }

    @OnOptionSelect(menu = "OrderMenu", option = "add_chips")
    public static void addChips(){
        Chips chips = UserInput.promptChipsSelection();
        if(chips == null) return;
        items.add(chips);
        UserOutput.display("Chips Added - " + chips.getName());
        sort();
    }

    @OnOptionSelect(menu = "OrderMenu", option = "remove_item")
    public static void removeItem(){

    }

    @OnOptionSelect(menu = "CheckOutMenu", option = "confirm_order")
    public static void checkOut(){
        Receipt.process(items);
        items.clear();
    }

    @OnOptionSelect(menu = "OrderMenu", option = "cancel_order")
    @OnOptionSelect(menu = "CheckOutMenu", option = "cancel_order")
    public static void clear(){
        items.clear();
    }
}
