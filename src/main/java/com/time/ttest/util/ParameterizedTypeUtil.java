package com.time.ttest.util;

import com.google.gson.reflect.TypeToken;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @Auther guoweijie

 * @Date 2020-03-22 23:45
 */
public class ParameterizedTypeUtil {

    public static Type generateList(Class generic){
        return ParameterizedTypeImpl.make(List.class, new Type[]{TypeToken.get(generic).getType()}, null);
    }

    public static Type generateGeneric(Class target,Class generic){
        return ParameterizedTypeImpl.make(target, new Type[]{TypeToken.get(generic).getType()}, null);
    }
}
