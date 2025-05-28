package com.pluralsight.app.ui;

import com.pluralsight.annotation.system.ScannerProducer;
import com.pluralsight.app.item.Chips;
import com.pluralsight.app.item.Drink;
import com.pluralsight.app.item.Sandwich;

import java.util.Scanner;

public final class UserInput {

    static Scanner scanner = new Scanner(System.in);
    public static Sandwich promptSandwichSelection(){
        //TODO
        return null;
    }


    public static Drink promptDrinkSelection(){
        //TODO
        return null;
    }


    public static Chips promptChipsSelection(){
        //TODO
        return null;
    }
    
    @ScannerProducer
    public static Scanner getScanner(){
        return scanner;
    }
}
