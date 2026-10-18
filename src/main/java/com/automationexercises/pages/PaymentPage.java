package com.automationexercises.pages;

import com.automationexercises.drivers.GUIDriver;
import com.automationexercises.pages.components.NavigationBarComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentPage {
    private final GUIDriver driver;
    //variables
    private final String paymentEndpoint = "/payment";
    //locators
    private final By nameOnCard = By.name("name_on_card");
    private final By cardNumber = By.name("card_number");
    private final By cardCvc = By.name("cvc");
    private final By cardMonthExpiration = By.name("expiry_month");
    private final By cardYearExpiration = By.name("expiry_year");
    private final By payAndConfirmOrderButton = By.id("submit");
    private final By paymentSuccessMessage = By.xpath("//section//h2/b");
    private final By downloadInvoiceButton = By.xpath("//a[.='Download Invoice']");

    public NavigationBarComponent navigationBar;

    public PaymentPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }


    //actions
    @Step("Fill card info and submit payment")
    public PaymentPage fillCardInfo(String name, String number, String CVC, String month, String year) {
        driver.element().type(nameOnCard, name)
                .type(cardNumber, number)
                .type(cardCvc, CVC)
                .type(cardMonthExpiration, month)
                .type(cardYearExpiration, year)
                .click(payAndConfirmOrderButton);
        return this;
    }

    @Step("Click on download invoice button")
    public PaymentPage clickDownloadInvoiceButton() {
        driver.element().click(downloadInvoiceButton);
        return this;
    }

    //validations
    @Step("Verify payment success message is displayed")
    public PaymentPage verifyPaymentSuccessMessageIsDesplayed(String expectedMessage) {
        driver.verification().Equals(driver.element().getText(paymentSuccessMessage), expectedMessage, "Payment success message");
        return this;
    }

}
