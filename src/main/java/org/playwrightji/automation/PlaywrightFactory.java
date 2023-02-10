package org.playwrightji.automation;

import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.playwrightji.configuration.Configuration;
import org.playwrightji.configuration.ConfigurationFactory;

import java.nio.file.Paths;
import java.util.Base64;

public class PlaywrightFactory {

    private static Logger log = LogManager.getLogger();
    private Playwright playwright;
    private Configuration configuration;

    /**
     * We need to initialize our configuration factory which contains playwright server
     *
     * @param configurationFactory - {@link ConfigurationFactory}
     */
    public PlaywrightFactory(ConfigurationFactory configurationFactory) {
        this.configuration = configurationFactory.getConfiguration();
        playwright = Playwright.create();
    }

    /**
     * Method to create a new playwright {@link Page} for the browser
     *
     * @return Page - Returns playwright {@link Page} instance or null if any
     *         exception occurs while retrieving {@link BrowserContext}
     */
    public Page createPage() {
        Page page = null;
        BrowserFactory browserFactory = new BrowserFactory(playwright, configuration);
        try {
            page = (browserFactory.getBrowserContext().newPage());
        } catch (Exception e) {
            log.error("Unable to create Page : ", e);
        }
        return page;
    }

    /**
     * Method to save the session state from the {@link BrowserContext} in a file
     * provided in 'sessionState' property
     *
     * @param page     - playwright {@link Page} instance
     * @param filename - {@link String} name of the file to store session state
     */
    public static void saveSessionState(Page page, String filename) {
        page.context().storageState(new BrowserContext.StorageStateOptions()
                .setPath(Paths.get(filename)));
    }

    /**
     * Method to take screenshot of the {@link Page}
     * It saves the screenshots with file name of ${currentTimeMillis}.png
     *
     * @param page - playwright {@link Page} instance
     * @return String - Returns encoded {@link Base64} String of image
     */
    public static String takeScreenshot(Page page) {
        String path = System.getProperty("user.dir") + "/test-results/screenshots/" + System.currentTimeMillis()
                + ".png";

        byte[] buffer = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        String base64Path = Base64.getEncoder().encodeToString(buffer);

        log.debug("Screenshot is taken and saved at the location  {}", path);
        return base64Path;
    }

}
