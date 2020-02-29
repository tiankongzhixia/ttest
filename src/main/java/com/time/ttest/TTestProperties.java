package com.time.ttest;

import java.util.HashMap;
import java.util.Properties;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-27 05:49
 */
public class TTestProperties extends Properties {

    public TTestProperties() {
        init();
    }

    private void init() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("ttest.report.hook","");
        map.put("ttest.report.hookUser","");
        map.put("testng.suite.file","/testng.xml");
        this.putAll(map);
    }

    public TTestProperties(Properties defaults) {
        super(defaults);
    }


}
