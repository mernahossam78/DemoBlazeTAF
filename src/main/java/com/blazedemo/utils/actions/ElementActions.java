package com.blazedemo.utils.actions;

import com.blazedemo.utils.WaitManager;
import com.blazedemo.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;

public class ElementActions {
    //that class will contain all the actions that we will reuse
    //type, click and get text
    private final WebDriver driver;
    private WaitManager waitManager;

    //constructor
    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    //Clicking
    public void click(By locator) {
        waitManager.fluentWait().until(d ->
                {
                    try {

                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.click();
                        LogsManager.info("Clicked on element: " + locator.toString());
                        return true;

                    } catch (Exception e) {
                        return false; //false here means that the until wil restart all over again
                        //the only way for until to finish is to return true or throw an exception, if we return false it will keep trying until the timeout is reached
                    }
                }
        );


    }


    //Typing
    public void type(By locator, String text) {
        waitManager.fluentWait().until(d ->
                {
                    try {

                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.clear();
                        element.sendKeys(text);
                        LogsManager.info("Typed text '" + text + "' into element: " + locator.toString());
                        return true;

                    } catch (Exception e) {
                        return false; //false here means that the until wil restart all over again
                        //the only way for until to finish is to return true or throw an exception, if we return false it will keep trying until the timeout is reached
                    }
                }
        );

    }


    //Getting text
    public String getText(By locator) {

        return waitManager.fluentWait().until(d ->
                {
                    try {

                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        String msg = element.getText();
                        LogsManager.info("Got text '" + msg + "' from element: " + locator.toString());
                        return !msg.isEmpty() ? msg : null;
                    } catch (Exception e) {
                        return null;
                    }
                }
        );
    }

    //Upload file
    public void uploadFile(By locator, String filePath) {
        String fileAbsolute = System.getProperty("user.dir") + File.separator + filePath;
        waitManager.fluentWait().until(d ->{
            try{
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                element.sendKeys(fileAbsolute);
                LogsManager.info("Uploaded file '" + fileAbsolute + "' to element: " + locator.toString());
                return true;
            }
            catch (Exception e){
                return false;
            }
        }
        );
    }

    //Find and element
    public WebElement findElement(By locator){
        return driver.findElement(locator);
    }

    //Function to scroll to an element using js
    public void scrollToElementJS(By locator){

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", findElement(locator));
    }

}
