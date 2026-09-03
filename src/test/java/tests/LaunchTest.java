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
import utils.ExcelUtils;
import utils.ExtentReportUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Optional;
import org.testng.annotations.Listeners;
import listeners.TestListener;

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

		int rowCount = ExcelUtils.getRowCount("LoginData");
		int executableRows = 0;

		for (int i = 1; i <= rowCount; i++) {

			String runMode = ExcelUtils.getCellData("LoginData", i, 7);

			if ("Y".equals(runMode)) {
				executableRows++;
			}
		}

		Object[][] data = new Object[executableRows][6];

		int dataIndex = 0;

		for (int i = 1; i <= rowCount; i++) {

			String runMode = ExcelUtils.getCellData("LoginData", i, 7);

			if ("Y".equals(runMode)) {

				String username = ExcelUtils.getCellData("LoginData", i, 1);

				String password = ExcelUtils.getCellData("LoginData", i, 2);

				String productName = ExcelUtils.getCellData("LoginData", i, 3);

				String firstName = ExcelUtils.getCellData("LoginData", i, 4);

				String lastName = ExcelUtils.getCellData("LoginData", i, 5);

				String zipCode = ExcelUtils.getCellData("LoginData", i, 6);

				data[dataIndex][0] = username;
				data[dataIndex][1] = password;
				data[dataIndex][2] = productName;
				data[dataIndex][3] = firstName;
				data[dataIndex][4] = lastName;
				data[dataIndex][5] = zipCode;

				dataIndex++;
			}
		}

		return data;
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
