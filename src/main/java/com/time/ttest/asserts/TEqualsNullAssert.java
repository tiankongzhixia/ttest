package com.time.ttest.asserts;

import java.util.List;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 18:01
 */
public class TEqualsNullAssert implements Assert {

    @Override
    public void tassert(TTestAssert tTestAssert, Object actual, Object expect) {
        if (tTestAssert.getAssertTypeEnum() != TAssertType.IsNULL) return;
        if (actual instanceof List){
            List actualList = (List) actual;
            actualList.forEach(a->{
                tassert(tTestAssert,a,expect);
            });
        }else {
            org.testng.Assert.assertNull(actual,tTestAssert.getErrorMsg());
        }
    }
}
