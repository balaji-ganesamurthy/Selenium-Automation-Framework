package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private By title =
            By.xpath("//span[normalize-space(text())='Products']");

    private By shoppingCartLink =
            By.className("shopping_cart_link");

    private By hamburgerMenu =
            By.id("react-burger-menu-btn");

    private By logoutLink =
            By.id("logout_sidebar_link");

    public boolean isTitleDisplayed() {
    	return isDisplayed(title);
    }

    public boolean isShoppingCartDisplayed() {
    	 return isDisplayed(shoppingCartLink);
    }

    public void addProductToCart(String productName) {

        String productId =
                productName.toLowerCase().replace(" ", "-");

        By addToCartButton =
                By.id("add-to-cart-" + productId);

        click(addToCartButton);
    }

    public CartPage clickShoppingCart() {
    	 click(shoppingCartLink);
        return new CartPage(driver);
    }

    public void clickHamburgerMenu() {
    	click(hamburgerMenu);
    }

    public LoginPage clickLogout() {
    	click(logoutLink);
        return new LoginPage(driver);
    }
}