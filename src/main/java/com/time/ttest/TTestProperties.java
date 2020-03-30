package com.time.ttest;

import com.time.ttest.file.FileFactory;
import com.time.ttest.file.YmlFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * 默认配置
 * @Auther guoweijie
 * @Date 2020-02-27 05:49
 */
public class TTestProperties extends Properties {


    private YmlFile mFile;

    private String fileName = "ttest.yml";

    private Class packageMainClass;

    public TTestProperties() throws Throwable {
        init();
    }

    public TTestProperties(String fileName) throws IOException {
        this.fileName = fileName;
        init();
    }

    public TTestProperties(String fileName,Class packageMainClass) throws Throwable {
        this.fileName = fileName;
        this.packageMainClass = packageMainClass;
        init();
    }

    public TTestProperties(Class packageMainClass) {
        this.packageMainClass = packageMainClass;
        init();
    }

    private void init() {
        mFile = (YmlFile) FileFactory.builder(fileName);
        HashMap<String,String> map = (HashMap<String, String>) mFile.transformation(HashMap.class);
        check(map);
        this.putAll(map);
    }

    private void check(HashMap<String,String> map){
        if (!map.containsKey("ttest.report.hook")){
            map.put("ttest.report.hook","");
        }
        if (!map.containsKey("ttest.report.hookUser")){
            map.put("ttest.report.hookUser","");
        }
        if (!map.containsKey("testng.suite.file")){
            map.put("testng.suite.file","/testng.xml");
        }
        if (!map.containsKey("package")){
            if (null == packageMainClass){
                throw new NoSuchElementException("ttest配置文件package为必填");
            }else {
                map.put("package",packageMainClass.getPackage().getName());
            }
        }
    }

}
