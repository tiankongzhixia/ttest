package com.time.ttest;

import com.google.gson.Gson;
import com.time.ttest.util.FileUtil;

import java.io.InputStream;
import java.nio.file.NoSuchFileException;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 23:15
 */
public abstract class AbsFile implements File{

    private String fileName;

    private InputStream inputStream;

    public AbsFile(String fileName) {
        this.fileName = fileName;
    }

    public AbsFile(String fileName, InputStream inputStream) {
        this.fileName = fileName;
        this.inputStream = inputStream;
    }

    public FileSubSuffix getSuffix(){
        return FileSubSuffix.match(this.fileName);
    }

    public InputStream getInputStream() throws NoSuchFileException {
        if (null == this.inputStream){
            this.inputStream = FileUtil.getResourceAsStream(this.fileName);
        }
        return this.inputStream;
    }

    public abstract Gson getGson();
}
