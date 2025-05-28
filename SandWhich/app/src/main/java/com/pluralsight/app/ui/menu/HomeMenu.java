package com.pluralsight.app.ui.menu;


import com.pluralsight.annotation.menu.Menu;
import com.pluralsight.annotation.menu.SelectionPromt;
import com.pluralsight.annotation.menu.WhiteSpace;
import com.pluralsight.annotation.menu.option.MenuOption;
import com.pluralsight.annotation.menu.NextMenu;
import com.pluralsight.annotation.menu.option.PressEnterToContinue;

@Menu(isMain = true) @WhiteSpace(5)
public class HomeMenu {

    @PressEnterToContinue
    @MenuOption(key = "1", order = 0) @NextMenu(nextMenu = "OrderMenu")
    public static final String new_order = "New Order";

    @PressEnterToContinue
    @MenuOption(key = "0", order = 1)
    public static final String exit = "Exit";

    @SelectionPromt @PressEnterToContinue
    public static final String prompt = "Please Enter Option >> ";
}
