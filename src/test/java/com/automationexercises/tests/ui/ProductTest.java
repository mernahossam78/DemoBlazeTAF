package com.automationexercises.tests.ui;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.components.NavigationBarComponent;
import com.automationexercises.pages.components.ProductsPage;
import com.automationexercises.tests.BaseTest;
import com.automationexercises.utils.dataReader.jsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("Automation Exercise")
@Feature("UI Products Management")
@Story("Products Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Merna")
public class ProductTest extends BaseTest {


    @Test
    @Description("Search for a product and validate its details")
    public void searchForProductWithoutLogin() {
        new ProductsPage(driver)
                .navigate()
                .searchProduct(testData.getJsonData("searchedProduct.name"))
                .verifyProductDetails(testData.getJsonData("searchedProduct.name"),
                        testData.getJsonData("searchedProduct.price"));

    }

    @Test
    @Description("Add a product to the cart without logging in")
    public void addProductToCartWithoutLogin() {
        new ProductsPage(driver).navigate()
                .clickOnAddToCart(testData.getJsonData("searchedProduct.productId"))
                .validateItemAddedLabel(testData.getJsonData("messages.cartAdded"));

    }

    //Configurations

    @BeforeClass
    public void preCondition() {
        testData = new jsonReader("products-data");
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
