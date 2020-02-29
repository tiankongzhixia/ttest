package com.time.ttest.annotations;

import java.lang.annotation.*;

/**
 * 标记最近一次http使用哪个用户的配置
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
public @interface User {

    /**
     * 用户
     */
    String value();

}
