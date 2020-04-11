package com.time.ttest.util;

import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.NoSuchElementException;

/**
 * @Auther guoweijie

 * @Date 2020-03-18 21:54
 */
@Slf4j
public class FileUtil {
    /**
     * 获取文件的流
     * @param fileName 文件名称
     * @return 文件流
     * @throws NoSuchElementException
     */
    public static InputStream getResourceAsStream(String fileName) throws NoSuchElementException {
        InputStream resourceAsStream = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (null == resourceAsStream){
            throw new NoSuchElementException("not such file "+fileName);
        }
        return resourceAsStream;
    }

}
