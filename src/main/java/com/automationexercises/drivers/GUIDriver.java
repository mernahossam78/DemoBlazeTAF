package com.automationexercises.drivers;

import com.automationexercises.utils.actions.AlertActions;
import com.automationexercises.utils.actions.BrowserActions;
import com.automationexercises.utils.actions.ElementActions;
import com.automationexercises.utils.actions.FrameActions;
import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import com.automationexercises.validations.Validation;
import com.automationexercises.validations.Verification;
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

    public ElementActions element() {
        return new ElementActions(get());
    }

    public BrowserActions browser() {
        return new BrowserActions(get());
    }

    public FrameActions frame() {
        return new FrameActions(get());
    }

    public AlertActions alert() {
        return new AlertActions(get());
    }

    //Soft assertions
    public Validation validation() {
        return new Validation(get());
    }

    //Hard assertions
    public Verification verification() {
        return new Verification(get());
    }

    // Tests will call this instead of creating drivers themselves.
    public WebDriver get() {
        return driverThreadLocal.get();
    }

    public void quitDriver() {
        driverThreadLocal.get().quit();
    }
}
