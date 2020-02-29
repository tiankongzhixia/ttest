package com.time.ttest.report;

import com.google.inject.Inject;
import com.time.ttest.context.TTestApplicationContext;
import com.time.ttest.event.ApplicationEndEvent;
import com.time.ttest.event.ApplicationReportEvent;
import com.time.ttest.listener.ApplicationListener;
import com.time.ttest.util.AttributesUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.collections.Lists;
import org.testng.xml.XmlClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther guoweijie

 * @Date 2020-02-26 20:42
 */
@Slf4j
public class TTestReportListener implements ApplicationListener<ApplicationReportEvent> {

    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    @Inject
    private TTestApplicationContext context;

    @Override
    public void onApplicationEvent(ApplicationReportEvent event) {
        TestNGReport testNGReport = (TestNGReport) event.getSource();
        TTestReport report = new TTestReport(DateTime.now().toString(STANDARD_FORMAT));
        List<ISuite> suites = testNGReport.getSuites();
        List<TTestSuite> suiteList = Lists.newArrayList();
        for (ISuite suite:suites){
            Map<String, ISuiteResult> results = suite.getResults();
            if (results.size() == 0){
                continue;
            }
            suiteList.add(getSuite(suite));
        }
        report.setTestsCount(suites.stream().mapToInt(iSuite-> iSuite.getAllMethods().size()).sum());
        report.setSuites(suiteList);
        report.setPassTestsCount(suiteList.stream().mapToInt(TTestSuite::getPassTestsCount).sum());
        report.setFailedTestsCount(suiteList.stream().mapToInt(TTestSuite::getFailedTestsCount).sum());
        report.setSkippedTestsCount(suiteList.stream().mapToInt(TTestSuite::getSkippedTestsCount).sum());
        report.setDurationTime(suiteList.stream().mapToLong(TTestSuite::getDurationTime).sum());
        context.publishEvent(new ApplicationEndEvent(report));
    }


    protected TTestSuite getSuite(ISuite iSuite){
        TTestSuite suite = new TTestSuite();
        List<TTestContext> tTestContexts = loaderResult(iSuite);
        suite.setName(iSuite.getName());
        suite.setStartTime(new DateTime(iSuite.getAttribute(START_TIME)).toString(STANDARD_FORMAT));
        suite.setEndTime(new DateTime(iSuite.getAttribute(END_TIME)).toString(STANDARD_FORMAT));
        suite.setDurationTime((long) iSuite.getAttribute(END_TIME) - (long)iSuite.getAttribute(START_TIME));
        suite.setPassTestsCount(tTestContexts.stream().mapToInt(TTestContext::getPassTestsCount).sum());
        suite.setFailedTestsCount(tTestContexts.stream().mapToInt(TTestContext::getFailedTestsCount).sum());
        suite.setSkippedTestsCount(tTestContexts.stream().mapToInt(TTestContext::getSkippedTestsCount).sum());
        suite.setResult(tTestContexts);
        iSuite.getAllMethods();
        return suite;
    }

    private List<TTestContext> loaderResult(ISuite iSuite) {
        Map<String, ISuiteResult> iResults = iSuite.getResults();
        List<TTestContext> results = Lists.newArrayList();
        iResults.forEach((resultName,result)->{
            results.add(getContext(result));
        });
        return results;
    }

    private TTestContext getContext(ISuiteResult iResult) {
        TTestContext context = new TTestContext();
        context.setName(iResult.getTestContext().getName());
        context.setStartTime(new DateTime(iResult.getTestContext().getStartDate().getTime()).toString(STANDARD_FORMAT));
        context.setEndTime(new DateTime(iResult.getTestContext().getEndDate().getTime()).toString(STANDARD_FORMAT));
        context.setDurationTime(iResult.getTestContext().getEndDate().getTime() - iResult.getTestContext().getStartDate().getTime());
        context.setPassTestsCount(iResult.getTestContext().getPassedTests().size());
        context.setFailedTestsCount(iResult.getTestContext().getFailedTests().size());
        context.setSkippedTestsCount(iResult.getTestContext().getSkippedTests().size());
        context.setTests(loaderTests(iResult.getTestContext()));
        context.setAttributes(AttributesUtil.getAll(iResult.getTestContext()));
        return context;
    }

    private List<TTestResult> loaderTests(ITestContext testContext) {
        List<TTestResult> tTestResults = Lists.newArrayList();
        List<XmlClass> testClasses = testContext.getCurrentXmlTest().getClasses();
        HashMap<String,List<TTestMethod>> classMethod = loaderMethod(testContext);
        testClasses.forEach(xmlClass -> {
            Annotation[] annotations = xmlClass.getSupportClass().getAnnotations();
            List<TTestMethod> tTestMethods = classMethod.get(xmlClass.getName());
            TTestResult tTestResult = new TTestResult();
            tTestResult.setName(xmlClass.getName());
            tTestResult.setMethods(classMethod.get(xmlClass.getName()));
            tTestResult.setPassTestsCount((int) tTestMethods.stream()
                    .filter(method -> method.getStatus() == ReportStatus.SUCCESS)
                    .map(TTestMethod::getStatus).count());
            tTestResult.setFailedTestsCount((int) tTestMethods.stream()
                    .filter(method -> method.getStatus() == ReportStatus.FAILURE)
                    .map(TTestMethod::getStatus).count());
            tTestResult.setSkippedTestsCount((int) tTestMethods.stream()
                    .filter(method -> method.getStatus() == ReportStatus.SKIP)
                    .map(TTestMethod::getStatus).count());
            tTestResult.setOtherTestsCount((int) tTestMethods.stream().filter(
                    method -> method.getStatus() != ReportStatus.SKIP
                    && method.getStatus() != ReportStatus.FAILURE &&
                    method.getStatus() != ReportStatus.SUCCESS)
                    .map(TTestMethod::getStatus).count());
            tTestResult.setDurationTime(tTestMethods.stream().mapToLong(TTestMethod::getDurationTime).sum());
            try {
                AttributesUtil.setAllureAnnotation(tTestResult,annotations);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            tTestResults.add(tTestResult);
        });
        return tTestResults;
    }

    private HashMap<String, List<TTestMethod>> loaderMethod(ITestContext testContext) {
        HashMap<String, List<TTestMethod>> methodMap = new HashMap<String, List<TTestMethod>>();
        List<IResultMap> allResultMap = Lists.newArrayList(testContext.getPassedTests(),
                testContext.getFailedTests(),
                testContext.getSkippedTests());
        allResultMap.forEach(iResultMap -> {
            iResultMap.getAllResults().forEach(iTestResult -> {
                TTestMethod tTestMethod = new TTestMethod();
                tTestMethod.setName(iTestResult.getName());
                tTestMethod.setStatus(ReportStatus.of(iTestResult.getStatus()));
                tTestMethod.setStartTime(new DateTime(iTestResult.getStartMillis()).toString(STANDARD_FORMAT));
                tTestMethod.setEndTime(new DateTime(iTestResult.getEndMillis()).toString(STANDARD_FORMAT));
                tTestMethod.setDurationTime(iTestResult.getEndMillis() - iTestResult.getStartMillis());
                tTestMethod.setThrowable(iTestResult.getThrowable());
                tTestMethod.setAttributes(AttributesUtil.getAll(iTestResult));
                String className = iTestResult.getMethod().getRealClass().getName();
                if (methodMap.containsKey(className)){
                    methodMap.get(className).add(tTestMethod);
                }else {
                    methodMap.put(className,Lists.newArrayList(tTestMethod));
                }
            });
        });
        return methodMap;
    }
}
