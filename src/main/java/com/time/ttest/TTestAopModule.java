package com.time.ttest;

import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.time.ttest.annotations.Assert;
import com.time.ttest.annotations.Asserts;
import com.time.ttest.annotations.User;
import com.time.ttest.interceptor.AssertInterceptor;
import com.time.ttest.interceptor.UserInterceptor;
import com.time.ttest.module.BaseModule;
import org.aopalliance.intercept.MethodInterceptor;
import org.testng.collections.Lists;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * 切面注册器
 * @Auther guoweijie

 * @Date 2020-02-25 22:50
 */
public class TTestAopModule extends BaseModule {

    static TTestAopModule getModule(){
        return new TTestAopModule();
    }

    @Override
    public void configure(){
        bindMyInterceptor(Matchers.any(),Matchers.annotatedWith(User.class),UserInterceptor.class);
        bindMyInterceptor(Matchers.any(),Matchers.annotatedWith(Assert.class),AssertInterceptor.class);
        bindMyInterceptor(Matchers.any(),Matchers.annotatedWith(Asserts.class),AssertInterceptor.class);
    }

    /**
     * 绑定拦截
     * @param classMatcher guice 匹配方法规则
     * @param methodMatcher 匹配处理方法规则
     * @param methodInterceptorClasss 拦截器
     */
    @SafeVarargs
    private final void bindMyInterceptor(Matcher<? super Class<?>> classMatcher,
                                         Matcher<? super Method> methodMatcher,
                                         Class<? extends MethodInterceptor>... methodInterceptorClasss) {
        List<MethodInterceptor> interceptors = Lists.newArrayList();
        for (Class clazz:methodInterceptorClasss){
            //实例化拦截器
            MethodInterceptor interceptor = null;
            try {
                interceptor = (MethodInterceptor) clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            //注册拦截器
            requestInjection(interceptor);
            interceptors.add(interceptor);
        }
        bindInterceptor(classMatcher,methodMatcher,interceptors.toArray(new MethodInterceptor[0]));
    }

}