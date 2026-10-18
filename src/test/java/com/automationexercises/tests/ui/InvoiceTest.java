package com.automationexercises.tests.ui;

import com.automationexercises.apis.UserManagementAPI;
import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.drivers.UITest;
import com.automationexercises.pages.PaymentPage;
import com.automationexercises.pages.components.*;
import com.automationexercises.tests.BaseTest;
import com.automationexercises.utils.TimeManager;
import com.automationexercises.utils.dataReader.jsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

@Epic("Automation Exercise")
@Feature("UI Invoice")
@Story("Invoice")
@Severity(SeverityLevel.CRITICAL)
@Owner("Merna")
@UITest
public class InvoiceTest extends BaseTest {
    String timeStamp = TimeManager.getSimpleTimestamp();


    @Test
    public void registerNewAccount() {
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

    }

    @Test(dependsOnMethods = "registerNewAccount")
    public void loginToAccount() {
        new SignupLogin(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timeStamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clicklLoginButton()
                .navigationBar
                .verifyUserLabel(testData.getJsonData("name"));

    }

    @Test(dependsOnMethods = {"loginToAccount", "registerNewAccount"})
    public void addProductToCart() {
        new ProductsPage(driver)
                .navigate()
                .clickOnAddToCart(testData.getJsonData("product.productId"))
                .validateItemAddedLabel(testData.getJsonData("messages.cartAdded"))
                .clickOnViewCart()
                .verifyProductDetailsOnCart(testData.getJsonData("product.name"),
                        testData.getJsonData("product.price"),
                        testData.getJsonData("product.quantity"),
                        testData.getJsonData("product.total"));
    }

    @Test(dependsOnMethods = {"addProductToCart", "loginToAccount", "registerNewAccount"})
    public void proceedToCheckout() {
        new CartPage(driver)
                .clickOnProceedToCheckout()
                .verifyDeliveryAddress(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyBillingAddress(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyOrderTotal();

    }

    @Test(dependsOnMethods = {"proceedToCheckout", "addProductToCart", "loginToAccount", "registerNewAccount"})
    public void paymentTest() {
        new CheckoutPage(driver)
                .clickOnPlaceOrder()
                .fillCardInfo(testData.getJsonData("card.cardName"),
                        testData.getJsonData("card.cardNumber"),
                        testData.getJsonData("card.cvc"),
                        testData.getJsonData("card.exMonth"),
                        testData.getJsonData("card.exYear"))
                .verifyPaymentSuccessMessageIsDesplayed(testData.getJsonData("messages.paymentSuccess"));
    }

    @Test(dependsOnMethods = {"paymentTest", "proceedToCheckout", "addProductToCart", "loginToAccount", "registerNewAccount"})
    public void downloadInvoice() throws IOException {
        new PaymentPage(driver)
                .clickDownloadInvoiceButton()
                .verifyDownloadedFile(testData.getJsonData("invoiceName"));


    }

    @Test(dependsOnMethods = {"paymentTest", "proceedToCheckout", "addProductToCart", "loginToAccount", "registerNewAccount"})
    public void deleteAccountAsPostCondition() {
        new UserManagementAPI()
                .deleteUserAccount(testData.getJsonData("email") + timeStamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }


    //Configurations

    //We used here before class and after class because we created a test suits
    @BeforeClass
    public void setUp() {
        testData = new jsonReader("checkout-data");
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
    }

    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }
}
