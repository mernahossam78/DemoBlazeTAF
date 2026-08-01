package com.blazedemo.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class BrowserActions {
    private final WebDriver driver;
    public BrowserActions(WebDriver driver){
        this.driver = driver;
    }

    //Maximize window
    public void maximizeWindow(){
        driver.manage().window().maximize();
    }

    //get current web page URL
    public String getCurrentURL(){
        String url = driver.getCurrentUrl();
        return url;
    }

    //Navigate to a specific URL
    public void navigateTo(String url){
        driver.get(url);
    }

    //Refresh the current page
    public void refreshPage(){
        driver.navigate().refresh();
    }

    //close the current window
    public void closeCurrentWindow(){
        driver.close();
    }

    //open a new window
    public void openNewWindow(){
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

}
