package com.time.ttest;

import com.time.ttest.file.FileFactory;
import com.time.ttest.file.YmlFile;

import java.util.*;

/**
 * 默认配置
 * @Auther guoweijie
 * @Date 2020-02-27 05:49
 */
public class TTestProperties extends Properties {


    private YmlFile mFile;

    private String fileName = "ttest.yml";

    private Class packageMainClass;

    public TTestProperties(){
        init();
    }

    public TTestProperties(String fileName) {
        this.fileName = fileName;
        init();
    }

    public TTestProperties(String fileName,Class packageMainClass) {
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
        HashMap<String,String> map = new HashMap<String, String>();
        convertToMap((HashMap<String, String>) mFile.transformation(HashMap.class),map,null);
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

    private void convertToMap(Map map, Map newMap, String key){
        Set<Map.Entry> set = map.entrySet();
        for (Map.Entry entry:set){
            Object key2 = entry.getKey();
            Object value = entry.getValue();
            if(value instanceof LinkedHashMap){
                if(key==null){
                    convertToMap((Map)value,newMap,key2.toString());
                }else{
                    convertToMap((Map)value,newMap,key+"."+key2.toString());
                }
            }
            if(key==null){
                newMap.put(key2.toString(), value.toString());
            }
            if(key!=null){
                newMap.put(key+"."+key2.toString(), value.toString());
            }
        }
    }

}
