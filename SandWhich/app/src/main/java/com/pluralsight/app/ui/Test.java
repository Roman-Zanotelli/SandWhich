package com.pluralsight.app.ui;

import com.pluralsight.annotation.system.OnShutDown;
import com.pluralsight.annotation.system.OnStartUp;

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

}
