package qa.pages;

import qa.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class HomePage {

    private final WebDriver driver;

    private final By searchBox = By.name("search");
    private final By searchButton = By.cssSelector("button.btn-default[type='button']");
    private final By myAccount = By.xpath("//span[text()='My Account']");
    private final By loginOption = By.linkText("Login");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }


    public String getPageTitle() {
        return driver.getTitle();
    }

    public void searchFor(String term) {
        WaitUtils.waitForVisible(searchBox).clear();
        driver.findElement(searchBox).sendKeys(term);
        driver.findElement(searchButton).click();
        System.out.println("Searched for: " + term);
    }

    public void clickMyAccount() {
        WaitUtils.waitForClickable(myAccount).click();
    }

    public void clickLogin() {
        clickMyAccount();
        WaitUtils.waitForClickable(loginOption).click();
    }

    public boolean isSearchBoxVisible() {
        return driver.findElement(searchBox).isDisplayed();
    }
}