# TTest 轻量级测试框架
结合Testng+Mybatis+Guice+Unirest完成的测试框架,可以快速结合业务开展自动化测试工作。

使用Druid连接池,支持多数据源。

注解断言,支持json-path格式。

兼容Allure注解,测试结束生成JSON报告可配置发送到hook地址,进行解析/存储。

## 快速开始

#### 一、maven
```
   <dependency>
       <groupId>com.timemf</groupId>
       <artifactId>t-test</artifactId>
       <version>1.0-SNAPSHOT</version>
   </dependency> 
```

#### 二、IDEA调试

需要在Run/Debug Configuration -> Testng running params添加参数

```
    -dependencyinjectorfactory com.ttest.TTestInjectorFactory
```

#### 三、添加Guice注解

在测试类上添加注解 `@Guice` 以便testng自动发现

#### 四、添加配置文件 ttest.yml

基本配置
```
//包名
package: test 
//配置testng启动的xml,多xml用,分隔
//使用TTestApplication.run 启动时必填
testng:
  suite:
    file: /Users/***/test-demo/src/main/resources/test1.xml,/Users/***/test-demo/src/main/resources/test2.xml
```






