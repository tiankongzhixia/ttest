package com.time.ttest.report;

import lombok.Data;
import org.testng.internal.Attributes;

import java.util.Map;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-26 21:45
 */
@Data
public abstract class AbstractReport extends Attributes implements Report {

    public AbstractReport() {
    }
    public AbstractReport(String name, ReportStatus status) {
        this.name = name;
        this.status = status;
    }

    private String name;

    /**
     * 状态
     */
    private ReportStatus status;

    /**
     * 开始时间 毫秒
     */
    private String startTime;

    /**
     * 结束时间 毫秒
     */
    private String endTime;

    /**
     * 持续时间
     */
    private long durationTime;

    /**
     * 通过用例数
     */
    private Integer passTestsCount;

    /**
     * 失败用例数
     */
    private Integer failedTestsCount;

    /**
     * 跳过用例数
     */
    private Integer skippedTestsCount;

    /**
     * 其他用例数
     */
    private Integer otherTestsCount;

    public void setAttributes(Map<String, Object> attributes){
        attributes.forEach(this::setAttribute);
    }
}
