package com.time.ttest.report;

import com.time.ttest.http.HttpSummary;
import lombok.Data;

import java.util.List;

/**
 * @Auther guoweijie

 * @Date 2020-02-26 21:18
 */
@Data
public class TTestReport extends AbstractReport {
    public TTestReport() {
        super();
    }


    private String time;

    private Integer testsCount;

    /**
     * testng运行情况
     */
    List<TTestSuite> suites;

    /**
     * 所有http请求
     */
    List<HttpSummary> httpSummaries;

}
