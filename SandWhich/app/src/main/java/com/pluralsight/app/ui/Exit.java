package com.pluralsight.app.ui;

import com.pluralsight.annotation.menu.option.OnOptionSelect;

public class Exit {
    @OnOptionSelect(menu = "HomeMenu", option = "exit")
    public static void sysExit(){
        System.exit(0);
    }
}
