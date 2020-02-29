package com.time.ttest.annotations;

import java.lang.annotation.*;

/**
 * 添加该注解的类将会被扫描 注入到guice
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-28 08:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Module {
}
