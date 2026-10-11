package com.automationexercises.pages.components;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    //Variables
    private final String cartEndpoint = "/view_cart";
    //locators
    private final By proceedToCheckoutButton = By.xpath("//a[.='Proceed To Checkout']");
    public NavigationBarComponent navigationBar;
    private GUIDriver driver;

    public CartPage(GUIDriver driver) {
        this.driver = driver;

    }


    //dynamic locators
    private By productName(String productName) {
        return By.xpath("//h4/a[.='" + productName + "']");
    }

    private By productPrice(String productName) {
        return By.xpath("//tr[.//h4/a[.='" + productName + "']]//td[@class='cart_price']/p");
    }

    private By productQuantity(String productName) {
        return By.xpath("//tr[.//h4/a[.='" + productName + "']]//td[@class='cart_quantity']/button");
    }

    private By productTotal(String productName) {
        return By.xpath("//tr[.//h4/a[normalize-space()='" + productName + "']]//td[@class='cart_total']/p");
    }

    private By removeProductDL(String productName) {
        return By.xpath("//tr[.//h4/a[.='" + productName + "']]//td[@class='cart_delete']");
    }

    //actions
    @Step("Navigate to Cart Page")
    public CartPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + cartEndpoint);
        return this;
    }

    @Step("Click on Proceed to Checkout")
    public CartPage clickOnProceedToCheckout() {
        driver.element().click(proceedToCheckoutButton);
        return this;
    }

    @Step("Remove product {pName} from cart")
    public CartPage removeProduct(String pName) {
        driver.element().click(removeProductDL(pName));
        return this;
    }

    //validations
    @Step("Verify product details on cart")
    public CartPage verifyProductDetailsOnCart(String productName, String productPrice, String productQuantity, String productTotal) {
        String actualProductName = driver.element().getText(productName(productName));
        String actualProductPrice = driver.element().getText(productPrice(productName));
        String actualProductQuantity = driver.element().getText(productQuantity(productName));
        String actualProductTotal = driver.element().getText(productTotal(productName));
        driver.validation().Equals(actualProductName, productName, "Product Name is not matched");
        driver.validation().Equals(actualProductPrice, productPrice, "Product Price is not matched");
        driver.validation().Equals(actualProductQuantity, productQuantity, "Product Quantity is not matched");
        driver.validation().Equals(actualProductTotal, productTotal, "Product Total is not matched");

        return this;
    }
}
