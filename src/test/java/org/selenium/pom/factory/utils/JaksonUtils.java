package org.selenium.pom.factory.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.selenium.pom.factory.objects.BillingAddress;

import java.io.IOException;
import java.io.InputStream;

public class JaksonUtils {

    public static <T> T deserializeJson(String fileName, Class <T> T) throws IOException {
        InputStream inputStream= JaksonUtils.class.getClassLoader().getResourceAsStream(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        ConfigLoader configLoader=new ConfigLoader();
        return  objectMapper.readValue(inputStream,T);
    }
}
