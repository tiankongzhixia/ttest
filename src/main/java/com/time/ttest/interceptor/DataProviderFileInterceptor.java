package com.time.ttest.interceptor;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.time.ttest.FileSubSuffix;
import com.time.ttest.file.File;
import com.time.ttest.GsonBuilder;
import com.time.ttest.annotations.DataProviderFile;
import com.time.ttest.context.TTestApplicationContext;
import com.time.ttest.file.JsonFile;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import java.lang.reflect.Method;

/**
 * @DataProviderFile 注解拦截器
 * 解析注解内的参数，将文件内容转为对应实体，并以Object[][]返回，供testng DataProvider 使用
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
            //如果是json 需要注入指定的gson
            if (file.getSuffix() == FileSubSuffix.JSON){
                JsonFile jsonFile = (JsonFile) file;
                jsonFile.setGson(getDataProviderGson(dataProviderFile.gson()));
            }
            return file.dataProviderTransfer(dataProviderFile.transform());
        }
        return methodInvocation.proceed();
    }

    /**
     * 获取gson实例
     * @param clazz
     * @return
     */
    private Gson getDataProviderGson(Class<? extends GsonBuilder> clazz){
        GsonBuilder build = (GsonBuilder) context.getInjector().getInstance(clazz);
        return build.build();
    }

}
