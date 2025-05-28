package com.pluralsight.app.item;

import com.pluralsight.app.ingredient.Ingredient;


import java.util.ArrayList;

public final class Sandwich extends Item {
    private ArrayList<Ingredient> ingredientSelections;

    public Sandwich(String name, Size size, ArrayList<Ingredient> ingredients){

        super(name, size);
        ingredientSelections = ingredients;
    }

    @Override
    public double getPrice() {
        return ingredientSelections.stream().mapToDouble(value -> value.getPrice(this.size)).sum();
    }
}
