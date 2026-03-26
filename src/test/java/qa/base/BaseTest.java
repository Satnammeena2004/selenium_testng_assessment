package qa.base;

import qa.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

/*
 * BaseTest is the PARENT of all test classes.
 *
 * It handles:
 *   - Opening the browser before each test
 *   - Closing the browser after each test
 *   - Providing getDriver() to all child classes
 *
 * Every test class does: extends BaseTest
 * Then uses: getDriver() to interact with browser
 */
public class BaseTest {

    /*
     * ThreadLocal — each test thread gets its OWN driver.
     * Important when tests run in parallel.
     * Without this, two tests would share one browser = chaos.
     */
    private static ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("chrome") String browser) {

        // System property overrides testng.xml parameter
        // This lets Jenkins pass -Dbrowser=firefox
        String browserToUse = System.getProperty("browser", browser);

        System.out.println("Browser: " + browserToUse);

        WebDriver driver;

        switch (browserToUse.toLowerCase()) {

            case "firefox":
                FirefoxOptions ffOptions = new FirefoxOptions();
                driver = new FirefoxDriver(ffOptions);
                break;

            case "edge":
                driver = new EdgeDriver();
                break;

            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                // Uncomment below line to run headless in Jenkins
                // chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(
                        Long.parseLong(ConfigReader.get("implicit.wait"))
                )
        );

        // Navigate to base URL
        driver.get(ConfigReader.get("base.url"));

        // Store driver for this thread
        driverHolder.set(driver);

        System.out.println("Browser opened: " + browserToUse);
    }

    // All test classes and utils call this to get the driver
    public static WebDriver getDriver() {
        return driverHolder.get();
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        if (getDriver() != null) {
            getDriver().quit();
            driverHolder.remove(); // prevent memory leak
        }
    }
}