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
                {
                    menuImports.add("import java.util.Scanner;");
                    menuImports.add("import java.util.ArrayList;");
                    menuImports.add("import java.util.List;");

                    String imp = "import " + processingEnv.getElementUtils().getPackageOf(menuElement).toString() + "." + menuName + ";";
                    if (!menuImports.contains(imp)) menuImports.add(imp);
                }
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

                writer.append(String.format("""
                        package com.pluralsight.generated;
                        \s
                        //Auto Generated Imports
                        %s
                        \s
                        //Auto Generated Class of Menu "%s"
                        public class ExpressoGenerated%s{
                            public static String load() {
                                //@WhiteSpace Elements
                        %s
                        \s
                                //@Header Element
                        %s
                        
                                //@OnMenuLoad Elements
                        %s
                        \s
                                //@MenuOptions Display (Uses @PrintOverride)
                        %s
                                \s
                                //@ScannerProducer Element
                                Scanner scanner = %s.getScanner();
                                \s
                                //Generate "Accepted Inputs" for the Menu
                                ArrayList<String> acceptedInputs = new ArrayList<>(List.of("%s"));
                                \s
                                String userSelection = "";
                                do{
                                    //@PrintOverride & @SelectionPrompt Element
                                    %s.print(%s);
                                    userSelection = scanner.nextLine().toUpperCase();
                                } while(!acceptedInputs.contains(userSelection));
                                \s
                                switch(userSelection){
                                //AutoGenerated Cases from @MenuOption, @OnOptionSelect, @PressEnterToContinue, & @NextMenu
                        %s
                                    default -> {}
                                }
                                return null;
                            }
                        }
                        \s""",
                        //Imports
                        menuImports.stream()
                                .map(menuImport -> menuImport+"\n")
                                .collect(Collectors.joining()),
                        //Auto Generated Note
                        menuName,
                        //Class Name
                        menuName,
                        //White Space
                        Generators.getWhitSpaceString(menuElement, usingPrintOverride.get()),
                        //Menu Header
                        Generators.getMenuHeader(menuElement, menuName, usingPrintOverride.get()),
                        //OnLoad
                        Generators.getOnMenuLoadString(menuName),
                        //Display
                        Generators.getDisplayString(menuElement, menuName, usingPrintOverride.get()),
                        //Scanner
                        Generators.getScannerString(menuElement, menuName, usingScannerProducer.get()),
                        //Accepted Inputs
                        Generators.getAcceptedInputsString(menuElement),
                        //Print Override
                        Generators.getPrintOvverideString(usingPrintOverride.get()),
                        //Load Prompt
                        Generators.getPromptString(menuElement, menuName),
                        //Generate cases
                        Generators.getCasesString(menuElement, menuName, usingPrintOverride.get())

                ));
            }
        } catch (IOException ignore) {

        }

    }
    private static class Generators {
        private static String getPromptString(TypeElement menuElement, String menuName) {
            return menuElement.getEnclosedElements().stream()
                    .filter(element -> element.getAnnotation(SelectionPromt.class) != null)
                    .findFirst().map(element -> menuName + "." + element.getSimpleName().toString())
                    .orElse("\"Enter Selection: \"");
        }

        private static String getPrintOvverideString(boolean usingPrintOverride) {
            return usingPrintOverride ?
                    roundEnv.getElementsAnnotatedWith(PrintOverride.class).stream().findFirst().get().getSimpleName().toString() :
                    "System.out";
        }

        private static String getAcceptedInputsString(TypeElement menuElement) {
            return menuElement.getEnclosedElements().stream()
                    .filter(element -> element.getAnnotation(MenuOption.class) != null)
                    .map(element -> element.getAnnotation(MenuOption.class).key())
                    .distinct()
                    .collect(Collectors.joining("\", \""));
        }

        private static String getScannerString(TypeElement menuElement, String menuName, boolean usingScannerProducer) {
            return usingScannerProducer ?
                    roundEnv.getElementsAnnotatedWith(ScannerProducer.class).stream().findFirst().get().getSimpleName().toString() :
                    "ExpressoScanner";
        }

        private static String getDisplayString(TypeElement menuElement, String menuName, boolean usingPrintOverride) {
            return menuElement.getEnclosedElements().stream()
                    .filter(element -> element.getAnnotation(MenuOption.class) != null)
                    .sorted(Comparator.comparing(element -> element.getAnnotation(MenuOption.class).order()))
                    .map(element -> {
                        MenuOption option = element.getAnnotation(MenuOption.class);
                        return String.format(
                                "\t\t%s.print(\"%s%s\" + %s.%s +\"\\n\");\n",
                                usingPrintOverride ?
                                        roundEnv.getElementsAnnotatedWith(PrintOverride.class).stream().findFirst().get().getSimpleName().toString() :
                                        "System.out",
                                option.key(),
                                option.delimiter(),
                                menuName,
                                element.getSimpleName().toString()
                        );
                    }).collect(Collectors.joining());
        }

        private static String getWhitSpaceString(TypeElement menuElement, boolean usingPrintOverride) {
            WhiteSpace ws = menuElement.getAnnotation(WhiteSpace.class);
            if (ws != null) {
                return String.format(
                        "\t\t%s.print(\"\\n\".repeat(%s));",
                        usingPrintOverride ?
                                roundEnv.getElementsAnnotatedWith(PrintOverride.class).stream()
                                        .findFirst()
                                        .get()
                                        .getSimpleName()
                                        .toString() :
                                "System.out",
                        ws.value()
                );
            }
            return "";
        }

        private static String getOnMenuLoadString(String menuName) {
            //Repeated
            return roundEnv.getElementsAnnotatedWith(OnMenuLoads.class).stream()
                    //Filter annotations relevant to menu
                    .filter(element -> Arrays.stream(element.getAnnotation(OnMenuLoads.class).value()).anyMatch(onMenuLoad -> onMenuLoad.menu().equals(menuName)))
                    //Sort by menu wave
                    .sorted(Comparator.comparing(element -> Arrays.stream(element.getAnnotation(OnMenuLoads.class).value()).filter(onMenuLoad -> onMenuLoad.menu().equals(menuName)).findFirst().get().wave()))
                    .map(onLoadElement -> String.format("\t\t%s.%s();\n", onLoadElement.getEnclosingElement().getSimpleName().toString(), onLoadElement.getSimpleName().toString()))
                    .collect(Collectors.joining()) + roundEnv.getElementsAnnotatedWith(OnMenuLoad.class).stream()
                    //Filter annotations relevant to menu
                    .filter(element -> element.getAnnotation(OnMenuLoad.class).menu().equals(menuName))
                    //Sort by menu wave
                    .sorted(Comparator.comparing(element -> element.getAnnotation(OnMenuLoad.class).wave()))
                    .map(onLoadElement -> String.format("\t\t%s.%s();\n", onLoadElement.getEnclosingElement().getSimpleName().toString(), onLoadElement.getSimpleName().toString()))
                    .collect(Collectors.joining());

        }


        private static String getCasesString(TypeElement menuElement, String menuName, boolean usingPrintOverride) {
            return menuElement.getEnclosedElements().stream()
                    .filter(element -> element.getAnnotation(MenuOption.class) != null)
                    .map(optionElement -> {
                        MenuOption option = optionElement.getAnnotation(MenuOption.class);
                        return String.format(
                                "\n\t\t\tcase \"%s\" -> {\n%s\n%s\n%s\n\t\t\t}",
                                option.key(),
                                getOptionSelectString(optionElement, menuName),
                                getPressEnterString(menuElement, optionElement, usingPrintOverride),
                                getNextMenuString(optionElement)
                        );
                    }).collect(Collectors.joining());
        }


        private static <T extends Element> String getOptionSelectString(T optionElement, String menuName) {
            return roundEnv.getElementsAnnotatedWith(OnOptionSelects.class).stream()
                    .filter(element -> {
                        OnOptionSelects annotation = element.getAnnotation(OnOptionSelects.class);
                        return Arrays.stream(annotation.value()).anyMatch(onOptionSelect -> onOptionSelect.menu().equals(menuName) && onOptionSelect.option().equals(optionElement.getSimpleName().toString()));
                    })
                    .sorted(Comparator.comparing(element -> Arrays.stream(element.getAnnotation(OnOptionSelects.class).value()).filter(onOptionSelect -> onOptionSelect.menu().equals(menuName) && onOptionSelect.option().equals(optionElement.getSimpleName().toString())).findFirst().get().wave()))
                    .map(optionLogicElement -> String.format(
                            "\t\t\t\t%s.%s();\n",
                            optionLogicElement.getEnclosingElement().getSimpleName().toString(),
                            optionLogicElement.getSimpleName().toString())
                    )
                    .collect(Collectors.joining())
                    +
                    roundEnv.getElementsAnnotatedWith(OnOptionSelect.class).stream().filter(element -> {
                                OnOptionSelect annotation = element.getAnnotation(OnOptionSelect.class);
                                return annotation.menu().equals(menuName) && annotation.option().equals(optionElement.getSimpleName().toString());
                            }).map(optionLogicElement -> String.format(
                                    "\t\t\t\t%s.%s();\n",
                                    optionLogicElement.getEnclosingElement().getSimpleName().toString(),
                                    optionLogicElement.getSimpleName().toString())
                            )
                            .collect(Collectors.joining());

        }


        private static <T extends Element> String getPressEnterString(TypeElement menuElement, T optionElement, boolean usingPrintOverride) {
            return optionElement.getAnnotation(PressEnterToContinue.class) != null ?
                    String.format(
                            "\t\t\t\t%s.print(%s);\n\t\t\t\tscanner.nextLine();",
                            usingPrintOverride ?
                                    roundEnv.getElementsAnnotatedWith(PrintOverride.class).stream()
                                            .findFirst().get()
                                            .getSimpleName().toString() :
                                    "System.out",

                            menuElement.getEnclosedElements().stream()
                                    .filter(element -> element.getAnnotation(PressEnterPrompt.class) != null)
                                    //TODO: Implement alternative prompt
                                    .findFirst().map(element -> element.getSimpleName().toString())
                                    .orElse("\"Press Enter to Continue\"")
                    ) : "";
        }


        private static <T extends Element> String getNextMenuString(T optionElement) {
            NextMenu next = optionElement.getAnnotation(NextMenu.class);
            return next != null ? String.format("\t\t\t\treturn \"%s\";", next.nextMenu()) : "\t\t\t\t//Return To Same Menu";
        }


        private static String getMenuHeader(TypeElement menuElement, String menuName, boolean usingPrintOverride) {
            return menuElement.getEnclosedElements().stream()
                    .filter(element -> element.getAnnotation(Header.class) != null)
                    .findFirst().map(element -> String.format(
                            "\t\t%s.print(%s.%s + \"\\n\");",
                            usingPrintOverride ?
                                    roundEnv.getElementsAnnotatedWith(PrintOverride.class).stream()
                                            .findFirst().get()
                                            .getSimpleName().toString() :
                                    "System.out",
                            menuName,
                            element.getSimpleName().toString()
                    )).orElse("");
        }
    }
}
