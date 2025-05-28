package com.pluralsight.app.ui;

import com.pluralsight.annotation.system.PrintOverride;
@PrintOverride
public final class UserOutput {

    public static void display(Object object){
        System.out.println(object);
    }
    public static void print(Object object){
        System.out.print(object);
    }
}
