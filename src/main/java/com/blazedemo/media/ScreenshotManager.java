package com.blazedemo.media;

import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.logs.LogsManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;


public class ScreenshotManager {
    private static final String SCREENSHOT_PATH = "test-output/screenshots";

    //take full page screenshot
    public static void takeFullPageScreenshot(WebDriver driver, String ScreenshotName) {
        // Implementation for taking full page screenshot
        try {
            //Capture screenshot using TakeScreenshot
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            //Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOT_PATH + ScreenshotName + "-" + TimeManager.getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            // TODO: Attach screenshot to allure

            LogsManager.info("Capturing Screenshot succeeded");

        } catch (Exception e) {
            LogsManager.error("Capturing Screenshot failed: " + e.getMessage());
        }

    }

    public static void takeElementScreenshot(WebDriver driver, By elementLocator) {
        // Implementation for taking element screenshot
        try {
            //Capture screenshot using TakeScreenshot
            String araiName = driver.findElement(elementLocator).getAccessibleName();
            File screenshotSrc = driver.findElement(elementLocator).getScreenshotAs(OutputType.FILE);

            //Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOT_PATH + araiName + "-" + TimeManager.getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            // TODO: Attach screenshot to allure

            LogsManager.info("Capturing Screenshot succeeded");

        } catch (Exception e) {
            LogsManager.error("Capturing Screenshot failed: " + e.getMessage());
        }

    }

}
