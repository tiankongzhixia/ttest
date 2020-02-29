package com.time.ttest.report;

import lombok.Data;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.util.List;

/**
 * @Auther guoweijie
 * @Email 877502087@qq.com
 * @Date 2020-02-26 20:43
 */
@Data
public class TestNGReport {
    public TestNGReport() {
    }

    public TestNGReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        this.xmlSuites = xmlSuites;
        this.suites = suites;
        this.outputDirectory = outputDirectory;
    }

    private List<XmlSuite> xmlSuites;

    private List<ISuite> suites;

    private String outputDirectory;
}
