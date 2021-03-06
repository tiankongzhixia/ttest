package com.time.ttest.module;

import com.google.inject.name.Names;
import com.time.ttest.DruidDataSourceProvider;
import com.time.ttest.TTestApplication;
import com.time.ttest.annotations.MapperScan;
import com.time.ttest.annotations.MapperScans;
import com.time.ttest.util.PropertyUtil;
import com.time.ttest.util.ReflectionsUtil;
import lombok.Getter;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.testng.collections.Lists;

import java.util.List;
import java.util.Properties;
import java.util.Set;


public class TTestMyBatisModule extends MyBatisModule {

    private TTestApplication application;
    private static final String DATASOURCE_PREFIX = "ttest.datasource.druid.";

    @Getter
    private MapperScan mapperScan;


    @Override
    protected void initialize() {
        //替换配置文件中 druid.{数据库名} 为 jdbc DruidDataSourceProvider 配置中使用
        Properties properties = PropertyUtil.replacePropertyPrefix(application.getProperties(),
                DATASOURCE_PREFIX + mapperScan.datasource(), "jdbc");
        properties.put("mybatis.environment.id",mapperScan.datasource());
        Names.bindProperties(binder(), properties);
        bindDataSourceProviderType(DruidDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addMapperClasses(mapperScan.value());
        addSimpleAliases(mapperScan.value());
    }

    public String getMapperPackage(){
        return mapperScan.value();
    }

    public static TTestMyBatisModule getModule(TTestApplication application,MapperScan mapperScan) {
        TTestMyBatisModule myBatisModule = new TTestMyBatisModule();
        myBatisModule.application = application;
        myBatisModule.mapperScan = mapperScan;
        return myBatisModule;
    }

    /**
     * 根据MapperScan MapperScans获取多个Module配置
     * @return
     */
    public static List<TTestMyBatisModule> getModules(TTestApplication application,Class clazz){
        List<TTestMyBatisModule> moduleList = Lists.newArrayList();
        if (clazz.isAnnotationPresent(MapperScan.class)){
            MapperScan mapperScan = (MapperScan) clazz.getAnnotation(MapperScan.class);
            moduleList.add(getModule(application,mapperScan));
        }
        if (clazz.isAnnotationPresent(MapperScans.class)){
            MapperScans mapperScans = (MapperScans) clazz.getAnnotation(MapperScans.class);
            MapperScan[] mapperScanList = mapperScans.value();
            for (MapperScan mapperScan:mapperScanList){
                moduleList.add(getModule(application,mapperScan));
            }
        }
        return moduleList;
    }
}
