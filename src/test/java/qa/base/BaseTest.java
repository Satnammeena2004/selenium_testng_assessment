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


public class BaseTest {


    private static ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("chrome") String browser) {

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

        driver.get(ConfigReader.get("base.url"));

        driverHolder.set(driver);

        System.out.println("Browser opened: " + browserToUse);
    }

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