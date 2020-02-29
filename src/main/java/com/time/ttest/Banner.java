package com.time.ttest;

import java.io.PrintStream;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-24 09:34
 */
@FunctionalInterface
public interface Banner {

    /**
     * 输出banner
     * @param out System.out
     */
    void printBanner(PrintStream out);

}
