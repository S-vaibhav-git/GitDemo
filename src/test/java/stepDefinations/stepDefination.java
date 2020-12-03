package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import pojo.AddPlace;
//import pojo_serialization.AddPlace;
//import pojo_serialization.Location;
import pojo.Location;

public class StepDefination extends Utils {

	RequestSpecification res;
	ResponseSpecification resSpec;
	Response response;
	static String place_id;          //when scenarios change this variable wont get null


	TestDataBuild data= new TestDataBuild();

	@Given("^Add Place Payload with \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void add_Place_Payload_with(String name, String language, String address) throws Throwable 
	{

		res = given().spec(requestSpecification())		 						// res is uesd collect all requisites from req and store body part info
				.body(data.addPlacePayload(name,language,address));	
	}

	@When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request$")
	public void user_calls_with_http_request(String resource, String method)
	{
		// Write code here that turns the phrase above into concrete actions

		APIResources resourceAPI= APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());



		if(method.equalsIgnoreCase("POST"))
			response=res.when().post(resourceAPI.getResource());				//	passing response object here
		else if(method.equalsIgnoreCase("GET"))  
			response=res.when().get(resourceAPI.getResource());

	}

	@Then("^the API call got success with status code (\\d+)$")
	public void the_API_call_got_success_with_status_code(int arg1) {
		// Write code here that turns the phrase above into concrete actions

		assertEquals(response.statusCode(),200);

	}

	@And("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
	public void in_response_body_is(String keyValue, String expectedValue) 
	{
		// Write code here that turns the phrase above into concrete actions

		assertEquals(getJsonPath(response,keyValue) , expectedValue );

		//place_id = js.getString("place_id");
	}

	@And("^verify place_id created maps to \"([^\"]*)\" using \"([^\"]*)\"$")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException 
	{
		//prepare req spec
		place_id=getJsonPath(response,"place_id");
		res=given().spec(requestSpecification()).queryParam("place_id", place_id);

		user_calls_with_http_request(resource, "GET");

		String actualName = getJsonPath(response, "name");  
		assertEquals(actualName, expectedName);

	}


	@Given("^DeletePlace Payload$")
	public void deleteplace_Payload() throws IOException {
		// Write code here that turns the phrase above into concrete actions

		res=given().spec(requestSpecification())
				.body(data.deletePlacePayload(place_id));	
				
	}
}
