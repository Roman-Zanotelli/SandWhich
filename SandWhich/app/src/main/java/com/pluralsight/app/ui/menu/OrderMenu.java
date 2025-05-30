package com.pluralsight.app.ui.menu;

import com.pluralsight.annotation.menu.Header;
import com.pluralsight.annotation.menu.Menu;
import com.pluralsight.annotation.menu.WhiteSpace;
import com.pluralsight.annotation.menu.option.MenuOption;
import com.pluralsight.annotation.menu.NextMenu;
import com.pluralsight.annotation.menu.option.PressEnterToContinue;

@Menu @WhiteSpace(5)
public class OrderMenu {


    @NextMenu(nextMenu = "SandwichSizeMenu")
    @MenuOption(key = "1", order = 0) @PressEnterToContinue
    public static final String add_sandwich = "Add Sandwich";

    @NextMenu(nextMenu = "DrinkSizeMenu")
    @MenuOption(key = "2", order = 1) @PressEnterToContinue
    public static final String add_drink = "Add Drink";

    @MenuOption(key = "3", order = 2) @PressEnterToContinue
    public static final String add_chips = "Add Chips";

    @MenuOption(key = "4", order = 3) @PressEnterToContinue
    public static final String remove_item = "Remove Item";

    @PressEnterToContinue
    @MenuOption(key = "C", order = 4) @NextMenu(nextMenu = "CheckOutMenu")
    public static final String checkout_cart = "Checkout";

    @PressEnterToContinue
    @MenuOption(key = "X", order = 5) @NextMenu(nextMenu = "HomeMenu")
    public static final String cancel_order = "Cancel Order";


    @Header
    public static final String header = "--- Order Menu ---";

    //@PressEnterToContinue
    //@MenuOption(key = "S", order = 3.1) @NextMenu(nextMenu = "Specials")
    public static final String add_special = "Add Specialty Sandwich";

}
