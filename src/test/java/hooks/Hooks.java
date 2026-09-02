package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ScreenshotUtils;
import base.BaseClass;
import utils.ConfigReader;

public class Hooks {

	@Before
	public void setUp() {
		String browser = ConfigReader.getBrowser();
		BaseClass.setUp(browser);
	}

	@After
	public void tearDown(Scenario scenario) {

		if (scenario.isFailed()) {

			ScreenshotUtils screenshotUtils = new ScreenshotUtils(BaseClass.getDriver());

	        byte[] screenshot = screenshotUtils.takeScreenshotAsBytes();
	        
	        scenario.attach(screenshot, "image/png", "Failure Screenshot");
	        
		}

		BaseClass.tearDown();
	}
}