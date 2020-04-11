package com.time.ttest.asserts;

import lombok.Builder;
import lombok.Data;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 15:32
 */
@Data
public class TTestAssert {

    /**
     * 校验方式
     */
    private TAssertType assertTypeEnum;

    /**
     * 实际结果
     */
    private Object actual;

    /**
     * 需要提取的实际结果路径
     */
    private String actualPath;

    /**
     * 预期
     */
    private Object expect;

    /**
     * 需要提取的预期路径
     */
    private String expectPath;

    /**
     * 错误提示
     */
    private String errorMsg;
}
