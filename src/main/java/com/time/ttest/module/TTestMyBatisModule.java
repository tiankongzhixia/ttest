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
        Properties properties = PropertyUtil.getPropertyPrefix(application.getProperties(),
                DATASOURCE_PREFIX + mapperScan.datasource(), "jdbc");
        properties.put("mybatis.environment.id",mapperScan.datasource());
        Names.bindProperties(binder(), properties);
        bindDataSourceProviderType(DruidDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addMapperClasses(mapperScan.value());
        addSimpleAliases(mapperScan.value());
    }


    public static TTestMyBatisModule getModule(TTestApplication application,MapperScan mapperScan) {
        TTestMyBatisModule myBatisModule = new TTestMyBatisModule();
        myBatisModule.application = application;
        myBatisModule.mapperScan = mapperScan;
        return myBatisModule;
    }

    public static List<TTestMyBatisModule> getModules(TTestApplication application){
        Set<Class<?>> mapperScanClasss = ReflectionsUtil.getTypesAnnotatedWith(application.getProperties().getProperty("package"), MapperScan.class);
        Set<Class<?>> mapperScansClasss = ReflectionsUtil.getTypesAnnotatedWith(application.getProperties().getProperty("package"), MapperScans.class);
        List<TTestMyBatisModule> moduleList = Lists.newArrayList();
        for (Class clazz:mapperScanClasss){
            if (clazz.getAnnotation(MapperScan.class) != null){
                MapperScan mapperScan = (MapperScan) clazz.getAnnotation(MapperScan.class);
                moduleList.add(getModule(application,mapperScan));
            }
        }

        for (Class clazz:mapperScansClasss){
            if (clazz.getAnnotation(MapperScans.class) != null){
                MapperScans mapperScans = (MapperScans) clazz.getAnnotation(MapperScans.class);
                MapperScan[] mapperScanList = mapperScans.value();
                for (MapperScan mapperScan:mapperScanList){
                    moduleList.add(getModule(application,mapperScan));
                }
            }
        }
        return moduleList;
    }
}
