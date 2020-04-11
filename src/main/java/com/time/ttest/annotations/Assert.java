package com.time.ttest.annotations;


import com.time.ttest.asserts.AssertInterceptor;
import com.time.ttest.asserts.TAssertType;

import java.lang.annotation.*;

/**
 * 断言注解 默认只校验 assertEquals
 * @Auther guoweijie

 * @Date 2020-02-25 21:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
@Repeatable(Asserts.class)
public @interface Assert {

    /**
     * 使用json-path
     */
    String path() default "$";

    /**
     * 预期结果
     */
    String expect() default "";

    /**
     * 预期结果的json-path
     * @return
     */
    String expectPath() default "$";

    /**
     * 使用的校验类型
     * @return
     */
    TAssertType assertType() default TAssertType.Equals;

    /**
     * 校验失败抛出异常信息
     * @return
     */
    String errorMsg() default "";
}
