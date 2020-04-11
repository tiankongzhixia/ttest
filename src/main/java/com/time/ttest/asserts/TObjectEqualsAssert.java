package com.time.ttest.asserts;

import com.google.gson.Gson;
import com.time.ttest.util.GsonUtil;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.util.List;
import java.util.Map;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 17:46
 */
public class TObjectEqualsAssert implements Assert{


    @Override
    public void tassert(TTestAssert tTestAssert, Object actual, Object expect) {
        if (tTestAssert.getAssertTypeEnum() != TAssertType.ObjectEquals) return;
        if (expect instanceof List) org.testng.Assert.fail("使用 ObjectEquals 预期结果不能为List assert:"+tTestAssert.toString());
        Map expectMap;
        Map actualMap;
        if (expect instanceof Map){
            expectMap = (Map) expect;
        }else {
            expectMap = GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(expect),Map.class);
        }
        if (actual instanceof List){
            List actualList = (List) actual;
            if (actualList.size() == 0) org.testng.Assert.fail("实际结果为空 assert:"+tTestAssert.toString());
            actualList.forEach(a->{
                org.testng.Assert.assertEqualsDeep((Map)a,expectMap);
            });
        }else {
            if (actual instanceof Map){
                actualMap = (Map) actual;
            }else {
                actualMap = GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(actual),Map.class);
            }
            org.testng.Assert.assertEqualsDeep(actualMap,expectMap);
        }
    }


}
