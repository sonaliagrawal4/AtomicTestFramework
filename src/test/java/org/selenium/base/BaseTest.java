package org.selenium.base;

import io.restassured.http.Cookies;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.factory.DriverManager;
import org.selenium.pom.factory.utils.CookieUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseTest {
protected ThreadLocal<WebDriver> driver=new ThreadLocal<>();

private void setDriver(WebDriver driver)
{
    this.driver.set(driver);
}
protected WebDriver getDriver()
{
    return this.driver.get();
}

    @Parameters("browser")
    @BeforeMethod
    public void startDriver(@Optional String browser)
    {
       //browser= System.getProperty("browser",browser);
        if(browser==null)
        {
            browser="CHROME";
        }

        setDriver(new DriverManager().initializeDriver(browser));
        System.out.println("CURRENT THREAD"+ Thread.currentThread().threadId()+","+"DRIVER = "+getDriver());
        System.out.println("hi");

    }

    @AfterMethod
    public void quitDriver(@Optional String browser,ITestResult result) throws IOException {System.out.println("CURRENT THREAD"+ Thread.currentThread().threadId()+","+"DRIVER = "+getDriver());

        if(result.getStatus()==ITestResult.FAILURE)
        {
            File destfile=new File("scr" + File.separator+ browser+
                    File.separator+result.getTestClass().getRealClass().getSimpleName()
            +"_"+result.getMethod().getMethodName()+".png");
            //takeScreenshot(destfile);
            takeScreenshotusingAshot(destfile);
        }
        getDriver().quit();
    }

    public void injectCookiesToBrowser(Cookies cookies)
    {
        List<Cookie> seleniumCookies= new CookieUtils().covertRestAssuredCookiesToSeleniumCookies(cookies);
        for(Cookie cookie:seleniumCookies)
        {
            getDriver().manage().addCookie(cookie);
        }
    }

    private void takeScreenshot(File destFile) throws IOException {
        TakesScreenshot takesScreenshot=(TakesScreenshot) getDriver();
        File srcFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile,destFile);
    }
    private void takeScreenshotusingAshot(File destFile)
    {
        Screenshot screenshot=new AShot()
                .coordsProvider(new WebDriverCoordsProvider()) //find coordinates with WebDriver API
                .takeScreenshot(getDriver());
        try
        {
            ImageIO.write(screenshot.getImage(),"PNG",destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
