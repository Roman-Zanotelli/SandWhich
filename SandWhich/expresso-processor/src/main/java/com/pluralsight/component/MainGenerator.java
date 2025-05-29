package com.pluralsight.component;

import com.pluralsight.annotation.system.OnShutDown;
import com.pluralsight.annotation.system.OnStartUp;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

import static com.pluralsight.Program.*;

public class MainGenerator {
    private MainGenerator(){}
    static HashMap<Double, List<String>> startUpWaves;
    static HashMap<Double, List<String>> shutDownWaves;
    static ArrayList<String> mainImports;

    public static void init(){
        startUpWaves = new HashMap<>();
        shutDownWaves = new HashMap<>();
        mainImports = new ArrayList<>();
    }
    public static void plan(){
        {
            roundEnv.getElementsAnnotatedWith(OnStartUp.class).stream()
                    .filter(element -> element.getKind() == ElementKind.METHOD)
                    .forEach(element -> planStartUp((ExecutableElement) element));
        }
        //Handle OnShutDown Executables
        {
            roundEnv.getElementsAnnotatedWith(OnShutDown.class).stream()
                    .filter(element -> element.getKind() == ElementKind.METHOD)
                    .sorted(Comparator.comparing(element -> element.getAnnotation(OnShutDown.class).wave()))
                    .forEach(element -> planShutDown((ExecutableElement) element));
        }
        MenuGenerator.getImports().stream().filter(imp -> !mainImports.contains(imp)).forEach(imp -> mainImports.add(imp));
    }


    private static void planStartUp(ExecutableElement executableElement){
        TypeElement enclosingElement = (TypeElement) executableElement.getEnclosingElement();
        String pkg = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();
        String className = enclosingElement.getSimpleName().toString();
        String methodName = executableElement.getSimpleName().toString();
        startUpWaves
                .computeIfAbsent(executableElement.getAnnotation(OnStartUp.class).wave(), v -> new ArrayList<>())
                .add(className + "." + methodName + "();");
        String qualifiedImport = "import "+pkg+"."+className+";";
        if(!mainImports.contains(qualifiedImport)) mainImports.add(qualifiedImport);

    }
    private static void planShutDown(ExecutableElement executableElement){
        TypeElement enclosingElement = (TypeElement) executableElement.getEnclosingElement();
        String pkg = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();
        String className = enclosingElement.getSimpleName().toString();
        String methodName = executableElement.getSimpleName().toString();
        shutDownWaves
                .computeIfAbsent(executableElement.getAnnotation(OnShutDown.class).wave(), v -> new ArrayList<>())
                .add(className + "." + methodName + "();");
        String qualifiedImport = "import "+pkg+"."+className+";";
        if(!mainImports.contains(qualifiedImport)) mainImports.add(qualifiedImport);
    }

    public static void write(){
        try {

            //Create the main file
            JavaFileObject file = processingEnv.getFiler().createSourceFile("com.pluralsight.generated.ExpressoMain");

            //Open a writer
            try(Writer writer = file.openWriter()){
                    writer.append(String.format("""
                            package com.pluralsight.generated;
                            \s
                            //Auto Generated Imports
                            %s
                            \s
                            //Auto Generated Main File :)
                            public class ExpressoMain {
                            \s
                                public static void main(String[] args) {
                            \s
                                    //Auto-Generated @OnStartUp(wave < 0) Elements
                            %s
                            \s
                                    //ShutDownHook
                                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                            \s
                                    //Auto-Generated @OnShutDown Elements
                            %s
                                    }));
                                    \s
                                    //Auto-Generated @OnStartUp(wave >= 0) Elements
                            %s
                                    \s
                                    //Auto-Generated Default to First Found @Menu(isMain = true)
                                    String currentMenu = "%s";
                                    while(true){
                                        switch(currentMenu){
                                        \s
                                            //Auto-Generated Menu-Cases
                            %s
                                            //Default to MainMenu
                            %s
                                        }
                                    }
                                }
                            }
                            """,
                            //Main Imports
                            mainImports.stream().map(s -> s+"\n").collect(Collectors.joining()),
                            //StartUp Waves < 0
                            startUpWaves.entrySet().stream()
                                    .filter(wave -> wave.getKey() < 0)
                                    .sorted(Map.Entry.comparingByKey())
                                    .map(wave -> wave.getValue().stream().map(s -> "\t\t"+s+"\n").collect(Collectors.joining()))
                                    .collect(Collectors.joining()),
                            //ShutDown Waves
                            shutDownWaves.entrySet().stream()
                                    .sorted(Map.Entry.comparingByKey())
                                    .map(wave -> wave.getValue().stream().map(s -> "\t\t\t" + s + "\n").collect(Collectors.joining()))
                                    .collect(Collectors.joining()),
                            //StartUp Waves >= 0
                            startUpWaves.entrySet().stream()
                                    .filter(wave -> wave.getKey() >= 0)
                                    .sorted(Map.Entry.comparingByKey())
                                    .map(wave -> wave.getValue().stream().map(s -> "\t\t" + s + "\n").collect(Collectors.joining()))
                                    .collect(Collectors.joining()),
                            //Start at MainMenu
                            MenuGenerator.getMainMenu(),
                            //Generate Cases for each Registered Menu
                            MenuGenerator.getMenus().stream().map(menu ->
                                String.format("""
                                                        case "%s" -> {
                                                            String res = ExpressoGenerated%s.load();
                                                            currentMenu = (res != null) ? res : currentMenu;
                                                        }
                                        """, menu, menu)
                            ).collect(Collectors.joining()),
                            //Default Case back to MainMenu
                            String.format("\t\t\t\tdefault -> currentMenu = \"%s\";\t", MenuGenerator.getMainMenu())
                    ));

            }
        } catch (IOException ignored) {

        }
    }
}
