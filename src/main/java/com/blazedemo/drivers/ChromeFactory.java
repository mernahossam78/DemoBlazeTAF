package com.blazedemo.drivers;

import com.blazedemo.utils.dataReader.PropertyReader;
import com.blazedemo.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class ChromeFactory extends AbstractDriver {
    private final String remoteHost = PropertyReader.getProperty("remoteHost");
    private final String remotePort = PropertyReader.getProperty("remotePort");

    private ChromeOptions getOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        if (PropertyReader.getProperty("excutionType").equalsIgnoreCase("LocalHeadless") || PropertyReader.getProperty("excutionType").equalsIgnoreCase("Remote")) {
            options.addArguments("--headless");
        }
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }

    @Override
    public WebDriver createDriver() {
        if (PropertyReader.getProperty("excutionType").equalsIgnoreCase("Local") ||
                PropertyReader.getProperty("excutionType").equalsIgnoreCase("LocalHeadless")) {
            return new ChromeDriver(getOptions());

        } else if (PropertyReader.getProperty("excutionType").equalsIgnoreCase("Remote")) {
            try {
                return new RemoteWebDriver(
                        new URI("http://" + remoteHost + ":" + remotePort + "/wd/hub").toURL(), getOptions());
            } catch (Exception e) {
                LogsManager.error("Failed to create RemoteWebDriver: " + e.getMessage());
                throw new RuntimeException("Failed to create RemoteWebDriver", e);
            }
        } else {
            LogsManager.error("Invalid execution type: " + PropertyReader.getProperty("excutionType"));
            throw new IllegalArgumentException("Invalid execution type: " + PropertyReader.getProperty("excutionType"));
        }
    }
}
