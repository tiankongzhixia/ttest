package com.time.ttest.annotations;

import java.lang.annotation.*;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-28 05:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface Asserts {

    Assert[] value();
}
