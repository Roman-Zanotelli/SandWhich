package com.pluralsight.app.ui;

import com.pluralsight.annotation.system.ScannerProducer;
import com.pluralsight.app.ingredient.ChipSelection;
import com.pluralsight.app.ingredient.DrinkSelection;
import com.pluralsight.app.ingredient.Ingredient;
import com.pluralsight.app.item.*;

import java.util.ArrayList;
import java.util.Scanner;

@ScannerProducer
public final class UserInput {

    static Scanner scanner = new Scanner(System.in);
    public static Sandwich promptSandwichSelection(Size size){
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        //Bread Logic
        {
            UserOutput.print("\n".repeat(5));
            UserOutput.display(String.format("%s Bread - %.2f", size.toString(), Ingredient.Premium.Bread.Rye.getPrice(size)));
            Ingredient.Premium.Bread[] ingredientSelection = Ingredient.Premium.Bread.values();
            for (int i = 0; i < ingredientSelection.length; i++) {
                UserOutput.display(String.format("%d : %s", i, ingredientSelection[i].toString().replace("_", " ")));
            }
            UserOutput.display("-1 : Done");

            int selection;
            do {
                UserOutput.print("Please Select a Bread: ");
                selection = scanner.nextInt();
                scanner.nextLine();
            } while (selection < -1 || selection >= ingredientSelection.length);
            UserOutput.print("\n".repeat(5));
            if(selection != -1) {
                ingredients.add(ingredientSelection[selection]);
                UserOutput.display(ingredientSelection[selection].toString().replace("_", " ") + " Bread Added!");
            }
        }

        //Meat Logic
        {

            UserOutput.display(String.format("%s Meat - %.2f", size.toString(), Ingredient.Premium.Meat.Ham.getPrice(size)));
            Ingredient.Premium.Meat[] ingredientSelection = Ingredient.Premium.Meat.values();
            for (int i = 0; i < ingredientSelection.length; i++) {
                UserOutput.display(String.format("%d : %s", i, ingredientSelection[i].toString().replace("_", " ")));
            }
            UserOutput.display("-1 : Done");

            int selection;
            do {
                UserOutput.print("Please Select a Meat: ");
                selection = scanner.nextInt();
                scanner.nextLine();
            } while (selection < -1 || selection >= ingredientSelection.length);
            UserOutput.print("\n".repeat(5));
            if(selection != -1){
                ingredients.add(ingredientSelection[selection]);
                UserOutput.display(ingredientSelection[selection].toString().replace("_", " ") + " Added!");
                //Meat Logic
                int selection2;
                do {

                    UserOutput.display(String.format("%s ExtraMeat - %.2f", size.toString(), Ingredient.Premium.ExtraMeat.Ham.getPrice(size)));
                    Ingredient.Premium.ExtraMeat[] ingredientSelection2 = Ingredient.Premium.ExtraMeat.values();
                    for (int i = 0; i < ingredientSelection2.length; i++) {
                        UserOutput.display(String.format("%d : %s", i, ingredientSelection2[i].toString().replace("_", " ")));
                    }
                    UserOutput.display("-1 : Done");

                    do {
                        UserOutput.print("Please Select a Meat: ");
                        selection2 = scanner.nextInt();
                        scanner.nextLine();
                    } while (selection2 < -1 || selection2 >= ingredientSelection2.length);
                    UserOutput.print("\n".repeat(5));
                    if(selection2 != -1) {
                        ingredients.add(ingredientSelection2[selection]);
                        UserOutput.display("Extra "+ingredientSelection[selection].toString().replace("_", " ") + " Added!");
                    }
                }while(selection2 != - 1);
            }
        }

        //Cheese Logic
        {
            UserOutput.display(String.format("%s Cheese - %.2f", size.toString(), Ingredient.Premium.Cheese.Cheddar.getPrice(size)));
            Ingredient.Premium.Cheese[] ingredientSelection = Ingredient.Premium.Cheese.values();
            for (int i = 0; i < ingredientSelection.length; i++) {
                UserOutput.display(String.format("%d : %s", i, ingredientSelection[i].toString().replace("_", " ")));
            }
            UserOutput.display("-1 : Done");

            int selection;
            do {
                UserOutput.print("Please Select a Cheese: ");
                selection = scanner.nextInt();
                scanner.nextLine();
            } while (selection < -1 || selection >= ingredientSelection.length);
            UserOutput.print("\n".repeat(5));
            if(selection != -1){
                ingredients.add(ingredientSelection[selection]);
                UserOutput.display(ingredientSelection[selection].toString().replace("_", " ") + " Added!");

                //Meat Logic
                int selection2;
                do {

                    UserOutput.display(String.format("%s ExtraCheese - %.2f", size.toString(), Ingredient.Premium.ExtraCheese.American.getPrice(size)));
                    Ingredient.Premium.ExtraCheese[] ingredientSelection2 = Ingredient.Premium.ExtraCheese.values();
                    for (int i = 0; i < ingredientSelection2.length; i++) {
                        UserOutput.display(String.format("%d : %s", i, ingredientSelection2[i].toString().replace("_", " ")));
                    }
                    UserOutput.display("-1 : Done");

                    do {
                        UserOutput.print("Please Select a Cheese: ");
                        selection2 = scanner.nextInt();
                        scanner.nextLine();
                    } while (selection2 < -1 || selection2 >= ingredientSelection2.length);
                    UserOutput.print("\n".repeat(5));
                    UserOutput.print("\n".repeat(5));
                    if(selection2 != -1) {
                        ingredients.add(ingredientSelection2[selection]);
                        UserOutput.display("Extra "+ingredientSelection[selection].toString().replace("_", " ") + " Added!");
                    }
                }while(selection2 != - 1);
            }
        }

        //Included Toppings Logic
        {
            int selection;
            do {
                UserOutput.display("Included Toppings");
                Ingredient.Included.Topping[] ingredientSelection = Ingredient.Included.Topping.values();
                for (int i = 0; i < ingredientSelection.length; i++) {
                    UserOutput.display(String.format("%d : %s", i, ingredientSelection[i].toString().replace("_", " ")));
                }
                UserOutput.display("-1 : Done");

                do {
                    UserOutput.print("Please Select a Topping: ");
                    selection = scanner.nextInt();
                    scanner.nextLine();
                } while (selection < -1 || selection >= ingredientSelection.length);
                UserOutput.print("\n".repeat(5));
                if (selection != -1) {
                    ingredients.add(ingredientSelection[selection]);
                    UserOutput.display(ingredientSelection[selection].toString().replace("_", " ") + " Added!");
                }
            } while(selection != -1);
        }
        //Included Sauce Logic
        {
            int selection;
            do {
                UserOutput.display("Included Sauces");
                Ingredient.Included.Sauce[] ingredientSelection = Ingredient.Included.Sauce.values();
                for (int i = 0; i < ingredientSelection.length; i++) {
                    UserOutput.display(String.format("%d : %s", i, ingredientSelection[i].toString().replace("_", " ")));
                }
                UserOutput.display("-1 : Done");

                do {
                    UserOutput.print("Please Select a Sauce: ");
                    selection = scanner.nextInt();
                    scanner.nextLine();
                } while (selection < -1 || selection >= ingredientSelection.length);
                UserOutput.print("\n".repeat(5));
                if (selection != -1) {
                    ingredients.add(ingredientSelection[selection]);
                    UserOutput.display(ingredientSelection[selection].toString().replace("_", " ") + " Added!");
                }
            } while(selection != -1);
        }

        UserOutput.print("Name Your Sandwich (Nothing to cancel): ");
        String name = scanner.nextLine();
        UserOutput.print("\n".repeat(5));
        if(name.isBlank()) return null;
        return new Sandwich(name, size, ingredients);
    }


