package qa.tests;

import qa.base.BaseTest;
import qa.pages.HomePage;
import qa.pages.LoginPage;
import qa.utils.ConfigReader;
import qa.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void goToLoginPage() {
        HomePage homePage = new HomePage(getDriver());
        loginPage = new LoginPage(getDriver());

        homePage.clickLogin();

//       ExtentReportManager.getTest().info("Navigated to Login page");
    }


    @Test(
            groups = {"login","regression","smoke"},
            description = "Valid login should redirect to My Account page"
    )
    public void validLogin_ShouldRedirectToMyAccount() {

        loginPage.login(
                ConfigReader.get("valid.email"),
                ConfigReader.get("valid.password")
        );

        ExtentReportManager.getTest().info("Login submitted with valid credentials");

        Assert.assertTrue(
                getDriver().getCurrentUrl().contains("account"),
                "URL should contain 'account' after login"
        );

        Assert.assertTrue(
                getDriver().getTitle().contains("My Account"),
                "Page title should contain 'My Account'"
        );

        ExtentReportManager.getTest().info("Valid login verified successfully");
    }


    @Test(
            groups = {"login"},
            description = "Invalid credentials should show error message"
    )
    public void invalidLogin_ShouldShowError() {

        loginPage.login(
                ConfigReader.get("invalid.email"),
                ConfigReader.get("invalid.password")
        );

        ExtentReportManager.getTest().info("Login submitted with invalid credentials");

        Assert.assertTrue(
                loginPage.isErrorDisplayed(),
                "Error message should be displayed for invalid credentials"
        );

        Assert.assertTrue(
                loginPage.getErrorMessage().contains("No match for E-Mail Address"),
                "Error message text mismatch. Got: " + loginPage.getErrorMessage()
        );

        ExtentReportManager.getTest().info("Error message verified: "
                + loginPage.getErrorMessage());
    }


}