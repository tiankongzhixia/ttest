package com.time.ttest.util;

import com.google.gson.Gson;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 20:22
 */
public class GsonUtil {

    public static Gson gson;

    public static void setGson(Gson g){
        gson = g;
    }

    public static Gson getGson(){
        if (gson == null) gson = new Gson();
       return gson;
    }
}
