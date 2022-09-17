package mobile.stepDefinitions;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.Scenario;
import mobile.pages.*;
import utilities.BaseSteps;
import utilities.SoftAssert;

public class stepDefBase extends BaseSteps {
	
	protected pageGirisYap GirisYap;
	
	final protected SoftAssert assertPool = new SoftAssert();
	protected int stepNumber;

    protected void setupScreens(WebDriver driver, Scenario scenario) {
    	stepNumber++;
    	Object[] testProperties = new Object[4];
    	testProperties[0] = driver;
    	testProperties[1] = scenario;
		testProperties[2] = stepNumber;
    	testProperties[3] = assertPool; 
    	
		GirisYap 		= new pageGirisYap(testProperties);
		}
}