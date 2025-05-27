package com.pluralsight.app.ui.menu;

import com.pluralsight.build.annotation.menu.Menu;
import com.pluralsight.build.annotation.menu.option.MenuOption;
import com.pluralsight.build.annotation.menu.NextMenu;

@Menu
public class OrderMenu {

    @MenuOption(key = "1", order = 0)
    public static final String add_sandwich = "Add Sandwich";

    @MenuOption(key = "2", order = 1)
    public static final String add_drink = "Add Drink";

    @MenuOption(key = "3", order = 2)
    public static final String add_chips = "Add Chips";

    @MenuOption(key = "4", order = 3)
    public static final String remove_item = "Remove Item";

    @MenuOption(key = "5", order = 4) @NextMenu(nextMenu = "CheckOutMenu")
    public static final String checkout_cart = "Checkout";

    @MenuOption(key = "0", order = 5) @NextMenu(nextMenu = "HomeMenu")
    public static final String cancel_order = "Cancel Order";

}
