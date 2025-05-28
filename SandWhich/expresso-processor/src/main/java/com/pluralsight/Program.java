package com.pluralsight;

import com.pluralsight.component.MainGenerator;
import com.pluralsight.component.MenuGenerator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.*;


public class Program {
    private Program(){}

    public static ProcessingEnvironment processingEnv;
    public static RoundEnvironment roundEnv;

    static void init(ProcessingEnvironment processingEnv){
        Program.processingEnv = processingEnv;
        MainGenerator.init();

    }

    static void plan (Set<? extends TypeElement> annotations, RoundEnvironment roundEnv){
        Program.roundEnv = roundEnv;
        MainGenerator.plan();
    }

    static void writeProgram(){
        MainGenerator.write();
        MenuGenerator.write();
    }

}
