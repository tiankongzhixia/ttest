package com.time.ttest.report;

import lombok.Data;

import java.util.List;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-26 21:18
 */
@Data
public class TTestReport extends AbstractReport {

    public TTestReport(String time) {
        super();
        this.time = time;
    }

    private String time;

    private Integer testsCount;

    List<TTestSuite> suites;

}
