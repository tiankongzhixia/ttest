package com.time.ttest;

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.time.ttest.context.AbstractApplicationContext;
import com.time.ttest.context.TTestApplicationContext;
import com.time.ttest.listener.ApplicationListener;
import com.time.ttest.listener.EventPublishingRunListener;
import com.time.ttest.util.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.testng.CommandLineArgs;
import org.testng.TestNG;
import org.testng.TestNGException;
import org.testng.TestRunner;
import org.testng.collections.Lists;
import org.testng.internal.ExitCode;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class TTestApplication{
    private static final String FILE = "ttest.yml";

    @Getter
    private Injector injector;
    @Getter
    private List<ApplicationListener<?>> listeners;
    @Getter
    private Properties properties;

    public TTestApplication(){
        //获取配置文件
        loadProperties();
        printBanner();
        injector = Guice.createInjector(Stage.PRODUCTION,TTestModule.getModule(this));
        //添加监听器
        this.setListeners(ReflectionsUtil.getSubTypesOf(this.getClass().getPackage().getName(), ApplicationListener.class));
        EventPublishingRunListener listener = new EventPublishingRunListener(this);
        listener.starting();
        TTestApplicationContext context = createApplicationContext();
        preContext(context,listeners);
        listener.running(context);
    }

    private void loadProperties() {
        this.properties = PropertyUtil.loadFile(FILE,this.getClass());
    }


    /**
     * 添加监听器
     * @param applicationListeners 监听器列表
     */
    private void setListeners(Set<Class<? extends ApplicationListener>> applicationListeners) {
        listeners = Lists.newArrayList();
        applicationListeners.forEach(listener->{
            listeners.add(injector.getInstance(listener));
        });
    }

    private void printBanner() {
        TTestBanner tTestBanner = new TTestBanner();
        tTestBanner.printBanner(System.out);
    }


    private void preContext(AbstractApplicationContext context, List<ApplicationListener<?>> listeners) {
        listeners.forEach(context::addApplicationListener);
    }

    private TTestApplicationContext createApplicationContext() {
        TTestApplicationContext context = injector.getInstance(TTestApplicationContext.class);
        context.setInjector(injector);
        return context;
    }


    public static void run(Class clazz,String[] args){
        TestNG testNG = generateTestNg(clazz, args);
        System.exit(testNG.getStatus());
    }

    public static TestNG generateTestNg(Class clazz,String[] args){
        Properties properties = PropertyUtil.loadFile(FILE,clazz);
        properties.setProperty("package",clazz.getPackage().getName());
        TestNG testNG = new TestNG();
        CommandLineArgs cla = commandLineArgs(properties,args);
        try {
            testNG.setTestSuites(cla.suiteFiles);
            TestNgUtil.invokeTestNGMethod("validateCommandLineParameters",new Class[]{CommandLineArgs.class},testNG,cla);
            TestNgUtil.invokeTestNGMethod("configure",new Class[]{CommandLineArgs.class},testNG,cla);
            testNG.run();
        } catch (TestNGException ex) {
            if (TestRunner.getVerbose() > 1) {
                ex.printStackTrace(System.out);
            } else {
                log.error(ex.getMessage());
            }
            TestNgUtil.setPrivateParam("exitCode",testNG,ExitCode.newExitCodeRepresentingFailure());
        }
        return testNG;
    }

    private static CommandLineArgs commandLineArgs(Properties properties,String... args){
        CommandLineArgs cla = new CommandLineArgs();
        JCommander jCommander = new JCommander(cla);
        jCommander.parse(args);
        //添加配置的suit xml 并去重
        cla.suiteFiles = cla.suiteFiles.size()>0?cla.suiteFiles:getTestNgSuitFiles(properties);
        //添加 guice InjectorFactory
        cla.dependencyInjectoryFactoryClass = TTestInjectorFactory.class.getName();
        return cla;
    }

    /**
     *  获取配置文件的 suitefile
     * @param properties 配置文件参数
     * @return
     */
    private static List<String> getTestNgSuitFiles(Properties properties){
        List<String> suiteFiles = Lists.newArrayList();
        String[] ttestSuiteFiles =properties.getProperty("testng.suite.file").split(",");
        suiteFiles.addAll(Arrays.asList(ttestSuiteFiles));
        suiteFiles = suiteFiles.stream().distinct().collect(Collectors.toList());
        return suiteFiles;
    }
}
