package com.pluralsight.app.ui.menu.size;

import com.pluralsight.annotation.menu.Header;
import com.pluralsight.annotation.menu.Menu;
import com.pluralsight.annotation.menu.NextMenu;
import com.pluralsight.annotation.menu.WhiteSpace;
import com.pluralsight.annotation.menu.option.MenuOption;
import com.pluralsight.annotation.menu.option.PressEnterToContinue;

@Menu @WhiteSpace(5)
public class DrinkSizeMenu {
    @PressEnterToContinue
    @MenuOption(key = "S", order = 0) @NextMenu(nextMenu = "OrderMenu")
    public static String smallDrink = "Small";

    @PressEnterToContinue
    @MenuOption(key = "M", order = 1) @NextMenu(nextMenu = "OrderMenu")
    public static String mediumDrink = "Medium";

    @PressEnterToContinue
    @MenuOption(key = "L", order = 2) @NextMenu(nextMenu = "OrderMenu")
    public static String largeDrink = "Large";

    @PressEnterToContinue
    @MenuOption(key = "X", order = 3) @NextMenu(nextMenu = "OrderMenu")
    public static String cancel = "Cancel";

    @Header
    public static final String header = "--- Drink Size ---";
}
