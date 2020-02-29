package com.time.ttest.annotations;

import java.lang.annotation.*;

/**
 * 类spring @Service 注解
 * 可将标记@Service类的接口实现绑定到当前类
 * service -> serviceImpl
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Service {
}
