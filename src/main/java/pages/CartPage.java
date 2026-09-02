package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
	
	public CartPage(WebDriver driver) {
		super(driver);
	}


    private By cartTitle = By.xpath("//span[normalize-space(text())='Your Cart']");
    private By checkoutButton = By.id("checkout");

    public boolean isCartPageDisplayed() {
        return isDisplayed(cartTitle);
    }
    
    public boolean isProductDisplayed(String productName) {

        By product = By.xpath("//div[@class='inventory_item_name' and normalize-space()='" + productName + "']");

        return isDisplayed(product);
    }
    
    public CheckoutPage clickCheckout() {
        click(checkoutButton);
        return new CheckoutPage(driver);
    }
}