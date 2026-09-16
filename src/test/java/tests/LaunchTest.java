package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import base.BaseClass;
import pages.CartPage;
import pages.CheckoutCompletePage;
import pages.CheckoutOverviewPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;
import utils.ExtentReportUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Optional;
import org.testng.annotations.Listeners;
import listeners.TestListener;
import utils.TestDataUtils;

@Listeners(TestListener.class)
public class LaunchTest {

	private static final Logger logger = LogManager.getLogger(LaunchTest.class);
	private HomePage homePage;
	private CartPage cartPage;
	private CheckoutPage checkoutPage;
	private CheckoutOverviewPage overviewPage;
	private CheckoutCompletePage completePage;

	@BeforeMethod(alwaysRun = true)
	@Parameters("browser")
	public void browserSetUp(@Optional("chrome") String browser) {
		BaseClass.setUp(browser);
		}

	@DataProvider(name = "loginData")
	public Object[][] getLoginData() {
		return TestDataUtils.getLoginData();
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "loginData")
	public void loginWithValidCredentials(String username, String password, String productName, String firstName,
			String lastName, String zipCode) {
		System.out.println("----------------------------------");
		System.out.println("Thread : " + Thread.currentThread().getName());
		System.out.println("Browser: " + BaseClass.getDriver());
		logger.info("Starting Login Test");
		LoginPage loginPage = new LoginPage(BaseClass.getDriver());
		loginPage.enterUserName(username);
		loginPage.enterPassword(password);
		homePage = loginPage.clickLogin();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(homePage.isTitleDisplayed());
		logger.info("User logged in successfully.");
		softAssert.assertTrue(homePage.isShoppingCartDisplayed());
		homePage.addProductToCart(productName);
		cartPage = homePage.clickShoppingCart();
		softAssert.assertTrue(cartPage.isCartPageDisplayed());
		softAssert.assertTrue(cartPage.isProductDisplayed(productName));
		checkoutPage = cartPage.clickCheckout();
		softAssert.assertTrue(checkoutPage.isCheckoutPageDisplayed());
		checkoutPage.enterFirstName(firstName);
		checkoutPage.enterLastName(lastName);
		checkoutPage.enterZipCode(zipCode);
		softAssert.assertEquals(checkoutPage.getFirstName(), firstName, "First Name was not entered correctly");
		softAssert.assertEquals(checkoutPage.getLastName(), lastName, "Last Name was not entered correctly");
		softAssert.assertEquals(checkoutPage.getZipCode(), zipCode, "ZIP Code was not entered correctly");
		overviewPage = checkoutPage.clickContinue();
		softAssert.assertTrue(overviewPage.isCheckoutOverviewPageDisplayed());
		softAssert.assertTrue(overviewPage.isProductDisplayed(productName));
		completePage = overviewPage.clickFinish();
		softAssert.assertTrue(completePage.isCheckoutCompletePageDisplayed());
		softAssert.assertTrue(completePage.isThankYouMessageDisplayed());
		homePage = completePage.clickBackHome();
		softAssert.assertTrue(homePage.isTitleDisplayed());
		homePage.clickHamburgerMenu();
		loginPage = homePage.clickLogout();
		softAssert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page was not displayed after logout");
		softAssert.assertAll();
		}

	@AfterMethod(alwaysRun = true)
	public void closeBrowser() {
		BaseClass.tearDown();
	}

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		ExtentReportUtils.createReport();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		ExtentReportUtils.flushReport();
	}
}
