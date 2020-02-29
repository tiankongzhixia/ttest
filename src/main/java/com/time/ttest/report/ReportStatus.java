package com.time.ttest.report;

import org.testng.ITestResult;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-26 21:50
 */
public enum ReportStatus {

    CREATED,
    /**
     * 报告状态 成功
     */
    SUCCESS,

    /**
     * 报告状态 失败
     */
    FAILURE,

    /**
     * 报告状态 跳过
     */
    SKIP,

    STARTED,

    SUCCESS_PERCENTAGE_FAILURE;


    public static ReportStatus of(Boolean pass){
        return pass?ReportStatus.SUCCESS :ReportStatus.FAILURE;
    }

    public static ReportStatus of(int iTestResultStatus){
        if (iTestResultStatus == ITestResult.CREATED){
            return ReportStatus.CREATED;
        }
        if (iTestResultStatus == ITestResult.SUCCESS){
            return ReportStatus.SUCCESS;
        }
        if (iTestResultStatus == ITestResult.FAILURE){
            return ReportStatus.FAILURE;
        }
        if (iTestResultStatus == ITestResult.SKIP){
            return ReportStatus.SKIP;
        }
        if (iTestResultStatus == ITestResult.SUCCESS_PERCENTAGE_FAILURE){
            return ReportStatus.SUCCESS_PERCENTAGE_FAILURE;
        }
        if (iTestResultStatus == ITestResult.STARTED){
            return ReportStatus.STARTED;
        }
        return ReportStatus.FAILURE;
    }
}
