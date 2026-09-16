package com.automationexercises.drivers;

import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class EdgeFactory extends AbstractDriver {
    private EdgeOptions getOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        if (PropertyReader.getProperty("excutionType").equalsIgnoreCase("LocalHeadless") ||
                PropertyReader.getProperty("excutionType").equalsIgnoreCase("Remote")) {
            options.addArguments("--headless");
        }
        return options;
    }

    @Override
    public WebDriver createDriver() {
        if (PropertyReader.getProperty("excutionType").equalsIgnoreCase("Local") ||
                PropertyReader.getProperty("excutionType").equalsIgnoreCase("LocalHeadless")) {
            return new EdgeDriver(getOptions());

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
