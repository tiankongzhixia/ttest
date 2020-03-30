package com.time.ttest.file;


import com.time.ttest.FileSubSuffix;
import com.time.ttest.file.File;
import com.time.ttest.file.JsonFile;

/**
 * 文件工厂类
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 23:36
 */
public class FileFactory {

    /**
     * 根据文件后缀创建不同的File
     * @param fileName
     * @return
     */
    public static File builder(String fileName){
        FileSubSuffix subSuffix = FileSubSuffix.match(fileName);
        File file = null;
        if (subSuffix == FileSubSuffix.JSON){
            file = new JsonFile(fileName);
        }
        if (subSuffix == FileSubSuffix.YML){
            file = new YmlFile(fileName);
        }
        return file;
    }
}
