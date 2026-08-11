package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features", 
                 glue = { "hooks", "stepDefinitions" }, 
                 plugin = { "pretty", "html:reports/cucumber-report.html" },
                 tags = "@smoke")
public class TestRunner extends AbstractTestNGCucumberTests {

}