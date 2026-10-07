package com.automationexercises.pages.components;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.ProductDetailsPage;
import com.automationexercises.utils.dataReader.PropertyReader;
import com.automationexercises.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductsPage {
    private final GUIDriver driver;

    //variables
    private final String productsEndpoint = "/products";
    //Locators
    private final By searchField = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By itemAddedLabel = By.xpath("//p[.='Your product has been added to cart.']");
    private final By viewCartButton = By.cssSelector("p>[href=\"/view_cart\"]");
    private final By continueShoppingButton = By.cssSelector(".modal-footer>button");

    public NavigationBarComponent navigationBar;

    public ProductsPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    //dynamic locator
    private By productName(String productName) {
        return By.xpath("//div[@class='overlay-content']/p[.='" + productName + "']");
    }

    private By productPrice(String productName) {
        return By.xpath("//div[@class='overlay-content']/p[.='" + productName + "']//preceding-sibling::h2");
    }

    private By hoverOverProduct(String productId) {
        return By.xpath("//div[contains(@class,'productinfo')][.//a[@data-product-id='" + productId + "']]");
    }

    private By addToCartButton(String productId) {
        return By.xpath("//a[@data-product-id='" + productId + "']");
    }

    private By viewProduct(String productName) {
        return By.xpath("//p[.='" + productName + "']//following::div[@class='choose'][1]");
    }

    //actions
    @Step("Navigate to Products Page")
    public ProductsPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + productsEndpoint);
        return this;
    }

    @Step("Search for product {productName}")
    public ProductsPage searchProduct(String productName) {
        driver.element().type(searchField, productName)
                .click(searchButton);
        return this;
    }

    @Step("Click on Add to Cart for product")
    public ProductsPage clickOnAddToCart(String productId) {
        driver.element().hover(hoverOverProduct(productId))
                .click(addToCartButton(productId));
        return this;
    }

    @Step("Click on View Product for product {productName}")
    public ProductDetailsPage clickOnViewProduct(String productName) {
        driver.element().click(viewProduct(productName));
        return new ProductDetailsPage(driver);
    }

    @Step("Click on View Cart button")
    public ProductsPage clickOnViewCart() {
        driver.element().click(viewCartButton);
        return this;
    }

    @Step("Click on Continue Shopping button")
    public ProductsPage clickOnContinueShopping() {
        driver.element().click(continueShoppingButton);
        return this;
    }

    //validations
    @Step("Verify product details for {productName} with price {productPrice} is displayed")
    public ProductsPage verifyProductDetails(String productName, String productPrice) {
        String actualProductName = driver.element().getText(productName(productName));
        String actualProductPrice = driver.element().getText(productPrice(productName));
        LogsManager.info("Verifying product details for " + productName + " with price " + productPrice);
        //we used soft assertion here (validation) because im checking on more than one item
        //if one of them fails the other will continue
        driver.validation().Equals(actualProductName, productName, "Product name does not match");
        driver.validation().Equals(actualProductPrice, productPrice, "Product price does not match");
        return this;
    }

    @Step("Validate item added label contains: {expectedText}")
    public ProductsPage validateItemAddedLabel(String expectedText) {
        String actualText = driver.element().getText(itemAddedLabel);
        LogsManager.info("Validating item added label contains: " + expectedText);
        driver.verification().Equals(actualText, expectedText, "Item added label does not match the expected label");
        return this;
    }

}
