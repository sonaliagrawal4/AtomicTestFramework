package org.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.base.BasePage;
import org.selenium.pages.components.ProductThumbnail;
import org.selenium.pom.factory.objects.Products;

import java.io.IOException;

public class StorePage extends BasePage {
private final By searchFeild = By.id("woocommerce-product-search-field-0");
private final By searchBtn =By.xpath("//button[@value='Search']");
private final By title= By.xpath("//h1");
private final By viewCartLink= By.xpath("//a[@title='View cart']");
private final By nonExistingProduct=By.cssSelector(".woocommerce-info.woocommerce-no-products-found");
private final By product=By.cssSelector("li[class='ast-col-sm-12 ast-article-post astra-woo-hover-swap product type-product post-1198 status-publish first instock product_cat-accessories product_cat-women has-post-thumbnail sale featured taxable shipping-taxable purchasable product-type-simple'] img[class='attachment-woocommerce_thumbnail size-woocommerce_thumbnail entered lazyloaded']");
    private final By infoTxt = By.cssSelector(".woocommerce-info");

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }
    private ProductThumbnail productThumbnail;
    public StorePage(WebDriver driver) {
        super(driver);
        productThumbnail = new ProductThumbnail(driver);
    }

    private StorePage enterTextInSearchFld(String txt){
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchFeild)).sendKeys(txt);
        return this;
    }
    public ProductPage searchExactMatch(String txt){
        enterTextInSearchFld(txt).clickSearchBtn();
        return new ProductPage(driver);
    }

    public Boolean isLoaded()
    {
       return wait.until(ExpectedConditions.urlContains("/store"));
    }
    public StorePage enterTextInSearchfeild(String txt)
    {
        waitForElementToBeVisible(searchFeild).sendKeys(txt);
        return this;
    }
    public StorePage search(String txt)
    {
        enterTextInSearchfeild(txt).clickSearchBtn();
        return this;
    }
    public StorePage clickSearchBtn()
    {
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        return this;
    }
    public StorePage load()
    {
        load("/store");
        return this;
    }

    public String getTitle()
    {
        return waitForElementToBeVisible(title).getText();
    }



    public ProductPage navigateToTheProduct(Integer id) throws IOException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[normalize-space()='"+ new Products(id).getName().trim() + "']"))).click();
        return new ProductPage(driver);
    }

    public String getInfo(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(infoTxt)).getText();
    }


}
