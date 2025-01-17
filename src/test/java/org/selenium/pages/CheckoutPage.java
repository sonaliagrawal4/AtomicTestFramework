package org.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.selenium.base.BasePage;
import org.selenium.pom.factory.objects.BillingAddress;
import org.selenium.pom.factory.objects.Users;

public class CheckoutPage extends BasePage {
    private final By firstname=By.id("billing_first_name");
    private  final By lastname=By.id("billing_last_name");
    private final By billingAddress=By.id("billing_address_1");
    private final By billingCity=By.id("billing_city");
    private final By billingPostcode=By.id("billing_postcode");
    private final By billingEmail =By.id("billing_email");
    private final By placeOrderBtn=By.id("place_order");
    private final By thankYouTxt=By.xpath("//p[contains(@class,'thankyou')]");
    private final By showLoginLink=By.cssSelector(".showlogin");
    private final By username=By.id("username");
    private final By password=By.id("password");
    private final By loginBtn=By.cssSelector("button[value='Login']");
    private final By overlay=By.cssSelector(".blockUI.blockOverlay");
    private final By countryDropdown=By.id("billing_country");
    private final By stateDropdown=By.id("billing_state");
    private final By directBankTransferRadioBtn=By.id("payment_method_bacs");
    private final By alternateCountryDropdown = By.id("select2-billing_country-container");
    private final By alternateStateDropdown = By.id("select2-billing_state-container");
    private final By productName=By.cssSelector("td[class='product-name']");
    private final By cashOnDeliveryTransferRadioBtn = By.id("payment_method_cod");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage load()
    {
        load("/checkout");
        return this;
    }
    public CheckoutPage enterFirstname(String Firstname)
    {
        WebElement e=waitForElementToBeVisible(firstname);
        e.sendKeys(Firstname);
       // driver.findElement(firstname).sendKeys(Firstname);
        return this;
    }
    public CheckoutPage selectCountry(String countryName)
    {
       // Select select= new Select(wait.until(ExpectedConditions.elementToBeClickable(countryDropdown)));
        //select.selectByVisibleText(countryName);

        wait.until(ExpectedConditions.elementToBeClickable(alternateCountryDropdown)).click();
        WebElement e= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='"+countryName+"']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",e);
        e.click();
        return this;

    }
    public CheckoutPage selectState(String stateName)
    {
        //Select select= new Select(wait.until(ExpectedConditions.elementToBeClickable(stateDropdown)));
       // select.selectByVisibleText(stateName);
        wait.until(ExpectedConditions.elementToBeClickable(alternateStateDropdown)).click();
        WebElement e= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='"+stateName+"']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",e);
        e.click();
        return this;


    }
    public CheckoutPage enterLastname(String Lastname)
    {
         waitForElementToBeVisible(lastname).sendKeys(Lastname);
        return this;
    }
    public CheckoutPage enterBillingAddress(String BillingAddress)
    {
        waitForElementToBeVisible(billingAddress).sendKeys(BillingAddress);
        return this;
    }
    public CheckoutPage enterBillingCity(String BillingCity)
    {
        waitForElementToBeVisible(billingCity).sendKeys(BillingCity);
        return this;
    }
    public CheckoutPage enterBillingPostcode(String BillingPostcode)
    {
        waitForElementToBeVisible(billingPostcode).sendKeys(BillingPostcode);
        return this;
    }
    public CheckoutPage enterBillingEmail(String BillingEmail)
    {
        waitForElementToBeVisible(billingEmail).sendKeys(BillingEmail);
        return this;
    }
    public CheckoutPage setBillingAddressDetails(BillingAddress billingAddress)
    {
       return enterFirstname(billingAddress.getFirstname())
                .enterLastname(billingAddress.getLastname())
                .selectCountry(billingAddress.getCountry())
                .enterBillingAddress(billingAddress.getAddressLine1())
                .enterBillingCity(billingAddress.getCity())
               .selectState(billingAddress.getState())
                .enterBillingPostcode(billingAddress.getPostalCode())
                .enterBillingEmail(billingAddress.getEmail());
    }

    public CheckoutPage setUserDetails(Users users)
    {
        return enterUsername(users.getUsername())
                .enterPassword(users.getPassword());
    }
    public CheckoutPage clickPlaceOrderBtn()
    {
        waitForOverlaysToDisappear(overlay);
        driver.findElement(placeOrderBtn).click();
        return this;
    }
    public String getNotice()
    {
        return waitForElementToBeVisible(thankYouTxt).getText();

    }
    public CheckoutPage clickShowloginLink()
    {
        wait.until(ExpectedConditions.elementToBeClickable(showLoginLink)).click();
        return this;
    }
    public CheckoutPage enterUsername(String usernametxt)
    {
        waitForElementToBeVisible(username).sendKeys(usernametxt);
        return this;
    }
    public CheckoutPage enterPassword(String passwordtxt)
    {
        waitForElementToBeVisible(password).sendKeys(passwordtxt);
        return this;
    }
    public CheckoutPage clickloginBtn()
    {
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
        return this;
    }
    public CheckoutPage login(Users users)
    {
        return enterUsername(users.getUsername())
                .enterPassword(users.getPassword())
                .clickloginBtn();
    }
    public CheckoutPage selectDirectBankTransfer()
    {
        WebElement e=wait.until(ExpectedConditions.elementToBeClickable(directBankTransferRadioBtn));
        if(!e.isSelected())
        {
            e.click();
        }
        return this;
    }
    public  String getProductName()
    {
       return  wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }
    public CheckoutPage selectCashOnDeliveryTransfer(){
        wait.until(ExpectedConditions.elementToBeClickable(cashOnDeliveryTransferRadioBtn)).click();
        return this;
    }
    public CheckoutPage placeOrder(){
        waitForOverlaysToDisappear(overlay);
        driver.findElement(placeOrderBtn).click();
        return this;
    }

}
