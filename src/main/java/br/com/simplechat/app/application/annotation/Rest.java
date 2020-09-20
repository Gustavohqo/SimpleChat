package br.com.simplechat.app.application.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Rest {
    public String path() default "/";
}
