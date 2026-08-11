package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	
	private static final Logger logger = LogManager.getLogger(BaseClass.class);
	
	public static WebDriver getDriver() {
	    return driver.get();
	}

	public static WebDriver setUp(String browser) {
		String url = ConfigReader.getURL();
		logger.info("Launching browser: {}", browser);

		try {
			switch (browser.toLowerCase()) {

			case "chrome":
				WebDriverManager.chromedriver().setup();
				driver.set(new ChromeDriver());
				logger.info("Chrome browser launched successfully.");
				break;

			case "edge":
				WebDriverManager.edgedriver().setup();
				driver.set(new EdgeDriver());
				logger.info("Edge browser launched successfully.");
				break;

			default:
				logger.error("Invalid browser specified: {}", browser);
			    throw new RuntimeException("Invalid browser: " + browser);
			}

			driver.get().manage().window().maximize();
			logger.info("Browser window maximized.");
			driver.get().get(url);
			logger.info("Navigated to URL: {}", url);
			
		} catch (Exception e) {
			logger.error("Failed during browser setup.", e);
			throw e;
		}
		return driver.get();

	}

	public static void tearDown() {
		if (driver.get() != null) {
			logger.info("Closing browser.");
			driver.get().quit();
			driver.remove();
		}
	}

}
