package qa.pages;

import qa.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPage {

    private WebDriver driver;

    private By emailField = By.id("input-email");
    private By passwordField = By.id("input-password");
    private By loginButton = By.cssSelector("input[value='Login']");
    private By errorAlert = By.cssSelector("div.alert-danger");
    private By myAccountText = By.cssSelector("#content h2");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    public void enterEmail(String email) {
        WaitUtils.waitForVisible(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        WaitUtils.waitForClickable(loginButton).click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
        System.out.println("Login attempted with: " + email);
    }

    public String getErrorMessage() {
        return WaitUtils.waitForVisible(errorAlert).getText().trim();
    }

    public boolean isErrorDisplayed() {
        try {
            return driver.findElement(errorAlert).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // After successful login, this heading appears
    public String getLoggedInHeading() {
        return WaitUtils.waitForVisible(myAccountText).getText().trim();
    }
}