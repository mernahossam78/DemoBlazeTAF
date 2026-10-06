package com.automationexercises.tests.ui;

import com.automationexercises.apis.UserManagementAPI;
import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.components.NavigationBarComponent;
import com.automationexercises.pages.components.SignupLogin;
import com.automationexercises.tests.BaseTest;
import com.automationexercises.utils.TimeManager;
import com.automationexercises.utils.dataReader.jsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Merna")
public class LoginTest extends BaseTest {
    String timeStamp = TimeManager.getSimpleTimestamp();

    @Description("Verify that user can login successfully with valid data")
    @Test
    public void validLoginTC() {
        new UserManagementAPI().createRegisterAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstname"),
                        testData.getJsonData("lastname"))
                .verifyUserCreatedSuccessfully();
        new SignupLogin(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timeStamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clicklLoginButton()
                .navigationBar
                .verifyUserLabel(testData.getJsonData("name"));
        new UserManagementAPI().deleteUserAccount(
                        testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }

    @Description("Verify that user cannot login with an invalid email")
    @Test
    public void inValidLoginUsingInvalidEmailTC() {
        new UserManagementAPI().createRegisterAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstname"),
                        testData.getJsonData("lastname"))
                .verifyUserCreatedSuccessfully();
        new SignupLogin(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clicklLoginButton()
                .verifyLoginErrorMsg(testData.getJsonData("messages.error"));
        new UserManagementAPI().deleteUserAccount(
                        testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }


    @Description("Verify that user cannot login with an invalid password")
    @Test
    public void inValidLoginUsingInvalidPasswordTC() {
        new UserManagementAPI().createRegisterAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstname"),
                        testData.getJsonData("lastname"))
                .verifyUserCreatedSuccessfully();
        new SignupLogin(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timeStamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password") + timeStamp)
                .clicklLoginButton()
                .verifyLoginErrorMsg(testData.getJsonData("messages.error"));
        new UserManagementAPI().deleteUserAccount(
                        testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }


    //Configurations

    @BeforeClass
    public void preCondition() {
        testData = new jsonReader("login-data");
    }

    @BeforeMethod
    public void setUp() {
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}
