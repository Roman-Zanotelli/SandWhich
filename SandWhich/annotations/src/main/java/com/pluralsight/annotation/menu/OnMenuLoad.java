package com.pluralsight.annotation.menu;

import java.lang.annotation.*;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@Repeatable(OnMenuLoads.class)
public @interface OnMenuLoad {
    String menu();
    double wave() default 0;
}
