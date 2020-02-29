package com.time.ttest.report;

import lombok.Data;

import java.util.List;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-27 03:55
 */
@Data
public class TTestResult extends AbstractReport {

    private List<TTestMethod> methods;
}
