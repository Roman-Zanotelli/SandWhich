package com.pluralsight.app.ui;

import com.pluralsight.annotation.system.OnShutDown;
import com.pluralsight.annotation.system.ScannerProducer;
import com.pluralsight.app.ingredient.ChipSelection;
import com.pluralsight.app.ingredient.DrinkSelection;
import com.pluralsight.app.ingredient.Ingredient;
import com.pluralsight.app.item.*;

import java.util.*;
import java.util.stream.Collectors;

@ScannerProducer
public final class UserInput {

    static Scanner scanner = new Scanner(System.in);
    public static Sandwich promptSandwichSelection(Size size){
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        //Bread Logic
        {
            UserOutput.print("\n".repeat(5));
            UserOutput.display(String.format("%s Bread - $%.2f", size.toString(), Ingredient.Premium.Bread.Rye_Bread.getPrice(size)));
            ArrayList<Ingredient.Premium.Bread> ingredientSelection =  Arrays.stream(Ingredient.Premium.Bread.values())
                    .sorted(Comparator.comparing(Objects::toString)).collect(Collectors.toCollection(ArrayList::new));
            for (int i = 0; i < ingredientSelection.size(); i++) {
                UserOutput.display(String.format("%d : %s", i, ingredientSelection.get(i).toString().replace("_", " ")));
            }
            UserOutput.display("-1 : Done");

            int selection;
            do {
                UserOutput.print("Please Select a Bread: ");
                try {
                    selection = scanner.nextInt();
                } catch (Exception e){
                    selection = -777;
                }
                scanner.nextLine();
            } while (selection < -1 || selection >= ingredientSelection.size());
            UserOutput.print("\n".repeat(5));
            if(selection != -1) {
                ingredients.add(ingredientSelection.get(selection));
                UserOutput.display(ingredientSelection.get(selection).toString().replace("_", " ") + " Added!");
            }
        }

        //Meat Logic
        {

            UserOutput.display(String.format("%s Meat - $%.2f", size.toString(), Ingredient.Premium.Meat.Ham.getPrice(size)));
            ArrayList<Ingredient.Premium.Meat> ingredientSelection = Arrays.stream(Ingredient.Premium.Meat.values()).sorted(Comparator.comparing(Objects::toString))
                    .collect(Collectors.toCollection(ArrayList::new));;
            for (int i = 0; i < ingredientSelection.size(); i++) {
                UserOutput.display(String.format("%d : %s", i, ingredientSelection.get(i).toString().replace("_", " ")));
            }
            UserOutput.display("-1 : Done");

            int selection;
            do {
                UserOutput.print("Please Select a Meat: ");
                try {
                    selection = scanner.nextInt();
                } catch (Exception e){
                    selection = -777;
                }
                scanner.nextLine();
            } while (selection < -1 || selection >= ingredientSelection.size());
            UserOutput.print("\n".repeat(5));
            if(selection != -1){
                ingredients.add(ingredientSelection.get(selection));
                UserOutput.display(ingredientSelection.get(selection).toString().replace("_", " ") + " Added!");
                //Meat Logic
                int selection2;
                do {

                    UserOutput.display(String.format("%s ExtraMeat - $%.2f", size.toString(), Ingredient.Premium.ExtraMeat.Ham.getPrice(size)));
                    ArrayList<Ingredient.Premium.ExtraMeat> ingredientSelection2 =  Arrays.stream(Ingredient.Premium.ExtraMeat.values()).sorted(Comparator.comparing(Objects::toString)).collect(Collectors.toCollection(ArrayList::new));
                    for (int i = 0; i < ingredientSelection2.size(); i++) {
                        UserOutput.display(String.format("%d : %s", i, ingredientSelection2.get(i).toString().replace("_", " ")));
                    }
                    UserOutput.display("-1 : Done");

                    do {
                        UserOutput.print("Please Select a Meat: ");
                        try {
                            selection2 = scanner.nextInt();
                        } catch (Exception e){
                            selection2 = -777;
                        }
                        scanner.nextLine();
                    } while (selection2 < -1 || selection2 >= ingredientSelection2.size());
                    UserOutput.print("\n".repeat(5));
                    if(selection2 != -1) {
                        ingredients.add(ingredientSelection2.get(selection2));
                        UserOutput.display("Extra "+ingredientSelection2.get(selection2).toString().replace("_", " ") + " Added!");
                    }
                }while(selection2 != - 1);
            }
        }

        //Cheese Logic
        {
            UserOutput.display(String.format("%s Cheese - $%.2f", size.toString(), Ingredient.Premium.Cheese.Cheddar.getPrice(size)));
            ArrayList<Ingredient.Premium.Cheese> ingredientSelection = Arrays.stream(Ingredient.Premium.Cheese.values()).sorted(Comparator.comparing(Objects::toString)).collect(Collectors.toCollection(ArrayList::new));;
            for (int i = 0; i < ingredientSelection.size(); i++) {
                UserOutput.display(String.format("%d : %s", i, ingredientSelection.get(i).toString().replace("_", " ")));
            }
            UserOutput.display("-1 : Done");

            int selection;
            do {
                UserOutput.print("Please Select a Cheese: ");
                try {
                    selection = scanner.nextInt();
                } catch (Exception e){
                    selection = -777;
                }
                scanner.nextLine();
            } while (selection < -1 || selection >= ingredientSelection.size());
            UserOutput.print("\n".repeat(5));
            if(selection != -1){
                ingredients.add(ingredientSelection.get(selection));
                UserOutput.display(ingredientSelection.get(selection).toString().replace("_", " ") + " Added!");
                //Meat Logic
                int selection2;
                do {

                    UserOutput.display(String.format("%s ExtraCheese - $%.2f", size.toString(), Ingredient.Premium.ExtraCheese.American.getPrice(size)));
                    ArrayList<Ingredient.Premium.ExtraCheese> ingredientSelection2 =  Arrays.stream(Ingredient.Premium.ExtraCheese.values()).sorted(Comparator.comparing(Objects::toString)).collect(Collectors.toCollection(ArrayList::new));;
                    for (int i = 0; i < ingredientSelection2.size(); i++) {
                        UserOutput.display(String.format("%d : %s", i, ingredientSelection2.get(i).toString().replace("_", " ")));
                    }
                    UserOutput.display("-1 : Done");

                    do {
                        UserOutput.print("Please Select a Cheese: ");
                        try {
                            selection2 = scanner.nextInt();
                        } catch (Exception e){
                            selection2 = -777;
                        }
                        scanner.nextLine();
                    } while (selection2 < -1 || selection2 >= ingredientSelection2.size());
                    UserOutput.print("\n".repeat(5));
                    UserOutput.print("\n".repeat(5));
                    if(selection2 != -1) {
                        ingredients.add(ingredientSelection2.get(selection2));
                        UserOutput.display("Extra "+ingredientSelection2.get(selection2).toString().replace("_", " ") + " Added!");
                    }
                }while(selection2 != - 1);
            }
        }
        //Included Toppings Logic
        {
            int selection;
            do {
                UserOutput.display("Included Toppings");
                ArrayList<Ingredient.Included.Topping> ingredientSelection =  Arrays.stream(Ingredient.Included.Topping.values()).sorted(Comparator.comparing(Objects::toString)).collect(Collectors.toCollection(ArrayList::new));
                for (int i = 0; i < ingredientSelection.size(); i++) {
                    UserOutput.display(String.format("%d : %s", i, ingredientSelection.get(i).toString().replace("_", " ")));
                }
                UserOutput.display("-1 : Done");

                do {
                    UserOutput.print("Please Select a Topping: ");
                    try {
                        selection = scanner.nextInt();
                    } catch (Exception e){
                        selection = -777;
                    }
                    scanner.nextLine();
                } while (selection < -1 || selection >= ingredientSelection.size());
                UserOutput.print("\n".repeat(5));
                if (selection != -1) {
                    ingredients.add(ingredientSelection.get(selection));
                    UserOutput.display(ingredientSelection.get(selection).toString().replace("_", " ") + " Added!");
                }
            } while(selection != -1);
        }
        //Included Sauce Logic
        {
            int selection;
            do {
                UserOutput.display("Included Sauces");
                ArrayList<Ingredient.Included.Sauce> ingredientSelection = Arrays.stream(Ingredient.Included.Sauce.values()).sorted(Comparator.comparing(Objects::toString)).collect(Collectors.toCollection(ArrayList::new));;
                for (int i = 0; i < ingredientSelection.size(); i++) {
                    UserOutput.display(String.format("%d : %s", i, ingredientSelection.get(i).toString().replace("_", " ")));
                }
                UserOutput.display("-1 : Done");

                do {
                    UserOutput.print("Please Select a Sauce: ");
                    try {
                        selection = scanner.nextInt();
                    } catch (Exception e){
                        selection = -777;
                    }
                    scanner.nextLine();
                } while (selection < -1 || selection >= ingredientSelection.size());
                UserOutput.print("\n".repeat(5));
                if (selection != -1) {
                    ingredients.add(ingredientSelection.get(selection));
                    UserOutput.display(ingredientSelection.get(selection).toString().replace("_", " ") + " Added!");
                }
            } while(selection != -1);
        }

        UserOutput.print("Name Your Sandwich (Nothing to cancel): ");
        String name = scanner.nextLine();
        UserOutput.print("\n".repeat(5));
        if(name.isBlank()) return null;
        boolean isToasted;
        UserOutput.print("Do you want that Toasted? (true/false)>> ");
        while (!scanner.hasNextBoolean()){
            UserOutput.print("Do you want that Toasted? (true/false) >> ");
            scanner.nextLine();
        }
        isToasted = scanner.nextBoolean();
        scanner.nextLine();
        if(ingredients.isEmpty()) return null;
        return new Sandwich(name + " Sandwhich?", size, ingredients, isToasted);
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

            UserOutput.display(String.format("%s Drink - $%.2f", size.toString() , price));

            ArrayList<DrinkSelection> ingredientSelection = Arrays.stream(DrinkSelection.values()).sorted(Comparator.comparing(Objects::toString)).collect(Collectors.toCollection(ArrayList::new));
            for (int i = 0; i < ingredientSelection.size(); i++) {
                UserOutput.display(String.format("%d : %s", i, ingredientSelection.get(i).toString().replace("_", " ")));
            }
            UserOutput.display("-1 : Cancel");

            int selection;
            do {
                UserOutput.print("Please Select a Flavor: ");
                try {
                    selection = scanner.nextInt();
                } catch (Exception e){
                    selection = -777;
                }
                scanner.nextLine();
            } while (selection < -1 || selection >= ingredientSelection.size());
            if(selection != -1) return new Drink(ingredientSelection.get(selection).toString().replace("_", " "), size);
            return null;
        }
    }


    public static Chips promptChipsSelection(){
        UserOutput.print("\n".repeat(5));
        UserOutput.display("Chips - $1.5");

        ArrayList<ChipSelection> ingredientSelection = Arrays.stream(ChipSelection.values()).sorted(Comparator.comparing(Objects::toString)).collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < ingredientSelection.size(); i++) {
            UserOutput.display(String.format("%d : %s", i, ingredientSelection.get(i).toString().replace("_", " ")));
        }
        UserOutput.display("-1 : Cancel");

        int selection;
        do {
            UserOutput.print("Please Select a Flavor: ");
            try {
                selection = scanner.nextInt();
            } catch (Exception e){
                selection = -777;
            }
            scanner.nextLine();
        } while (selection < -1 || selection >= ingredientSelection.size());
        if(selection != -1) return new Chips(ingredientSelection.get(selection).toString().replace("_", " "));
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
            try {
                selection = scanner.nextInt();
            } catch (Exception e){
                selection = -777;
            }
            scanner.nextLine();
        }while (selection < -1 || selection >= items.size());
        return selection;
    }

    public static Scanner getScanner(){
        return scanner;
    }
}
