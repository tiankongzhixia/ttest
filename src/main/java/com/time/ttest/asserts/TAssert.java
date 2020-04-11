package com.time.ttest.asserts;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.time.ttest.context.TTestApplicationContext;
import com.time.ttest.util.GsonUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 15:31
 */
public class TAssert {

    @Inject
    private TTestApplicationContext context;

    /**
     * 开始校验 根据配置的json-path获取需要校验的实际结果和预期结果进行比较
     * @param tTestAssert
     */
    void startAssert(TTestAssert tTestAssert){
        String actualPath = tTestAssert.getActualPath();
        String expectPath = tTestAssert.getExpectPath();
        Object actual = tTestAssert.getActual(),expect = tTestAssert.getExpect();
        if (!StringUtils.isEmpty(actualPath) && !"$".equals(actualPath)){
            actual = jsonPathRead(actual,actualPath);
        }
        if (!StringUtils.isEmpty(expectPath) && !"$".equals(expectPath)){
            expect = jsonPathRead(expect,expectPath);
        }
        //根据不同校验类型获取不同的类
        Assert tAssert = context.getInjector().getInstance(tTestAssert.getAssertTypeEnum().getAssertClass());
        tAssert.tassert(tTestAssert,actual,expect);
    };

    private Object jsonPathRead(Object obj,String path){
        try {
            return JsonPath.read(GsonUtil.getGson().toJson(obj),path);
        }catch (PathNotFoundException e){
            return null;
        }
    }
}
