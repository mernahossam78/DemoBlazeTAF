package com.automationexercises.pages;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.components.NavigationBarComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupPage {
    private final GUIDriver driver;
    //private final String signUpEndpoint = "/signup";
    //locators
    //account information
    private final By name = By.id("name");
    private final By email = By.id("email");
    private final By password = By.id("password");
    private final By day = By.id("days");
    private final By month = By.id("months");
    private final By year = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By offersCheckbox = By.id("optin");
    //address information
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By company = By.id("company");
    private final By address1 = By.id("address1");
    private final By address2 = By.id("address2");
    private final By country = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipcode = By.id("zipcode");
    private final By mobileNumber = By.id("mobile_number");
    private final By createAccountButton = By.cssSelector("button[data-qa='create-account']");
    private final By accountCreatedLabel = By.tagName("b");
    private final By continueButton = By.xpath("//a[@data-qa='continue-button']");

    public SignupPage(GUIDriver driver) {
        this.driver = driver;
    }

    //actions
    @Step("Choose title {title}") //Mr - Mrs
    private SignupPage chooseTitle(String title) {
        By titleLocator = By.xpath("//input[@value='" + title + "']");
        driver.element().click(titleLocator);
        return this;
    }

    @Step("Fill registeration form")
    public SignupPage fillRegistrationForm(String title,
                                           String passwordText,
                                           String dayText,
                                           String monthText,
                                           String yearText,
                                           String firstNameText,
                                           String lastNameText,
                                           String companyText,
                                           String address1Text,
                                           String address2Text,
                                           String countryText,
                                           String stateText,
                                           String cityText,
                                           String zipcodeText,
                                           String mobileNumberText) {
        chooseTitle(title);
        driver.element().type(password, passwordText);
        driver.element().selectFromDropdown(day, dayText);
        driver.element().selectFromDropdown(month, monthText);
        driver.element().selectFromDropdown(year, yearText);
        driver.element().click(newsletterCheckbox);
        driver.element().click(offersCheckbox);
        driver.element().type(firstName, firstNameText);
        driver.element().type(lastName, lastNameText);
        driver.element().type(company, companyText);
        driver.element().type(address1, address1Text);
        driver.element().type(address2, address2Text);
        driver.element().selectFromDropdown(country, countryText);
        driver.element().type(state, stateText);
        driver.element().type(city, cityText);
        driver.element().type(zipcode, zipcodeText);
        driver.element().type(mobileNumber, mobileNumberText);
        return this;

    }

    @Step("Click on Create Account Button")
    public SignupPage clickCreateAccountButton() {
        driver.element().click(createAccountButton);
        return this;
    }


    //validations
    @Step("Verify Account Created")
    public SignupPage verifyAccountCreated() {
        driver.verification().isElementVisible(accountCreatedLabel);
        return this;
    }

    @Step("Click on Continue Button")
    public NavigationBarComponent clickOnContinueButton() {
        driver.element().click(continueButton);
        return new NavigationBarComponent(driver);
    }
}
