package com.pluralsight.app.item;

import com.pluralsight.app.ingredient.Ingredient;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public final class Sandwich extends Item {
    private ArrayList<Ingredient> ingredientSelections;
    private boolean isToasted;

    public Sandwich(String name, Size size, ArrayList<Ingredient> ingredients, boolean toasted){
        super(name, size);
        ingredientSelections = ingredients;
        this.isToasted = toasted;
    }

    @Override
    public double getPrice() {
        return ingredientSelections.stream().mapToDouble(value -> value.getPrice(this.size)).sum();
    }

    @Override
    public String toString() {
        return  String.format("%s%s\n%s", isToasted ? "Toasted " :"", super.toString(), ingredientSelections.stream()
                //Option to sort by Ingredient Price
                //.sorted(Comparator.comparing(ingredient -> ingredient.getPrice(size)))
                //Option to sort by Ingredient Name
                .sorted(Comparator.comparing(Object::toString))
                .map(ingredient -> String.format("\t%s - $%.2f\n", ingredient.toString().replace("_", " "), ingredient.getPrice(size))).collect(Collectors.joining()));
    }

    public void removeCheese(String cheese){
        if(!ingredientSelections.removeIf(ingredient -> ingredient instanceof Ingredient.Premium.ExtraCheese && ingredient.toString().equals(cheese))){
            if(ingredientSelections.removeIf(ingredient -> ingredient instanceof Ingredient.Premium.Cheese && ingredient.toString().equals(cheese))){
                Ingredient.Premium.Cheese swap = ingredientSelections.stream().filter(ingredient -> ingredient instanceof Ingredient.Premium.ExtraCheese).findFirst().map(ingredient -> Ingredient.Premium.Cheese.valueOf(ingredient.toString())).orElse(null);
                if(swap != null) {
                    //Swap
                    ingredientSelections.add(swap);
                    ingredientSelections.remove(Ingredient.Premium.ExtraCheese.valueOf(swap.toString()));
                }
            }
        }
    }
    public void removeMeat(String meat){
        if(!ingredientSelections.removeIf(ingredient -> ingredient instanceof Ingredient.Premium.ExtraMeat && ingredient.toString().equals(meat))){
            if(ingredientSelections.removeIf(ingredient -> ingredient instanceof Ingredient.Premium.Meat && ingredient.toString().equals(meat))){
                Ingredient.Premium.Meat swap = ingredientSelections.stream().filter(ingredient -> ingredient instanceof Ingredient.Premium.ExtraMeat).findFirst().map(ingredient -> Ingredient.Premium.Meat.valueOf(ingredient.toString())).orElse(null);
                if(swap != null) {
                    //Swap
                    ingredientSelections.add(swap);
                    ingredientSelections.remove(Ingredient.Premium.ExtraMeat.valueOf(swap.toString()));
                }
            }
        }
    }

    public void removeIngredient(Ingredient ingredient){
        ingredientSelections.remove(ingredient);
    }
    public boolean hasBread(){
        return ingredientSelections.stream().anyMatch(ingredient -> ingredient instanceof Ingredient.Premium.Bread);
    }
    public boolean hasMeat(){
        return ingredientSelections.stream().anyMatch(ingredient -> ingredient instanceof Ingredient.Premium.Meat);
    }
    public boolean hasCheese(){
        return ingredientSelections.stream().anyMatch(ingredient -> ingredient instanceof Ingredient.Premium.Cheese);
    }
}
