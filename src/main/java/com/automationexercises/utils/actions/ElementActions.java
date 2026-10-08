package com.automationexercises.utils.actions;

import com.automationexercises.utils.WaitManager;
import com.automationexercises.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

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
    public ElementActions click(By locator) {
        waitManager.fluentWait().until(d ->
                {
                    try {

                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        //Wait until the element is stable (not moving) before clicking
                        /*Point initialLocation = element.getLocation();
                        LogsManager.info("Initial location: " + initialLocation);
                        Point finalLocation = element.getLocation();
                        LogsManager.info("Final location: " + finalLocation);
                        if(!initialLocation.equals(finalLocation)){
                            return false; //element is moving, wait for it to stabilize
                        }

                         */
                        element.click();
                        LogsManager.info("Clicked on element: " + locator.toString());
                        return true;



                    } catch (Exception e) {
                        return false; //false here means that the until wil restart all over again
                        //the only way for until to finish is to return true or throw an exception, if we return false it will keep trying until the timeout is reached
                    }
                }
        );
        return this;


    }

    public ElementActions hover(By locator) {
        waitManager.fluentWait().until(d ->
                {
                    try {

                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        new Actions(d).moveToElement(element).perform();
                        LogsManager.info("Hovered over element: " + locator.toString());
                        return true;

                    } catch (Exception e) {
                        return false; //false here means that the until wil restart all over again
                        //the only way for until to finish is to return true or throw an exception, if we return false it will keep trying until the timeout is reached
                    }
                }
        );
        return this;
    }


    //Typing
    public ElementActions type(By locator, String text) {
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
        return this;

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
    public ElementActions uploadFile(By locator, String filePath) {
        String fileAbsolute = System.getProperty("user.dir") + File.separator + filePath;
        waitManager.fluentWait().until(d -> {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.sendKeys(fileAbsolute);
                        LogsManager.info("Uploaded file '" + fileAbsolute + "' to element: " + locator.toString());
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

    //Find and element
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    //Function to scroll to an element using js
    public void scrollToElementJS(By locator) {

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", findElement(locator));
    }

    //select from dropdown
    public void selectFromDropdown(By locator, String value) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                Select select = new Select(element);
                select.selectByVisibleText(value);
                LogsManager.info("Selected value '" + value + "' from dropdown: " + locator.toString());
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

}
