package com.time.ttest.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 00:33
 */
public class ClassUtil {

    public static ParameterizedType findClassGenericInterface(Class clazz,Type generic){
        Type[] genericInterfaces =  clazz.getGenericInterfaces();
        for (Type type:genericInterfaces){
            if (type.getTypeName().equals(generic.getTypeName())){
                return (ParameterizedType) type;
            }
        }
        return null;
    };
}
