package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.playwrightji.automation.PlaywrightFactory;
import org.playwrightji.configuration.ConfigurationFactory;
import org.playwrightji.reporter.ReporterFactory;
import org.playwrightji.salesforce.login.LoginPage;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.nio.file.Paths;

import static org.playwrightji.automation.PlaywrightFactory.takeScreenshot;
import static org.playwrightji.reporter.ReporterFactory.extentLogWithScreenshot;

public class TestSetup {
    protected Page page;
    protected SoftAssert softAssert = new SoftAssert();
    protected ExtentTest extentTest, testNode;
    protected LoginPage loginPage;
    protected static ExtentReports reporter;
    protected static ConfigurationFactory configurationFactory;
    private static Logger log;

    /**
     * BeforeSuite method to clean up the test-results directory and initialize the
     * extent reporter, logger and read test properties
     *
     * @throws Exception
     */
    @BeforeSuite
    public void setupBeforeTestSuite() throws Exception {
        File file = new File("test-results");
        if (file.exists() && !deleteDirectory(file)) {
            throw new Exception("Exception occurred while deleting test-results directory");
        }
        log = LogManager.getLogger();
        configurationFactory = new ConfigurationFactory();
        reporter = ReporterFactory.getExtentReporter(configurationFactory.getConfiguration());
    }

    /**
     * AfterSuite method to assert all the soft assertions and flush(write) the
     * extent report
     */
    @AfterSuite
    public void teardownAfterTestSuite() {
        try {
            softAssert.assertAll();
            reporter.flush();
        } catch (Exception e) {
            log.error("Error in AfterSuite Method ", e);
        }
    }

    /**
     * BeforeMethod to start the playwright server, create page and navigate to the
     * base URL
     */
    @BeforeMethod
    public void startPlaywrightServer() {
        PlaywrightFactory playwrightFactory = new PlaywrightFactory(configurationFactory);
        page = playwrightFactory.createPage();
        page.navigate(configurationFactory.getConfiguration().getUrl());
    }

    /**
     * AfterMethod to stop the tracing if enabled and save the tracing
     * and add screenshot for tests which result is not SUCCESS
     *
     * @param result - {@link ITestResult} of current Test
     */
    @AfterMethod
    public void closePage(ITestResult result) {
        String testName = testNode.getModel().getName().replaceAll("[^A-Za-z0-9_\\-\\.\\s]", "");
        if (configurationFactory.getConfiguration().getEnableTracing()) {
            String fileName = configurationFactory.getConfiguration().getTracingDirectory() + "Trace_"
                    + testName + ".zip";
            page.context().tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(fileName)));
        }
        if (!result.isSuccess())
            extentLogWithScreenshot(testNode, Status.WARNING, "The test is not Passed. Please refer the previous step.",
                    takeScreenshot(page));
        page.context().browser().close();
        reporter.flush();
    }

    /**
     * Method to delete the directory recursively
     *
     * @param directoryToBeDeleted - {@link File} to be deleted
     * @return boolean - Returns {@link Boolean} of delete operation
     */
    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
