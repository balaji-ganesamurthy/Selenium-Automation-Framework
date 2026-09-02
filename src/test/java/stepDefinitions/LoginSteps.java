package stepDefinitions;

import org.testng.Assert;

import base.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import pages.CheckoutCompletePage;
import pages.CheckoutOverviewPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;


public class LoginSteps {

    private LoginPage loginPage;
    private HomePage homePage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private CheckoutOverviewPage overviewPage;
    private CheckoutCompletePage completePage;

    @Given("User launches the application")
    public void userLaunchesApplication() {

        loginPage = new LoginPage(BaseClass.getDriver());
        
    }

    @When("User logs in with username {string} and password {string}")
    public void userLogsIn(String username, String password) {

        loginPage.enterUserName(username);
        loginPage.enterPassword(password);

        homePage = loginPage.clickLogin();
    }
    
    @When("User adds {string} to the cart")
    public void userAddsProductToCart(String product) {

        homePage.addProductToCart(product);
    }
    
    @When("User proceeds to checkout")
    public void userProceedsToCheckout() {

        cartPage = homePage.clickShoppingCart();

        checkoutPage = cartPage.clickCheckout();
    }
    
    @When("User enters first name {string}, last name {string} and zip code {string}")
    public void userEntersCheckoutInformation(String firstName, String lastName, String zipCode) {

        checkoutPage.enterFirstName(firstName);
        checkoutPage.enterLastName(lastName);
        checkoutPage.enterZipCode(zipCode);
    }
    
    @When("User continues to checkout overview")
    public void userContinuesToCheckoutOverview() {

        overviewPage = checkoutPage.clickContinue();
    }
    
    @Then("Checkout overview should display {string}")
    public void checkoutOverviewShouldDisplayProduct(String product) {

        Assert.assertTrue(
            overviewPage.isCheckoutOverviewPageDisplayed(),
            "Checkout Overview page was not displayed"
        );

        Assert.assertTrue(
            overviewPage.isProductDisplayed(product),
            "Product was not displayed in Checkout Overview: " + product
        );
    }
    
    @When("User finishes the order")
    public void userFinishesTheOrder() {

        completePage = overviewPage.clickFinish();
    }
    
    @Then("Checkout complete page should be displayed")
    public void checkoutCompletePageShouldBeDisplayed() {

        Assert.assertTrue(
            completePage.isCheckoutCompletePageDisplayed(),
            "Checkout Complete page was not displayed"
        );
    }
    
    @Then("Thank you message should be displayed")
    public void thankYouMessageShouldBeDisplayed() {

        Assert.assertTrue(
            completePage.isThankYouMessageDisplayed(),
            "Thank you message was not displayed"
        );
    }
    
    @When("User goes back home")
    public void userGoesBackHome() {

        homePage = completePage.clickBackHome();
    }
    
    @When("User logs out")
    public void userLogsOut() {

        homePage.clickHamburgerMenu();
        loginPage = homePage.clickLogout();
    }
    
    @Then("Login page should be displayed")
    public void loginPageShouldBeDisplayed() {

        Assert.assertTrue(
            loginPage.isLoginPageDisplayed(),
            "Login page was not displayed after logout"
        );
    }
}