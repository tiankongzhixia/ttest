package com.time.ttest.interceptor;

import com.time.ttest.File;
import com.time.ttest.FileFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 23:34
 */
public class DataProviderFileHolder {

    private static ConcurrentHashMap<String, File> dataProviderFiles = new ConcurrentHashMap<>();

    public static File getFile(String name){
        if (!dataProviderFiles.containsKey(name)){
            dataProviderFiles.putIfAbsent(name, FileFactory.createFile(name));
        }
        return dataProviderFiles.get(name);
    }
}
