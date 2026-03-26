package qa.tests;

import qa.base.BaseTest;
import qa.pages.HomePage;
import qa.pages.ProductPage;
import qa.pages.SearchResultsPage;
import qa.utils.ConfigReader;
import qa.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class CartTest extends BaseTest {

    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductPage productPage;

    @BeforeMethod(alwaysRun = true)
    public void setupPages() {
        homePage = new HomePage(getDriver());
        searchResultsPage = new SearchResultsPage(getDriver());
        productPage = new ProductPage(getDriver());
    }

    // ============================================

    @Test(groups = {"smoke", "regression", "cart"}, description = "Add to cart should show success message")
    public void addToCart_ShouldShowSuccessMessage() {

        String searchTerm = ConfigReader.get("search.term");
        ExtentReportManager.getTest().info("Step 1: Searching for " + searchTerm);
        homePage.searchFor(searchTerm);

        ExtentReportManager.getTest().info("Step 2: Clicking first product");
        searchResultsPage.clickFirstProduct();

        String productName = productPage.getProductName();
        ExtentReportManager.getTest().info("Step 3: On product page - " + productName);

        Assert.assertTrue(productPage.getPageTitle().contains(productName), "Product page title should contain product name");

        ExtentReportManager.getTest().info("Step 4: Adding to cart");
        productPage.clickAddToCart();

        Assert.assertTrue(productPage.isSuccessMessageDisplayed(), "Success message should appear after adding to cart");

        String successMsg = productPage.getSuccessMessage();
        ExtentReportManager.getTest().info("Success message: " + successMsg);

        Assert.assertTrue(successMsg.contains("Success"), "Message should say Success. Got: " + successMsg);

        System.out.println("Product added to cart: " + productName);
    }



}