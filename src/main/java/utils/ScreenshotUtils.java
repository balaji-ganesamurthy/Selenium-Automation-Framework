package utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {

	private WebDriver driver;

	public ScreenshotUtils(WebDriver driver) {

		this.driver = driver;

	}

	public String takeScreenshot(String testName) {

	    TakesScreenshot ts = (TakesScreenshot) driver;

	    File sourceFile = ts.getScreenshotAs(OutputType.FILE);

	    DateTimeFormatter formatter =
	            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

	    String timestamp = LocalDateTime.now().format(formatter);

	    String fileName = testName + "_" + timestamp + ".png";

	    String directoryPath = "src/test/resources/screenshots/";

	    File screenshotDirectory = new File(directoryPath);

	    if (!screenshotDirectory.exists()) {
	        screenshotDirectory.mkdirs();
	    }

	    File destinationFile =
	            new File(directoryPath + fileName);

	    try {
	        FileUtils.copyFile(sourceFile, destinationFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return destinationFile.getAbsolutePath();
	}

	public byte[] takeScreenshotAsBytes() {
		TakesScreenshot ts = (TakesScreenshot) driver;
		return ts.getScreenshotAs(OutputType.BYTES);
	}
}
