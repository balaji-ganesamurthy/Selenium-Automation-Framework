package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.WaitUtils;

public class HomePage {

	private WaitUtils waitUtils;

	public HomePage(WebDriver driver) {

		waitUtils = new WaitUtils(driver);
	}

	private By Title = By.xpath("//span[normalize-space(text())='Products']"); 
	private By shoppingCartLink = By.className("shopping_cart_link");

	public boolean isTitleDisplayed() {
		return waitUtils.waitForElementVisible(Title).isDisplayed();
	}

	public boolean isShoppingCartDisplayed() {
		return waitUtils.waitForElementVisible(shoppingCartLink).isDisplayed();
	}

}
