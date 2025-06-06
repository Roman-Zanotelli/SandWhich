package com.pluralsight.app.special;

import com.pluralsight.annotation.menu.option.OnOptionSelect;
import com.pluralsight.app.Cart;
import com.pluralsight.app.ingredient.Ingredient;
import com.pluralsight.app.item.Sandwich;
import com.pluralsight.app.item.Size;

import java.util.ArrayList;
import java.util.List;

public class PhillyCheeseSteak {
    @OnOptionSelect(menu = "Specials", option = "smallPhilly")
    public static void addSmall(){
        Cart.add(new Sandwich("Philly Special", Size.Small, new ArrayList<>(List.of(Ingredient.Premium.Bread.White_Bread, Ingredient.Premium.Meat.Steak, Ingredient.Premium.Cheese.Provolone, Ingredient.Included.Topping.Peppers, Ingredient.Included.Topping.Jalapenos)),true));
    }
    @OnOptionSelect(menu = "Specials", option = "mediumPhilly")
    public static void addMedium(){
        Cart.add(new Sandwich("Philly Special", Size.Medium, new ArrayList<>(List.of(Ingredient.Premium.Bread.White_Bread, Ingredient.Premium.Meat.Steak, Ingredient.Premium.Cheese.Provolone, Ingredient.Included.Topping.Peppers, Ingredient.Included.Topping.Jalapenos)),true));
    }
    @OnOptionSelect(menu = "Specials", option = "largePhilly")
    public static void addLarge(){
        Cart.add(new Sandwich("Philly Special", Size.Large, new ArrayList<>(List.of(Ingredient.Premium.Bread.White_Bread, Ingredient.Premium.Meat.Steak, Ingredient.Premium.Cheese.Provolone, Ingredient.Included.Topping.Peppers, Ingredient.Included.Topping.Jalapenos)),true));
    }
}
