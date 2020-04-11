package com.time.ttest;

import com.google.gson.Gson;
import com.google.inject.Singleton;
import com.time.ttest.annotations.EnableHttpManager;
import com.time.ttest.annotations.GsonBean;
import com.time.ttest.annotations.HttpUserBean;
import com.time.ttest.context.TTestApplicationContext;
import com.time.ttest.event.ApplicationReportEvent;
import com.time.ttest.event.ApplicationRunningEvent;
import com.time.ttest.http.HttpManagerFactory;
import com.time.ttest.listener.ApplicationListener;
import com.time.ttest.report.TestNGReport;
import com.time.ttest.util.AttributesUtil;
import com.time.ttest.util.GsonUtil;
import com.time.ttest.util.ReflectionsUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

@Slf4j
@Singleton
public class TTestMainListener implements ISuiteListener,IReporter, ApplicationListener<ApplicationRunningEvent> {

    private static TTestApplicationContext context;
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";

    public void onStart(ISuite suite) {
        AttributesUtil.set(suite,START_TIME, System.currentTimeMillis());
    }

    public void onFinish(ISuite suite) {
        AttributesUtil.set(suite,END_TIME, System.currentTimeMillis());
    }

    public void generateReport(
            List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        context.publishEvent(new ApplicationReportEvent(new TestNGReport(xmlSuites,suites,outputDirectory)));
    }

    @Override
    public void onApplicationEvent(ApplicationRunningEvent event) {
        context = (TTestApplicationContext) event.getTTestContext();
        TTestApplication application = (TTestApplication) event.getSource();
        HttpManagerFactory httpManagerFactory = application.getInjector().getInstance(HttpManagerFactory.class);
        Set<Class<?>> configurations = application.getConfigurations();
        for (Class clazz:configurations){
            Method[] declaredMethods = clazz.getDeclaredMethods();
            if (clazz.isAnnotationPresent(EnableHttpManager.class)){
                EnableHttpManager enableHttpManager = (EnableHttpManager) clazz.getAnnotation(EnableHttpManager.class);
                httpManagerFactory.setHttpClientType(enableHttpManager.value());
            }
            try {
                Object instance = clazz.newInstance();
                for (Method declaredMethod : declaredMethods) {
                    Object result = declaredMethod.invoke(instance, (Object[]) declaredMethod.getParameters());
                    if (declaredMethod.isAnnotationPresent(HttpUserBean.class)){
                        HttpUserBean userBean = declaredMethod.getAnnotation(HttpUserBean.class);
                        httpManagerFactory.getHttpManager().add(userBean.value(),result,userBean.isMain());
                    }
                    if (declaredMethod.isAnnotationPresent(GsonBean.class)){
                        GsonUtil.setGson((Gson) result);
                    }
                }
            } catch (Exception e) {
                throw new TTestException(clazz.getName()+"配置失败 error:"+e);
            }
        }

    }
}
