package org.selenium.pom.tests;

import io.restassured.http.Cookies;
import org.selenium.base.BaseTest;
import org.selenium.pages.CheckoutPage;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignUpApi;
import org.selenium.pom.factory.objects.BillingAddress;
import org.selenium.pom.factory.objects.Products;
import org.selenium.pom.factory.objects.Users;
import org.selenium.pom.factory.utils.FakerUtils;
import org.selenium.pom.factory.utils.JaksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CheckoutTest extends BaseTest {


    @Test
    public void GuestCheckoutUsingDirectbankTransfer() throws IOException {
        BillingAddress billingAddress= JaksonUtils.deserializeJson("myBillingAddress.json",BillingAddress.class);
        CheckoutPage checkoutPage=new CheckoutPage(getDriver()).load();
        CartApi cartApi=new CartApi();
        cartApi.addToCart(1215,1);
        injectCookiesToBrowser(cartApi.getCookies());
        checkoutPage.load().
        setBillingAddressDetails(billingAddress)
        .clickPlaceOrderBtn();
        Assert.assertEquals(checkoutPage.getNotice(),"Thank you. Your order has been received.");
    }

    @Test
    public void LoginAndCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {
        BillingAddress billingAddress= JaksonUtils.deserializeJson("myBillingAddress.json",BillingAddress.class);
        String username="demouser"+new FakerUtils().generateRandomNumber();
        Users users=new Users()
                .setUsername(username)
                .setEmail(username+"askomdch.com")
                .setPassword("demopwd");
        SignUpApi signUpApi=new SignUpApi();
        signUpApi.register(users);
        CartApi cartApi=new CartApi(signUpApi.getCookies());
        Products products=new Products(1215);
        cartApi.addToCart(products.getId(),1);


        CheckoutPage checkoutPage=new CheckoutPage(getDriver()).load();
        Thread.sleep(5000);
        injectCookiesToBrowser(cartApi.getCookies());
        checkoutPage.load().
        setBillingAddressDetails(billingAddress)
                .clickPlaceOrderBtn();
        Assert.assertEquals(checkoutPage.getNotice(),"Thank you. Your order has been received.");
    }

    @Test
    public void GuestCheckoutUsingCashOnDelivery() throws IOException, InterruptedException {
        BillingAddress billingAddress = JaksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();

        CartApi cartApi = new CartApi(new Cookies());
        cartApi.addToCart(1215, 1);
        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.load().
                setBillingAddressDetails(billingAddress);
        Thread.sleep(5000);
                checkoutPage.selectCashOnDeliveryTransfer();

                checkoutPage.placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }

    @Test
    public void LoginAndCheckoutUsingCashOnDelivery() throws IOException, InterruptedException {
        BillingAddress billingAddress = JaksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        String username = "demouser1";
        Users user = new Users(username, "abcd", username + "@askomdch.com");

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        CartApi cartApi = new CartApi(signUpApi.getCookies());
        Products product = new Products(1215);
        cartApi.addToCart(product.getId(), 1);

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(signUpApi.getCookies());
        checkoutPage.load().
                setBillingAddressDetails(billingAddress);

        checkoutPage.selectCashOnDeliveryTransfer();

        checkoutPage.placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }
}
