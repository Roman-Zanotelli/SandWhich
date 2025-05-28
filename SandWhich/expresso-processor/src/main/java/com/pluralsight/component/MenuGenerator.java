package com.pluralsight.component;

import com.pluralsight.Program;
import com.pluralsight.annotation.menu.Menu;

import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.pluralsight.Program.processingEnv;

public class MenuGenerator {
    private MenuGenerator(){}

    public static String getMainMenu(){
        return Program.roundEnv.getElementsAnnotatedWith(Menu.class).stream()
                .filter(element -> element.getAnnotation(Menu.class).isMain()).findFirst().get()
                .getSimpleName().toString();
    }

    public static ArrayList<String> getMenus(){
        return Program.roundEnv.getElementsAnnotatedWith(Menu.class).stream()
                .map(element -> element.getSimpleName().toString())
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static ArrayList<String> getImports(){
        return Program.roundEnv.getElementsAnnotatedWith(Menu.class).stream()
                .map(element -> "import com.pluralsight.generated.ExpressoGenerated"+ element.getSimpleName().toString() + ";")
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static void write(){
        Program.roundEnv.getElementsAnnotatedWith(Menu.class).forEach(element -> writeMenu((TypeElement) element));
    }
    private static void writeMenu(TypeElement menuElement){
        try {
            JavaFileObject file = processingEnv.getFiler().createSourceFile("com.pluralsight.generated.ExpressoGenerated"+menuElement.getSimpleName().toString());
            ArrayList<String> menuImports = new ArrayList<>();
            //Todo: generate all menu Imports
            {

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
                //TODO: Append OnOptionSelect
                {

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
