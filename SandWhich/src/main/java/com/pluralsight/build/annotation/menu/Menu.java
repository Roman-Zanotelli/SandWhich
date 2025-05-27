package com.pluralsight.build.annotation.menu;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Menu {
    boolean isMain() default false;
    String overrideName() default "";
    String overrideMainPackage() default "";
}
