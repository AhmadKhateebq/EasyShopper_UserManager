package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * This is a custom annotation.
 * Use this annotation to mark methods for editor security process.<p>
 * guidelines:
 * - provide the user jwt token in the Authorization headers<p>
 * access:<p>
 * - this user can view the list's item inside the list that he is trying to access<p>
 * - this user can add items to the list's item inside the list that he is trying to access<p>
 * - this user can delete items from the list's item inside the list that he is trying to access
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EditorSecured {
}
