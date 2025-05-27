package com.pluralsight.app.ui;

import com.pluralsight.build.annotation.system.OnShutDown;
import com.pluralsight.build.annotation.system.OnStartUp;

public class Test {

    @OnStartUp
    public static void testStart(){
        System.out.println("Test Start");
    }

    @OnShutDown
    public static void testShutdown(){
        System.out.println("Test Shutdown");
    }

}
