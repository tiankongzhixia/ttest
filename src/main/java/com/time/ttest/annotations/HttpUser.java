package com.time.ttest.annotations;

import java.lang.annotation.*;

/**
 * @Auther guoweijie

 * @Date 2020-04-08 03:08
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
public @interface HttpUser {

    /**
     * http登录用户名
     * @return
     */
    String value() default "main";
}
