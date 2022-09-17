package ios.pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import utilities.SoftAssert;

import page.PageBaseIos;
import utilities.Configuration;
import utilities.MappedLogin;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.cucumber.java.Scenario;

public class pageIlkGiris extends PageBaseIos {
	
	// ********* Constructor *********
		public pageIlkGiris(Object[] args) {
			super((IOSDriver<IOSElement>)args[0]);
			scenario = (Scenario)args[1];
			stepNumber = (int)args[2];
			assertPool = (SoftAssert)args[3];
		}

		// ********************************* Elements ***********************************
		String img_Logo											= "#logoBilyonerSecond";
		String btn_uyeOl										= "#ÜYE OL";
		String btn_uyeOlmadanDevamEt							= "#Üye olmadan devam et";
		String btn_girisYap										= "@//XCUIElementTypeButton[@name='GİRİŞ YAP']";
		
		// *********Page Methods*********
		
	public pageIlkGiris Logo_Kontrol() {
		assertFound(img_Logo);
		return this;
	}

	public pageIlkGiris kontrol_btn_UyeOl() {
		assertFound(btn_uyeOl);
		return this;
	}

	public pageIlkGiris kontrol_btn_UyeOlmadanDevamEt() {
		assertFound(btn_uyeOlmadanDevamEt);
		return this;
	}

	public pageIlkGiris tikla_btn_UyeOl() {
		click(btn_uyeOl);
		return this;
	}

	public pageIlkGiris tikla_btn_UyeOlmadanDevamEt() {	
		if(exists(btn_uyeOlmadanDevamEt, 1))
			click(btn_uyeOlmadanDevamEt);
		return this;
	}

	public pageIlkGiris Klavye_Gizle(){
		wait(1);
		hideKeyboard();
		return this;
	}

