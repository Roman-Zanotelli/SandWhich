package com.pluralsight;

import com.pluralsight.annotation.system.OnShutDown;
import com.pluralsight.app.ui.UserOutput;

import javax.print.attribute.standard.Severity;

public class Logging {
    public static void log(Severity severity, Object... objects){
        //TODO
        // This is one of the last things I plan to implement if at all
    }
    @OnShutDown
    public static void goodBye(){
        UserOutput.display("GoodBye! :^)");
    }
}
