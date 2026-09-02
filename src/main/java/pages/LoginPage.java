package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

	private static final Logger logger = LogManager.getLogger(LoginPage.class);

	private By usernameTextBox = By.id("user-name");
	private By passwordTextBox = By.id("password");
	private By loginButton = By.id("login-button");

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public void enterUserName(String username) {
	    type(usernameTextBox, username);
	    logger.info("Username entered successfully");
	}

	public void enterPassword(String password) {
	    type(passwordTextBox, password);
	    logger.info("Password entered successfully");
	}

	public HomePage clickLogin() {
	    click(loginButton);
	    logger.info("Login button clicked.");
	    return new HomePage(driver);
	}
	
	public boolean isLoginPageDisplayed() {
	    return isDisplayed(loginButton);
	}

}
