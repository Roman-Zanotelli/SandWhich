package com.pluralsight.app.ingredient;

import com.pluralsight.app.item.Size;

public interface Ingredient {
    double getPrice(Size size);

    interface Premium {
        public enum Meat implements Ingredient{
            Steak, Ham, Salami, Roast_Beef, Chicken, Bacon;

            @Override
            public double getPrice(Size size) {
                return switch (size){
                    case Small -> 1;
                    case Medium -> 2;
                    case Large -> 3;
                };
            }
        }

        public enum Cheese implements Ingredient{
            American, Provolone, Cheddar, Swiss;

            @Override
            public double getPrice(Size size) {
                return switch (size){
                    case Small -> .75;
                    case Medium -> 1.50;
                    case Large -> 2.25;
                };
            }
        }
        public enum ExtraCheese implements Ingredient{
            American, Provolone, Cheddar, Swiss;

            @Override
            public double getPrice(Size size) {
                return switch (size){
                    case Small -> .3;
                    case Medium -> .6;
                    case Large -> .9;
                };
            }
        }
        public enum ExtraMeat implements Ingredient{
            Steak, Ham, Salami, Roast_Beef, Chicken, Bacon;

            @Override
            public double getPrice(Size size) {
                return switch (size){
                    case Small -> .5;
                    case Medium -> 1;
                    case Large -> 1.5;
                };
            }
        }

        public enum Bread implements Ingredient{
            White_Bread, Wheat_Bread, Rye_Bread, Wrap;

            @Override
            public double getPrice(Size size) {
                return switch (size){
                    case Small -> 5.50;
                    case Medium -> 7;
                    case Large -> 8.5;
                };
            }
        }
    }

    interface Included  {
        enum Sauce implements Ingredient {
            Mayo, Mustard, Ketchup, Ranch, Thousand_Island, Vinaigrette, Aus_Jus;

            @Override
            public double getPrice(Size size) {
                return 0;
            }
        }

        enum Topping  implements Ingredient{
            Lettuce, Peppers, Onions, Tomatoes, Jalapenos, Cucumbers, Pickles, Guacamole, Mushrooms;

            @Override
            public double getPrice(Size size) {
                return 0;
            }
        }

    }

}
