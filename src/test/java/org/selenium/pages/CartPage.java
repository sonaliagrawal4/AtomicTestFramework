package org.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.base.BasePage;

public class CartPage extends BasePage {
    private final By productName = By.cssSelector("td[class='product-name'] a");
    private final By checkoutBtn=By.cssSelector(".checkout-button.button.alt.wc-forward");
    private final By cartText=By.cssSelector(".has-text-align-center");
    public CartPage(WebDriver driver) {
        super(driver);
    }
    public Boolean isLoaded()
    {
        return wait.until(ExpectedConditions.textToBe(cartText,"Cart"));
    }
    public String getProductName()
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }
    public CheckoutPage clickCheckoutPage()
    {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
        return new CheckoutPage(driver);
    }
}
