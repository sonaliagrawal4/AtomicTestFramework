package org.selenium.pom.tests;

import org.selenium.base.BaseTest;
import org.selenium.pages.ProductPage;
import org.selenium.pages.StorePage;
import org.selenium.pom.factory.objects.Products;
import org.selenium.pom.factory.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class SearchTest extends BaseTest {

    @Test
    public void searchWithPartialMatch()
    {
        String searchFor="Blue";
        StorePage storePage=new StorePage(getDriver()).load()
        .search("Blue");
        Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");

    }

    @Test
    public void searchWithExactMatch() throws IOException, IOException {
        Products product = new Products(1215);
        ProductPage productPage = new StorePage(getDriver()).
                load().
                searchExactMatch(product.getName());
        Assert.assertEquals(productPage.getProductTitle(),product.getName());
    }

    @Test
    public void SearchForNonExistingProduct() {
        StorePage storePage = new StorePage(getDriver()).
                load().
                search(new FakerUtils().generateRandomName());
        Assert.assertEquals(storePage.getInfo(),"No products were found matching your selection.");
    }
}
