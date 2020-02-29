package com.time.ttest.report;

import lombok.Data;

import java.util.List;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-27 00:31
 */
@Data
public class TTestContext extends AbstractReport{

    List<TTestResult> tests;
}
