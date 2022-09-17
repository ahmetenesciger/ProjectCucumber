package mobile.pages;

import org.openqa.selenium.WebDriver;
import utilities.SoftAssert;

import io.cucumber.java.Scenario;
import page.PageBaseWeb;
import utilities.MappedLogin;

public class pageGirisYap extends PageBaseWeb {
	MappedLogin mappedLogin;
	
	// *********Constructor*********
	public pageGirisYap(Object[] args) {
		super((WebDriver)args[0]);
		scenario = (Scenario)args[1];
		stepNumber = (int)args[2];
		assertPool = (SoftAssert)args[3];
		mappedLogin = new MappedLogin();
	}

		// ********************************* Elements ***********************************
		String txt_TcKimlikUyeNo								= "#username";
		String txt_Sifre										= "#password";
		String btn_GirisYap										= ".btn--primary";
	
		String txt_Baslik										= ".form__header > h2";
		String btn_SifremiUnuttum								= ".link-forgot-password";


		// *********Page Methods*********
	public pageGirisYap BaslikKontrol(String text){
		assertEquals(txt_Baslik, text);
		return this;
	}

	public pageGirisYap UyeNoGir(String text) {
		writeText(txt_TcKimlikUyeNo, text);
		return this;
	}
	public pageGirisYap SifreGir(String text) {
		writeText(txt_Sifre, text);
		return this;
	}
	
	public pageGirisYap kontrol_btn_GirisYap() {
		assertFound(btn_GirisYap);
		return this;
	}
	
	public pageGirisYap Sifremi_Unuttum_Alan_Kontrol() {
		assertFound(btn_SifremiUnuttum);
		return this;
	}
	
	public pageGirisYap Sifremi_Unuttum_Alana_Tikla() {
		click(btn_SifremiUnuttum);
		return this;
	}

	public pageGirisYap GirisYap_Tikla() {
		click(btn_GirisYap);
		return this;
	}

	public void Giris_Basarili(String user_role) {
		if (user_role.equals("user")) {
			if (System.getProperty("environment").equals("test01")
					|| System.getProperty("environment").equals("test02")
					|| System.getProperty("environment").equals("m")
					|| System.getProperty("environment").equals("test08"))
				user_role = "prod" + user_role;
			else
				user_role = "test" + user_role;
		}

		if (exists(".btn-login", 1)) {
			click(".btn-login");
		} else if (exists("#username", 1)) {
		} else {
			navigateToURL("https://" + System.getProperty("environment") + ".bilyoner.com");
			assertFound(".bly-home");
			click(".btn-login");
		}

		writeText(txt_TcKimlikUyeNo, mappedLogin.get(user_role)[0]);
		writeText(txt_Sifre, mappedLogin.get(user_role)[1]);
		click(btn_GirisYap);
//		assertFound(".header-balance__info__value");
	}

	public pageGirisYap Bilgilerle_Giris() {
	  
		
	  assertFound("#username.input__input");
	  writeText("#username.input__input","15787609");
	  assertFound("#password.input__input");
	  writeText("#password.input__input","245966");
	  click(".btn--primary.btn--block");
	  
	  return this;
		
	}
}