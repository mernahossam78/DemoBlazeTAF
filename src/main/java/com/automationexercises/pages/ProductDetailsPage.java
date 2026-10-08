package com.automationexercises.pages;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.components.NavigationBarComponent;
import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetailsPage {
    private final GUIDriver driver;
    private final String productDetailsEndpoint = "/product_details/1";
    //Locators
    private final By productName = By.xpath("//div[@class='product-information']/h2");
    private final By productPrice = By.cssSelector(".product-information>span>span");
    private final By name = By.id("name");
    private final By email = By.id("email");
    private final By reviewTextArea = By.id("review");
    private final By submitButton = By.id("button-review");
    private final By reviewMsg = By.cssSelector("#review-section span");

    public NavigationBarComponent navigationBar;

    public ProductDetailsPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    //actions
    @Step("Navigate to Product Details Page")
    public ProductDetailsPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + productDetailsEndpoint);
        return this;
    }

    @Step("Add review to a product")
    public ProductDetailsPage addReview(String name, String email, String review) {
        //we used this. because the name of the variables the same in the function parameter and the class
        //if you do not want to use it you can change the name of the parameter to something else like userName, userEmail, userReview
        driver.element().type(this.name, name);
        driver.element().type(this.email, email);
        driver.element().type(this.reviewTextArea, review);
        driver.element().click(submitButton);
        return this;
    }


    //validations
    @Step("Verify product details")
    public ProductDetailsPage verifyProductDetails(String pName, String pPrice) {
        String actualProductName = driver.element().getText(productName);
        String actualProductPrice = driver.element().getText(productPrice);
        LogsManager.info("actual name: " + actualProductName, "actual price: " + actualProductPrice);
        driver.validation().Equals(actualProductName, pName, "Product name does not match");
        driver.validation().Equals(actualProductPrice, pPrice, "Product price does not match");
        return this;
    }

    @Step("Verify review submission message")
    public ProductDetailsPage verifyReviewMsg(String msg) {
        String actualMsg = driver.element().getText(reviewMsg);
        LogsManager.info("actual message: " + actualMsg);
        driver.verification().Equals(actualMsg, msg, "Review submission message does not match");
        return this;
    }

}
