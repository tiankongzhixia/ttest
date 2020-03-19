package com.time.ttest.interceptor;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.time.ttest.File;
import com.time.ttest.GsonBuilder;
import com.time.ttest.annotations.DataProviderFile;
import com.time.ttest.context.TTestApplicationContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import java.lang.reflect.Method;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-18 19:30
 */
public class DataProviderFileInterceptor implements MethodInterceptor {

    @Inject
    private TTestApplicationContext context;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        File file = null;
        if (method.isAnnotationPresent(DataProviderFile.class)){
            DataProviderFile dataProviderFile = method.getAnnotation(DataProviderFile.class);
            file = DataProviderFileHolder.getFile(dataProviderFile.value());
            file.setGson(getDataProviderGson(dataProviderFile.gson()));
            return file.dataProviderTransfer(dataProviderFile.transform());
        }
        return methodInvocation.proceed();
    }

    private Gson getDataProviderGson(Class<? extends GsonBuilder> clazz){
        GsonBuilder build = (GsonBuilder) context.getInjector().getInstance(clazz);
        return build.build();
    }

}
