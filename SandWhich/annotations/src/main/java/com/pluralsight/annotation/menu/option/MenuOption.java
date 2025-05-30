package com.pluralsight.annotation.menu.option;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface MenuOption {
    String key();
    double order();
    String delimiter() default " - ";
    String overrideName() default "";
}