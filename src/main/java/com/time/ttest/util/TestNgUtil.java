package com.time.ttest.util;
import org.testng.TestNG;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Auther guoweijie

 * @Date 2020-02-23 21:38
 */
public class TestNgUtil {

    public static Object invokeTestNGMethod(String methodName, Class<?>[] parameterTypes, Object instance, Object... args) {
        try {
            Method declaredMethod = TestNG.class.getDeclaredMethod(methodName, parameterTypes);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(instance, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setPrivateParam(String fileName, Object instance, Object param) {
        try {
            Field declaredField = TestNG.class.getDeclaredField(fileName);
            declaredField.setAccessible(true);
            declaredField.set(instance,param);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
