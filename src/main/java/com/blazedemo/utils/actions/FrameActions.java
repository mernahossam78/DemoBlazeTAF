package com.blazedemo.utils.actions;

import com.blazedemo.utils.WaitManager;
import com.blazedemo.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrameActions {
    private final WebDriver driver;
    private final WaitManager waitManager;
    public FrameActions(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }
    /**
     * Switch to a frame by its index.
     * @param index The index of the frame to switch to.
     */
    public void switchToFrameByIndex(int index){
        waitManager.fluentWait().until(d -> {
            try {
             d.switchTo().frame(index);
                LogsManager.info("Switched to frame with index: " + index);
             return true;
            }
            catch (Exception e){
                return false;
            }
        }
        );
    }

    /**
     * Switch to a frame by its name or ID.
     * @param nameOrId The name or ID of the frame to switch to.
     */
    public void switchToFreamByNameOrId(String nameOrId){
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().frame(nameOrId);
                LogsManager.info("Switched to frame with name or ID: " + nameOrId);
                return true;
            }
            catch (Exception e){
                return false;
            }
        }
        );
    }

    /**
     * Switch to frame by its WebElement
     */
    public void switchToFrameByWebElement(By frameLocator){
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().frame(d.findElement(frameLocator));
                LogsManager.info("Switched to frame with locator: " + frameLocator.toString());
                return true;
            }
            catch (Exception e){
                return false;
            }
        }
        );
    }

    /**
     * Switch back to the default content (main page)
     */
    public void switchToDefaultContent(){
        waitManager.fluentWait().until(d -> {
                    try {
                        d.switchTo().defaultContent();
                        LogsManager.info("Switched back to default content");
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
    }
}
