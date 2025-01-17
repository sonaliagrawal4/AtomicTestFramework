package org.selenium.pom.tests;

import org.selenium.base.BaseTest;
import org.selenium.pages.CartPage;
import org.selenium.pages.CheckoutPage;
import org.selenium.pages.HomePage;
import org.selenium.pages.StorePage;
import org.selenium.pom.factory.objects.BillingAddress;
import org.selenium.pom.factory.objects.Products;
import org.selenium.pom.factory.objects.Users;
import org.selenium.pom.factory.utils.JaksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class MyFirstTestCase extends BaseTest {

   // @Test
    public void guestCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
       BillingAddress billingAddress=JaksonUtils.deserializeJson("myBillingAddress.json",BillingAddress.class);
        Products products=new Products(1215);
//        billingAddress.setFirstname("Sonali")
//        .setLastname("Abcd")
//        .setAddressLine1("Sector 49")
//        .setCity("Jaipur")
//        .setPostalCode("94188")
//        .setEmail("testemail@gmail.com");

        StorePage storePage= new HomePage(getDriver())
                .load().getMyHeader()
                .navigateToStoreUsingMenu()
                .search("Blue");
        Assert.assertEquals(storePage.getTitle(),"Search results: “Blue”");
        storePage.getProductThumbnail().clickAddToCartBtn(products.getName().trim());
        CartPage cartPage=storePage.getProductThumbnail().clickViewCart();
      // driver.findElement(By.xpath("//a[@title='View cart']")).click();
       Assert.assertEquals(cartPage.getProductName(), products.getName().trim());
       //billing details
        CheckoutPage checkoutPage= cartPage.
                clickCheckoutPage()
                .selectDirectBankTransfer()
                .setBillingAddressDetails(billingAddress);
        checkoutPage.clickPlaceOrderBtn();
        Assert.assertEquals(checkoutPage.getNotice(),"Thank you. Your order has been received.");

    }
   // @Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
        BillingAddress billingAddress=JaksonUtils.deserializeJson("myBillingAddress.json",BillingAddress.class);
        Products products=new Products(1215);
        Users users=JaksonUtils.deserializeJson("user.json",Users.class);
        StorePage storePage= new HomePage(getDriver())
                .load().getMyHeader()
                .navigateToStoreUsingMenu()
                .search("Blue");
        Assert.assertEquals(storePage.getTitle(),"Search results: “Blue”");
        storePage.getProductThumbnail().clickAddToCartBtn(products.getName().trim());
        CartPage cartPage=storePage.getProductThumbnail().clickViewCart();
        Assert.assertEquals(cartPage.getProductName(),products.getName().trim());
        //billing details
        CheckoutPage checkoutPage= cartPage.clickCheckoutPage().selectDirectBankTransfer() .clickShowloginLink();
        checkoutPage.setUserDetails(users).clickloginBtn();

        //billing details
        checkoutPage.setBillingAddressDetails(billingAddress);
        checkoutPage.clickPlaceOrderBtn();
        Assert.assertEquals(checkoutPage.getNotice(),"Thank you. Your order has been received.");


    }
}
