# TTest 轻量级测试框架
结合Testng+Mybatis+Guice+Unirest完成的测试框架,可以快速结合业务开展自动化测试工作。

使用Druid连接池,支持多数据源。

配置断言,支持json-path格式。

兼容Allure注解,测试结束生成JSON报告可配置发送到hook地址,进行解析/存储。

## 快速开始
maven



IDEA调试

需要在Run/Debug Configuration -> Testng running params添加参数

`-dependencyinjectorfactory com.ttest.TTestInjectorFactory`







