package com.automationexercises.pages.components;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.ContactUsPage;
import com.automationexercises.pages.DeleteAccountPage;
import com.automationexercises.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {
    private final GUIDriver driver;
    //Locators
    private final By homeButton = By.xpath("//a[.=' Home']");
    private final By productsButton = By.cssSelector("a[href='/products']");
    private final By cartButton = By.xpath("//a[.=' Cart']");
    private final By signUpLoginButton = By.xpath("//a[.=' Signup / Login']");
    private final By logoutButton = By.xpath("//a[.=' Logout']");
    private final By testCasesButton = By.xpath("//a[.=' Test Cases']");
    private final By apiTestingButton = By.xpath("//a[.=' API Testing']");
    private final By contactUsButton = By.xpath("//a[.=' Contact us']");
    private final By videoTutorialButton = By.xpath("//a[.=' Video Tutorials']");
    private final By deleteAccountButton = By.xpath("//a[.=' Delete Account']");
    private final By homePageLabel = By.cssSelector("h1>span");
    private final By userLabel = By.tagName("b");


    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
    }


    //Actions
    @Step("Navigate to Home Page")
    public NavigationBarComponent navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }

    @Step("Click on Home Button")
    public NavigationBarComponent clickHomeButton() {
        driver.element().click(homeButton);
        return this;
    }

    @Step("Click on Products Button")
    public ProductsPage clickProductsButton() {
        driver.element().click(productsButton);
        return new ProductsPage(driver);
    }

    @Step("Click on Cart Button")
    public CartPage clickCartButton() {
        driver.element().click(cartButton);
        return new CartPage(driver);
    }

    @Step("Click on Sign Up/Login Button")
    public SignupLogin clickSignUpLoginButton() {
        driver.element().click(signUpLoginButton);
        return new SignupLogin(driver);
    }

    @Step("Click on Logout Button")
    public LogoutPage clickLogoutButton() {
        driver.element().click(logoutButton);
        return new LogoutPage(driver);
    }

    @Step("Click on Test Cases Button")
    public TestCasesPage clickTestCasesButton() {
        driver.element().click(testCasesButton);
        return new TestCasesPage(driver);
    }

    @Step("Click on Contact Us Button")
    public ContactUsPage clickContactUsButton() {
        driver.element().click(contactUsButton);
        return new ContactUsPage(driver);
    }

    @Step("Click on Delete Account Button")
    public DeleteAccountPage clickDeleteAccountButton() {
        driver.element().click(deleteAccountButton);
        return new DeleteAccountPage(driver);
    }

    //Validations

    @Step("Verify that the Home Page is displayed")
    public NavigationBarComponent verifyHomePage() {
        driver.verification().isElementVisible(homePageLabel);
        return this;
    }

    @Step("Verify User Label")
    public NavigationBarComponent verifyUserLabel(String expectedName) {
        String actualName = driver.element().getText(userLabel);
        driver.verification().Equals(actualName, expectedName, "User label does not match expected: " + expectedName + " ,Actual Name: " + actualName);
        return this;
    }
}
