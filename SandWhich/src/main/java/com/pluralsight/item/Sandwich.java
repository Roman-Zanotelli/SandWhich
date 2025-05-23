package com.pluralsight.item;

import com.pluralsight.ingredient.Ingredient;


import java.util.ArrayList;
import java.util.EnumMap;

public final class Sandwich extends Item {
    private ArrayList<Ingredient> ingredientSelections;

    public Sandwich(String name, ArrayList<Ingredient> ingredients){
        super(name, 0);
    }
}
