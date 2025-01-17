package org.selenium.pom.tests;

import org.junit.Assert;
import org.selenium.base.BaseTest;
import org.selenium.pages.CheckoutPage;
import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignUpApi;
import org.selenium.pom.factory.objects.Products;
import org.selenium.pom.factory.objects.Users;
import org.selenium.pom.factory.utils.FakerUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest extends BaseTest {

    @Test
    public void loginduringCheckout() throws IOException, InterruptedException {
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
        checkoutPage.load();
        Thread.sleep(5000);
           checkoutPage.clickShowloginLink().login(users);
           Thread.sleep(5000);
        Assert.assertTrue(checkoutPage.getProductName().contains(products.getName()));
    }
}
