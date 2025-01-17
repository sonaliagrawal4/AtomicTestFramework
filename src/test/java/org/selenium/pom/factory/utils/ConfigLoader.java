package org.selenium.pom.factory.utils;

import org.selenium.constants.EnvType;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;
    ConfigLoader()
    {
        String env=System.getProperty("env", String.valueOf(EnvType.STAGE) );
        switch (EnvType.valueOf(env)){
            case STAGE -> {
                properties=PropertyUtils.propertyLoader("src/test/resources/config.properties");
                break;
            }
            case PRODUCTION -> {
                properties=PropertyUtils.propertyLoader("src/test/resources/prodConfig.properties");
                break;
            }
            default -> throw new IllegalStateException("Invalid env type: "+env);
        }

    }
    public static ConfigLoader getInstance()
    {
        if(configLoader==null)
        {
            configLoader=new ConfigLoader();
        }
        return new ConfigLoader();
    }

    public String getBaseUrl()
    {
        String prop=properties.getProperty("baseUrl");
        if(prop!=null)return prop;
        else throw new RuntimeException("property baseUrl is not specified in the config.properties file");
    }
    public String getUsername()
    {
        String prop=properties.getProperty("username");
        if(prop!=null)return prop;
        else throw new RuntimeException("property username is not specified in the config.properties file");
    }
    public String getPassword()
    {
        String prop=properties.getProperty("password");
        if(prop!=null)return prop;
        else throw new RuntimeException("property password is not specified in the config.properties file");
    }
}
