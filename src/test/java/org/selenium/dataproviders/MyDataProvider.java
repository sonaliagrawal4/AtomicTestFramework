package org.selenium.dataproviders;

import org.selenium.pom.factory.objects.Products;
import org.selenium.pom.factory.utils.JaksonUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class MyDataProvider {

    @DataProvider(name = "getFeaturedProducts", parallel = true)
    public Object[] getFeaturedProducts() throws IOException {
        return JaksonUtils.deserializeJson("products.json", Products[].class);
    }
}
