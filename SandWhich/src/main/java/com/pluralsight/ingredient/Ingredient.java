package com.pluralsight.ingredient;

import java.util.Arrays;

public final class Ingredient {
    private Ingredient(){}

    private IngredientSelection selection;

    public<Selection extends Enum<?> & IngredientSelection> Ingredient as(Selection selection){
        this.selection = selection;
        return this;
    }

    public double getPrice(){
        return selection.getPrice();
    }



    //Ingredient Selection Logic bellow


    //Interface handling most of the default enum logic
    private interface IngredientSelection{
        default double getPrice(){
            return 0;
        }
        default String[] getOptions() {
            return (String[]) Arrays.stream(this.getClass().getEnumConstants()).map(ingredient ->((Enum<?>) ingredient).name().replaceAll("_", " ")).toArray();
        }
    }


    public interface Premium {
        enum Meat implements IngredientSelection{
            Steak, Ham, Salami, Roast_Beef, Chicken, Bacon;
            public double getPrice() {
                //TODO
                return -1;
            }
        }

        enum Cheese implements IngredientSelection{
            American, Provolone, Cheddar, Swiss;

            @Override
            public double getPrice() {
                //TODO
                return -1;
            }
        }

        enum Bread implements IngredientSelection{
            White, Wheat, Rye, Wrap;
            @Override
            public double getPrice() {
                //TODO
                return -1;
            }
        }
    }

    public interface Included  {
        enum Sauce implements IngredientSelection {
            Mayo, Mustard, Ketchup, Ranch, Thousand_Island, Vinaigrette, Aus_Jus;
        }

        enum Topping  implements IngredientSelection{
            Lettuce, Peppers, Onions, Tomatoes, Jalapenos, Cucumbers, Pickles, Guacamole, Mushrooms;
        }

    }

}
