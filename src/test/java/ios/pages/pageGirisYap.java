package ios.pages;

import page.PageBaseIos;

import utilities.Configuration;

import utilities.SoftAssert;

import android.pages.pageIlkGiris;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.cucumber.java.Scenario;
import utilities.MappedLogin;

public class pageGirisYap extends PageBaseIos {

	IOSDriver<IOSElement> iosDriver;
	MappedLogin mappedLogin;

	// ********* Constructor *********
		public pageGirisYap(Object[] args) {
			super((IOSDriver<IOSElement>)args[0]);
			scenario = (Scenario)args[1];
			stepNumber = (int)args[2];
			assertPool = (SoftAssert)args[3];
			mappedLogin = new MappedLogin();
		}

		// ********************************* Elements ***********************************	
		String lbl_Baslik										= "@//XCUIElementTypeStaticText";
		String txt_TextBoxs										= "@//XCUIElementTypeTextField | //XCUIElementTypeSecureTextField";
		String btn_SifremiUnuttum								= "#Şifremi Unuttum";
		String txt_Sifre										= "$**/XCUIElementTypeSecureTextField[`value == 'Şifre'`]";
		String txt_TcKimlikUyeNo								= "$**/XCUIElementTypeTextField[`value == \"T.C. No veya Üye Numaran\"`]";
		String btn_GirisYap										= "#GİRİŞ YAP";
		String btn_Password										= "#iconsGeneralHide";
		String btn_HemenUyeOl									= "#HEMEN ÜYE OL";

	// *********Page Methods*********
		
	public pageGirisYap Baslik_Kontrol(String text) {
		assertListText(lbl_Baslik, text);
		return this;
	}

	public pageGirisYap TextBox_Kontrol(String text) {
		assertListText(txt_TextBoxs, text);
		return this;
	}

	public pageGirisYap kontrol_btn_GirisYap() {
		assertFound(btn_GirisYap);
		return this;
	}
	
	public pageGirisYap Sifremi_Unuttum_Buton_Kontrol() {
		assertFound(btn_SifremiUnuttum);
		return this;
	}
	
	public pageGirisYap Sifremi_Unuttum_Buton_Tikla() {
		click(btn_SifremiUnuttum);
		return this;
	}

//	public pageGirisYap Parmak_Izi_Kullanmak_Istemiyorum_Tikla() {
//		click(btn_ParmakIziKullanmakIstemiyorum);
//		return this;
//	}

	public pageGirisYap Giris_Basarili(String user_role) {
		if (user_role.equals("user")) {
			if(Configuration.getInstance().ipa_bundle_id().contains("beta"))
				user_role = "test" + user_role;
			else
				user_role = "prod" + user_role;	
		}
		
		writeText(txt_TcKimlikUyeNo, mappedLogin.get(user_role)[0]);
		writeText(txt_Sifre, mappedLogin.get(user_role)[1]);

		click(btn_GirisYap);
		if (exists("#Kullanmak istemiyorum", 1)) {
			click("#Kullanmak istemiyorum");
		}
		return this;
	}

	public pageGirisYap TC_Gir(String text) {
		writeText(txt_TcKimlikUyeNo, text);
		return this;
	}
	public pageGirisYap Sifre_Gir(String text) {
		writeText(txt_Sifre, text);
		return this;
	}

	public pageGirisYap Klavye_Gizle(){
		wait(1);
		hideKeyboard();
		return this;
	}

	public pageGirisYap Password_Tikla() {
		click(btn_Password);
		return this;
	}

	public pageGirisYap Password_AktifMi() {

		return this;
	}

	public pageGirisYap Password_PasifMi() {

		return this;
	}

	public pageGirisYap HemenUyeOl_Tikla() {
		click(btn_HemenUyeOl);
		return this;
	}

	public pageGirisYap GirisYap_Buton_PasifMi() {
		assertAttribute(btn_GirisYap, "enabled", "false");
		return this;
	}

	public pageGirisYap GirisYap_Buton_AktifMi() {
		assertAttribute(btn_GirisYap, "enabled", "true");
		return this;
	}
	public pageGirisYap tikla_btn_GirisYap() {
		if(exists("#GİRİŞ YAP", 2))
			click("#GİRİŞ YAP");
		else
			click("#Giriş Yap");
		return this;
	}

	public pageGirisYap ifLoggedDoLogout(String user_role) {
		if (exists("#Hoş geldin", 1)) {
			if (user_role.equals("user")) {
				if(Configuration.getInstance().ipa_bundle_id().contains("beta"))
					user_role = "test" + user_role;
				else
					user_role = "prod" + user_role;	
			}
			click("$**/XCUIElementTypeStaticText[`label == \""+ mappedLogin.get(user_role)[2] +"\"`]");
			click("#Çıkış Yap");
		}
		return this;
		
		
	}	public pageGirisYap konumServisiIzinVer() {
		if (exists("#Uygulamayı Kullanırken İzin Ver", 2)) 
			click("#Uygulamayı Kullanırken İzin Ver");
		
		return this;
	}

	public pageGirisYap ilkGirisReklamKapat() {
		if(exists("$**/XCUIElementTypeButton[`name == \'×'`]", 1))
			click("$**/XCUIElementTypeButton[`name == \'×'`]");
		return this;
	}
}