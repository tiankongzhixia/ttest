package com.time.ttest.file;

import java.io.IOException;
import java.util.List;

/**
 * @Auther guoweijie

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
        Object transformationObj = transformation(target);
        if (transformationObj instanceof List){
            List<T> targetList = (List<T>) transformation(target);
            Object[][] dataProvider = new Object[targetList.size()][1];
            for (int i =0;i<targetList.size();i++){
                dataProvider[i][0] = targetList.get(i);
            }
            return dataProvider;
        }else {
            Object[][] dataProvider = new Object[1][1];
            dataProvider[0][0] = (T) transformationObj;
            return dataProvider;
        }
    };
}
