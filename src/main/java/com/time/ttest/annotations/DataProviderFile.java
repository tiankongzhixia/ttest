package com.time.ttest.annotations;

import com.google.gson.JsonObject;
import java.lang.annotation.*;

/**
 * @Auther guoweijie

 * @Date 2020-03-18 19:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface DataProviderFile {
    /**
     * 路径
     */
    String value();

    /**
     * 需要转换的类型
     */
    Class<?> transform() default JsonObject.class;
}
