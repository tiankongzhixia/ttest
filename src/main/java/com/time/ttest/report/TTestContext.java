package com.time.ttest.report;

import lombok.Data;

import java.util.List;

/**
 * @Auther guoweijie

 * @Date 2020-02-27 00:31
 */
@Data
public class TTestContext extends AbstractReport{

    List<TTestResult> tests;
}
