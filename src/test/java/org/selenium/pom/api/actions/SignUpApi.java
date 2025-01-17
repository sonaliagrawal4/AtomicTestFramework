package org.selenium.pom.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.selenium.constants.EndPoint;
import org.selenium.pom.factory.objects.Users;
import org.selenium.pom.factory.utils.ConfigLoader;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class SignUpApi {
    private Cookies cookies;
    public Cookies getCookies()
    {
        return  cookies;
    }
    public String fetchRegisterNonceValueUsingGroovy()
    {
        Response response =getAccount();
       return response.htmlPath().getString("**.findAll{ it.@name == 'woocommerce-register-nonce' }.@value");
    }
    public String fetchRegisterNonceValueUsingJsoup()
    {
        Response response =getAccount();
        Document doc= Jsoup.parse(response.body().prettyPrint());
        Element element= doc.selectFirst("#woocommerce-register-nonce");
        return element.attr("value");
    }
    public Response getAccount()
    {
        Cookies cookies=new Cookies();
        Response response = ApiRequest.get(EndPoint.ACCOUNT.url, cookies);
        if(response.getStatusCode()!=200)
        {
            throw new RuntimeException("Failed to fetch the account, HTTP status code :"+response.getStatusCode());
        }
        return response;
    }
    public Response register(Users user)
    {
        Cookies cookies=new Cookies();
        Header header=new Header("content-type","application/x-www-form-urlencoded");
        Headers headers=new Headers(header);
        HashMap<String, Object> formParams = new HashMap<>();
        formParams.put("username",user.getUsername());
        formParams.put("email", user.getEmail());
        formParams.put("password",user.getPassword());
        formParams.put("woocommerce-register-nonce",fetchRegisterNonceValueUsingJsoup());
        formParams.put("register","Register");

        Response response = ApiRequest.post(EndPoint.ACCOUNT.url, headers, formParams, cookies);
        if(response.getStatusCode()!=200)
        {
            throw new RuntimeException("Failed to register the account, HTTP status code :"+response.getStatusCode());
        }
        this.cookies=response.getDetailedCookies();
        return response;

    }
}
