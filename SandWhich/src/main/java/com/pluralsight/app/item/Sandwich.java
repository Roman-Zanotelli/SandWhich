package com.pluralsight.app.item;

import com.pluralsight.app.ingredient.Ingredient;


import java.util.ArrayList;

public final class Sandwich extends Item {
    private ArrayList<Ingredient> ingredientSelections;

    public Sandwich(String name, ArrayList<Ingredient> ingredients){
        super(name, 0);
    }
}
