package com.pluralsight.app.ui;

import com.pluralsight.annotation.system.PrintOverride;

public final class UserOutput {
    @PrintOverride
    public static void display(Object object){
        System.out.println(object);
    }
}
