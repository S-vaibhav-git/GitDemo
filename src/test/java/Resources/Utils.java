package resources;			

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


//Utils is for reusable request and response specifications

public class Utils {							
	
    public static RequestSpecification req;   //to create only one instance of req Object through out all the execution even if it is for addPlace, deletePlace, etc
	
	public RequestSpecification requestSpecification() throws IOException
	{
		/*   without logging any info :-----
		 *    req = new RequestSpecBuilder()                      //req is used to build given contents only
				.setBaseUri("https://rahulshettyacademy.com")		  
				.addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();
			  return req; 
		*/
		//with logging all request and response info in a text file
		
		if(req==null)          		//to run this req block only once so that i will not overwrite the log file
		{
			PrintStream log=new PrintStream(new FileOutputStream("logging.txt"));      //to log everything, go to workspace path to check logging.txt file 
			
			req = new RequestSpecBuilder()                      //req is used to build given contents only
					.setBaseUri(getGlobalValue("baseURI"))		  
					.addQueryParam("key", "qaclick123")
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).build();
			return req;
		}	
		return req;
	}
	
	public static String getGlobalValue(String key) throws IOException {
		
		Properties prop=new Properties();
		FileInputStream fis=new FileInputStream("C:\\Users\\vaibhav.suradkar\\eclipse-workspace\\APIFramework\\src\\test\\java\\Resources\\global.properties");
		
		prop.load(fis);
		return (prop.getProperty(key));
		
		
	}
	
	public static String getJsonPath(Response response, String key)
	{
		String resp= response.asString();
		JsonPath js=new JsonPath(resp);
		
		return js.get(key).toString();
		
	}

}
