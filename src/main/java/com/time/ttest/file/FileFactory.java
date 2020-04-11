package com.time.ttest.file;


/**
 * 文件工厂类
 * @Auther guoweijie

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
