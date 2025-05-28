package com.pluralsight.app.item;

import com.pluralsight.app.ingredient.Ingredient;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        return  String.format("%s\n%s",super.toString(), ingredientSelections.stream()
                //Option to sort by Ingredient Price
                //.sorted(Comparator.comparing(ingredient -> ingredient.getPrice(size)))
                //Option to sort by Ingredient Name
                .sorted(Comparator.comparing(Object::toString))
                .map(ingredient -> String.format("\t%s - $%.2f\n", ingredient.toString().replace("_", " "), ingredient.getPrice(size))).collect(Collectors.joining()));
    }
}
