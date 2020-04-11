package com.time.ttest.http;

import com.google.inject.Inject;
import com.time.ttest.annotations.HttpUser;
import com.time.ttest.http.HttpUserStack;
import com.time.ttest.util.ParamUtil;
import io.qameta.allure.util.NamingUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * http用户拦截器
 * 使用栈结构存储，一个方法
 *
 *
 * @Auther guoweijie

 * @Date 2020-04-10 18:59
 */
public class UserInterceptor implements MethodInterceptor {

    @Inject
    private HttpUserStack httpUserStack;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        HttpUser annotation = methodInvocation.getMethod().getAnnotation(HttpUser.class);
        String userName = annotation.value();
        if (ParamUtil.isProcessNameTemplate(annotation.value())){
            userName = ParamUtil.processNameTemplate(annotation.value(),methodInvocation.getMethod(),methodInvocation.getArguments());
        }
        httpUserStack.pushUser(userName);
        Object proceed = methodInvocation.proceed();
        httpUserStack.popUser();
        return proceed;
    }
}
