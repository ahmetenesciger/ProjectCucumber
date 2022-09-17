package android.pages;

import page.PageBaseAndroid;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.Scenario;
import utilities.Configuration;

import org.openqa.selenium.Keys;
import utilities.SoftAssert;

public class pageIlkGiris extends PageBaseAndroid {


	// ********* Constructor *********
	public pageIlkGiris(Object[] args) {
		super((AndroidDriver<AndroidElement>)args[0]);
		scenario = (Scenario)args[1];
		stepNumber = (int)args[2];
		assertPool = (SoftAssert)args[3];
	}

	// ********************************* Elements ***********************************
	String img_Logo 										= "#imageViewOnboardingLogo";
	String btn_RootTamam 									= "#buttonPositive";
	String btn_GirisYap										= "#buttonOnboardingLogin";
	String btn_UyeOl 										= "#buttonOnboardingRegister";
	String btn_UyeOlmadanDevamEt 							= "#linearLayoutOnboardingSkip";
	String lbl_KullaniciAdi									= "*textViewToolbarUsername";
	String menu_CikisYap									= "#buttonDrawerLogout";

	// *********Page Methods*********
	public pageIlkGiris Root_Ekrani_Tamam_Tikla() {
		if (exists(btn_RootTamam, 10) != true) {
			return this;
		} else {
			click(btn_RootTamam);
		}
		return this;
	}

	public pageIlkGiris Logo_Kontrol() {
		waitLoginLoading();
		assertFound(img_Logo);
		return this;
	}

	public pageIlkGiris tikla_GirisYap() {
		click(btn_GirisYap);
		return this;
	}

	public pageIlkGiris tikla_UyeOl() {
		click(btn_UyeOl);
		return this;
	}

	public pageIlkGiris tikla_UyeOlmadanDevamEt() {
		waitLoginLoading();
		if(exists("#imageViewOnboardingLogo", 1))
		click(btn_UyeOlmadanDevamEt);
		return this;
	}

	public pageIlkGiris kontrol_btn_UyeOl() {
		assertFound(btn_UyeOl);
		return this;
	}

	public pageIlkGiris kontrol_btn_GirisYap() {
		assertFound(btn_GirisYap);
		return this;
	}

	public pageIlkGiris kontrol_btn_UyeOlmadanDevamEt() {
		assertFound(btn_UyeOlmadanDevamEt);
		return this;
	}

	public pageIlkGiris Klavye_Gizle() {
		wait(1);
		hideKeyboard();
		return this;
	}

//    public void deleteAppAndInstall(String ipa_bundle_id) {
////		List args = new ArrayList();
//		Map<String, Object> params = new HashMap<>();
//		params.put("bundleId", "com.apple.mobilesafari");
////		params.put("arguments", args);
//		iosDriver.executeScript("mobile: terminateApp", params);
//		wait(2);
//		iosDriver.executeScript("mobile: launchApp", params);
//		iosDriver.findElement(By.id("URL")).click();
//		wait(1);
//		iosDriver.switchTo().activeElement().sendKeys(Keys.DELETE);
//		wait(1);
//		iosDriver.switchTo().activeElement().sendKeys("https://appdistribution.firebase.google.com/testerapps");
//		wait(1);
//		iosDriver.switchTo().activeElement().sendKeys(Keys.ENTER);
//		
//		click("#"+ipa_bundle_id);
//		click("#Download");
//		click("#Yükle");
//		
//		iosDriver.removeApp(ipa_bundle_id);
//			wait(5);
//		int count = 0;
//		do {
//			try {
//				iosDriver.launchApp();
//				count = 10;
//			} catch (Exception e) {
//				wait(5);
//				count++;
//			}
//			
//		} while (count<10);
//	}

