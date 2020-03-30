package com.time.ttest.file;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.time.ttest.FileSubSuffix;
import org.testng.collections.Lists;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.IOException;
import java.util.List;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 23:14
 */
public interface File<T> {

    FileSubSuffix getSuffix();

    /**
     * 将文件转换为目标格式
     * @param target 目标类
     * @return
     */
    default T transformation(Class<T> target) {return (T) null;}

    /**
     * 转为testNg dataProvider
     * @param target 目标类
     * @return
     * @throws IOException
     */
    default Object[][] dataProviderTransfer(Class<T> target){
        List<T> targetList = (List<T>) transformation(target);
        Object[][] dataProvider = new Object[targetList.size()][1];
        for (int i =0;i<targetList.size();i++){
            dataProvider[i][0] = targetList.get(i);
        }
        return dataProvider;
    };
}
