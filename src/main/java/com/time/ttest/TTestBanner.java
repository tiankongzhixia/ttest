package com.time.ttest;

import java.io.PrintStream;

/**
 * @Auther guoweijie

 * @Date 2020-02-24 09:36
 */
public class TTestBanner implements Banner {

    private static final String[] BANNER = {"                                              \n" +
            ",--------.,--------.,------. ,---. ,--------. \n" +
            "'--.  .--''--.  .--'|  .---''   .-''--.  .--' \n" +
            "   |  |      |  |   |  `--, `.  `-.   |  |    \n" +
            "   |  |      |  |   |  `---..-'    |  |  |    \n" +
            "   `--'      `--'   `------'`-----'   `--'    \n" +
            "                                              "};


    private static final String SPRING_BOOT = " :: TTest :: ";

    private static final int STRAP_LINE_SIZE = 42;


    @Override
    public void printBanner(PrintStream printStream) {
        for (String line : BANNER) {
            printStream.println(line);
        }
        String version = this.getClass().getPackage().getImplementationVersion();
        version = (version != null) ? " (v" + version + ")" : "";
        printStream.println(SPRING_BOOT+" "+version);
        printStream.println();
    }
}
