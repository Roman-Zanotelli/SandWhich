package com.pluralsight.app;

import com.pluralsight.app.item.*;
import com.pluralsight.app.ui.UserInput;
import com.pluralsight.app.ui.UserOutput;
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
        if(items.size() == 0) {
            UserOutput.display("Empty Cart");
            return;
        }
        UserOutput.display("Cart: ");
        items.forEach(UserOutput::display);
    }

    @OnMenuLoad(menu = "CheckOutMenu", wave = 1)
    @OnMenuLoad(menu = "OrderMenu", wave = 1)
    public static void displayTotal(){
        if(!items.isEmpty())UserOutput.display("Cart Total: $"+getTotal());
    }

    public static double getTotal(){
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    private static void sort(){
        items.sort(Comparator.comparing(Item::getName));
    }


    //Sandwich
    private static void addSandwich(Sandwich sandwich){
        if(sandwich == null) return;
        items.add(sandwich);
        UserOutput.display("Sandwich Added - $" + sandwich.getPrice());
        sort();
    }

    @OnOptionSelect(menu = "SandwichSizeMenu", option = "smallSandwich")
    public static void addSmallSandwich(){
        addSandwich(UserInput.promptSandwichSelection(Size.Small));
    }

    @OnOptionSelect(menu = "SandwichSizeMenu", option = "mediumSandwich")
    public static void addMediumSandwich(){
        addSandwich(UserInput.promptSandwichSelection(Size.Medium));
    }

    @OnOptionSelect(menu = "SandwichSizeMenu", option = "largeSandwich")
    public static void addLargeSandwich(){
        addSandwich(UserInput.promptSandwichSelection(Size.Large));
    }

    @OnOptionSelect(menu = "OrderMenu", option = "modify_item")
    public static void modifyItem(){
        int selection = UserInput.promptItemRemoval(items);
        if(selection == -1) return;
        items.add(UserInput.promptItemModification(items.remove(selection)));
    }


    //Drink
    private static void addDrink (Drink drink){
        if (drink == null) return;
        items.add(drink);
        UserOutput.display("Drink Added - " + drink.getName());
        sort();
    }

    @OnOptionSelect(menu = "DrinkSizeMenu", option = "smallDrink")
    public static void addSmallDrink () {
        addDrink(UserInput.promptDrinkSelection(Size.Small));
    }

    @OnOptionSelect(menu = "DrinkSizeMenu", option = "mediumDrink")
    public static void addMediumDrink () {
        addDrink(UserInput.promptDrinkSelection(Size.Medium));
    }

    @OnOptionSelect(menu = "DrinkSizeMenu", option = "largeDrink")
    public static void addLargeDrink () {
        addDrink(UserInput.promptDrinkSelection(Size.Large));
    }


    //Chips
    @OnOptionSelect(menu = "OrderMenu", option = "add_chips")
    public static void addChips(){
        Chips chips = UserInput.promptChipsSelection();
        if(chips == null) return;
        items.add(chips);
        UserOutput.display("Chips Added - " + chips.getName());
        sort();
    }


    //Remove and item
    @OnOptionSelect(menu = "OrderMenu", option = "remove_item")
    public static void removeItem(){
        int i = UserInput.promptItemRemoval(items);
        if(i == -1){
            UserOutput.display("Canceled!");
            return;
        }
        UserOutput.display(items.remove(i).getName() + " Removed");
    }

    //Checkout
    @OnOptionSelect(menu = "CheckOutMenu", option = "confirm_order")
    public static void checkOut(){
        Receipt.process(items);
        items.clear();
    }


    //Clear cart
    @OnOptionSelect(menu = "OrderMenu", option = "cancel_order")
    @OnOptionSelect(menu = "CheckOutMenu", option = "cancel_order")
    public static void clear(){
        items.clear();
    }
}
