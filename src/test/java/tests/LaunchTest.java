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
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelUtils;
import utils.ExtentReportUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.PageObjectManager;
import org.testng.annotations.Optional;
import org.testng.annotations.Listeners;
import listeners.TestListener;

@Listeners(TestListener.class)
public class LaunchTest {

	private static final Logger logger = LogManager.getLogger(LaunchTest.class);
	private PageObjectManager pageObjectManager;
	private HomePage homePage;

	@BeforeMethod(alwaysRun = true)
	@Parameters("browser")
	public void browserSetUp(@Optional("edge") String browser) {
		BaseClass.setUp(browser);
		pageObjectManager = new PageObjectManager(BaseClass.getDriver());

	}

	@DataProvider(name = "loginData")
	public Object[][] getLoginData() {

		int rowCount = ExcelUtils.getRowCount("LoginData");
		int executableRows = 0;

		for (int i = 1; i <= rowCount; i++) {
			String runMode = ExcelUtils.getCellData("LoginData", i, 3);
			if ("Y".equals(runMode)) {
				executableRows++;
			}
		}
		Object[][] data = new Object[executableRows][2];
		int dataIndex = 0;

		for (int i = 1; i <= rowCount; i++) {
			String runMode = ExcelUtils.getCellData("LoginData", i, 3);
			if ("Y".equals(runMode)) {
				String username = ExcelUtils.getCellData("LoginData", i, 1);
				String password = ExcelUtils.getCellData("LoginData", i, 2);
				data[dataIndex][0] = username;
				data[dataIndex][1] = password;
				dataIndex++;
			}
		}

		return data;

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "loginData")
	public void loginWithValidCredentials(String username, String password) {
		System.out.println("----------------------------------");
		System.out.println("Thread : " + Thread.currentThread().getName());
		System.out.println("Browser: " + BaseClass.getDriver());
		logger.info("Starting Login Test");
		LoginPage loginPage = pageObjectManager.getLoginPage();
		loginPage.enterUserName(username);
		loginPage.enterPassword(password);
		loginPage.clickLogin();
		homePage = pageObjectManager.getHomePage();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(homePage.isTitleDisplayed());
		softAssert.assertTrue(homePage.isShoppingCartDisplayed());
		softAssert.assertAll();
		logger.info("User logged in successfully.");
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
