package com.time.ttest;

import com.google.common.collect.Lists;
import com.time.ttest.annotations.User;
import com.time.ttest.context.TTestApplicationContext;
import com.time.ttest.event.ApplicationReportEvent;
import com.time.ttest.http.UserFactory;
import com.time.ttest.report.TestNGReport;
import com.time.ttest.util.AttributesUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class TTestMainListener  implements IExecutionListener, ITestListener, ISuiteListener, IInvokedMethodListener,IReporter {

    private TTestApplicationContext context;
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";


    public void onExecutionStart() {

    }

    @SneakyThrows
    public void onStart(ISuite suite) {
        AttributesUtil.set(suite,START_TIME, System.currentTimeMillis());
        if (null == context){
            context = suite.getParentInjector().getInstance(TTestApplicationContext.class);
        }
    }

    public void onFinish(ISuite suite) {
        AttributesUtil.set(suite,END_TIME, System.currentTimeMillis());
    }

    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
    }

    public void onTestStart(ITestResult result) {
        List<Class<?>> paramClasses = getMethodParameterTypes(result.getParameters());
        try {
            Method method = result.getMethod().getRealClass().getDeclaredMethod(result.getName(),paramClasses.toArray(new Class[0]));
            UserFactory userFactory = context.getInjector().getInstance(UserFactory.class);
            //设置http 用户,当前test没有，从class找
            if (method.isAnnotationPresent(User.class)){
                userFactory.setMethodThreadUserName(result.getParameters(),method,true);
            }else if (method.getClass().isAnnotationPresent(User.class)){
                String value = method.getClass().getAnnotation(User.class).value();
                userFactory.getThreadUserManager().setDefaultUser(value);
                userFactory.getThreadUserManager().setUserName(value);
            }
            //设置allure注解内容
            AttributesUtil.setAllureAnnotation(result,method);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public void onTestSuccess(ITestResult result) {
    }

    public void onTestFailure(ITestResult result) {
    }

    public void onTestSkipped(ITestResult result) {
    }

    public void generateReport(
            List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        context.publishEvent(new ApplicationReportEvent(new TestNGReport(xmlSuites,suites,outputDirectory)));
    }

    /**
     * 获取方法参数类型
     * @param parameters 方法参数
     * @return
     */
    private List<Class<?>> getMethodParameterTypes(Object[] parameters) {
        List<Class<?>> classes = Lists.newArrayList();
        for (Object parameter:parameters){
            classes.add(parameter.getClass());
        }
        return classes;
    }

}
