package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import base.BaseClass;
import utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;
import utils.ExtentReportUtils;

public class TestListener implements ITestListener {
	
	private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {

	    System.out.println("Test Started: " + result.getName());

	    ExtentReports extent = ExtentReportUtils.getReport();

	    String testName = result.getName();

	    Object[] parameters = result.getParameters();

	    if (parameters.length > 0) {
	        testName = testName + " - " + parameters[0];
	    }

	    ExtentTest test = extent.createTest(testName);

	    extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {

	    System.out.println("Test Passed: " + result.getName());

	    extentTest.get().pass("Test Passed");
	    
	    extentTest.remove();
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
	    
	    extentTest.remove();
	}

    @Override
    public void onTestSkipped(ITestResult result) {
    	extentTest.get().skip("Test Skipped");
        System.out.println("Test Skipped: " + result.getName());
        extentTest.remove();
    }
}