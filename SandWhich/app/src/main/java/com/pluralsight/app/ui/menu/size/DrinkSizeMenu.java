package com.pluralsight.app.ui.menu.size;

import com.pluralsight.annotation.menu.Menu;
import com.pluralsight.annotation.menu.NextMenu;
import com.pluralsight.annotation.menu.option.MenuOption;

@Menu
public class DrinkSizeMenu {
    @MenuOption(key = "S", order = 0) @NextMenu(nextMenu = "OrderMenu")
    public static String smallDrink = "Small";

    @MenuOption(key = "M", order = 1) @NextMenu(nextMenu = "OrderMenu")
    public static String mediumDrink = "Medium";

    @MenuOption(key = "L", order = 2) @NextMenu(nextMenu = "OrderMenu")
    public static String largeDrink = "Large";

    @MenuOption(key = "X", order = 3) @NextMenu(nextMenu = "OrderMenu")
    public static String cancel = "Cancel";
}
