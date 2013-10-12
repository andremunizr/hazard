package model;

import javax.enterprise.util.Nonbinding;

public @interface JongoCollection {
    @Nonbinding
    String value() default "";
}
