package com.time.ttest.test.dataProviderFile;

import com.time.ttest.annotations.DataProviderFile;
import com.time.ttest.test.TestConfig;
import com.time.ttest.test.ast.TTestAssertDTO;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 19:41
 */
@Guice
@Slf4j
public class DataProviderFileTest {

    @Test(dataProvider = "data1",dataProviderClass = TestConfig.class)
    public void test1(TTestAssertDTO assertDTO){
        log.info("dataproviderFile :{}",assertDTO.toString());
    }

    @Test(dataProvider = "data2",dataProviderClass = TestConfig.class)
    public void test2(TTestAssertDTO assertDTO){
        log.info("dataproviderFile :{}",assertDTO.toString());
    }
}