    public static Drink promptDrinkSelection(Size size){
        UserOutput.print("\n".repeat(5));
        //Drink Logic
        {
            float price = switch (size){
            case Small -> 2;
            case Medium -> 2.50f;
            case Large -> 3;
            };

            UserOutput.display(String.format("%s Drink - %.2f", size.toString() , price));

            DrinkSelection[] ingredientSelection = DrinkSelection.values();
            for (int i = 0; i < ingredientSelection.length; i++) {
                UserOutput.display(String.format("%d : %s", i, ingredientSelection[i].toString().replace("_", " ")));
            }
            UserOutput.display("-1 : Cancel");

            int selection;
            do {
                UserOutput.print("Please Select a Flavor: ");
                selection = scanner.nextInt();
                scanner.nextLine();
            } while (selection < -1 || selection >= ingredientSelection.length);
            if(selection != -1) return new Drink(ingredientSelection[selection].toString().replace("_", " "), size);
            return null;
        }
    }


    public static Chips promptChipsSelection(){
        UserOutput.print("\n".repeat(5));
        UserOutput.display("Chips - $1.5");

        ChipSelection[] ingredientSelection = ChipSelection.values();
        for (int i = 0; i < ingredientSelection.length; i++) {
            UserOutput.display(String.format("%d : %s", i, ingredientSelection[i].toString().replace("_", " ")));
        }
        UserOutput.display("-1 : Cancel");

        int selection;
        do {
            UserOutput.print("Please Select a Flavor: ");
            selection = scanner.nextInt();
            scanner.nextLine();
        } while (selection < -1 || selection >= ingredientSelection.length);
        if(selection != -1) return new Chips(ingredientSelection[selection].toString().replace("_", " "));
        return null;
    }

    public static int promptItemRemoval(ArrayList<Item> items){
        UserOutput.print("\n".repeat(5));
        for (int i = 0; i < items.size(); i++) {
            UserOutput.display(String.format("%d : %s", i, items.get(i).getName()));
        }
        UserOutput.display("-1 : Cancel");
        int selection;
        do{
            UserOutput.print("Enter Selection: ");
            selection = scanner.nextInt();
            scanner.nextLine();
        }while (selection < -1 || selection >= items.size());
        return selection;
    }

    public static Scanner getScanner(){
        return scanner;
    }
}
