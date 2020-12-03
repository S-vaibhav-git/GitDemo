package cucumber.Options;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)								     			   //before running check if Junit is from eclipse or from maven dependencies,  
@CucumberOptions(features="src/test/java/features" ,						//if at both places then case will run if version is same
				 plugin="json:target/jsonReports/cucumber-report.json",   //if you need results in json format then plugin key is used, its values are json and location to store that json file
				 glue= {"stepDefinations"}, 								//for both eclipse Junit in build path and Maven Junit in POM.xml,
				 tags= {"@DeletePlace",})								// thats why remove any one Junit either from eclise or from the POM.xml			
																		//specify tag name to run only that tag scenario

public class TestRunner {								

}
