package com.pluralsight.app.ui.menu.size;

import com.pluralsight.annotation.menu.Menu;
import com.pluralsight.annotation.menu.NextMenu;
import com.pluralsight.annotation.menu.option.MenuOption;

@Menu
public class SandwichSizeMenu {
    @MenuOption(key = "S", order = 0) @NextMenu(nextMenu = "OrderMenu")
    public static String smallSandwich = "Small";

    @MenuOption(key = "M", order = 1) @NextMenu(nextMenu = "OrderMenu")
    public static String mediumSandwich = "Medium";

    @MenuOption(key = "L", order = 2) @NextMenu(nextMenu = "OrderMenu")
    public static String largeSandwich = "Large";

    @MenuOption(key = "X", order = 3) @NextMenu(nextMenu = "OrderMenu")
    public static String cancel = "Cancel";
}
