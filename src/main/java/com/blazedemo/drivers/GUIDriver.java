package com.blazedemo.drivers;

import com.blazedemo.utils.dataReader.PropertyReader;
import com.blazedemo.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {

    // Read the browser name (chrome, edge, firefox...) from the properties file.
    // This value is loaded once when a GUIDriver object is created.
    private final String browser = PropertyReader.getProperty("browserType");
    // ThreadLocal gives each running test its own WebDriver instance.
    // This prevents tests from sharing the same browser when running in parallel.
    private ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // Constructor: Creates a browser based on the browserType property and stores it inside ThreadLocal.
    public GUIDriver() {
        //String browser = PropertyReader.getProperty("browserType");
        Browser browserEnum = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Starting driver for browser: " + browserEnum);
        AbstractDriver abstractDriver = browserEnum.getDriverFactory();
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());
        // Store this driver's instance for the current thread.
        driverThreadLocal.set(driver);

    }

    // Tests will call this instead of creating drivers themselves.
    public WebDriver get() {
        return driverThreadLocal.get();
    }

    public void quitDriver() {
        driverThreadLocal.get().quit();
    }
}
