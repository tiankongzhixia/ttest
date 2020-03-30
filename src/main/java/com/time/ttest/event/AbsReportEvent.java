package com.time.ttest.event;

/**
 * 测试报告的消息
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-03-03 13:15
 */
public abstract class AbsReportEvent extends ApplicationEvent{
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public AbsReportEvent(Object source) {
        super(source);
    }
}
