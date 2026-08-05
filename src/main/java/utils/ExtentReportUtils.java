package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportUtils {

	private static ExtentReports extent;

	public static void createReport() {
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/AutomationReport.html");
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
	}

	public static ExtentReports getReport() {
		return extent;
	}

	public static void flushReport() {
		extent.flush();
	}

}
