package com.time.ttest.listener;

import com.google.inject.Inject;
import com.time.ttest.TTestApplication;
import com.time.ttest.annotations.Login;
import com.time.ttest.event.ApplicationStartedEvent;
import com.time.ttest.http.UnirestManager;
import com.time.ttest.http.UserFactory;
import com.time.ttest.util.ReflectionsUtil;
import kong.unirest.Config;
import kong.unirest.UnirestInstance;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Unirest 初始化 加载@Login配置文件
 * @Auther guoweijie

 * @Date 2020-02-23 17:05
 */
public class UnirestInitializerListener implements ApplicationListener<ApplicationStartedEvent>{

    private final UnirestManager unirestManager;
    private final UserFactory userFactory;
    @Inject
    public UnirestInitializerListener(UnirestManager unirestManager, UserFactory userFactory) {
        this.unirestManager = unirestManager;
        this.userFactory = userFactory;
    }

    public void init(ApplicationStartedEvent event){
        TTestApplication tTestApplication = (TTestApplication) event.getSource();
        Set<Method> methodsAnnotatedWith = ReflectionsUtil.getMethodsAnnotatedWith(
                tTestApplication.getProperties().getProperty("package"),
                Login.class);
        methodsAnnotatedWith.forEach(method -> {
            Config config = new Config();
            try {
                config = (Config) method.invoke(method.getDeclaringClass().newInstance(), (Object[]) method.getParameterTypes());
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
            Login annotation = method.getAnnotation(Login.class);
            if (StringUtils.isNotEmpty(annotation.value())){
                unirestManager.put(annotation.value(),new UnirestInstance(config));
                if (annotation.isDefault()){
                    userFactory.setDefaultUser(annotation.value());
                }
            }else {
                unirestManager.putDefault(new UnirestInstance(config));
            }
        });
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        init(event);
    }
}
