package org.selenium.pom.tests;

import org.selenium.base.BaseTest;
import org.selenium.dataproviders.MyDataProvider;
import org.selenium.pages.CartPage;
import org.selenium.pages.HomePage;
import org.selenium.pages.ProductPage;
import org.selenium.pages.StorePage;
import org.selenium.pom.factory.objects.Products;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddToCart extends BaseTest {

    @Test
    public void addToCartFromStorePage() throws IOException {
        Products products=new Products(1215);
        CartPage cartPage=new StorePage(getDriver()).
                load().getProductThumbnail().
                clickAddToCartBtn(products.getName().trim()).
                clickViewCart();
        // driver.findElement(By.xpath("//a[@title='View cart']")).click();
        Assert.assertEquals(cartPage.getProductName(), products.getName().trim());
    }


    @Test(dataProvider = "getFeaturedProducts", dataProviderClass = MyDataProvider.class)
    public void addToCartFeaturedProducts(Products products){
        CartPage cartPage = new HomePage(getDriver()).load()
                .getProductThumbnail()
                .clickAddToCartBtn(products.getName()).clickViewCart();

        Assert.assertEquals(cartPage.getProductName(), products.getName().trim());
    }
    @Test()
    public void AddToCartFromProductPage() throws IOException {
        Products product = new Products(1215);
        String productNameSeparatedByDash = product.getName().toLowerCase().replaceAll(" ", "-");

        ProductPage productPage = new ProductPage(getDriver()).loadProduct(productNameSeparatedByDash).
                addToCart();
        Assert.assertTrue(productPage.getAlert().contains("“" + product.getName().trim() +"” has been added to your cart."));
    }
}


