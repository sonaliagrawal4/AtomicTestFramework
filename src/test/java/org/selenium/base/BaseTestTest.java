package org.selenium.base;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeDriver;
import org.selenium.pom.factory.DriverManager;
import org.selenium.pom.factory.utils.CookieUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class BaseTestTest {

    private BaseTest baseTest;

    @Mock
    private WebDriver mockWebDriver;

    @Mock
    private DriverManager mockDriverManager;

    @Mock
    private CookieUtils mockCookieUtils;

    @Mock
    private Options mockOptions;

    @Mock
    private ITestResult mockTestResult;

    @Mock
    private org.testng.IClass mockTestClass;

    @Mock
    private org.testng.ITestNGMethod mockTestMethod;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        baseTest = new BaseTest();
        when(mockWebDriver.manage()).thenReturn(mockOptions);
    }

    @Test(description = "Test startDriver with default Chrome browser when browser parameter is null")
    public void testStartDriverWithNullBrowser() {
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            DriverManager driverManager = mock(DriverManager.class);
            when(driverManager.initializeDriver("CHROME")).thenReturn(mockWebDriver);
            mockedDriverManager.when(() -> new DriverManager()).thenReturn(driverManager);

            baseTest.startDriver(null);

            assertNotNull(baseTest.getDriver());
            assertEquals(baseTest.getDriver(), mockWebDriver);
            verify(driverManager).initializeDriver("CHROME");
        }
    }

    @Test(description = "Test startDriver with specified browser parameter")
    public void testStartDriverWithSpecifiedBrowser() {
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            DriverManager driverManager = mock(DriverManager.class);
            when(driverManager.initializeDriver("FIREFOX")).thenReturn(mockWebDriver);
            mockedDriverManager.when(() -> new DriverManager()).thenReturn(driverManager);

            baseTest.startDriver("FIREFOX");

            assertNotNull(baseTest.getDriver());
            assertEquals(baseTest.getDriver(), mockWebDriver);
            verify(driverManager).initializeDriver("FIREFOX");
        }
    }

    @DataProvider(name = "browserTypes")
    public Object[][] browserTypes() {
        return new Object[][]{
            {"CHROME"},
            {"FIREFOX"},
            {"EDGE"},
            {"SAFARI"}
        };
    }

    @Test(dataProvider = "browserTypes", description = "Test startDriver with various browser types")
    public void testStartDriverWithVariousBrowsers(String browser) {
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            DriverManager driverManager = mock(DriverManager.class);
            when(driverManager.initializeDriver(browser)).thenReturn(mockWebDriver);
            mockedDriverManager.when(() -> new DriverManager()).thenReturn(driverManager);

            baseTest.startDriver(browser);

            assertNotNull(baseTest.getDriver());
            assertEquals(baseTest.getDriver(), mockWebDriver);
            verify(driverManager).initializeDriver(browser);
        }
    }

    @Test(description = "Test quitDriver with successful test result")
    public void testQuitDriverWithSuccessfulTest() throws IOException {
        // Setup
        baseTest.setDriver(mockWebDriver);
        when(mockTestResult.getStatus()).thenReturn(ITestResult.SUCCESS);

        baseTest.quitDriver("CHROME", mockTestResult);

        verify(mockWebDriver).quit();
        verify(mockTestResult, never()).getTestClass();
        verify(mockTestResult, never()).getMethod();
    }

    @Test(description = "Test quitDriver with failed test result - should take screenshot")
    public void testQuitDriverWithFailedTest() throws IOException {
        // Setup
        baseTest.setDriver(mockWebDriver);
        when(mockTestResult.getStatus()).thenReturn(ITestResult.FAILURE);
        when(mockTestResult.getTestClass()).thenReturn(mockTestClass);
        when(mockTestResult.getMethod()).thenReturn(mockTestMethod);
        when(mockTestClass.getRealClass()).thenReturn((Class) BaseTestTest.class);
        when(mockTestMethod.getMethodName()).thenReturn("testMethod");

        baseTest.quitDriver("CHROME", mockTestResult);

        verify(mockWebDriver).quit();
        verify(mockTestResult).getTestClass();
        verify(mockTestResult).getMethod();
    }

    @Test(description = "Test quitDriver with skipped test result")
    public void testQuitDriverWithSkippedTest() throws IOException {
        // Setup
        baseTest.setDriver(mockWebDriver);
        when(mockTestResult.getStatus()).thenReturn(ITestResult.SKIP);

        baseTest.quitDriver("FIREFOX", mockTestResult);

        verify(mockWebDriver).quit();
        verify(mockTestResult, never()).getTestClass();
        verify(mockTestResult, never()).getMethod();
    }

    @Test(description = "Test injectCookiesToBrowser with valid cookies")
    public void testInjectCookiesToBrowserWithValidCookies() {
        // Setup
        baseTest.setDriver(mockWebDriver);

        Cookies restAssuredCookies = new Cookies(
            new Cookie.Builder("sessionId", "abc123").build(),
            new Cookie.Builder("userId", "user456").build()
        );

        List<org.openqa.selenium.Cookie> seleniumCookies = new ArrayList<>();
        seleniumCookies.add(new org.openqa.selenium.Cookie.Builder("sessionId", "abc123")
            .isHttpOnly(true)
            .build());
        seleniumCookies.add(new org.openqa.selenium.Cookie.Builder("userId", "user456")
            .isHttpOnly(true)
            .build());

        try (MockedStatic<CookieUtils> mockedCookieUtils = mockStatic(CookieUtils.class)) {
            CookieUtils cookieUtils = mock(CookieUtils.class);
            when(cookieUtils.covertRestAssuredCookiesToSeleniumCookies(restAssuredCookies))
                .thenReturn(seleniumCookies);
            mockedCookieUtils.when(() -> new CookieUtils()).thenReturn(cookieUtils);

            baseTest.injectCookiesToBrowser(restAssuredCookies);

            verify(mockOptions, times(2)).addCookie(argThat(cookie -> cookie.isHttpOnly()));
            verify(cookieUtils).covertRestAssuredCookiesToSeleniumCookies(restAssuredCookies);
        }
    }

    @Test(description = "Test injectCookiesToBrowser with empty cookies list")
    public void testInjectCookiesToBrowserWithEmptyCookies() {
        // Setup
        baseTest.setDriver(mockWebDriver);

        Cookies emptyCookies = new Cookies();
        List<org.openqa.selenium.Cookie> emptySeleniumCookies = new ArrayList<>();

        try (MockedStatic<CookieUtils> mockedCookieUtils = mockStatic(CookieUtils.class)) {
            CookieUtils cookieUtils = mock(CookieUtils.class);
            when(cookieUtils.covertRestAssuredCookiesToSeleniumCookies(emptyCookies))
                .thenReturn(emptySeleniumCookies);
            mockedCookieUtils.when(() -> new CookieUtils()).thenReturn(cookieUtils);

            baseTest.injectCookiesToBrowser(emptyCookies);

            verify(mockOptions, never()).addCookie(any(org.openqa.selenium.Cookie.class));
            verify(cookieUtils).covertRestAssuredCookiesToSeleniumCookies(emptyCookies);
        }
    }

    @Test(description = "Test injectCookiesToBrowser with null cookies - should handle gracefully")
    public void testInjectCookiesToBrowserWithNullCookies() {
        // Setup
        baseTest.setDriver(mockWebDriver);

        try (MockedStatic<CookieUtils> mockedCookieUtils = mockStatic(CookieUtils.class)) {
            CookieUtils cookieUtils = mock(CookieUtils.class);
            when(cookieUtils.covertRestAssuredCookiesToSeleniumCookies(null))
                .thenReturn(new ArrayList<>());
            mockedCookieUtils.when(() -> new CookieUtils()).thenReturn(cookieUtils);

            baseTest.injectCookiesToBrowser(null);

            verify(mockOptions, never()).addCookie(any(org.openqa.selenium.Cookie.class));
        }
    }

    @Test(description = "Test thread safety of driver ThreadLocal")
    public void testDriverThreadSafety() throws InterruptedException {
        final WebDriver[] drivers = new WebDriver[2];
        final Thread[] threads = new Thread[2];

        for (int i = 0; i < 2; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                BaseTest localBaseTest = new BaseTest();
                WebDriver localDriver = mock(WebDriver.class);
                localBaseTest.setDriver(localDriver);
                drivers[index] = localBaseTest.getDriver();
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // Each thread should have its own driver instance
        assertNotNull(drivers[0]);
        assertNotNull(drivers[1]);
        assertNotSame(drivers[0], drivers[1]);
    }

    @Test(description = "Test getDriver returns null when no driver is set")
    public void testGetDriverWhenNotSet() {
        BaseTest newBaseTest = new BaseTest();
        assertNull(newBaseTest.getDriver());
    }

    @Test(description = "Test setDriver and getDriver work correctly")
    public void testSetAndGetDriver() {
        BaseTest newBaseTest = new BaseTest();
        WebDriver testDriver = mock(WebDriver.class);

        newBaseTest.setDriver(testDriver);

        assertEquals(newBaseTest.getDriver(), testDriver);
    }

    @Test(description = "Test screenshot file path generation for failed tests")
    public void testScreenshotFilePathGeneration() throws IOException {
        // Setup
        baseTest.setDriver(mockWebDriver);
        when(mockTestResult.getStatus()).thenReturn(ITestResult.FAILURE);
        when(mockTestResult.getTestClass()).thenReturn(mockTestClass);
        when(mockTestResult.getMethod()).thenReturn(mockTestMethod);
        when(mockTestClass.getRealClass()).thenReturn((Class) BaseTestTest.class);
        when(mockTestMethod.getMethodName()).thenReturn("testScreenshotMethod");

        baseTest.quitDriver("FIREFOX", mockTestResult);

        // Verify that screenshot logic was triggered
        verify(mockTestResult).getTestClass();
        verify(mockTestResult).getMethod();
        verify(mockTestClass).getRealClass();
        verify(mockTestMethod).getMethodName();
        verify(mockWebDriver).quit();
    }

    @Test(description = "Test multiple browser sessions don't interfere with each other")
    public void testMultipleBrowserSessions() {
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            DriverManager driverManager = mock(DriverManager.class);
            WebDriver chromeDriver = mock(WebDriver.class);
            WebDriver firefoxDriver = mock(WebDriver.class);

            when(driverManager.initializeDriver("CHROME")).thenReturn(chromeDriver);
            when(driverManager.initializeDriver("FIREFOX")).thenReturn(firefoxDriver);
            mockedDriverManager.when(() -> new DriverManager()).thenReturn(driverManager);

            // Test Chrome session
            baseTest.startDriver("CHROME");
            assertEquals(baseTest.getDriver(), chromeDriver);

            // Switch to Firefox session
            baseTest.startDriver("FIREFOX");
            assertEquals(baseTest.getDriver(), firefoxDriver);

            verify(driverManager).initializeDriver("CHROME");
            verify(driverManager).initializeDriver("FIREFOX");
        }
    }

    @Test(description = "Test cookie injection with special characters in cookie values")
    public void testInjectCookiesWithSpecialCharacters() {
        // Setup
        baseTest.setDriver(mockWebDriver);

        Cookies specialCookies = new Cookies(
            new Cookie.Builder("special", "value with spaces & symbols!@#$%").build(),
            new Cookie.Builder("encoded", "value%20with%20encoding").build()
        );

        List<org.openqa.selenium.Cookie> seleniumCookies = new ArrayList<>();
        seleniumCookies.add(new org.openqa.selenium.Cookie.Builder("special", "value with spaces & symbols!@#$%")
            .isHttpOnly(true)
            .build());
        seleniumCookies.add(new org.openqa.selenium.Cookie.Builder("encoded", "value%20with%20encoding")
            .isHttpOnly(true)
            .build());

        try (MockedStatic<CookieUtils> mockedCookieUtils = mockStatic(CookieUtils.class)) {
            CookieUtils cookieUtils = mock(CookieUtils.class);
            when(cookieUtils.covertRestAssuredCookiesToSeleniumCookies(specialCookies))
                .thenReturn(seleniumCookies);
            mockedCookieUtils.when(() -> new CookieUtils()).thenReturn(cookieUtils);

            baseTest.injectCookiesToBrowser(specialCookies);

            verify(mockOptions, times(2)).addCookie(argThat(cookie -> cookie.isHttpOnly()));
        }
    }

    @Test(description = "Test startDriver handles DriverManager initialization exceptions")
    public void testStartDriverWithDriverManagerException() {
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            DriverManager driverManager = mock(DriverManager.class);
            when(driverManager.initializeDriver("CHROME")).thenThrow(new RuntimeException("Driver initialization failed"));
            mockedDriverManager.when(() -> new DriverManager()).thenReturn(driverManager);

            assertThrows(RuntimeException.class, () -> baseTest.startDriver("CHROME"));
        }
    }

    @Test(description = "Test quitDriver handles driver quit exceptions gracefully")
    public void testQuitDriverWithException() throws IOException {
        // Setup
        baseTest.setDriver(mockWebDriver);
        when(mockTestResult.getStatus()).thenReturn(ITestResult.SUCCESS);
        doThrow(new RuntimeException("Driver quit failed")).when(mockWebDriver).quit();

        assertThrows(RuntimeException.class, () -> baseTest.quitDriver("CHROME", mockTestResult));
    }

    // Helper method to expose private setDriver method for testing
    private static class TestableBaseTest extends BaseTest {
        public void setDriver(WebDriver driver) {
            super.setDriver(driver);
        }
    }

    @Test(description = "Test BaseTest inheritance and method visibility")
    public void testBaseTestInheritance() {
        TestableBaseTest testableBaseTest = new TestableBaseTest();
        WebDriver testDriver = mock(WebDriver.class);

        testableBaseTest.setDriver(testDriver);

        assertEquals(testableBaseTest.getDriver(), testDriver);
        assertNotNull(testableBaseTest.getDriver());
    }
}