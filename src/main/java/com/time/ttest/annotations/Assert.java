package com.time.ttest.annotations;


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
    String expect();

    /**
     * 预期结果的类型
     * 校验时会将 expect 转换
     * {@link com.time.ttest.interceptor.AssertInterceptor}
     */
    Class transform() default String.class;
}
