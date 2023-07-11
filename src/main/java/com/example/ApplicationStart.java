package com.example;

import com.example.annotation.UserSecured;
import com.example.controller.AdminController;
import com.example.marketComponents.controller.ProductController;
import com.example.listComponents.controller.UserListController;
import com.example.userComponents.controller.AppUserController;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run (ApplicationStart.class, args);
        print ();
    }

    public static void print() {
        List<Method> adminSecuredMethods = AnnotationUtils.getMethodsAnnotatedWith (AdminController.class, UserSecured.class);
        List<Method> listSecuredMethods = AnnotationUtils.getMethodsAnnotatedWith (UserListController.class, UserSecured.class);
        List<Method> itemsSecuredMethods = AnnotationUtils.getMethodsAnnotatedWith (ProductController.class, UserSecured.class);
        List<Method> userSecuredMethods = AnnotationUtils.getMethodsAnnotatedWith (AppUserController.class, UserSecured.class);
        String url = "http:\\localhost:8085\\";
        try {
            FileWriter writer = new FileWriter ("UserSecured.txt");
            writer.write ("\n ------ \n");
            writer.write (url+"admin"+"\n");
            for (String method : getLink (adminSecuredMethods)) {
                writer.write (method + "\n");
            }
            writer.write ("\n ------ \n");
            writer.write (url+"list"+"\n");
            for (String method : getLink (listSecuredMethods)) {
                writer.write (method + "\n");
            }
            writer.write ("\n ------ \n");
            writer.write (url+"products"+"\n");
            for (String method : getLink (itemsSecuredMethods)) {
                writer.write (method + "\n");
            }
            writer.write ("\n ------ \n");
            writer.write (url+"user"+"\n");
            for (String method : getLink (userSecuredMethods)) {
                writer.write (method + "\n");
            }
            writer.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }

    }

    static List<String> getLink(List<Method> methods) {
        List<String> methodValues = new ArrayList<> ();
        for (Method method : methods) {
            GetMapping getMappingAnnotation = method.getAnnotation (GetMapping.class);
            if (getMappingAnnotation != null) {
                String[] getMappingValue = getMappingAnnotation.value ();
                methodValues.add ("Get : " + Arrays.toString (getMappingValue) +" | "+method.getName ());
            }

            PostMapping postMappingAnnotation = method.getAnnotation (PostMapping.class);
            if (postMappingAnnotation != null) {
                String[] postMappingValue = postMappingAnnotation.value ();
                methodValues.add ("Post : " + Arrays.toString (postMappingValue) +" | "+method.getName ());
            }
            DeleteMapping deleteMapping = method.getAnnotation (DeleteMapping.class);
            if (deleteMapping != null) {
                String[] postMappingValue = deleteMapping.value ();
                methodValues.add ("Delete : " + Arrays.toString (postMappingValue) +" | "+method.getName ());
            }
            PutMapping putMapping = method.getAnnotation (PutMapping.class);
            if (putMapping != null) {
                String[] postMappingValue = putMapping.value ();
                methodValues.add ("Put : " + Arrays.toString (postMappingValue) +" | "+method.getName ());
            }
        }
        return methodValues;
    }


    static class AnnotationUtils {

        public static List<Method> getMethodsAnnotatedWith(Class<?> clazz, Class<? extends Annotation> annotation) {
            List<Method> annotatedMethods = new ArrayList<> ();
            Method[] methods = clazz.getDeclaredMethods ();
            for (Method method : methods) {
                if (method.isAnnotationPresent (annotation)) {
                    annotatedMethods.add (method);
                }
            }
            return annotatedMethods;
        }
    }


}
