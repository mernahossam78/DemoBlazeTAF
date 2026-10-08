package com.automationexercises.tests.ui;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.components.NavigationBarComponent;
import com.automationexercises.pages.components.ProductsPage;
import com.automationexercises.tests.BaseTest;
import com.automationexercises.utils.TimeManager;
import com.automationexercises.utils.dataReader.jsonReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductDetailsTest extends BaseTest {
    String timeStamp = TimeManager.getSimpleTimestamp();


    @Test
    public void verifyProductDetailsTC() {
        new ProductsPage(driver)
                .navigate()
                .clickOnViewProduct(testData.getJsonData("product.name"))
                .verifyProductDetails(testData.getJsonData("product.name"),
                        testData.getJsonData("product.price"));

    }

    @Test
    public void verifyReviewMsgTC() {
        new ProductsPage(driver)
                .navigate()
                .clickOnViewProduct(testData.getJsonData("product.name"))
                .addReview(testData.getJsonData("review.name"),
                        testData.getJsonData("review.email"),
                        testData.getJsonData("review.review"))
                .verifyReviewMsg(testData.getJsonData("messages.review"));

    }

    //Configurations

    @BeforeClass
    public void preCondition() {
        testData = new jsonReader("products-details-data");
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
