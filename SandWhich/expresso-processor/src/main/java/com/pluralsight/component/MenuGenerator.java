package com.pluralsight.component;

import com.pluralsight.annotation.menu.*;
import com.pluralsight.annotation.menu.option.MenuOption;
import com.pluralsight.annotation.menu.option.OnOptionSelect;
import com.pluralsight.annotation.menu.option.OnOptionSelects;
import com.pluralsight.annotation.menu.option.PressEnterToContinue;
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

            String menuName = menuElement.getSimpleName().toString();
            JavaFileObject file = processingEnv.getFiler().createSourceFile("com.pluralsight.generated.ExpressoGenerated"+menuName);
            ArrayList<String> menuImports = new ArrayList<>();
            AtomicBoolean usingScannerProducer = new AtomicBoolean(false);
            AtomicBoolean usingPrintOverride = new AtomicBoolean(false);
            String printOverride = null;

            //Load imports
            {
                //Required
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
                                            .anyMatch(onMenuLoad -> onMenuLoad.menu().equals(menuName))
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
                                            .anyMatch(onMenuLoad -> onMenuLoad.menu().equals(menuName))
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
                                            .anyMatch(onMenuLoad -> onMenuLoad.menu().equals(menuName))
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
                                            .anyMatch(onMenuLoad -> onMenuLoad.menu().equals(menuName))
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
                    String className = menuName;
                   writer.append("import " + pkg + "." + className + ";\n");
                }

                //Append the start of the MenuClass
                {
                    writer.append("""
                            
                            public class ExpressoGenerated"""+menuName+"""
                            {
                                public static String load() {
                            
                            """);
                }
                //Whitespace
                {
                    WhiteSpace ws = menuElement.getAnnotation(WhiteSpace.class);
                    if(ws != null){
                        //Decide which print function to use
                        writer.append("\t\t").append(usingPrintOverride.get() ? roundEnv.getElementsAnnotatedWith(PrintOverride.class).stream().findFirst().get().getSimpleName().toString():"System.out");
                        //Invoke
                        writer.append(".print(\"\\n\".repeat(");
                        writer.append(String.valueOf(ws.value()));
                        writer.append("));");
                    }
                }
                //OnMenuLoad
                {
                    //Repeated
                    roundEnv.getElementsAnnotatedWith(OnMenuLoads.class).stream()
                            //Filter annotations relevant to menu
                            .filter(element -> Arrays.stream(element.getAnnotation(OnMenuLoads.class).value()).anyMatch(onMenuLoad -> onMenuLoad.menu().equals(menuName)))
                            //Sort by menu wave
                            .sorted(Comparator.comparing(element -> Arrays.stream(element.getAnnotation(OnMenuLoads.class).value()).filter(onMenuLoad -> onMenuLoad.menu().equals(menuName)).findFirst().get().wave()))
                            //generate each
                            .forEach(onLoadElement -> {
                                try {
                                    writer.append("\n\t\t").append(onLoadElement.getEnclosingElement().getSimpleName().toString())
                                            .append(".")
                                            .append(onLoadElement.getSimpleName().toString())
                                            .append("();");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                    //Single
                    roundEnv.getElementsAnnotatedWith(OnMenuLoad.class).stream()
                            //Filter annotations relevant to menu
                            .filter(element -> element.getAnnotation(OnMenuLoad.class).menu().equals(menuName))
                            //Sort by menu wave
                            .sorted(Comparator.comparing(element -> element.getAnnotation(OnMenuLoad.class).wave()))
                            //generate each
                            .forEach(onLoadElement -> {
                                try {
                                    writer.append("\n\t\t").append(onLoadElement.getEnclosingElement().getSimpleName().toString())
                                            .append(".")
                                            .append(onLoadElement.getSimpleName().toString())
                                            .append("();");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                    writer.append("\n");
                }

                //Display Menu
                {
                    writer.append("\n");
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
                                            .append(option.key()).append(option.delimiter())
                                            .append("\" + ")
                                            .append(menuName).append(".").append(element.getSimpleName().toString())
                                            .append("+\"\\n\");\n");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            });
                }

                //Scanner
                {
                    //Get the scanner
                    writer.append("\n\t\tScanner scanner = "
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
                            .findFirst().map(element -> menuName+"."+element.getSimpleName().toString()+"")
                            .orElse("\"Enter Selection: \""));
                    //Close Invocation and finish do while loop
                    writer.append(");\n\t\t\tuserSelection = scanner.nextLine().toUpperCase();\n\t\t} while(!acceptedInputs.contains(userSelection));\n\n");
                }

                //MenuSwitch
                {
                    //generate the start of the switch
                    {
                        writer.append("""
                                        switch(userSelection){
                                """);
                    }
                    //Generate the cases
                    {
                    menuElement.getEnclosedElements().stream()
                            .filter(element -> element.getAnnotation(MenuOption.class) != null)
                            .forEach(optionElement -> {
                                try {
                                    MenuOption option = optionElement.getAnnotation(MenuOption.class);
                                    writer.append("\n\t\t\tcase \"").append(option.key()).append("\" -> {\n");
                                    //OnOptionSelect
                                    {
                                        //formatting
                                        writer.append("\n");
                                        //write invokes
                                        //Repeated
                                        {
                                            roundEnv.getElementsAnnotatedWith(OnOptionSelects.class).stream()
                                                    .filter(element -> {
                                                        OnOptionSelects annotation = element.getAnnotation(OnOptionSelects.class);
                                                        return Arrays.stream(annotation.value()).anyMatch(onOptionSelect -> onOptionSelect.menu().equals(menuName) && onOptionSelect.option().equals(optionElement.getSimpleName().toString()));})
                                                    .sorted(Comparator.comparing(element -> Arrays.stream(element.getAnnotation(OnOptionSelects.class).value()).filter(onOptionSelect -> onOptionSelect.menu().equals(menuName) && onOptionSelect.option().equals(optionElement.getSimpleName().toString())).findFirst().get().wave()))
                                                    .forEach(optionLogicElement -> {
                                                try {
                                                    writer.append("\t\t\t\t")
                                                            .append(optionLogicElement.getEnclosingElement().getSimpleName().toString())
                                                            .append(".").append(optionLogicElement.getSimpleName().toString()).append("();\n");

                                                } catch (IOException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            });
                                        }
                                        //Single
                                        {
                                            roundEnv.getElementsAnnotatedWith(OnOptionSelect.class).stream().filter(element -> {
                                                OnOptionSelect annotation = element.getAnnotation(OnOptionSelect.class);
                                                return annotation.menu().equals(menuName) && annotation.option().equals(optionElement.getSimpleName().toString());
                                            }).forEach(optionLogicElement -> {
                                                try {
                                                    writer.append("\t\t\t\t")
                                                            .append(optionLogicElement.getEnclosingElement().getSimpleName().toString())
                                                            .append(".").append(optionLogicElement.getSimpleName().toString()).append("();\n");

                                                } catch (IOException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            });
                                        }

                                    }
                                    //PressEnterToContinue
                                    {
                                        if(optionElement.getAnnotation(PressEnterToContinue.class) != null) {
                                            //Decide which print function to use
                                            writer.append("\n\t\t\t\t").append(usingPrintOverride.get() ? roundEnv.getElementsAnnotatedWith(PrintOverride.class).stream().findFirst().get().getSimpleName().toString():"System.out");
                                            //Invoke
                                            writer.append(".print(");
                                            //Load selection prompt or default
                                            writer.append(menuElement.getEnclosedElements().stream()
                                                    .filter(element -> element.getAnnotation(PressEnterPrompt.class) != null)
                                                    .findFirst().map(element -> element.getSimpleName().toString()+"")
                                                    .orElse("\"Press Enter to Continue: \""));
                                            writer.append(");\n");
                                            writer.append("\t\t\t\tscanner.nextLine();\n");
                                        }
                                    }
                                    //NextMenu Logic
                                    {
                                        NextMenu next = optionElement.getAnnotation(NextMenu.class);
                                        if(next != null) writer.append("\n\t\t\t\treturn \"").append(next.nextMenu()).append("\";");
                                    }
                                    writer.append("\n\n\t\t\t}\n");
                                } catch (IOException e) {
                                    //TODO
                                    throw new RuntimeException(e);
                                }
                            });
                    }
                    //Generate a default case
                    writer.append("\n\t\t\tdefault -> {\n\n\t\t\t}\n");
                    //Close the switch
                    {
                    writer.append("""
                                
                                    }
                                
                            """);
                    }
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
