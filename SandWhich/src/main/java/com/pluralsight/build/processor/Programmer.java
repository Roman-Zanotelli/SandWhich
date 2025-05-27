package com.pluralsight.build.processor;

import com.pluralsight.build.annotation.menu.Menu;
import com.pluralsight.build.annotation.system.OnShutDown;
import com.pluralsight.build.annotation.system.OnStartUp;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.*;

public class Programmer {
    private Programmer(){}
    static RoundEnvironment roundEnv;
    static HashMap<Integer, List<ExecutableElement>> startUpWaves;
    static HashMap<Integer, List<ExecutableElement>> shutDownWaves;

    static void plan (RoundEnvironment roundEnv){
        Programmer.roundEnv = roundEnv;

        //Handle OnStart Executables
        {
            roundEnv.getElementsAnnotatedWith(OnStartUp.class).stream()
                    .filter(element -> element.getKind() == ElementKind.METHOD)
                    .sorted(Comparator.comparing(element -> element.getAnnotation(OnStartUp.class).wave()))
                    .forEach(element -> planStartUp((ExecutableElement) element));
        }
        //Handle OnShutDown Executables
        {
            roundEnv.getElementsAnnotatedWith(OnShutDown.class).stream()
                    .filter(element -> element.getKind() == ElementKind.METHOD)
                    .sorted(Comparator.comparing(element -> element.getAnnotation(OnShutDown.class).wave()))
                    .forEach(element -> planShutDown((ExecutableElement) element));
        }
        //Load Main Menu
        {

//            //TODO this need redone to check for missing @Menu annotation
//            planMainMenu(
//                    (TypeElement) roundEnv.getElementsAnnotatedWith(Menu.class).stream()
//                    .filter(element -> element.getAnnotation(Menu.class).isMain())
//                    .findFirst()
//                    .get()
//            );
        }
        //TODO MENU
        //TODO MenuOption
        //TODO OnMenuLoad
        //TODO OnMenuOption
    }
    private static void planStartUp(ExecutableElement executableElement){
        startUpWaves
                .computeIfAbsent((int)executableElement.getAnnotation(OnStartUp.class).wave(), v -> new ArrayList<>())
                .add(executableElement);
    }
    private static void planShutDown(ExecutableElement executableElement){
        shutDownWaves
                .computeIfAbsent((int)executableElement.getAnnotation(OnShutDown.class).wave(), v -> new ArrayList<>())
                .add(executableElement);
    }

    static void writeProgram(){
        writeMain();
    }

    private static void writeMain(){
        ArrayList<String> mainImports = new ArrayList<>();
        //Load Main Path
        {

            TypeElement mainMenu = (TypeElement) roundEnv.getElementsAnnotatedWith(Menu.class).stream()
            .filter(element -> element.getAnnotation(Menu.class).isMain())
            .findFirst()
            .get();


        }

    }
}
