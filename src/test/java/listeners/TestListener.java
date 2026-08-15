package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import base.BaseClass;
import utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;
import utils.ExtentReportUtils;

public class TestListener implements ITestListener {
	
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {

	    System.out.println("Test Started: " + result.getName());

	    ExtentReports extent = ExtentReportUtils.getReport();

	    ExtentTest test = extent.createTest(result.getName());

	    extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {

	    System.out.println("Test Passed: " + result.getName());

	    extentTest.get().pass("Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {

	    System.out.println("Test Failed: " + result.getName());

	    ScreenshotUtils screenshotUtils =
	            new ScreenshotUtils(BaseClass.getDriver());

	    String screenshotPath =
	            screenshotUtils.takeScreenshot(result.getName());

	    extentTest.get().fail("Test Failed");
	    extentTest.get().addScreenCaptureFromPath(screenshotPath);

	    System.out.println("Failure screenshot captured: " + screenshotPath);
	}

    @Override
    public void onTestSkipped(ITestResult result) {
    	extentTest.get().skip("Test Skipped");
        System.out.println("Test Skipped: " + result.getName());
    }
}