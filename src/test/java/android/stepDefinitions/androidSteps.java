package android.stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import utilities.ThreadLocalDriver;

public class androidSteps extends stepDefBase {

	@BeforeStep
	public void BeforeStep(Scenario scenario) {
		setupScreens(ThreadLocalDriver.getAndroidTLDriver(), scenario);
		}
	
	@Before
	public void BeforeScenario() {
		setupScenario(ThreadLocalDriver.getAndroidTLDriver());
	}

	@After
	public void AfterScenario(Scenario scenario) {
		takeFailScreenShot(scenario, "android");
		releaseDevice(DeviceInfo, scenario);
		assertPool.assertAll(scenario.getName() + " senaryosundaki bulgular : ");
	}

    //==============================================	Feature Methods	   ============================================
   
	@Given("android - İlk Giriş sayfasında Bilyoner logosunun geldiği kontrol edilir.")
    public void i̇lk_giriş_sayfasında_bilyoner_logosunun_geldiği_kontrol_edilir() {
        // ********** PAGE METHODS ***************

    	try {
			IlkGiris
//				.uiAutomatorDurdurulduHatasi()
//				.Root_Ekrani_Tamam_Tikla()
                .Logo_Kontrol();
			
    	}
    	catch(Exception ex)
    	{

    	}
    }
    
    @Given("android - Uygulama cihaz üzerinden kaldırılır ve tekrar yüklenir")
    public void yeni_surum_yukle() {
        // ********** PAGE METHODS ***************

			IlkGiris
				.installNewVersion();
			
		
                //.Root_Ekrani_Tamam_Tikla()
//                .Logo_Kontrol();
    }

    @Given("android - Login Ekranında {string} ile sisteme giriş yapılır.")
    public void login_ekraninda_ile_sisteme_giris_yapilir(String string) {
        // ********** PAGE METHODS ***************
      IlkGiris
      	.reklamKapat()
      	.anaSayfada_GirisYap();
//      .Klavye_Gizle();
    	
        GirisYap
                .Giris_Basarili(string)
                .Parmak_Izi_Kullanmak_Istemiyorum_Tikla();
    }
}


