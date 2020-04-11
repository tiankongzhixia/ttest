package com.time.ttest.asserts;

import lombok.Getter;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 15:33
 */
public enum TAssertType {

    //校验是否相同
    Equals(TEqualsAssert.class),
    //校验结果是否为true
    EqualsTrue(TEqualsTrueAssert.class),
    //校验结果是否为false
    EqualsFalse(TEqualsFalseAssert.class),
    //校验两个对象中所有的属性值是否相等
    ObjectEquals(TObjectEqualsAssert.class),
    //校验是否为Null
    IsNULL(TEqualsNullAssert.class),
    //校验是否不同
    NotEquals(TNotEqualsAssert.class),
    //校验是否不是null
    NotNull(TEqualsNotNullAssert.class);

    @Getter
    private Class<? extends Assert> assertClass;

    TAssertType(Class<? extends Assert> assertClass) {
        this.assertClass = assertClass;
    }
}
