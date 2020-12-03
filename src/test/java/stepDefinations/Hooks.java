package stepDefinations;

//Hooks used for preconditions and postconditions of cucumer scenarios
//import org.testng.annotations.BeforeMethod;

import cucumber.api.java.Before;

//This class used to prioritize the scenario, using tag names which scenario to implement first defined in this class

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws Throwable 
	{ 	
		//Execute this code only when place_id is null
		//write a code that will give you place_id

		StepDefination m=new StepDefination();

		if(StepDefination.place_id == null) {				//place_id is static variable so directly called with the clss name

			m.add_Place_Payload_with("Ramesh", "English", "Asia");
			m.user_calls_with_http_request("AddPlaceAPI", "POST");
			m.verify_place_id_created_maps_to_using("Ramesh", "GetPlaceAPI");
		}
	}

} 
