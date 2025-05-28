package com.pluralsight.component;

import com.pluralsight.Program;
import com.pluralsight.annotation.menu.Menu;
import com.pluralsight.annotation.menu.OnMenuLoad;
import com.pluralsight.annotation.menu.OnMenuLoads;
import com.pluralsight.annotation.menu.option.MenuOption;
import com.pluralsight.annotation.menu.option.OnOptionSelect;
import com.pluralsight.annotation.menu.option.OnOptionSelects;
import com.pluralsight.annotation.system.OnStartUp;

import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.pluralsight.Program.processingEnv;
import static com.pluralsight.Program.roundEnv;

public class MenuGenerator {
    private MenuGenerator(){}

    public static String getMainMenu(){
        return roundEnv.getElementsAnnotatedWith(Menu.class).stream()
                .filter(element -> element.getAnnotation(Menu.class).isMain()).findFirst().get()
                .getSimpleName().toString();
    }

    public static ArrayList<String> getMenus(){
        return roundEnv.getElementsAnnotatedWith(Menu.class).stream()
                .map(element -> element.getSimpleName().toString())
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static ArrayList<String> getImports(){
        return roundEnv.getElementsAnnotatedWith(Menu.class).stream()
                .map(element -> "import com.pluralsight.generated.ExpressoGenerated"+ element.getSimpleName().toString() + ";")
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static void write(){
        roundEnv.getElementsAnnotatedWith(Menu.class).forEach(element -> writeMenu((TypeElement) element));
    }
    private static void writeMenu(TypeElement menuElement){
        try {
            JavaFileObject file = processingEnv.getFiler().createSourceFile("com.pluralsight.generated.ExpressoGenerated"+menuElement.getSimpleName().toString());
            ArrayList<String> menuImports = new ArrayList<>();
            //Load imports
            {
                //Load OnMenuLoad[s]
                {
                    //Repeated
                    roundEnv.getElementsAnnotatedWith(OnMenuLoads.class).stream()
                            .filter(
                                    //Filter annotations meant for this class
                                    element -> Arrays.stream(element.getAnnotationsByType(OnMenuLoad.class))
                                            .anyMatch(onMenuLoad -> onMenuLoad.menu().equals(menuElement.getSimpleName().toString()))
                            )
                            //Generate the import
                            .map(executableElement -> {
                                TypeElement enclosingElement = (TypeElement) executableElement.getEnclosingElement();
                                String pkg = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();
                                String className = enclosingElement.getSimpleName().toString();
                                return "import " + pkg + "." + className + ";";
                            }).filter(imp -> !menuImports.contains(imp)).forEach(menuImports::add);
                    //Single
                    roundEnv.getElementsAnnotatedWith(OnMenuLoad.class).stream()
                            .filter(
                                    //Filter annotations meant for this class
                                    element -> Arrays.stream(element.getAnnotationsByType(OnMenuLoad.class))
                                            .anyMatch(onMenuLoad -> onMenuLoad.menu().equals(menuElement.getSimpleName().toString()))
                            )
                            //Generate the import
                            .map(executableElement -> {
                                TypeElement enclosingElement = (TypeElement) executableElement.getEnclosingElement();
                                String pkg = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();
                                String className = enclosingElement.getSimpleName().toString();
                                return "import " + pkg + "." + className + ";";
                            }).filter(imp -> !menuImports.contains(imp)).forEach(menuImports::add);
                }

                //Load OnOptionSelect & OnOptionSelects
                {
                    //Repeated
                    roundEnv.getElementsAnnotatedWith(OnOptionSelects.class).stream()
                            .filter(
                                    //Filter annotations meant for this class
                                    element -> Arrays.stream(element.getAnnotationsByType(OnOptionSelect.class))
                                            .anyMatch(onMenuLoad -> onMenuLoad.menu().equals(menuElement.getSimpleName().toString()))
                            )
                            //Generate the import
                            .map(executableElement -> {
                                TypeElement enclosingElement = (TypeElement) executableElement.getEnclosingElement();
                                String pkg = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();
                                String className = enclosingElement.getSimpleName().toString();
                                return "import " + pkg + "." + className + ";";
                            }).filter(imp -> !menuImports.contains(imp)).forEach(menuImports::add);

                    //Single
                    roundEnv.getElementsAnnotatedWith(OnOptionSelect.class).stream()
                            .filter(
                                    //Filter annotations meant for this class
                                    element -> Arrays.stream(element.getAnnotationsByType(OnOptionSelect.class))
                                            .anyMatch(onMenuLoad -> onMenuLoad.menu().equals(menuElement.getSimpleName().toString()))
                            )
                            //Generate the import
                            .map(executableElement -> {
                                TypeElement enclosingElement = (TypeElement) executableElement.getEnclosingElement();
                                String pkg = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();
                                String className = enclosingElement.getSimpleName().toString();
                                return "import " + pkg + "." + className + ";";
                            }).filter(imp -> !menuImports.contains(imp)).forEach(menuImports::add);

                }
            }
            try(Writer writer = file.openWriter()){
                //generate package
                {
                    writer.append("""
                            package com.pluralsight.generated;
                            
                            """);
                }

                //Append imports needed at the top of the file
                {
                    menuImports.forEach(menuImport -> {
                        try {
                            writer.append(menuImport+"\n");
                        } catch (IOException e) {
                            //This shouldn't happen
                            //TODO: Add something for when it does
                            throw new RuntimeException(e);
                        }
                    });
                }

                //Append the start of the MenuClass
                {
                    writer.append("""
                            
                            public class ExpressoGenerated"""+menuElement.getSimpleName().toString()+"""
                            {
                                public static String load() {
                            
                            """);
                }
                //TODO: Append OnLoad
                {

                }
                //TODO: Scanner
                {
                    writer.append("\t\tString userSelection = \"default\";\n");
                }

                {
                    //generate the start of the switch
                    writer.append("""
                                    switch(userSelection){
                            """);

                    //Generate the cases
                    menuElement.getEnclosedElements().stream()
                            .filter(element -> element.getAnnotation(MenuOption.class) != null)
                            .forEach(element -> {
                                try {
                                    writer.append("\n\t\t\tcase \"").append(element.getAnnotation(MenuOption.class).key()).append("\" -> {\n");
                                    //TODO: ADD OnMenuSelect Functions Here
                                    writer.append("\n\t\t\t}\n");
                                } catch (IOException e) {
                                    //TODO
                                    throw new RuntimeException(e);
                                }
                            });

                    //Generate a default case
                    writer.append("\n\t\t\tdefault -> {\n");
                    writer.append("\n\t\t\t}\n");
                    //Close the switch
                    writer.append("""
                                
                                    }
                                
                            """);
                }
                //Append return statement
                {
                    writer.append("""
                                    return null;
                            """);
                }

                {
                    writer.append("""
                                }
                            }
                            """);
                }
            }
        } catch (IOException ignore) {

        }

    }
}
