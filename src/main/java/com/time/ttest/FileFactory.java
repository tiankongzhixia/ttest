package com.time.ttest;


/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 23:36
 */
public class FileFactory {

    public static File createFile(String fileName){
        FileSubSuffix subSuffix = FileSubSuffix.match(fileName);
        File file = null;
        if (subSuffix == FileSubSuffix.JSON){
            file = new JsonFile(fileName);
        }
        return file;
    }
}
