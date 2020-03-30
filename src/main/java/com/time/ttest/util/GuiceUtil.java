package com.time.ttest.util;

import com.google.inject.Injector;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-30 13:17
 */
public class GuiceUtil {

    private static Injector injector;

    public static void setInjector(Injector inj){
        injector = inj;
    }

    public static Injector getInjector(){
        return injector;
    }

    public static Object getInstance(Class className){
        return injector.getInstance(className);
    }

}
