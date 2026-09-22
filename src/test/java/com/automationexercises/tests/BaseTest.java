package com.automationexercises.tests;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.drivers.WebDriverProvider;
import com.automationexercises.utils.dataReader.jsonReader;
import org.openqa.selenium.WebDriver;

public class BaseTest implements WebDriverProvider {

    protected GUIDriver driver;

    protected jsonReader testData;

    public void beforClass() {
        testData = new jsonReader("");
    }

    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }
}
