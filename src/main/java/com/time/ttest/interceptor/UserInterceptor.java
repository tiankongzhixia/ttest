package com.time.ttest.interceptor;


import com.google.inject.Inject;
import com.time.ttest.http.UserFactory;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 用户拦截器
 */
@Slf4j
public class UserInterceptor implements MethodInterceptor {

    @Inject
    private UserFactory userFactory;


    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        userFactory.setMethodThreadUserName(methodInvocation.getArguments(), method,false);
        return methodInvocation.proceed();
    }
}