	public pageIlkGiris installNewVersion() {
		try {
			wait(10);
		androidDriver.terminateApp(Configuration.getInstance().app_package());
		androidDriver.activateApp("com.android.chrome");
		click("*com.android.chrome:id/url_bar");
		writeText("*com.android.chrome:id/url_bar", "https://appdistribution.firebase.google.com/testerapps/");
		writeTextByKeyboard(Keys.ENTER.toString());
		wait(1);
		waitLoadingAppDonwloadPage();
		click("*com.android.chrome:id/menu_button");
		click("@//android.widget.TextView[@content-desc='İndirilenler']");
		while(exists("*com.android.chrome:id/more", 2)) {
			click("*com.android.chrome:id/more");
			click("@//*[@text='Sil']");
		}
		back();
		
		if(System.getProperty("appName").contains("beta"))
			click("@//*[contains(@text, 'Bilyoner Beta')] ");
		else
			click("@//*[@text='Bilyoner']");
		waitLoadingAppDonwloadPage();
		wait(1);
		assertFound("@//android.widget.Button[@text='İndir']");
		clickUnstable("@//android.widget.Button[@text='İndir']");
		
		if (exists("@//android.view.ViewGroup/android.widget.Button[@text='İndir']", 5)) {
			click("@//android.view.ViewGroup/android.widget.Button[@text='İndir']");
		}
		
		if (exists("@//*[@text='Tamam']", 5)) {
			click("@//*[@text='Tamam']");
		}
		
		
		int count = 0;
		do {
			if (readText("@//*[@resource-id='com.android.chrome:id/infobar_message']").contains(".apk")) 
				break;
			
			wait(2);
			count++;
			
		} while (count < 30);
		
		click("#com.android.chrome:id/menu_button");
		click("@//android.widget.TextView[@content-desc='İndirilenler']");
		click("*com.android.chrome:id/title");
		
		
//		clickToCoordinateOfElement("@//*[@resource-id='com.android.chrome:id/infobar_message']", 
//				(getWidth("@//*[@resource-id='com.android.chrome:id/infobar_message']")/2)-250, 
//				(getHeight("@//*[@resource-id='com.android.chrome:id/infobar_message']")/2) - 2);
		wait(3);
		
		if (androidDriver.getCapabilities().getCapability("deviceName").toString().equals("1e185b4b0406") //1e185b4b0406
				|| androidDriver.getCapabilities().getCapability("deviceName").toString().equals("89U4C18911005825") //HUAWEI_P20
				|| androidDriver.getCapabilities().getCapability("deviceName").toString().equals("LGM400QWR8NFQSSK9H")) {  //LGStylus3
			click("@//*[@text='YÜKLE']");
			assertFound("@//*[@text='AÇ']", 0 ,120);
			click("@//*[@text='AÇ']");
		} else {
			click("@//*[@text='Yükle']");
			assertFound("@//*[@text='Uygulama yüklendi.']", 0 ,120);
			click("@//*[@text='Aç']");

		}
		
		System.out.println("Yukleme islemi basarili");
		}catch(Exception ex)
		{
			System.err.println("!!!!!!!!!!!!!!!!!!********  YENI SURUM KURULAMADI  ********!!!!!!!!!!!!!!!!!!");
//			System.exit(1);
		}
		return this;
		}

	public pageIlkGiris waitStartingLogo() {
		waitStartLoading();
		return this;
	}

	public pageIlkGiris uiAutomatorDurdurulduHatasi() {	
		if(exists("@//*[@text='UIAutomator durduruldu.']", 30))
			click("@//*[@text='Tamam']");		
		return this;
	}

	public pageIlkGiris reklamKapat() {	
		if (exists("*ib_close", 1)) {
			click("*ib_close", 1);
		}
		return this;
	}
		

	
	public pageIlkGiris anaSayfada_GirisYap() {	
		assertFound("*viewPagerBanner");
		if(exists("*textViewToolbarWelcome", 1)) {
			click(lbl_KullaniciAdi);
			int i = 0;
			while(!exists(menu_CikisYap, 1) && i < 5){
				scroll(Direction.UP);
				i++;
			}
			click(menu_CikisYap);
			wait(1);
			click("*buttonToolbarLogin");
			return this;
		}
		else if(exists("*buttonToolbarLogin", 1)) {
			click("*buttonToolbarLogin");
			return this;
		}
		click("*buttonOnboardingLogin");
		return this;
	}
}