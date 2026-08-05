package utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

	private WebDriverWait wait;

	public WaitUtils(WebDriver driver) {

		wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getTimeOut()));
	}

	public WebElement waitForElementVisible(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
}
