package com.pluralsight.app.ui.menu;


import com.pluralsight.annotation.menu.Menu;
import com.pluralsight.annotation.menu.SelectionPromt;
import com.pluralsight.annotation.menu.option.MenuOption;
import com.pluralsight.annotation.menu.NextMenu;

@Menu(isMain = true)
public class HomeMenu {

    @MenuOption(key = "1", order = 0) @NextMenu(nextMenu = "OrderMenu")
    public static final String new_order = "New Order";

    @MenuOption(key = "0", order = 1)
    public static final String exit = "Exit";

    @SelectionPromt
    public static final String prompt = "Please Enter Option >> ";
}
