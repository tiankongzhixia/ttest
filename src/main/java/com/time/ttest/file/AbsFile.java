package com.time.ttest.file;

import com.time.ttest.FileSubSuffix;
import com.time.ttest.util.FileUtil;

import java.io.InputStream;
import java.nio.file.NoSuchFileException;

/**
 * 文件的父类，提供获取转换后的目标对象
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 23:15
 */
public abstract class AbsFile<T> implements File {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 目标转换对象
     */
    private T targetObject;


    public AbsFile(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取后缀
     * @return FileSubSuffix 文件后缀枚举
     */
    public FileSubSuffix getSuffix(){
        return FileSubSuffix.match(this.fileName);
    }

    /**
     * 读取文件流
     * @return InputStream
     */
    public InputStream getInputStream() {
        return FileUtil.getResourceAsStream(this.fileName);
    }

    public T getTargetObject(){
        return targetObject;
    }

    public void setTargetObject(T targets){
        targetObject = targets;
    }
}
