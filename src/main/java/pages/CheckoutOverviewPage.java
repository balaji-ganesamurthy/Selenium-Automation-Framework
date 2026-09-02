package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOverviewPage extends BasePage {

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    private By overviewTitle =
            By.xpath("//span[normalize-space(text())='Checkout: Overview']");

    private By finishButton = By.id("finish");

    public boolean isCheckoutOverviewPageDisplayed() {
    	return isDisplayed(overviewTitle);
    }

    public boolean isProductDisplayed(String productName) {

        By product = By.xpath(
            "//div[@class='inventory_item_name' and normalize-space()='" 
            + productName + "']"
        );

        return isDisplayed(product);
    }

    public CheckoutCompletePage clickFinish() {
    	click(finishButton);
        return new CheckoutCompletePage(driver);
    }
}