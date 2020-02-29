package com.time.ttest.util;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public class ReflectionsUtil {

    public static Set<Method> getMethodsAnnotatedWith(String name, Class<? extends Annotation>  clazz) {
        Reflections reflections = new Reflections(name,new MethodAnnotationsScanner());
        return reflections.getMethodsAnnotatedWith(clazz);
    }

    public static <T> Set<Class<? extends T>> getSubTypesOf(String name,Class<T> clazz){
        Reflections reflections = new Reflections(name,new SubTypesScanner());
        return reflections.getSubTypesOf(clazz);
    }

    public static <T> Set<Class<?>> getTypesAnnotatedWith(String name, Class<? extends Annotation>  clazz){
        Reflections reflections = new Reflections(name);
        return reflections.getTypesAnnotatedWith(clazz);
    }

}
