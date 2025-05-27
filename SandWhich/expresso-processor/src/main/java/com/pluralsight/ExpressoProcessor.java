package com.pluralsight;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class ExpressoProcessor extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, ">> Expresso Processor Invoked");
        try {
            JavaFileObject file = processingEnv.getFiler().createSourceFile("com.pluralsight.generated.ExpressoMain");
            try (Writer writer = file.openWriter()) {
                writer.write("""
                package com.pluralsight.generated;
                
                public class ExpressoMain {
                    public static void main(String[] args) {
                        System.out.println("Hello from generated main!");
                    }
                }
                """);

            }

        } catch (IOException e) {

        }
        return true;
    }
}
