package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.WaitUtils;

public class BasePage {

    protected WebDriver driver;
    protected WaitUtils waitUtils;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        waitUtils = new WaitUtils(driver);
    }

    protected void click(By locator) {
        waitUtils.waitForElementClickable(locator).click();
    }

    protected void type(By locator, String text) {
        waitUtils.waitForElementVisible(locator).sendKeys(text);
    }
    
    protected boolean isDisplayed(By locator) {
        return waitUtils.waitForElementVisible(locator).isDisplayed();
    }

    protected String getValue(By locator) {
        return waitUtils.waitForElementVisible(locator).getAttribute("value");
    }
}