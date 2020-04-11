package com.time.ttest.asserts;

import java.util.List;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 17:46
 */
public class TNotEqualsAssert implements Assert{

    @Override
    public void tassert(TTestAssert tTestAssert, Object actual, Object expect) {
        if (tTestAssert.getAssertTypeEnum() != TAssertType.NotEquals) return;
        if (expect instanceof List) org.testng.Assert.fail("使用 Equals 预期结果不能为List assert:"+tTestAssert.toString());
        if (actual instanceof List){
            List actualList = (List) actual;
            if (actualList.size() == 0) org.testng.Assert.fail("实际结果为空 assert:"+tTestAssert.toString());
            actualList.forEach(a->{
                tassert(tTestAssert,a,expect);
            });
        }else {
            org.testng.Assert.assertNotEquals(String.valueOf(actual),String.valueOf(expect),tTestAssert.getErrorMsg());
        }

    }
}
