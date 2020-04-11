package com.time.ttest.util;
import java.util.*;

public class PropertyUtil {

    /**
     * 替换 配置文件属性的前缀
     * @param properties  配置文件
     * @param prefix 替换的前缀
     * @param targetPrefix 目标前缀
     * @return
     */
    public static Properties replacePropertyPrefix(Properties properties, String prefix, String targetPrefix){
        Properties prefixProperties = new Properties();
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()){
            String key = String.valueOf(keys.nextElement());
            if (key.indexOf(prefix) == 0){
                prefixProperties.put(key.replace(prefix,targetPrefix),properties.get(key));
            }
        }
        return prefixProperties;
    }
}
