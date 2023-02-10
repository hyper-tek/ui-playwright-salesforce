package org.playwrightji.reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.playwrightji.configuration.Configuration;

import java.io.IOException;
import java.nio.file.Paths;

public class ReporterFactory {

    private static final String REPORT_CONFIG_FILE = "./src/main/resources/reporter-config.xml";

    private ReporterFactory() {
        throw new IllegalStateException("Reporter Factory class instantiation is not allowed");
    }

    /**
     * Method to configure and get the ExtentReporter instance
     *
     * @param configuration - {@link Configuration}
     * @return ExtentReports - Returns {@link ExtentReports} instance
     * @throws IOException - Throws {@link IOException}
     */
    public static ExtentReports getExtentReporter(Configuration configuration) throws IOException {
        ExtentSparkReporter reporter = new ExtentSparkReporter(configuration.getExtentReportPath());
        reporter.loadXMLConfig(REPORT_CONFIG_FILE);

        reporter.config().setCss("img.r-img { width: 30%; }");
        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);

        String applicationURL = "<a href=\"" + configuration.getUrl()
                + "\" target=\"_blank\">Open salesforce trial website</a>";
        extentReports.setSystemInfo("Application", applicationURL);

        extentReports.setSystemInfo("OS", System.getProperties().getProperty("os.name"));
        extentReports.setSystemInfo("Browser", configuration.getBrowser());

        if (configuration.getEnableVideoRecording()) {
            String filePath = Paths.get(configuration.getVideoRecordingDirectory()).toAbsolutePath()
                    .toString();
            String recordedVideoFilePath = "<a href=\"" + filePath
                    + "\" target=\"_blank\"Open salesforce trial website</a>";
            extentReports.setSystemInfo("Execution Recorded Video", recordedVideoFilePath);
        }
        return extentReports;
    }

    /**
     * Method to add the log with the step to extent report
     *
     * @param extentTest - {@link ExtentTest}
     * @param status     - {@link Status}
     * @param message    - {@link String} log message
     */
    public static void extentLog(ExtentTest extentTest, Status status, String message) {
        extentTest.log(status, message);
        log(status, message);
    }

    /**
     * Method to add the log step with the screenshot to the extent report
     *
     * @param extentTest - {@link ExtentTest}
     * @param status     - {@link Status}
     * @param message    - {@link String} log message
     * @param base64Path - {@link java.util.Base64} {@link String} of screenshot
     */
    public static void extentLogWithScreenshot(ExtentTest extentTest, Status status, String message,
                                               String base64Path) {
        String imageElement = "<br/><img class='r-img' src='data:image/png;base64," + base64Path
                + "' href='data:image/png;base64," + base64Path + "'data-featherlight='image'>";
        extentTest.log(status, message + imageElement);
        log(status, message);
    }

    /**
     * Method to log the message to console and log file.
     * It removes any HTML element in the message before printing logging
     *
     * @param status  - {@link Status}
     * @param message - {@link String} log message
     */
    private static void log(Status status, String message) {
        message = message.replaceAll("\\<.*?\\>", "");
        Logger log = LogManager.getLogger(Thread.currentThread().getStackTrace()[3].getClassName().split("\\.")[1]+ "." + Thread.currentThread().getStackTrace()[3].getMethodName());
        Marker marker = MarkerManager.getMarker("ReportLog");
        switch (status) {
            case FAIL:
            case SKIP:
            case WARNING:
                log.warn(marker, message);
                break;
            case INFO:
                log.info(marker, message);
                break;
            default:
                log.debug(marker, message);
                break;
        }
    }
}
