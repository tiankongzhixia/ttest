package com.time.ttest.interceptor;

import com.google.inject.Inject;
import com.time.ttest.TTestException;
import com.time.ttest.file.FileSubSuffix;
import com.time.ttest.file.File;
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
            try {
                return file.dataProviderTransfer(dataProviderFile.transform());
            }catch (Exception e){
                throw new TTestException("DataProviderFile 转换失败 \nclass: ["+method.getDeclaringClass().getName()+"]\nmethod: ["+method.getName()+"]\nerror: "+e.getMessage());
            }

        }
        return methodInvocation.proceed();
    }

}
