package com.time.ttest.proxy;

import com.beust.jcommander.JCommander;
import com.time.ttest.TTestApplication;
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
import java.util.stream.Collectors;

/**
 * testng代理类
 * @Auther guoweijie

 * @Date 2020-03-22 23:07
 */
@Slf4j
public class TTestNGProxy<T> extends Proxy<T>{

    /**
     * 配置文件
     */
    private Properties properties;

    /**
     * 命令行参数
     */
    private String[] args;

    public TTestNGProxy(TestNG testNG) {
        setBeProxy((T) testNG);
    }

    public TTestNGProxy(TestNG testNG,Properties properties) {
        setBeProxy((T) testNG);
        this.properties = properties;
    }

    public TTestNGProxy(TestNG testNG,Properties properties,String[] args) {
        setBeProxy((T) testNG);
        this.properties = properties;
        this.args = args;
    }

    public TestNG getTestNG(){
        return (TestNG) getBeProxy();
    }

    public void run(){
        CommandLineArgs cla = commandLineArgs(properties,args);
        getTestNG().setTestSuites(cla.suiteFiles);
        //调用testng的方法，校验参数
        invokeMethod("validateCommandLineParameters",new Class[]{CommandLineArgs.class},cla);
        //调用testng的方法，配置参数
        invokeMethod("configure",new Class[]{CommandLineArgs.class},cla);
        try {
            getTestNG().run();
        }catch (TestNGException ex) {
            if (TestRunner.getVerbose() > 1) {
                ex.printStackTrace(System.out);
            } else {
                log.error(ex.getMessage());
            }
            modifyPrivateParam("exitCode", ExitCode.newExitCodeRepresentingFailure());
        }
    }

    private CommandLineArgs commandLineArgs(Properties properties,String... args){
        CommandLineArgs cla = new CommandLineArgs();
        JCommander jCommander = new JCommander(cla);
        jCommander.parse(args);
        //如果启动参数没有指定xml，则使用配置文件中的
        cla.suiteFiles = cla.suiteFiles.size()>0?cla.suiteFiles:getTestNgSuitFiles(properties);
        //添加 guice InjectorFactory
        cla.dependencyInjectoryFactoryClass = TTestApplication.class.getName();
        return cla;
    }


    /**
     *  获取配置文件的 suitefile
     * @param properties 配置文件参数
     * @return
     */
    private List<String> getTestNgSuitFiles(Properties properties){
        List<String> suiteFiles = Lists.newArrayList();
        String[] ttestSuiteFiles =properties.getProperty("testng.suite.file").split(",");
        suiteFiles.addAll(Arrays.asList(ttestSuiteFiles));
        suiteFiles = suiteFiles.stream().distinct().collect(Collectors.toList());
        return suiteFiles;
    }
}
