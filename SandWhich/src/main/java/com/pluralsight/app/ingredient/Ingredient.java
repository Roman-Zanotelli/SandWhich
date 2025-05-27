package com.pluralsight.app.ingredient;

import java.util.Arrays;

public final class Ingredient {
    private Ingredient(){}
    public interface IngredientSelection{}

    public static final class Premium {
        public enum Meat implements IngredientSelection{
            Steak, Ham, Salami, Roast_Beef, Chicken, Bacon;
        }

        enum Cheese implements IngredientSelection{
            American, Provolone, Cheddar, Swiss;
        }

        enum Bread implements IngredientSelection{
            White, Wheat, Rye, Wrap;
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
