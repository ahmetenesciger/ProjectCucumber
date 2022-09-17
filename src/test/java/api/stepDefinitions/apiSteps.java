package api.stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import test.ServiceBase;
import utilities.Configuration;

public class apiSteps extends ServiceBase {
	Scenario scenario;
	String baseURLTribun;
	String baseURLCore;
	String baseURLCore1;
	String baseURLContent;
	String baseURLCore11;
	String baseURLContent1;
	
	@Before
	public void BeforeScenario(Scenario scenario) {
		this.scenario = scenario;
		configurationGet = Configuration.getInstance();
		baseURLTribun = "http://tribun-mobile-bff-ng-test.apps.bilyoner.local";
		baseURLCore = "http://mobile-aggregator-v3-ng-test.apps.bilyoner.local";
	
		baseURLContent1 = "http://perform-live-score-service-ng-test.apps.bilyoner.local/";
							
		baseURLCore1 = "http://users-settings-api-ng-test.apps.bilyoner.local";
		baseURLCore11 = "http://authentication-api-ng-test.apps.bilyoner.local";
	}
	
	
//	//token create
//	String endPoint = "/mobile/tribun/create/token";
//	String result;
//	result = get(baseURLTribun, endPoint, paramHeaders("Create Token"));	
//	setHeader(scenario.getName(), "token", getValueOfJSONResult(result, "token"));
//	get(baseURLTribun, endPoint, paramHeaders(scenario.getName()));
	
	/////// CORE
	@Given("clearAllCaches1")
	public void clear_all_caches1() {
		//token create
		String endPoint = "TTTTTTTTTTTTTTTTTTT";
		String result;
		result = get(baseURLCore, endPoint, paramHeaders("Create Token"));	
		
		endPoint = "/cache/clear-cache";
//		setBody(scenario.getName(), "authorComments", "true", 0);
		setHeader(scenario.getName(), "token", getValueOfJSONResult(result, "token"));
		post(baseURLContent, endPoint, paramHeaders(scenario.getName()));
	}	 
}
