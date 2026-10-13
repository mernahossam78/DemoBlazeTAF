package com.automationexercises.pages.components;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.OrderPage;
import com.automationexercises.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutPage {
    private final GUIDriver driver;
    //delivery address
    private final By deliveryName = By.xpath("//ul[@id='address_delivery']/li[@class='address_firstname address_lastname']");
    private final By deliveryCompany = By.xpath("//ul[@id='address_delivery']/li[@class='address_address1 address_address2'][1]");
    private final By deliveryAddress1 = By.xpath("//ul[@id='address_delivery']/li[@class='address_address1 address_address2'][2]");
    private final By deliveryAddress2 = By.xpath("//ul[@id='address_delivery']/li[@class='address_address1 address_address2'][3]");
    private final By deliveryCityStateZipcode = By.xpath("//ul[@id='address_delivery']/li[@class='address_city address_state_name address_postcode']");
    private final By deliveryCountry = By.xpath("//ul[@id='address_delivery']/li[@class='address_country_name']");
    private final By deliveryPhone = By.xpath("//ul[@id='address_delivery']/li[@class='address_phone']");
    //billing address
    private final By billingName = By.xpath("//ul[@id='address_invoice']/li[@class='address_firstname address_lastname']");
    private final By billingCompany = By.xpath("//ul[@id='address_invoice']/li[@class='address_address1 address_address2'][1]");
    private final By billingAddress1 = By.xpath("//ul[@id='address_invoice']/li[@class='address_address1 address_address2'][2]");
    private final By billingAddress2 = By.xpath("//ul[@id='address_invoice']/li[@class='address_address1 address_address2'][3]");
    private final By billingCityStateZipcode = By.xpath("//ul[@id='address_invoice']/li[@class='address_city address_state_name address_postcode']");
    private final By billingCountry = By.xpath("//ul[@id='address_invoice']/li[@class='address_country_name']");
    private final By billingPhone = By.xpath("//ul[@id='address_invoice']/li[@class='address_phone']");
    //Variables
    private final String checkoutEndpoint = "/checkout";
    //locators
    private final By productTotals = By.xpath("//tr//td[@class='cart_total']/p");
    private final By totalAmount = By.xpath("//tr[td/h4[.='Total Amount']]//p[@class='cart_total_price']");
    private final By placeOrderButton = By.xpath("//a[.='Place Order']");
    public NavigationBarComponent navigationBar;

    public CheckoutPage(GUIDriver driver) {
        this.driver = driver;
    }

    //actions
    @Step("Navigate to Checkout Page")
    public CheckoutPage navigate() {
        driver.browser()
                .navigateTo(PropertyReader.getProperty("baseUrlWeb") + checkoutEndpoint);
        return this;
    }

    @Step("Click on Place Order")
    public OrderPage clickOnPlaceOrder() {
        driver.element().click(placeOrderButton);
        return new OrderPage(driver);
    }


    //validations
    @Step("Verify Delivery Address")
    public CheckoutPage verifyDeliveryAddress(String title, String fName, String lName, String company, String address1,
                                              String address2, String city, String state, String zipcode, String country, String phone) {
        // Implementation for verifying delivery address
        driver.validation().Equals(driver.element().getText(deliveryName), (title + ". " + fName + " " + lName), "Delivery name is not matched")
                .Equals(driver.element().getText(deliveryCompany), company, "Delivery company is not matched")
                .Equals(driver.element().getText(deliveryAddress1), address1, "Delivery address 1 is not matched")
                .Equals(driver.element().getText(deliveryAddress2), address2, "Delivery address 2 is not matched")
                .Equals(driver.element().getText(deliveryCityStateZipcode), (zipcode + " " + city + " " + state), "Delivery city, state, zipcode is not matched")
                .Equals(driver.element().getText(deliveryCountry), country, "Delivery country is not matched")
                .Equals(driver.element().getText(deliveryPhone), phone, "Delivery phone is not matched");
        return this;
    }

    @Step("Verify Billing Address")
    public CheckoutPage verifyBillingAddress(String title, String fName, String lName, String company, String address1,
                                             String address2, String city, String state, String zipcode, String country, String phone) {
        // Implementation for verifying billing address
        driver.validation().Equals(driver.element().getText(billingName), (title + ". " + fName + " " + lName), "Billing name is not matched")
                .Equals(driver.element().getText(billingCompany), company, "Billing company is not matched")
                .Equals(driver.element().getText(billingAddress1), address1, "Billing address 1 is not matched")
                .Equals(driver.element().getText(billingAddress2), address2, "Billing address 2 is not matched")
                .Equals(driver.element().getText(billingCityStateZipcode), (zipcode + " " + city + " " + state), "Billing city, state, zipcode is not matched")
                .Equals(driver.element().getText(billingCountry), country, "Billing country is not matched")
                .Equals(driver.element().getText(billingPhone), phone, "Billing phone is not matched");
        return this;
    }

    @Step("Verify Order Total")
    public CheckoutPage verifyOrderTotal() {
        List<WebElement> totals = driver.element().findElements(productTotals);
        double sum = 0.0;
        String totalAmountText = driver.element().findElement(totalAmount).getText();
        double totalAmount = Double.parseDouble(
                totalAmountText.replaceAll("[^0-9.]", "")
        );
        for (WebElement total : totals) {
            String priceText = total.getText();
            int price = Integer.parseInt(
                    priceText.replaceAll("[^0-9]", "")
            );
            sum += price;
        }
        driver.verification().Equals(String.valueOf(sum), String.valueOf(totalAmount), "Total number does not match");
        return this;
    }

}
