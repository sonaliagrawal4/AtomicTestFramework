package org.selenium.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.pom.factory.utils.ConfigLoader;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected  WebDriverWait wait;

    public BasePage(WebDriver driver)
    {
        this.driver=driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    public void load(String endpoint)
    {
        driver.get(ConfigLoader.getInstance().getBaseUrl()+endpoint);
    }
    public void waitForOverlaysToDisappear(By overlay)
    {
        List<WebElement> overlays=driver.findElements(overlay);
        System.out.println("OVERLAY SIZE :"+overlays.size());
        if(overlays.size()>0)
        {
            wait.until(
                    ExpectedConditions.invisibilityOfAllElements(overlays)
            );
            System.out.println("Overlays invisible");
        }
        else {
            System.out.println("Overlay not found");
        }
    }
    public WebElement waitForElementToBeVisible(By element)
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }
    public void scrollIntoView(By element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
    }

}
