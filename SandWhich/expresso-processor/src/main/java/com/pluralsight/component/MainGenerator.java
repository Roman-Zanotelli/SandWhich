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


    public static void writeV2(){
        try {

            //Create the main file
            JavaFileObject file = processingEnv.getFiler().createSourceFile("com.pluralsight.generated.ExpressoMain");

            //Open a writer
            try(Writer writer = file.openWriter()){

                //Append the package of the main class
                {
                    writer.append("""
                            package com.pluralsight.generated;
                            
                            """);
                }

                //Append imports needed at the top of the file
                {
                mainImports.forEach(mainImport -> {
                    try {
                        writer.append(mainImport+"\n");
                    } catch (IOException e) {
                        //This shouldn't happen
                        //TODO: Add something for when it does
                        throw new RuntimeException(e);
                    }
                });
                }

                //Append the start of the main class/function
                {
                    writer.append("""
                            
                            public class ExpressoMain {
                                public static void main(String[] args) {
                            
                            """);
                }

                //Append start up logic with negative wave (Before shutdown hook)
                {
                    startUpWaves.entrySet().stream()
                            .filter(wave -> wave.getKey() < 0)
                            .sorted(Map.Entry.comparingByKey())
                            .forEach(wave -> wave.getValue().forEach(s -> {
                                try {
                                    writer.append("\t\t" + s + "\n");
                                } catch (IOException e) {
                                    //This shouldn't happen
                                    //TODO: Add something for when it does
                                    throw new RuntimeException(e);
                                }
                            }));
                }

                //Shut Down Hook
                {
                    writer.append("""
                            
                                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                            
                            """);
                    shutDownWaves.entrySet().stream()
                            .sorted(Map.Entry.comparingByKey()).forEach(wave -> wave.getValue().forEach(s -> {
                                try {
                                    writer.append("\t\t\t" + s + "\n");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }));
                    writer.append("""
                            
                                    }));
                            
                            """);
                }

                //Normal Start up Logic
                {
                    startUpWaves.entrySet().stream()
                            .filter(wave -> wave.getKey() >= 0)
                            .sorted(Map.Entry.comparingByKey()).forEach(wave -> wave.getValue().forEach(s -> {
                                try {
                                    writer.append("\t\t" + s + "\n");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }));
                }

                //MenuLoop
                {
                    //Set the initial menu to the menu marked isMain
                    writer.append("\t\tString currentMenu = \"").append(MenuGenerator.getMainMenu()).append("\";\n");

                    //Generate while loop with switch case on current Menu
                    writer.append("""
                            
                                    while(true){
                                        switch(currentMenu){
                                        
                            """);

                    MenuGenerator.getMenus().forEach(menu -> {
                        try {
                            //Generates "case GeneratedMenuClass -> {" for each generated menu class
                            writer.append("\t\t\t\tcase \"").append(menu).append("\" -> {\n\t\t\t\t\t")
                                    // Generates "String res = GeneratedMenuClass.load();" for each
                                    .append("String res = ExpressoGenerated").append(menu).append(".load();\n\t\t\t\t\t")
                                    //Generates code that sets the current menu to the next menu (if not null)
                                    .append("currentMenu = (res != null) ? res : currentMenu;")
                                    //Generates the closing "}" to the case
                                    .append("\n\t\t\t\t}\n\n");
                        } catch (IOException e) {
                            //This shouldn't happen
                            //TODO: Add something for when it does
                            throw new RuntimeException(e);
                        }
                    });

                    //Generate code to default back to the main menu
                    writer.append("\t\t\t\t").append("default -> currentMenu = \"").append(MenuGenerator.getMainMenu()).append("\";\n");

                    //Close while loop and switch case
                    writer.append("""
                                        
                                        }
                                    }
                                    
                            """);
                }

                //Append the end of the main class
                {
                    writer.append("""
                                }
                            }
                            """);
                }
            }
        } catch (IOException ignored) {

        }
//        ArrayList<String> mainImports = new ArrayList<>();
//        //Load Main Path
//        {
//            TypeElement mainMenu = (TypeElement) roundEnv.getElementsAnnotatedWith(Menu.class).stream()
//            .filter(element -> element.getAnnotation(Menu.class).isMain())
//            .findFirst()
//            .get();
//
//        }

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
                            %s
                           \s
                            public class ExpressoMain {
                           \s
                                public static void main(String[] args) {
                           \s
                            %s
                           \s
                                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                           \s
                            %s
                           \s
                                    }));
                                   \s
                            %s
                                   \s
                                    String currentMenu = "%s";
                                    while(true){
                                        switch(currentMenu){
                                       \s
                            %s
                            %s
                                        }
                                    }
                                }
                            }         
                           """,
                            mainImports.stream().map(s -> s+"\n").collect(Collectors.joining()),
                            startUpWaves.entrySet().stream()
                                    .filter(wave -> wave.getKey() < 0)
                                    .sorted(Map.Entry.comparingByKey())
                                    .map(wave -> wave.getValue().stream().map(s -> "\t\t"+s+"\n").collect(Collectors.joining()))
                                    .collect(Collectors.joining()),
                            shutDownWaves.entrySet().stream()
                                    .sorted(Map.Entry.comparingByKey())
                                    .map(wave -> wave.getValue().stream().map(s -> "\t\t\t" + s + "\n").collect(Collectors.joining()))
                                    .collect(Collectors.joining()),
                            startUpWaves.entrySet().stream()
                                    .filter(wave -> wave.getKey() >= 0)
                                    .sorted(Map.Entry.comparingByKey())
                                    .map(wave -> wave.getValue().stream().map(s -> "\t\t" + s + "\n").collect(Collectors.joining()))
                                    .collect(Collectors.joining()),
                            MenuGenerator.getMainMenu(),
                            MenuGenerator.getMenus().stream().map(menu ->
                                String.format("""
                                                        case "%s" -> {
                                                            String res = ExpressoGenerated%s.load();
                                                            currentMenu = (res != null) ? res : currentMenu;
                                                        }
                                                        
                                        """, menu, menu)
                            ).collect(Collectors.joining()),
                            String.format("""
                                                    default -> currentMenu = "%s";
                                    """, MenuGenerator.getMainMenu())



                    ));

            }
        } catch (IOException ignored) {

        }
    }
}
