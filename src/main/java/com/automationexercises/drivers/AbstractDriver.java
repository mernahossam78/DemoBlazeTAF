package com.automationexercises.drivers;

import com.automationexercises.utils.dataReader.PropertyReader;
import org.openqa.selenium.WebDriver;

public abstract class AbstractDriver {
    protected final String remoteHost = PropertyReader.getProperty("remoteHost");
    protected final String remotePort = PropertyReader.getProperty("remotePort");

    public abstract WebDriver createDriver(); //this method will be implemented in the child classes, and it will return a WebDriver object
    //the ones who will extend this class will be the ones who will implement this method, and they will return a WebDriver object, and they will be the ones who will create the driver

}
