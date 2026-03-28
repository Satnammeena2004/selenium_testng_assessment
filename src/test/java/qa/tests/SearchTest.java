package qa.tests;

import qa.base.BaseTest;
import qa.pages.HomePage;
import qa.pages.SearchResultsPage;
import qa.utils.ConfigReader;
import qa.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class SearchTest extends BaseTest {

    private HomePage homePage;
    private SearchResultsPage searchResultsPage;

    @BeforeMethod(alwaysRun = true)
    public void setupPages() {
        homePage          = new HomePage(getDriver());
        searchResultsPage = new SearchResultsPage(getDriver());
    }

    // ============================================

    @Test(
            groups      = {"smoke", "regression" ,"search"},
            description = "Searching MacBook should return results"
    )
    public void search_ValidTerm_ShouldReturnResults() {

        String searchTerm = ConfigReader.get("search.term");

        ExtentReportManager.getTest().info("Searching for: " + searchTerm);

        homePage.searchFor(searchTerm);

        String pageTitle = searchResultsPage.getPageTitle();
        Assert.assertTrue(
                pageTitle.contains(searchTerm),
                "Page title should contain search term. Got: " + pageTitle
        );

        ExtentReportManager.getTest().info("Page title verified: " + pageTitle);

        int resultCount = searchResultsPage.getResultCount();
        Assert.assertTrue(
                resultCount > 0,
                "Search should return at least one result. Got: " + resultCount
        );

        ExtentReportManager.getTest().info("Result count: " + resultCount);

        System.out.println("Search results count: " + resultCount);
    }

    // ============================================

    @Test(
            groups      = {"regression", "smoke","search"},
            description = "Search results page heading should match search term"
    )
    public void search_PageHeading_ShouldMatchTerm() {

        String searchTerm = ConfigReader.get("search.term");

        homePage.searchFor(searchTerm);

        String heading = searchResultsPage.getResultsHeading();

        ExtentReportManager.getTest().info("Results heading: " + heading);

        Assert.assertTrue(
                heading.contains(searchTerm),
                "Heading should contain search term. Got: " + heading
        );
    }

    // ============================================

    @Test(
            groups      = {"regression", "search"},
            description = "Invalid search term should show no results message"
    )
    public void search_InvalidTerm_ShouldShowNoResults() {

        String invalidTerm = ConfigReader.get("search.invalid");

        ExtentReportManager.getTest().info("Searching invalid term: " + invalidTerm);

        homePage.searchFor(invalidTerm);

        // Assert: result count should be 0
        Assert.assertEquals(
                searchResultsPage.getResultCount(),
                0,
                "No results should appear for invalid search term"
        );

        ExtentReportManager.getTest().info("No results confirmed for invalid term");
    }
}