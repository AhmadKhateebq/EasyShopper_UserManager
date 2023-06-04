package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * This is a custom annotation.
 * Use this annotation to mark methods for editor security process.<p>
 * guidelines:<p>
 * - provide the admin jwt token in the Authorization headers
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AdminSecured {
}
