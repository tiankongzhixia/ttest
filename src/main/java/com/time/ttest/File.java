package com.time.ttest;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 23:14
 */
public interface File {

    /**
     * 将文件转换为目标格式
     * @param target
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> List<T> transformation(Class target) throws IOException;

    /**
     * 转为testNg dataProvider
     * @param target
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> Object[][] dataProviderTransfer(Class target) throws IOException;


    void setGson(Gson gson);
}
