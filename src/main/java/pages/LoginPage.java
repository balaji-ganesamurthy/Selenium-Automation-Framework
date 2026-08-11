package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.ConfigReader;
import utils.WaitUtils;

public class LoginPage {

	private WebDriver driver;
	private WaitUtils waitUtils;

	private static final Logger logger = LogManager.getLogger(LoginPage.class);

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		waitUtils = new WaitUtils(driver);
	}

	private By usernameTextBox = By.id("user-name");
	private By passwordTextBox = By.id("password");
	private By loginButton = By.id("login-button");

	public void launchApplication() {
		driver.get(ConfigReader.getURL());
	}

	public void enterUserName(String username) {
		waitUtils.waitForElementVisible(usernameTextBox).sendKeys(username);
		logger.info("Username entered successfully");
	}

	public void enterPassword(String password) {
		waitUtils.waitForElementVisible(passwordTextBox).sendKeys(password);
		logger.info("Password entered successfully");
	}

	public void clickLogin() {
	    waitUtils.waitForElementVisible(loginButton).click();
	    logger.info("Login button clicked.");
	}

}
