package com.time.ttest.util;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectionsUtil {
    private static ConcurrentHashMap<String,Reflections> reflectionsHashMap = new ConcurrentHashMap<>();

    private static Reflections getReflections(String packageName){
        if (!reflectionsHashMap.containsKey(packageName)){
            Reflections reflections =  new Reflections(new ConfigurationBuilder().forPackages(packageName)
                    .addScanners(new MethodAnnotationsScanner())// 添加 方法注解扫描工具
                    .addScanners(new FieldAnnotationsScanner()) // 添加 属性注解扫描工具
                    .addScanners(new SubTypesScanner()) // 添加子类扫描工具
                    .addScanners(new MethodParameterScanner() ) // 添加方法参数扫描工具
            );
            reflectionsHashMap.put(packageName,reflections);
        }
        return reflectionsHashMap.get(packageName);
    }

    public static Set<Method> getMethodsAnnotatedWith(String name, Class<? extends Annotation>  clazz) {
        return getReflections(name).getMethodsAnnotatedWith(clazz);
    }

    public static <T> Set<Class<? extends T>> getSubTypesOf(String name,Class<T> clazz){
        return getReflections(name).getSubTypesOf(clazz);
    }

    public static <T> Set<Class<?>> getTypesAnnotatedWith(String name, Class<? extends Annotation>  clazz){
        return getReflections(name).getTypesAnnotatedWith(clazz);
    }

}
