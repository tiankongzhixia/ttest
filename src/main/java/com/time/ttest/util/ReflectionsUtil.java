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

    /**
     * 根据不同的包名获取不同的扫描器
     * @param packageName 包名
     * @return
     */
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

    /**
     * 从方法上匹配注解
     * @param packageName 包名
     * @param annotation  注解
     * @return
     */
    public static Set<Method> getMethodsAnnotatedWith(String packageName, Class<? extends Annotation>  annotation) {
        return getReflections(packageName).getMethodsAnnotatedWith(annotation);
    }

    /**
     * 获取指定类的所有子类
     * @param packageName 包名
     * @param clazz 指定父类
     * @param <T>
     * @return
     */
    public static <T> Set<Class<? extends T>> getSubTypesOf(String packageName,Class<T> clazz){
        return getReflections(packageName).getSubTypesOf(clazz);
    }

    /**
     * 从类上获取指定注解
     * @param packageName 包名
     * @param annotation 注解
     * @param <T>
     * @return
     */
    public static <T> Set<Class<?>> getTypesAnnotatedWith(String packageName, Class<? extends Annotation>  annotation){
        return getReflections(packageName).getTypesAnnotatedWith(annotation);
    }

}