	public void deleteAppAndInstall() {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("bundleId", "com.apple.mobilesafari");
			iosDriver.executeScript("mobile: terminateApp", params);
			wait(2);
			iosDriver.executeScript("mobile: launchApp", params);
			iosDriver.findElement(By.id("URL")).click();
			wait(1);
			iosDriver.switchTo().activeElement().sendKeys(Keys.DELETE);
			wait(1);
			iosDriver.switchTo().activeElement().sendKeys("https://appdistribution.firebase.google.com/testerapps");
			wait(1);
			iosDriver.switchTo().activeElement().sendKeys(Keys.ENTER);

			click("#" + Configuration.getInstance().ipa_bundle_id());
			click("#İndir");
			wait(5);
			try {
				iosDriver.switchTo().alert().accept();
				if (exists("$**/XCUIElementTypeButton[`label == 'Yükle'`]", 0)) {
					clickUnstable("$**/XCUIElementTypeButton[`label == 'Yükle'`]");
				}
			} catch (Exception ex) {
			}
			iosDriver.executeScript("mobile: terminateApp", params);
			wait(30);
			System.out.println("kurulum tamamlandı");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.err.println("!!!!!!!!!!!!!!!!!!********  yeni sürüm kurulamadi  ********!!!!!!!!!!!!!!!!!!");
//			System.exit(1);
		}
	
//		iosDriver.removeApp(ipa_bundle_id);

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
	}

	public void webUzerindeFiltreUygula(String filtre, String kategori, String device_name) {
		iosDriver.terminateApp(Configuration.getInstance().ipa_bundle_id());
		Map<String, Object> params = new HashMap<>();
		params.put("bundleId", "com.apple.mobilesafari");
		iosDriver.executeScript("mobile: terminateApp", params);
		iosDriver.executeScript("mobile: launchApp", params);

		for (int i = 0; i < 15; i++) {
		click("#URL");
		iosDriver.switchTo().activeElement().sendKeys(Keys.DELETE);
		
		//https://m.bilyoner.com/canli-iddaa/futbol
		iosDriver.switchTo().activeElement().sendKeys("https:/m.bilyoner.com/" + filtre + "/" + kategori);
		iosDriver.switchTo().activeElement().sendKeys(Keys.ENTER);
		assertFound("$**/XCUIElementTypeButton[`name == 'ReloadButton'`]", 0, 120);
		if(exists("#Canlı", 2))
				break;
		wait(5);
		}
		
		int x = 4;
		if (filtre.equals("iddaa")) {
			x = 6;
		}
		if(kategori.contains("uzun"))
			x=2;
		else
		isClickable("#Tümü");

		click("$**/XCUIElementTypeOther[`name CONTAINS '- Bilyoner'`]/XCUIElementTypeButton["+ x +"]");
		
		
//		click("#Tarih");
//		wait(1);
//		swipe(200, 300, 200, 200, 250);
//		wait(1);
//		click("$**/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSwitch[`visible == 1`][-1]");
		click("$**/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSwitch[`visible == 1`][1]");
		
		
		wait(1);		
		click("#UYGULA");
		System.out.println("web filtre uygulandı");
		click("$**/XCUIElementTypeTextField[`value == 'Takım ara'`]");
		click("$**/XCUIElementTypeTextField[`value == 'Takım ara'`]");
		wait(1);
		swipe(200, 200, 200, 300, 300);
		wait(1);
	
	
		
		
		if (device_name.contains("iPhone5S")
				|| device_name.contains("iPhone6")) {
			click("#AÇ");
		}
		else {
			clickToCoordinateOfElement("$**/XCUIElementTypeButton[`name CONTAINS[cd] \"uygulama\"`]", (getWidth("$**/XCUIElementTypeButton[`name CONTAINS[cd] \"uygulama\"`]")/2)-40
					, 0);
		}
		System.out.println("uygulama açılıyor");
		wait(5);
//		iosDriver.terminateApp("com.apple.mobilesafari");
		//bilyonerin logosu bazen takılı kalıyor. buraya onu beklemek icin süre eklenebilir
		
		for (int i = 0; i < 30; i++) {
			if (!exists("@//XCUIElementTypeButton[@name=\"icFilter\"]/following-sibling::*/child::XCUIElementTypeStaticText[@name=\"1\"]", 1)) 
				wait(1);
			else
				return;
		}
		throwFail("filtrelemedan sonra uygulama açılamadı.!");
		
//		waitClickable("#icFilter");
		
	//*[@label='c']
//		click("#URL");
//		String directLink = readText("#URL").replace("://m.", "://www.");
//		iosDriver.get(directLink);
	}
	
	public pageIlkGiris webUzerindeFiltreUygula2(String filtre, String kategori, Configuration configure) {
		iosDriver.terminateApp(configure.ipa_bundle_id());
		Map<String, Object> params = new HashMap<>();
		params.put("bundleId", "com.apple.mobilesafari");
		iosDriver.executeScript("mobile: terminateApp", params);
		iosDriver.executeScript("mobile: launchApp", params);
		click("#URL");
		iosDriver.switchTo().activeElement().sendKeys(Keys.DELETE);
		
		//https://m.bilyoner.com/canli-iddaa/futbol
		iosDriver.switchTo().activeElement().sendKeys("https://m.bilyoner.com/giris-yap");
		iosDriver.switchTo().activeElement().sendKeys(Keys.ENTER);
		wait(2);
		MappedLogin mappedLogin = new MappedLogin();
		click("$**/XCUIElementTypeTextField[`value == \"TC Kimlik veya Üye Numarası\"`]", 0);
		writeText("$**/XCUIElementTypeTextField[`value == \"TC Kimlik veya Üye Numarası\"`]", mappedLogin.get("produser")[0]);
		writeText("$**/XCUIElementTypeSecureTextField[`value == \"Şifre\"`]", mappedLogin.get("produser")[1]);
		click("$**/XCUIElementTypeButton[`label == \"GİRİŞ YAP\"`]");
		
		click("#URL");
		iosDriver.switchTo().activeElement().sendKeys(Keys.DELETE);
		//https://m.bilyoner.com/canli-iddaa/futbol
		iosDriver.switchTo().activeElement().sendKeys("https:/m.bilyoner.com/" + filtre + "/" + kategori);
		iosDriver.switchTo().activeElement().sendKeys(Keys.ENTER);	
		wait(2);
		isClickable("#Tümü");
		int x = 4;
		if (filtre.equals("iddaa")) {
			x = 6;
		}
		click("$**/XCUIElementTypeOther[`name CONTAINS '- Bilyoner'`]/XCUIElementTypeButton["+ x +"]");
		click("#Tarih");
		wait(1);
		click("$**/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSwitch[`visible == 1`][-1]");
		wait(1);		
		click("#UYGULA");
		System.out.println("web filtre uygulandı");
		click("$**/XCUIElementTypeOther[`label == \"İddaa - İddaa Bülteni & Tahminleri - Bilyoner\"`]/XCUIElementTypeButton[1]");
		wait(2);
		if (getSize("$**/XCUIElementTypeStaticText[`label == \"-\"`]")<2) {
		click("$**/XCUIElementTypeOther[`label == \"İddaa - İddaa Bülteni & Tahminleri - Bilyoner\"`]/XCUIElementTypeButton[1]");
		wait(1);	
		click("$**/XCUIElementTypeStaticText[`label == \"-\"`][1]");
		click("$**/XCUIElementTypeOther[`label == \"Bilyoner.com | Maç Kartı\"`]/XCUIElementTypeOther[2]/XCUIElementTypeImage");
		clickUnstable("$**/XCUIElementTypeOther[`label == \"Bilyoner.com | Maç Kartı\"`]/XCUIElementTypeOther[1]");
				
		click("$**/XCUIElementTypeStaticText[`label == \"-\"`][2]");
		click("$**/XCUIElementTypeOther[`label == \"Bilyoner.com | Maç Kartı\"`]/XCUIElementTypeOther[2]/XCUIElementTypeImage");
		clickUnstable("$**/XCUIElementTypeOther[`label == \"Bilyoner.com | Maç Kartı\"`]/XCUIElementTypeOther[1]");
		}
		else
			{
			click("$**/XCUIElementTypeOther[`label == \"İddaa - İddaa Bülteni & Tahminleri - Bilyoner\"`]/XCUIElementTypeButton[1]");
			}
		click("$**/XCUIElementTypeStaticText[`label == \"-\"`][3]");
		click("$**/XCUIElementTypeOther[`label == \"Bilyoner.com | Maç Kartı\"`]/XCUIElementTypeOther[2]/XCUIElementTypeImage");
		clickUnstable("$**/XCUIElementTypeOther[`label == \"Bilyoner.com | Maç Kartı\"`]/XCUIElementTypeOther[1]");
		
		click("$**/XCUIElementTypeOther[`label == \"İddaa - İddaa Bülteni & Tahminleri - Bilyoner\"`]/XCUIElementTypeButton[1]");
		System.out.println("favoriler seçildi");
		click("$**/XCUIElementTypeTextField[`value == 'Takım ara'`]");
		click("$**/XCUIElementTypeTextField[`value == 'Takım ara'`]");
		wait(1);
		swipe(200, 200, 200, 300, 300);
		wait(1);
	
		if (configure.device_name().contains("iPhone5S")
				|| configure.device_name().contains("iPhone6")) {
			click("#AÇ");
		}
		else {
			clickToCoordinate(335, 115);
		}
		System.out.println("uygulama açılıyor");
		wait(5);
		iosDriver.terminateApp("com.apple.mobilesafari");
		click("#filter favorite");
		return this;
	//*[@label='c']
//		click("#URL");
//		String directLink = readText("#URL").replace("://m.", "://www.");
//		iosDriver.get(directLink);
	}
	
	public pageIlkGiris kontrol_btn_GirisYap() {
		assertFound(btn_girisYap);
		return this;
	}
}