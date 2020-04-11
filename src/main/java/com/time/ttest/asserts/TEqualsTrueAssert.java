package com.time.ttest.asserts;

import java.util.List;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 18:01
 */
public class TEqualsTrueAssert implements Assert {

    @Override
    public void tassert(TTestAssert tTestAssert, Object actual, Object expect) {
        if (tTestAssert.getAssertTypeEnum() != TAssertType.EqualsTrue) return;
        if (expect instanceof List) org.testng.Assert.fail("使用 EqualsTrue 预期结果不能为List assert:"+tTestAssert.toString());
        if (actual instanceof String){
            org.testng.Assert.assertTrue(Boolean.parseBoolean(String.valueOf(actual)),tTestAssert.getErrorMsg());
        } else if (actual instanceof List){
            List actualList = (List) actual;
            if (actualList.size() == 0) org.testng.Assert.fail("实际结果为空 assert:"+tTestAssert.toString());
            actualList.forEach(a->{
               tassert(tTestAssert,a,expect);
            });
        }else {
            org.testng.Assert.assertTrue((Boolean) actual,tTestAssert.getErrorMsg());
        }
    }
}
