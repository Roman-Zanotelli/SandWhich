package com.pluralsight.annotation.menu.option;


import java.lang.annotation.*;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface OnOptionSelects {
    OnOptionSelect[] value();
}
