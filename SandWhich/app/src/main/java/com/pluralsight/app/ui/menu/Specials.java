package com.pluralsight.app.ui.menu;

import com.pluralsight.annotation.menu.Menu;
import com.pluralsight.annotation.menu.NextMenu;
import com.pluralsight.annotation.menu.WhiteSpace;
import com.pluralsight.annotation.menu.option.MenuOption;

@Menu @WhiteSpace(5)
public class Specials {

    @MenuOption(key = "SB", order = 1) @NextMenu(nextMenu = "OrderMenu")
    public static String smallBLT = "Small BLT";

    @MenuOption(key = "MB", order = 1) @NextMenu(nextMenu = "OrderMenu")
    public static String mediumBLT = "Medium BLT";

    @MenuOption(key = "LB", order = 1) @NextMenu(nextMenu = "OrderMenu")
    public static String largeBLT = "Large BLT";

    @MenuOption(key = "SP", order = 1) @NextMenu(nextMenu = "OrderMenu")
    public static String smallPhilly = "Small Philly";

    @MenuOption(key = "MP", order = 1) @NextMenu(nextMenu = "OrderMenu")
    public static String mediumPhilly = "Medium Philly";

    @MenuOption(key = "LP", order = 1) @NextMenu(nextMenu = "OrderMenu")
    public static String largePhilly = "Large Philly";
}
