package qa.pages;

import qa.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ProductPage {

    private WebDriver driver;

    private By productName = By.cssSelector("div#content h1");
    private By addToCartBtn = By.id("button-cart");
    private By cartSuccess = By.cssSelector("div.alert-success");
    private By productPrice = By.cssSelector("ul.list-unstyled li h2");
    private By cartQuantity = By.id("cart-total");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }


    public String getProductName() {
        return WaitUtils.waitForVisible(productName).getText().trim();
    }

    public String getProductPrice() {
        return WaitUtils.waitForVisible(productPrice).getText().trim();
    }

    public void clickAddToCart() {
        WaitUtils.waitForClickable(addToCartBtn).click();
        System.out.println("Clicked Add to Cart");
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            String text = WaitUtils.waitForVisible(cartSuccess).getText();
            return text.contains("Success");
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessMessage() {
        return WaitUtils.waitForVisible(cartSuccess).getText().trim();
    }

    public String getCartCount() {
        return WaitUtils.waitForVisible(cartQuantity).getText().trim();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}