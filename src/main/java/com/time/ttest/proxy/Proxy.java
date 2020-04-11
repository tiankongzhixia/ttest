package com.time.ttest.proxy;

import lombok.Data;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Auther guoweijie

 * @Date 2020-03-23 00:31
 */
@Data
public abstract class Proxy<T>{

    T beProxy;

    /**
     * 执行方法被代理类方法
     * @param methodName 方法名
     * @param parameterTypes 方法参数类型
     * @param args 参数
     * @return
     */
    Object invokeMethod(String methodName, Class<?>[] parameterTypes, Object... args){
        try {
            Method declaredMethod = beProxy.getClass().getDeclaredMethod(methodName,parameterTypes);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(this, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改被代理类的私有属性
     * @param fileName
     * @param param
     */
    void modifyPrivateParam(String fileName, Object param) {
        try {
            Field declaredField = beProxy.getClass().getDeclaredField(fileName);
            declaredField.setAccessible(true);
            declaredField.set(this,param);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
