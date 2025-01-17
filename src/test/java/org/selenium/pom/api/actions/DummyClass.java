package org.selenium.pom.api.actions;

import org.selenium.pom.factory.objects.Users;
import org.selenium.pom.factory.utils.FakerUtils;

public class DummyClass {
    public static void main(String[] args)
    {
        String username="demouser"+new FakerUtils().generateRandomNumber();
        Users users=new Users()
                .setUsername(username)
                .setEmail(username+"askomdch.com")
                .setPassword("demopwd");
        SignUpApi signUpApi=new SignUpApi();
        signUpApi.register(users);
        System.out.println("Register Cookies"+signUpApi.getCookies());
        CartApi  cartApi=new CartApi(signUpApi.getCookies());
        cartApi.addToCart(1215,1);
        System.out.println("Cart Cookies"+cartApi.getCookies());
    }
}
