package com.pluralsight.component;

import com.pluralsight.annotation.menu.Menu;
import com.pluralsight.annotation.menu.OnMenuLoad;
import com.pluralsight.annotation.menu.OnMenuLoads;
import com.pluralsight.annotation.menu.SelectionPromt;
import com.pluralsight.annotation.menu.option.MenuOption;
import com.pluralsight.annotation.menu.option.OnOptionSelect;
import com.pluralsight.annotation.menu.option.OnOptionSelects;
import com.pluralsight.annotation.system.PrintOverride;
import com.pluralsight.annotation.system.ScannerProducer;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
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
            AtomicBoolean usingScannerProducer = new AtomicBoolean(false);
            AtomicBoolean usingPrintOverride = new AtomicBoolean(false);
            //Load imports
            {
                menuImports.add("import java.util.Scanner;");
                menuImports.add("import java.util.ArrayList;");
                menuImports.add("import java.util.List;");
                //ScannerProducer
                {
                    roundEnv.getElementsAnnotatedWith(ScannerProducer.class).stream()
                            .findFirst()
                            .ifPresent(producerElement -> {
                                String pkg = processingEnv.getElementUtils().getPackageOf(producerElement).toString();
                                String className = producerElement.getSimpleName().toString();
                                String imp = "import " + pkg + "." + className + ";";
                                if (!menuImports.contains(imp)) menuImports.add(imp);
                                usingScannerProducer.set(true);
                    });
                    //If a scanner doesn't exist we generate our own helper class
                    if(!usingScannerProducer.get()){
                        ScannerGenerator.write();
                        menuImports.add("com.pluralsight.generated.ExpressoScanner");
                    }
                }
                //PrintOverride
                {
                    roundEnv.getElementsAnnotatedWith(PrintOverride.class).stream()
                            .findFirst()
                            .ifPresent(printOverrideElement -> {
                                String pkg = processingEnv.getElementUtils().getPackageOf(printOverrideElement).toString();
                                String className = printOverrideElement.getSimpleName().toString();
                                String imp = "import " + pkg + "." + className + ";";
                                if (!menuImports.contains(imp)) menuImports.add(imp);
                                usingPrintOverride.set(true);
                            });
                }
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
            //Start Generating the file
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
                    String pkg = processingEnv.getElementUtils().getPackageOf(menuElement).toString();
                    String className = menuElement.getSimpleName().toString();
                   writer.append("import " + pkg + "." + className + ";\n");
                }

                //Append the start of the MenuClass
                {
                    writer.append("""
                            
                            public class ExpressoGenerated"""+menuElement.getSimpleName().toString()+"""
                            {
                                public static String load() {
                            
                            """);
                }
                //Display Menu
                {
                    menuElement.getEnclosedElements().stream()
                            .filter(element -> element.getAnnotation(MenuOption.class) != null)
                            .sorted(Comparator.comparing(element -> element.getAnnotation(MenuOption.class).order()))
                            .forEach(element -> {
                                try {

                                    //Decide which print function to use
                                    writer.append("\t\t").append(usingPrintOverride.get() ? roundEnv.getElementsAnnotatedWith(PrintOverride.class).stream().findFirst().get().getSimpleName().toString():"System.out");
                                    //Invoke
                                    MenuOption option = element.getAnnotation(MenuOption.class);
                                    writer.append(".print(\"")
                                            .append(option.key() + option.delimiter())
                                            .append("\" + ")
                                            .append(menuElement.getSimpleName().toString()+"."+element.getSimpleName().toString())
                                            .append("+\"\\n\");\n");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            });
                }
                //Scanner
                {
                    //Get the scanner
                    writer.append("\t\tScanner scanner = "
                            + (usingScannerProducer.get() ?
                            roundEnv.getElementsAnnotatedWith(ScannerProducer.class).stream().findFirst().get().getSimpleName() : "ExpressoScanner")
                            + ".getScanner();\n");

                    //Generate a list of accepted inputs
                    writer.append(menuElement.getEnclosedElements().stream()
                            .filter(element -> element.getAnnotation(MenuOption.class) != null)
                            .map(element -> element.getAnnotation(MenuOption.class).key())
                            .distinct().collect(Collectors.joining("\", \"", "\t\tArrayList<String> acceptedInputs = new ArrayList<>(List.of(\"", "\"));\n")));


                    //Initialize userSelection
                    writer.append("\t\tString userSelection = \"\";\n");

                    //Write do while loop
                    writer.append("\n\t\tdo {\n\t\t\t");
                    //Decide which print function to use
                    writer.append(usingPrintOverride.get() ? roundEnv.getElementsAnnotatedWith(PrintOverride.class).stream().findFirst().get().getSimpleName().toString():"System.out");
                    //Invoke
                    writer.append(".print(");
                    //Load selection prompt or default
                    writer.append(menuElement.getEnclosedElements().stream()
                            .filter(element -> element.getAnnotation(SelectionPromt.class) != null)
                            .findFirst().map(element -> menuElement.getSimpleName().toString()+"."+element.getSimpleName().toString()+"")
                            .orElse("\"Enter Selection: \""));
                    //Close Invocation and finish do while loop
                    writer.append(");\n\t\t\tuserSelection = scanner.nextLine();\n\t\t} while(!acceptedInputs.contains(userSelection));\n\n");
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
                    writer.append("\n\t\t\tdefault -> {\n\n\t\t\t}\n");
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
