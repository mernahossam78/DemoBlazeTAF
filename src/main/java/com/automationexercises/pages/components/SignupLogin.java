package com.automationexercises.pages.components;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLogin {
    private final String signupLoginEndpoint = "/login";
    //locators
    private final By loginEmail = By.xpath("//input[@data-qa='login-email']");
    private final By loginPassword = By.xpath("//input[@data-qa='login-password']");
    private final By loginButton = By.xpath("//button[@data-qa='login-button']");
    private final By signupName = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmail = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("button[data-qa='signup-button']");
    private final By signupLabel = By.cssSelector("div.signup-form > h2");
    private final By loginError = By.xpath("//p[text()='Your email or password is incorrect!']");
    private final By registeredError = By.cssSelector(".signup-form p");
    public NavigationBarComponent navigationBar;
    private GUIDriver driver;


    public SignupLogin(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    //actions
    @Step("Navigate to Register/Login Page")
    public SignupLogin navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + signupLoginEndpoint);
        return this;
    }

    //Login field
    @Step("Enter name {email} in Login field")
    public SignupLogin enterLoginEmail(String email) {
        driver.element().type(loginEmail, email);
        return this;
    }

    @Step("Enter password {password} in login field")
    public SignupLogin enterLoginPassword(String password) {
        driver.element().type(loginPassword, password);
        return this;
    }

    @Step("Click on Login Button")
    public SignupLogin clicklLoginButton() {
        driver.element().click(loginButton);
        return this;
    }

    //Signup field
    @Step("Enter name {name} in signup field")
    public SignupLogin enterSignupName(String name) {
        driver.element().type(signupName, name);
        return this;
    }

    @Step("Enter email {email} in signup field")
    public SignupLogin enterSignupEmail(String email) {
        driver.element().type(signupEmail, email);
        return this;
    }

    @Step("Click on Signup Button")
    public SignupLogin clickSignupButton() {
        driver.element().click(signupButton);
        return new SignupLogin(driver);
    }

    //validations
    @Step("Verify Signup/Login Page is displayed")
    public SignupLogin verifySignupLoginPageIsDisplayed() {
        driver.verification().isElementVisible(signupLabel);
        return this;
    }

    @Step("Verify new user signup visible")
    public SignupLogin verifyLoginErrorMsg(String errorExpected) {
        String errorActual = driver.element().getText(loginError);
        driver.verification().Equals(errorActual, errorExpected, "The error message is not as expected");
        return this;
    }

    @Step("Verify registered error message is displayed")
    public SignupLogin verifyRegisteredErrorMsg(String errorExpected) {
        String errorActual = driver.element().getText(registeredError);
        driver.verification().Equals(errorActual, errorExpected, "The error message is not as expected");
        return this;
    }

}
