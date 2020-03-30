package com.time.ttest;

import java.util.NoSuchElementException;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 23:17
 */
public enum FileSubSuffix {

    JSON,
    YML;


    public static FileSubSuffix match(String fileName){
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        if (suffix.equals("json") || suffix.equals("JSON")){
            return FileSubSuffix.JSON;
        }
        if (suffix.equals("YML") || suffix.equals("yml")){
            return FileSubSuffix.YML;
        }
        throw new NoSuchElementException(suffix + " 不支持");
    }
}
