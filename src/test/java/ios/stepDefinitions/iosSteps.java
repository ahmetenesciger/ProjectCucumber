package ios.stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import utilities.ThreadLocalDriver;

public class iosSteps extends stepDefBase {

	@BeforeStep
	public void BeforeStep(Scenario scenario) {
		setupScreens(ThreadLocalDriver.getiOSTLDriver(), scenario);
		}
	
  @Before
    public void BeforeScenario() {
		setupScenario(ThreadLocalDriver.getiOSTLDriver());
    }

    @After
    public void AfterScenario(Scenario scenario) {
		takeFailScreenShot(scenario, "ios");
		releaseDevice(DeviceInfo, scenario);
		assertPool.assertAll(scenario.getName() + " senaryosundaki bulgular : ");
    }

    //==============================================	Feature Methods	 ===========================================
 
    @Given("ios - Uygulama cihaz üzerinden kaldırılır ve tekrar yüklenir")
    public void uygulamayı_sil_ve_tekrar_yükle() {
        // ********** PAGE METHODS ************* 
    	IlkGiris
        	.deleteAppAndInstall();
//    	runiOSDriver();
 }  
    
    @Given("ios - Login Ekranında {string} ile sisteme giriş yapılır.")
    public void login_ekraninda_ile_sisteme_giris_yapilir(String string) {
        // ********** PAGE METHODS ***************
        GirisYap
      	.ilkGirisReklamKapat()
        		.ifLoggedDoLogout(string)
        		.tikla_btn_GirisYap()
                .Giris_Basarili(string);
//                .Parmak_Izi_Kullanmak_Istemiyorum_Tikla();
    }
}
