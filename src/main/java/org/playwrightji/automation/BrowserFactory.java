package org.playwrightji.automation;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.playwrightji.configuration.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BrowserFactory {
    private static Logger log = LogManager.getLogger();
    private static String CH_MSEDGE = "msedge";
    private static String CH_CHROME = "chrome";

    private Playwright playwright;
    private Configuration configuration;
    private LaunchOptions launchOptions = new BrowserType.LaunchOptions();

    public BrowserFactory(Playwright playwright, Configuration configuration) {
        this.playwright = playwright;
        this.configuration = configuration;
    }

    private BrowserType getBrowserType(WebBrowser browser) {

        BrowserType browserType;
        switch (browser) {
            case CHROME -> {
                browserType = playwright.chromium();
                launchOptions.setChannel(CH_CHROME);
                }
            case EDGE -> {
                browserType = playwright.chromium();
                launchOptions.setChannel(CH_MSEDGE);
                }
            case FIREFOX -> browserType = playwright.firefox();
            case SAFARI -> browserType = playwright.webkit();
            default -> {
                String message = "Browser Name '" + browser + "' specified in Invalid.";
                message += " Please specify one of the supported browsers [chromium, edge, firefox, safari].";
                log.error(message);
                throw new IllegalArgumentException(message);
            }
        }
        return browserType;
    }

    /**
     * Method to get the playwright {@link BrowserContext} with the video recording,
     * tracing. storage context and view port
     * These properties are set based on values on config properties
     *
     * @return BrowserContext - Returns playwright {@link BrowserContext} instance
     */
    public BrowserContext getBrowserContext() {
        BrowserContext browserContext;
        BrowserType browserType = getBrowserType(configuration.getWebBrowser());
        launchOptions.setHeadless(configuration.getHeadless());
        log.info("Browser Selected for Test Execution '{}' with headless mode as '{}'", configuration.getBrowser(), configuration.getHeadless());
        Browser browser = browserType.launch(launchOptions);
        Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions();

        if (configuration.getEnableVideoRecording()) {
            Path path = Paths.get(configuration.getVideoRecordingDirectory());
            newContextOptions.setRecordVideoDir(path);
            log.info("Video Recording for BrowserContext will be stored here '{}'", path.toAbsolutePath());
        }

        int viewPortHeight = configuration.getViewPortHeight();
        int viewPortWidth =  configuration.getViewPortWidth();
        newContextOptions.setViewportSize(viewPortWidth, viewPortHeight);
        log.info("Browser Context - Viewport Width '{}' and Height '{}'", viewPortWidth, viewPortHeight);

        browserContext = (browser.newContext(newContextOptions));

        if (configuration.getEnableTracing()) {
            browserContext.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
            log.info("Browser Context - Tracing is enabled with Screenshots and Snapshots");
        }
        return browserContext;
    }
}
