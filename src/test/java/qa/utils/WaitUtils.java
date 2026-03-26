package qa.utils;

import qa.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/*
 * WaitUtils — one place for all wait logic.
 *
 * Why this exists:
 *   Without waits → test tries to click element before it loads → fails
 *   With Thread.sleep → wastes time, still not reliable
 *   With WaitUtils → waits exactly as long as needed, no more
 */
public class WaitUtils {

    // How long to wait before giving up (from config)
    private static int WAIT_SECONDS =
            Integer.parseInt(ConfigReader.get("explicit.wait"));

    // Create a fresh wait object using current driver
    private static WebDriverWait getWait() {
        return new WebDriverWait(
                BaseTest.getDriver(),
                Duration.ofSeconds(WAIT_SECONDS)
        );
    }

    // Wait until element is visible on screen
    public static WebElement waitForVisible(By locator) {
        return getWait().until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    // Wait until element can be clicked (visible + enabled)
    public static WebElement waitForClickable(By locator) {
        return getWait().until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    // Wait until page URL contains this text
    public static void waitForUrl(String partialUrl) {
        getWait().until(
                ExpectedConditions.urlContains(partialUrl)
        );
    }

    // Wait until element has this text
    public static void waitForText(By locator, String text) {
        getWait().until(
                ExpectedConditions.textToBePresentInElementLocated(locator, text)
        );
    }

    // Wait until element disappears (e.g. loading spinner)
    public static void waitForInvisible(By locator) {
        getWait().until(
                ExpectedConditions.invisibilityOfElementLocated(locator)
        );
    }
}