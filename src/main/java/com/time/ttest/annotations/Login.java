package com.time.ttest.annotations;

import java.lang.annotation.*;

/**
 * 标记登录用户,
 * 将配置一个Unirest config
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface Login {

    /**
     * 用户名称
     * @return
     */
    String value() default "";

    /**
     * 是否默认用户
     * @return
     */
    boolean isDefault() default false;
}
