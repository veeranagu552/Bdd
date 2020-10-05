package runners;


import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/Features", 
		glue =  "stepDefinitions" , 
		dryRun = false,
		tags = "@goibibo"
		)
public class Runner {

}

//import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.testng.CucumberOptions;
//import org.testng.annotations.DataProvider;
//
//@CucumberOptions(plugin = { "html:target/results.html", "message:target/results.ndjson" })
//public class RunCucumberTest extends AbstractTestNGCucumberTests {
//
//}