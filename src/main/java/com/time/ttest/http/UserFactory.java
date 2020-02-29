package com.time.ttest.http;

import com.google.inject.Singleton;
import com.time.ttest.annotations.User;
import com.time.ttest.util.ParamUtil;
import io.qameta.allure.util.NamingUtils;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther guoweijie

 * @Date 2020-02-24 13:18
 */
@Singleton
public class UserFactory {

    private ConcurrentHashMap<String,UserManager> threadUserManager = new ConcurrentHashMap<>();

    @Getter
    @Setter
    private String defaultUser;

    public UserManager getThreadUserManager(){
        if (threadUserManager.containsKey(getThreadName())){
            return threadUserManager.get(getThreadName());
        }
        UserManager userManager = new UserManager(defaultUser);
        threadUserManager.put(getThreadName(),userManager);
        return getThreadUserManager();
    }

    private String getThreadName(){
        return Thread.currentThread().getName();
    }

    /**
     * 设置方法的userName
     * @param parameters 方法参数
     * @param method 方法
     */
    public void setMethodThreadUserName(Object[] parameters, Method method,Boolean isDefault) {
        Map<String, Object> parametersMap = ParamUtil.getParametersMap(method, parameters);
        if (method.isAnnotationPresent(User.class)){
            User annotation = method.getAnnotation(User.class);
            String userName = NamingUtils.processNameTemplate(annotation.value(),parametersMap);
            if (isDefault){
                this.getThreadUserManager().setDefaultUser(userName);
            }
            this.getThreadUserManager().setUserName(userName);
        }
    }
}
