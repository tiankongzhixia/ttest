package com.time.ttest;

import com.time.ttest.context.TTestApplicationContext;
import com.time.ttest.event.ApplicationReportEvent;
import com.time.ttest.report.TestNGReport;
import com.time.ttest.util.AttributesUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;
import org.testng.xml.XmlSuite;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class TTestMainListener  implements IExecutionListener, ITestListener, ISuiteListener, IInvokedMethodListener,IReporter,IAnnotationTransformer {

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
    }

    public void transform(
            ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        String method = testMethod.getName();
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
}
