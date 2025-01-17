package org.selenium.pom.tests;

import org.selenium.base.BaseTest;
import org.selenium.pages.CartPage;
import org.selenium.pages.HomePage;
import org.selenium.pages.ProductPage;
import org.selenium.pages.StorePage;
import org.selenium.pom.factory.objects.Products;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class NavigationTest extends BaseTest {

    @Test
    public void NavigateFromHomeToStoreUsingMainMenu()
    {
        StorePage storePage= new HomePage(getDriver())
                .load().getMyHeader()
                .navigateToStoreUsingMenu();
        Assert.assertEquals(storePage.getTitle(),"Store");

    }
    @Test
    public void NavigateFromStoreToTheProduct() throws IOException, IOException {
        Products product = new Products(1215);
        ProductPage productPage = new StorePage(getDriver()).
                load().
                navigateToTheProduct(product.getId());
        Assert.assertEquals(productPage.getProductTitle().trim(), product.getName().trim());
    }

    @Test()
    public void NavigateFromHomeToTheFeaturedProduct() throws IOException {
        Products product = new Products(1215);
        ProductPage productPage = new HomePage(getDriver()).
                load().
                navigateToTheProduct(product.getId());
        Assert.assertEquals(productPage.getProductTitle().trim(), product.getName().trim());
    }
}
