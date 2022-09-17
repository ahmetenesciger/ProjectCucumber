package ios.stepDefinitions;

import io.cucumber.java.Scenario;
import ios.pages.*;
import utilities.BaseSteps;
import utilities.SoftAssert;

public class stepDefBase extends BaseSteps {

	
	  protected pageIlkGiris IlkGiris;              
	  protected pageGirisYap GirisYap;      
	  final protected SoftAssert assertPool = new SoftAssert();
	  protected int stepNumber;
	  
		String[] DeviceInfo;
	  
	protected void setupScenario(Object[] tlDriver) {
    	DeviceInfo = (String[])tlDriver[1];
	}
	  
    protected void setupScreens(Object[] tlDriver, Scenario scenario) {
    	stepNumber++;
    	Object[] testProperties = new Object[4];
    	testProperties[0] = tlDriver[0];
    	testProperties[1] = scenario;
		testProperties[2] = stepNumber;
    	testProperties[3] = assertPool;   
    	
        IlkGiris 		 = new pageIlkGiris(testProperties);
        GirisYap         = new pageGirisYap(testProperties);
		}
}