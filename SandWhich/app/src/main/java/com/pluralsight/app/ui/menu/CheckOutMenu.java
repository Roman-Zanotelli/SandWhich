package com.pluralsight.app.ui.menu;

import com.pluralsight.build.annotation.menu.Menu;
import com.pluralsight.build.annotation.menu.option.MenuOption;
import com.pluralsight.build.annotation.menu.NextMenu;

@Menu
public class CheckOutMenu {

    @MenuOption(key = "C", order = 0) @NextMenu(nextMenu = "HomeMenu")
    public static final String confirm_order = "Confirm Order";

    @MenuOption(key = "M", order = 1) @NextMenu(nextMenu = "OrderMenu")
    public static final String modify_order = "Modify Order";

    @MenuOption(key = "X", order = 2) @NextMenu(nextMenu = "HomeMenu")
    public static final String cancel_order = "Cancel Order";

}
