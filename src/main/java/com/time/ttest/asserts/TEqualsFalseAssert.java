package com.time.ttest.asserts;

import java.util.List;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 18:01
 */
public class TEqualsFalseAssert implements Assert {

    @Override
    public void tassert(TTestAssert tTestAssert, Object actual, Object expect) {
        if (tTestAssert.getAssertTypeEnum() != TAssertType.EqualsFalse) return;
        if (actual instanceof String){
            org.testng.Assert.assertFalse(Boolean.parseBoolean(String.valueOf(actual)),tTestAssert.getErrorMsg());
        } else if (actual instanceof List){
            List actualList = (List) actual;
            if (actualList.size() == 0) org.testng.Assert.fail("实际结果为空 assert:"+tTestAssert.toString());
            actualList.forEach(a->{
                tassert(tTestAssert,a,expect);
            });
        }else {
            org.testng.Assert.assertFalse((Boolean) actual,tTestAssert.getErrorMsg());
        }
    }
}
