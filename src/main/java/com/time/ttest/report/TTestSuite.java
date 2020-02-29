package com.time.ttest.report;

import lombok.Data;

import java.util.List;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-26 21:21
 */
@Data
public class TTestSuite extends AbstractReport {

    List<TTestContext> result;
}
