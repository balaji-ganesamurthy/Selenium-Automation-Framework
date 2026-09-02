package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import java.util.HashMap;
import java.util.Map;


public class BaseClass {

	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	
	private static final Logger logger = LogManager.getLogger(BaseClass.class);
	
	public static WebDriver getDriver() {
	    return driver.get();
	}

	public static WebDriver setUp(String browser) {
		String url = ConfigReader.getURL();
		logger.info("Launching browser: {}", browser);

		try {
			
			boolean headless = ConfigReader.isHeadless();
			
			switch (browser.toLowerCase()) {

			case "chrome":
			    WebDriverManager.chromedriver().setup();

			    ChromeOptions chromeOptions = new ChromeOptions();
			    
			    Map<String, Object> chromePrefs = new HashMap<>();
			    
			    chromePrefs.put("credentials_enable_service", false);
			    chromePrefs.put("profile.password_manager_enabled", false);
			    chromePrefs.put("profile.password_manager_leak_detection", false);

			    chromeOptions.setExperimentalOption("prefs", chromePrefs);
			    
			    if (headless) {
			        chromeOptions.addArguments("--headless");
			    }

			    driver.set(new ChromeDriver(chromeOptions));
			    logger.info("Chrome browser launched successfully.");
			    break;

			case "edge":
			    WebDriverManager.edgedriver().setup();

			    EdgeOptions edgeOptions = new EdgeOptions();

			    if (headless) {
			        edgeOptions.addArguments("--headless");
			    }

			    driver.set(new EdgeDriver(edgeOptions));
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
