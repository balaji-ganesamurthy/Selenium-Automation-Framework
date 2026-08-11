package stepDefinitions;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import base.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.LoginPage;
import utils.PageObjectManager;
import io.cucumber.datatable.DataTable;

public class LoginSteps {
	
	private PageObjectManager pageObjectManager;
	private LoginPage loginPage;
	private HomePage homePage;

	@Given("User launches the application")
	public void userLaunchesApplication() {
	    pageObjectManager = new PageObjectManager(BaseClass.getDriver());
	    loginPage = pageObjectManager.getLoginPage();
	    loginPage.launchApplication();
	}

	@When("User enters login credentials")
	public void userEntersLoginCredentials(DataTable dataTable) {

		List<Map<String, String>> credentials = dataTable.asMaps();

		String username = credentials.get(0).get("username");
		String password = credentials.get(0).get("password");

		loginPage.enterUserName(username);
		loginPage.enterPassword(password);
	}

	@When("User enters username {string}")
	public void userEntersUserName(String username) {
		loginPage.enterUserName(username);
	}

	@When("User enters password {string}")
	public void userEntersPassword(String password) {
		loginPage.enterPassword(password);
	}
	
	@When("User clicks Login")
	public void userClicksLoginButton() {
	    loginPage.clickLogin();
	    homePage = pageObjectManager.getHomePage();
	}

	@Then("Home page should be displayed")
	public void homePageShouldBeDisplayed() {
		Assert.assertTrue(homePage.isTitleDisplayed());
	}
}
