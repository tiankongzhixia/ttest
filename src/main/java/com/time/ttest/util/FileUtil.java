package com.time.ttest.util;

import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.nio.file.NoSuchFileException;
/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 21:54
 */
@Slf4j
public class FileUtil {
    /**
     * 获取文件的流
     * @param fileName
     * @return
     * @throws NoSuchFileException
     */
    public static InputStream getResourceAsStream(String fileName) throws NoSuchFileException {
        InputStream resourceAsStream = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (null == resourceAsStream){
            throw new NoSuchFileException("not such file "+fileName);
        }
        return resourceAsStream;
    }

}
