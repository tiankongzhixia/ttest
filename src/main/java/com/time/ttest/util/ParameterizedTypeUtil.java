package com.time.ttest.util;

import com.google.gson.reflect.TypeToken;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-22 23:45
 */
public class ParameterizedTypeUtil {

    public static Type generateList(Class target){
        return ParameterizedTypeImpl.make(List.class, new Type[]{TypeToken.get(target).getType()}, null);
    }
}
