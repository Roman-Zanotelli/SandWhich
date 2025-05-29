package com.pluralsight;


import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class ExpressoProcessor extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Program.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, ">> Expresso Processor Invoked");
        processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, ">> Planning Program");
        Program.plan(annotations, roundEnv);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, ">> Writing Program");
        Program.writeProgram();
        return true;
    }
}
