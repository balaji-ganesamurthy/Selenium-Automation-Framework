package utils;

import org.openqa.selenium.WebDriver;

import pages.HomePage;
import pages.LoginPage;

public class PageObjectManager {

    private WebDriver driver;

    private LoginPage loginPage;
    private HomePage homePage;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }
    
    public LoginPage getLoginPage() {

        if (loginPage == null) {
            loginPage = new LoginPage(driver);
        }

        return loginPage;
    }
    
    public HomePage getHomePage() {

        if (homePage == null) {
            homePage = new HomePage(driver);
        }

        return homePage;
    }
}