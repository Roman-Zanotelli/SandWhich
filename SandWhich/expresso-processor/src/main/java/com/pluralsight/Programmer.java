package com.pluralsight;

import com.pluralsight.annotation.system.OnStartUp;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Programmer {
    private Programmer(){}

    static ProcessingEnvironment processingEnv;
    static RoundEnvironment roundEnv;
    static ArrayList<String> mainImports;
    static HashMap<Double, List<String>> startUpWaves;
    static HashMap<Double, List<String>> shutDownWaves;

    static void init(ProcessingEnvironment processingEnv){
        Programmer.processingEnv = processingEnv;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Annotation Processor Triggered");
        mainImports = new ArrayList<>();
        startUpWaves = new HashMap<>();
    }

    static void plan (Set<? extends TypeElement> annotations, RoundEnvironment roundEnv){
        Programmer.roundEnv = roundEnv;
        //Handle OnStart Executables
//        {
//            roundEnv.getElementsAnnotatedWith(OnStartUp.class).stream()
//                    .filter(element -> element.getKind() == ElementKind.METHOD)
//                    .forEach(element -> planStartUp((ExecutableElement) element));
//        }
//        //Handle OnShutDown Executables
//        {
//            roundEnv.getElementsAnnotatedWith(OnShutDown.class).stream()
//                    .filter(element -> element.getKind() == ElementKind.METHOD)
//                    .sorted(Comparator.comparing(element -> element.getAnnotation(OnShutDown.class).wave()))
//                    .forEach(element -> planShutDown((ExecutableElement) element));
//        }


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
        TypeElement enclosingElement = (TypeElement) executableElement.getEnclosingElement();
        String pkg = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();
        String className = enclosingElement.getSimpleName().toString();
        String methodName = executableElement.getSimpleName().toString();
        startUpWaves
                .computeIfAbsent(executableElement.getAnnotation(OnStartUp.class).wave(), v -> new ArrayList<>())
                .add(className + "." + methodName + "();");
        mainImports.add("import "+pkg+"."+className+";");

    }
    private static void planShutDown(ExecutableElement executableElement){
//        shutDownWaves
//                .computeIfAbsent((int)executableElement.getAnnotation(OnShutDown.class).wave(), v -> new ArrayList<>())
//                .add(executableElement);
    }

    static void writeProgram(){
        writeMain();
    }

    private static void writeMain(){
        try {

            JavaFileObject file = processingEnv.getFiler().createSourceFile("com.pluralsight.generated.ExpressoMain");
            try(Writer writer = file.openWriter()){
                writer.append("package com.pluralsight.generated;");
                //Append imports needed at the top of the file
                mainImports.forEach(mainImport -> {
                    try {
                        writer.append(mainImport);
                    } catch (IOException e) {
                        //This shouldn't happen
                        throw new RuntimeException(e);
                    }
                });

                //Append the start of the main class/function
                writer.append("""
                    public class ExpressoMain {
                        public static void main(String[] args) {
                    """);

                //Todo: Append startup logic with negative values
                startUpWaves.entrySet().stream()
                        .filter(wave -> wave.getKey() < 0)
                        .sorted(Comparator.comparing(Map.Entry::getKey))
                        .forEach(wave -> {
                            wave.getValue().forEach(s -> {
                                try {
                                    writer.append(s);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        });
                //TODO: Add shutdown Hook
                writer.append("""
                                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        
                        """);
                writer.append("""
                                System.out.println("GoodBye From Expresso!!!");
                            }));
                        """);
                //TODO: Add normal start up logic

                //Append Test logic
                writer.append("""
                                System.out.println("Hello From Expresso!!!");
                        """);

                //Append the end of the main class
                writer.append("""
                        }
                    }
                    """);
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
}
