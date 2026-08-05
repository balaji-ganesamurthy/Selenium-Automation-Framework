package tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import base.BaseClass;
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelUtils;
import utils.ExtentReportUtils;
import utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LaunchTest {

	private static final Logger logger = LogManager.getLogger(LaunchTest.class);

	BaseClass base = new BaseClass();

	@BeforeMethod(alwaysRun = true)
	@Parameters("browser")
	public void browserSetUp(String browser) {
		base.setUp(browser);

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
		LoginPage loginPage = new LoginPage(BaseClass.getDriver());
		loginPage.enterUserName(username);
		loginPage.enterPassword(password);
		HomePage homePage = loginPage.clickLogin();
		Assert.assertTrue(homePage.isTitleDisplayed());
		logger.info("User logged in successfully.");
	}

	@AfterMethod(alwaysRun = true)
	public void closeBrowser(ITestResult result) {

		ExtentReports extent = ExtentReportUtils.getReport();
		Object[] params = result.getParameters();
		String testName = result.getName();
		if (params != null && params.length > 0) {
			testName += "(" + params[0] + ")";
		}
		ExtentTest test = extent.createTest(testName);
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println(result.getThrowable());
			result.getThrowable().printStackTrace();
			ScreenshotUtils screenshotUtils = new ScreenshotUtils(BaseClass.getDriver());
			String screenshotPath = screenshotUtils.takeScreenshot(result.getName());
			test.fail("Test Failed");
			logger.error("Test '{}' failed.", testName);
			test.addScreenCaptureFromPath(screenshotPath);
			logger.info("Failure screenshot captured: {}", screenshotPath);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.info("Test '{}' Passed.", testName);
			test.pass("Test Passed");
		}

		base.tearDown();

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
