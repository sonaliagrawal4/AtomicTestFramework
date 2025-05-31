🧱 BasePage.java
The BasePage class serves as the foundational base class for all Page Object classes in the Selenium framework. It provides common reusable utility methods that help interact with web elements and improve code readability and maintainability.

✅ Features:
Constructor:

Accepts a WebDriver instance and initializes an explicit WebDriverWait (30 seconds).

Methods:

load(String endpoint): Navigates to a specific page by appending the endpoint to a base URL loaded from the configuration.

waitForOverlaysToDisappear(By overlay): Waits until all elements matching the given locator become invisible (e.g., loading spinners or overlays).

waitForElementToBeVisible(By element): Waits until the specified element is visible on the page.

scrollIntoView(By element): Scrolls the specified element into the viewport using JavaScript.

💡 Purpose:
Encapsulates common Selenium actions to promote DRY (Don't Repeat Yourself) principles and improve test reliability and stability by using explicit waits.

🧪 BaseTest.java
The BaseTest class is a core component of the test framework and serves as a base class for all TestNG test classes. It handles WebDriver initialization, thread safety for parallel test execution, cookie injection, and automatic screenshot capture on test failures.

✅ Features:
Thread-Safe Driver Management:

Uses ThreadLocal<WebDriver> to ensure isolated browser instances per test thread, supporting parallel test execution.

Driver Lifecycle Hooks:

@BeforeMethod: Initializes WebDriver before each test, with support for browser parameterization (defaults to Chrome).

@AfterMethod: Quits the WebDriver after test execution and captures a screenshot if the test fails.

Screenshot Capture on Failure:

Supports two methods:
Standard WebDriver screenshot via TakesScreenshot.
Full-page screenshot using the AShot library with WebDriver coordinate provider.

Cookie Injection Utility:
injectCookiesToBrowser(Cookies cookies): Converts RestAssured cookies to Selenium cookies and injects them into the browser session.

🛠️ Libraries Used:
Selenium WebDriver – for browser automation.
AShot – for advanced screenshot capabilities.
Apache Commons IO – for file operations.
RestAssured – for API testing and cookie handling.
TestNG – for test execution and lifecycle hooks.

 constants/ Folder
The constants folder contains environment-specific and endpoint-related configuration classes. These classes help centralize and manage URLs, environment flags, and API endpoints to make the test framework more maintainable and scalable.

✅ Purpose:
Environment Management:
Contains classes that define environment-related constants (e.g., DEV, QA, STAGE, PROD).
Helps in switching environments easily without hardcoding URLs in tests or page objects.
API Endpoint Definitions:
Stores constants for frequently used API endpoints.
Promotes reusability and avoids duplication or typos across tests.

🧩 Typical Classes:
EnvType.java – Enum or class defining supported environments.
Endpoints.java – Central location for API endpoint paths used in testing.
DriverType.java – General-purpose constants (e.g., timeouts, default usernames, etc.).

💡 Benefits:
Easy to maintain and update when endpoints or environments change.
Improves readability and reduces the risk of inconsistencies across the framework.

📦 dataprovider/ Folder
The dataprovider folder contains classes that supply test data to your test methods using TestNG’s @DataProvider annotation.
This approach supports data-driven testing, allowing the same test logic to be executed with different sets of input data.

📄 pages/ Folder
The pages folder contains all Page Object Model (POM) classes, each representing a specific web page or component in the application under test. 
These classes encapsulate the elements, actions, and validations related to individual pages, helping to keep test logic clean and maintainable.

pom/ Folder
The pom/ folder is the core of the Page Object Model framework, structured to support both UI and API automation. 
It is organized into three key subfolders: apiactions, factory, and tests.


