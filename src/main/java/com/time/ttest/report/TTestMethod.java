package com.time.ttest.report;

import lombok.Data;

/**
 * @Auther guoweijie

 * @Date 2020-02-27 03:15
 */
@Data
public class TTestMethod extends AbstractReport{

    /**
     * 是否重试
     */
    private boolean retried;

    /**
     * 所属组
     */
    private String[] groups;

    private Throwable throwable;
}
