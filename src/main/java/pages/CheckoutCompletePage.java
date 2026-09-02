package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    private By completeTitle =
            By.xpath("//span[normalize-space(text())='Checkout: Complete!']");

    private By thankYouMessage =
            By.xpath("//h2[normalize-space(text())='Thank you for your order!']");

    private By backHomeButton = By.id("back-to-products");

    public boolean isCheckoutCompletePageDisplayed() {
        return isDisplayed(completeTitle);
    }

    public boolean isThankYouMessageDisplayed() {
        return isDisplayed(thankYouMessage);
    }

    public HomePage clickBackHome() {
       click(backHomeButton);
        return new HomePage(driver);
    }
}