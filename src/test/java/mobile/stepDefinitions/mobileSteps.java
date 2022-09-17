package mobile.stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import utilities.ThreadLocalDriver;

import java.text.ParseException;

import org.testng.SkipException;

public class mobileSteps extends stepDefBase {

	@BeforeStep
	public void BeforeStep(Scenario scenario) {
		setupScreens(ThreadLocalDriver.getWebTLDriver(), scenario);
		}
	
	@After
	public void AfterScenario(Scenario scenario) {
		takeFailScreenShot(scenario, "mobile");
		assertPool.assertAll(scenario.getName() + " senaryosundaki bulgular : ");
	}

  //==============================================	Feature Methods	 ============================================

	
	@Given("mobile - Login Ekranında {string} ile sisteme giriş yapılır.")
	public void login_ekranında_sisteme_giriş_yapılır(String user_role) {
		// ********** PAGE METHODS ***************
		//AnaSayfa
		//.gotoGirisYap();
		
		GirisYap
				.Giris_Basarili(user_role);
	}
	
	   
}