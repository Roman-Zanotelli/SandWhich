package com.pluralsight.app.ui.menu;

import com.pluralsight.annotation.menu.Menu;
import com.pluralsight.annotation.menu.WhiteSpace;
import com.pluralsight.annotation.menu.option.MenuOption;
import com.pluralsight.annotation.menu.NextMenu;
import com.pluralsight.annotation.menu.option.PressEnterToContinue;

@Menu @WhiteSpace(5)
public class CheckOutMenu {

    @PressEnterToContinue
    @MenuOption(key = "C", order = 0) @NextMenu(nextMenu = "HomeMenu")
    public static final String confirm_order = "Confirm Order";

    @PressEnterToContinue
    @MenuOption(key = "M", order = 1) @NextMenu(nextMenu = "OrderMenu")
    public static final String modify_order = "Modify Order";

    @PressEnterToContinue
    @MenuOption(key = "X", order = 2) @NextMenu(nextMenu = "HomeMenu")
    public static final String cancel_order = "Cancel Order";

}
