package com.automationexercises.tests.ui;

import com.automationexercises.apis.UserManagementAPI;
import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.SignupPage;
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
@Story("User Registration")
@Severity(SeverityLevel.CRITICAL)
@Owner("Merna")
public class RegisterTest extends BaseTest {

    String timeStamp = TimeManager.getSimpleTimestamp();

    //Tests
    @Description("Verify that user can register successfully with valid data")
    @Test
    public void validSignUpTc() {
        new SignupLogin(driver).navigate()
                .enterSignupName(testData.getJsonData("name"))
                .enterSignupEmail(testData.getJsonData("email") + timeStamp + "@gmail.com")
                .clickSignupButton();
        new SignupPage(driver)
                .fillRegistrationForm(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("password"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("mobileNumber")
                )
                .clickCreateAccountButton()
                .verifyAccountCreated();


        new UserManagementAPI().deleteUserAccount(
                        testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    @Description("Verify that user cannot register with an email that has already been used")
    @Test
    public void verifyErrorMessageWhenAccountCreatedBefore() {
        //precondition > create a user account
        new UserManagementAPI().createRegisterAccount(testData.getJsonData("name"),
                        testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("mobileNumber")

                )
                .verifyUserCreatedSuccessfully();
        new SignupLogin(driver).navigate()
                .enterSignupName(testData.getJsonData("name"))
                .enterSignupEmail(testData.getJsonData("email") + timeStamp + "@gmail.com")
                .clickSignupButton()
                .verifyRegisteredErrorMsg(testData.getJsonData("messages.error"));

        new UserManagementAPI().deleteUserAccount(
                        testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    //Configurations

    @BeforeClass
    public void preCondition() {
        testData = new jsonReader("register-data");
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
