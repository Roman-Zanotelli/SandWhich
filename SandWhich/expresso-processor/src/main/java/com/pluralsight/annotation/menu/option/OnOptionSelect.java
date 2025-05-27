package com.pluralsight.annotation.menu.option;

import java.lang.annotation.*;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@Repeatable(OnOptionSelects.class)
public @interface OnOptionSelect {
    String menu();
    String option();
    double wave() default 0;
}
