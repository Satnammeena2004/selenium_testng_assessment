package qa.pages;

import qa.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultsPage {

    private WebDriver driver;

    private By pageTitle = By.cssSelector("#content h1");
    private By productCards = By.cssSelector("div.product-layout");
    private By noResultsText = By.cssSelector("#content p");
    private By productNames = By.cssSelector("div.caption h4 a");
    private By firstProduct = By.cssSelector("div.product-layout:first-child h4 a");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }


    public String getPageTitle() {
        return driver.getTitle();
    }

    public int getResultCount() {
        List<WebElement> products = driver.findElements(productCards);
        return products.size();
    }

    public boolean hasResults() {
        return getResultCount() > 0;
    }

    public String getResultsHeading() {
        return WaitUtils.waitForVisible(pageTitle).getText().trim();
    }

    public List<WebElement> getAllProductNames() {
        return driver.findElements(productNames);
    }

    public void clickFirstProduct() {
        WaitUtils.waitForClickable(firstProduct).click();
        System.out.println("Clicked first product in results");
    }

    public String getNoResultsMessage() {
        return WaitUtils.waitForVisible(noResultsText).getText().trim();
    }
}