package org.selenium.pom.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.selenium.constants.DriverType;

import java.time.Duration;



public class DriverManager {

    public WebDriver initializeDriver(String browser) {
        WebDriver driver;

        // Validate and ensure the browser input is not null or invalid
        if (browser == null || browser.isEmpty()) {
            throw new IllegalArgumentException("The 'browser' system property is not set or is empty.");
        }

        try {
            switch (DriverType.valueOf( browser )) { // Corrected to `.toUpperCase()`
                case CHROME -> {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                }
                case FIREFOX -> {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                }
                default -> throw new IllegalStateException("Unexpected value: " + browser);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid browser name provided: " + browser, e);
        }

        driver.manage().window().maximize();
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        return driver;
    }
}
