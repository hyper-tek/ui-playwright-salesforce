package org.playwrightji.configuration;

import org.playwrightji.automation.WebBrowser;

public class Configuration {
    private static final String CHROME = "chrome";
    private static final String EDGE = "edge";
    private static final String FIREFOX = "firefox";
    private static final String SAFARI = "safari";

    private String url;
    private String browser;
    private boolean headless;
    private String username;
    private String password;
    private String extentReportPath;
    private boolean enableVideoRecording;
    private String videoRecordingDirectory;
    private boolean enableTracing;
    private String tracingDirectory;
    private int viewPortWidth;
    private int viewPortHeight;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url.trim();
    }

    public String getBrowser() {
        return browser;
    }

    public WebBrowser getWebBrowser() {
        WebBrowser webBrowser;
        switch (browser) {
            case CHROME -> webBrowser = WebBrowser.CHROME;
            case EDGE -> webBrowser = WebBrowser.EDGE;
            case FIREFOX -> webBrowser = WebBrowser.FIREFOX;
            case SAFARI -> webBrowser = WebBrowser.SAFARI;
            default -> {
                String message = "The browser configured in config.yaml is invalid: '" + browser + "'." + System.lineSeparator();
                throw new IllegalArgumentException(message + "<< !! Choose either: chrome; edge; firefox; safari !! >>");
            }
        }
        return webBrowser;
    }

    public void setBrowser(String browser) {
        this.browser = browser.trim();
    }


    public boolean getHeadless() {
        return headless;
    }

    public void setHeadless(boolean headless) {
        this.headless = headless;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExtentReportPath() {
        return extentReportPath;
    }

    public void setExtentReportPath(String extentReportPath) {
        this.extentReportPath = extentReportPath.trim();
    }

    public boolean getEnableVideoRecording() {
        return enableVideoRecording;
    }

    public void setEnableVideoRecording(boolean enableVideoRecording) {
        this.enableVideoRecording = enableVideoRecording;
    }

    public String getVideoRecordingDirectory() {
        return videoRecordingDirectory;
    }

    public void setVideoRecordingDirectory(String videoRecordingDirectory) {
        this.videoRecordingDirectory = videoRecordingDirectory.trim();
    }

    public boolean getEnableTracing() {
        return enableTracing;
    }

    public void setEnableTracing(boolean enableTracing) {
        this.enableTracing = enableTracing;
    }

    public String getTracingDirectory() {
        return tracingDirectory;
    }

    public void setTracingDirectory(String tracingDirectory) {
        this.tracingDirectory = tracingDirectory.trim();
    }

    public int getViewPortWidth() {
        return viewPortWidth;
    }

    public void setViewPortWidth(int viewPortWidth) {
        this.viewPortWidth = viewPortWidth;
    }

    public int getViewPortHeight() {
        return viewPortHeight;
    }

    public void setViewPortHeight(int viewPortHeight) {
        this.viewPortHeight = viewPortHeight;
    }

}
