package org.selenium.pom.factory.utils;

import io.restassured.http.Cookies;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

public class CookieUtils {

    public List<Cookie> covertRestAssuredCookiesToSeleniumCookies(Cookies cookies)
    {
        List<io.restassured.http.Cookie> restassuredCookies = new ArrayList<>();
        restassuredCookies=cookies.asList();
        List<Cookie> seleniumCookies = new ArrayList<>();
        for(io.restassured.http.Cookie cookie: restassuredCookies)
        {
            seleniumCookies.add(new Cookie(cookie.getName(),cookie.getValue(),cookie.getDomain(),cookie.getPath()
            ,cookie.getExpiryDate(),cookie.isSecured(),cookie.isHttpOnly(),cookie.getSameSite()));
        }
        return seleniumCookies;
    }
}
