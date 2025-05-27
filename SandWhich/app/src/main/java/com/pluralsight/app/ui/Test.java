package com.pluralsight.app.ui;

import com.pluralsight.annotation.system.OnShutDown;
import com.pluralsight.annotation.system.OnStartUp;

@Deprecated
public class Test {

    @OnStartUp(wave = -1)
    public static void testStart(){
        System.out.println("Test Start");
    }

    @OnShutDown
    public static void testShutdown(){
        System.out.println("Test Shutdown");
    }

}
