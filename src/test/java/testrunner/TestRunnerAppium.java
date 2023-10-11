package testrunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true,
		plugin = { "pretty", "html:target/html-report.html",
				"json:target/json-report.json" },
		features = "src/test/resources/Features",
		glue = "stepdefs",
		stepNotifications = true)

public class TestRunnerAppium {

}
