package com.pluralsight.app.ui;

import com.pluralsight.annotation.system.PrintOverride;
@PrintOverride
public final class UserOutput {

    public static void display(Object object){
        print(object+"\n");
    }
    public static void print(Object object){
        //System.out.print("\u001B[37m\u001B[40m" + object); //Black BG & White
        //System.out.print("\u001B[30m\u001B[47m"+ object); //White BG & Black
        //System.out.print("\u001B[30m\u001B[44m"+object); //Blue BG & Black
        //System.out.print("\u001B[30m\u001B[41m"+object); //Regular BG & Black
        System.out.print(object);
    }
}
