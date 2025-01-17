package org.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.base.BasePage;
import org.selenium.pages.components.MyHeader;
import org.selenium.pages.components.ProductThumbnail;
import org.selenium.pom.factory.objects.Products;

import java.io.IOException;

public class HomePage extends BasePage {

    public MyHeader getMyHeader() {
        return myHeader;
    }
    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }
    private MyHeader myHeader;
    private ProductThumbnail productThumbnail;
    public HomePage(WebDriver driver) {
        super(driver);
        myHeader = new MyHeader(driver);
        productThumbnail = new ProductThumbnail(driver);
    }

    public HomePage load()
    {
        load("/");
        wait.until(ExpectedConditions.titleContains("AskOmDch"));
        return this;
    }
    public ProductPage navigateToTheProduct(int id) throws IOException, IOException {
        driver.findElement(By.xpath("//h2[normalize-space()='"+ new Products(id).getName().trim() + "']")).click();
        return new ProductPage(driver);
    }
}
