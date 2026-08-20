package com.blazedemo.utils.actions;

import com.blazedemo.utils.WaitManager;
import com.blazedemo.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;

public class AlertActions {
    private final WebDriver driver;
    private final WaitManager waitManager;
    public AlertActions(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    //Accepting an alert
    public void acceptAlert(){
        waitManager.fluentWait().until(driver1 ->
        {
            try {
                driver.switchTo().alert().accept();
                return true;
            }
            catch (Exception e){
                LogsManager.error("Failed to accept alert: ", e.getMessage());
                return false;
            }
        }
        );
    }

    //Dismissing an alert
    public void dismissAlert(){
        waitManager.fluentWait().until(driver1 ->
                {
                    try {
                        driver.switchTo().alert().dismiss();
                        return true;
                    }
                    catch (Exception e){
                        LogsManager.error("Failed to dismiss alert: ", e.getMessage());
                        return false;
                    }
                }
        );

    }

    //Getting the text of an alert
    public String getAlertText(){
        return waitManager.fluentWait().until(driver1 -> {
            try {
                String text = driver.switchTo().alert().getText();
                return !text.isEmpty() ? text : null;
            }
            catch (Exception e)
            {
                LogsManager.error("Failed to get get text: ", e.getMessage());
                return null;
            }
        }
        );
    }

    //Sending text to an alert
    public void sendAlertText(String text){
        waitManager.fluentWait().until(driver1 -> {
            try {
                driver.switchTo().alert().sendKeys(text);
                return true;
            }
            catch (Exception e)
            {
                LogsManager.error("Failed to send text to alert: ", e.getMessage());
                return false;
            }
        }
        );
    }
}
