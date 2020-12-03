Feature: Validating Place API's

#Scenario: Verify if Place is being Successfully added using AddPlaceAPI       in this : Scenario keyword used to describe scenario but it has no knowledge that there is extra data to pass
#Scenario Outline : this keyword is used to mak eit known that there is an extra data in examples to send

@AddPlace @Regression
Scenario Outline: Verify if Place is being Successfully added using AddPlaceAPI

Given Add Place Payload with "<name>" "<language>" "<address>"
When user calls "AddPlaceAPI" with "POST" http request
Then the API call got success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And verify place_id created maps to "<name>" using "GetPlaceAPI"

Examples:
	|name 	    | language 		  |  address 			|
	|GermanName | GermanLanguage  | brooklyn, germany   |
	|SpanishName| SpanishLanguage | cross centre, Spain |
	
# when scenarios change the variables are also set to NULL, in delete place, place_id from last scenario is needed
# thats why make the place_id variable static, due to which it wont become null even when new scenario is started	

@DeletePlace @Regression	       #multiple tags can also be given, and any tag name can be used to trigger te scenario
Scenario: Verify if delete Place functionality is working

	Given DeletePlace Payload
	When user calls "DeletePlaceAPI" with "Post" http request
	Then the API call got success with status code 200
	And "status" in response body is "OK" 