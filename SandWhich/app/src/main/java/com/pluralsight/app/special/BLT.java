package com.pluralsight.app.special;

import com.pluralsight.annotation.menu.option.OnOptionSelect;
import com.pluralsight.app.Cart;
import com.pluralsight.app.ingredient.Ingredient;
import com.pluralsight.app.item.Sandwich;
import com.pluralsight.app.item.Size;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BLT {

    @OnOptionSelect(menu = "Specials", option = "smallBLT")
    public static void addSmall(){
        Cart.add(new Sandwich("BLT Special", Size.Small, new ArrayList<>(List.of(Ingredient.Premium.Bread.White_Bread, Ingredient.Premium.Meat.Bacon, Ingredient.Premium.Cheese.Cheddar, Ingredient.Included.Topping.Lettuce, Ingredient.Included.Topping.Tomatoes)),true));
    }
    @OnOptionSelect(menu = "Specials", option = "mediumBLT")
    public static void addMedium(){
        Cart.add(new Sandwich("BLT Special", Size.Medium, new ArrayList<>(List.of(Ingredient.Premium.Bread.White_Bread, Ingredient.Premium.Meat.Bacon, Ingredient.Premium.Cheese.Cheddar, Ingredient.Included.Topping.Lettuce, Ingredient.Included.Topping.Tomatoes)),true));
    }
    @OnOptionSelect(menu = "Specials", option = "largeBLT")
    public static void addLarge(){
        Cart.add(new Sandwich("BLT Special", Size.Large, new ArrayList<>(List.of(Ingredient.Premium.Bread.White_Bread, Ingredient.Premium.Meat.Bacon, Ingredient.Premium.Cheese.Cheddar, Ingredient.Included.Topping.Lettuce, Ingredient.Included.Topping.Tomatoes)),true));
    }
}
