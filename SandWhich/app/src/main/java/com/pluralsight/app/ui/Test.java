package com.pluralsight.app.ui;

import com.pluralsight.annotation.menu.OnMenuLoad;
import com.pluralsight.annotation.menu.option.OnOptionSelect;
import com.pluralsight.annotation.system.OnShutDown;
import com.pluralsight.annotation.system.OnStartUp;

import java.util.ArrayList;
import java.util.Arrays;

@Deprecated
public class Test {

    @OnStartUp(wave = -1)
    public static void testStart(){
        String test = "";
        switch (test){
            case "blah" -> test = "";
            default -> test = "blah";
        }
        System.out.println("Test Start");
    }
    @OnStartUp(wave = 1)
    public static void testStart2(){
        System.out.println("Test Start 2");
    }

    @OnShutDown
    public static void testShutdown(){
        System.out.println("Test Shutdown");
    }

    //@OnMenuLoad(menu = "CheckOutMenu")
    public static void testImport(){

    }
    public static void testt(){
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList("a", "b", "c"));
    }


    public static void hahahahahahahahahaha(){
        UserOutput.display("ahahahahahahahahahahahahahahahahahahahaha");
    }

    public static void hahahahahahahahahaha2(){
        UserOutput.display("ahahahahahahahahahahahahahahahahahahahaha");
    }

    public static void hahahahahahahahahaha3(){
        UserOutput.display("ahahahahahahahahahahahahahahahahahahahaha");
    }


}
