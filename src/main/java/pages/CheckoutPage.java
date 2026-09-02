package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
       super(driver);
    }

    private By checkoutTitle = By.xpath("//span[normalize-space(text())='Checkout: Your Information']");    
    private By firstNameTextBox = By.id("first-name");
    private By lastNameTextBox = By.id("last-name");
    private By zipCodeTextBox = By.id("postal-code");
    private By continueButton = By.id("continue");
    
    public boolean isCheckoutPageDisplayed() {
    	return isDisplayed(checkoutTitle);
    }
    
    public void enterFirstName(String firstName) {
        type(firstNameTextBox, firstName);
    }

    public void enterLastName(String lastName) {
        type(lastNameTextBox, lastName);
    }

    public void enterZipCode(String zipCode) {
        type(zipCodeTextBox, zipCode);
    }
    
    public String getFirstName() {
        return getValue(firstNameTextBox);
    }

    public String getLastName() {
        return getValue(lastNameTextBox);
    }

    public String getZipCode() {
        return getValue(zipCodeTextBox);
    }
    
    public CheckoutOverviewPage clickContinue() {
    	click(continueButton);
        return new CheckoutOverviewPage(driver);
    }
}