package com.pluralsight.build.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes({"Menu", "MenuOption", "OnMenuLoad", "OnMenuOption", "OnShutDown", "OnStart"})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class ExpressoProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Programmer.plan(roundEnv); //parse & plan program
        Programmer.writeProgram(); //write program to file
        return true;
    }
}
